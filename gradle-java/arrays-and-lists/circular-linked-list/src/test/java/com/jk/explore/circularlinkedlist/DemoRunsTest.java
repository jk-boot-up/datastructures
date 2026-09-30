package com.jk.explore.circularlinkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = CircularLinkedListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneNeedsASpecialCase() {
        prints("  6 turns: Ann, Ben, Cat, Dan, Ann, Ben; after Dan the line ends, and 1 special case sends it back to Ann");
    }

    @Test
    void actTwoJoinsTheEnds() {
        prints("  built with insertAtEnd: 0 steps, 12 pointer changes; the list keeps only last");
        prints("  display(): Ann -> Ben -> Cat -> Dan -> (back to Ann)");
        prints("  last = Dan, last.next = Ann: both ends are one step from one pointer");
    }

    @Test
    void actThreeInsertsAndDeletes() {
        prints("  6 turns: Ann, Ben, Cat, Dan, Ann, Ben; 6 steps, no special case");
        prints("  insertAtBeginning(\"Eve\"): 0 steps, 2 pointer changes");
        prints("  insertAtEnd(\"Fay\"): 0 steps, 3 pointer changes: insert at the beginning, then move last on");
        prints("  insertAtPosition(3, \"Gus\"): 2 steps, 2 pointer changes");
        prints("  deleteAtBeginning() removed Eve: 1 pointer change");
        prints("  deleteAtEnd() removed Fay: 4 steps round to the node before last");
        prints("  deleteByKey(\"Cat\"): 4 comparisons, 1 pointer change");
        prints("  Ann -> Ben -> Gus -> Dan -> (back to Ann)");
    }

    @Test
    void actFourNeverFindsNull() {
        prints("  a loop \"while (current != null)\" walked 1000 steps and never found null; it was stopped");
        prints("  a list of one node: Zoe -> (back to Zoe); its next points to itself");
        prints("  and when that player leaves: (empty), last is null");
    }

    @Test
    void actFiveCountsOut() {
        prints("  counting out every third player: out: Cat, Ann, Eve, Ben; winner Dan");
        prints("  8 steps and 4 pointer changes for the whole game");
    }
}
