# Problem Statement — Singly Linked List (Generic, Recursive)

## The situation

The treasure hunt's clues, distances and places, in one generic linked-list class whose operations are recursive.

## The obvious approach, and where it breaks

Separate recursive classes per type; or recursion that matches with `==`, forgets to store returned nodes, or runs over long lists.

## What this project must show

- A `GenericRecursiveSinglyLinkedList<T>` of `Node<T>`s with recursive display, displayReverse, count, insertAtEnd, insertAtPosition, deleteAtEnd, deleteByKey, search, get and reverse.
- Matching with equals; recursion depth counted; a real StackOverflowError.
- Time and stack space of every operation, compared with the other three singly lists.
