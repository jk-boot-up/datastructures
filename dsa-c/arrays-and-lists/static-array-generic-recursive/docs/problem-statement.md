# Problem Statement — Static Array (Generic, Recursive)

## The situation

A weather station keeps its week as day names, temperatures and readings, and wants the textbook array operations written once for every type, in C, and written recursively.

## The obvious approach, and where it breaks

A separate recursive implementation per element type; or linear recursion on large data, which overflows the call stack and stops the program.

## What this project must show

- A `GenericArray` (bytes, elem_size, n) with recursive traverse, insert_at, delete_at, linear_search, binary_search, find_max and reverse, driven by comparison and print functions.
- Pure textbook code: the demo counts comparisons with counting comparison functions, from outside.
- A real stack overflow on a million-element linear recursion, in a child process, beside a 20-deep binary search.
- Time and stack space of every operation, compared with the other three static-array projects.
