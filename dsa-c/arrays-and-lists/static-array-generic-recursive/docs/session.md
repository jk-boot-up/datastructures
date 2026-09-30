# Session Guide — Static Array (Generic, Recursive)

## By the end you can

- Write recursive functions over a generic byte-block array, driven by comparison functions.
- Identify the base cases of recursive linear search, binary search and find_max.
- State the time and stack space of each operation.
- Explain why neither void * nor recursion changes the time.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: Recursion, for any element type | 7 min |
| 0:17 | Act 2: The same recursion, three types | 7 min |
| 0:24 | Act 3: Comparison functions, recursively | 7 min |
| 0:31 | Act 4: Insertion, deletion and reversal, recursively | 7 min |
| 0:38 | Act 5: The limit of recursion | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the queue-of-helpers analogy. Trace the recursive search for "Thu" on the board. Run the demo; in act two, point out that the depth is 7 for every type. In act three, pass the two comparison functions to find_max. In act five, show the child process stopped by SIGSEGV. Finish with the four-column table.

## Exercises

See [exercises.md](exercises.md).
