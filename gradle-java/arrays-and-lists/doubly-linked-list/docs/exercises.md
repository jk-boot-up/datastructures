# Exercises — Doubly Linked List

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `String getFromEnd(int k)` that returns the data of the node k places from the end (k = 0 is the tail). What is its time complexity?

## 2. Medium

Write `void moveToFront(Node node)` that moves a node already reached to the head in O(1). Where is this used?

## 3. Harder

Make `get(pos)` walk from whichever end is nearer. What extra field does the list need, which operations must maintain it, and what is the worst case now?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
String getFromEnd(int k) {
    Node current = tail;
    for (int i = 0; i < k && current != null; i++) {
        current = current.prev;
    }
    if (current == null) throw new IndexOutOfBoundsException("no node " + k + " from the end");
    return current.data;
}
```
O(k) time, O(1) space; a singly linked list would need O(n), or two pointers k apart.

</details>

<details>
<summary>Answer 2</summary>

```java
void moveToFront(Node node) {
    if (node == head) return;
    deleteNode(node);                   // unlink: 2 pointers
    node.prev = null;                   // relink at the front
    node.next = head;
    head.prev = node;
    head = node;
}
```
O(1): no search, only pointer changes. An LRU cache does exactly this every time an entry is used, which is why it keeps a doubly linked list.

</details>

<details>
<summary>Answer 3</summary>

The list needs a `size` field (without it, finding which end is nearer costs a count, O(n)). Every insertion adds one and every deletion subtracts one. Then:
```java
Node nodeAt(int pos) {
    if (pos < size / 2) {
        Node current = head;
        for (int i = 0; i < pos; i++) current = current.next;
        return current;
    }
    Node current = tail;
    for (int i = size - 1; i > pos; i--) current = current.prev;
    return current;
}
```
The worst case is the middle, n / 2 steps: still O(n), but half the walk. `java.util.LinkedList` does exactly this.

</details>
