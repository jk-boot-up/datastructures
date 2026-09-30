# Static Array — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `static-array-explained.mp4` | the video, 1920×1080 |
| `static-array-explained.m4a` | audio only |
| `static-array-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Static Array in Java, explained with a week of daily temperatures stored in int arr[10] with n = 7 elements. We start with seven separate variables and see why every question needs code written out by hand, then store the week in one array and compute the address of arr[3] with base + i x size. We count the real cost of each textbook operation: access and update in one step, linear search in n comparisons, binary search on a sorted array in 3, insertion and deletion shifting 5 and 7 elements, and an overflow when the array is full. We finish with binary search on a million elements in 20 comparisons and the bill: a capacity that never changes. Every operation here is iterative, using O(1) extra space.
