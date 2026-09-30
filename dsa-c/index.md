# Data Structures in C — Course Index

Every classic data structure as its own C17 project with a Makefile, built from scratch and explained for complete beginners, each with a narrated video and an interactive animation. Most structures also have a generic version, written with `void *` elements so it holds any type. The plan is in [PLAN.md](PLAN.md); new to the subject? Begin with [Start here](START-HERE.md).

## Progress

**7 of 156 done**, 0 in progress, 149 planned.

| Category | Done | In progress | Planned | Total |
| --- | ---: | ---: | ---: | ---: |
| [Arrays and lists](#arrays-and-lists) | 7 | 0 | 17 | 24 |
| [Stacks and queues](#stacks-and-queues) | 0 | 0 | 8 | 8 |
| [Hashing](#hashing) | 0 | 0 | 18 | 18 |
| [Trees](#trees) | 0 | 0 | 49 | 49 |
| [Heaps and priority queues](#heaps-and-priority-queues) | 0 | 0 | 24 | 24 |
| [Graphs](#graphs) | 0 | 0 | 14 | 14 |
| [Strings and text](#strings-and-text) | 0 | 0 | 5 | 5 |
| [Probabilistic](#probabilistic) | 0 | 0 | 6 | 6 |
| [Specialised](#specialised) | 0 | 0 | 8 | 8 |
| **All** | **7** | **0** | **149** | **156** |

## Arrays and lists

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 1 | [Static Array](arrays-and-lists/static-array/README.md) | A week of daily temperatures, one numbered slot per day | ✅ done | [README](arrays-and-lists/static-array/README.md) · [explained](arrays-and-lists/static-array/docs/static-array-explained.md) · [animation](arrays-and-lists/static-array/docs/animation.html) · [cheat sheet](arrays-and-lists/static-array/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/static-array/docs/youtube.md) |
| 2 | [Static Array (Recursive)](arrays-and-lists/static-array-recursive/README.md) | The same operations, written recursively | ✅ done | [README](arrays-and-lists/static-array-recursive/README.md) · [explained](arrays-and-lists/static-array-recursive/docs/static-array-recursive-explained.md) · [animation](arrays-and-lists/static-array-recursive/docs/animation.html) · [cheat sheet](arrays-and-lists/static-array-recursive/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/static-array-recursive/docs/youtube.md) |
| 3 | [Static Array (Generic)](arrays-and-lists/static-array-generic/README.md) | The same structure for any type: void * elements and an element size | ✅ done | [README](arrays-and-lists/static-array-generic/README.md) · [explained](arrays-and-lists/static-array-generic/docs/static-array-generic-explained.md) · [animation](arrays-and-lists/static-array-generic/docs/animation.html) · [cheat sheet](arrays-and-lists/static-array-generic/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/static-array-generic/docs/youtube.md) |
| 4 | [Static Array (Generic, Recursive)](arrays-and-lists/static-array-generic-recursive/README.md) | Generic and recursive: void * elements, operations written recursively | ✅ done | [README](arrays-and-lists/static-array-generic-recursive/README.md) · [explained](arrays-and-lists/static-array-generic-recursive/docs/static-array-generic-recursive-explained.md) · [animation](arrays-and-lists/static-array-generic-recursive/docs/animation.html) · [cheat sheet](arrays-and-lists/static-array-generic-recursive/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/static-array-generic-recursive/docs/youtube.md) |
| 5 | [Dynamic Array](arrays-and-lists/dynamic-array/README.md) | A music playlist that grows as songs are added | ✅ done | [README](arrays-and-lists/dynamic-array/README.md) · [explained](arrays-and-lists/dynamic-array/docs/dynamic-array-explained.md) · [animation](arrays-and-lists/dynamic-array/docs/animation.html) · [cheat sheet](arrays-and-lists/dynamic-array/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/dynamic-array/docs/youtube.md) |
| 6 | [Dynamic Array (Recursive)](arrays-and-lists/dynamic-array-recursive/README.md) | The same operations, written recursively | ✅ done | [README](arrays-and-lists/dynamic-array-recursive/README.md) · [explained](arrays-and-lists/dynamic-array-recursive/docs/dynamic-array-recursive-explained.md) · [animation](arrays-and-lists/dynamic-array-recursive/docs/animation.html) · [cheat sheet](arrays-and-lists/dynamic-array-recursive/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/dynamic-array-recursive/docs/youtube.md) |
| 7 | [Dynamic Array (Generic)](arrays-and-lists/dynamic-array-generic/README.md) | The same structure for any type: void * elements and an element size | ✅ done | [README](arrays-and-lists/dynamic-array-generic/README.md) · [explained](arrays-and-lists/dynamic-array-generic/docs/dynamic-array-generic-explained.md) · [animation](arrays-and-lists/dynamic-array-generic/docs/animation.html) · [cheat sheet](arrays-and-lists/dynamic-array-generic/docs/cheat-sheet.md) · [YouTube](arrays-and-lists/dynamic-array-generic/docs/youtube.md) |
| 8 | Dynamic Array (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 9 | Singly Linked List | A treasure hunt: each clue says where the next one is | planned |  |
| 10 | Singly Linked List (Recursive) | The same operations, written recursively | planned |  |
| 11 | Singly Linked List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 12 | Singly Linked List (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 13 | Doubly Linked List | A photo viewer with Previous and Next | planned |  |
| 14 | Doubly Linked List (Recursive) | The same operations, written recursively | planned |  |
| 15 | Doubly Linked List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 16 | Doubly Linked List (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 17 | Circular Linked List | Taking turns in a board game, round and round | planned |  |
| 18 | Circular Linked List (Recursive) | The same operations, written recursively | planned |  |
| 19 | Circular Linked List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 20 | Circular Linked List (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 21 | Skip List | A train line with stopping and express services | planned |  |
| 22 | Skip List (Recursive) | The same operations, written recursively | planned |  |
| 23 | Skip List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 24 | Skip List (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |

## Stacks and queues

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 25 | Stack | A text editor's undo, and a browser's back button | planned |  |
| 26 | Stack (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 27 | Queue | Documents waiting at an office printer | planned |  |
| 28 | Queue (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 29 | Deque | A hospital waiting room where urgent cases join at the front | planned |  |
| 30 | Deque (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 31 | Circular Buffer | A dashcam that keeps only the last minute of footage | planned |  |
| 32 | Circular Buffer (Generic) | The same structure for any type: void * elements and an element size | planned |  |

## Hashing

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 33 | Hash Table with Separate Chaining | A library's shelves, one shelf per first letter | planned |  |
| 34 | Hash Table with Separate Chaining (Recursive) | The same operations, written recursively | planned |  |
| 35 | Hash Table with Separate Chaining (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 36 | Hash Table with Separate Chaining (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 37 | Hash Table with Open Addressing | A car park: if your bay is taken, try the next one | planned |  |
| 38 | Hash Table with Open Addressing (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 39 | Cuckoo Hash Table | Two possible lockers per coat; a newcomer can move one along | planned |  |
| 40 | Cuckoo Hash Table (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 41 | Hash Set | The guest list at a party door: in or not | planned |  |
| 42 | Hash Set (Recursive) | The same operations, written recursively | planned |  |
| 43 | Hash Set (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 44 | Hash Set (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 45 | LRU Cache | A small desk that keeps only the books you used most recently | planned |  |
| 46 | LRU Cache (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 47 | Consistent Hash Ring | Sharing out the library's books when a new branch opens | planned |  |
| 48 | Consistent Hash Ring (Recursive) | The same operations, written recursively | planned |  |
| 49 | Consistent Hash Ring (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 50 | Consistent Hash Ring (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |

## Trees

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 51 | Binary Tree | A family tree, and the ways to walk it | planned |  |
| 52 | Binary Tree (Recursive) | The same operations, written recursively | planned |  |
| 53 | Binary Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 54 | Binary Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 55 | Binary Search Tree | A guessing game: higher or lower? | planned |  |
| 56 | Binary Search Tree (Recursive) | The same operations, written recursively | planned |  |
| 57 | Binary Search Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 58 | Binary Search Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 59 | AVL Tree | The same guessing game, kept fair as names are added | planned |  |
| 60 | AVL Tree (Recursive) | The same operations, written recursively | planned |  |
| 61 | AVL Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 62 | AVL Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 63 | Red-Black Tree | A dictionary that must stay fast whatever order words arrive in | planned |  |
| 64 | Red-Black Tree (Recursive) | The same operations, written recursively | planned |  |
| 65 | Red-Black Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 66 | Red-Black Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 67 | Splay Tree | A phone that brings your most-called contacts to the top | planned |  |
| 68 | Splay Tree (Recursive) | The same operations, written recursively | planned |  |
| 69 | Splay Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 70 | Splay Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 71 | Treap | A queue of tickets where a coin toss keeps the line balanced | planned |  |
| 72 | Treap (Recursive) | The same operations, written recursively | planned |  |
| 73 | Treap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 74 | Treap (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 75 | B-Tree | A library catalogue stored in drawers, many cards per drawer | planned |  |
| 76 | B-Tree (Recursive) | The same operations, written recursively | planned |  |
| 77 | B-Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 78 | B-Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 79 | B+ Tree | Bank transactions between two dates, read in order | planned |  |
| 80 | B+ Tree (Recursive) | The same operations, written recursively | planned |  |
| 81 | B+ Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 82 | B+ Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 83 | Trie | A phone keyboard suggesting the rest of a word | planned |  |
| 84 | Trie (Recursive) | The same operations, written recursively | planned |  |
| 85 | Radix Tree | The same suggestions, with shared beginnings stored once | planned |  |
| 86 | Radix Tree (Recursive) | The same operations, written recursively | planned |  |
| 87 | Ternary Search Tree | A spell-checker's word list, using less memory | planned |  |
| 88 | Ternary Search Tree (Recursive) | The same operations, written recursively | planned |  |
| 89 | Segment Tree | Rainfall between any two days of the year, with corrections | planned |  |
| 90 | Segment Tree (Recursive) | The same operations, written recursively | planned |  |
| 91 | Fenwick Tree | A running total of steps walked, updated every day | planned |  |
| 92 | Interval Tree | Which meetings clash with this one? | planned |  |
| 93 | Interval Tree (Recursive) | The same operations, written recursively | planned |  |
| 94 | Interval Tree (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 95 | Interval Tree (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 96 | k-d Tree | The nearest bus stop to where you are standing | planned |  |
| 97 | k-d Tree (Recursive) | The same operations, written recursively | planned |  |
| 98 | Quadtree | Finding every tree in a park on a map, zooming in | planned |  |
| 99 | Quadtree (Recursive) | The same operations, written recursively | planned |  |

## Heaps and priority queues

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 100 | Binary Heap | A hospital emergency department: the most urgent first | planned |  |
| 101 | Binary Heap (Recursive) | The same operations, written recursively | planned |  |
| 102 | Binary Heap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 103 | Binary Heap (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 104 | d-ary Heap | The same triage, with fewer levels to climb | planned |  |
| 105 | d-ary Heap (Recursive) | The same operations, written recursively | planned |  |
| 106 | d-ary Heap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 107 | d-ary Heap (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 108 | Leftist Heap | Merging two hospitals' waiting lists | planned |  |
| 109 | Leftist Heap (Recursive) | The same operations, written recursively | planned |  |
| 110 | Leftist Heap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 111 | Leftist Heap (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 112 | Pairing Heap | A fast, simple mergeable waiting list | planned |  |
| 113 | Pairing Heap (Recursive) | The same operations, written recursively | planned |  |
| 114 | Pairing Heap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 115 | Pairing Heap (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 116 | Binomial Heap | Merging tournaments of players in logarithmic time | planned |  |
| 117 | Binomial Heap (Recursive) | The same operations, written recursively | planned |  |
| 118 | Binomial Heap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 119 | Binomial Heap (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 120 | Fibonacci Heap | A sat-nav improving its best-known times as roads are checked | planned |  |
| 121 | Fibonacci Heap (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 122 | Indexed Priority Queue | An airport board where a flight's delay changes its place | planned |  |
| 123 | Indexed Priority Queue (Generic) | The same structure for any type: void * elements and an element size | planned |  |

## Graphs

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 124 | Adjacency Matrix | Which of five friends know each other | planned |  |
| 125 | Adjacency Matrix (Recursive) | The same operations, written recursively | planned |  |
| 126 | Adjacency Matrix (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 127 | Adjacency Matrix (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 128 | Adjacency List | A city's one-way streets | planned |  |
| 129 | Adjacency List (Recursive) | The same operations, written recursively | planned |  |
| 130 | Adjacency List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 131 | Adjacency List (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
| 132 | Edge List | Every road with its length, sorted for planning cables | planned |  |
| 133 | Edge List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 134 | Disjoint Set (Union-Find) | Islands joined by bridges: are these two towns connected? | planned |  |
| 135 | Disjoint Set (Union-Find) (Recursive) | The same operations, written recursively | planned |  |
| 136 | Disjoint Set (Union-Find) (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 137 | Disjoint Set (Union-Find) (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |

## Strings and text

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 138 | Gap Buffer | Typing in a text editor | planned |  |
| 139 | Rope | Editing a whole novel without copying it each time | planned |  |
| 140 | Rope (Recursive) | The same operations, written recursively | planned |  |
| 141 | Suffix Array | Finding a phrase anywhere in a book | planned |  |
| 142 | Inverted Index | A book's index at the back, built by a program | planned |  |

## Probabilistic

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 143 | Bloom Filter | Has this web address already been visited by the crawler? | planned |  |
| 144 | Bloom Filter (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 145 | Count-Min Sketch | Which hashtags are trending right now? | planned |  |
| 146 | Count-Min Sketch (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 147 | HyperLogLog | How many different people visited a website today? | planned |  |
| 148 | HyperLogLog (Generic) | The same structure for any type: void * elements and an element size | planned |  |

## Specialised

| # | Data structure | Worked example | Status | Material |
| ---: | --- | --- | --- | --- |
| 149 | Bit Set | Which seats in a cinema row are booked | planned |  |
| 150 | Sparse Matrix | Who follows whom among a million users, mostly nobody | planned |  |
| 151 | Sparse Table | The coldest day in any range of a year's temperatures | planned |  |
| 152 | Sparse Table (Recursive) | The same operations, written recursively | planned |  |
| 153 | Persistent List | Every earlier version of a document, kept for free | planned |  |
| 154 | Persistent List (Recursive) | The same operations, written recursively | planned |  |
| 155 | Persistent List (Generic) | The same structure for any type: void * elements and an element size | planned |  |
| 156 | Persistent List (Generic, Recursive) | Generic and recursive: void * elements, operations written recursively | planned |  |
