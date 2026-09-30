# Session Guide — Singly Linked List (Recursive)

## By the end you can

- State the recursive definition of a list, and use it to write count and display.
- Write changing operations that return the new first node.
- Explain how displayReverse and reverse use the way back up the recursion.
- State the time and stack space of each operation.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: A list is a node followed by a smaller list | 7 min |
| 0:17 | Act 2: Forwards and backwards | 7 min |
| 0:24 | Act 3: Search and insertion, recursively | 7 min |
| 0:31 | Act 4: Deletion and reversal, recursively | 7 min |
| 0:38 | Act 5: The limit of recursion | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the team-of-finders analogy. Write count on the board from the definition. Run the demo; in act two, swap the two lines of display to get displayReverse. In act three, trace insertAtEnd and point out every `node.next =`. In act four, trace reverse on three nodes with arrows. In act five, show the StackOverflowError.

## Exercises

See [exercises.md](exercises.md).
