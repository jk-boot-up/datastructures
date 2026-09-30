# Problem Statement — Dynamic Array (Generic, Recursive)

## The situation

A music app's playlist of Song records, written as a generic dynamic array with every loop replaced by a recursion.

## The obvious approach, and where it breaks

Combining generics and recursion without noticing that the copy inside a resize is now a linear recursion: it grows with the list and eventually overflows.

## What this project must show

- A `GenericRecursiveDynamicArray<T>` on an `Object[]` cast to `T[]`, with recursive traverse, copy, shiftRight, shiftLeft and linearSearch (equals).
- Doubling when full and halving when a quarter full; references counted; recursion depth measured.
- A real StackOverflowError inside an append.
- Time and stack space of every operation, compared with the other three dynamic arrays.
