package com.jk.explore.staticarray;

/**
 * Tells the story of the static array in five acts, printing the real step counts.
 *
 * <p>The worked example is a week of daily temperatures in degrees Celsius, Monday at index 0
 * through Sunday at index 6, in an array of capacity 10.
 */
public final class StaticArrayDemo {

    static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    static final int[] WEEK = {21, 23, 19, 25, 24, 22, 20};
    static final int CAPACITY = 10;

    private StaticArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Seven separate variables.");
        WeekInVariables byHand = new WeekInVariables(21, 23, 19, 25, 24, 22, 20);
        out.add("  mon=21 tue=23 wed=19 thu=25 fri=24 sat=22 sun=20");
        out.add("  the maximum, by hand: " + WeekInVariables.HAND_WRITTEN_COMPARISONS
                + " comparisons written out one by one, answer " + byHand.hottest() + " C");
        out.add("  \"day number 3\" needs a switch with 7 branches: " + byHand.day(3) + " C");

        out.add("");
        out.add("TWO. One array: int arr[10], n = 7.");
        StaticArray a = StaticArray.of(CAPACITY, WEEK);
        out.add("  traverse: " + a.traverse());
        out.add("  capacity " + a.capacity() + ", n = " + a.size() + ", " + a.bytes() + " bytes in one block");
        out.add("  address of arr[3] = base + 3 x " + StaticArray.ELEMENT_BYTES + " = base + "
                + 3 * StaticArray.ELEMENT_BYTES + ": computed, not searched");

        out.add("");
        out.add("THREE. Access, update, search.");
        a.steps().reset();
        out.add("  get(3) = " + a.get(3) + " C (" + DAYS[3] + "): " + a.steps().reads() + " step");
        a.steps().reset();
        a.update(5, 26);
        out.add("  update(5, 26) (" + DAYS[5] + "): " + a.steps().writes() + " step");
        a.steps().reset();
        int at = a.linearSearch(24);
        out.add("  linearSearch(24): index " + at + " after " + a.steps().compares() + " comparisons");
        a.steps().reset();
        int miss = a.linearSearch(30);
        out.add("  linearSearch(30): " + miss + " after " + a.steps().compares() + " comparisons");
        StaticArray sorted = StaticArray.of(CAPACITY, 19, 20, 21, 23, 24, 25, 26);
        sorted.steps().reset();
        int found = sorted.binarySearch(24);
        out.add("  sorted " + sorted + ": binarySearch(24) = index " + found + " after "
                + sorted.steps().compares() + " comparisons");

        out.add("");
        out.add("FOUR. Insertion, deletion, overflow.");
        a.steps().reset();
        a.insertAt(2, 18);
        out.add("  insertAt(2, 18): " + a.steps().shifts() + " elements shifted right, n = " + a.size());
        out.add("  " + a);
        a.steps().reset();
        int gone = a.deleteAt(0);
        out.add("  deleteAt(0) removed " + gone + ": " + a.steps().shifts() + " elements shifted left, n = " + a.size());
        a.insertAt(a.size(), 27);
        a.insertAt(a.size(), 28);
        a.insertAt(a.size(), 29);
        try {
            a.insertAt(0, 30);
            out.add("  one more insert fits");
        } catch (IllegalStateException e) {
            out.add("  n = " + a.size() + ", insertAt(0, 30): " + e.getMessage());
        }
        try {
            a.get(a.size());
            out.add("  get(" + a.size() + ") works");
        } catch (IndexOutOfBoundsException e) {
            out.add("  get(" + a.size() + "): " + e.getMessage());
        }

        out.add("");
        out.add("FIVE. The bill.");
        int million = 1_000_000;
        int[] big = new int[million];
        for (int i = 0; i < million; i++) {
            big[i] = i * 2;
        }
        StaticArray bigI = StaticArray.of(million, big);
        bigI.steps().reset();
        bigI.binarySearch(1_333_332);
        out.add("  binarySearch on 1,000,000 sorted elements: " + bigI.steps().compares() + " comparisons");
        bigI.steps().reset();
        out.add("  linearSearch on 1,000,000: " + bigI.linearSearch(-1) + " after "
                + String.format("%,d", bigI.steps().compares()) + " comparisons, with O(1) extra space");
        StaticArray week = StaticArray.of(7, WEEK);
        week.steps().reset();
        StaticArray month = week.copyWithCapacity(31);
        out.add("  growing 7 to 31: a new array and " + week.steps().copies() + " copies; capacity " + month.capacity());
        out.add("  every loop here uses O(1) extra space; the static-array-recursive project writes them recursively");
        out.add("  already in Java: int[] itself, and java.util.Arrays for fill, copyOf, sort, binarySearch");
        return out.text();
    }
}
