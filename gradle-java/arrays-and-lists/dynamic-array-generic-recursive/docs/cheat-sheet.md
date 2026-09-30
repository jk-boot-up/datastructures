# Dynamic Array (Generic, Recursive) — Cheat Sheet

**The generic recursive dynamic array holds references to any type T, doubles when full and halves when a quarter full, finds elements with equals, and does every linear pass, including the copy inside a resize, as a recursion: the time is unchanged, the extra space is one frame per element, and a large resize overflows the stack.**

![A recursive resize copies references: copy(0) ... copy(3), then the base case copy(4)](images/structure.png)

| | |
| --- | --- |
| **What it is** | The generic recursive dynamic array stores references to any type, finds them with equals, and does every linear pass as a recursion, so its time is unchanged and its extra space is one frame per element. |
| **Everyday picture** | A line of helpers copying a list of addresses, one line each |
| **Use it when** | Learning how generics and recursion combine, on a structure you already know; Small lists, where the recursion depth stays low |
| **Avoid it when** | Any list that may grow large: a recursive resize overflows the call stack. Use `dynamic-array-generic`, or `ArrayList<E>`; Performance-sensitive code: each call costs a frame, a jump and a return |
| **Already in Java** | Nothing: `java.util.ArrayList<E>` resizes with a loop |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | Steps counted in the demo |
| --- | --- | --- | --- | --- | --- |
| Traverse | Visit arr[i], then traverse from i + 1 | O(n) / O(n) / O(n) | O(n) | O(1) | depth 5 for 5 play counts |
| Access `get(i)` / update | One address calculation (not recursive) | O(1) / O(1) / O(1) | O(1) | O(1) | 1 step |
| Insert at end `append(x)` | Write arr[size]; recursive resize first when full | O(1) / O(1) amortised / O(n) when it resizes | O(1), or O(n) during a resize | O(1), plus the new array | 4 references at depth 4; 12 over 9 appends |
| Insert `insertAt(pos, x)` | shiftRight from the last element down to pos | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 9 shifts at depth 9 |
| Delete `deleteAt(pos)` | shiftLeft from pos, clear the freed place | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 4 shifts at depth 4 |
| Delete at end `deleteAtEnd()` | Clear arr[size-1]; recursive halving when a quarter full | O(1) / O(1) amortised / O(n) when it shrinks | O(1), or O(n) during a shrink | O(1) | 0 shifts |
| Linear search | arr[i].equals(key)? If not, search from i + 1 | O(1) / O(n) / O(n) | O(n) | O(1) | 7 equals calls at depth 7 |
| Shrink to fit | Recursive copy into exactly size places | O(n) / O(n) / O(n) | O(n) | O(1), plus the new array | 8 references at depth 8 |

## Compared with related structures

The four dynamic-array projects side by side (n elements):

| Property | Generic, recursive (this) | Generic, loops | String, recursive | String, loops |
| --- | --- | --- | --- | --- |
| Element types | any T | any T | String | String |
| Append | O(1) amortised; resize O(n) stack | O(1) amortised | O(1) amortised; resize O(n) stack | O(1) amortised |
| Insert / delete at front | O(n), O(n) stack | O(n), O(1) extra | O(n), O(n) stack | O(n), O(1) extra |
| Linear search | equals, O(n) stack | equals, O(1) extra | equals, O(n) stack | equals, O(1) extra |
| A million appends | StackOverflowError | fine | StackOverflowError | fine |

## Watch out for

- **Writing `new T[capacity]`**: `(T[]) new Object[capacity]`, kept inside the class.
- **Searching with `==`**: Compare with `equals`.
- **A recursive copy inside resize**: Keep hidden linear work as a loop; recurse only where the depth is small.
- **Not clearing freed places**: Set every place that falls out of use to `null`.
