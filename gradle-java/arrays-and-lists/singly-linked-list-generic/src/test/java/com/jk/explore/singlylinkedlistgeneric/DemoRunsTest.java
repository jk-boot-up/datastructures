package com.jk.explore.singlylinkedlistgeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericSinglyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneHoldsThreeTypes() {
        prints("  <Integer>: head -> 40 -> 25 -> 60 -> 35 -> 50 -> 20 -> null");
        prints("  one class, three element types; metres.insertAtEnd(\"far\") does not compile");
    }

    @Test
    void actTwoHoldsReferences() {
        prints("  count() = 6: 6 steps; each Node<Clue> holds a reference to a Clue and a next pointer");
    }

    @Test
    void actThreeMatchesWithEquals() {
        prints("  search(new Clue(\"the fountain\", 50)) = position 4: 5 comparisons; a record's equals compares place and metres");
        prints("  the same object? false: equals, not ==, is what matches");
        prints("  insertAtPosition(1, the mill (30 m)): 2 pointers changed");
        prints("  deleteByKey(new Clue(\"the red gate\", 60)) = true: 4 comparisons, 1 pointer changed");
    }

    @Test
    void actFourDeletesAtBothEnds() {
        prints("  deleteAtBeginning() = the oak tree (40 m): 1 pointer changed");
        prints("  deleteAtEnd() = the chest (20 m): 3 steps to the second-to-last node");
        prints("  an empty list, deleteAtEnd(): underflow: the list is empty");
    }

    @Test
    void actFiveReusesTheAlgorithm() {
        prints("  <Integer> reverse(): 7 pointer changes; head -> 20 -> 50 -> 35 -> 60 -> 25 -> 40 -> null");
        prints("  <String>  reverse(): 7 pointer changes, the very same code");
    }
}
