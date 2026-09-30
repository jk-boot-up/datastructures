"""Documents derived from a finished video: narration.md, youtube.md and the spec's runtime.

videokit's docs stage runs the three scripts in tools/dskit/hooks/, which call
these functions with a project slug.
"""

import importlib.util
import re
import subprocess
import tomllib
from pathlib import Path

from . import generate, mdhtml

ROOT = generate.ROOT
REPO_URL = "https://github.com/jk-boot-up/datastructures"
COMMON_TAGS = ["data structures", "java data structures", "java", "java 25", "algorithms",
               "computer science", "programming tutorial", "beginner", "big o"]


def project_dir(slug):
    hits = sorted(ROOT.glob("*/%s/ds.toml" % slug))
    if len(hits) != 1:
        raise SystemExit("dskit: %s project %r" % ("no" if not hits else "more than one", slug))
    return hits[0].parent


def _scenes(pdir):
    spec = importlib.util.spec_from_file_location("scenes_" + pdir.name, pdir / "video" / "scenes.py")
    m = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(m)
    return m.SCENES


def narration(slug):
    pdir = project_dir(slug)
    d = generate.load(pdir)
    out = ["# %s — Video Narration Script" % d["project"]["title"], ""]
    for n, s in enumerate(_scenes(pdir), 1):
        out += ["## %d. %s" % (n, s["title"]), "", generate.spoken(s["narration"]), ""]
    (pdir / "video" / "narration.md").write_text("\n".join(out))


def _mmss(t):
    t = int(t)
    h, rem = divmod(t, 3600)
    m, s = divmod(rem, 60)
    return "%d:%02d:%02d" % (h, m, s) if h else "%02d:%02d" % (m, s)


def chapters(pdir):
    """`mm:ss Title` per scene with a new title, from the SRT's cue starts."""
    import sys
    sys.path.insert(0, str(ROOT / "tools" / "videokit"))
    from videokit.script import split_cues
    srt = (pdir / "video" / ("%s-explained.srt" % pdir.name)).read_text()
    starts = []
    for m in re.finditer(r"(\d\d):(\d\d):(\d\d),(\d\d\d) -->", srt):
        h, mi, s, ms = (int(x) for x in m.groups())
        starts.append(h * 3600 + mi * 60 + s + ms / 1000)
    lines, cursor, last = [], 0, None
    for i, s in enumerate(_scenes(pdir)):
        if cursor >= len(starts):
            break
        name = "Introduction" if i == 0 else s["title"]
        if name != last:
            lines.append("%s %s" % (_mmss(0 if i == 0 else starts[cursor]), name))
            last = name
        cursor += len(split_cues(s["narration"]))
    return lines


def runtime(pdir):
    mp4 = pdir / "video" / ("%s-explained.mp4" % pdir.name)
    if not mp4.exists():
        return None
    r = subprocess.run(["ffprobe", "-v", "error", "-show_entries", "format=duration", "-of", "csv=p=0", str(mp4)],
                       capture_output=True, text=True)
    try:
        return float(r.stdout.strip())
    except ValueError:
        return None


def youtube(slug):
    pdir = project_dir(slug)
    d = generate.load(pdir)
    p = d["project"]
    title = p["youtube_title"]
    tags = ", ".join(p["youtube_tags"] + COMMON_TAGS)
    chaps = chapters(pdir)
    desc = " ".join(p["youtube_description"].split())
    rel = pdir.relative_to(ROOT.parent)
    t = runtime(pdir)
    out = ["# YouTube — %s" % p["title"], "",
           "Everything needed to publish `video/%s-explained.mp4`. Copy the fields straight out of this file." % slug, "",
           "Chapter timings come from the video's `.srt`; they are regenerated whenever the video is rebuilt.", "",
           "## Title", "", "```", title, "```", "",
           "%d characters (YouTube shows about 60 before cutting a title off in search)." % len(title), "",
           "## Description", "",
           "The first two lines are what a viewer sees above the fold, so they carry the hook rather than the boilerplate.", "",
           "```", desc, "", "CHAPTERS", *chaps, "",
           "SOURCE CODE, WRITTEN NOTES AND AN INTERACTIVE ANIMATION",
           "%s/tree/main/%s" % (REPO_URL, rel), "",
           "WHAT YOU NEED FIRST",
           "Java basics: variables, loops, classes and methods. No data-structures knowledge is assumed; "
           "START-HERE.md in the repository explains memory, references and counting steps.", "```", "",
           "## Chapters", "", "YouTube shows these as chapters because there are at least three and the first is at `00:00`.", "",
           "```", *chaps, "```", "",
           "## Tags", "", "```", tags, "```", "", "%d characters, under YouTube's 500 limit." % len(tags), "",
           "## Thumbnail", "", "![Thumbnail](thumbnail.png)", "",
           "Upload `docs/thumbnail.png` (1280×720) under **Details → Thumbnail → Upload file**. It is made for "
           "the small size YouTube shows in search: the name, one line of promise and one piece of code.", "",
           "## Upload checklist", "",
           "- [ ] Upload `video/%s-explained.mp4` (1080p, H.264, 30 fps, AAC 48 kHz)" % slug,
           "- [ ] Set the video language to **English**, then add subtitles: *Upload file → With timing* → "
           "`video/%s-explained.srt`" % slug,
           "- [ ] Upload `docs/thumbnail.png` as the thumbnail",
           "- [ ] Paste the title, description and tags from above",
           "- [ ] Add to the **Data Structures in Java** playlist",
           "- [ ] Wait for 1080p processing before sharing the link", "",
           "## Runtime", "", ("%s." % _mmss(t)) if t else "Not built yet.", ""]
    (pdir / "docs" / "youtube.md").write_text("\n".join(out))


def spec(slug):
    pdir = project_dir(slug)
    path = pdir / "docs" / "spec.md"
    if not path.exists():
        path.write_text(generate.spec(generate.load(pdir), pdir))
    text = path.read_text()
    text = re.sub(r"\n## Video\n.*", "", text, flags=re.S)
    t = runtime(pdir)
    if t:
        text = text.rstrip() + "\n\n## Video\n\nThe narrated video runs %s, voiced by %s.\n" % (_mmss(t), generate.VOICE)
    path.write_text(text)
    mdhtml.page(path)
