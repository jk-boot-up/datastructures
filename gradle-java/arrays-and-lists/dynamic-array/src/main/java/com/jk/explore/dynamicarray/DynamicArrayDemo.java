package com.jk.explore.dynamicarray;

/**
 * Tells the story of the dynamic array in five acts, printing the real step counts.
 *
 * <p>The worked example is a music playlist that grows as songs are added.
 */
public final class DynamicArrayDemo {

    static final String[] SONGS = {"Blue Sky", "Rain Dance", "Night Drive", "Sunrise", "Paper Moon",
            "Wild Honey", "Echoes", "Firefly", "Last Train"};

    private DynamicArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Copies needed to append {@code n} songs one by one from capacity 4, doubling or growing by one. */
    static long copiesToAdd(int n, boolean doubling) {
        DynamicArray list = new DynamicArray(DynamicArray.INITIAL_CAPACITY, doubling);
        for (int i = 0; i < n; i++) {
            list.append("song " + i);
        }
        return list.steps().copies();
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. A playlist in a fixed array.");
        String[] fixed = {SONGS[0], SONGS[1], SONGS[2], SONGS[3]};
        out.add("  String[] playlist = new String[4]: " + String.join(", ", fixed));
        try {
            fixed[4] = SONGS[4];
            out.add("  a fifth song fits");
        } catch (ArrayIndexOutOfBoundsException e) {
            out.add("  a fifth song: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        long plusOne = copiesToAdd(1000, false);
        out.add("  growing by one place each time, 1,000 songs cost " + String.format("%,d", plusOne) + " copies");

        out.add("");
        out.add("TWO. Size and capacity.");
        DynamicArray playlist = new DynamicArray();
        for (int i = 0; i < 3; i++) {
            playlist.append(SONGS[i]);
        }
        out.add("  capacity " + playlist.capacity() + ", size " + playlist.size() + ": " + playlist);
        playlist.steps().reset();
        playlist.append(SONGS[3]);
        out.add("  append(\"" + SONGS[3] + "\"): " + playlist.steps().total() + " step, capacity "
                + playlist.capacity() + ", size " + playlist.size() + ", now full");

        out.add("");
        out.add("THREE. Full? Double it.");
        playlist.steps().reset();
        playlist.append(SONGS[4]);
        out.add("  append(\"" + SONGS[4] + "\"): full, new array of " + playlist.capacity() + " places, "
                + playlist.steps().copies() + " songs copied, then 1 write");
        for (int i = 5; i < 8; i++) {
            playlist.append(SONGS[i]);
        }
        out.add("  3 more songs: 1 step each, capacity " + playlist.capacity() + ", size " + playlist.size());
        playlist.steps().reset();
        playlist.append(SONGS[8]);
        out.add("  append(\"" + SONGS[8] + "\"): full again, new array of " + playlist.capacity() + " places, "
                + playlist.steps().copies() + " songs copied");
        out.add("  9 songs appended, " + playlist.resizes() + " resizes, " + (4 + 8) + " copies in total");
        long doubling = copiesToAdd(1000, true);
        out.add("  1,000 appends by doubling: " + String.format("%,d", doubling) + " copies, about "
                + (doubling + 500) / 1000 + " per song (growing by one: " + String.format("%,d", plusOne) + ")");

        out.add("");
        out.add("FOUR. The middle, and the spare places.");
        playlist.steps().reset();
        playlist.insertAt(0, "Intro");
        out.add("  insertAt(0, \"Intro\"): " + playlist.steps().shifts() + " songs shifted right");
        playlist.steps().reset();
        String removed = playlist.deleteAt(5);
        out.add("  deleteAt(5) removed \"" + removed + "\": " + playlist.steps().shifts() + " songs shifted left");
        out.add("  capacity " + playlist.capacity() + ", size " + playlist.size() + ": "
                + (playlist.capacity() - playlist.size()) + " places spare");
        try {
            playlist.get(playlist.size());
            out.add("  index " + playlist.size() + " can be read");
        } catch (IndexOutOfBoundsException e) {
            out.add("  get(" + playlist.size() + "), a spare place: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        playlist.steps().reset();
        playlist.shrinkToFit();
        out.add("  shrinkToFit(): capacity " + playlist.capacity() + ", " + playlist.steps().copies() + " songs copied");

        out.add("");
        out.add("FIVE. The bill.");
        DynamicArray big = new DynamicArray();
        for (int i = 0; i < 512; i++) {
            big.append("song " + i);
        }
        big.steps().reset();
        big.append("song 512");
        out.add("  the one append that resizes at 512 songs copies " + big.steps().copies() + " of them");
        out.add("  513 songs: capacity " + big.capacity() + ", " + (big.capacity() - big.size())
                + " places spare, almost half");
        int before = big.resizes();
        while (big.size() > 256) {
            big.deleteAtEnd();
        }
        out.add("  deleteAtEnd down to 256 songs, a quarter of 1024: capacity halves to " + big.capacity()
                + " (" + (big.resizes() - before) + " resize)");
        out.add("  already in Java: java.util.ArrayList, which starts at 10 places and grows by half each time");
        return out.text();
    }
}
