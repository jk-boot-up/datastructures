package com.jk.explore.staticarray;

/**
 * The week without an array: seven separate variables, one per day (the "before" of act one).
 *
 * <p>Nothing here is wrong, and for seven values it even works. But every question needs code
 * written out by hand, day by day, and a second week means seven more variables.
 */
public record WeekInVariables(int mon, int tue, int wed, int thu, int fri, int sat, int sun) {

    /** How many comparisons {@link #hottest()} writes out by hand. */
    public static final int HAND_WRITTEN_COMPARISONS = 6;

    /** The hottest temperature, found by six hand-written comparisons. */
    public int hottest() {
        int best = mon;
        if (tue > best) best = tue;
        if (wed > best) best = wed;
        if (thu > best) best = thu;
        if (fri > best) best = fri;
        if (sat > best) best = sat;
        if (sun > best) best = sun;
        return best;
    }

    /** "The temperature on day number n" needs a switch with a branch for every day. */
    public int day(int n) {
        return switch (n) {
            case 0 -> mon;
            case 1 -> tue;
            case 2 -> wed;
            case 3 -> thu;
            case 4 -> fri;
            case 5 -> sat;
            case 6 -> sun;
            default -> throw new IllegalArgumentException("no day " + n);
        };
    }
}
