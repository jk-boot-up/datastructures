package com.jk.explore.skiplist;

/**
 * Counts what a linked-list operation costs: traversal steps (moving forward to the next node on some level)
 * and pointer changes (setting one forward pointer).
 */
public final class StepCounter {

    private long steps;
    private long pointerChanges;

    void step() {
        steps++;
    }

    void pointer(int n) {
        pointerChanges += n;
    }

    /** Traversal steps: forward pointers followed. */
    public long steps() {
        return steps;
    }

    /** Pointers set to a different node. */
    public long pointerChanges() {
        return pointerChanges;
    }

    public void reset() {
        steps = 0;
        pointerChanges = 0;
    }
}
