# Exercises — Singly Linked List

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `int countOccurrences(String key)` that counts the nodes holding `key`. What are its time and space complexity?

## 2. Medium

Add a `tail` pointer that always refers to the last node, so `insertAtEnd` takes no steps. Which other operations must now keep `tail` up to date?

## 3. Harder

Write `String middle()` that returns the data of the middle node in one pass, without counting first.

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int countOccurrences(String key) {
    int c = 0;
    Node current = head;
    while (current != null) {
        if (current.data.equals(key)) c++;
        current = current.next;
    }
    return c;
}
```
One pass over n nodes: O(n) time, O(1) extra space.

</details>

<details>
<summary>Answer 2</summary>

`insertAtEnd` becomes: if the list is empty, `head = tail = newNode`; otherwise `tail.next = newNode; tail = newNode;`, O(1). `insertAtBeginning` must set `tail` when the list was empty; `insertAtPosition` when the new node is the last; `deleteAtBeginning` when the list becomes empty; `deleteAtEnd`, `deleteAtPosition` and `deleteByKey` when they delete the last node; and `reverse`, which makes the old head the new tail. `deleteAtEnd` is still O(n): the second-to-last node must be found by walking.

</details>

<details>
<summary>Answer 3</summary>

```java
String middle() {
    Node slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;          // one step
        fast = fast.next.next;     // two steps
    }
    return slow == null ? null : slow.data;
}
```
When `fast` reaches the end, `slow` has gone half as far: the middle. One pass, O(n) time, O(1) space. On the six clues it returns "the bridge", position 3.

</details>
