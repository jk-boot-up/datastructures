# Problem Statement — Dynamic Array (Generic)

## The situation

A music app keeps growing lists of songs, titles and play counts, in C, and wants one dynamic array for all of them.

## The obvious approach, and where it breaks

A separate copy of the dynamic-array code per element type.

## What this project must show

- A `GenericDynamicArray` written by hand: a byte block, `elem_size`, `size` and `capacity`, with append, insert_at, delete_at_end, delete_at, linear_search, get, update, traverse and shrink_to_fit.
- Doubling when full and halving when a quarter full; the demo counts copies from outside, from capacity changes.
- Comparison and print functions for the element types.
- Time and space complexity of every operation, compared with the char * version and the generic static array.
