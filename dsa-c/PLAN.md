# Data Structures in C — Plan

A teaching course: every classic data structure as its own small C17 project with a
Makefile, built from scratch, explained for beginners, with a narrated video and an
interactive animation. It is the C edition of the Java course in `../gradle-java`, with the
same catalogue, the same teaching standard and the same variants, and everything lives inside
this folder.

Progress is tracked in [`index.md`](index.md) ([`index.html`](index.html)), which is
regenerated every time a project is built.

## 1. What each project contains

Every data structure is one self-contained C project in its own folder,
`<category>/<slug>/`. None of them depends on another, and none uses code from elsewhere in
the repository: every implementation is written fresh.

| Part | What it is |
| --- | --- |
| `src/<module>.h`, `src/<module>.c` | The data structure, implemented from scratch in plain C17 (iteratively or recursively, for one element type or for any type through `void *`): pure textbook code, with nothing added for measuring |
| `src/demo.c` | The demo: a `main` that tells the five acts with `printf` |
| `tests/` | The tests, on a hand-written harness (`tests/check.h`): the operations and the edge cases (empty, one element, full, duplicates); and `expected-output.txt`, the demo's exact output, which `make test` compares with what the demo prints |
| `Makefile` | `make run` and `make test`, with `cc -std=c17 -Wall -Wextra -Wpedantic -Werror -O0 -g` |
| `ds.toml` | Everything that is not C: the story, the acts, the diagrams, the video scenes |
| `README.md` + `README.html` | Technologies and versions, how to build and run, the operations and their costs, the key functions quoted from the source, the diagrams, and learning material |
| `docs/` | The explained walkthrough, problem statement, prerequisites, a one-hour session plan, architecture, type, data-flow and sequence diagrams (PNG, never Mermaid), `animation.html`, `thumbnail.png` and `youtube.md` |
| `video/` | `scenes.py` (narration and slides) and `videokit.toml`; the rendered video, audio and subtitles are not committed |

### The five acts of every demo

1. **The problem**: the example without this data structure, and what goes wrong (too slow, too much memory, wrong answer).
2. **The structure**: how it is laid out in memory, drawn and printed.
3. **The operations**: insert, find and remove, with the real step counts the demo measures.
4. **The edge case**: the moment that trips people up (full, empty, a collision, a rebalance, a resize, a dangling pointer).
5. **The bill**: time and space costs in plain words, and what the C standard library already offers (`qsort`, `bsearch`, `realloc`), or that it offers nothing.

## 2. Making it crystal clear for a complete beginner

Everything in section 2 of the Java plan applies unchanged: a course primer,
[`START-HERE.md`](START-HERE.md), written for C (memory, addresses and pointers, `malloc` and
`free`, counting steps, Big-O after the idea is clear); picture first, words second, code last;
one everyday analogy per structure; costs as counted steps before notation; every new word
explained where it first appears; common mistakes with the fix; three exercises with hidden
answers; a one-page cheat sheet; an animation that draws the real structure; and narration
that never relies on the screen. C adds its own mistakes to warn about, and every project
names the ones that apply: forgetting to `free`, using memory after `free`, reading past the
end of an array, forgetting the terminating `'\0'`, and a pointer that was never initialised.

## 3. Standing rules

- **Operations named as in the textbooks**, in C's snake_case and without prefixes, because
  each project is its own program (`insert_at(&a, 2, 18)`, as a C textbook writes it):
  arrays `traverse`, `insert_at`, `delete_at`, `linear_search`, `binary_search`; linked lists
  `insert_at_beginning`, `insert_at_end`, `insert_at_position`, `delete_at_beginning`,
  `delete_at_end`, `delete_by_key`, `search`, `display`, `count`, `reverse`; stacks `push`,
  `pop`, `peek`, `is_empty`, `is_full`; queues `enqueue`, `dequeue`, `front`, `rear`; trees
  `insert`, `delete`, `search`, `inorder`, `preorder`, `postorder`, `level_order`, `height`;
  graphs `add_vertex`, `add_edge`, `bfs`, `dfs`; heaps `insert`, `extract_min`, `heapify`.
- **Textbook C.** `struct node { data; struct node *next; }` with `head` and `tail`, `top`,
  `front` and `rear`, `size` and `capacity`, `root`, `parent`, `left` and `right`. Memory is
  taken with `malloc` and given back with `free`, and every project frees everything it takes;
  errors are reported by return values (overflow, underflow, out of range), not by printing.
- **Separate projects for each version**, in this order, exactly as in the Java course:
  1. `<slug>`: iterative, one concrete element type (`int` or a `char` string);
  2. `<slug>-recursive`: the same operations written recursively, wherever recursion is natural;
  3. `<slug>-generic`: iterative, holding any type through `void *` (section 6);
  4. `<slug>-generic-recursive`: generic and recursive.
  Each recursive project explains the call stack, measures the recursion depth of every
  operation, and ends a deep enough recursion in a real stack overflow. C cannot catch one, so
  the demo runs that recursion in a child process (`fork`) and reports how the child ended
  (killed by `SIGSEGV`); the rest of the demo carries on. The Makefile compiles with `-O0` so
  that the compiler does not turn recursion into loops behind the learner's back. 156 projects
  in all: 54 plain, 35 recursive, 40 generic and 27 generic recursive.
- **Complexity for every operation**: best, average and worst time and the extra space, with
  the cost in the worked example beside the formulas; recursive projects add the call-stack space.
- **No instrumentation.** The structure's code contains nothing but the algorithm. The demo
  works out its numbers from outside: from what the operations return (the index a search
  found gives the comparisons made; a position gives the shifts), from a comparison function
  that counts its calls in the generic versions, or from the formula, saying which. Its whole
  output is kept in `tests/expected-output.txt`, so every quoted number stays checked.
- **Compared with the related structures**, with pros and cons, in every project.
- **No libraries.** Only the C standard library, and inside a project's structure code only
  what C textbooks use (`malloc`, `realloc`, `free`, `memcpy`, `strcmp`). The tests use a
  harness written in the project (`tests/check.h`), not a test framework. POSIX `fork` and
  `waitpid` appear only in the recursive demos, to survive the stack overflow they show.
- **C17**, compiled with `-std=c17 -Wall -Wextra -Wpedantic -Werror -O0 -g`, built with the
  system `make`: nothing to install beyond a C compiler.
- **The domain that explains it best**, the same worked examples as the Java course.
- **Written for beginners and for listening**; **narration voice: amy-slow**; the credit is
  "This video is presented by Jayasekhar Konduru."; the outro never names the next video;
  **animations** speak amy-slow only, with the shared UX layer; **diagrams** are PNG or SVG,
  never Mermaid; generated HTML is self-contained; posters never strike text through.
- **Committed**: source and documentation. **Not committed**: build output, rendered video,
  audio, subtitles, generated specs and demo output.
- **Commits** carry no co-author trailer and no assistant attribution.

## 4. Shared tools (in this folder, used by every project)

| Tool | What it does |
| --- | --- |
| `tools/dskit/` | `dskit.sh scaffold / docs / build / index / next / html`: turns one `ds.toml` into the README, docs, structure pictures, diagrams, animation and video scenes; runs `make test` and `make run`; makes the thumbnail, the YouTube document, the HTML twins and the index |
| `tools/dskit/dskit/structure.py` | Draws any data structure's state (arrays, stacks, linked lists, trees, graphs, hash buckets, grids, 2-D planes) as one SVG used by both the animation and the video |
| `tools/videokit/` | Slides, narration, video, subtitles and animation narration with the shared UX layer |
| `tools/template/` | What every project starts from: the `Makefile` and the test harness `check.[ch]` |

These are copies of the Java course's tools, adapted for C, so this folder stands alone. The
Python environment for them lives outside the repository (`~/.cache/videokit`), and nothing it
generates is committed.

## 5. The catalogue — 54 data structures in 9 categories

The order is the teaching order: each one builds on ideas from the ones before it.
Each has the worked example that explains it best.

### 5.1 Arrays and lists — `arrays-and-lists/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 1 | Static array | A week of daily temperatures, one numbered slot per day |
| 2 | Dynamic array | A music playlist that grows as songs are added |
| 3 | Singly linked list | A treasure hunt: each clue says where the next one is |
| 4 | Doubly linked list | A photo viewer with Previous and Next |
| 5 | Circular linked list | Taking turns in a board game, round and round |
| 6 | Skip list | A train line with stopping and express services |

### 5.2 Stacks and queues — `stacks-and-queues/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 7 | Stack | A text editor's undo, and a browser's back button |
| 8 | Queue | Documents waiting at an office printer |
| 9 | Deque | A hospital waiting room where urgent cases join at the front |
| 10 | Circular buffer | A dashcam that keeps only the last minute of footage |

### 5.3 Hashing — `hashing/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 11 | Hash table with separate chaining | A library's shelves, one shelf per first letter |
| 12 | Hash table with open addressing | A car park: if your bay is taken, try the next one |
| 13 | Cuckoo hash table | Two possible lockers per coat; a newcomer can move one along |
| 14 | Hash set | The guest list at a party door: in or not |
| 15 | LRU cache | A small desk that keeps only the books you used most recently |
| 16 | Consistent hash ring | Sharing out the library's books when a new branch opens |

### 5.4 Trees — `trees/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 17 | Binary tree | A family tree, and the ways to walk it |
| 18 | Binary search tree | A guessing game: higher or lower? |
| 19 | AVL tree | The same guessing game, kept fair as names are added |
| 20 | Red-black tree | A dictionary that must stay fast whatever order words arrive in |
| 21 | Splay tree | A phone that brings your most-called contacts to the top |
| 22 | Treap | A queue of tickets where a coin toss keeps the line balanced |
| 23 | B-tree | A library catalogue stored in drawers, many cards per drawer |
| 24 | B+ tree | Bank transactions between two dates, read in order |
| 25 | Trie | A phone keyboard suggesting the rest of a word |
| 26 | Radix tree | The same suggestions, with shared beginnings stored once |
| 27 | Ternary search tree | A spell-checker's word list, using less memory |
| 28 | Segment tree | Rainfall between any two days of the year, with corrections |
| 29 | Fenwick tree | A running total of steps walked, updated every day |
| 30 | Interval tree | Which meetings clash with this one? |
| 31 | k-d tree | The nearest bus stop to where you are standing |
| 32 | Quadtree | Finding every tree in a park on a map, zooming in |

### 5.5 Heaps and priority queues — `heaps/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 33 | Binary heap | A hospital emergency department: the most urgent first |
| 34 | d-ary heap | The same triage, with fewer levels to climb |
| 35 | Leftist heap | Merging two hospitals' waiting lists |
| 36 | Pairing heap | A fast, simple mergeable waiting list |
| 37 | Binomial heap | Merging tournaments of players in logarithmic time |
| 38 | Fibonacci heap | A sat-nav improving its best-known times as roads are checked |
| 39 | Indexed priority queue | An airport board where a flight's delay changes its place |

### 5.6 Graphs — `graphs/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 40 | Adjacency matrix | Which of five friends know each other |
| 41 | Adjacency list | A city's one-way streets |
| 42 | Edge list | Every road with its length, sorted for planning cables |
| 43 | Disjoint set (union-find) | Islands joined by bridges: are these two towns connected? |

### 5.7 Strings and text — `strings/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 44 | Gap buffer | Typing in a text editor |
| 45 | Rope | Editing a whole novel without copying it each time |
| 46 | Suffix array | Finding a phrase anywhere in a book |
| 47 | Inverted index | A book's index at the back, built by a program |

### 5.8 Probabilistic — `probabilistic/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 48 | Bloom filter | Has this web address already been visited by the crawler? |
| 49 | Count-min sketch | Which hashtags are trending right now? |
| 50 | HyperLogLog | How many different people visited a website today? |

### 5.9 Specialised — `specialised/`

| # | Data structure | Worked example |
| ---: | --- | --- |
| 51 | Bit set | Which seats in a cinema row are booked |
| 52 | Sparse matrix | Who follows whom among a million users, mostly nobody |
| 53 | Sparse table | The coldest day in any range of a year's temperatures |
| 54 | Persistent list | Every earlier version of a document, kept for free |

## 6. Generic versions (40 of the 54, and 27 of those also recursive)

Every plain project is written for one concrete element type (`int` or a `char` string). Its
`-generic` sibling holds any type the way C textbooks and the C standard library do it, as
`qsort` and `bsearch` do:

- elements are stored as `void *` or as raw bytes, with an **element size** given when the
  structure is created (`sizeof(int)`, `sizeof(struct song)`), and copied in and out with
  `memcpy`;
- anything that needs to compare elements takes a **comparison function pointer**,
  `int (*compare)(const void *, const void *)`, returning negative, zero or positive;
- printing takes a function pointer that formats one element.

What the sibling teaches is the trade C makes: one structure for every type, at the price of
type safety. The compiler cannot tell that an array of songs was handed a temperature, so the
generic project shows that mistake too, and compares the approach with the Java course's
generics. The same structures have siblings as in the Java course.

## 7. Library versions

None are planned. C has no standard collections, and pulling in a third-party container
library would work against the point of the course; each project's act five says what the C
standard library does offer.

## 8. How the work proceeds

1. **Tooling first**: the Java course's tools, copied into `tools/` and adapted for C (the
   Makefile, the test harness, C source listings, C wording).
2. **Then one project at a time**, in catalogue order, each structure's plain project followed
   by its recursive, generic and generic recursive siblings: write the C and the tests, write
   `ds.toml`, run `tools/dskit/dskit.sh build <slug>`, check the output, and update the index.
3. **Batches**: the first ten projects are built, then the work pauses for review before the
   next batch.
