# Exercises — Dynamic Array (Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int count(String key, int i)` returning how many elements from index i on are equal to `key`. What is its base case, and its extra space?

## 2. Medium

Rewrite the recursive `copy(i, newArr)` as a loop. Which part of the recursion became the loop condition, and which became `i++`?

## 3. Harder

Write a recursive copy that needs only O(log n) stack: copy the left half and the right half of a range by calling itself on each. Why is the depth logarithmic although every element is still copied?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int count(String key, int i) {
    if (i == size) return 0;                              // base case
    return (arr[i].equals(key) ? 1 : 0) + count(key, i + 1);
}
```
The base case is `i == size`. O(n) time and O(n) stack: one frame per element.

</details>

<details>
<summary>Answer 2</summary>

```java
for (int i = 0; i < size; i++) {
    newArr[i] = arr[i];
}
```
The base case `i == size` became the loop condition `i < size` (its opposite: keep going while the base case is not reached), and the call `copy(i + 1, newArr)` became `i++`. The loop needs O(1) extra space.

</details>

<details>
<summary>Answer 3</summary>

```java
void copyRange(int low, int high, String[] newArr) {
    if (low > high) return;                               // empty range
    int mid = low + (high - low) / 2;
    newArr[mid] = arr[mid];
    copyRange(low, mid - 1, newArr);
    copyRange(mid + 1, high, newArr);
}
```
Called as `copyRange(0, size - 1, newArr)`. Each call halves the range, and only one chain of calls is waiting at a time, so the depth is about log2(n): 20 for a million elements. The time is still O(n), because every element is copied once.

</details>
