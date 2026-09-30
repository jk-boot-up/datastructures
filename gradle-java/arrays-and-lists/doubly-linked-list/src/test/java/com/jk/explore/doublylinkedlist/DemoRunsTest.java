package com.jk.explore.doublylinkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = DoublyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneWalksWithoutPrev() {
        prints("  looking at \"mountain\", press Previous: walk from \"beach\", 3 steps to \"forest\"");
    }

    @Test
    void actTwoDisplaysBothWays() {
        prints("  built with insertAtEnd through tail: 0 steps, 14 pointer changes");
        prints("  displayForward():  head -> beach <-> cake <-> dog <-> forest <-> mountain <- tail");
        prints("  displayBackward(): tail -> mountain <-> forest <-> dog <-> cake <-> beach <- head");
    }

    @Test
    void actThreeInsertsAndDeletes() {
        prints("  insertAtBeginning(\"airport\"): 3 pointer changes");
        prints("  insertAtEnd(\"river\"): 0 steps, 3 pointer changes");
        prints("  insertAtPosition(3, \"garden\"): 2 steps, 4 pointer changes");
        prints("  deleteAtEnd() removed \"river\": 0 steps, 2 pointer changes, through tail");
        prints("  deleteByKey(\"cake\"): 3 comparisons, 2 pointer changes, no prev pointer to keep");
        prints("  head -> airport <-> beach <-> garden <-> dog <-> forest <-> mountain <- tail");
    }

    @Test
    void actFourForgetsAPointer() {
        prints("  forwards:  [beach <-> garden <-> cake <-> dog <-> forest <-> mountain], 6 photos");
        prints("  backwards: 5 photos; \"cake\".prev still points to \"beach\"");
        prints("  delete the only photo: head is null, tail is null");
    }

    @Test
    void actFiveReverses() {
        prints("  reverse(): 12 pointer changes (prev and next swapped in 5 nodes, then head and tail)");
        prints("  head -> mountain <-> forest <-> dog <-> cake <-> beach <- tail");
    }
}
