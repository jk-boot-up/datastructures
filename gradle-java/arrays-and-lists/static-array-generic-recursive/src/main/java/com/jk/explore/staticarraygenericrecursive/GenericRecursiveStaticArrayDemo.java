package com.jk.explore.staticarraygenericrecursive;

/**
 * Tells the story of the generic, recursive static array in five acts, printing the real counts
 * and the real depth of every recursion.
 *
 * <p>The worked example is the same week as the other static-array projects, held as {@code String}
 * day names, {@code Integer} temperatures and {@code Reading} records.
 */
public final class GenericRecursiveStaticArrayDemo {

    static final String[] DAYS = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    static final Integer[] WEEK = {21, 23, 19, 25, 24, 22, 20};
    static final int CAPACITY = 10;

    private GenericRecursiveStaticArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static GenericRecursiveStaticArray<Reading> readings() {
        Reading[] r = new Reading[WEEK.length];
        for (int i = 0; i < WEEK.length; i++) {
            r[i] = new Reading(DAYS[i], WEEK[i]);
        }
        return GenericRecursiveStaticArray.of(CAPACITY, r);
    }

    /** Prints how linearSearch(key, i) unfolds, one call per line, until a match or the base case. */
    static <T> void traceSearch(T[] a, T key, int i, String indent, Lines out) {
        if (i == a.length) {
            out.add(indent + "linearSearch(" + i + "): no elements left, -1   <- base case");
            return;
        }
        if (a[i].equals(key)) {
            out.add(indent + "linearSearch(" + i + "): " + a[i] + ".equals(" + key + ") -> found, index " + i + "   <- base case");
            return;
        }
        out.add(indent + "linearSearch(" + i + "): " + a[i] + ".equals(" + key + ")? no -> linearSearch(" + (i + 1) + ")");
        traceSearch(a, key, i + 1, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Recursion, for any element type.");
        traceSearch(DAYS, "Thu", 0, "  ", out);
        GenericRecursiveStaticArray<String> days = GenericRecursiveStaticArray.of(CAPACITY, DAYS);
        days.steps().reset();
        int thu = days.linearSearch("Thu");
        out.add("  linearSearch(\"Thu\") = index " + thu + ": " + days.steps().compares() + " equals calls, depth "
                + days.steps().maxDepth());

        out.add("");
        out.add("TWO. The same recursion, three types.");
        GenericRecursiveStaticArray<Integer> temps = GenericRecursiveStaticArray.of(CAPACITY, WEEK);
        GenericRecursiveStaticArray<Reading> readings = readings();
        temps.steps().reset();
        out.add("  <Integer> traverse: " + temps.traverse() + ", depth " + temps.steps().maxDepth());
        days.steps().reset();
        out.add("  <String>  traverse: " + days.traverse() + ", depth " + days.steps().maxDepth());
        readings.steps().reset();
        out.add("  <Reading> traverse: " + readings.traverse() + ", depth " + readings.steps().maxDepth());
        String fri = new String("Fri");
        days.steps().reset();
        out.add("  linearSearch(new String(\"Fri\")) = index " + days.linearSearch(fri) + " at depth "
                + days.steps().maxDepth() + ": equals compares the text, == would not match");

        out.add("");
        out.add("THREE. compareTo, recursively.");
        GenericRecursiveStaticArray<String> names = GenericRecursiveStaticArray.of(CAPACITY, "Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed");
        names.steps().reset();
        int found = names.binarySearch("Thu");
        out.add("  sorted " + names + ": binarySearch(\"Thu\") = index " + found + ", "
                + names.steps().compares() + " compareTo calls, depth " + names.steps().maxDepth());
        readings.steps().reset();
        int max = readings.findMax();
        out.add("  findMax on readings = " + readings.get(max) + ": " + readings.steps().compares()
                + " compareTo calls on the way back up, depth " + readings.steps().maxDepth());
        days.steps().reset();
        out.add("  findMax on day names = " + days.get(days.findMax()) + ": the same method, alphabetical order");

        out.add("");
        out.add("FOUR. Insertion, deletion and reversal, recursively.");
        readings.steps().reset();
        readings.insertAt(2, new Reading("Wed*", 18));
        out.add("  insertAt(2, Wed* 18 C): " + readings.steps().shifts() + " shifts, depth " + readings.steps().maxDepth());
        readings.steps().reset();
        Reading gone = readings.deleteAt(0);
        out.add("  deleteAt(0) removed " + gone + ": " + readings.steps().shifts() + " shifts, depth "
                + readings.steps().maxDepth() + "; the freed place is set to null");
        readings.steps().reset();
        readings.reverse();
        out.add("  reverse: " + readings.steps().swaps() + " swaps, depth " + readings.steps().maxDepth() + "; " + readings);

        out.add("");
        out.add("FIVE. The limit of recursion.");
        int million = 1_000_000;
        Integer[] big = new Integer[million];
        for (int i = 0; i < million; i++) {
            big[i] = i * 2;
        }
        GenericRecursiveStaticArray<Integer> bigR = GenericRecursiveStaticArray.of(million, big);
        bigR.steps().reset();
        bigR.binarySearch(1_333_332);
        out.add("  binarySearch on 1,000,000 Integers: " + bigR.steps().compares() + " compareTo calls, depth "
                + bigR.steps().maxDepth());
        bigR.steps().reset();
        try {
            bigR.linearSearch(-1);
            out.add("  linearSearch on 1,000,000: finished");
        } catch (StackOverflowError e) {
            out.add("  linearSearch on 1,000,000: StackOverflowError, one frame per element");
        }
        out.add("  generics change what is compared, recursion changes how much stack is used; neither changes the time");
        return out.text();
    }
}
