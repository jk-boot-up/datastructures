"""index.md and index.html at gradle-java/: the course's progress, category by category.

A project is **done** when its docs/youtube.md exists, because that is written
only after its video has been built; **in progress** when its folder exists
but the video is not built; **planned** otherwise. youtube.md is committed and
the video is not, so the index reads the same on any clone.
"""

from . import catalogue, mdhtml
from .generate import ROOT


def status(cat, slug):
    p = ROOT / cat / slug
    if (p / "docs" / "youtube.md").exists():
        return "done"
    if (p / "ds.toml").exists():
        return "in progress"
    return "planned"


def build():
    rows = [(c, s, t, ex, status(c, s)) for c, s, t, ex in catalogue.all_entries()]
    total = len(rows)
    done = sum(r[4] == "done" for r in rows)
    wip = sum(r[4] == "in progress" for r in rows)
    out = ["# Data Structures in Java — Course Index", "",
           "Every classic data structure as its own Java 25 / Gradle project, built from scratch and explained "
           "for complete beginners, each with a narrated video and an interactive animation. Most structures "
           "also have a generic version, written with type parameters so it holds any type of object. The plan is in "
           "[PLAN.md](PLAN.md); new to the subject? Begin with [Start here](START-HERE.md).", "",
           "## Progress", "",
           "**%d of %d done**, %d in progress, %d planned." % (done, total, wip, total - done - wip), "",
           "| Category | Done | In progress | Planned | Total |", "| --- | ---: | ---: | ---: | ---: |"]
    for cat, name in catalogue.CATEGORIES:
        rs = [r for r in rows if r[0] == cat]
        out.append("| [%s](#%s) | %d | %d | %d | %d |" % (
            name, name.lower().replace(" ", "-"), sum(r[4] == "done" for r in rs),
            sum(r[4] == "in progress" for r in rs), sum(r[4] == "planned" for r in rs), len(rs)))
    out.append("| **All** | **%d** | **%d** | **%d** | **%d** |" % (done, wip, total - done - wip, total))
    n = 0
    for cat, name in catalogue.CATEGORIES:
        out += ["", "## %s" % name, "", "| # | Data structure | Worked example | Status | Material |",
                "| ---: | --- | --- | --- | --- |"]
        for c, s, t, ex, st in (r for r in rows if r[0] == cat):
            n += 1
            if st == "planned":
                links, label = "", t
            else:
                base = "%s/%s" % (c, s)
                label = "[%s](%s/README.md)" % (t, base)
                links = " · ".join(["[README](%s/README.md)" % base,
                                    "[explained](%s/docs/%s-explained.md)" % (base, s),
                                    "[animation](%s/docs/animation.html)" % base,
                                    "[cheat sheet](%s/docs/cheat-sheet.md)" % base] +
                                   (["[YouTube](%s/docs/youtube.md)" % base] if st == "done" else []))
            badge = {"done": "✅ done", "in progress": "🛠 in progress", "planned": "planned"}[st]
            out.append("| %d | %s | %s | %s | %s |" % (n, label, ex, badge, links))
    (ROOT / "index.md").write_text("\n".join(out) + "\n")
    mdhtml.page(ROOT / "index.md")
    return done, wip, total
