package com.jk.explore.dynamicarrayrecursive;

/**
 * Tells the story of the recursive dynamic array in five acts, printing the real counts and the
 * real depth of every recursion.
 *
 * <p>The worked example is the same growing music playlist as the dynamic-array project.
 */
public final class RecursiveDynamicArrayDemo {

    static final String[] SONGS = {"Blue Sky", "Rain Dance", "Night Drive", "Sunrise", "Paper Moon",
            "Wild Honey", "Echoes", "Firefly", "Last Train"};

    private RecursiveDynamicArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Prints how copy(i) unfolds during a resize: one call per element, down to the base case. */
    static void traceCopy(String[] a, int i, String indent, Lines out) {
        if (i == a.length) {
            out.add(indent + "copy(" + i + "): no elements left   <- base case");
            return;
        }
        out.add(indent + "copy(" + i + "): newArr[" + i + "] = " + a[i] + ", then copy(" + (i + 1) + ")");
        traceCopy(a, i + 1, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. A recursive copy.");
        RecursiveDynamicArray playlist = new RecursiveDynamicArray();
        for (int i = 0; i < 4; i++) {
            playlist.append(SONGS[i]);
        }
        out.add("  capacity " + playlist.capacity() + ", size " + playlist.size() + ": full");
        out.add("  append(\"" + SONGS[4] + "\") must resize to 8, copying recursively:");
        traceCopy(new String[] {SONGS[0], SONGS[1], SONGS[2], SONGS[3]}, 0, "    ", out);
        playlist.steps().reset();
        playlist.append(SONGS[4]);
        out.add("  " + playlist.steps().copies() + " copies at depth " + playlist.steps().maxDepth()
                + ", then 1 write; capacity " + playlist.capacity());

        out.add("");
        out.add("TWO. Growing.");
        for (int i = 5; i < 8; i++) {
            playlist.append(SONGS[i]);
        }
        playlist.steps().reset();
        playlist.append(SONGS[8]);
        out.add("  append(\"" + SONGS[8] + "\"): capacity " + playlist.capacity() + ", "
                + playlist.steps().copies() + " copies at depth " + playlist.steps().maxDepth());
        out.add("  9 songs appended, " + playlist.resizes() + " resizes, 12 copies in total");
        playlist.steps().reset();
        out.add("  traverse: " + playlist.traverse() + ", depth " + playlist.steps().maxDepth());

        out.add("");
        out.add("THREE. The middle, recursively.");
        playlist.steps().reset();
        playlist.insertAt(0, "Intro");
        out.add("  insertAt(0, \"Intro\"): " + playlist.steps().shifts() + " shifts, depth " + playlist.steps().maxDepth());
        playlist.steps().reset();
        String removed = playlist.deleteAt(5);
        out.add("  deleteAt(5) removed \"" + removed + "\": " + playlist.steps().shifts() + " shifts, depth "
                + playlist.steps().maxDepth());
        playlist.steps().reset();
        int at = playlist.linearSearch("Firefly");
        out.add("  linearSearch(\"Firefly\") = index " + at + ": " + playlist.steps().compares()
                + " comparisons, depth " + playlist.steps().maxDepth());

        out.add("");
        out.add("FOUR. Shrinking, recursively.");
        out.add("  capacity " + playlist.capacity() + ", size " + playlist.size());
        playlist.steps().reset();
        playlist.shrinkToFit();
        out.add("  shrinkToFit(): capacity " + playlist.capacity() + ", " + playlist.steps().copies()
                + " copies at depth " + playlist.steps().maxDepth());
        RecursiveDynamicArray many = new RecursiveDynamicArray();
        for (int i = 0; i < 17; i++) {
            many.append("song " + i);
        }
        int before = many.resizes();
        many.steps().reset();
        while (many.size() > 8) {
            many.deleteAtEnd();
        }
        out.add("  17 songs in 32 places, deleteAtEnd down to 8: capacity " + many.capacity() + " ("
                + (many.resizes() - before) + " resize, " + many.steps().copies() + " copies at depth "
                + many.steps().maxDepth() + ")");

        out.add("");
        out.add("FIVE. The limit of recursion.");
        RecursiveDynamicArray big = new RecursiveDynamicArray();
        try {
            for (int i = 0; i < 1_000_000; i++) {
                big.append("song " + i);
            }
            out.add("  1,000,000 appends: finished");
        } catch (StackOverflowError e) {
            out.add("  1,000,000 appends: StackOverflowError inside a resize, long before the end");
        }
        out.add("  a recursive copy needs one frame per element, so a large resize overflows the call stack");
        out.add("  the dynamic-array project copies with a loop, in O(1) extra space, and finishes");
        return out.text();
    }
}
