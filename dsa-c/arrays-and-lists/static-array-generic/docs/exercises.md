# Exercises — Static Array (Generic)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `int count_equal(GenericArray *a, const void *key, CompareFn compare)`, which counts the elements equal to `key`. Why does it need the comparison function?

## 2. Medium

Write `int compare_reading_by_day(const void *a, const void *b)` that orders readings by day name. What does `find_max` then return on the week, and did you change `generic_array.c`?

## 3. Harder

Sort the readings by temperature with the standard library's `qsort`, then find 24 C with `bsearch`. What do `qsort` and `bsearch` need that `GenericArray` already has?

---

## Answers

<details>
<summary>Answer 1</summary>

```c
int count_equal(GenericArray *a, const void *key, CompareFn compare) {
    int count = 0;
    for (int i = 0; i < a->n; i++) {
        if (compare(element_at(a, i), key) == 0) count++;
    }
    return count;
}
```
The array does not know what its bytes mean, so only the comparison function can say whether two elements are equal. O(n) time, O(1) space.

</details>

<details>
<summary>Answer 2</summary>

```c
int compare_reading_by_day(const void *a, const void *b) {
    const Reading *x = a, *y = b;
    return strcmp(x->day, y->day);
}
```
`find_max(&week, compare_reading_by_day)` returns the reading whose day name is last alphabetically, "Wed" (19 C). `generic_array.c` is unchanged: the order lives entirely in the function passed in.

</details>

<details>
<summary>Answer 3</summary>

```c
qsort(week.data, week.n, week.elem_size, compare_reading);
Reading key = {"", 24};
Reading *hit = bsearch(&key, week.data, week.n, week.elem_size, compare_reading);
```
Both take the same three things `GenericArray` keeps: the block (`data`), the number of elements and the element size, plus a comparison function. After sorting, `hit` points at "Fri 24 C". O(n log n) to sort, O(log n) to search.

</details>
