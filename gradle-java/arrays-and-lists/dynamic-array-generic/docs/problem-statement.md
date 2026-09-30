# Problem Statement — Dynamic Array (Generic)

## The situation

A music app keeps growing lists of songs, titles and play counts, and wants one dynamic array class for all of them.

## The obvious approach, and where it breaks

A separate copy of the dynamic-array code per element type, or one class of `Object` that accepts anything and needs a cast on every read.

## What this project must show

- A `GenericDynamicArray<T>` written by hand on an `Object[]` cast to `T[]`, with append, insertAt, deleteAtEnd, deleteAt, linearSearch, get, update, traverse and shrinkToFit.
- Doubling when full and halving when a quarter full, with every reference copied counted.
- equals for search, and freed places set to null.
- Time and space complexity of every operation, and a comparison with the String-only and static versions.
