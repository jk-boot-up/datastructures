# Session Guide — Static Array (Generic)

## By the end you can

- Store any element type in a byte block with an element size.
- Compute an element's address as data + i x elem_size.
- Write comparison functions, including one for an array of pointers.
- Explain what is lost: type checking.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: One array type, any element type | 7 min |
| 0:17 | Act 2: Inside: bytes and an element size | 7 min |
| 0:24 | Act 3: Searching with comparison functions | 7 min |
| 0:31 | Act 4: Insertion, deletion, overflow | 7 min |
| 0:38 | Act 5: One algorithm, many orders | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start from the int static array and ask how to store day names without copying the code. Introduce `void *`, `sizeof` and `memcpy`. Run the demo; in act two, compute data + 36 on the board. In act three, write compare_name together and show `==` failing. Finish by calling qsort and bsearch on the same block.

## Exercises

See [exercises.md](exercises.md).
