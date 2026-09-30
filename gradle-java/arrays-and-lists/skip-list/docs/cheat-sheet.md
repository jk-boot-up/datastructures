# Skip List — Cheat Sheet

**A skip list adds express levels on top of a sorted linked list, chosen by tossing coins, so a search rides the express as far as it can and then changes down: about log2(n) hops instead of n, on average.**

![level 1 calls at every station; each level above skips more of them](images/structure.png)

| | |
| --- | --- |
| **What it is** | A skip list is a sorted linked list with extra express levels, chosen by coin tosses, so a search rides the highest level as far as it can, then drops down, taking about log2(n) hops on average. |
| **Everyday picture** | A train line with stopping trains and express trains |
| **Use it when** | You need values kept in order, with fast search, insert and delete, and simple code; You want ordered data that many threads can change at once (the JDK's concurrent skip lists); You want the speed of a balanced tree without writing rotations |
| **Avoid it when** | You need a guaranteed worst case: a skip list is fast on average, and bad luck makes it slow; Memory is tight: the express arrows cost about one extra arrow per value; The data never changes: a sorted array searched by halving is smaller and just as fast |
| **Already in Java** | `java.util.concurrent.ConcurrentSkipListMap` and `ConcurrentSkipListSet` |

## Costs

| Operation | What it does | Steps it takes | Name for that cost |
| --- | --- | --- | --- |
| Search | Ride each level as far as possible without passing, then drop a level | 3 hops for Marden (13 on the stopping line); 9.7 on average at 1,000 stations | O(log n) expected |
| Insert | Search for the spot, toss coins for the height, link in on each level | The search, plus 2 arrows per level | O(log n) expected |
| Remove | Search for it, then unlink it from every level it stands on | The search, plus 1 arrow per level: 4 for Hailey | O(log n) expected |
| Walk in order | Follow the level 1 arrows from the head | One hop per station | O(n), linear |
| Worst case (unlucky coins) | Every station stays on level 1: a plain sorted list | 13 hops for Marden again | O(n) |

## Watch out for

- **Forgetting to record the last station before the target on every level**: Keep a `before` array: while searching, store the last station visited on each level, then link in on each.
- **Linking the arrows in the wrong order**: On each level, first `box.next[l] = before[l].next[l]`, then `before[l].next[l] = box`.
- **Using the same random generator without a seed in tests**: Seed the coin in tests and demos, as `Coin.seeded(2026)` does.
- **Letting the height grow without limit**: Cap the height (`MAX_LEVEL`), about log2 of the largest size you expect.
