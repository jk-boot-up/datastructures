package com.jk.explore.circularlinkedlistrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = RecursiveCircularLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneStopsAtLast() {
        prints("  count(Ann) = 1 + count(Ben)");
        prints("        count(Dan) = 1   <- base case: this is last, the lap is over");
        prints("  count() = 4, depth 4; waiting for null would recurse for ever and overflow");
    }

    @Test
    void actTwoGoesOneLap() {
        prints("  display(): Ann -> Ben -> Cat -> Dan -> (back to Ann), depth 4");
        prints("  insertAtBeginning(\"Eve\") and insertAtEnd(\"Fay\"): depth 0, 5 pointer changes");
    }

    @Test
    void actThreeFindsAndLinks() {
        prints("  search(\"Dan\") = position 4: 5 comparisons, depth 5");
        prints("  insertAtPosition(3, \"Gus\"): the node before found at depth 2, 2 pointers changed");
        prints("  deleteByKey(\"Cat\"): 5 comparisons, depth 5");
        prints("  deleteAtEnd() removed Fay: the node before last found at depth 4");
        prints("  Eve -> Ann -> Ben -> Gus -> Dan -> (back to Eve)");
    }

    @Test
    void actFourUsesTheFormula() {
        prints("  J(1) = 0, J(n) = (J(n - 1) + k) mod n: winner at position 3 of 5 with k = 3, depth 5");
        prints("  41 players, k = 3: winner at position 30, depth 41");
    }

    @Test
    void actFiveOverflows() {
        prints("  count(): StackOverflowError, one frame per player in the lap");
    }
}
