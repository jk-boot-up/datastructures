# Problem Statement — Singly Linked List

## The situation

The organisers of a treasure hunt keep a trail of clues, each leading to the next, and keep adding, removing and reordering clues as they plan the route.

## The obvious approach, and where it breaks

Kept in an array, every new clue near the front shifts every later clue one place: five shifts for six clues, and it grows with the hunt.

## What this project must show

- A `SinglyLinkedList` of `Node`s (`data`, `next`) with only `head` stored, written by hand.
- The textbook operations: insertAtBeginning, insertAtEnd, insertAtPosition, deleteAtBeginning, deleteAtEnd, deleteAtPosition, deleteByKey, search, get, display, count, reverse.
- Steps, comparisons and pointer changes counted; underflow reported.
- The wrong-order insertion reproduced, and the lost nodes counted.
- Time and space complexity of every operation, and a comparison with the doubly linked list and the dynamic array.
