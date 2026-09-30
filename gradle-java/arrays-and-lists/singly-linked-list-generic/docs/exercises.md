# Exercises — Singly Linked List (Generic)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `boolean contains(T key)` using `search`. What does `contains(new Clue("the bridge", 35))` return on the demo's clues, and why?

## 2. Medium

Write `int totalMetres(GenericSinglyLinkedList<Clue> clues)`, outside the list class, using only `count()` and `get(i)`. What is its time complexity, and how would a method inside the class do better?

## 3. Harder

Write a generic static method `<T> GenericSinglyLinkedList<T> copyOf(GenericSinglyLinkedList<T> list)` that makes a new list with the same elements in the same order in O(n). Are the elements copied?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
boolean contains(T key) {
    return search(key) >= 0;
}
```
True: `search` uses `equals`, and a record's `equals` compares place and metres. O(n) time, O(1) space.

</details>

<details>
<summary>Answer 2</summary>

```java
int totalMetres(GenericSinglyLinkedList<Clue> clues) {
    int total = 0;
    for (int i = 0; i < clues.count(); i++) {
        total += clues.get(i).metres();
    }
    return total;
}
```
`count()` is O(n) and `get(i)` is O(i), and both run inside the loop: O(n²) in all. A method inside the class walks once with `current`, adding as it goes: O(n). This is why a linked list is walked, never indexed.

</details>

<details>
<summary>Answer 3</summary>

Walk the original once, keeping a `tail` pointer in the new list so each append is O(1) (inside the class, so the fields are reachable):
```java
static <T> GenericSinglyLinkedList<T> copyOf(GenericSinglyLinkedList<T> list) {
    GenericSinglyLinkedList<T> copy = new GenericSinglyLinkedList<>();
    Node<T> tail = null;
    for (Node<T> current = list.head; current != null; current = current.next) {
        Node<T> node = new Node<>(current.data, null);
        if (tail == null) copy.head = node; else tail.next = node;
        tail = node;
    }
    return copy;
}
```
The nodes are new, but the elements are not: both lists refer to the same objects. That is a shallow copy.

</details>
