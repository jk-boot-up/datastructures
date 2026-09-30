# Exercises — Dynamic Array (Generic, Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `boolean contains(T key, int i)`. What are its two base cases?

## 2. Medium

Write a recursive `int count(T key, int i)` that counts the elements equal to `key`. How deep does it go on the 9-song playlist, whatever the key?

## 3. Harder

Rewrite the recursive `copy` so that its depth is O(log n): copy the middle reference, then the left half and the right half by recursive calls. Why is the time still O(n)?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
boolean contains(T key, int i) {
    if (i == size) return false;                 // base case: nothing left
    if (arr[i].equals(key)) return true;         // base case: found
    return contains(key, i + 1);
}
```
Called as `contains(key, 0)`. O(n) time and O(n) stack.

</details>

<details>
<summary>Answer 2</summary>

```java
int count(T key, int i) {
    if (i == size) return 0;
    return (arr[i].equals(key) ? 1 : 0) + count(key, i + 1);
}
```
It always visits every element, so it goes 9 calls deep on 9 songs, found or not: O(n) time and stack.

</details>

<details>
<summary>Answer 3</summary>

```java
void copyRange(int low, int high, T[] newArr) {
    if (low > high) return;
    int mid = low + (high - low) / 2;
    newArr[mid] = arr[mid];
    copyRange(low, mid - 1, newArr);
    copyRange(mid + 1, high, newArr);
}
```
Each call halves the range and only one chain of calls waits at a time, so the depth is about log2(n): 20 for a million. Every reference is still copied once, so the time is O(n).

</details>
