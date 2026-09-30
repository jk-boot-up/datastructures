# Doubly Linked List — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `doubly-linked-list-explained.mp4` | the video, 1920×1080 |
| `doubly-linked-list-explained.m4a` | audio only |
| `doubly-linked-list-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Doubly Linked List in Java, explained with a photo viewer that has Previous and Next buttons. We start with one-way pointers and see Previous from the last photo walk three steps from the start. Then we build the list by hand, as a C textbook does: every node holds data, a prev pointer and a next pointer, and the list keeps head and tail. We display it forward and backward, and count every textbook operation: insertAtBeginning and insertAtEnd with no walking, insertAtPosition setting four pointers, deleteAtEnd through the tail in O(1) where a singly list needs O(n), and deleteByKey with no prev pointer to keep. Then we forget one of the four pointers an insertion needs and watch the forward and backward walks disagree. We finish by reversing the list by swapping every node's prev and next, and with the bill: an extra pointer in every node.
