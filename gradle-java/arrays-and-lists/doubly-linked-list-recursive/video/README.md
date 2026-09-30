# Doubly Linked List (Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `doubly-linked-list-recursive-explained.mp4` | the video, 1920×1080 |
| `doubly-linked-list-recursive-explained.m4a` | audio only |
| `doubly-linked-list-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Doubly Linked List in Java, written recursively, explained with a photo viewer. We trace a recursive display backward from the tail, following prev pointers five calls deep, and see display forward use the same recursion on next. We insert and delete at both ends with no recursion at all, then find nodes recursively and link or unlink them in O(1): insertAtPosition finds the node before it two calls deep and sets four pointers, deleteByKey finds cake three calls deep and changes two. We reverse the list by swapping each node's prev and next and recursing on the old next. We finish at the limit: counting a million photos recursively throws a real StackOverflowError.
