# Problem Statement — Singly Linked List (Generic)

## The situation

The treasure-hunt organisers keep the trail as place names, as distances and as clue records, and want one linked-list class for all three.

## The obvious approach, and where it breaks

Three copies of the linked-list code, one per type; or one list of `Object` with a cast on every read and no compiler check.

## What this project must show

- A `GenericSinglyLinkedList<T>` of `Node<T>`s, written by hand, with the textbook operations: insertAtBeginning, insertAtEnd, insertAtPosition, deleteAtBeginning, deleteAtEnd, deleteAtPosition, deleteByKey, search, get, display, count, reverse.
- Matching with equals; the same class used with String, Integer and a Clue record.
- Time and space complexity of every operation, and a comparison with the String-only list and the generic dynamic array.
