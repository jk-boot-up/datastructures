# Exercises — Static Array (Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int countAbove(int limit, int i)` that returns how many elements from index i onwards are greater than `limit`. What are its base case, its time and its extra space?

## 2. Medium

Write a recursive `boolean isSorted(int i)` that returns true when `arr[i..n-1]` is in ascending order. How deep does it go on the week, and how deep on [5, 3, 8, 9]?

## 3. Harder

Rewrite recursive `binarySearch(key, low, high)` as a loop. Why can every tail-recursive method be rewritten like this, and what does it save?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int countAbove(int limit, int i) {
    if (i == n) return 0;                              // base case: no elements left
    return (arr[i] > limit ? 1 : 0) + countAbove(limit, i + 1);
}
```
Called as `countAbove(limit, 0)`. The base case is `i == n`. O(n) time, and O(n) extra space, because n calls wait on the stack; the loop version uses O(1).

</details>

<details>
<summary>Answer 2</summary>

```java
boolean isSorted(int i) {
    if (i >= n - 1) return true;                       // zero or one element: sorted
    if (arr[i] > arr[i + 1]) return false;             // found a pair out of order
    return isSorted(i + 1);
}
```
On the week, 21 > 23 is false, then 23 > 19 is true, so it returns false at depth 2. On [5, 3, 8, 9] it returns false at once, depth 1. A sorted array of n elements goes n - 1 deep.

</details>

<details>
<summary>Answer 3</summary>

```java
int binarySearch(int key) {
    int low = 0, high = n - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == key) return mid;
        if (arr[mid] < key) low = mid + 1;
        else high = mid - 1;
    }
    return -1;
}
```
In tail recursion the call is the last thing the method does, so nothing is left to do after it returns: the call can be replaced by assigning the new parameters (`low = mid + 1`) and jumping back to the start, which is a loop. It saves the O(log n) stack frames, reducing extra space to O(1).

</details>
