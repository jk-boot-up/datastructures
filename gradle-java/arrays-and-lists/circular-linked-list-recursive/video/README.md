# Circular Linked List (Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `circular-linked-list-recursive-explained.mp4` | the video, 1920×1080 |
| `circular-linked-list-recursive-explained.m4a` | audio only |
| `circular-linked-list-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Circular Linked List in Java, written recursively, explained with players taking turns round a board game. We trace count(Ann) = 1 + count(Ben) round the table and see why the base case must be reaching last, the end of one lap: a recursion waiting for null would never stop. We display one lap recursively, insert at both ends with no recursion, and find nodes recursively before linking them in O(1): search, insertAtPosition, deleteByKey and deleteAtEnd, each with its depth. Then we solve the counting-out game without the list, by the recursive Josephus formula J(n) = (J(n - 1) + k) mod n, and finish at the limit: counting a million players recursively throws a real StackOverflowError.
