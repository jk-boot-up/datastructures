package com.jk.explore.dynamicarray;

/**
 * Counts the steps a dynamic array takes, so the demo can print real numbers.
 *
 * <p>A read is one look at one element; a write is one value stored; a comparison is one element
 * compared with a key; a shift is one element moved one place during insertion or deletion; a copy
 * is one element carried into a new array during a resize.
 */
public final class StepCounter {

    private long reads;
    private long writes;
    private long compares;
    private long shifts;
    private long copies;

    void read() {
        reads++;
    }

    void write() {
        writes++;
    }

    void compare() {
        compares++;
    }

    void shift() {
        shifts++;
    }

    void copy() {
        copies++;
    }

    public long reads() {
        return reads;
    }

    public long writes() {
        return writes;
    }

    public long compares() {
        return compares;
    }

    public long shifts() {
        return shifts;
    }

    public long copies() {
        return copies;
    }

    /** Every step of every kind. */
    public long total() {
        return reads + writes + compares + shifts + copies;
    }

    /** Starts counting again from zero. */
    public void reset() {
        reads = 0;
        writes = 0;
        compares = 0;
        shifts = 0;
        copies = 0;
    }
}
