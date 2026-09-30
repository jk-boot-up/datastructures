package com.jk.explore.skiplist;

/**
 * Tells the story of the skip list in five acts, printing the real step counts.
 *
 * <p>The worked example is a train line with a stopping service that calls at every station and
 * express services that skip most of them.
 */
public final class SkipListDemo {

    static final String[] STATIONS = {"Ashford", "Bexley", "Carlton", "Denby", "Elstow", "Farley", "Garston",
            "Hailey", "Ickford", "Jarrow", "Kelby", "Lydd", "Marden", "Norley", "Oakley", "Pinner"};
    static final int[] KM = {3, 8, 12, 17, 21, 26, 30, 35, 39, 44, 48, 53, 57, 62, 66, 71};
    /** A perfectly regular express pattern: every 2nd station on level 2, every 4th on 3, every 8th on 4. */
    static final int[] REGULAR = {1, 2, 1, 3, 1, 2, 1, 4, 1, 2, 1, 3, 1, 2, 1, 4};

    private SkipListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static SkipList line(int[] heights) {
        SkipList line = new SkipList(Coin.seeded(2026));
        for (int i = 0; i < STATIONS.length; i++) {
            line.insertWithLevel(KM[i], STATIONS[i], heights[i]);
        }
        line.steps().reset();
        return line;
    }

    /** Average steps to find every station of a line of {@code n} built with a seeded fair coin, times ten. */
    static long averageHopsTimesTen(int n, Coin coin) {
        SkipList line = new SkipList(coin);
        for (int i = 0; i < n; i++) {
            line.insert(i * 2, "s" + i);
        }
        line.steps().reset();
        for (int i = 0; i < n; i++) {
            line.find(i * 2);
        }
        return line.steps().steps() * 10 / n;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. A stopping service only.");
        int[] ones = new int[STATIONS.length];
        for (int i = 0; i < ones.length; i++) {
            ones[i] = 1;
        }
        SkipList stopping = line(ones);
        out.add("  " + stopping.size() + " stations in order, every train calls at every one");
        stopping.find(57);
        out.add("  find Marden (km 57): " + stopping.steps().steps() + " steps, one station at a time");
        out.add("  a sorted linked list cannot jump to the middle: there are no positions to jump to");

        out.add("");
        out.add("TWO. Express lanes.");
        SkipList line = line(REGULAR);
        for (String level : line.describe().split("\n")) {
            out.add("  " + level);
        }
        out.add("  " + line.pointers() + " forward pointers for " + line.size() + " stations");

        out.add("");
        out.add("THREE. Ride the express, then change.");
        line.steps().reset();
        String found = line.find(57);
        out.add("  find " + found + " (km 57): " + line.steps().steps() + " steps: express to Hailey, then Lydd, then step on");
        line.steps().reset();
        String missing = line.find(60);
        out.add("  find km 60: " + (missing == null ? "no station" : missing) + ", " + line.steps().steps() + " steps to be sure");
        line.steps().reset();
        int height = line.insert(50, "Quarry");
        out.add("  add Quarry (km 50): the coin gives height " + height + ", "
                + line.steps().pointerChanges() + " pointers set");
        line.steps().reset();
        line.remove(35);
        out.add("  remove Hailey (height 4): " + line.steps().pointerChanges() + " pointers changed, one per level");

        out.add("");
        out.add("FOUR. An unlucky coin.");
        SkipList unlucky = new SkipList(Coin.alwaysTails());
        for (int i = 0; i < STATIONS.length; i++) {
            unlucky.insert(KM[i], STATIONS[i]);
        }
        out.add("  every toss is tails: " + unlucky.levels() + " level, no express at all");
        unlucky.steps().reset();
        unlucky.find(57);
        out.add("  find Marden: " + unlucky.steps().steps() + " steps, the same as the stopping service");
        out.add("  a skip list is only fast on average: bad luck makes it a plain list");

        out.add("");
        out.add("FIVE. The bill, at a thousand stations.");
        SkipList big = new SkipList(Coin.seeded(2026));
        for (int i = 0; i < 1000; i++) {
            big.insert(i * 2, "s" + i);
        }
        long avg = averageHopsTimesTen(1000, Coin.seeded(2026));
        out.add("  1,000 stations: " + big.levels() + " levels, finding a station takes " + avg / 10 + "." + avg % 10
                + " steps on average (the stopping service: 500.5)");
        out.add("  " + String.format("%,d", big.pointers()) + " forward pointers: about 2 per station instead of 1");
        out.add("  already in Java: java.util.concurrent.ConcurrentSkipListMap");
        return out.text();
    }
}
