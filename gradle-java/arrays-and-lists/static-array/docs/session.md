# Session Guide — Static Array

## By the end you can

- Explain why access by index is O(1), using base address + i x size.
- Implement insertion and deletion with shifts in the correct direction.
- Implement linear and binary search, and state their best, average and worst cases.
- Compare the static array with the dynamic array and the linked list.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: Seven separate variables | 7 min |
| 0:17 | Act 2: One array: int arr[10], n = 7 | 7 min |
| 0:24 | Act 3: Access, update, search | 7 min |
| 0:31 | Act 4: Insertion, deletion, overflow | 7 min |
| 0:38 | Act 5: The bill | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the lockers analogy and draw ten boxes with indexes. Run the demo; in act two, compute the address of arr[3] on the board. In act three, trace linear search and then binary search on the sorted week, counting comparisons. In act four, perform insertAt(2, 18) on paper, shifting from the end, and ask what goes wrong when shifting from the front. Finish with the complexity table and the comparison table, and point to the static-array-recursive project for the same operations written recursively.

## Exercises

See [exercises.md](exercises.md).
