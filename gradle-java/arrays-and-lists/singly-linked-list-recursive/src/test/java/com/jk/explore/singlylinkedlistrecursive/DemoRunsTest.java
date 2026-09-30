package com.jk.explore.singlylinkedlistrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = RecursiveSinglyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneTracesCount() {
        prints("  count(the oak tree) = 1 + count(rest)");
        prints("              count(null) = 0   <- base case: the empty list");
        prints("  count() = 6, with 6 calls waiting at once");
    }

    @Test
    void actTwoDisplaysBothWays() {
        prints("  displayReverse(): the chest <- the fountain <- the bridge <- the red gate <- the old well <- the oak tree <- (head)   depth 6");
    }

    @Test
    void actThreeSearchesAndInserts() {
        prints("  search(\"the fountain\") = position 4: 5 comparisons, depth 5");
        prints("  get(3) = \"the bridge\": depth 3");
        prints("  insertAtEnd(\"the island\"): depth 6; the base case, the empty list at the end, becomes the new node");
        prints("  insertAtPosition(1, \"the mill\"): depth 1, 2 pointers changed");
    }

    @Test
    void actFourDeletesAndReverses() {
        prints("  deleteByKey(\"the red gate\"): 4 comparisons, depth 4; that call returns node.next instead of itself");
        prints("  deleteAtEnd() removed \"the island\": depth 6");
        prints("  reverse(): depth 5; reverse the rest, then hang this node on its end");
        prints("  head -> the chest -> the fountain -> the bridge -> the old well -> the mill -> the oak tree -> null");
    }

    @Test
    void actFiveOverflows() {
        prints("  count(): StackOverflowError, one frame per node");
    }
}
