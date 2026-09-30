# Session Guide — Static Array (Recursive)

## By the end you can

- Identify the base case and the recursive case of a recursive method.
- Trace a recursion, and explain what the call stack holds at each moment.
- State the time and the extra (stack) space of each recursive operation.
- Decide when recursion is safe (logarithmic depth) and when a loop is better.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: Thinking recursively | 7 min |
| 0:17 | Act 2: The call stack | 7 min |
| 0:24 | Act 3: Searching recursively | 7 min |
| 0:31 | Act 4: Insertion, deletion and reversal, recursively | 7 min |
| 0:38 | Act 5: The limit of recursion | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the queue analogy. Trace sum(0) on the board, writing each call beneath the last, then return the values upwards. Run the demo; in act two, draw the call stack for traverse. In act three, trace recursive binary search and note that the depth equals the number of comparisons. In act four, ask why shiftRight must start from the end. In act five, show the StackOverflowError and compare it with the loop in the static-array project.

## Exercises

See [exercises.md](exercises.md).
