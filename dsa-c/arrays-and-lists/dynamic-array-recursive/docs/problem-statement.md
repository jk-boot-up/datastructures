# Problem Statement — Dynamic Array (Recursive)

## The situation

A music app's playlist grows one song at a time; the textbook dynamic-array operations are to be written recursively, and the cost of that shown.

## The obvious approach, and where it breaks

Turning every loop into a recursion without noticing that the copy inside a resize is one of them: the hidden recursion grows with the array and eventually overflows the call stack.

## What this project must show

- A `RecursiveDynamicArray` of strings with recursive traverse, copy (inside resize), shift_right, shift_left and linear_search, each with a base case.
- Doubling when full and halving when a quarter full; the demo works the copies and the recursion depth out from what the operations did.
- A real stack overflow inside an append on a million songs.
- Time and stack space of every operation, compared with the loop version.
