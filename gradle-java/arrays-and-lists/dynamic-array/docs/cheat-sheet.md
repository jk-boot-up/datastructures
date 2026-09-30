# Dynamic Array — Cheat Sheet

**A dynamic array keeps a fixed array inside with some spare places; when it is full it shifts everything into a new array twice as big, so adding at the end stays cheap on average.**

![size 5, capacity 8: five songs in use and three spare places, dashed](images/structure.png)

| | |
| --- | --- |
| **What it is** | A dynamic array is an ordinary array with spare places, which it swaps for one twice as big and copies across whenever it runs out of room, so adding at the end costs one step on average. |
| **Everyday picture** | Moving house to a place twice the size, only when you run out of room |
| **Use it when** | You do not know in advance how many values there will be; You mostly add at the end and read by position; You want one-step reads by index, like an array, without fixing the length |
| **Avoid it when** | You often insert or remove at the front or in the middle: every later value moves (a linked list or a deque suits that better); Every single append must be fast: the rare resize copies everything at once; Memory is very tight: up to half the places can sit unused |
| **Already in Java** | `java.util.ArrayList` (starts at 10 places and grows by half each time) |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space | Steps counted in the demo |
| --- | --- | --- | --- | --- |
| Traverse | Visit arr[0] to arr[size-1] once | O(n) / O(n) / O(n) | O(1) | one read per element |
| Access `get(i)` | Return arr[i], by address calculation | O(1) / O(1) / O(1) | O(1) | 1 step |
| Update `update(i, x)` | Replace arr[i] | O(1) / O(1) / O(1) | O(1) | 1 step |
| Insert at end `append(x)` | Store x at arr[size]; resize to double first when full | O(1) / O(1) amortised / O(n) when it resizes | O(1) amortised; O(n) for the new array during a resize | 1 write usually; 1,020 copies over 1,000 appends |
| Resize (inside append) | Allocate an array of twice the capacity and copy every element | O(n) / O(n) / O(n), but rare | O(n) | 4 copies at the 5th song, 8 at the 9th, 512 at the 513th |
| Insert `insertAt(pos, x)` | Shift arr[pos..size-1] right, store x | O(1) at the end / O(n) / O(n) at the front | O(1), plus a resize when full | 9 shifts to insert at the front of 9 songs |
| Delete at end `deleteAtEnd()` | Clear arr[size-1]; halve the capacity when a quarter full | O(1) / O(1) amortised / O(n) when it shrinks | O(1) amortised | 0 shifts; 1 resize going from 513 songs down to 256 |
| Delete `deleteAt(pos)` | Shift arr[pos+1..size-1] left | O(1) at the end / O(n) / O(n) at the front | O(1) | 4 shifts to delete index 5 of 10 |
| Linear search | Compare each element with the key in turn | O(1) / O(n) / O(n) | O(1) | one comparison per element looked at |
| Shrink to fit | Resize to exactly size places | O(n) / O(n) / O(n) | O(n) | 9 copies for 9 songs |

## Compared with related structures

How a dynamic array compares with the structures it is usually weighed against (n elements):

| Operation | Dynamic array | Static array | Singly linked list |
| --- | --- | --- | --- |
| Access element i | O(1) | O(1) | O(n) |
| Insert at the end | O(1) amortised, O(n) on a resize | O(1), overflow when full | O(1) with a tail pointer |
| Insert or delete at the front | O(n) shifts | O(n) shifts | O(1) |
| Search (unsorted) | O(n) | O(n) | O(n) |
| Size | grows and shrinks by copying | fixed at creation | grows one node at a time |
| Extra memory | up to half the places spare after a resize | capacity - n places | one next pointer per node |
| Memory layout | consecutive | consecutive | scattered nodes |

## Watch out for

- **Growing by one place at a time**: Grow by a factor (double, or one and a half as Java does), never by a fixed amount.
- **Treating capacity as size**: Only indexes 0 to size - 1 hold elements; check indexes against the size.
- **Forgetting to clear a removed place**: Set the place that falls out of use to `null`, as `deleteAt` and `deleteAtEnd` do.
- **Deleting from the front in a loop**: Delete from the end, or use a structure made for the front, such as a deque.
