package com.jk.explore.doublylinkedlistgeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = GenericDoublyLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneHoldsThreeTypes() {
        prints("  <Integer>: tail -> 1200 <-> 930 <-> 610 <-> 540 <-> 820 <- head");
        prints("  <Photo>:   head -> beach 820KB <-> cake 540KB <-> dog 610KB <-> forest 930KB <-> mountain 1200KB <- tail");
    }

    @Test
    void actTwoShowsANode() {
        prints("  head.data = beach 820KB, head.prev = null, head.next.data = cake 540KB");
        prints("  tail.data = mountain 1200KB, tail.prev.data = forest 930KB, tail.next = null");
    }

    @Test
    void actThreeWorksAtTheEndsAndWithEquals() {
        prints("  insertAtBeginning and insertAtEnd: 0 steps, 6 pointer changes");
        prints("  search(new Photo(\"dog\", 610)) = position 3: 4 comparisons; equals compares name and size");
        prints("  insertAtPosition(3, garden 760KB): 4 pointer changes");
        prints("  deleteByKey(new Photo(\"cake\", 540)) = true: 3 comparisons, 2 pointer changes");
    }

    @Test
    void actFourDeletesAtTheEnds() {
        prints("  deleteAtBeginning() = airport 700KB, deleteAtEnd() = river 880KB: 0 steps, 4 pointer changes");
        prints("  an empty list, deleteAtBeginning(): underflow: the list is empty");
    }

    @Test
    void actFiveReusesTheAlgorithm() {
        prints("  <Integer> reverse(): 12 pointer changes; head -> 1200 <-> 930 <-> 610 <-> 540 <-> 820 <- tail");
        prints("  <String>  reverse(): 12 pointer changes, the very same code");
    }
}
