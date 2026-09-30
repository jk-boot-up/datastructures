# Problem Statement — Skip List

## The situation

A railway line keeps sixteen stations in order of distance and answers "which station is at kilometre k?", while stations are added and removed.

## The obvious approach, and where it breaks

A sorted linked list inserts cheaply but can only be searched by walking: thirteen hops to reach Marden, and 500 on average for a line of a thousand. A sorted array can be halved but moves everything on every insert.

## What this project must show

- A `SkipList` built by hand: nodes with a key, a value and an array of forward arrows, and a head on every level.
- Search that rides each level and drops down, counting hops: 3 for Marden against 13.
- Insert and remove on every level a station stands on, counting arrows.
- A hand-written, seeded `Coin` (no `java.util.Random`) so heights and counts are the same on every run.
- The unlucky always-tails coin, and a 1,000-station line averaging 9.7 hops.
