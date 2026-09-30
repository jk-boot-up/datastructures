package com.jk.explore.dynamicarraygenericrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericRecursiveDynamicArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneTracesARecursiveCopyOfReferences() {
        prints("    copy(0): newArr[0] = reference to Blue Sky, then copy(1)");
        prints("            copy(4): no references left   <- base case");
        prints("  append(Paper Moon (2:56)): 4 references copied at depth 4; index 0 is still the same Song: true");
    }

    @Test
    void actTwoGrows() {
        prints("  9 songs: 2 resizes, capacity 16, 12 references copied since the first resize, deepest copy 8");
        prints("  GenericRecursiveDynamicArray<Integer>: [10, 20, 30, 40, 50], depth 5");
    }

    @Test
    void actThreeWorksInTheMiddle() {
        prints("  insertAt(0, Intro (0:42)): 9 shifts, depth 9");
        prints("  deleteAt(5) removed Paper Moon (2:56): 4 shifts, depth 4");
        prints("  linearSearch(new Song(\"Echoes\", 201)) = index 6: 7 equals calls, depth 7");
    }

    @Test
    void actFourShrinks() {
        prints("  shrinkToFit(): capacity 8, 8 references copied at depth 8");
    }

    @Test
    void actFiveOverflows() {
        prints("  1,000,000 appends: StackOverflowError inside a resize, long before the end");
    }
}
