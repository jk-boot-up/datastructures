# Problem Statement — Static Array (Generic)

## The situation

A weather station stores the same week as temperatures, as day names and as readings, and needs the same array operations on each, in C.

## The obvious approach, and where it breaks

Three copies of the array code, one per element type, all with identical loops.

## What this project must show

- A `GenericArray` written by hand: a block of bytes, an element size and a count, with traverse, get, update, insert_at, delete_at, linear_search, binary_search, find_max, reverse and copy_with_capacity.
- Elements moved with `memcpy`; comparisons through `CompareFn` function pointers.
- The same array used with `int`, `char *` and a `Reading` struct.
- Time and space complexity of every operation, and a comparison with the int array and the generic dynamic array.
