# Circular Linked List — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `circular-linked-list-explained.mp4` | the video, 1920×1080 |
| `circular-linked-list-explained.m4a` | audio only |
| `circular-linked-list-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Circular Linked List in Java, explained with players taking turns round a board game. We start with the players in a straight line, where the turn falls off the end after the last player and a special case sends it back to the first. Then we join the ends by hand, as a C textbook does: the last node points back at the first, and the list keeps a single pointer, last, so the first node is last.next and both ends are one step away. We count the real cost of every textbook operation: turns with no special case, insertAtBeginning and insertAtEnd in O(1), insertAtPosition, deleteAtBeginning in one pointer change, deleteAtEnd walking round to the node before last, and deleteByKey. Then a loop waiting for null walks a thousand steps and never finds one, and a do-while traversal stops where it started. We finish with the counting-out game, every third player out until one is left.
