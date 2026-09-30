# Exercises — Doubly Linked List (Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int countBackward(Node node)` that counts from the tail using `prev`. Does it give the same answer as `count()`?

## 2. Medium

Write a recursive `boolean isPalindrome(Node front, Node back)` that compares the data from both ends inward. What are its base cases?

## 3. Harder

Write a recursive `Node nodeAtFromNearerEnd(int pos, int size)` that recurses from the head for the first half and from the tail for the second. What is its worst-case depth?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int countBackward(Node node) {
    if (node == null) return 0;
    return 1 + countBackward(node.prev);
}
```
Called as `countBackward(tail)`. On a correctly linked list it equals `count()`; if they differ, a `prev` pointer is wrong, which makes it a useful check. O(n) time and stack.

</details>

<details>
<summary>Answer 2</summary>

```java
boolean isPalindrome(Node front, Node back) {
    if (front == back || front.prev == back) return true;     // met in the middle
    if (!front.data.equals(back.data)) return false;
    return isPalindrome(front.next, back.prev);
}
```
Called as `isPalindrome(head, tail)` on a non-empty list. It stops when the two pointers meet (odd length) or cross (even length), or at the first mismatch. O(n) time, O(n / 2) stack; only a doubly linked list can walk inward from both ends.

</details>

<details>
<summary>Answer 3</summary>

```java
Node fromHead(Node node, int k) { return k == 0 ? node : fromHead(node.next, k - 1); }
Node fromTail(Node node, int k) { return k == 0 ? node : fromTail(node.prev, k - 1); }

Node nodeAtFromNearerEnd(int pos, int size) {
    return pos < size / 2 ? fromHead(head, pos) : fromTail(tail, size - 1 - pos);
}
```
The worst case is the middle: about n / 2 calls deep. Still O(n), but half the stack of a walk from the head.

</details>
