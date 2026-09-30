# Session Guide — Dynamic Array

## By the end you can

- Explain the difference between size and capacity.
- Show why growing by one place costs about n squared over 2 copies and doubling about n.
- Count the shifts for an insertion or deletion in the middle.
- Say what amortised one step means, and when the rare slow append matters.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: A playlist in a fixed array | 7 min |
| 0:17 | Act 2: Size and capacity | 7 min |
| 0:24 | Act 3: Full? Double it | 7 min |
| 0:31 | Act 4: The middle, and the spare places | 7 min |
| 0:38 | Act 5: The bill | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Begin with the house-moving analogy. Run the demo and pause after act one: ask the class to guess how many copies 1,000 songs cost when growing by one place, then reveal 499,494. In act three, add up 4 + 8 + 16 + ... + 512 on the board and compare with 1,020. In act four, act out the insert with people standing in a row, everyone stepping right. Close with act five and the memory trade-off, and compare Java's choice of 1.5.

## Exercises

See [exercises.md](exercises.md).
