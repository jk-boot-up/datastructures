# Problem statement — Reverse a Sub-list (`reverseBetween`)

> Also known as **Reverse Linked List II**. This is the problem this project
> solves. Read it *before* the [README](README.md) if you want to try it
> yourself first — which is by far the best way to use this repository.

| | |
| --- | --- |
| **Difficulty** | Medium |
| **Topic** | Singly linked list, pointer manipulation |
| **Target** | O(n) time, O(1) extra space, **one pass**, **in place** |
| **Prerequisite** | reversing a whole linked list |

---

## 1. The statement

You are given the head of a singly linked list, and two positions `from` and
`to`.

**Reverse the nodes of the list from position `from` to position `to`,
inclusive, and return the resulting list.** Every node outside that range must
keep its original position and its original order.

Do it **in place**: you may not create new nodes, and you may not copy the values
into an array, reverse that, and copy them back.

> **Indexing.** This project is **zero-based**, and `to` is **inclusive** — so
> `from` and `to` are the same kind of index you would pass to `get(i)`. The
> classic textbook and interview phrasing is **one-based** (`m` and `n`
> starting at 1). Nothing about the algorithm changes; only the arithmetic on
> the bounds does. See [section 6](#6-one-based-versus-zero-based).

---

## 2. Examples

### Example 1 — the general case

```
Input:   A → B → C → D → E,   from = 1, to = 3
Output:  A → D → C → B → E
```

`A` (index 0) and `E` (index 4) are outside the range, so they do not move.
`B C D` becomes `D C B`.

### Example 2 — the slice starts at the head

```
Input:   A → B → C → D,   from = 0, to = 2
Output:  C → B → A → D
```

Note that the **head of the list changed**. Any solution that assumes there is a
node before the slice will fail here. This is the single most common bug.

### Example 3 — the slice ends at the tail

```
Input:   A → B → C → D,   from = 1, to = 3
Output:  A → D → C → B
```

The **tail moved** too. If your list caches a tail pointer (this one does), it
must be updated — otherwise the list still *prints* correctly but the next
`addLast` silently corrupts it.

### Example 4 — a single-node slice

```
Input:   A → B → C,   from = 1, to = 1
Output:  A → B → C
```

A one-node range is already reversed. This must be a no-op, not a crash.

### Example 5 — the whole list

```
Input:   A → B → C → D,   from = 0, to = 3
Output:  D → C → B → A
```

Reversing everything is just the special case where the slice is the whole list.

---

## 3. Constraints

* The list has `n` nodes, `1 ≤ n`.
* `0 ≤ from ≤ to ≤ n - 1`.
* Element values may be **any type**, including `null` — this implementation is
  generic, so it must not assume the values are comparable, non-null, or even
  distinct.
* The list is **singly** linked: a node knows its successor, never its
  predecessor.

**Invariants that must still hold afterwards:**

1. `size` is unchanged — reversing moves no nodes in or out.
2. `head` points at the first node of the resulting list.
3. `tail` points at the last node.
4. The chain is fully traversable from `head` to `null`, with no cycle and no
   lost nodes.

---

## 4. What to think about before writing code

These are the questions worth asking out loud in an interview, and worth
answering for yourself before you start typing.

* **What is the node *before* the slice, and what happens when there isn't one?**
  This is the crux of the problem.
* **Which node ends up last in the reversed slice?** (The one that started it.)
  **What must it point at?** (Whatever came after the slice.)
* **When do I need to save a reference before overwriting a pointer?**
* **Does my solution work when the slice is the whole list? A single node? At
  either end?**
* **Am I making one pass, or two?** Walking to `from`, then reversing, is still
  one pass overall — you never revisit a node.

---

## 5. Expected complexity

| | Target | Why |
| --- | --- | --- |
| **Time** | **O(n)** | walk `from` steps to reach the slice, then flip `to - from + 1` arrows. No node is visited twice. |
| **Space** | **O(1)** | a fixed number of reference variables, regardless of `n` or the slice length. |

Solutions that copy values into an `ArrayList`, reverse the sub-list, and write
them back are **O(n) space** and do not count as solving this problem — though
they are a perfectly reasonable warm-up, and a good thing to compare against.

---

## 6. One-based versus zero-based

The classic statement uses one-based positions:

> Given `head` and two integers `m` and `n` where `1 ≤ m ≤ n ≤ length`, reverse
> the nodes from position `m` to position `n`.

To convert, subtract one from each:

```java
reverseBetweenOneBased(m, n)  ==  reverseBetween(m - 1, n - 1)
```

If you are practising for interviews, write the one-based wrapper as an
exercise — it should be a two-line method. If it is longer, you have duplicated
the algorithm instead of reusing it.

---

## 7. Hints

Read them one at a time, and only when stuck.

<details>
<summary><b>Hint 1 — where to start</b></summary>

You already know how to reverse a whole list with three pointers: save `next`,
flip the arrow, advance `previous`, advance `current`. That loop is *unchanged*
here. The only question is where to start it, when to stop it, and what to do
with the two loose ends afterwards.
</details>

<details>
<summary><b>Hint 2 — when to stop</b></summary>

A whole-list reversal stops when `current` becomes `null`. Here you know exactly
how many arrows to flip: `to - from + 1`. Use a counted loop instead of a
`while`.
</details>

<details>
<summary><b>Hint 3 — the two loose ends</b></summary>

After the loop, `previous` is the slice's **new first** node and `current` is the
**first node after** the slice. You need two more assignments:

* the node before the slice must point at `previous`;
* the node that *started* the slice must point at `current`.

Which means you had to save that starting node before the loop ran.
</details>

<details>
<summary><b>Hint 4 — killing the edge case</b></summary>

The "node before the slice" does not exist when `from == 0`. Rather than writing
an `if` for it, allocate a temporary **sentinel** node whose `next` is the
current head, and start your walk from *there*. Now the node before the slice
always exists. At the end, the real head is `sentinel.next` — which is correct
whether or not the head moved.
</details>

---

## 8. How this project answers it

| | |
| --- | --- |
| Reference solution | [`reverseBetween(from, to)`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L481) |
| The same thing without a sentinel, for comparison | [`reverseBetweenWithoutSentinel`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L543) |
| The whole-list special case | [`reverse()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L592) |
| Non-destructive variant (O(n) space) | [`reversedBetweenCopy`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L611) |
| Line-by-line explanation | [README section 4](README.md#4-the-star-operation-reversebetweenfrom-to) |
| Step-by-step animation | [`docs/animation.html`](docs/animation.html) |
| Tests | [`SinglyLinkedListTest`](src/test/java/org/jk/dsa/learning/SinglyLinkedListTest.java) |

### Verifying your own attempt

The test suite is written so you can drop in your own implementation and check
it honestly. In particular,
[`everySliceMatchesAReferenceImplementation`](src/test/java/org/jk/dsa/learning/SinglyLinkedListTest.java#L169)
tries **every possible `(from, to)` pair** on lists of size 1, 2, 3, 5 and 8 and
compares the result against an obviously-correct implementation built with
`java.util.Collections.reverse`. Over a hundred slices, checked automatically —
far more convincing than a handful of examples you thought of yourself.

```bash
./mvnw test
```

---

## 9. Follow-up problems

Natural next steps once this one is solid. Each reuses the machinery you just
built:

| Problem | Relationship |
| --- | --- |
| **Reverse in groups of k** | `reverseBetween` applied repeatedly, block by block |
| **Swap nodes in pairs** | reverse in groups of k, with k = 2 |
| **Rotate the list right by k** | join into a ring, then break it in the right place |
| **Palindrome check** | reverse the *second half*, then compare |
| **Reverse only even-positioned nodes** | same bookkeeping, different selection rule |

---

## 10. Common mistakes

A checklist to test yourself against — every one of these has a test in this
project that catches it.

1. **Forgetting the head can move.** Fails whenever `from == 0`.
2. **Forgetting to update `tail`.** The list prints fine; the next `addLast`
   corrupts it. Genuinely nasty, because the symptom appears far from the cause.
3. **Not saving the slice's first node** before reversing, so the tail of the
   list is stranded.
4. **Off-by-one in the walk.** Walking `from` steps from `head` lands you *on*
   the slice, not before it — one of the reasons the sentinel version is easier
   to get right.
5. **Off-by-one in the flip count.** The slice has `to - from + 1` nodes, not
   `to - from`.
6. **Crashing on `from == to`** instead of doing nothing.
7. **Assuming values are non-null** when comparing or printing — this list
   explicitly allows `null` elements.
