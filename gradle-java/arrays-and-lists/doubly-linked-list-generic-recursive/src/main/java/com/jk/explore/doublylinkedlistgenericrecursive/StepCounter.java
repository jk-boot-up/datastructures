package com.jk.explore.doublylinkedlistgenericrecursive;

/**
 * Counts what a doubly-linked-list operation costs: traversal steps (following one next or prev
 * pointer), comparisons (checking one node's data against a key), and pointer changes (setting one
 * head, tail, next or prev pointer), and the deepest recursion reached: the most call-stack frames
 * alive at once, which is the extra space a recursive operation uses.
 */
public final class StepCounter {

    private long steps;
    private long compares;
    private long pointerChanges;
    private int depth;
    private int maxDepth;

    void step() {
        steps++;
    }

    void compare() {
        compares++;
    }

    void pointer(int n) {
        pointerChanges += n;
    }

    void enter() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    void exit() {
        depth--;
    }

    /** The most recursive calls that were waiting on the call stack at once. */
    public int maxDepth() {
        return maxDepth;
    }

    /** Traversal steps: next or prev pointers followed. */
    public long steps() {
        return steps;
    }

    /** Nodes whose data was compared with a key. */
    public long compares() {
        return compares;
    }

    /** Pointers (head, tail, next or prev) set to a new value. */
    public long pointerChanges() {
        return pointerChanges;
    }

    public void reset() {
        steps = 0;
        compares = 0;
        pointerChanges = 0;
        depth = 0;
        maxDepth = 0;
    }
}
