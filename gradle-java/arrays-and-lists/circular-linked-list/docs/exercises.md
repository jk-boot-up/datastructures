# Exercises — Circular Linked List

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `String playerAfter(String name, int k)` that returns the player k turns after `name`, going round as often as needed. What is its time complexity?

## 2. Medium

Write `void split(CircularLinkedList a, CircularLinkedList b)` that splits a circular list with an even number of nodes into two circular halves. How many pointers change?

## 3. Harder

In the counting-out game with n players and k = 2, where should you sit to win? Work out n = 5 and n = 8 with the list, then find the pattern.

---

## Answers

<details>
<summary>Answer 1</summary>

```java
String playerAfter(String name, int k) {
    Node current = last.next;
    while (!current.data.equals(name)) current = current.next;   // assumes the name is present
    for (int i = 0; i < k; i++) current = current.next;
    return current.data;
}
```
O(n + k): finding the player, then k steps; no special case at the end of the circle.

</details>

<details>
<summary>Answer 2</summary>

Walk `slow` one step and `fast` two steps from `last.next` until `fast` reaches `last` (or the node before it): `slow` is then the end of the first half. Then:
```java
Node firstA = last.next, firstB = slow.next;
slow.next = firstA;          // close the first half into a circle
last.next = firstB;          // close the second half into a circle
a.last = slow;
b.last = last;
```
Two next pointers change, plus the two lists' `last`. O(n) for the walk.

</details>

<details>
<summary>Answer 3</summary>

With k = 2 every second player leaves. For n = 5 the winner is the 3rd seat; for n = 8 it is the 1st. Writing n = 2^m + L (with 2^m the largest power of two not above n), the winner is seat 2L + 1: 5 = 4 + 1 gives 3; 8 = 8 + 0 gives 1. The list simulation costs O(n k); the formula answers in O(1), which is why this is also a classic mathematics puzzle.

</details>
