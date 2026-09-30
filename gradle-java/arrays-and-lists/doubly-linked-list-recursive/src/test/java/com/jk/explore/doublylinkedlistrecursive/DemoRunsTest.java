package com.jk.explore.doublylinkedlistrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = RecursiveDoublyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneRecursesBothWays() {
        prints("  displayBackward(mountain): print it, then displayBackward(prev)");
        prints("            displayBackward(null): past the head   <- base case");
        prints("  tail -> mountain <-> forest <-> dog <-> cake <-> beach <- head, depth 5");
    }

    @Test
    void actTwoWorksAtTheEnds() {
        prints("  insertAtBeginning(\"airport\") and insertAtEnd(\"river\"): depth 0, 6 pointer changes");
        prints("  deleteAtEnd() removed \"river\": depth 0, through tail");
        prints("  count() = 6, depth 6");
    }

    @Test
    void actThreeFindsAndLinks() {
        prints("  search(\"forest\") = position 4: 5 comparisons, depth 5");
        prints("  insertAtPosition(3, \"garden\"): the node before found at depth 2, then 4 pointers set");
        prints("  deleteByKey(\"cake\"): found at depth 3, then 2 pointers changed");
        prints("  head -> airport <-> beach <-> garden <-> dog <-> forest <-> mountain <- tail");
    }

    @Test
    void actFourReverses() {
        prints("  reverse(): swap this node's prev and next, then reverse from the old next: depth 5, 12 pointer changes");
    }

    @Test
    void actFiveOverflows() {
        prints("  count(): StackOverflowError, one frame per node");
    }
}
