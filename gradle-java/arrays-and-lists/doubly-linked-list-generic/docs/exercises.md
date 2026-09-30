# Exercises — Doubly Linked List (Generic)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `int totalKilobytes(GenericDoublyLinkedList<Photo> album)` walking from the head with `head()`, `next()` and `data()`. What does it return for the five photos?

## 2. Medium

Write `void moveToFront(Node<T> node)` for the generic list. Why is it O(1), and which structure uses it?

## 3. Harder

Write `T removeFirstLargerThan(T limit)`, which deletes and returns the first element greater than `limit`. What must change in the class header for `compareTo` to be available, and what does that cost the class?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int totalKilobytes(GenericDoublyLinkedList<Photo> album) {
    int total = 0;
    for (Node<Photo> n = album.head(); n != null; n = n.next()) {
        total += n.data().kilobytes();
    }
    return total;
}
```
820 + 540 + 610 + 930 + 1200 = 4,100. O(n) time, O(1) space.

</details>

<details>
<summary>Answer 2</summary>

```java
void moveToFront(Node<T> node) {
    if (node == head) return;
    deleteNode(node);
    node.prev = null;
    node.next = head;
    head.prev = node;
    head = node;
}
```
Unlinking and relinking are pointer changes only, with no search, because the node knows both neighbours. An LRU cache does this on every access.

</details>

<details>
<summary>Answer 3</summary>

The header must become `GenericDoublyLinkedList<T extends Comparable<T>>`, so that `compareTo` is available:
```java
T removeFirstLargerThan(T limit) {
    for (Node<T> n = head; n != null; n = n.next) {
        if (n.data.compareTo(limit) > 0) {
            deleteNode(n);
            return n.data;
        }
    }
    return null;
}
```
The cost is that the list can then only hold comparable types; a better design puts such a method outside the class, or takes the comparison as a parameter.

</details>
