# Exercises — Static Array

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `int countAbove(int limit)` that returns how many elements are greater than `limit`. What is its time and space complexity?

## 2. Medium

Write `void insertSorted(int x)`, which inserts `x` into an array that is sorted ascending so that it stays sorted. How many shifts does inserting 22 into [19, 20, 21, 23, 24, 25, 26] take?

## 3. Harder

Binary search in this project returns any index holding the key. Write `int firstOccurrence(int key)` that returns the lowest index holding `key` in a sorted array with duplicates, still in O(log n).

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int countAbove(int limit) {
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (arr[i] > limit) count++;
    }
    return count;
}
```
One pass over n elements: O(n) time, O(1) extra space (one counter).

</details>

<details>
<summary>Answer 2</summary>

```java
void insertSorted(int x) {
    if (n == arr.length) throw new IllegalStateException("overflow");
    int i = n - 1;
    while (i >= 0 && arr[i] > x) {
        arr[i + 1] = arr[i];
        i--;
    }
    arr[i + 1] = x;
    n++;
}
```
It shifts every element greater than x one place right, starting from the end: 23, 24, 25 and 26, four shifts, then stores 22 at index 3. This is also the inner step of insertion sort.

</details>

<details>
<summary>Answer 3</summary>

```java
int firstOccurrence(int key) {
    int low = 0, high = n - 1, result = -1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == key) {
            result = mid;          // remember it, but keep looking to the left
            high = mid - 1;
        } else if (arr[mid] < key) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return result;
}
```
When the middle equals the key, it is recorded and the search continues in the left half, so the loop still halves the range each time: O(log n).

</details>
