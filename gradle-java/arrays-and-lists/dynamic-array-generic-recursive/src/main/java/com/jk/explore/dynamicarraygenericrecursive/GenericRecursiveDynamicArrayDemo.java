package com.jk.explore.dynamicarraygenericrecursive;

/**
 * Tells the story of the generic, recursive dynamic array in five acts, printing the real counts
 * and the real depth of every recursion.
 *
 * <p>The worked example is the playlist of {@code Song} records from dynamic-array-generic.
 */
public final class GenericRecursiveDynamicArrayDemo {

    static final Song[] SONGS = {new Song("Blue Sky", 214), new Song("Rain Dance", 187),
            new Song("Night Drive", 243), new Song("Sunrise", 198), new Song("Paper Moon", 176),
            new Song("Wild Honey", 225), new Song("Echoes", 201), new Song("Firefly", 169),
            new Song("Last Train", 232)};

    private GenericRecursiveDynamicArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Prints how copy(i) unfolds during a resize: one call per reference, down to the base case. */
    static void traceCopy(Song[] a, int i, String indent, Lines out) {
        if (i == a.length) {
            out.add(indent + "copy(" + i + "): no references left   <- base case");
            return;
        }
        out.add(indent + "copy(" + i + "): newArr[" + i + "] = reference to " + a[i].title() + ", then copy(" + (i + 1) + ")");
        traceCopy(a, i + 1, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. A recursive copy of references.");
        GenericRecursiveDynamicArray<Song> playlist = new GenericRecursiveDynamicArray<>();
        for (int i = 0; i < 4; i++) {
            playlist.append(SONGS[i]);
        }
        out.add("  GenericRecursiveDynamicArray<Song>: capacity " + playlist.capacity() + ", size " + playlist.size() + ", full");
        traceCopy(new Song[] {SONGS[0], SONGS[1], SONGS[2], SONGS[3]}, 0, "    ", out);
        playlist.steps().reset();
        playlist.append(SONGS[4]);
        out.add("  append(" + SONGS[4] + "): " + playlist.steps().copies() + " references copied at depth "
                + playlist.steps().maxDepth() + "; index 0 is still the same Song: " + playlist.holdsSameObject(0, SONGS[0]));

        out.add("");
        out.add("TWO. Growing, and any type.");
        for (int i = 5; i < 9; i++) {
            playlist.append(SONGS[i]);
        }
        out.add("  9 songs: " + playlist.resizes() + " resizes, capacity " + playlist.capacity() + ", "
                + playlist.steps().copies() + " references copied since the first resize, deepest copy "
                + playlist.steps().maxDepth());
        GenericRecursiveDynamicArray<Integer> plays = new GenericRecursiveDynamicArray<>();
        for (int i = 1; i <= 5; i++) {
            plays.append(i * 10);
        }
        plays.steps().reset();
        out.add("  GenericRecursiveDynamicArray<Integer>: " + plays.traverse() + ", depth " + plays.steps().maxDepth());

        out.add("");
        out.add("THREE. The middle, and equals, recursively.");
        playlist.steps().reset();
        playlist.insertAt(0, new Song("Intro", 42));
        out.add("  insertAt(0, Intro (0:42)): " + playlist.steps().shifts() + " shifts, depth " + playlist.steps().maxDepth());
        playlist.steps().reset();
        Song removed = playlist.deleteAt(5);
        out.add("  deleteAt(5) removed " + removed + ": " + playlist.steps().shifts() + " shifts, depth "
                + playlist.steps().maxDepth());
        Song echoes = new Song("Echoes", 201);
        playlist.steps().reset();
        int at = playlist.linearSearch(echoes);
        out.add("  linearSearch(new Song(\"Echoes\", 201)) = index " + at + ": " + playlist.steps().compares()
                + " equals calls, depth " + playlist.steps().maxDepth());

        out.add("");
        out.add("FOUR. Shrinking, recursively.");
        Song last = playlist.deleteAtEnd();
        out.add("  deleteAtEnd() removed " + last + " and set its place to null");
        playlist.steps().reset();
        playlist.shrinkToFit();
        out.add("  shrinkToFit(): capacity " + playlist.capacity() + ", " + playlist.steps().copies()
                + " references copied at depth " + playlist.steps().maxDepth());

        out.add("");
        out.add("FIVE. The limit of recursion.");
        GenericRecursiveDynamicArray<Integer> big = new GenericRecursiveDynamicArray<>();
        try {
            for (int i = 0; i < 1_000_000; i++) {
                big.append(i);
            }
            out.add("  1,000,000 appends: finished");
        } catch (StackOverflowError e) {
            out.add("  1,000,000 appends: StackOverflowError inside a resize, long before the end");
        }
        out.add("  generics change what is stored; recursion changes how much stack a resize needs");
        return out.text();
    }
}
