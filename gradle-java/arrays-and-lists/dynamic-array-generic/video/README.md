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

> Generic Dynamic Array in Java, explained with a music playlist of Song records, beside the same class holding titles and play counts. We see why the array inside is created as (T[]) new Object[4], then append until it is full and watch a resize copy 4 references, not 4 songs: the Song at index 0 is still the very same object. We grow to 9 songs with 2 resizes, insert an intro at the front with 9 shifts, delete from the middle with 4, and find a separately made Song with equals, because a record's equals compares its fields. We clear freed places so the garbage collector can reclaim deleted songs, shrink to fit, and finish with the bill: 1,020 references copied for 1,000 appends, and the capacity halving when a quarter full. This is how java.util.ArrayList is built.
