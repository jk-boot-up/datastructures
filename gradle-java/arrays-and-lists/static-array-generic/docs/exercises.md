# Exercises — Static Array (Generic)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `int count(T key)`, which returns how many elements are equal to `key`. Why must it use `equals` and not `==`?

## 2. Medium

Make `Reading` order by day name instead of temperature. What does `findMax` then return on the week, and did you change `GenericStaticArray`?

## 3. Harder

Write `boolean isSorted()`, true when the elements are in ascending order by `compareTo`. Then explain why `binarySearch` may give a wrong answer if `isSorted()` is false.

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int count(T key) {
    int c = 0;
    for (int i = 0; i < n; i++) {
        if (arr[i].equals(key)) c++;
    }
    return c;
}
```
`==` compares references: two equal strings or readings created separately are different objects, so `==` would miss them. O(n) time, O(1) space.

</details>

<details>
<summary>Answer 2</summary>

```java
public int compareTo(Reading other) {
    return day.compareTo(other.day);
}
```
`findMax` now returns the reading whose day name is last alphabetically, "Wed" (19 C). `GenericStaticArray` is unchanged: the algorithm asks the element for its order, so changing the element's `compareTo` changes the answer.

</details>

<details>
<summary>Answer 3</summary>

```java
boolean isSorted() {
    for (int i = 1; i < n; i++) {
        if (arr[i - 1].compareTo(arr[i]) > 0) return false;
    }
    return true;
}
```
Binary search discards the half that "cannot" hold the key, which is only true when the elements are sorted. On unsorted data it may discard the half that holds the key and return -1. O(n) time, O(1) space.

</details>
