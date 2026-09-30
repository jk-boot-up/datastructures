package com.jk.explore.dynamicarrayrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = RecursiveDynamicArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneTracesARecursiveCopy() {
        prints("    copy(0): newArr[0] = Blue Sky, then copy(1)");
        prints("            copy(4): no elements left   <- base case");
        prints("  4 copies at depth 4, then 1 write; capacity 8");
    }

    @Test
    void actTwoGrows() {
        prints("  append(\"Last Train\"): capacity 16, 8 copies at depth 8");
        prints("  9 songs appended, 2 resizes, 12 copies in total");
    }

    @Test
    void actThreeWorksInTheMiddle() {
        prints("  insertAt(0, \"Intro\"): 9 shifts, depth 9");
        prints("  deleteAt(5) removed \"Paper Moon\": 4 shifts, depth 4");
        prints("  linearSearch(\"Firefly\") = index 7: 8 comparisons, depth 8");
    }

    @Test
    void actFourShrinks() {
        prints("  shrinkToFit(): capacity 9, 9 copies at depth 9");
        prints("  17 songs in 32 places, deleteAtEnd down to 8: capacity 16 (1 resize, 8 copies at depth 8)");
    }

    @Test
    void actFiveOverflows() {
        prints("  1,000,000 appends: StackOverflowError inside a resize, long before the end");
    }
}
