package com.jk.explore.skiplist;

/**
 * A skip list: a sorted linked list with extra levels of forward pointers that skip over many nodes.
 *
 * <p>Every node is on level 1 (the ordinary sorted list). Some nodes are also on level 2, fewer on
 * level 3, and so on. A search starts at the head on the highest level, moves forward while the
 * next key is smaller than the key sought, then drops one level, and repeats. A node's height (the
 * number of levels it is on) is chosen by tossing a coin, so on average each level has half the
 * nodes of the level below and a search takes about log2(n) steps instead of n. Written the way
 * William Pugh's original description and C textbooks write it: {@code key}, {@code value},
 * {@code forward[]} and an {@code update[]} array.
 */
public final class SkipList {

    /** The maximum height of any node. */
    public static final int MAX_LEVEL = 12;

    /** A node: its key (the kilometre marker), its value (the station name) and one forward pointer per level. */
    static final class Node {
        final int key;
        final String value;
        final Node[] forward;

        Node(int key, String value, int levels) {
            this.key = key;
            this.value = value;
            this.forward = new Node[levels];
        }
    }

    private final Node head = new Node(Integer.MIN_VALUE, "head", MAX_LEVEL);
    private int level = 1;
    private int size;
    private final Coin coin;
    private final StepCounter steps = new StepCounter();

    /** An empty skip list whose node heights come from {@code coin}. */
    public SkipList(Coin coin) {
        this.coin = coin;
    }

    public int size() {
        return size;
    }

    /** The number of levels in use: 1 when every node is on level 1 only. */
    public int levels() {
        return level;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Tosses the coin: height 1, plus one more for every head in a row. */
    int randomLevel() {
        int height = 1;
        while (height < MAX_LEVEL && coin.heads()) {
            height++;
        }
        return height;
    }

    /**
     * Search: the value stored for {@code key}, or {@code null}. On each level, move forward while
     * the next key is smaller than {@code key}, then drop a level. Every forward move is one step.
     */
    public String find(int key) {
        Node x = head;
        for (int i = level - 1; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key < key) {
                x = x.forward[i];
                steps.step();
            }
        }
        x = x.forward[0];
        steps.step();
        return x != null && x.key == key ? x.value : null;
    }

    /** Inserts with a height chosen by the coin. @return the new node's height */
    public int insert(int key, String value) {
        return insertWithLevel(key, value, randomLevel());
    }

    /**
     * Inserts with a chosen height (used to draw a perfectly regular list). While searching,
     * {@code update[i]} records the last node before {@code key} on level i; the new node is then
     * linked in after {@code update[i]} on each of its levels: two pointer changes per level.
     *
     * @return the new node's height
     */
    public int insertWithLevel(int key, String value, int height) {
        Node[] update = new Node[MAX_LEVEL];
        Node x = head;
        for (int i = level - 1; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key < key) {
                x = x.forward[i];
                steps.step();
            }
            update[i] = x;
        }
        if (x.forward[0] != null && x.forward[0].key == key) {
            throw new IllegalArgumentException("duplicate key " + key);
        }
        if (height > level) {
            for (int i = level; i < height; i++) {
                update[i] = head;           // a new top level starts from the head
            }
            level = height;
        }
        Node newNode = new Node(key, value, height);
        for (int i = 0; i < height; i++) {
            newNode.forward[i] = update[i].forward[i];   // first the new node points on
            update[i].forward[i] = newNode;              // then its predecessor points to it
            steps.pointer(2);
        }
        size++;
        return height;
    }

    /** Deletes the node with {@code key} from every level it is on. @return true if it was there */
    public boolean remove(int key) {
        Node[] update = new Node[MAX_LEVEL];
        Node x = head;
        for (int i = level - 1; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key < key) {
                x = x.forward[i];
                steps.step();
            }
            update[i] = x;
        }
        Node target = x.forward[0];
        if (target == null || target.key != key) {
            return false;
        }
        for (int i = 0; i < target.forward.length; i++) {
            update[i].forward[i] = target.forward[i];
            steps.pointer(1);
        }
        while (level > 1 && head.forward[level - 1] == null) {
            level--;
        }
        size--;
        return true;
    }

    /** The height of the node with {@code key}, or 0 if there is none (not counted as steps). */
    public int heightOf(int key) {
        for (Node x = head.forward[0]; x != null; x = x.forward[0]) {
            if (x.key == key) {
                return x.forward.length;
            }
        }
        return 0;
    }

    /** The total number of forward pointers in all nodes: the memory the extra levels cost. */
    public int pointers() {
        int n = 0;
        for (Node x = head.forward[0]; x != null; x = x.forward[0]) {
            n += x.forward.length;
        }
        return n;
    }

    /** One line per level, top first, listing the values on that level. */
    public String describe() {
        StringBuilder s = new StringBuilder();
        for (int i = level - 1; i >= 0; i--) {
            s.append("level ").append(i + 1).append(':');
            for (Node x = head.forward[i]; x != null; x = x.forward[i]) {
                s.append(' ').append(x.value);
            }
            if (i > 0) {
                s.append('\n');
            }
        }
        return s.toString();
    }
}
