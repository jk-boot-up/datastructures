# Dynamic Array (Recursive) — Cheat Sheet

**The recursive dynamic array doubles when full and halves when a quarter full, like any dynamic array, but copies, shifts, traverses and searches recursively: the time is unchanged, and the extra space is one call-stack frame per element, so a large resize can overflow the stack.**

![A recursive resize from 4 to 8: copy_from(0) waits for copy_from(1) ... down to the base case copy_from(4)](images/structure.png)

| | |
| --- | --- |
| **What it is** | The recursive dynamic array grows and shrinks like any dynamic array, but copies, shifts, traverses and searches by calling itself on the rest, so each operation keeps its time and gains O(n) stack, and a large resize overflows. |
| **Everyday picture** | Moving house one box at a time, each helper waiting for the next |
| **Use it when** | Learning how each loop of a dynamic array becomes a recursion, with its base case; Small arrays, where the depth stays far below the call-stack limit |
| **Avoid it when** | Any array that may grow large: a recursive resize of a few tens of thousands of elements can overflow the call stack. Use the loops of `dynamic-array`; Performance-sensitive code: each call costs a frame, a jump and a return |
| **Already in C** | Nothing: C's `ArrayList` grows with a loop (`Arrays.copyOf`) |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | In the example |
| --- | --- | --- | --- | --- | --- |
| Traverse | Visit arr[i], then traverse from i + 1 | O(n) / O(n) / O(n) | O(n) | O(1) | depth 9 for 9 songs |
| Access `get(i)` / update | One address calculation (not recursive) | O(1) / O(1) / O(1) | O(1) | O(1) | 1 step |
| Insert at end `append(x)` | Write arr[size]; recursive resize first when full | O(1) / O(1) amortised / O(n) when it resizes | O(1), or O(n) during a resize | O(1), plus the new array | 4 copies at depth 4; 8 at depth 8 |
| Resize (inside append) | copy_from(i): new_arr[i] = arr[i], then copy_from(i + 1) | O(n) / O(n) / O(n) | O(n) | O(1), plus the new array | 12 copies over 9 appends |
| Insert `insert_at(pos, x)` | shift_right from the last element down to pos | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 9 shifts at depth 9 |
| Delete `delete_at(pos)` | shift_left from pos, clear the freed place | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 4 shifts at depth 4 |
| Delete at end `delete_at_end()` | Clear arr[size-1]; halve (recursive copy) when a quarter full | O(1) / O(1) amortised / O(n) when it shrinks | O(1), or O(n) during a shrink | O(1) | 8 copies at depth 8 from 32 to 16 |
| Linear search | arr[i].equals(key)? If not, search from i + 1 | O(1) / O(n) / O(n) | O(n) | O(1) | 8 comparisons at depth 8 |
| Shrink to fit | Resize to exactly size places | O(n) / O(n) / O(n) | O(n) | O(1), plus the new array | 9 copies at depth 9 |

## Compared with related structures

The recursive dynamic array against the loop version and the related structures (n elements):

| Operation | Recursive dynamic array | Dynamic array (loops) | Recursive static array |
| --- | --- | --- | --- |
| Access element i | O(1) | O(1) | O(1) |
| Append | O(1) amortised; a resize O(n) time and O(n) stack | O(1) amortised; a resize O(n) time, O(1) extra | overflow when full |
| Insert or delete at the front | O(n) time and stack | O(n) time, O(1) extra | O(n) time and stack |
| Linear search | O(n) time and stack | O(n) time, O(1) extra | O(n) time and stack |
| A million elements | stack overflow inside a resize | fine | stack overflow in a linear recursion |

## Watch out for

- **A resize copy with no base case**: Stop at `i == size`, the number of elements, not the new capacity.
- **Forgetting that resize is recursive**: Keep hidden, linear work such as copying as a loop.
- **Shifting from pos upwards on insertion**: Start shift_right at the last element and move down to pos.
- **Forgetting to free the old array after the recursive copy**: `free` the old array once the copy has returned, as `resize` does.
