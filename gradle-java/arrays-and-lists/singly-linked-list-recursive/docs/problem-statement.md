# Problem Statement — Singly Linked List (Recursive)

## The situation

The treasure-hunt trail of clues, with every list operation written recursively from the definition of a list.

## The obvious approach, and where it breaks

Recursion written without a base case for the empty list crashes on `null`; recursion that forgets to store the returned node loses insertions; and recursion over a long list overflows the call stack.

## What this project must show

- A `RecursiveSinglyLinkedList` with recursive display, displayReverse, count, insertAtEnd, insertAtPosition, deleteAtEnd, deleteByKey, search, get and reverse.
- insertAtBeginning and deleteAtBeginning in O(1), without recursion.
- Steps, comparisons, pointer changes and recursion depth counted; a real StackOverflowError.
- Time and stack space of every operation, compared with the loop version.
