package com.jk.explore.skiplist;

/**
 * A coin to toss when deciding how tall a new station is: heads, go up a level; tails, stop.
 *
 * <p>Written by hand as a tiny xorshift generator rather than using {@code java.util.Random}, so
 * the idea is visible and a fixed seed makes every run of the demo toss exactly the same coins.
 */
public final class Coin {

    private int state;
    private final boolean alwaysTails;

    private Coin(int seed, boolean alwaysTails) {
        this.state = seed == 0 ? 1 : seed;
        this.alwaysTails = alwaysTails;
    }

    /** A fair coin that tosses the same sequence every time for the same seed. */
    public static Coin seeded(int seed) {
        return new Coin(seed, false);
    }

    /** An unlucky coin that never comes up heads: every station stays on the bottom level. */
    public static Coin alwaysTails() {
        return new Coin(1, true);
    }

    /** One toss: true for heads. */
    public boolean heads() {
        if (alwaysTails) {
            return false;
        }
        state ^= state << 13;       // xorshift: scramble the bits three ways
        state ^= state >>> 17;
        state ^= state << 5;
        return (state & 1) == 1;
    }
}
