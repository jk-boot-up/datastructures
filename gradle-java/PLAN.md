# Data Structures in Java — Plan

A teaching course: every classic data structure as its own small Java 25 / Gradle
project, built from scratch, explained for beginners, with a narrated video and an
interactive animation. It follows the same specification as the design-patterns
course in `jk-boot-up/java-design-patterns/gradle-java`, adapted from patterns to data
structures, and everything lives inside this folder.

Progress is tracked in [`index.md`](index.md) ([`index.html`](index.html)), which is
regenerated every time a project is built.

## 1. What each project contains

Every data structure is one self-contained Gradle project in its own folder,
`<category>/<slug>/`. None of them depends on another, and none uses code that
exists elsewhere in the repository: every implementation is written fresh.

| Part | What it is |
| --- | --- |
| `src/main/java/...` | The data structure, implemented from scratch in plain Java 25 (iteratively or recursively, generic or not, depending on the project), plus a `Demo` class whose `main` prints the five acts |
| `src/test/java/...` | JUnit 6 tests: the operations, the edge cases (empty, one element, full, duplicates), and a test that the demo runs |
| `ds.toml` | Everything that is not Java: the story, the acts, the diagrams, the video scenes |
| `README.md` + `README.html` | Technologies and versions, how to run, the operations and their costs, the diagrams embedded, and learning material |
| `docs/` | The explained walkthrough, problem statement, prerequisites, a one-hour session plan, the specification, architecture, class, data-flow and sequence diagrams (PNG/SVG, never Mermaid), `animation.html`, `thumbnail.png` and `youtube.md` |
| `video/` | `scenes.py` (narration and slides) and `videokit.toml`; the rendered video, audio and subtitles are not committed |

### The five acts of every demo

1. **The problem**: the example without this data structure, and what goes wrong (too slow, too much memory, wrong answer).
2. **The structure**: how it is laid out, drawn and printed.
3. **The operations**: insert, find and remove, with the real step counts the demo measures.
4. **The edge case**: the moment that trips people up (full, empty, a collision, a rebalance, a resize).
5. **The bill**: time and space costs in plain words, and the JDK class (or well-known library) that already does this.

## 2. Making it crystal clear for a complete beginner

Every project is written so that someone who has never studied data structures can
follow all of it on the first try.

- **Start here, once.** A course primer, `START-HERE.md` (and `.html`), explains the
  few ideas every project relies on, in plain words with pictures: what a data
  structure is; how memory holds values in a row, and what a reference (an arrow to
  another object) is; what "counting the steps" means; and the words *fast*,
  *slow*, *grows with the size* and Big-O, introduced only after the idea is clear.
- **Picture first, words second, code last.** Each operation is shown as a before
  picture and an after picture of the actual structure (cells for arrays, boxes and
  arrows for linked structures, drawn trees and graphs), then described in one or two
  sentences, and only then shown in Java.
- **One everyday analogy per structure**, used consistently: a row of numbered lockers
  for an array, a treasure hunt of clues for a linked list, a pile of plates for a
  stack, a queue at a till for a queue.
- **Costs as counted steps before notation.** "Finding a product among 1,000 took
  1,000 looks; with the tree it took 10" comes first; "O(n)" and "O(log n)" come after,
  as names for what was just counted.
- **Every new word is explained where it first appears**, and a short glossary closes
  each README.
- **Common mistakes** each project warns about (an off-by-one index, forgetting to move
  an arrow, comparing with `==`), with what goes wrong and the fix.
- **Try it yourself**: three small exercises per project, from easy to harder, with the
  answers in a separate section so they are not given away.
- **A one-page cheat sheet** per project: what it is, when to use it, the costs, the
  JDK class that already does it.
- **The animation draws the real structure.** Each step shows the cells, nodes and
  arrows as they are at that moment, highlights what just changed, and says in words
  what happened, so the learner watches the data structure work rather than reading
  about it. "Predict first" asks the learner to guess the next picture.
- **The narration** walks through the same pictures slowly, with a pause after each
  key point, and never relies on the screen.

## 3. Standing rules (the same as the design-patterns course)

- **Operations named as in the textbooks.** Each structure offers the operations a
  computer-science and engineering data-structures book lists for it, under those names:
  arrays `traverse`, `insertAt`, `deleteAt`, `linearSearch`, `binarySearch`; linked lists
  `insertAtBeginning`, `insertAtEnd`, `insertAtPosition`, `deleteAtBeginning`,
  `deleteAtEnd`, `deleteByKey`, `search`, `display`, `count`, `reverse`; stacks `push`, `pop`,
  `peek`, `isEmpty`, `isFull`, `display`; queues `enqueue`, `dequeue`, `front`, `rear`; trees
  `insert`, `delete`, `search`, `inorder`, `preorder`, `postorder`, `levelOrder`, `height`;
  graphs `addVertex`, `addEdge`, `bfs`, `dfs`; heaps `insert`, `extractMin`/`extractMax`,
  `heapify`.
- **Separate projects for each version.** Every structure is built as up to four separate,
  self-contained projects, in this order:
  1. `<slug>`: non-recursive (iterative) and non-generic, one concrete element type, as in C;
  2. `<slug>-recursive`: the same operations written recursively, wherever recursion is natural;
  3. `<slug>-generic`: non-recursive, written with Java generics so it holds any type of object;
  4. `<slug>-generic-recursive`: generic and recursive.
  Recursive projects exist where the operations are naturally recursive (arrays' search and
  traversal, linked lists, trees, heaps, graph traversal, union-find, ropes); stacks, queues,
  deques, circular buffers, bit sets and the probabilistic sketches are simple loops and have
  none. Generic projects exist where the structure can hold any object (section 6). Each
  recursive project explains the call stack, shows the recursion depth of every operation, and
  ends a deep enough recursion in a real `StackOverflowError`. That makes 156 projects: 54 plain,
  35 recursive, 40 generic and 27 generic recursive.
- **Complexity for every operation.** Time in the best, average and worst case, and auxiliary
  space, for both versions, in one table; counted steps from the demo sit beside the formulas.
- **Compared with the related structures.** Every project has a comparison table against the
  structures a student would weigh it against (an array against a linked list, a stack on an
  array against one on a linked list, a hash table against a search tree), and a list of pros and
  cons.
- **Textbook terminology, kept simple.** Code and explanations use the standard names
  found in data-structures textbooks for computer-engineering students, so what a
  learner meets here matches their course: `Node` with `data` and `next` (and `prev`),
  `head` and `tail`; a stack's `top`, `push`, `pop`, `peek`, overflow and underflow; a
  queue's `front`, `rear`, `enqueue` and `dequeue`; `size` and `capacity`; a tree's
  `root`, `parent`, `child`, `leaf` and height; a graph's vertex and edge; a hash
  table's bucket, collision and load factor. Pictures and analogies (lockers, a
  treasure hunt, a train) illustrate the terms but never replace them: the narration
  says "node" and "next pointer", not "box" and "arrow".
- **Implement it the way you would in C.** Every data structure and its algorithms are
  built by hand from first principles: raw arrays, nodes you link yourself, and every
  loop, shift, copy and pointer change written out. No `java.util` collections, no
  `Arrays` helpers and no third-party libraries inside a project's main code, unless a
  project genuinely needs one (and then the README says why). Even the demos collect
  their output with a plain `StringBuilder`, so a learner never meets a ready-made
  collection inside the project that is teaching how to build one. The JDK's own
  class is only *mentioned* in act five, as the thing to use in real code once the
  idea is clear. JUnit is the one library every project uses, for the tests.
- **Java 25 (the current long-term-support release), Gradle 9.8.0** (the newest release), one Gradle wrapper per project, JUnit 6.1.3. The Foojay toolchain resolver downloads JDK 25 on a machine that does not have it.
- **The domain that explains it best.** Unlike the design-patterns course, there is no
  single worked domain: each data structure is taught through whichever everyday
  setting makes it clearest (a browser's back button for a stack, a road map for a
  graph, a phone's contact search for a trie), chosen per structure and named in its
  README.
- **Written for beginners and for listening.** A listener with the screen off must
  follow: name the parts, say what each does, one idea at a time, numbers spoken as
  words, "ID" said as "I D", "API" as the letters.
- **Narration voice: amy-slow** (Piper `en_US-amy-medium`, speed 0.8, longer pauses).
  The credit is "This video is presented by Jayasekhar Konduru." The opening states
  what the video is, gives the credit, gives a plain definition, and only then turns
  to the worked example. The outro never names the next video.
- **Animations** speak amy-slow only, with the shared UX layer: Light / Dim / Dark
  themes, a step timeline, Back, Predict first, keyboard control, and no sideways
  scrolling on a phone.
- **Diagrams** are SVG or PNG files, never Mermaid. Every generated HTML file is
  self-contained.
- **Committed**: source and documentation. **Not committed**: rendered video, audio,
  subtitles and build output (already covered by the repository `.gitignore`).
- **Posters and thumbnails** never strike text through; contrast is carried by colour
  and labels.
- **Commits** carry no co-author trailer and no assistant attribution.

## 4. Shared tools (in this folder, used by every project)

| Tool | What it does |
| --- | --- |
| `tools/dskit/` | `dskit.sh scaffold / docs / build / index / next / html`: turns one `ds.toml` into the README, docs, structure pictures, diagrams, animation and video scenes; runs the tests; makes the thumbnail, the YouTube document, the HTML twins and the index |
| `tools/dskit/dskit/structure.py` | Draws any data structure's state (arrays, stacks, linked lists, trees, graphs, hash buckets, grids, 2-D planes) as one SVG used by both the animation and the video |
| `tools/videokit/` | Slides, narration, video, subtitles and animation narration with the shared UX layer (copied from the design-patterns course so this folder stands alone) |
| `tools/template/` | The Gradle 9.8.0 wrapper every project starts from |

The Python environment for these tools lives outside the repository
(`~/.cache/videokit`, which also holds the downloaded voice model), and nothing generated by it is committed.

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

Every plain project is written the way a C textbook writes it, with one concrete element type
(`String` or `int`). Wherever it makes sense, a sibling project `<slug>-generic` implements the
same structure again with Java generics, so it can hold any type of object: songs, photos,
`Integer`s, a `record` of your own. The plain project is never changed by its sibling; the
sibling is built straight after it, and teaches what generics add:

- a type parameter (`Node<T>`, `SinglyLinkedList<T>`) and compile-time type safety: no casts,
  and a wrong type is a compile error instead of a crash;
- why Java cannot say `new T[capacity]` (type erasure), and the standard `Object[]` plus cast;
- bounded types, `<T extends Comparable<T>>` or a `Comparator<T>`, for structures that keep
  their elements in order (skip lists, search trees, heaps);
- `equals` and `hashCode` for structures that compare or hash their elements;
- the cost: primitives are boxed (`int` becomes `Integer`).

Siblings exist for the arrays and lists, stacks and queues, hash tables and sets, the ordered
trees, the heaps, the graphs, the probabilistic filters and the persistent list. Tries, radix and
ternary trees, suffix arrays, gap buffers, ropes, bit sets, segment and Fenwick trees, sparse
matrices and sparse tables work on characters, bits or numbers by nature, so they have none.

## 7. Library versions (phase 2, after the 156)

The design-patterns course pairs a plain version with a real-framework version where
the real one teaches something new. For data structures the JDK's own class is
covered inside each project (act five), so a separate project is only worth it where
a well-known library adds something the JDK does not. Candidates, each a new
`<slug>-with-<library>` project beside its plain twin:

| Plain project | Library | What it adds |
| --- | --- | --- |
| Bloom filter | Guava `BloomFilter` | Sizing from a target false-positive rate, serialisation |
| Count-min sketch, HyperLogLog | Apache DataSketches | Mergeable, production-grade sketches |
| Adjacency list | JGraphT | Ready-made graph algorithms over the same structure |
| LRU cache | Caffeine | Near-optimal eviction (W-TinyLFU), expiry, statistics |
| Persistent list | Vavr | Persistent collections used in real code |

These are decided and recorded here once the 156 are done.

## 8. How the work proceeds

1. **Tooling first**: copy and adapt videokit, write dskit and the shared generators,
   and build one project end to end (the dynamic array) as the reference.
2. **Then one project at a time**, in catalogue order, each structure's plain project followed
   by its recursive, generic and generic recursive siblings: write the Java and tests, write
   `ds.toml`, run `tools/dskit/dskit.sh build <slug>`, check the output, and update the
   index.
3. **The index** (`index.md` / `index.html`) shows each category's done and pending
   counts and links to every finished project's README, animation, video document and
   thumbnail.
