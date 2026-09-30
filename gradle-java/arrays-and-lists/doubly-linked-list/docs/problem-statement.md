# Problem Statement — Doubly Linked List

## The situation

A photo viewer needs Previous as well as Next, adds photos at the end, deletes the photo on screen, and sometimes shows the album in reverse.

## The obvious approach, and where it breaks

With next pointers only, Previous and deleting the photo on screen both walk from the start to find the node before.

## What this project must show

- A `DoublyLinkedList` of `Node`s (`data`, `prev`, `next`) with `head` and `tail`, written by hand.
- The textbook operations: insertAtBeginning, insertAtEnd, insertAtPosition, deleteAtBeginning, deleteAtEnd, deleteAtPosition, deleteByKey, deleteNode, search, get, displayForward, displayBackward, count, reverse.
- Steps, comparisons and pointer changes counted; underflow reported.
- The forgotten-pointer insertion reproduced.
- Time and space complexity of every operation, and a comparison with the singly linked list and the dynamic array.
