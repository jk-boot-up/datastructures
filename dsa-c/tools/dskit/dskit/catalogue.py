"""The course catalogue, in teaching order (see PLAN.md section 5)."""

CATEGORIES = [
    ("arrays-and-lists", "Arrays and lists"),
    ("stacks-and-queues", "Stacks and queues"),
    ("hashing", "Hashing"),
    ("trees", "Trees"),
    ("heaps", "Heaps and priority queues"),
    ("graphs", "Graphs"),
    ("strings", "Strings and text"),
    ("probabilistic", "Probabilistic"),
    ("specialised", "Specialised"),
]

# (category, slug, title, worked example)
ENTRIES = [
    ("arrays-and-lists", "static-array", "Static Array", "A week of daily temperatures, one numbered slot per day"),
    ("arrays-and-lists", "dynamic-array", "Dynamic Array", "A music playlist that grows as songs are added"),
    ("arrays-and-lists", "singly-linked-list", "Singly Linked List", "A treasure hunt: each clue says where the next one is"),
    ("arrays-and-lists", "doubly-linked-list", "Doubly Linked List", "A photo viewer with Previous and Next"),
    ("arrays-and-lists", "circular-linked-list", "Circular Linked List", "Taking turns in a board game, round and round"),
    ("arrays-and-lists", "skip-list", "Skip List", "A train line with stopping and express services"),
    ("stacks-and-queues", "stack", "Stack", "A text editor's undo, and a browser's back button"),
    ("stacks-and-queues", "queue", "Queue", "Documents waiting at an office printer"),
    ("stacks-and-queues", "deque", "Deque", "A hospital waiting room where urgent cases join at the front"),
    ("stacks-and-queues", "circular-buffer", "Circular Buffer", "A dashcam that keeps only the last minute of footage"),
    ("hashing", "hash-table-separate-chaining", "Hash Table with Separate Chaining", "A library's shelves, one shelf per first letter"),
    ("hashing", "hash-table-open-addressing", "Hash Table with Open Addressing", "A car park: if your bay is taken, try the next one"),
    ("hashing", "cuckoo-hash-table", "Cuckoo Hash Table", "Two possible lockers per coat; a newcomer can move one along"),
    ("hashing", "hash-set", "Hash Set", "The guest list at a party door: in or not"),
    ("hashing", "lru-cache", "LRU Cache", "A small desk that keeps only the books you used most recently"),
    ("hashing", "consistent-hash-ring", "Consistent Hash Ring", "Sharing out the library's books when a new branch opens"),
    ("trees", "binary-tree", "Binary Tree", "A family tree, and the ways to walk it"),
    ("trees", "binary-search-tree", "Binary Search Tree", "A guessing game: higher or lower?"),
    ("trees", "avl-tree", "AVL Tree", "The same guessing game, kept fair as names are added"),
    ("trees", "red-black-tree", "Red-Black Tree", "A dictionary that must stay fast whatever order words arrive in"),
    ("trees", "splay-tree", "Splay Tree", "A phone that brings your most-called contacts to the top"),
    ("trees", "treap", "Treap", "A queue of tickets where a coin toss keeps the line balanced"),
    ("trees", "b-tree", "B-Tree", "A library catalogue stored in drawers, many cards per drawer"),
    ("trees", "b-plus-tree", "B+ Tree", "Bank transactions between two dates, read in order"),
    ("trees", "trie", "Trie", "A phone keyboard suggesting the rest of a word"),
    ("trees", "radix-tree", "Radix Tree", "The same suggestions, with shared beginnings stored once"),
    ("trees", "ternary-search-tree", "Ternary Search Tree", "A spell-checker's word list, using less memory"),
    ("trees", "segment-tree", "Segment Tree", "Rainfall between any two days of the year, with corrections"),
    ("trees", "fenwick-tree", "Fenwick Tree", "A running total of steps walked, updated every day"),
    ("trees", "interval-tree", "Interval Tree", "Which meetings clash with this one?"),
    ("trees", "k-d-tree", "k-d Tree", "The nearest bus stop to where you are standing"),
    ("trees", "quadtree", "Quadtree", "Finding every tree in a park on a map, zooming in"),
    ("heaps", "binary-heap", "Binary Heap", "A hospital emergency department: the most urgent first"),
    ("heaps", "d-ary-heap", "d-ary Heap", "The same triage, with fewer levels to climb"),
    ("heaps", "leftist-heap", "Leftist Heap", "Merging two hospitals' waiting lists"),
    ("heaps", "pairing-heap", "Pairing Heap", "A fast, simple mergeable waiting list"),
    ("heaps", "binomial-heap", "Binomial Heap", "Merging tournaments of players in logarithmic time"),
    ("heaps", "fibonacci-heap", "Fibonacci Heap", "A sat-nav improving its best-known times as roads are checked"),
    ("heaps", "indexed-priority-queue", "Indexed Priority Queue", "An airport board where a flight's delay changes its place"),
    ("graphs", "adjacency-matrix", "Adjacency Matrix", "Which of five friends know each other"),
    ("graphs", "adjacency-list", "Adjacency List", "A city's one-way streets"),
    ("graphs", "edge-list", "Edge List", "Every road with its length, sorted for planning cables"),
    ("graphs", "disjoint-set", "Disjoint Set (Union-Find)", "Islands joined by bridges: are these two towns connected?"),
    ("strings", "gap-buffer", "Gap Buffer", "Typing in a text editor"),
    ("strings", "rope", "Rope", "Editing a whole novel without copying it each time"),
    ("strings", "suffix-array", "Suffix Array", "Finding a phrase anywhere in a book"),
    ("strings", "inverted-index", "Inverted Index", "A book's index at the back, built by a program"),
    ("probabilistic", "bloom-filter", "Bloom Filter", "Has this web address already been visited by the crawler?"),
    ("probabilistic", "count-min-sketch", "Count-Min Sketch", "Which hashtags are trending right now?"),
    ("probabilistic", "hyperloglog", "HyperLogLog", "How many different people visited a website today?"),
    ("specialised", "bit-set", "Bit Set", "Which seats in a cinema row are booked"),
    ("specialised", "sparse-matrix", "Sparse Matrix", "Who follows whom among a million users, mostly nobody"),
    ("specialised", "sparse-table", "Sparse Table", "The coldest day in any range of a year's temperatures"),
    ("specialised", "persistent-list", "Persistent List", "Every earlier version of a document, kept for free"),
]


# Structures that also get a generic sibling, "<slug>-generic": the same structure written with
# type parameters, so it holds any type of object (PLAN.md section 6). The rest are inherently about
# characters, bits or numbers.
GENERIC = {
    "static-array", "dynamic-array", "singly-linked-list", "doubly-linked-list", "circular-linked-list",
    "skip-list", "stack", "queue", "deque", "circular-buffer",
    "hash-table-separate-chaining", "hash-table-open-addressing", "cuckoo-hash-table", "hash-set",
    "lru-cache", "consistent-hash-ring",
    "binary-tree", "binary-search-tree", "avl-tree", "red-black-tree", "splay-tree", "treap", "b-tree",
    "b-plus-tree", "interval-tree",
    "binary-heap", "d-ary-heap", "leftist-heap", "pairing-heap", "binomial-heap", "fibonacci-heap",
    "indexed-priority-queue",
    "adjacency-matrix", "adjacency-list", "edge-list", "disjoint-set",
    "bloom-filter", "count-min-sketch", "hyperloglog",
    "persistent-list",
}


# Structures whose operations are naturally recursive get a separate recursive project,
# "<slug>-recursive" (and "<slug>-generic-recursive" when they are generic too). Structures whose
# operations are simple loops over an array or two pointers (stacks, queues, deques, circular
# buffers, bit sets, the probabilistic sketches) have none.
RECURSIVE = {
    "static-array", "dynamic-array", "singly-linked-list", "doubly-linked-list", "circular-linked-list",
    "skip-list",
    "hash-table-separate-chaining", "hash-set", "consistent-hash-ring",
    "binary-tree", "binary-search-tree", "avl-tree", "red-black-tree", "splay-tree", "treap", "b-tree",
    "b-plus-tree", "trie", "radix-tree", "ternary-search-tree", "segment-tree", "interval-tree",
    "k-d-tree", "quadtree",
    "binary-heap", "d-ary-heap", "leftist-heap", "pairing-heap", "binomial-heap",
    "adjacency-matrix", "adjacency-list", "disjoint-set",
    "rope", "persistent-list", "sparse-table",
}


def variants(slug, title):
    """The projects for one structure: (slug, title, worked-example note), in build order."""
    out = [(slug, title, None)]
    if slug in RECURSIVE:
        out.append((slug + "-recursive", title + " (Recursive)", "The same operations, written recursively"))
    if slug in GENERIC:
        out.append((slug + "-generic", title + " (Generic)", "The same structure for any type: void * elements and an element size"))
        if slug in RECURSIVE:
            out.append((slug + "-generic-recursive", title + " (Generic, Recursive)",
                        "Generic and recursive: void * elements, operations written recursively"))
    return out


def all_entries():
    """Every project in build order: each structure's plain, recursive, generic and generic recursive versions."""
    out = []
    for cat, slug, title, example in ENTRIES:
        for s, t, note in variants(slug, title):
            out.append((cat, s, t, note or example))
    return out


def category_title(cat):
    return dict(CATEGORIES)[cat]
