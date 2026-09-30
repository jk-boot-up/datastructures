package com.jk.explore.staticarraygeneric;

/**
 * Tells the story of the generic static array in five acts, printing the real step counts.
 *
 * <p>The worked example is the same week of temperatures as the static-array project, stored three
 * ways by one class: as {@code Integer} temperatures, as {@code String} day names, and as
 * {@code Reading} records of our own.
 */
public final class GenericStaticArrayDemo {

    static final String[] DAYS = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    static final Integer[] WEEK = {21, 23, 19, 25, 24, 22, 20};
    static final int CAPACITY = 10;

    private GenericStaticArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static GenericStaticArray<Reading> readings() {
        GenericStaticArray<Reading> r = new GenericStaticArray<>(CAPACITY);
        for (int i = 0; i < WEEK.length; i++) {
            r.insertAt(i, new Reading(DAYS[i], WEEK[i]));
        }
        return r;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. One class, any element type.");
        GenericStaticArray<Integer> temps = GenericStaticArray.of(CAPACITY, WEEK);
        GenericStaticArray<String> days = GenericStaticArray.of(CAPACITY, DAYS);
        GenericStaticArray<Reading> readings = readings();
        out.add("  GenericStaticArray<Integer>: " + temps.traverse());
        out.add("  GenericStaticArray<String>:  " + days.traverse());
        out.add("  GenericStaticArray<Reading>: " + readings.traverse());
        out.add("  one class, three element types; temps.update(0, \"hot\") does not compile");

        out.add("");
        out.add("TWO. Inside: an array of references.");
        out.add("  new T[10] is not allowed: T is erased when the program runs");
        out.add("  so arr = (T[]) new Comparable[10]: " + temps.capacity() + " references, n = " + temps.size());
        GenericStaticArray<Integer> copy = temps.copyWithCapacity(31);
        out.add("  copyWithCapacity(31): " + temps.steps().copies() + " references copied; index 0 is the same object: "
                + temps.sameObjectAt(0, copy));

        out.add("");
        out.add("THREE. Searching with equals and compareTo.");
        temps.steps().reset();
        int at = temps.linearSearch(24);
        out.add("  linearSearch(24) = index " + at + ": " + temps.steps().compares() + " comparisons with equals");
        String fri = new String("Fri");
        out.add("  linearSearch(new String(\"Fri\")) = index " + days.linearSearch(fri) + ": equals compares the text");
        out.add("  days.get(4) == new String(\"Fri\") is " + (days.get(4) == fri) + ": == compares references");
        GenericStaticArray<Integer> sorted = GenericStaticArray.of(CAPACITY, 19, 20, 21, 23, 24, 25, 26);
        sorted.steps().reset();
        int found = sorted.binarySearch(24);
        out.add("  sorted " + sorted + ": binarySearch(24) = index " + found + ", "
                + sorted.steps().compares() + " comparisons with compareTo");
        GenericStaticArray<String> names = GenericStaticArray.of(CAPACITY, "Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed");
        names.steps().reset();
        int thu = names.binarySearch("Thu");
        out.add("  sorted " + names + ": binarySearch(\"Thu\") = index " + thu + ", "
                + names.steps().compares() + " comparisons");

        out.add("");
        out.add("FOUR. Insertion, deletion, overflow.");
        readings.steps().reset();
        readings.insertAt(2, new Reading("Wed*", 18));
        out.add("  insertAt(2, Wed* 18 C): " + readings.steps().shifts() + " shifts, n = " + readings.size());
        readings.steps().reset();
        Reading gone = readings.deleteAt(0);
        out.add("  deleteAt(0) removed " + gone + ": " + readings.steps().shifts()
                + " shifts; the freed place is set to null");
        out.add("  " + readings);
        readings.insertAt(readings.size(), new Reading("Mon", 27));
        readings.insertAt(readings.size(), new Reading("Tue", 28));
        readings.insertAt(readings.size(), new Reading("Wed", 29));
        try {
            readings.insertAt(0, new Reading("Thu", 30));
            out.add("  one more insert fits");
        } catch (IllegalStateException e) {
            out.add("  n = " + readings.size() + ", insertAt(0, Thu 30 C): " + e.getMessage());
        }

        out.add("");
        out.add("FIVE. One algorithm, many orders.");
        GenericStaticArray<Reading> week = readings();
        week.steps().reset();
        int hottest = week.findMax();
        out.add("  readings, findMax by temperature: " + week.get(hottest) + ", " + week.steps().compares() + " comparisons");
        out.add("  day names, findMax alphabetically: " + days.get(days.findMax()));
        week.steps().reset();
        week.reverse();
        out.add("  reverse: " + week.steps().swaps() + " swaps; " + week);
        out.add("  no sum(): adding needs numbers, and T can be any type");
        out.add("  already in Java: java.util.ArrayList<E> and Arrays.binarySearch(T[], key) are generic the same way");
        return out.text();
    }
}
