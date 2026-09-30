package com.jk.explore.dynamicarray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = DynamicArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneRunsOutOfRoom() {
        prints("  a fifth song: ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4");
        prints("  growing by one place each time, 1,000 songs cost 499,494 copies");
    }

    @Test
    void actTwoSeparatesSizeFromCapacity() {
        prints("  capacity 4, size 3: [Blue Sky, Rain Dance, Night Drive]");
        prints("  append(\"Sunrise\"): 1 step, capacity 4, size 4, now full");
    }

    @Test
    void actThreeDoubles() {
        prints("  append(\"Paper Moon\"): full, new array of 8 places, 4 songs copied, then 1 write");
        prints("  append(\"Last Train\"): full again, new array of 16 places, 8 songs copied");
        prints("  9 songs appended, 2 resizes, 12 copies in total");
        prints("  1,000 appends by doubling: 1,020 copies, about 1 per song (growing by one: 499,494)");
    }

    @Test
    void actFourShiftsAndSpares() {
        prints("  insertAt(0, \"Intro\"): 9 songs shifted right");
        prints("  deleteAt(5) removed \"Paper Moon\": 4 songs shifted left");
        prints("  capacity 16, size 9: 7 places spare");
        prints("  get(9), a spare place: IndexOutOfBoundsException: Index 9 out of bounds for length 9");
        prints("  shrinkToFit(): capacity 9, 9 songs copied");
    }

    @Test
    void actFivePresentsTheBill() {
        prints("  the one append that resizes at 512 songs copies 512 of them");
        prints("  513 songs: capacity 1024, 511 places spare, almost half");
        prints("  deleteAtEnd down to 256 songs, a quarter of 1024: capacity halves to 512 (1 resize)");
    }
}
