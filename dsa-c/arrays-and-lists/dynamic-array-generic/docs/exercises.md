# Exercises — Dynamic Array (Generic)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `int contains(const GenericDynamicArray *a, const void *key, CompareFn compare)` using `linear_search`. What does it return for `Song {"Echoes", 201}` on the demo's playlist, and why?

## 2. Medium

Write `int delete_by_key(GenericDynamicArray *a, const void *key, CompareFn compare)`, deleting the first element equal to `key`. What does it cost?

## 3. Harder

Rewrite `resize` using `realloc`. What can `realloc` do that the copying loop cannot, and what must you be careful of when it fails?

---

## Answers

<details>
<summary>Answer 1</summary>

```c
int contains(const GenericDynamicArray *a, const void *key, CompareFn compare) {
    return linear_search(a, key, compare) >= 0;
}
```
It returns 1: `compare_song` compares title and length, and a song with that title and length is in the playlist. O(n) time, O(1) space.

</details>

<details>
<summary>Answer 2</summary>

```c
int delete_by_key(GenericDynamicArray *a, const void *key, CompareFn compare) {
    int i = linear_search(a, key, compare);
    if (i < 0) return 0;
    unsigned char gone[64];                 /* room for one element; assumes elem_size <= 64 */
    delete_at(a, i, gone);
    return 1;
}
```
One comparison per element examined, plus one shift per element after the one deleted: one pass, O(n). A general version would `malloc(a->elem_size)` for the deleted element instead of a fixed buffer.

</details>

<details>
<summary>Answer 3</summary>

```c
static Status resize(GenericDynamicArray *a, int new_capacity) {
    unsigned char *bigger = realloc(a->data, (size_t) new_capacity * a->elem_size);
    if (bigger == NULL) return STATUS_NO_MEMORY;   /* a->data is still valid */
    a->data = bigger;
    a->capacity = new_capacity;
    return STATUS_OK;
}
```
`realloc` may grow the block in place when the memory after it is free, copying nothing. If it fails it returns `NULL` and leaves the old block alone, so the result must go into a new variable: `a->data = realloc(a->data, ...)` would lose the only pointer to the old block.

</details>
