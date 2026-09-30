package com.jk.explore.staticarrayrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = RecursiveStaticArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneTracesTheRecursion() {
        prints("  sum(0) = 21 + sum(1)");
        prints("                sum(7) = 0   <- base case: no elements left");
        prints("  sum() = 154, after 7 calls waiting on the stack at once");
    }

    @Test
    void actTwoMeasuresTheStack() {
        prints("  call-stack depth 7: one frame per element, each waiting for the rest");
        prints("  findMax = index 3 (25 C): depth 6, 6 comparisons on the way back up");
    }

    @Test
    void actThreeSearches() {
        prints("  linearSearch(24) = index 4: 5 comparisons, depth 5");
        prints("  sorted [19, 20, 21, 23, 24, 25, 26]: binarySearch(24) = index 4, 3 comparisons, depth 3");
    }

    @Test
    void actFourShiftsRecursively() {
        prints("  insertAt(2, 18): 5 shifts, depth 5; [21, 23, 18, 19, 25, 24, 22, 20]");
        prints("  deleteAt(0): 7 shifts, depth 7; [23, 18, 19, 25, 24, 22, 20]");
        prints("  reverse: 3 swaps, depth 3; [20, 22, 24, 25, 19, 18, 23]");
    }

    @Test
    void actFiveFindsTheLimit() {
        prints("  binarySearch on 1,000,000: 20 comparisons, depth 20: halving keeps the stack small");
        prints("  linearSearch on 1,000,000: StackOverflowError, one frame per element");
    }
}
