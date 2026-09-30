package com.jk.explore.doublylinkedlistgenericrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericRecursiveDoublyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneRecursesAlongPrev() {
        prints("  mountain 1200KB: not equal, recurse on prev");
        prints("      dog 610KB: equals   <- base case: found");
    }

    @Test
    void actTwoUsesAnyType() {
        prints("  <Integer> count() = 5, depth 5");
        prints("  <String>  head -> beach <-> cake <-> dog <-> forest <-> mountain <- tail, depth 5");
    }

    @Test
    void actThreeFindsRecursively() {
        prints("  insertAtBeginning and insertAtEnd: depth 0, 6 pointer changes");
        prints("  search(new Photo(\"dog\", 610)) = position 3: 4 equals calls, depth 4");
        prints("  insertAtPosition(3, garden 760KB): found at depth 2, 4 pointers set");
        prints("  deleteByKey(new Photo(\"cake\", 540)): found at depth 3, 2 pointers changed");
    }

    @Test
    void actFourReverses() {
        prints("  reverse(): depth 7, 16 pointer changes");
    }

    @Test
    void actFiveOverflows() {
        prints("  count() of 1,000,000 nodes: StackOverflowError, one frame per node");
    }
}
