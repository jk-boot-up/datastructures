# Singly Linked List — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `singly-linked-list-explained.mp4` | the video, 1920×1080 |
| `singly-linked-list-explained.m4a` | audio only |
| `singly-linked-list-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Singly Linked List in Java, explained with a treasure hunt where each clue says where the next one is hidden. We start with the hunt in an array, where one new clue near the front shifts five others. Then we build the list by hand, the way a C textbook does: a Node with data and a next pointer, and a head. We run every textbook operation and count the real cost: insertAtEnd walking to the last node, count by traversal, search in 5 comparisons, insertAtPosition and insertAtBeginning changing 2 pointers and shifting nothing, deleteByKey, deleteAtBeginning and deleteAtEnd. Then we set the two pointers of an insertion in the wrong order and watch a clue point at itself and four clues vanish. We finish by reversing the list with three pointers, prev, current and next, and with the bill: 999 steps to reach position 999, where an array takes one.
