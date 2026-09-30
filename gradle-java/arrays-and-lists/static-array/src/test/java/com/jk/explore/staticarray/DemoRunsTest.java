package com.jk.explore.staticarray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = StaticArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneWritesTheWeekOutByHand() {
        prints("  the maximum, by hand: 6 comparisons written out one by one, answer 25 C");
    }

    @Test
    void actTwoComputesAnAddress() {
        prints("  capacity 10, n = 7, 40 bytes in one block");
        prints("  address of arr[3] = base + 3 x 4 = base + 12: computed, not searched");
    }

    @Test
    void actThreeCountsComparisons() {
        prints("  get(3) = 25 C (Thursday): 1 step");
        prints("  linearSearch(24): index 4 after 5 comparisons");
        prints("  linearSearch(30): -1 after 7 comparisons");
        prints("  sorted [19, 20, 21, 23, 24, 25, 26]: binarySearch(24) = index 4 after 3 comparisons");
    }

    @Test
    void actFourShiftsAndOverflows() {
        prints("  insertAt(2, 18): 5 elements shifted right, n = 8");
        prints("  deleteAt(0) removed 21: 7 elements shifted left, n = 7");
        prints("  n = 10, insertAt(0, 30): overflow: the array is full (10 of 10)");
    }

    @Test
    void actFivePresentsTheBill() {
        prints("  binarySearch on 1,000,000 sorted elements: 20 comparisons");
        prints("  linearSearch on 1,000,000: -1 after 1,000,000 comparisons, with O(1) extra space");
        prints("  growing 7 to 31: a new array and 7 copies; capacity 31");
    }
}
