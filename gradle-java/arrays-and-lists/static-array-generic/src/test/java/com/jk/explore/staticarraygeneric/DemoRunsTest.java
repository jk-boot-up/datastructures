package com.jk.explore.staticarraygeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericStaticArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneHoldsThreeTypes() {
        prints("  GenericStaticArray<Integer>: [21, 23, 19, 25, 24, 22, 20]");
        prints("  GenericStaticArray<String>:  [Mon, Tue, Wed, Thu, Fri, Sat, Sun]");
        prints("  GenericStaticArray<Reading>: [Mon 21 C, Tue 23 C, Wed 19 C, Thu 25 C, Fri 24 C, Sat 22 C, Sun 20 C]");
    }

    @Test
    void actTwoStoresReferences() {
        prints("  so arr = (T[]) new Comparable[10]: 10 references, n = 7");
        prints("  copyWithCapacity(31): 7 references copied; index 0 is the same object: true");
    }

    @Test
    void actThreeSearches() {
        prints("  linearSearch(24) = index 4: 5 comparisons with equals");
        prints("  linearSearch(new String(\"Fri\")) = index 4: equals compares the text");
        prints("  days.get(4) == new String(\"Fri\") is false: == compares references");
        prints("  sorted [19, 20, 21, 23, 24, 25, 26]: binarySearch(24) = index 4, 3 comparisons with compareTo");
        prints("  sorted [Fri, Mon, Sat, Sun, Thu, Tue, Wed]: binarySearch(\"Thu\") = index 4, 3 comparisons");
    }

    @Test
    void actFourInsertsAndDeletes() {
        prints("  insertAt(2, Wed* 18 C): 5 shifts, n = 8");
        prints("  deleteAt(0) removed Mon 21 C: 7 shifts; the freed place is set to null");
        prints("  n = 10, insertAt(0, Thu 30 C): overflow: the array is full (10 of 10)");
    }

    @Test
    void actFiveUsesEachTypesOrder() {
        prints("  readings, findMax by temperature: Thu 25 C, 6 comparisons");
        prints("  day names, findMax alphabetically: Wed");
        prints("  reverse: 3 swaps; [Sun 20 C, Sat 22 C, Fri 24 C, Thu 25 C, Wed 19 C, Tue 23 C, Mon 21 C]");
    }
}
