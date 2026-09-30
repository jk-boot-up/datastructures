# Session Guide — Dynamic Array (Recursive)

## By the end you can

- Turn each loop of a dynamic array into a recursion with a base case.
- Trace a recursive resize and draw its call stack.
- Explain why the time is unchanged but the extra space becomes O(n).
- Explain why an append can overflow the stack.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: A recursive copy | 7 min |
| 0:17 | Act 2: Growing | 7 min |
| 0:24 | Act 3: The middle, recursively | 7 min |
| 0:31 | Act 4: Shrinking, recursively | 7 min |
| 0:38 | Act 5: The limit of recursion | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start with the moving-helpers analogy. Put the loop `for (i = 0; i < size; i++) newArr[i] = arr[i];` beside `copy(i, newArr)` and match the parts. Run the demo; in act one, draw the stack of copy calls. In act two, point out that the counts equal the loop version's. In act five, show the StackOverflowError and ask where the recursion was hidden.

## Exercises

See [exercises.md](exercises.md).
