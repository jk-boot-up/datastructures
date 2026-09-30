# Problem Statement — Static Array (Generic)

## The situation

A weather station stores the same week as temperatures, as day names, and as readings, and needs the same array operations on each.

## The obvious approach, and where it breaks

Three copies of the array class, one per element type, all with identical code; or one array of `Object` that accepts anything, so a mistake is only found when the program crashes.

## What this project must show

- A `GenericStaticArray<T extends Comparable<T>>` written by hand, with the textbook operations: traverse, get, update, insertAt, deleteAt, linearSearch, binarySearch, findMax, reverse, copyWithCapacity.
- Equality by `equals`, order by `compareTo`, and the freed place cleared on deletion.
- The same class used with `Integer`, `String` and a `Reading` record.
- Time and space complexity of every operation, and a comparison with the int array and the generic dynamic array.
