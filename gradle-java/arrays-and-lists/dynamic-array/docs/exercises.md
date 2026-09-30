# Exercises — Dynamic Array

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

A dynamic array starts with capacity 4 and doubles. What are its size and capacity after 10 appends, and how many resizes happened?

## 2. Medium

Write `boolean deleteByKey(String key)`, which finds the first element equal to `key` and deletes it, returning whether it found one. Use the methods the class already has.

## 3. Harder

Change `newCapacity` to grow by one and a half times (`capacity + capacity / 2`, as Java's `ArrayList` does). Starting from 4 places, what capacities does it pass through for the first 20 appends, and why might Java prefer 1.5 to 2?

---

## Answers

<details>
<summary>Answer 1</summary>

Size 10, capacity 16. It resized at the 5th append (4 to 8) and at the 9th append (8 to 16): two resizes, copying 4 + 8 = 12 values.

</details>

<details>
<summary>Answer 2</summary>

```java
public boolean deleteByKey(String key) {
    int i = linearSearch(key);
    if (i < 0) return false;
    deleteAt(i);
    return true;
}
```
It costs one comparison per element looked at, plus one shift per element after the one deleted: one pass over the array either way, O(n).

</details>

<details>
<summary>Answer 3</summary>

4, 6, 9, 13, 19, 28. Growing by 1.5 resizes a little more often, so it copies somewhat more in total, but it leaves less capacity unused: at worst about a third of the places are spare instead of a half. Java chose to waste less memory at the price of a few more copies. Both keep adding at an amortised one step, because both grow by a factor.

</details>
