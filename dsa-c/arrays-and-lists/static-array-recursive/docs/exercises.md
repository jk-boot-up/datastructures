# Exercises — Static Array (Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int count_above(StaticArray *a, int limit, int i)` that returns how many elements from index i onwards are greater than `limit`. What are its base case, its time and its extra space?

## 2. Medium

Write a recursive `int is_sorted(StaticArray *a, int i)`, returning 1 or 0, that returns true when `arr[i..n-1]` is in ascending order. How deep does it go on the week, and how deep on [5, 3, 8, 9]?

## 3. Harder

Rewrite the recursive `binary_search_range(a, key, low, high)` as a loop. Why can every tail-recursive function be rewritten like this, and what does it save?

---

## Answers

<details>
<summary>Answer 1</summary>

```c
int count_above(StaticArray *a, int limit, int i) {
    if (i == a->n) return 0;                           /* base case: no elements left */
    return (a->arr[i] > limit ? 1 : 0) + count_above(a, limit, i + 1);
}
```
Called as `count_above(&a, limit, 0)`. The base case is `i == n`. O(n) time, and O(n) extra space, because n calls wait on the stack; the loop version uses O(1).

</details>

<details>
<summary>Answer 2</summary>

```c
int is_sorted(StaticArray *a, int i) {
    if (i >= a->n - 1) return 1;                       /* zero or one element: sorted */
    if (a->arr[i] > a->arr[i + 1]) return 0;           /* found a pair out of order */
    return is_sorted(a, i + 1);
}
```
On the week, 21 > 23 is false, then 23 > 19 is true, so it returns 0 at depth 2. On [5, 3, 8, 9] it returns 0 at once, depth 1. A sorted array of n elements goes n - 1 deep.

</details>

<details>
<summary>Answer 3</summary>

```c
int binary_search(StaticArray *a, int key) {
    int low = 0, high = a->n - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (a->arr[mid] == key) return mid;
        if (a->arr[mid] < key) low = mid + 1;
        else high = mid - 1;
    }
    return -1;
}
```
In tail recursion the call is the last thing the function does, so nothing is left to do after it returns: the call can be replaced by assigning the new parameters (`low = mid + 1`) and jumping back to the start, which is a loop. It saves the O(log n) stack frames, reducing extra space to O(1).

</details>
