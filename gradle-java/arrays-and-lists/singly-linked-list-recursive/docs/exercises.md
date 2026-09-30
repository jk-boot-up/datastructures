# Exercises — Singly Linked List (Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int countOccurrences(Node node, String key)`. What is its base case, and its extra space?

## 2. Medium

Write a recursive `String getLast(Node node)` that returns the data of the last node. How deep does it go on the six clues?

## 3. Harder

Write a recursive `Node insertSorted(Node node, String data)` that inserts into a list kept in alphabetical order, returning the new first node. Use it to build the six clues in alphabetical order.

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int countOccurrences(Node node, String key) {
    if (node == null) return 0;                               // base case
    return (node.data.equals(key) ? 1 : 0) + countOccurrences(node.next, key);
}
```
Base case: the empty list. O(n) time and O(n) stack.

</details>

<details>
<summary>Answer 2</summary>

```java
String getLast(Node node) {
    if (node == null) return null;                             // an empty list has no last node
    if (node.next == null) return node.data;                   // base case: this is the last node
    return getLast(node.next);
}
```
It makes one call per node before the last: 5 waiting calls on six clues, returning "the chest". O(n) time and stack.

</details>

<details>
<summary>Answer 3</summary>

```java
Node insertSorted(Node node, String data) {
    if (node == null || data.compareTo(node.data) <= 0) {
        return new Node(data, node);                           // base case: it goes in front of this list
    }
    node.next = insertSorted(node.next, data);
    return node;
}
```
Called as `head = insertSorted(head, clue)` for each clue. The list becomes: the bridge, the chest, the fountain, the oak tree, the old well, the red gate. Each insertion is O(n) time and stack in the worst case.

</details>
