# Static Array (Generic, Recursive) — Cheat Sheet

**The generic recursive static array in C holds any type as a block of bytes with an element size, compares through comparison functions, and writes every walk as a function that handles one element or one half and calls itself for the rest; the time is unchanged and the extra space is the recursion depth.**

![linear_search for "Thu": one call per element, each asking the comparison function](images/structure.png)

| | |
| --- | --- |
| **What it is** | The generic recursive static array holds any type as bytes with an element size, compares through comparison functions, and writes each operation as one element's work plus a call on the rest; the time is unchanged and the extra space is the recursion depth. |
| **Everyday picture** | A queue of helpers, each comparing one box with a rule you hand them |
| **Use it when** | The structure must hold several element types, and the algorithm is naturally recursive; The recursion depth is logarithmic, as in binary search; Learning: this is the shape of generic recursive C code for trees and divide-and-conquer |
| **Avoid it when** | Linear recursion over large arrays: a million elements overflow the call stack; use the loops of `static-array-generic`; Only one element type is needed: the typed version is simpler and checked by the compiler |
| **Already in C** | Nothing recursive: `qsort` and `bsearch` in `<stdlib.h>` take the same block, size and comparison function |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | In the example |
| --- | --- | --- | --- | --- | --- |
| Traverse | Print element i, then traverse from i + 1 | O(n) / O(n) / O(n) | O(n) | O(1) | depth 7, for each type |
| Access `get` / `update` | One address calculation and a memcpy (not recursive) | O(1) / O(1) / O(1) | O(1) | O(1) | 1 step |
| Insert `insert_at` | shift_right from the last element down to pos, then memcpy in | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 5 shifts at depth 5 |
| Delete `delete_at` | memcpy the element out, then shift_left from pos | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 7 shifts at depth 7 |
| Linear search | compare(element i, key) == 0? If not, search from i + 1 | O(1) / O(n) / O(n) | O(n) | O(1) | 4 comparisons at depth 4 to find "Thu" |
| Binary search (sorted) | compare with the middle, search one half | O(1) / O(log n) / O(log n) | O(log n) | O(1) | 3 calls for "Thu"; 20 on 1,000,000 |
| Find maximum | compare element i with the maximum of the rest | O(n) / O(n) / O(n) | O(n) | O(1) | 6 comparisons at depth 6 |
| Reverse | Swap the ends byte by byte, reverse the middle | O(n) / O(n) / O(n) | O(n / 2) | O(1) | 3 swaps at depth 3 |

## Compared with related structures

The four static-array projects side by side (n elements):

| Property | Generic, recursive (this) | Generic, loops | int, recursive | int, loops |
| --- | --- | --- | --- | --- |
| Element types | any, by elem_size | any, by elem_size | int | int |
| Compares with | a comparison function | a comparison function | ==, < | ==, < |
| Traverse, linear search | O(n) time, O(n) stack | O(n) time, O(1) space | O(n) time, O(n) stack | O(n) time, O(1) space |
| Binary search | O(log n) time and stack | O(log n) time, O(1) space | O(log n) time and stack | O(log n) time, O(1) space |
| Type checked by the compiler | no | no | yes | yes |
| A million elements | linear recursion overflows | fine | linear recursion overflows | fine |

## Watch out for

- **No base case, or no progress towards it**: Write the base case first; make every call work on `i + 1` or a smaller half.
- **Comparing strings or structs with `==`**: Compare through the comparison function.
- **The wrong element size**: Pass `sizeof` of the element type itself.
- **Linear recursion on large data**: Recurse where the depth is logarithmic; loop over long linear passes.
