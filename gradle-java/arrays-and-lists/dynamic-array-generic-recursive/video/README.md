# Dynamic Array (Generic, Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `dynamic-array-generic-recursive-explained.mp4` | the video, 1920×1080 |
| `dynamic-array-generic-recursive-explained.m4a` | audio only |
| `dynamic-array-generic-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Generic Recursive Dynamic Array in Java, explained with a playlist of Song records. We trace a recursive resize that copies references, not songs: copy(0) copies the reference to Blue Sky and calls copy(1), down to the base case copy(4), four frames deep, and the Song at index 0 is still the same object afterwards. We grow to 9 songs with 2 resizes, traverse a list of Integers with the same class, insert at the front with 9 recursive shifts, delete with 4, and find a separately made Song with a recursive equals search at depth 7. We shrink to fit with 8 references copied at depth 8, and finish at the limit: a million appends throw a real StackOverflowError inside a resize.
