# Problem Statement — Doubly Linked List (Recursive)

## The situation

The photo viewer's album as a doubly linked list whose walks are recursive, in both directions.

## The obvious approach, and where it breaks

Recursing for work at the ends, which the head and tail pointers do in O(1); or recursing over long lists, which overflows.

## What this project must show

- A `RecursiveDoublyLinkedList` with recursive displayForward, displayBackward, count, search, find, nodeAt and reverse.
- insertAtBeginning, insertAtEnd, deleteAtBeginning and deleteAtEnd in O(1) with no recursion; insertAtPosition and deleteByKey that find recursively and link in O(1).
- Recursion depth counted; underflow reported; a real StackOverflowError.
- Time and stack space of every operation, compared with the loop version.
