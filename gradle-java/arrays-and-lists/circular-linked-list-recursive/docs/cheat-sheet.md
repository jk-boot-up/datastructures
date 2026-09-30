# Circular Linked List (Recursive) — Cheat Sheet

**A recursion over a circular linked list cannot stop at null, because there is none: its base case is reaching last, the end of one lap; work at both ends needs no recursion, finding a node is recursive, and the counting-out winner has a recursive formula of its own.**

![count(Ann) waits for count(Ben) ... count(Dan) is 1: Dan is last, the lap is over](images/structure.png)

| | |
| --- | --- |
| **What it is** | A recursive circular linked list stops each recursion at last, the end of one lap, because a circle has no null; it works at both ends without recursion and finds the counting-out winner by the formula J(n) = (J(n - 1) + k) mod n. |
| **Everyday picture** | Asking round the table until the question comes back to the dealer |
| **Use it when** | Learning how to choose a base case when there is no null; Short circles, such as the players at one table; The counting-out winner for any n, by the O(n) recursive formula |
| **Avoid it when** | Long circles: one frame per node overflows the call stack. Use the do-while loops of `circular-linked-list` |
| **Already in Java** | Nothing: no circular list in `java.util` |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | Steps counted in the demo |
| --- | --- | --- | --- | --- | --- |
| Display / count | This node, then the rest of the lap; stop at last | O(n) / O(n) / O(n) | O(n) | O(1) | depth 4 for 4 players |
| Insert at beginning / end, delete at beginning | Through last and last.next; no recursion | O(1) / O(1) / O(1) | O(1) | O(1) | depth 0 |
| Insert at position | Find the node before recursively, then 2 pointers | O(1) at 0 / O(n) / O(n) | O(pos) | O(1) | depth 2 |
| Delete at end | Find the node before last recursively; it becomes last | O(n) / O(n) / O(n) | O(n) | O(1) | depth 4 |
| Delete by key / search | Recurse round one lap; stop at a match or at last | O(1) / O(n) / O(n) | O(n) | O(1) | 5 comparisons, depth 5 |
| Counting-out winner (formula) | J(n) = (J(n - 1) + k) mod n, J(1) = 0 | O(n) / O(n) / O(n) | O(n) | O(1) as a loop | depth 5 for 5 players, 41 for 41 |

## Compared with related structures

The recursive circular list against the loop version and the recursive singly list (n nodes):

| Property | Recursive circular list | Circular list (loops) | Recursive singly list |
| --- | --- | --- | --- |
| Base case of a walk | reaching last | do-while back at the start | null |
| Insert at both ends | O(1), no recursion | O(1) | O(1) at the beginning, O(n) at the end |
| Delete at the end | O(n) time and stack | O(n) time, O(1) space | O(n) time and stack |
| Search, display, count | O(n) time and stack | O(n) time, O(1) space | O(n) time and stack |
| A million nodes | StackOverflowError | fine | StackOverflowError |

## Watch out for

- **Base case `node == null`**: Stop at `node == last`, the end of the lap.
- **Starting the lap at last**: Start at `last.next`, the first node.
- **Recursing to reach the end**: Use `last` and `last.next` for work at the ends.
- **Recursing round long circles**: Use do-while loops.
