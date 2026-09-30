package com.jk.explore.dynamicarraygeneric;

/**
 * Tells the story of the generic dynamic array in five acts, printing the real step counts.
 *
 * <p>The worked example is the growing playlist of the dynamic-array project, now holding
 * {@code Song} records of our own, beside the same class holding titles and play counts.
 */
public final class GenericDynamicArrayDemo {

    static final Song[] SONGS = {new Song("Blue Sky", 214), new Song("Rain Dance", 187),
            new Song("Night Drive", 243), new Song("Sunrise", 198), new Song("Paper Moon", 176),
            new Song("Wild Honey", 225), new Song("Echoes", 201), new Song("Firefly", 169),
            new Song("Last Train", 232)};

    private GenericDynamicArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. One class, any element type.");
        GenericDynamicArray<String> titles = new GenericDynamicArray<>();
        GenericDynamicArray<Integer> plays = new GenericDynamicArray<>();
        GenericDynamicArray<Song> playlist = new GenericDynamicArray<>();
        for (int i = 0; i < 3; i++) {
            titles.append(SONGS[i].title());
            plays.append(10 * (i + 1));
            playlist.append(SONGS[i]);
        }
        out.add("  GenericDynamicArray<String>:  " + titles.traverse());
        out.add("  GenericDynamicArray<Integer>: " + plays.traverse());
        out.add("  GenericDynamicArray<Song>:    " + playlist.traverse());
        out.add("  inside each: (T[]) new Object[4], capacity " + playlist.capacity() + ", size " + playlist.size());

        out.add("");
        out.add("TWO. Full? Double it, copying references.");
        playlist.append(SONGS[3]);
        playlist.steps().reset();
        playlist.append(SONGS[4]);
        out.add("  append(" + SONGS[4] + "): full, new array of " + playlist.capacity() + " places, "
                + playlist.steps().copies() + " references copied");
        out.add("  index 0 still holds the very same Song object: " + playlist.holdsSameObject(0, SONGS[0]));
        for (int i = 5; i < 9; i++) {
            playlist.append(SONGS[i]);
        }
        out.add("  9 songs appended, " + playlist.resizes() + " resizes, capacity " + playlist.capacity());

        out.add("");
        out.add("THREE. The middle, and equals.");
        playlist.steps().reset();
        playlist.insertAt(0, new Song("Intro", 42));
        out.add("  insertAt(0, Intro (0:42)): " + playlist.steps().shifts() + " songs shifted right");
        playlist.steps().reset();
        Song removed = playlist.deleteAt(5);
        out.add("  deleteAt(5) removed " + removed + ": " + playlist.steps().shifts() + " songs shifted left");
        Song echoes = new Song("Echoes", 201);
        playlist.steps().reset();
        int at = playlist.linearSearch(echoes);
        out.add("  linearSearch(new Song(\"Echoes\", 201)) = index " + at + " after " + playlist.steps().compares()
                + " comparisons: a record's equals compares title and length");
        out.add("  but it is a different object: holdsSameObject = " + playlist.holdsSameObject(at, echoes));

        out.add("");
        out.add("FOUR. Deleting clears references.");
        out.add("  capacity " + playlist.capacity() + ", size " + playlist.size() + ": "
                + (playlist.capacity() - playlist.size()) + " places spare, all null");
        Song last = playlist.deleteAtEnd();
        out.add("  deleteAtEnd() removed " + last + "; its place is set to null so the Song can be freed");
        playlist.steps().reset();
        playlist.shrinkToFit();
        out.add("  shrinkToFit(): capacity " + playlist.capacity() + ", " + playlist.steps().copies() + " references copied");

        out.add("");
        out.add("FIVE. The bill.");
        GenericDynamicArray<Integer> big = new GenericDynamicArray<>();
        for (int i = 0; i < 1000; i++) {
            big.append(i);
        }
        out.add("  1,000 appends: " + String.format("%,d", big.steps().copies()) + " references copied, about 1 per append");
        while (big.size() > 256) {
            big.deleteAtEnd();
        }
        out.add("  deleteAtEnd down to 256 of capacity 1024: capacity halves to " + big.capacity());
        out.add("  the same costs as the String-only dynamic array: generics change the type, not the algorithm");
        out.add("  already in Java: java.util.ArrayList<E>, written exactly this way on an Object[]");
        return out.text();
    }
}
