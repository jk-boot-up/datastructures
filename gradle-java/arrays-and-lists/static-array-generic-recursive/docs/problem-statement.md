# Problem Statement — Static Array (Generic, Recursive)

## The situation

A weather station keeps its week as day names, temperatures and readings, and wants the textbook array operations written once for every type and written recursively.

## The obvious approach, and where it breaks

A separate recursive class per element type, all identical; or recursion on objects with `==`, which misses equal elements, and linear recursion on large data, which overflows the call stack.

## What this project must show

- A `GenericRecursiveStaticArray<T extends Comparable<T>>` written by hand, with recursive traverse, insertAt, deleteAt, linearSearch (equals), binarySearch and findMax (compareTo), and reverse.
- Steps and recursion depth counted; the freed place cleared on deletion.
- A real StackOverflowError on a million-element linear recursion, beside a 20-deep binary search.
- Time and stack space of every operation, compared with the other three static-array projects.
