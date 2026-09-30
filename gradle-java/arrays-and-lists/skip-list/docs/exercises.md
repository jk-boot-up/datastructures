# Exercises — Skip List

Try each one before opening its answer. They start easy and get harder.

## 1. Easy

With the regular line of act two, which stations does a search for Kelby (km 48) visit, and how many hops does it take?

## 2. Medium

Add `String firstAtOrAfter(int km)`, which returns the name of the first station at or beyond `km` (or null). Why does it need hardly any new code?

## 3. Harder

Each station is on level 2 with probability 1/2, level 3 with 1/4, and so on. Show that the expected total number of arrows is about 2n, and explain why a search visits about 2 stations per level.

---

## Answers

<details>
<summary>Answer 1</summary>

From the head on level 4 it rides to Hailey (1 hop); Pinner is beyond 48, so it drops to level 3 and stays (Lydd, km 53, is beyond); drops to level 2 and rides to Jarrow (2 hops); drops to level 1 and rides one more station to Kelby (3 hops). About the same as Marden, where the stopping line would need 11.

</details>

<details>
<summary>Answer 2</summary>

```java
public String firstAtOrAfter(int km) {
    Node x = head;
    for (int level = levels - 1; level >= 0; level--) {
        while (x.next[level] != null && x.next[level].km < km) x = x.next[level];
    }
    x = x.next[0];
    return x == null ? null : x.station;
}
```
It is `find` without the final equality check: the search already stops just before the first station that is not less than `km`, which is exactly the one wanted. Range questions like this are why skip lists, like trees, keep their values in order.

</details>

<details>
<summary>Answer 3</summary>

A station has 1 arrow, plus 1 more with probability 1/2, plus another with 1/4, and so on: 1 + 1/2 + 1/4 + ... = 2 arrows on average, so 2n in total (the demo counts 2,031 for 1,000 stations). Looking backwards along a search path, each step either goes up a level (if the station is taller, probability 1/2) or left along the same level (probability 1/2), so on average about 2 steps per level, over about log2(n) levels: roughly 2 log2(n) hops, which for 1,000 is under 20, and the measured average is 9.7.

</details>
