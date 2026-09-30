# Problem Statement — Static Array

## The situation

A weather station stores one whole-number temperature a day and must answer: the temperature on a given day, corrections, whether a value occurs, inserting late readings, deleting wrong ones, and what happens when storage fills up.

## The obvious approach, and where it breaks

Seven separate variables, one per day: every question must be written out for each day, a loop is impossible without an index, and a second week means seven more variables.

## What this project must show

- A `StaticArray` of fixed capacity holding n elements, written by hand with the textbook operations: traverse, get, update, insert_at, delete_at, linear_search, binary_search, find_max, sum, reverse.
- Every operation iterative, with O(1) extra space, and written as pure textbook code.
- Overflow on insertion into a full array and underflow on deletion from an empty one.
- Time and space complexity of every operation, and a comparison with the dynamic array and the linked list.
