# Static Array (Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `static-array-recursive-explained.mp4` | the video, 1920×1080 |
| `static-array-recursive-explained.m4a` | audio only |
| `static-array-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Static Array in C, written recursively, explained with a week of daily temperatures stored in int arr[10] with n = 7 elements. We trace sum(0) = 21 + sum(1) all the way down to the base case and see seven calls waiting on the call stack at once. We then write every textbook operation recursively and follow both the steps and the recursion depth: traversal and sum at depth 7, finding the maximum in 6 comparisons, linear search for 24 in 5 comparisons at depth 5, binary search in at most 3 comparisons and 3 calls, insertion and deletion shifting 5 and 7 elements recursively, and reversal in 3 swaps. We finish at the limit of recursion: binary search on a million elements goes only 20 calls deep, while linear search on a million elements overflows the call stack, and the child process running it is stopped by the operating system. The same operations written with loops are in the Static Array project.
