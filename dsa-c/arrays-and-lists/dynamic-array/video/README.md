# Dynamic Array — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `dynamic-array-explained.mp4` | the video, 1920×1080 |
| `dynamic-array-explained.m4a` | audio only |
| `dynamic-array-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Dynamic Array in C, explained with a music playlist that grows as songs are added. We start with a fixed array of four songs and see a fifth song fail, then count what growing one place at a time would cost: 499,494 copies for 1,000 songs. Then we build the fix by hand: an array with spare capacity, and when it is full, a new array twice as big with every song copied across. We count the real steps, 1,020 copies for 1,000 songs, about one per song, then insert_at and delete_at in the middle, find that a spare place is not an element, and see the capacity halve when deletions leave it a quarter full. We finish with the bill: up to half the places unused, and one slow append now and then.
