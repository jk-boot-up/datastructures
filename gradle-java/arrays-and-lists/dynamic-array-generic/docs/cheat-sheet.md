# Dynamic Array (Generic) — Cheat Sheet

**A generic dynamic array is the dynamic array written once with a type parameter T: it stores references on an Object[] cast to T[], doubles when full and halves when a quarter full, copies references (not objects) when it resizes, and finds elements with equals; this is how Java's ArrayList<E> is written.**

![GenericDynamicArray<Song>: size 5, capacity 8; each place holds a reference to a Song](images/structure.png)

| | |
| --- | --- |
| **What it is** | A generic dynamic array stores references to any type T on an Object[] cast to T[], doubles when full, halves when a quarter full, and finds elements with equals: Java's ArrayList, built by hand. |
| **Everyday picture** | Moving house with a list of addresses, not the furniture |
| **Use it when** | A growing list of objects of one type: songs, orders, readings; Mostly appending at the end and reading by index; Whenever you would use `ArrayList<E>`; this project shows what it does inside |
| **Avoid it when** | Frequent insertion and deletion at the front: every element shifts; use a linked list or a deque; Millions of plain numbers: an `int[]` avoids a reference and an object per element |
| **Already in Java** | `java.util.ArrayList<E>`, written on an `Object[]` exactly like this |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space | Steps counted in the demo |
| --- | --- | --- | --- | --- |
| Traverse | Visit arr[0] to arr[size-1] | O(n) / O(n) / O(n) | O(1) | one read per element |
| Access `get(i)` / `update(i, x)` | Read or replace the reference in arr[i] | O(1) / O(1) / O(1) | O(1) | 1 step |
| Insert at end `append(x)` | Store x at arr[size]; double first when full | O(1) / O(1) amortised / O(n) when it resizes | O(1) amortised; O(n) for the new array during a resize | 1,020 references copied over 1,000 appends |
| Resize (inside append) | A new T[] of twice the capacity, references copied | O(n) / O(n) / O(n), but rare | O(n) | 4 references at the 5th song, 8 at the 9th |
| Insert `insertAt(pos, x)` | Shift arr[pos..size-1] right, store x | O(1) at the end / O(n) / O(n) at the front | O(1), plus a resize when full | 9 shifts at the front of 9 songs |
| Delete at end `deleteAtEnd()` | Clear arr[size-1]; halve when a quarter full | O(1) / O(1) amortised / O(n) when it shrinks | O(1) amortised | 0 shifts |
| Delete `deleteAt(pos)` | Shift arr[pos+1..size-1] left, clear the freed place | O(1) at the end / O(n) / O(n) at the front | O(1) | 4 shifts to delete index 5 of 10 |
| Linear search | arr[i].equals(key), for each i in turn | O(1) / O(n) / O(n) | O(1) | 7 comparisons to find Echoes |
| Shrink to fit | Resize to exactly size places | O(n) / O(n) / O(n) | O(n) | 8 references copied |

## Compared with related structures

The generic dynamic array against its neighbours (n elements):

| Property | Generic dynamic array | Dynamic array of String | Generic static array |
| --- | --- | --- | --- |
| Element types | any T | String only | any Comparable T |
| Access element i | O(1) | O(1) | O(1) |
| Append | O(1) amortised | O(1) amortised | overflow when full |
| Insert or delete at the front | O(n) shifts | O(n) shifts | O(n) shifts |
| A resize copies | references | references to strings | not possible: fixed capacity |
| Finds elements with | equals | equals | equals, and compareTo for binary search |
| Java's own | ArrayList<E> | ArrayList<String> | T[] |

## Watch out for

- **Writing `new T[capacity]`**: `(T[]) new Object[capacity]`, kept inside the class.
- **Searching with `==`**: Compare with `equals`.
- **Not clearing freed places**: Set every place that falls out of use to `null`.
- **Growing by a fixed amount**: Grow by a factor: double, or one and a half as `ArrayList` does.
