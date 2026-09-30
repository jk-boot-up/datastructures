# Session Guide — Dynamic Array (Generic)

## By the end you can

- Write a generic class on an `Object[]` cast to `T[]`.
- Explain that a resize copies references, not objects.
- Explain why freed places must be set to null.
- State that every cost is unchanged from the String-only dynamic array.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: One class, any element type | 7 min |
| 0:17 | Act 2: Full? Double it, copying references | 7 min |
| 0:24 | Act 3: The middle, and equals | 7 min |
| 0:31 | Act 4: Deleting clears references | 7 min |
| 0:38 | Act 5: The bill | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Show the String-only DynamicArray and ask how to store songs. Replace String with T and hit `new T[n]`; introduce the Object[] cast. Run the demo; in act two, draw references as arrows and show they are copied. In act three, show equals finding a separately made Song. In act four, discuss the memory leak a leftover reference causes. Finish by opening the source of java.util.ArrayList and finding elementData.

## Exercises

See [exercises.md](exercises.md).
