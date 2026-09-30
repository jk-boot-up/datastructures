# Problem Statement — Static Array (Recursive)

## The situation

A weather station stores one whole-number temperature a day. The textbook operations on the week (sum, traversal, search, maximum, insertion, deletion, reversal) are to be written recursively, and the cost of the recursion measured.

## The obvious approach, and where it breaks

Writing recursion without a base case, or without progress towards it, recurses for ever; writing every operation recursively without watching the depth overflows the call stack on large arrays.

## What this project must show

- A `RecursiveStaticArray` of fixed capacity with the textbook operations written recursively, each with a stated base case: traverse, insertAt, deleteAt, linearSearch, binarySearch, findMax, sum, reverse.
- Every step counted, and the recursion depth of every operation measured.
- A real `StackOverflowError` on a million-element linear recursion, beside a 20-deep binary search.
- Time and stack space of every operation, compared with the iterative array and the linked list.
