package com.jk.explore.stack;

/** Counts the steps a stack takes: one per push, pop or peek, each of which touches one slot. */
public final class StepCounter {

    private long steps;

    void step() {
        steps++;
    }

    public long steps() {
        return steps;
    }

    public void reset() {
        steps = 0;
    }
}
