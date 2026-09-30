package com.jk.explore.doublylinkedlist;

/**
 * Counts what a doubly-linked-list operation costs: traversal steps (following one next or prev
 * pointer), comparisons (checking one node's data against a key), and pointer changes (setting one
 * head, tail, next or prev pointer).
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
    }
}
