# Session Guide — Static Array (Generic)

## By the end you can

- Write a class with a type parameter and use it with several element types.
- Explain why `new T[n]` is not allowed and how the array is created instead.
- Use `equals` for equality and `compareTo` for order, and say why `==` is wrong for objects.
- State that the costs of every operation are unchanged from the int version.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: One class, any element type | 7 min |
| 0:17 | Act 2: Inside: an array of references | 7 min |
| 0:24 | Act 3: Searching with equals and compareTo | 7 min |
| 0:31 | Act 4: Insertion, deletion, overflow | 7 min |
| 0:38 | Act 5: One algorithm, many orders | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start by showing the int static array and asking how to store day names: the answer "copy the class" leads to generics. Run the demo; in act two, draw references as arrows to objects. In act three, show `==` failing on `new String("Fri")`. In act five, change `Reading.compareTo` to order by day and rerun findMax. Finish with the comparison table.

## Exercises

See [exercises.md](exercises.md).
