package com.jk.explore.singlylinkedlistgeneric;

/**
 * Counts what a linked-list operation costs: traversal steps (following one next pointer),
 * comparisons (checking one node's data against a key), and pointer changes (setting one pointer).
 * Also shifts, which an array needs and a linked list never does, so the demo can compare the two.
 */
public final class StepCounter {

    private long steps;
    private long compares;
    private long pointerChanges;
    private long shifts;

    void step() {
        steps++;
    }

    void compare() {
        compares++;
    }

    void pointer() {
        pointerChanges++;
    }

    void shift() {
        shifts++;
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

    /** Array elements shifted one position (only the demo's array comparison uses this). */
    public long shifts() {
        return shifts;
    }

    public void reset() {
        steps = 0;
        compares = 0;
        pointerChanges = 0;
        shifts = 0;
    }
}
