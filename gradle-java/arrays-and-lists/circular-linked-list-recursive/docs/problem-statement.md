# Problem Statement — Circular Linked List (Recursive)

## The situation

The board game's circle of players, with every walk written recursively, and the counting-out winner found by formula.

## The obvious approach, and where it breaks

A recursion that waits for null on a circle, which never stops; or recursing to reach the ends, which last reaches in one step.

## What this project must show

- A `RecursiveCircularLinkedList` keeping only `last`, with recursive display, count, search, nodeAt, nodeBeforeLast and beforeKey, each stopping at `last`.
- insertAtBeginning, insertAtEnd and deleteAtBeginning in O(1) with no recursion.
- The recursive Josephus formula, `winnerPosition(n, k)`.
- Recursion depth counted; a real StackOverflowError.
