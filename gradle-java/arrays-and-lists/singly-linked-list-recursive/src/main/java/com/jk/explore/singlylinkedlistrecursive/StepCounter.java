package com.jk.explore.singlylinkedlistrecursive;

/**
 * Counts what a linked-list operation costs: traversal steps (following one next pointer),
 * comparisons (checking one node's data against a key), and pointer changes (setting one pointer).
 * Also the deepest recursion reached: the most call-stack frames alive at once, which is the extra
 * space a recursive operation uses.
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

    void pointer() {
        pointerChanges++;
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

    /** Traversal steps: next pointers followed. */
    public long steps() {
        return steps;
    }

    /** Nodes whose data was compared with a key. */
    public long compares() {
        return compares;
    }

    /** Pointers (head or a next) set to a new value. */
    public long pointerChanges() {
        return pointerChanges;
    }

    /** The most recursive calls that were waiting on the call stack at once. */
    public int maxDepth() {
        return maxDepth;
    }

    public void reset() {
        steps = 0;
        compares = 0;
        pointerChanges = 0;
        depth = 0;
        maxDepth = 0;
    }
}
