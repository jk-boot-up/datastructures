# Problem Statement — Circular Linked List

## The situation

Players take turns round a board game; newcomers join and players leave while the turns keep going round.

## The obvious approach, and where it breaks

A straight list ends in null after the last player, so every round needs a special case to send the turn back to the first.

## What this project must show

- A `CircularLinkedList` of `Node`s (`data`, `next`) keeping only `last`, written by hand.
- The textbook operations: insertAtBeginning, insertAtEnd, insertAtPosition, deleteAtBeginning, deleteAtEnd, deleteByKey, search, display, count; turns and the counting-out game.
- Steps, comparisons and pointer changes counted; underflow reported.
- Time and space complexity of every operation, and a comparison with the singly and doubly linked lists.
