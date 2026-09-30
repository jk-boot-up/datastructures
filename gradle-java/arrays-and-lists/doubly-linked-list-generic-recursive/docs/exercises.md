# Exercises — Doubly Linked List (Generic, Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int countBackward(Node<T> node)` using `prev`. What does it return from the tail of the five photos?

## 2. Medium

Write a recursive `Node<T> findLast(Node<T> node, T key)` that finds the last node equal to `key` by recursing along `prev` from the tail. Why is this simpler than from the head?

## 3. Harder

Write a generic recursive `static <T> boolean sameForwardAndBack(Node<T> front, Node<T> back)` that checks whether a list reads the same from both ends. What is its depth on n nodes?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int countBackward(Node<T> node) {
    if (node == null) return 0;
    return 1 + countBackward(node.prev);
}
```
5, at depth 5. If it ever differs from `count()`, a prev pointer is wrong.

</details>

<details>
<summary>Answer 2</summary>

```java
Node<T> findLast(Node<T> node, T key) {
    if (node == null) return null;
    if (node.data.equals(key)) return node;
    return findLast(node.prev, key);
}
```
Called as `findLast(tail, key)`. Walking backwards, the first match is the last one; from the head, the whole list would have to be walked remembering the latest match.

</details>

<details>
<summary>Answer 3</summary>

```java
static <T> boolean sameForwardAndBack(Node<T> front, Node<T> back) {
    if (front == back || front.prev() == back) return true;
    if (!front.data().equals(back.data())) return false;
    return sameForwardAndBack(front.next(), back.prev());
}
```
Called with the head and the tail of a non-empty list. The two pointers move inward one node each per call, so the depth is about n / 2.

</details>
