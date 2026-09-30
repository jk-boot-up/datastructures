# Skip List — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `skip-list-explained.mp4` | the video, 1920×1080 |
| `skip-list-explained.m4a` | audio only |
| `skip-list-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Skip List in Java, explained with a train line that has a stopping service and express services. We start with sixteen stations on a stopping line and count thirteen hops to reach Marden, because a sorted linked list has no middle to jump to. Then we build express levels by hand, as in C: every station keeps one forward arrow per level it stands on, and a coin toss decides how tall each station is. We ride the express and change down, finding Marden in three hops, add and remove stations level by level, and meet an unlucky coin that turns the whole line back into a stopping service. We finish with the bill at a thousand stations: 9.7 hops on average instead of 500.5, for about two arrows per station.
