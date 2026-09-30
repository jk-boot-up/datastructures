# Singly Linked List (Recursive) — Teaching Video

A narrated, slide-based video built by `./build_video.sh` with the course's videokit library.

| File | What it is |
| --- | --- |
| `singly-linked-list-recursive-explained.mp4` | the video, 1920×1080 |
| `singly-linked-list-recursive-explained.m4a` | audio only |
| `singly-linked-list-recursive-explained.srt` | subtitles |
| `poster.png` | the opening frame |

**Narration:** Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice). Scenes are generated from `../ds.toml` into `scenes.py`.

Suggested description:

> Singly Linked List in Java, written recursively, explained with a treasure hunt of clues. We start from the recursive definition, a list is empty or a node followed by a smaller list, and trace count(the oak tree) = 1 + count(rest) down to count(null) = 0, six calls deep. We display the hunt forwards and, by printing after the recursive call instead of before it, backwards. We search, get, insert at the end and at a position recursively, delete by key by returning node.next in place of the matching node, delete at the end, and reverse the list by reversing the rest and hanging each node on its end. We finish at the limit: counting a million nodes recursively throws a real StackOverflowError, where the loop version needs O(1) extra space.
