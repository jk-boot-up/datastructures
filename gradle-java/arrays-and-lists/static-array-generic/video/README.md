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

> Generic Static Array in Java, explained with one week of temperatures stored three ways by the same class: as Integer temperatures, as String day names, and as Reading records of our own. We see why Java cannot create new T[10] and what the array really stores, references to objects, so copying the array copies references and not objects. We search with equals, which compares contents, and see == fail on two equal strings; we binary search numbers and day names with compareTo in 3 comparisons each. We insert and delete with the same shifts as the plain array, clearing the freed place so the object can be garbage-collected, and hit the same overflow. We finish with one findMax algorithm giving the hottest reading and the last day name alphabetically, because each type brings its own order. The int-only version is in the Static Array project.
