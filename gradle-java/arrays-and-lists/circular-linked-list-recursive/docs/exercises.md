# Exercises — Circular Linked List (Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `boolean contains(Node node, String key)` for one lap starting at `node`. What are its base cases?

## 2. Medium

Write a recursive `String displayFrom(Node start, Node node)` that displays one lap starting at any node, not just the first. What is the base case now?

## 3. Harder

Rewrite `winnerPosition(n, k)` as a loop. Why can every tail-recursive or simple linear recursion like this be turned into a loop, and what does it save?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
boolean contains(Node node, String key) {
    if (node.data.equals(key)) return true;      // found
    if (node == last) return false;              // the lap is over
    return contains(node.next, key);
}
```
Called as `contains(last.next, key)` on a non-empty list. O(n) time and stack.

</details>

<details>
<summary>Answer 2</summary>

```java
String displayFrom(Node start, Node node) {
    if (node.next == start) return node.data;             // the next node is where we began
    return node.data + " -> " + displayFrom(start, node.next);
}
```
Called as `displayFrom(n, n)`. The base case is the node whose next is the starting node, because any node can be the start of a lap.

</details>

<details>
<summary>Answer 3</summary>

```java
int winnerPosition(int n, int k) {
    int j = 0;                            // J(1)
    for (int m = 2; m <= n; m++) {
        j = (j + k) % m;                  // J(m) from J(m - 1)
    }
    return j;
}
```
The recursion computes J(n) from J(n - 1), so the values can be built upwards from J(1) in a loop. It saves the n stack frames: O(1) extra space, and no StackOverflowError for a million players.

</details>
