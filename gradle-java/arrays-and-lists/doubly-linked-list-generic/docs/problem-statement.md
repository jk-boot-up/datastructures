# Problem Statement — Doubly Linked List (Generic)

## The situation

The photo viewer keeps photos, names and sizes, each as a doubly linked list, and wants one class for all.

## The obvious approach, and where it breaks

Three copies of the doubly-linked-list code, or a list of `Object` with casts everywhere.

## What this project must show

- A `GenericDoublyLinkedList<T>` of `Node<T>`s with head and tail and the textbook operations, matching with equals.
- Used with String, Integer and a Photo record.
- Time and space complexity of every operation, compared with its neighbours.
