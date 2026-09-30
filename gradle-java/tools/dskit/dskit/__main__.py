"""dskit: make and build a data-structure project from one ds.toml.

    dskit.sh scaffold <slug> <package> <MainClass>
                                  new project folder (category from the catalogue), Gradle
                                  wrapper and build, the amy-slow voice, and a starter ds.toml
    dskit.sh docs  <slug>         README, docs/*.md, pictures, diagrams, animation.html, scenes.py
    dskit.sh build <slug>         docs, then ./gradlew test run, thumbnail, HTML twins, video +
                                  audio + animation narration, YouTube doc, spec, and the index
    dskit.sh index                regenerate index.md / index.html
    dskit.sh next                 the next catalogue entry with no project yet
    dskit.sh html                 PLAN, START-HERE and index as HTML
    dskit.sh html-all [path]      every .md under the course (or under path) as themed HTML

Output is one line per step; full tool output goes to <project>/build/dskit.log.
"""

import shutil
import subprocess
import sys
from pathlib import Path

from . import catalogue, generate, index, mdhtml, thumbnail

ROOT = generate.ROOT
TOOLS = ROOT / "tools"
VIDEOKIT = TOOLS / "videokit" / "videokit.sh"
TEMPLATE = TOOLS / "template"

BUILD_GRADLE = """plugins {
    id 'java'
    id 'application'
}

group = 'com.jk.explore'
version = '1.0'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(%(java)s)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation platform('org.junit:junit-bom:%(junit)s')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

application {
    mainClass = 'com.jk.explore.%(package)s.%(main)s'
}

test {
    useJUnitPlatform()
}
"""

SETTINGS = """plugins {
    // Downloads JDK %(java)s for the toolchain on a machine that does not have it.
    id 'org.gradle.toolchains.foojay-resolver-convention' version '1.0.0'
}

rootProject.name = '%(slug)s'
"""

VOICE_TOML = """# The course voice (amy-slow): Piper, open source (MIT), US female,
# slower than normal with longer pauses, for beginners listening without a screen.
[voice]
engine = "piper"
voice  = "en_US-amy-medium"
speed  = 0.8

[pacing]
sentence_gap = 0.7
pause_scale  = 1.5
"""

BUILD_VIDEO = """#!/usr/bin/env bash
# Builds this project's video with the course's videokit library (see video/videokit.toml).
set -euo pipefail
cd "$(dirname "$0")"
exec ../../../tools/videokit/videokit.sh "${1:-all}" . "${@:2}"
"""

ANIMATION_AUDIO = """#!/usr/bin/env bash
# Narration clips for animation.html, from the course's videokit library.
set -euo pipefail
cd "$(dirname "$0")/.."
exec ../../tools/videokit/videokit.sh animation . "$@"
"""


def entry(slug):
    for e in catalogue.all_entries():
        if e[1] == slug:
            return e
    raise SystemExit("dskit: %r is not in the catalogue" % slug)


def project_dir(slug):
    return ROOT / entry(slug)[0] / slug


def scaffold(slug, package, main_class):
    cat, _, title, example = entry(slug)
    p = ROOT / cat / slug
    if p.exists():
        raise SystemExit("dskit: %s already exists" % p)
    (p / "src/main/java/com/jk/explore" / package).mkdir(parents=True)
    (p / "src/test/java/com/jk/explore" / package).mkdir(parents=True)
    (p / "docs").mkdir()
    (p / "video").mkdir()
    for f in ("gradlew", "gradlew.bat"):
        shutil.copy2(TEMPLATE / f, p / f)
    shutil.copytree(TEMPLATE / "gradle", p / "gradle")
    v = dict(java=generate.JAVA, junit=generate.JUNIT, package=package, main=main_class, slug=slug)
    (p / "settings.gradle").write_text(SETTINGS % v)
    (p / "build.gradle").write_text(BUILD_GRADLE % v)
    (p / "video/videokit.toml").write_text(VOICE_TOML)
    (p / "video/build_video.sh").write_text(BUILD_VIDEO)
    (p / "docs/make_animation_audio.sh").write_text(ANIMATION_AUDIO)
    for f in ("video/build_video.sh", "docs/make_animation_audio.sh"):
        (p / f).chmod(0o755)
    text = (Path(__file__).with_name("ds_template.toml")).read_text()
    (p / "ds.toml").write_text(text.replace("__SLUG__", slug).replace("__CATEGORY__", cat)
                               .replace("__TITLE__", title).replace("__EXAMPLE__", example)
                               .replace("__PACKAGE__", package).replace("__MAIN__", main_class))
    return p


def run(log, *cmd, cwd=None):
    r = subprocess.run([str(c) for c in cmd], cwd=cwd, capture_output=True, text=True)
    log.write("$ %s\n%s%s\n" % (" ".join(str(c) for c in cmd), r.stdout, r.stderr))
    # onnxruntime (behind the Piper voice) can abort with this message while the
    # process exits, after every file has been written; that is not a failure.
    if r.returncode and "recursive_mutex lock failed" not in r.stderr + r.stdout:
        raise SystemExit("dskit: failed: %s\n%s" % (" ".join(str(c) for c in cmd), (r.stdout + r.stderr)[-2500:]))
    return r.stdout


def html_twins(p):
    """Every .md in the project gets its themed .html twin."""
    return mdhtml.all_pages(p)


def build(slug, media=True):
    p = project_dir(slug)
    (p / "build").mkdir(exist_ok=True)
    with open(p / "build" / "dskit.log", "a") as log:
        print("  docs      " + generate.all_docs(p))
        out = run(log, p / "gradlew", "-q", "test", "run", cwd=p)
        print("  gradle    tests pass; demo printed %d lines" % len(out.splitlines()))
        (p / "docs" / "demo-output.txt").write_text(out)
        print("  docs      " + generate.all_docs(p) + " (test count refreshed)")
        thumbnail.render(generate.load(p), p / "docs" / "thumbnail.png")
        print("  thumbnail docs/thumbnail.png")
        if media:
            run(log, VIDEOKIT, "all", slug)
            print("  video     video, audio, subtitles, narration.md, youtube.md, spec")
            run(log, VIDEOKIT, "animation", slug)
            print("  animation narration clips, player and UX layer")
        n = html_twins(p)
        for name in ("PLAN.md", "START-HERE.md", "PROGRESS.md"):
            if (ROOT / name).exists():
                mdhtml.page(ROOT / name)
        print("  html      %d Markdown files, each with a themed HTML twin" % n)
        done, wip, total = index.build()
        print("  index     %d of %d done, %d in progress" % (done, total, wip))


def next_entry():
    for c, s, t, ex in catalogue.all_entries():
        if not (ROOT / c / s).exists():
            return "%s/%s  (%s: %s)" % (c, s, t, ex)
    return "all %d scaffolded" % len(catalogue.all_entries())


def main(argv):
    if not argv or argv[0] in ("help", "-h"):
        print(__doc__.strip())
        return
    cmd, rest = argv[0], argv[1:]
    if cmd == "scaffold":
        print(scaffold(*rest))
    elif cmd == "docs":
        print(generate.all_docs(project_dir(rest[0])))
    elif cmd == "build":
        build(rest[0], media="--no-media" not in rest)
    elif cmd == "index":
        print("%d of %d done, %d in progress" % (lambda d, w, t: (d, t, w))(*index.build()))
    elif cmd == "next":
        print(next_entry())
    elif cmd == "html-all":
        target = Path(rest[0]).resolve() if rest else ROOT
        print("%d Markdown files converted to themed HTML" % mdhtml.all_pages(target))
    elif cmd == "html":
        from . import start_here
        print("start-here pictures: %d" % start_here.draw())
        for name in ("PLAN.md", "START-HERE.md", "index.md"):
            if (ROOT / name).exists():
                print(mdhtml.page(ROOT / name))
    else:
        raise SystemExit("dskit: unknown command %r (try: help)" % cmd)


if __name__ == "__main__":
    main(sys.argv[1:])
