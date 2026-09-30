# Static Array — Cheat Sheet

**A static array stores its elements in consecutive memory locations, so arr[i] is found by one address calculation in O(1); its capacity is fixed when it is created, so insertion and deletion shift elements and a full array overflows.**

![int arr[10] with n = 7: elements at indexes 0 to 6, three free places](images/structure.png)

| | |
| --- | --- |
| **What it is** | A static array stores elements of one type in consecutive memory locations, so arr[i] is found in O(1) by base address + i x element size, while its capacity is fixed, insertion and deletion shift elements in O(n), and a full array overflows. |
| **Everyday picture** | A row of numbered lockers |
| **Use it when** | The number of elements is known in advance and does not change much; You access elements by index, and need that access to be O(1); Memory must be compact: an array stores nothing but the elements; The data is sorted and searched often: binary search needs O(1) access to the middle |
| **Avoid it when** | The number of elements grows without a known limit: use a dynamic array; You insert or delete at the front or middle often: every later element shifts; a linked list does it in O(1) once positioned; Elements are looked up by a key rather than an index: use a hash table |
| **Already in Java** | Java's own arrays (`int[]`), with `java.util.Arrays` for `fill`, `copyOf`, `sort` and `binarySearch` |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space | Steps counted in the demo |
| --- | --- | --- | --- | --- |
| Traverse | Visit arr[0] to arr[n-1] once | O(n) / O(n) / O(n) | O(1) | 7 reads for the week |
| Access `get(i)` | Return arr[i], by address calculation | O(1) / O(1) / O(1) | O(1) | 1 step, for any i and any n |
| Update `update(i, x)` | Replace arr[i] | O(1) / O(1) / O(1) | O(1) | 1 step |
| Insert `insertAt(pos, x)` | Shift arr[pos..n-1] right, store x | O(1) at the end / O(n) / O(n) at the front | O(1) | 5 shifts to insert at index 2 of 7 |
| Delete `deleteAt(pos)` | Shift arr[pos+1..n-1] left | O(1) at the end / O(n) / O(n) at the front | O(1) | 7 shifts to delete index 0 of 8 |
| Linear search | Compare with each element in turn | O(1) / O(n) / O(n) | O(1) | 5 comparisons to find 24; 7 to learn 30 is absent |
| Binary search (sorted) | Compare with the middle, discard half, repeat | O(1) / O(log n) / O(log n) | O(1) | 3 comparisons on the sorted week; 20 on 1,000,000 |
| Find maximum | Compare each element with the largest so far | O(n) / O(n) / O(n) | O(1) | n - 1 comparisons |
| Reverse | Swap arr[i] and arr[n-1-i] moving inward | O(n) / O(n) / O(n) | O(1) | n / 2 swaps |
| Grow | Impossible in place: allocate a bigger array and copy | O(n) / O(n) / O(n) | O(n) | 7 copies to grow the week to 31 places |

## Compared with related structures

How a static array compares with the structures it is usually weighed against (n elements):

| Operation | Static array | Dynamic array | Singly linked list |
| --- | --- | --- | --- |
| Access element i | O(1) | O(1) | O(n) |
| Insert or delete at the front | O(n) shifts | O(n) shifts | O(1) |
| Insert or delete at the end | O(1), until full | O(1) amortised | O(n), or O(1) with a tail pointer |
| Search (unsorted) | O(n) | O(n) | O(n) |
| Search (sorted) | O(log n), binary search | O(log n), binary search | O(n): no middle to jump to |
| Size | fixed at creation | grows by copying | grows one node at a time |
| Extra memory per element | none | spare capacity | one next pointer per node |
| Memory layout | consecutive | consecutive | scattered nodes |

## Watch out for

- **Counting indexes from 1**: The first index is 0 and the last is n - 1.
- **Looping with `i <= n`**: Loop with `i < n`.
- **Shifting in the wrong direction on insertion**: Insertion shifts from the end down to `pos`; deletion shifts from `pos` up to the end.
- **Binary search on unsorted data**: Sort first, or use linear search.
- **`mid = (low + high) / 2`**: `mid = low + (high - low) / 2`.
