# Exercises — Singly Linked List (Generic, Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `boolean contains(Node<T> node, T key)`. What are its two base cases?

## 2. Medium

Write a recursive `int totalMetres(Node<Clue> node)` as a static method outside the generic class. Why can it not be an instance method of `GenericRecursiveSinglyLinkedList<T>`?

## 3. Harder

Write a recursive `Node<T> removeAll(Node<T> node, T key)` that deletes every node equal to `key`, returning the new first node. What does it cost?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
boolean contains(Node<T> node, T key) {
    if (node == null) return false;                // base case: empty list
    if (node.data.equals(key)) return true;        // base case: found
    return contains(node.next, key);
}
```
O(n) time and stack.

</details>

<details>
<summary>Answer 2</summary>

```java
static int totalMetres(Node<Clue> node) {
    if (node == null) return 0;
    return node.data().metres() + totalMetres(node.next());
}
```
Inside the generic class, `T` could be any type, so `node.data.metres()` would not compile: only `Clue` has `metres()`. On the six clues it returns 230.

</details>

<details>
<summary>Answer 3</summary>

```java
Node<T> removeAll(Node<T> node, T key) {
    if (node == null) return null;
    Node<T> rest = removeAll(node.next, key);
    if (node.data.equals(key)) return rest;        // drop this node
    node.next = rest;
    return node;
}
```
Called as `head = removeAll(head, key)`. Every node is visited once: O(n) time and O(n) stack.

</details>
