# Exercises — Static Array (Generic, Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int count(T key, int i)` returning how many elements from index i on are equal to `key`. Name its base case and its extra space.

## 2. Medium

Write a recursive `boolean isSorted(int i)` using `compareTo`. How deep does it go on the day names in week order, [Mon, Tue, Wed, Thu, Fri, Sat, Sun]?

## 3. Harder

Write a generic recursive `int findMin(int i)` that returns the index of the smallest element. Then change `Reading.compareTo` to order by day name and say what `findMin` returns on the readings, without changing the array class.

---

## Answers

<details>
<summary>Answer 1</summary>

```java
int count(T key, int i) {
    if (i == n) return 0;                                    // base case: none left
    return (arr[i].equals(key) ? 1 : 0) + count(key, i + 1);
}
```
Called as `count(key, 0)`. The base case is `i == n`. O(n) time and O(n) stack, one frame per element.

</details>

<details>
<summary>Answer 2</summary>

```java
boolean isSorted(int i) {
    if (i >= n - 1) return true;                             // zero or one element left
    if (arr[i].compareTo(arr[i + 1]) > 0) return false;      // a pair out of order
    return isSorted(i + 1);
}
```
The first call checks "Mon" before "Tue": in order, so it calls again. The second checks "Tue" before "Wed": in order, so it calls again. The third finds "Wed" after "Thu" alphabetically and returns false. Depth 3.

</details>

<details>
<summary>Answer 3</summary>

```java
int findMin(int i) {
    if (i == n - 1) return i;                                 // base case: last element
    int restMin = findMin(i + 1);
    return arr[i].compareTo(arr[restMin]) <= 0 ? i : restMin;
}
```
With readings ordered by day name, the smallest is "Fri" (24 C), the first alphabetically. The array class is unchanged: the element decides the order through its own `compareTo`.

</details>
