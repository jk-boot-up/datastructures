package com.jk.explore.staticarraygenericrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericRecursiveStaticArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneTracesARecursiveSearch() {
        prints("  linearSearch(0): Mon.equals(Thu)? no -> linearSearch(1)");
        prints("        linearSearch(3): Thu.equals(Thu) -> found, index 3   <- base case");
        prints("  linearSearch(\"Thu\") = index 3: 4 equals calls, depth 4");
    }

    @Test
    void actTwoUsesThreeTypes() {
        prints("  <Integer> traverse: [21, 23, 19, 25, 24, 22, 20], depth 7");
        prints("  <String>  traverse: [Mon, Tue, Wed, Thu, Fri, Sat, Sun], depth 7");
        prints("  linearSearch(new String(\"Fri\")) = index 4 at depth 5: equals compares the text, == would not match");
    }

    @Test
    void actThreeComparesRecursively() {
        prints("  sorted [Fri, Mon, Sat, Sun, Thu, Tue, Wed]: binarySearch(\"Thu\") = index 4, 3 compareTo calls, depth 3");
        prints("  findMax on readings = Thu 25 C: 6 compareTo calls on the way back up, depth 6");
        prints("  findMax on day names = Wed: the same method, alphabetical order");
    }

    @Test
    void actFourShiftsRecursively() {
        prints("  insertAt(2, Wed* 18 C): 5 shifts, depth 5");
        prints("  deleteAt(0) removed Mon 21 C: 7 shifts, depth 7; the freed place is set to null");
        prints("  reverse: 3 swaps, depth 3; [Sun 20 C, Sat 22 C, Fri 24 C, Thu 25 C, Wed 19 C, Wed* 18 C, Tue 23 C]");
    }

    @Test
    void actFiveFindsTheLimit() {
        prints("  binarySearch on 1,000,000 Integers: 20 compareTo calls, depth 20");
        prints("  linearSearch on 1,000,000: StackOverflowError, one frame per element");
    }
}
