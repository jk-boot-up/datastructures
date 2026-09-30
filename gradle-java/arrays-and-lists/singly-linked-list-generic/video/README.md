# Singly Linked List (Generic) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `singly-linked-list-generic-explained.mp4` | the video, 1920×1080 |
| `singly-linked-list-generic-explained.m4a` | audio only |
| `singly-linked-list-generic-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Generic Singly Linked List in Java, explained with a treasure hunt held three ways by one class: as String place names, as Integer distances in metres, and as Clue records of our own. We see what Node<T> holds: a reference to the data, not a copy, and a next pointer. We search for a separately made Clue and find it because a record's equals compares its fields, while == says it is a different object. We insert at a position and delete by key with the same pointer changes as the plain list, delete at both ends, and see the garbage collector free nodes nothing refers to. We finish by reversing a list of Integers and a list of Strings with the very same code. The String-only version is the Singly Linked List project.
