package com.jk.explore.staticarraygeneric;

/**
 * Counts what an array operation costs, so the demo prints real numbers instead of claims:
 * element reads and writes, comparisons, shifts (moving an element to the next position), swaps,
 * and copies into a new array.
 */
public final class StepCounter {

    private long reads;
    private long writes;
    private long compares;
    private long shifts;
    private long swaps;
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

    void swap() {
        swaps++;
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

    public long swaps() {
        return swaps;
    }

    public long copies() {
        return copies;
    }

    public void reset() {
        reads = 0;
        writes = 0;
        compares = 0;
        shifts = 0;
        swaps = 0;
        copies = 0;
    }
}
