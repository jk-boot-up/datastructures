# Static Array (Generic) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `static-array-generic-explained.mp4` | the video, 1920×1080 |
| `static-array-generic-explained.m4a` | audio only |
| `static-array-generic-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Generic Static Array in C, explained with one week of temperatures stored three ways by the same code: as ints, as day names (char pointers), and as Reading structs of our own. We see what the array really is, a block of bytes and an element size, and compute the address of element 3 by hand: data + 3 x 12. We search with comparison functions, the way qsort and bsearch do, and see why strcmp finds a day name typed by the user while == compares addresses and fails. We insert and delete with shifts of whole elements, hit the overflow, and finish with one find_max giving the hottest reading or the last day name, depending only on the comparison function passed in. The int-only version is the Static Array project.
