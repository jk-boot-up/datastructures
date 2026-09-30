# Problem Statement — Dynamic Array (Recursive)

## The situation

A music app's playlist grows one song at a time; the textbook dynamic-array operations are to be written recursively, and the cost of that measured.

## The obvious approach, and where it breaks

Turning every loop into a recursion without noticing that the copy inside a resize is one of them: the hidden recursion grows with the array and eventually overflows the call stack.

## What this project must show

- A `RecursiveDynamicArray` of strings with recursive traverse, copy (inside resize), shiftRight, shiftLeft and linearSearch, each with a base case.
- Doubling when full and halving when a quarter full, with every copy and the recursion depth counted.
- A real StackOverflowError inside an append on a million songs.
- Time and stack space of every operation, compared with the loop version.
