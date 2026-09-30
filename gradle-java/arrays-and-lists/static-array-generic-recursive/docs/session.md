# Session Guide — Static Array (Generic, Recursive)

## By the end you can

- Write a recursive method in a generic class, comparing elements with equals and compareTo.
- Identify the base cases of recursive linear search, binary search and findMax.
- State the time and stack space of each operation.
- Explain why generics change neither the time nor the depth.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: Recursion, for any element type | 7 min |
| 0:17 | Act 2: The same recursion, three types | 7 min |
| 0:24 | Act 3: compareTo, recursively | 7 min |
| 0:31 | Act 4: Insertion, deletion and reversal, recursively | 7 min |
| 0:38 | Act 5: The limit of recursion | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the queue analogy. Trace the recursive search for "Thu" on the board, one line per call. Run the demo; in act two, point out that the depth is 7 for every type. In act three, trace binary search on the sorted names. In act five, show the StackOverflowError and compare with the loop in static-array-generic. Finish with the four-column comparison table.

## Exercises

See [exercises.md](exercises.md).
