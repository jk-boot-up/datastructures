# Exercises — Static Array (Generic, Recursive)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write a recursive `int count_equal(const GenericArray *a, const void *key, CompareFn compare, int i)`. Name its base case and its extra space.

## 2. Medium

Write a recursive `int is_sorted(const GenericArray *a, CompareFn compare, int i)`. How deep does it go on the day names in week order, [Mon, Tue, Wed, Thu, Fri, Sat, Sun]?

## 3. Harder

Write a recursive `int find_min(const GenericArray *a, CompareFn compare, int i)`. Then write a comparison function that orders readings by day name, and say what `find_min` returns on the readings without changing `generic_array.c`.

---

## Answers

<details>
<summary>Answer 1</summary>

```c
int count_equal(const GenericArray *a, const void *key, CompareFn compare, int i) {
    if (i == a->n) return 0;                                        /* base case */
    return (compare(element_at(a, i), key) == 0) + count_equal(a, key, compare, i + 1);
}
```
Called with `i = 0`. The base case is `i == n`. O(n) time and O(n) stack, one frame per element.

</details>

<details>
<summary>Answer 2</summary>

```c
int is_sorted(const GenericArray *a, CompareFn compare, int i) {
    if (i >= a->n - 1) return 1;                                     /* zero or one element left */
    if (compare(element_at(a, i), element_at(a, i + 1)) > 0) return 0;
    return is_sorted(a, compare, i + 1);
}
```
The first call finds "Mon" before "Tue", the second "Tue" before "Wed", and the third finds "Wed" after "Thu" and returns 0. Depth 3.

</details>

<details>
<summary>Answer 3</summary>

```c
int find_min(const GenericArray *a, CompareFn compare, int i) {
    if (i == a->n - 1) return i;
    int rest_min = find_min(a, compare, i + 1);
    return compare(element_at(a, i), element_at(a, rest_min)) <= 0 ? i : rest_min;
}

int compare_reading_by_day(const void *a, const void *b) {
    return strcmp(((const Reading *) a)->day, ((const Reading *) b)->day);
}
```
With readings ordered by day name, the smallest is "Fri" (24 C), the first alphabetically. The array code is unchanged: the comparison function decides the order.

</details>
