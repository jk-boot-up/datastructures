# Problem Statement — Dynamic Array

## The situation

A music app keeps a playlist that the listener grows one song at a time, with no known limit. Songs are added at the end, read by position, and sometimes inserted or removed in the middle.

## The obvious approach, and where it breaks

A fixed array of four songs fails on the fifth. Making a new array one place bigger for every song works but copies everything already stored each time: 499,494 copies for 1,000 songs.

## What this project must show

- A `DynamicArray` of `const char *` song titles built by hand on a `malloc`ed array, with size and capacity kept separately, and the textbook operations: traverse, get, update, append, insert_at, delete_at_end, delete_at, linear_search, shrink_to_fit.
- Doubling when full; the demo counts copies from outside (every element present when the capacity changes was copied): 1,020 for 1,000 appends.
- Growing by one place as the comparison: 499,494 copies.
- insert_at and delete_at that count the elements shifted; spare places refused as out of bounds; the capacity halved when a quarter full.
- Time and space complexity of every operation, and a comparison with the static array and the linked list.
