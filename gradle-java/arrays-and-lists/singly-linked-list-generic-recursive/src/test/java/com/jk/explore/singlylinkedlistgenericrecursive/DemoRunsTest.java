package com.jk.explore.singlylinkedlistgenericrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericRecursiveSinglyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneTracesARecursiveSearch() {
        prints("  search(the oak tree (40 m)): not equal -> search(rest)");
        prints("      search(the red gate (60 m)): equals -> position 2   <- base case: found");
        prints("  search(new Clue(\"the red gate\", 60)) = position 2: 3 equals calls, depth 3");
    }

    @Test
    void actTwoUsesAnyType() {
        prints("  <Integer> count() = 6, depth 6");
        prints("  <Integer> displayReverse(): 20 <- 50 <- 35 <- 60 <- 25 <- 40 <- (head)");
    }

    @Test
    void actThreeInserts() {
        prints("  insertAtEnd(the island (70 m)): depth 6; the empty list at the end is replaced by the new node");
        prints("  insertAtPosition(1, the mill (30 m)): depth 1, 2 pointers changed");
    }

    @Test
    void actFourDeletesAndReverses() {
        prints("  deleteByKey(new Clue(\"the red gate\", 60)) = true: 4 equals calls, depth 4; the matching call returns node.next");
        prints("  deleteAtEnd() = the island (70 m): depth 6");
        prints("  <String> reverse(): depth 5; head -> the chest -> the fountain -> the bridge -> the red gate -> the old well -> the oak tree -> null");
    }

    @Test
    void actFiveOverflows() {
        prints("  count() of 1,000,000 nodes: StackOverflowError, one frame per node");
    }
}
