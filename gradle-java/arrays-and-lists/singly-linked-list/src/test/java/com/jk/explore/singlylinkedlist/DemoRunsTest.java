package com.jk.explore.singlylinkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = SinglyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneShiftsTheArray() {
        prints("  a new clue at index 1: 5 clues shifted one place right");
    }

    @Test
    void actTwoBuildsTheList() {
        prints("  built with insertAtEnd: 10 steps in all, each walking to the last node");
        prints("  head -> the oak tree -> the old well -> the red gate -> the bridge -> the fountain -> the chest -> null");
        prints("  count() = 6: 6 steps, because only head is stored");
    }

    @Test
    void actThreeSearchesInsertsAndDeletes() {
        prints("  search(\"the fountain\") = position 4: 5 comparisons");
        prints("  get(3) = \"the bridge\": 3 steps from the head");
        prints("  insertAtPosition(1, \"the mill\"): 2 pointers changed, 0 clues shifted");
        prints("  insertAtBeginning(\"the start\"): 2 pointers changed");
        prints("  deleteByKey(\"the red gate\"): 5 comparisons, 1 pointer changed");
        prints("  deleteAtBeginning() removed \"the start\": 1 pointer changed");
        prints("  deleteAtEnd() removed \"the chest\": 4 steps to reach the second-to-last node");
        prints("  head -> the oak tree -> the mill -> the old well -> the bridge -> the fountain -> null");
    }

    @Test
    void actFourBreaksTheList() {
        prints("  \"the mill\" now points at itself");
        prints("  3 of 7 clues reachable; the other 4 are lost");
        prints("  an empty list, deleteAtBeginning(): underflow: the list is empty");
    }

    @Test
    void actFiveReversesAndPresentsTheBill() {
        prints("  reverse(): 7 pointer changes (6 next pointers turned around, then head), no data moved");
        prints("  get(999) of 1,000: 999 steps (an array: 1 step)");
        prints("  insertAtEnd on 1,000 with only a head: 999 steps");
    }
}
