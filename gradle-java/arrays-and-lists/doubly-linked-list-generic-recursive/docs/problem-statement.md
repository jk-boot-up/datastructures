# Problem Statement — Doubly Linked List (Generic, Recursive)

## The situation

The photo viewer's album as one generic doubly linked list whose walks are recursive.

## The obvious approach, and where it breaks

Separate recursive classes per type; matching with ==; recursion for the ends; recursion over long lists.

## What this project must show

- A `GenericRecursiveDoublyLinkedList<T>` with recursive walks along next and prev, matching with equals, O(1) ends, and find-then-link in the middle.
- Recursion depth counted; a real StackOverflowError.
- Time and stack space of every operation, compared with the other three doubly lists.
