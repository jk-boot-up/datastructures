# Dynamic Array (Generic) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `dynamic-array-generic-explained.mp4` | the video, 1920×1080 |
| `dynamic-array-generic-explained.m4a` | audio only |
| `dynamic-array-generic-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Generic Dynamic Array in C, explained with a playlist of Song structs, beside the same code holding titles and play counts. We see what the array holds: a block of bytes, an element size of 8, 4 or 20 bytes, a size and a capacity. We append until the block is full and watch a resize copy 4 songs, 80 bytes, into a block twice as big. We insert at the front with 9 shifts, delete from the middle with 4, and find a song by title and length with a comparison function, while a song of a different length is not found. We shrink to fit, and finish with the bill: 1,020 elements copied for 1,000 appends, and the capacity halving when a quarter full. The char * version is the Dynamic Array project.
