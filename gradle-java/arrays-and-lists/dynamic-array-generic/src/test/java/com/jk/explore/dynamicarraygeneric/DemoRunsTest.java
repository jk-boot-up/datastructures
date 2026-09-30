package com.jk.explore.dynamicarraygeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericDynamicArrayDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneHoldsThreeTypes() {
        prints("  GenericDynamicArray<Integer>: [10, 20, 30]");
        prints("  GenericDynamicArray<Song>:    [Blue Sky (3:34), Rain Dance (3:07), Night Drive (4:03)]");
        prints("  inside each: (T[]) new Object[4], capacity 4, size 3");
    }

    @Test
    void actTwoCopiesReferences() {
        prints("  append(Paper Moon (2:56)): full, new array of 8 places, 4 references copied");
        prints("  index 0 still holds the very same Song object: true");
        prints("  9 songs appended, 2 resizes, capacity 16");
    }

    @Test
    void actThreeUsesEquals() {
        prints("  insertAt(0, Intro (0:42)): 9 songs shifted right");
        prints("  deleteAt(5) removed Paper Moon (2:56): 4 songs shifted left");
        prints("  linearSearch(new Song(\"Echoes\", 201)) = index 6 after 7 comparisons: a record's equals compares title and length");
        prints("  but it is a different object: holdsSameObject = false");
    }

    @Test
    void actFourClearsReferences() {
        prints("  deleteAtEnd() removed Last Train (3:52); its place is set to null so the Song can be freed");
        prints("  shrinkToFit(): capacity 8, 8 references copied");
    }

    @Test
    void actFivePresentsTheBill() {
        prints("  1,000 appends: 1,020 references copied, about 1 per append");
        prints("  deleteAtEnd down to 256 of capacity 1024: capacity halves to 512");
    }
}
