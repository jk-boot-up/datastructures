# Session Guide — Doubly Linked List (Recursive)

## By the end you can

- Write one recursion that walks along next or along prev.
- Separate finding (recursive) from linking (O(1)).
- Reverse a doubly linked list recursively.
- State the time and stack space of each operation.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: The same recursion in both directions | 7 min |
| 0:17 | Act 2: Both ends need no recursion | 7 min |
| 0:24 | Act 3: Find recursively, link in O(1) | 7 min |
| 0:31 | Act 4: Reversal, recursively | 7 min |
| 0:38 | Act 5: The limit of recursion | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Recall the doubly linked list. Trace displayBackward from the tail. Run the demo; in act two, ask why no recursion is needed at the ends. In act three, split each operation into its finding and its linking. In act four, trace reverse on three nodes. Finish with the StackOverflowError.

## Exercises

See [exercises.md](exercises.md).
