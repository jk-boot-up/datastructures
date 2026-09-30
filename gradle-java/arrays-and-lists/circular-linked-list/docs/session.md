# Session Guide — Circular Linked List

## By the end you can

- Draw a circular list with its single `last` pointer.
- Implement insertion at both ends in O(1), and explain why deletion at the end is O(n).
- Traverse with a do-while that stops back at the start.
- Simulate the counting-out game.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: Players in a straight line | 7 min |
| 0:17 | Act 2: Join the ends: last.next is the first node | 7 min |
| 0:24 | Act 3: Turns, insertion and deletion | 7 min |
| 0:31 | Act 4: Waiting for null | 7 min |
| 0:38 | Act 5: Counting out, and the bill | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the table analogy. Run the demo; in act two, show that last.next is the first node. In act three, perform insertAtEnd on the board as insert-at-beginning plus a move of last. In act four, discuss why while (current != null) never stops. Play the counting-out game with the class in act five.

## Exercises

See [exercises.md](exercises.md).
