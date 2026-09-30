# Static Array (Generic, Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `static-array-generic-recursive-explained.mp4` | the video, 1920×1080 |
| `static-array-generic-recursive-explained.m4a` | audio only |
| `static-array-generic-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Generic Recursive Static Array in C, explained with one week held as day names, ints and Reading structs by the same code. We trace a recursive linear search for "Thu" call by call, each call asking the comparison function and passing the rest of the array to the next call, until the match at depth 4. We traverse all three element types at depth 7, binary search the sorted day names in 3 comparisons and 3 calls, and find the hottest reading with a comparison on the way back up the call stack. We insert, delete and reverse recursively, moving whole elements with memcpy, and finish at the limit: binary search on a million ints goes only 20 calls deep, while linear search overflows the call stack and the child process running it is stopped.
