package com.jk.explore.circularlinkedlist;

/**
 * Counts what a circular-linked-list operation costs: traversal steps (following one next
 * pointer), comparisons (checking one node's data against a key), and pointer changes (setting one
 * last or next pointer).
 */
public final class StepCounter {

    private long steps;
    private long compares;
    private long pointerChanges;

    void step() {
        steps++;
    }

    void compare() {
        compares++;
    }

    void pointer(int n) {
        pointerChanges += n;
    }

    /** Traversal steps: next pointers followed. */
    public long steps() {
        return steps;
    }

    /** Nodes whose data was compared with a key. */
    public long compares() {
        return compares;
    }

    /** Pointers (last or next) set to a new value. */
    public long pointerChanges() {
        return pointerChanges;
    }

    public void reset() {
        steps = 0;
        compares = 0;
        pointerChanges = 0;
    }
}
