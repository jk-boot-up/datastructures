# Session Guide — Dynamic Array (Generic)

## By the end you can

- Build a growable byte block with an element size.
- Explain why a resize copies bytes and invalidates pointers into the old block.
- Search with a comparison function.
- State that every cost equals the char * dynamic array's.

## Timetable

| Start | Topic | Time |
| --- | --- | --- |
| 0:00 | The everyday idea and the picture | 10 min |
| 0:10 | Act 1: One array type, any element type | 7 min |
| 0:17 | Act 2: Full? Double it, copying bytes | 7 min |
| 0:24 | Act 3: The middle, and comparison functions | 7 min |
| 0:31 | Act 4: Deleting, and giving memory back | 7 min |
| 0:38 | Act 5: The bill | 7 min |
| 0:45 | Try it yourself | 15 min |

## Walkthrough

Start from the char * dynamic array and ask how to hold whole songs. Introduce elem_size and memcpy. Run the demo; in act two, compute 4 x 20 = 80 bytes. In act three, write compare_song together. Finish by rewriting resize with realloc (exercise three).

## Exercises

See [exercises.md](exercises.md).
