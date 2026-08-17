# Problem statement — Find the Middle Node (`findMiddle`)

> Also known as **Middle of the Linked List**. This is the problem this project
> solves. Read it *before* the [README](README.md) if you want to try it
> yourself first — which is by far the best way to use this repository.

| | |
| --- | --- |
| **Difficulty** | Easy |
| **Topic** | Singly linked list, two pointers ("tortoise and hare") |
| **Target** | O(n) time, O(1) extra space, **one pass**, **without knowing the length in advance** |
| **Prerequisite** | none — this is a good first two-pointer problem |

---

## 1. The statement

You are given the head of a singly linked list. **Return the value of the
middle node.**

If the list has an **even** number of nodes, there are two middle nodes —
return the value of the **second** one.

You may assume the length is not known ahead of time and cannot be looked up
for free (pretend, for the purpose of this exercise, that there is no cached
`size` field — see [section 4](#4-what-to-think-about-before-writing-code) for
why this restriction is the whole point).

---

## 2. Examples

### Example 1 — odd length

```
Input:   A → B → C → D → E
Output:  C
```

Five nodes, one true middle: `C`, at index 2.

### Example 2 — even length

```
Input:   A → B → C → D
Output:  C
```

Four nodes, two candidate middles (`B` at index 1, `C` at index 2). The
convention this project uses — the same one used by the classic interview
version of this problem — is to return the **second** one, `C`.

### Example 3 — a single node

```
Input:   A
Output:  A
```

A list of one node is its own middle.

### Example 4 — two nodes

```
Input:   A → B
Output:  B
```

The smallest case that actually exercises the "return the second middle" rule.
A solution that special-cases "two nodes → return the first" will fail this
example and nothing smaller catches it.

### Example 5 — a longer even list

```
Input:   A → B → C → D → E → F
Output:  D
```

Six nodes, middles at index 2 (`C`) and index 3 (`D`) — return `D`.

---

## 3. Constraints

* The list has `n` nodes, `1 ≤ n`. An empty list has no middle — decide what
  your method should do (this project throws, matching the convention every
  other "read from an empty list" operation in the class already uses).
* Element values may be **any type**, including `null` — this implementation is
  generic.
* The list is **singly** linked: a node knows its successor, never its
  predecessor, and there is no random access.
* You may **not** allocate a second list or copy values into an array.

**What must still hold afterwards:** nothing — this is a read-only query. The
list itself is left completely unchanged, which is a useful property to check
for, since it is easy to accidentally advance a pointer that the caller also
holds.

---

## 4. What to think about before writing code

These are the questions worth asking out loud in an interview, and worth
answering for yourself before you start typing.

* **If you already knew the length `n`, this would be trivial** — walk `n / 2`
  steps from the head. So why is this problem interesting at all? Because in
  general you *don't* get the length for free. A file read line by line, a
  network stream, an iterator you can only advance once — none of these tell
  you "how many are left" without consuming themselves to find out.
* **Can you find the middle by looking at the sequence only once, and never
  going backwards?**
* **What if you moved one pointer twice as fast as another, starting them both
  at the head?** Where is the slow one when the fast one runs out of list?
* **Exactly when does the fast pointer "run out of room" for two more steps?**
  Get this loop condition slightly wrong and you are off by one on every
  even-length list, while every odd-length list still passes — which is
  exactly the kind of bug that survives casual testing.
* **Does your solution work for a single node? Two nodes?** Trace it by hand;
  these are the cases that break first.

---

## 5. Expected complexity

| | Target | Why |
| --- | --- | --- |
| **Time** | **O(n)** | every node is visited at most once (by the faster pointer) |
| **Space** | **O(1)** | two reference variables, regardless of `n` |
| **Passes** | **1** | the defining constraint — see below |

A solution that counts the nodes first and then walks to `count / 2` is also
O(n) time and O(1) space — but it makes **two** passes over the list, and it
only works if the sequence can be traversed a second time at all. That
distinction, not the big-O, is the actual lesson of this problem. See
[README section 4.6](README.md#46-three-ways-one-answer) for a full comparison
of three different-cost implementations that all return the same answer.

---

## 6. Why "second middle" and not "first middle"?

Both are defensible; this project picks the convention used by the classic
interview statement of this problem (LeetCode 876), because it is the one you
are most likely to be asked to reproduce. The tie-break matters less than
**being consistent and testing the even-length case explicitly** — a
surprising number of correct-looking solutions silently return the *first*
middle on even lists because that is what a naively-terminated loop produces
by accident, not by design.

---

## 7. Hints

Read them one at a time, and only when stuck.

<details>
<summary><b>Hint 1 — the length-first approach</b></summary>

If you can count the nodes, the answer is `nodeAt(count / 2)`. Write this
version first — it is a correct O(n) solution, and a useful reference to check
your one-pass version against. Just remember it visits the sequence twice.
</details>

<details>
<summary><b>Hint 2 — two pointers, different speeds</b></summary>

Start two pointers at the head. Advance one by one node per step, and the
other by two nodes per step. Every time the fast one advances two nodes, the
slow one has advanced exactly one — so when the fast one has covered the whole
list, the slow one has covered half of it.
</details>

<details>
<summary><b>Hint 3 — the stopping condition</b></summary>

The fast pointer needs to take **two** steps each iteration, so before taking
them you must check that both are safe: the current node is not `null`,
*and* the current node's `next` is not `null`. Stop the loop the moment either
check fails.
</details>

<details>
<summary><b>Hint 4 — why the stopping condition gives you "second middle"</b></summary>

Trace a 4-node list `A B C D` by hand with the loop condition
`fast != null && fast.next != null`. The loop runs exactly twice, and slow
ends on `C` — the second of the two middles. Trace a 5-node list the same way
and confirm slow ends on the true middle, `C`. If your loop condition is
subtly different (for example, checking only `fast != null`), redo this trace
— that is where "off by one on even lists only" bugs come from.
</details>

---

## 8. How this project answers it

| | |
| --- | --- |
| Reference solution (one pass) | [`findMiddle()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L460) |
| The two-pass version, for comparison | [`findMiddleTwoPass()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L490) |
| The version that cheats using the cached `size` | [`findMiddleFromSize()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L524) |
| The zero-based index of the middle node | [`middleIndex()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L546) |
| Line-by-line explanation | [README section 4](README.md#4-the-star-operation-findmiddle) |
| Step-by-step animation | [`docs/animation.html`](docs/animation.html) |
| Tests | [`SinglyLinkedListTest`](src/test/java/org/jk/dsa/learning/SinglyLinkedListTest.java) |

### Verifying your own attempt

The test suite is written so you can drop in your own implementation and check
it honestly. In particular,
[`matchesReferenceForEveryLength`](src/test/java/org/jk/dsa/learning/SinglyLinkedListTest.java)
tries lists of size 1 through 50 (including several powers of two and their
neighbours, where off-by-one bugs like to hide) against a reference built with
a plain `java.util.ArrayList`.

```bash
./gradlew test
```

---

## 9. Follow-up problems

Natural next steps once this one is solid. Each reuses the slow/fast technique
you just built:

| Problem | Relationship |
| --- | --- |
| **Detect a cycle** | run slow/fast; if they ever meet, there is a cycle |
| **Find where a cycle begins** | detect the cycle, then reset one pointer to the head and advance both one step at a time |
| **Palindrome check** | find the middle, reverse the second half, compare it against the first |
| **Reorder list (`A B C D → A D B C`)** | find the middle, reverse the second half, then merge the two halves alternately |
| **Delete the middle node** | find the node *before* the middle (track one pointer behind `slow`), then unlink |

---

## 10. Common mistakes

A checklist to test yourself against — every one of these has a test in this
project that catches it.

1. **Wrong loop condition.** `while (fast != null)` alone lets `fast.next.next`
   throw a `NullPointerException` the moment `fast.next` is `null`. You need
   **both** `fast != null` **and** `fast.next != null`.
2. **Returning the first middle on even-length lists** instead of the second —
   passes every odd-length test and fails every even-length one.
3. **Off-by-one on very short lists.** Trace `n = 1` and `n = 2` by hand;
   these are the cases a subtly wrong loop condition gets wrong first.
4. **Reading `size` "just this once" for convenience** and calling it a
   one-pass solution. If you look at the cached length at all, you have used
   information a genuine single forward pass does not have — see
   [`findMiddleFromSize()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L524)
   for why this project keeps that version clearly separate and clearly
   labelled.
5. **Mutating `head` or the list's own cursors.** This is a read-only query;
   `slow` and `fast` must be local variables, never assignments back into the
   list's fields.
6. **Crashing on an empty list** instead of a clear exception.
