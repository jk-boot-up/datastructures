"""Turn a project's ds.toml into its README, docs, pictures, animation and video scenes.

Every sentence comes from ds.toml; this module only lays it out, the same way
for every project, so the teaching text is written once and never copied by
hand between files. The order of every page follows PLAN.md section 2:
picture first, words second, code last.
"""

import json
import re
import tomllib
from pathlib import Path

from . import diagrams, structure

JAVA = "25"
JUNIT = "6.1.3"
VOICE = "Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice)"
ROOT = Path(__file__).resolve().parents[3]          # gradle-java/


def load(pdir):
    return tomllib.loads((Path(pdir) / "ds.toml").read_text())


def ul(items):
    return "\n".join("- " + i.strip() for i in items)


def ol(items):
    return "\n".join("%d. %s" % (n, i.strip()) for n, i in enumerate(items, 1))


def para(text):
    return text.strip() + "\n"


def spoken(text):
    """Narration text without the [[slnc N]] pauses."""
    return " ".join(re.sub(r"\[\[slnc \d+\]\]", " ", text).split())


def rel_root(pdir):
    return "../" * len(Path(pdir).relative_to(ROOT).parts)


# ------------------------------------------------------------ pictures ----

def png(picture, out_path, width=1400):
    """A picture as a PNG on the dark stage colour, for the docs."""
    import cairosvg
    svg = structure.svg(picture)
    svg = re.sub(r"var\(--vk-[0-9a-f]{6}, (#[0-9a-f]{6})\)", r"\1", svg)
    svg = svg.replace('style="max-width:100%;height:auto">',
                      'style="background:#0f172a"><rect width="100%" height="100%" fill="#0f172a"/>', 1)
    natural = int(re.search(r'width="(\d+)"', svg).group(1))
    # Never enlarge a small picture more than twice: a ring of one should not fill the page.
    cairosvg.svg2png(bytestring=svg.encode("utf-8"), write_to=str(out_path),
                     output_width=min(width, natural * 2))


def frames(d):
    """Every (act number, frame number, act, frame) in order."""
    out = []
    for i, a in enumerate(d["act"], 1):
        for j, f in enumerate(a.get("frame", []), 1):
            out.append((i, j, a, f))
    return out


def pictures(d, pdir):
    img = pdir / "docs" / "images"
    img.mkdir(parents=True, exist_ok=True)
    for old in img.glob("act-*.png"):
        old.unlink()
    png(d["hero"]["picture"], img / "structure.png")
    for i, j, a, f in frames(d):
        png(f["picture"], img / ("act-%d-%d.png" % (i, j)), width=1100)
    return 1 + len(frames(d))


# ------------------------------------------------------------- helpers ----

def java_tree(pdir, package):
    src = pdir / "src" / "main" / "java" / "com" / "jk" / "explore" / package
    rows = []
    for f in sorted(src.rglob("*.java")):
        m = re.search(r"/\*\*(.*?)\*/", f.read_text(), re.S)
        doc = " ".join(m.group(1).replace("*", " ").split()) if m else ""
        first = re.split(r"(?<=[.!?])\s", doc, maxsplit=1)[0].rstrip(".")
        rows.append((str(f.relative_to(src)), first))
    width = max((len(n) for n, _ in rows), default=10) + 2
    lines = ["src/main/java/com/jk/explore/%s/" % package]
    for k, (n, w) in enumerate(rows):
        lines.append("%s %s%s" % ("└──" if k == len(rows) - 1 else "├──", n.ljust(width), w))
    return "```\n" + "\n".join(lines) + "\n```"


def test_count(pdir):
    tests = list((pdir / "src" / "test").rglob("*.java"))
    n = sum(len(re.findall(r"@(?:Test|ParameterizedTest|RepeatedTest)\b", f.read_text())) for f in tests)
    return n, sorted(f.stem for f in tests)


def gradle_version(pdir):
    props = (pdir / "gradle" / "wrapper" / "gradle-wrapper.properties").read_text()
    m = re.search(r"gradle-([\d.]+)-", props)
    return m.group(1) if m else "wrapper"


def ops_table(d):
    """Time (best / average / worst) and auxiliary space of every operation in this project."""
    if any("recursive_space" in o for o in d["operation"]):
        rows = ["| Operation | What it does | Time: best / average / worst | Extra space (call stack) "
                "| Same operation as a loop | Steps counted in the demo |", "| --- | --- | --- | --- | --- | --- |"]
        for o in d["operation"]:
            rows.append("| %s | %s | %s | %s | %s | %s |" % (
                o["name"], o["does"], o.get("time", ""), o["recursive_space"], o.get("space", "O(1)"), o["counted"]))
        return rows
    rows = ["| Operation | What it does | Time: best / average / worst | Extra space | Steps counted in the demo |",
            "| --- | --- | --- | --- | --- |"]
    for o in d["operation"]:
        rows.append("| %s | %s | %s | %s | %s |" % (
            o["name"], o["does"], o.get("time", o.get("big_o", "")), o.get("space", "O(1)"), o["counted"]))
    return rows


def extract_method(pdir, package, ref):
    """The source of methods, with their Javadoc, from "ClassName#name" or "ClassName#name,helper".

    Every overload of each name is included, in file order, so a recursive project shows the public
    method and the private recursive helper it calls.
    """
    cls, names = ref.split("#")
    src = (pdir / "src" / "main" / "java" / "com" / "jk" / "explore" / package / (cls + ".java")).read_text()
    lines = src.splitlines()
    blocks = []
    for name in names.split(","):
        found = False
        for sig, line in enumerate(lines):
            if not re.match(r"    (public|private|protected|static)\b[^;=]*\b%s\(" % re.escape(name), line):
                continue
            found = True
            start = sig
            while start > 0 and (lines[start - 1].strip().startswith("@") or lines[start - 1].strip().startswith("*")
                                 or lines[start - 1].strip().startswith("/**")):
                start -= 1
            depth, end, seen = 0, sig, False
            for k in range(sig, len(lines)):
                depth += lines[k].count("{") - lines[k].count("}")
                seen = seen or "{" in lines[k]
                if seen and depth == 0:
                    end = k
                    break
            blocks.append("\n".join(l[4:] if l.startswith("    ") else l for l in lines[start:end + 1]))
        if not found:
            raise SystemExit("dskit: no method %s in %s" % (name, cls))
    return "\n\n".join(blocks)


def listing_section(d, pdir, heading="##"):
    """The key operations as code, taken from the source files, each with a short explanation."""
    items = d.get("listing", [])
    if not items:
        return []
    intro = d.get("listing_intro", "")
    out = ["%s The operations in code" % heading, ""]
    if intro:
        out.append(para(intro))
    for r in items:
        out += ["%s# %s" % (heading, r["op"]), "", "```java",
                extract_method(pdir, d["project"]["package"], r["method"]), "```", "", para(r["note"])]
    return out


def compare_section(d, heading="##"):
    c = d.get("compare")
    out = []
    if c:
        out += ["%s Compared with related structures" % heading, "", para(c.get("intro", "")),
                "| " + " | ".join(c["columns"]) + " |", "| " + " | ".join("---" for _ in c["columns"]) + " |"]
        out += ["| " + " | ".join(r) + " |" for r in c["rows"]]
        out.append("")
    if d.get("pros") or d.get("cons"):
        out += ["%s Pros and cons" % heading, "", "**Pros**", "", ul(d.get("pros", [])), "", "**Cons**", "", ul(d.get("cons", [])), ""]
    return out


# -------------------------------------------------------------- README ----

def readme(d, pdir):
    p, r = d["project"], d["readme"]
    slug, title = p["slug"], p["title"]
    n_tests, test_classes = test_count(pdir)
    acts = d["act"]
    diag = {x["name"]: x for x in d.get("diagram", [])}
    up = rel_root(pdir)
    out = ["# %s" % title, "",
           "**%s**" % p["bold"].strip(), "",
           "![%s](docs/images/structure.png)" % d["hero"]["caption"], "",
           "*%s*" % d["hero"]["caption"].strip(), "",
           para(r["intro"]),
           "> New to data structures? Read [Start here](%sSTART-HERE.md) first: it explains memory, "
           "references and counting steps in ten minutes." % up, "",
           "## The everyday idea", "", para(r["analogy"]),
           "## The worked example: %s" % p["example"], "", para(r["example"]),
           "## Why it exists", "", para(r["why"]),
           "## New words", "",
           "| Word | What it means here |", "| --- | --- |"]
    out += ["| **%s** | %s |" % (w["term"], w["meaning"]) for w in d["word"]]
    out += ["", "## What you will see, act by act", "",
            "Run the demo and it tells the story in %d acts, printing the real numbers that the tests check:" % len(acts), "",
            "```bash", "./gradlew run", "```", "",
            "| Act | What it shows |", "| --- | --- |"]
    out += ["| %d. %s | %s |" % (i, a["title"], a["summary"]) for i, a in enumerate(acts, 1)]
    out += ["", "Each act is drawn step by step in [the explained walkthrough](docs/%s-explained.md) "
            "and in [the animation](docs/animation.html)." % slug, "",
            "## The operations, and what they cost", "",
            "Time is given for the best, average and worst case in Big-O notation (START-HERE.md explains it by "
            "counting steps); the last column is what the demo actually counted, so every formula can be checked "
            "against a real number.", ""]
    out += ops_table(d)
    out += ["", "Extra space is the memory an operation needs besides the structure itself. A loop needs a "
            "fixed amount, O(1); a recursive operation needs one call-stack frame for every call still "
            "waiting, so its extra space is the depth of the recursion.", ""]
    out += listing_section(d, pdir)
    out += compare_section(d)
    out += ["## The code", "", java_tree(pdir, p["package"]), "",
            "## Test", "", "```bash", "./gradlew test", "```", "",
            "%d tests in %s. Every number the demo prints is asserted, and nothing depends on the clock, "
            "so every run gives the same result." % (n_tests, ", ".join("`%s`" % c for c in test_classes)), "",
            "## Common mistakes", "",
            "| The mistake | What goes wrong | The fix |", "| --- | --- | --- |"]
    out += ["| %s | %s | %s |" % (m["mistake"], m["happens"], m["fix"]) for m in d["mistake"]]
    out += ["", "## Try it yourself", "",
            ol("**%s.** %s" % (e["level"].capitalize(), e["question"]) for e in d["exercise"]), "",
            "The answers are in [docs/exercises.md](docs/exercises.md), below the questions, so they are not "
            "given away.", "",
            "## When to use it", "", ul(r["when_use"]), "",
            "## When not to", "", ul(r["when_not"]), "",
            "## Where you have already met this", "", ul(r["met_before"]), "",
            "## Technologies and versions", "",
            "| Technology | Version | Used for |", "| --- | --- | --- |",
            "| Java | %s | the code (toolchain set in `build.gradle`; Gradle fetches JDK %s if it is missing) |" % (JAVA, JAVA),
            "| Gradle | %s (wrapper) | build and run, nothing to install |" % gradle_version(pdir),
            "| JUnit | %s | the tests |" % JUNIT]
    for t in r.get("technologies", []):
        out.append("| %s | %s | %s |" % tuple(t))
    out += ["| videokit | course tool | the narrated video and animation: %s |" % VOICE, "",
            "## Learning material", "",
            "| Document | What it is for |", "| --- | --- |",
            "| [Start here](%sSTART-HERE.md) | the ideas every project relies on |" % up,
            "| [Problem statement](docs/problem-statement.md) | the situation and what the project must show |",
            "| [Prerequisites](docs/prerequisites.md) | what you need to know first |",
            "| [%s, explained](docs/%s-explained.md) | every act, picture by picture |" % (title, slug),
            "| [Animated walkthrough](docs/animation.html) | the structure changing step by step, narrated |",
            "| [Cheat sheet](docs/cheat-sheet.md) | the whole structure on one page |",
            "| [Exercises and answers](docs/exercises.md) | practice, easy to harder |",
            "| [Session guide](docs/session.md) | a one-hour lesson |",
            "| [Specification](docs/spec.md) | what this project must be true of |", ""]
    for name, heading in (("architecture-diagram", "How the pieces fit"),
                          ("class-diagram", "The classes"),
                          ("data-flow-diagram", "How the data moves"),
                          ("sequence-diagram", "Who calls whom, in order")):
        if name in diag:
            out += ["### " + heading, "", diag[name].get("caption", "").strip(), "",
                    "![%s](docs/images/%s.png)" % (diag[name]["title"], name), ""]
    out += ["### Video", "",
            "`video/%s-explained.mp4` (with `.m4a` audio and `.srt` subtitles) is built by "
            "`video/build_video.sh`. Rendered media is not committed." % slug, "",
            "## Where this sits", "",
            "Part of the [data structures course](%sindex.md), in *%s*." % (up, p["category"]), ""]
    return "\n".join(out).rstrip() + "\n"


# ---------------------------------------------------------------- docs ----

def explained(d, pdir=None):
    p, x = d["project"], d["docs"]["explained"]
    out = ["# %s, Explained" % p["title"], "", "## In one sentence", "", para(x["sentence"]),
           "## The picture", "", "![%s](images/structure.png)" % d["hero"]["caption"], "",
           "*%s*" % d["hero"]["caption"].strip(), "",
           "## The everyday idea", "", para(d["readme"]["analogy"]),
           "## The %d acts" % len(d["act"]), ""]
    for i, a in enumerate(d["act"], 1):
        out += ["### Act %d: %s" % (i, a["title"]), "", para(a["detail"])]
        for j, f in enumerate(a.get("frame", []), 1):
            out += ["![%s](images/act-%d-%d.png)" % (f.get("caption", ""), i, j), "",
                    "**%s** %s" % (f.get("caption", "").strip(), spoken(f["say"])), ""]
        if a.get("console"):
            out += ["What the demo printed:", "", "```"] + [c[0] for c in a["console"]] + ["```", ""]
    out += listing_section(d, pdir) if pdir is not None else []
    out += ["## The operations, and what they cost", ""] + ops_table(d) + [""]
    out += compare_section(d)
    out += ["## The verdict", "", para(x["verdict"]),
        "## How to recognise it in code you did not write", "", ul(x["recognise"]), "",
        "## Where you have already met this", "", ul(d["readme"]["met_before"]), ""]
    return "\n".join(out)


def problem(d):
    x = d["docs"]["problem"]
    return "\n".join(["# Problem Statement — %s" % d["project"]["title"], "",
                      "## The situation", "", para(x["scenario"]),
                      "## The obvious approach, and where it breaks", "", para(x["naive"]),
                      "## What this project must show", "", ul(x["deliver"]), ""])


def prerequisites(d, pdir):
    x = d["docs"]["prerequisites"]
    return "\n".join(["# Prerequisites — %s" % d["project"]["title"], "",
                      "## You need", "", ul(x["required"]), "",
                      "## You do not need", "", ul(x["not_required"]), "",
                      "## If any of that is new", "",
                      "[Start here](%sSTART-HERE.md) explains memory, references and counting steps "
                      "from the beginning." % ("../" + rel_root(pdir)), "",
                      "## What to install", "",
                      "- Nothing but a JDK. The Gradle wrapper downloads Gradle, and the build downloads "
                      "JDK %s itself if your machine does not have it." % JAVA, "",
                      "- About an hour for the session guide.", ""])


def session(d):
    p, x, acts = d["project"], d["docs"]["session"], d["act"]
    per = max(5, 35 // max(1, len(acts)))
    rows = ["| 0:00 | The everyday idea and the picture | 10 min |"]
    t = 10
    for i, a in enumerate(acts, 1):
        rows.append("| 0:%02d | Act %d: %s | %d min |" % (t, i, a["title"], per))
        t += per
    rows.append("| 0:%02d | Try it yourself | %d min |" % (t, max(10, 60 - t)))
    return "\n".join(["# Session Guide — %s" % p["title"], "", "## By the end you can", "", ul(x["objectives"]), "",
                      "## Timetable", "", "| Start | Topic | Time |", "| --- | --- | --- |", *rows, "",
                      "## Walkthrough", "", para(x["walkthrough"]),
                      "## Exercises", "", "See [exercises.md](exercises.md).", ""])


def cheat_sheet(d):
    p, r = d["project"], d["readme"]
    out = ["# %s — Cheat Sheet" % p["title"], "", "**%s**" % p["bold"].strip(), "",
           "![%s](images/structure.png)" % d["hero"]["caption"], "",
           "| | |", "| --- | --- |",
           "| **What it is** | %s |" % " ".join(d["docs"]["explained"]["sentence"].split()),
           "| **Everyday picture** | %s |" % p["analogy_short"],
           "| **Use it when** | %s |" % "; ".join(x.strip().rstrip(".") for x in r["when_use"]),
           "| **Avoid it when** | %s |" % "; ".join(x.strip().rstrip(".") for x in r["when_not"]),
           "| **Already in Java** | %s |" % p["jdk"], "",
           "## Costs", ""] + ops_table(d) + [""] + compare_section(d) + ["## Watch out for", "",
                                               ul("**%s**: %s" % (m["mistake"], m["fix"]) for m in d["mistake"]), ""]
    return "\n".join(out)


def exercises(d):
    ex = d["exercise"]
    out = ["# Exercises — %s" % d["project"]["title"], "",
           "Try each one before opening its answer. They start easy and get harder.", ""]
    for n, e in enumerate(ex, 1):
        out += ["## %d. %s" % (n, e["level"].capitalize()), "", para(e["question"])]
    out += ["---", "", "## Answers", ""]
    for n, e in enumerate(ex, 1):
        out += ["<details>", "<summary>Answer %d</summary>" % n, "", para(e["answer"]), "</details>", ""]
    return "\n".join(out)


def spec(d, pdir):
    x, p = d["spec"], d["project"]
    return "\n".join(["# Specification — %s" % p["title"], "", "## Purpose", "", para(x["purpose"]),
                      "## Requirements", "", ol(x["requirements"]), "",
                      "## Not in scope", "", ul(x["nongoals"]), "",
                      "## Deliverables", "",
                      "- `src/main/java/com/jk/explore/%s/`: the structure and `%s`" % (p["package"], p["main_class"]),
                      "- `src/test/java/...`: tests for every operation and edge case, and that the demo runs",
                      "- `README.md` and `README.html`, `docs/` (explained, cheat sheet, exercises, session, "
                      "diagrams, `animation.html`, `youtube.md`) and `video/` (scenes and voice)",
                      "- Java %s, Gradle wrapper %s, JUnit %s" % (JAVA, gradle_version(pdir), JUNIT), ""])


def diagram_docs(d, pdir):
    title, docs = d["project"]["title"], pdir / "docs"
    (docs / "images").mkdir(parents=True, exist_ok=True)
    for x in d.get("diagram", []):
        diagrams.render(x, docs / "images" / (x["name"] + ".png"))
        kind = x["name"].replace("-diagram", "").replace("-", " ").title()
        (docs / (x["name"] + ".md")).write_text("\n".join([
            "# %s — %s Diagram" % (title, kind), "", para(x.get("caption", "")),
            "![%s](images/%s.png)" % (x["title"], x["name"]), "", para(x.get("prose", ""))]))
    return len(d.get("diagram", []))


# ----------------------------------------------------------- animation ----

def animation(d, pdir):
    p = d["project"]
    steps = []
    for i, j, a, f in frames(d):
        n = len(a.get("frame", []))
        steps.append(dict(label="Act %d — %s%s" % (i, a["title"], " (%d of %d)" % (j, n) if n > 1 else ""),
                          narration=spoken(f["say"]), caption=f.get("caption", ""),
                          svg=structure.svg(f["picture"]), console=[list(c) for c in a.get("console", [])]))
    html = ((Path(__file__).with_name("anim_template.html")).read_text()
            .replace("__TITLE__", p["title"]).replace("__SUBTITLE__", d["animation"]["subtitle"].strip())
            .replace("__STEPS__", json.dumps(steps, indent=1, ensure_ascii=False))
            .replace("__START_SVG__", json.dumps(structure.svg(d["hero"]["picture"]), ensure_ascii=False)))
    (pdir / "docs" / "animation.html").write_text(html)
    return len(steps)


# -------------------------------------------------------------- scenes ----

ORDINAL = ["One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"]


def _act_scenes(d):
    """Per act: its console slide (the act's speech), then one picture slide per frame."""
    out = []
    for i, a in enumerate(d["act"], 1):
        lines = ["%s. %s." % (ORDINAL[i - 1].upper(), a["title"])] + ["  " + c[0] for c in a.get("console", [])]
        out.append(dict(key="act-%d" % i, kind="console", title="Act %s — %s" % (ORDINAL[i - 1], a["title"]),
                        body="\n".join(lines), narration=a["speech"]))
        for j, f in enumerate(a.get("frame", []), 1):
            out.append(dict(key="act-%d-%d" % (i, j), kind="structure", title=a["title"],
                            svg=structure.svg(f["picture"]), caption=f.get("caption", ""), narration=f["say"]))
    return out


def scenes(d, pdir):
    p = d["project"]
    out = []
    for s in d["scene"]:
        if s["kind"] == "acts":
            out += _act_scenes(d)
        elif s["kind"] == "structure":
            out.append(dict(s, svg=structure.svg(s["picture"])))
        else:
            out.append(dict(s))
    for n, s in enumerate(out, 1):
        s["key"] = "%02d-%s" % (n, re.sub(r"^\d+-", "", s["key"]))
    lines = ['"""Scene definitions for the %s teaching video (generated from ds.toml)."""' % p["title"],
             "", "SCENES = ["]
    for s in out:
        extra = ""
        if s["kind"] == "structure":
            extra = ",\n         svg=%r,\n         caption=%r" % (s["svg"], s.get("caption", ""))
        lines.append("    dict(key=%r, kind=%r, title=%r,\n         body=%r%s,\n         narration=%r),"
                     % (s["key"], s["kind"], s["title"], s.get("body"), extra, " ".join(s["narration"].split())))
    poster = dict(d.get("poster", {}))
    for k in ("headline", "taglines"):
        if k in poster:
            poster[k] = [tuple(x) for x in poster[k]]
    video = dict(footer="%s  ·  Java %s" % (p["title"], JAVA), tagline="DATA STRUCTURES  -  JAVA",
                 poster=poster or None, highlight=d.get("highlight", {}))
    lines += ["]", "", "VIDEO = %r" % video, ""]
    (pdir / "video" / "scenes.py").write_text("\n".join(lines))
    return len(out)


def video_readme(d, pdir):
    p = d["project"]
    desc = " ".join(p["youtube_description"].split())
    (pdir / "video" / "README.md").write_text("\n".join([
        "# %s — Teaching Video" % p["title"], "",
        "A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.", "",
        "| File | What it is |", "| --- | --- |",
        "| `%s-explained.mp4` | the video, 1920×1080 |" % p["slug"],
        "| `%s-explained.m4a` | audio only |" % p["slug"],
        "| `%s-explained.srt` | subtitles |" % p["slug"],
        "| `poster.png` | the opening frame |", "",
        "**Narration:** %s. Scenes are generated from `../ds.toml` into `scenes.py`." % VOICE, "",
        "Suggested description:", "", "> " + desc, ""]))


def all_docs(pdir):
    pdir = Path(pdir)
    d = load(pdir)
    slug = d["project"]["slug"]
    docs = pdir / "docs"
    docs.mkdir(exist_ok=True)
    n_pics = pictures(d, pdir)
    (pdir / "README.md").write_text(readme(d, pdir))
    (docs / "problem-statement.md").write_text(problem(d))
    (docs / "prerequisites.md").write_text(prerequisites(d, pdir))
    (docs / "session.md").write_text(session(d))
    (docs / "cheat-sheet.md").write_text(cheat_sheet(d))
    (docs / "exercises.md").write_text(exercises(d))
    (docs / "spec.md").write_text(spec(d, pdir))
    (docs / ("%s-explained.md" % slug)).write_text(explained(d, pdir))
    n_diag = diagram_docs(d, pdir)
    n_steps = animation(d, pdir)
    n_scenes = scenes(d, pdir)
    video_readme(d, pdir)
    return "README + 7 docs, %d pictures, %d diagrams, %d animation steps, %d scenes" % (
        n_pics, n_diag, n_steps, n_scenes)
