package com.jk.explore.dynamicarraygenericrecursive;

/**
 * Counts what an array operation costs, so the demo prints real numbers instead of claims:
 * element reads and writes, comparisons, shifts (moving an element to the next position), swaps,
 * copies into a new array, and the deepest recursion reached (the most call-stack frames alive at
 * once, which is the extra space a recursive version uses).
 */
public final class StepCounter {

    private long reads;
    private long writes;
    private long compares;
    private long shifts;
    private long swaps;
    private long copies;
    private int depth;
    private int maxDepth;

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

    void enter() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    void exit() {
        depth--;
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

    /** The most recursive calls that were waiting on the call stack at the same time. */
    public int maxDepth() {
        return maxDepth;
    }

    public void reset() {
        reads = 0;
        writes = 0;
        compares = 0;
        shifts = 0;
        swaps = 0;
        copies = 0;
        depth = 0;
        maxDepth = 0;
    }
}
