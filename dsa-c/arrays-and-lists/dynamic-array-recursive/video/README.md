# Dynamic Array (Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `dynamic-array-recursive-explained.mp4` | the video, 1920×1080 |
| `dynamic-array-recursive-explained.m4a` | audio only |
| `dynamic-array-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Dynamic Array in C, written recursively, explained with a music playlist that grows one song at a time. We trace a recursive resize call by call: copy_from(0) copies Blue Sky and calls copy_from(1), down to the base case copy_from(4), four copies at depth 4. We grow the playlist to 9 songs with 2 resizes and 12 copies, traverse it at depth 9, insert at the front with 9 recursive shifts, delete from the middle with 4, and search recursively. We shrink to fit with 9 copies at depth 9 and see the capacity halve when deletions leave it a quarter full. We finish at the limit: appending a million songs overflows the call stack inside a resize, and the child process running it is stopped, because a recursive copy needs one frame per element. The loop version is in the Dynamic Array project.
