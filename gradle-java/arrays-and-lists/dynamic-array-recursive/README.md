# Dynamic Array (Recursive)

**The recursive dynamic array doubles when full and halves when a quarter full, like any dynamic array, but copies, shifts, traverses and searches recursively: the time is unchanged, and the extra space is one call-stack frame per element, so a large resize can overflow the stack.**

![A recursive resize from 4 to 8: copy(0) waits for copy(1) ... down to the base case copy(4)](docs/images/structure.png)

*A recursive resize from 4 to 8: copy(0) waits for copy(1) ... down to the base case copy(4)*

A **dynamic array** keeps a static array inside, with a **size** (elements in use) and a **capacity** (places available): appending writes into the next spare place, a full array is **resized** to twice the capacity, and a quarter-full array is halved. This project is the `dynamic-array` project with every loop written as a **recursion**: traversal, linear search, the shifts of insertion and deletion, and the **copy inside a resize**. Each recursive method handles one element and calls itself for the rest, stopping at a **base case**. The time of every operation is unchanged. The extra space is not: each call still waiting keeps a **frame** on the **call stack**, so a resize of n elements needs n frames, and a large enough resize overflows the stack.

> New to data structures? Read [Start here](../../START-HERE.md) first: it explains memory, references and counting steps in ten minutes.

## The everyday idea

Picture a family moving house, carrying one box at a time down a line of helpers. The first helper carries box 0 and then asks the next helper to carry the rest, and waits there until the whole move is finished. The second helper carries box 1, asks the next, and waits too. The last helper finds no boxes left and says "done", and only then can everyone go home. With four boxes, four helpers wait at once. With a million boxes, you would need a million helpers standing in the corridor at the same time, and the corridor is not that long: that is a stack overflow.

## The worked example: A growing music playlist, with every loop written as a recursion

The playlist from the `dynamic-array` project starts with room for four songs. The demo traces the recursive copy when the fifth song arrives, grows the playlist to nine songs, inserts an intro at the front, deletes a song from the middle, searches, shrinks, and finally tries to append a million songs.

## Why it exists

Writing the loops of a dynamic array as recursions shows two things. First, how a loop becomes a recursion: the loop variable becomes a parameter, the loop condition becomes the base case, and `i++` becomes the call with `i + 1`. Second, why real code does not do this for linear passes: the resize, an operation the programmer never calls directly, suddenly needs as many stack frames as there are elements, and an ordinary `append` can crash the program.

## New words

| Word | What it means here |
| --- | --- |
| **size and capacity** | The number of elements in use, and the number of places in the array inside. |
| **resize** | A new array of a different capacity, with every element copied across; here the copy is recursive. |
| **base case** | The call that returns without calling again: `i == size`, nothing left to copy, visit or compare. |
| **call stack and frame** | One frame per call that has not yet returned, holding its parameters, such as `i`. |
| **recursion depth** | The most frames at once: 4 for a resize of 4 elements, n for a resize of n. |
| **amortised O(1)** | The average time per append, counting the rare resize: unchanged by writing the copy recursively. |

## What you will see, act by act

Run the demo and it tells the story in 5 acts, printing the real numbers that the tests check:

```bash
./gradlew run
```

| Act | What it shows |
| --- | --- |
| 1. A recursive copy | The fifth song needs a resize to 8: copy(0) copies Blue Sky and calls copy(1), down to the base case copy(4); 4 copies at depth 4. |
| 2. Growing | The ninth song resizes to 16 with 8 copies at depth 8; 9 appends make 2 resizes and 12 copies; traversal is 9 calls deep. |
| 3. The middle, recursively | insertAt(0, "Intro") shifts 9 songs at depth 9; deleteAt(5) shifts 4 at depth 4; linearSearch("Firefly") finds index 7 at depth 8. |
| 4. Shrinking, recursively | shrinkToFit copies 9 songs at depth 9 into exactly 9 places; deleting 17 songs down to 8 halves 32 places to 16 with 8 copies at depth 8. |
| 5. The limit of recursion | Appending 1,000,000 songs throws StackOverflowError inside a resize, long before the end; the loop version finishes. |

Each act is drawn step by step in [the explained walkthrough](docs/dynamic-array-recursive-explained.md) and in [the animation](docs/animation.html).

## The operations, and what they cost

Time is given for the best, average and worst case in Big-O notation (START-HERE.md explains it by counting steps); the last column is what the demo actually counted, so every formula can be checked against a real number.

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | Steps counted in the demo |
| --- | --- | --- | --- | --- | --- |
| Traverse | Visit arr[i], then traverse from i + 1 | O(n) / O(n) / O(n) | O(n) | O(1) | depth 9 for 9 songs |
| Access `get(i)` / update | One address calculation (not recursive) | O(1) / O(1) / O(1) | O(1) | O(1) | 1 step |
| Insert at end `append(x)` | Write arr[size]; recursive resize first when full | O(1) / O(1) amortised / O(n) when it resizes | O(1), or O(n) during a resize | O(1), plus the new array | 4 copies at depth 4; 8 at depth 8 |
| Resize (inside append) | copy(i): newArr[i] = arr[i], then copy(i + 1) | O(n) / O(n) / O(n) | O(n) | O(1), plus the new array | 12 copies over 9 appends |
| Insert `insertAt(pos, x)` | shiftRight from the last element down to pos | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 9 shifts at depth 9 |
| Delete `deleteAt(pos)` | shiftLeft from pos, clear the freed place | O(1) at the end / O(n) / O(n) at the front | O(n - pos) | O(1) | 4 shifts at depth 4 |
| Delete at end `deleteAtEnd()` | Clear arr[size-1]; halve (recursive copy) when a quarter full | O(1) / O(1) amortised / O(n) when it shrinks | O(1), or O(n) during a shrink | O(1) | 8 copies at depth 8 from 32 to 16 |
| Linear search | arr[i].equals(key)? If not, search from i + 1 | O(1) / O(n) / O(n) | O(n) | O(1) | 8 comparisons at depth 8 |
| Shrink to fit | Resize to exactly size places | O(n) / O(n) / O(n) | O(n) | O(1), plus the new array | 9 copies at depth 9 |

Extra space is the memory an operation needs besides the structure itself. A loop needs a fixed amount, O(1); a recursive operation needs one call-stack frame for every call still waiting, so its extra space is the depth of the recursion.

## The operations in code

### Resize with a recursive copy

```java
/** Allocates a new array and copies the elements into it recursively. O(n) time and stack. */
private void resize(int newCapacity) {
    String[] newArr = new String[newCapacity];
    copy(0, newArr);
    arr = newArr;
    resizes++;
}

private void copy(int i, String[] newArr) {
    if (i == size) {               // base case: every element copied
        return;
    }
    steps.enter();
    newArr[i] = arr[i];
    steps.copy();
    copy(i + 1, newArr);
    steps.exit();
}
```

`copy(i, newArr)` copies element i, then calls itself for i + 1; the base case is `i == size`, nothing left to copy. The loop `for (i = 0; i < size; i++)` has become the parameter `i`, the base case `i == size`, and the call with `i + 1`. Every frame waits until the last element is copied, so a resize of n elements is n frames deep.

### Insertion at the end

```java
/**
 * Insertion at the end: one write, after a recursive resize to double the capacity when full.
 * O(1) amortised; the resizing append costs O(n) time and O(n) stack.
 */
public void append(String value) {
    checkValue(value);
    if (size == arr.length) {
        resize(arr.length == 0 ? 1 : arr.length * 2);
    }
    arr[size] = value;
    steps.write();
    size++;
}
```

Not recursive itself, but when the array is full it calls `resize`, and with it the recursive copy. That is why an ordinary append can throw `StackOverflowError` once the array is large.

### Insertion at a position

```java
/** Insertion at {@code pos}: resize when full, then shiftRight from the last element down to pos. */
public void insertAt(int pos, String value) {
    checkValue(value);
    if (pos < 0 || pos > size) {
        throw new IndexOutOfBoundsException("position " + pos + " outside 0.." + size);
    }
    if (size == arr.length) {
        resize(arr.length == 0 ? 1 : arr.length * 2);
    }
    shiftRight(size - 1, pos);
    arr[pos] = value;
    steps.write();
    size++;
}

private void shiftRight(int i, int pos) {
    if (i < pos) {                 // base case: every element from pos onwards has moved
        return;
    }
    steps.enter();
    arr[i + 1] = arr[i];
    steps.shift();
    shiftRight(i - 1, pos);
    steps.exit();
}
```

`shiftRight(i, pos)` moves `arr[i]` one place right and recurses on `i - 1`, starting at the last element so nothing is overwritten; it stops when `i < pos`.

### Linear search

```java
/** Linear search: is the key at i? If not, search from i + 1. O(n) time, O(n) stack. */
public int linearSearch(String key) {
    return linearSearch(key, 0);
}

private int linearSearch(String key, int i) {
    if (i == size) {
        return -1;                 // base case: searched everything
    }
    steps.enter();
    steps.compare();
    int result = arr[i].equals(key) ? i : linearSearch(key, i + 1);
    steps.exit();
    return result;
}
```

Two base cases: no elements left (-1) and a match (i). Otherwise the rest of the array is searched by the next call.

## Compared with related structures

The recursive dynamic array against the loop version and the related structures (n elements):

| Operation | Recursive dynamic array | Dynamic array (loops) | Recursive static array |
| --- | --- | --- | --- |
| Access element i | O(1) | O(1) | O(1) |
| Append | O(1) amortised; a resize O(n) time and O(n) stack | O(1) amortised; a resize O(n) time, O(1) extra | overflow when full |
| Insert or delete at the front | O(n) time and stack | O(n) time, O(1) extra | O(n) time and stack |
| Linear search | O(n) time and stack | O(n) time, O(1) extra | O(n) time and stack |
| A million elements | StackOverflowError inside a resize | fine | StackOverflowError in a linear recursion |

## The code

```
src/main/java/com/jk/explore/dynamicarrayrecursive/
├── Lines.java                      The demo's printed lines, kept in one {@code StringBuilder} so the tests can check them
├── RecursiveDynamicArray.java      A dynamic array (a resizable array) of strings whose operations are written recursively
├── RecursiveDynamicArrayDemo.java  Tells the story of the recursive dynamic array in five acts, printing the real counts and the real depth of every recursion
└── StepCounter.java                Counts what an array operation costs, so the demo prints real numbers instead of claims: element reads and writes, comparisons, shifts (moving an element to the next position), swaps, copies into a new array, and the deepest recursion reached (the most call-stack frames alive at once, which is the extra space a recursive version uses)
```

## Test

```bash
./gradlew test
```

21 tests in `DemoRunsTest`, `RecursiveDynamicArrayTest`. Every number the demo prints is asserted, and nothing depends on the clock, so every run gives the same result.

## Common mistakes

| The mistake | What goes wrong | The fix |
| --- | --- | --- |
| A resize copy with no base case | copy(i) keeps calling copy(i + 1) past the elements and throws an exception. | Stop at `i == size`, the number of elements, not the new capacity. |
| Forgetting that resize is recursive | An append that looks O(1) crashes the program with a StackOverflowError on a large array. | Keep hidden, linear work such as copying as a loop. |
| Shifting from pos upwards on insertion | Each element overwrites the next before it moves, copying one value everywhere. | Start shiftRight at the last element and move down to pos. |
| Not clearing a deleted place | The deleted string stays referenced and is never freed. | Set the freed place to `null`. |

## Try it yourself

1. **Easy.** Write a recursive `int count(String key, int i)` returning how many elements from index i on are equal to `key`. What is its base case, and its extra space?
2. **Medium.** Rewrite the recursive `copy(i, newArr)` as a loop. Which part of the recursion became the loop condition, and which became `i++`?
3. **Harder.** Write a recursive copy that needs only O(log n) stack: copy the left half and the right half of a range by calling itself on each. Why is the depth logarithmic although every element is still copied?

The answers are in [docs/exercises.md](docs/exercises.md), below the questions, so they are not given away.

## When to use it

- Learning how each loop of a dynamic array becomes a recursion, with its base case.
- Small arrays, where the depth stays far below the call-stack limit.

## When not to

- Any array that may grow large: a recursive resize of a few tens of thousands of elements can overflow the call stack. Use the loops of `dynamic-array`.
- Performance-sensitive code: each call costs a frame, a jump and a return.

## Where you have already met this

- The `dynamic-array` project: the same structure with loops.
- The `static-array-recursive` project: the same recursive shifts and search on a fixed array.
- `ArrayList.add`, which resizes with a loop inside `Arrays.copyOf`.

## Technologies and versions

| Technology | Version | Used for |
| --- | --- | --- |
| Java | 25 | the code (toolchain set in `build.gradle`; Gradle fetches JDK 25 if it is missing) |
| Gradle | 9.8.0 (wrapper) | build and run, nothing to install |
| JUnit | 6.1.3 | the tests |
| videokit | course tool | the narrated video and animation: Piper `en_US-amy-medium`, speed 0.8, longer pauses (the `amy-slow` voice) |

## Learning material

| Document | What it is for |
| --- | --- |
| [Start here](../../START-HERE.md) | the ideas every project relies on |
| [Problem statement](docs/problem-statement.md) | the situation and what the project must show |
| [Prerequisites](docs/prerequisites.md) | what you need to know first |
| [Dynamic Array (Recursive), explained](docs/dynamic-array-recursive-explained.md) | every act, picture by picture |
| [Animated walkthrough](docs/animation.html) | the structure changing step by step, narrated |
| [Cheat sheet](docs/cheat-sheet.md) | the whole structure on one page |
| [Exercises and answers](docs/exercises.md) | practice, easy to harder |
| [Session guide](docs/session.md) | a one-hour lesson |
| [Specification](docs/spec.md) | what this project must be true of |

### How the pieces fit

The demo appends songs; a full array calls resize, which starts the recursive copy.

![Dynamic Array (Recursive): the pieces](docs/images/architecture-diagram.png)

### The classes

Public operations on the left; the private recursive helpers they call are listed with a minus sign.

![Dynamic Array (Recursive): the classes](docs/images/class-diagram.png)

### How the data moves

The loop's condition became the base case; the loop's i++ became the call on i + 1.

![Dynamic Array (Recursive): one copy call](docs/images/data-flow-diagram.png)

### Who calls whom, in order

The append triggers a resize, whose copy goes four calls deep.

![Dynamic Array (Recursive): appending the fifth song](docs/images/sequence-diagram.png)

### Video

`video/dynamic-array-recursive-explained.mp4` (with `.m4a` audio and `.srt` subtitles) is built by `video/build_video.sh`. Rendered media is not committed.

## Where this sits

Part of the [data structures course](../../index.md), in *arrays-and-lists*.
