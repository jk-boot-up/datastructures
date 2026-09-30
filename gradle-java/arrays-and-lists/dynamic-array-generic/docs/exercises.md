# Exercises — Dynamic Array (Generic)

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

Write `boolean contains(T key)` using the methods the class already has. What does `contains(new Song("Echoes", 201))` return after the demo's act three, and why?

## 2. Medium

Write `boolean deleteByKey(T key)`, which deletes the first element equal to `key`. What does it cost?

## 3. Harder

Add a constructor `GenericDynamicArray(T[] values)` that builds the array from an existing array with one resize at most. How many references does it copy for 9 songs, and how does that compare with appending them one by one from capacity 4?

---

## Answers

<details>
<summary>Answer 1</summary>

```java
boolean contains(T key) {
    return linearSearch(key) >= 0;
}
```
It returns true: `linearSearch` uses `equals`, and a record's `equals` compares title and length, so a new `Song("Echoes", 201)` equals the one in the playlist. O(n) time, O(1) space.

</details>

<details>
<summary>Answer 2</summary>

```java
boolean deleteByKey(T key) {
    int i = linearSearch(key);
    if (i < 0) return false;
    deleteAt(i);
    return true;
}
```
One comparison per element examined, plus one shift per element after the one deleted: a single pass over the array, O(n).

</details>

<details>
<summary>Answer 3</summary>

```java
GenericDynamicArray(T[] values) {
    this(values.length);
    for (int i = 0; i < values.length; i++) {
        arr[i] = values[i];
    }
    size = values.length;
}
```
It allocates exactly 9 places and copies 9 references once. Appending one by one from capacity 4 copies 4 + 8 = 12 references in two resizes, then 9 writes. Knowing the size in advance avoids every resize.

</details>
