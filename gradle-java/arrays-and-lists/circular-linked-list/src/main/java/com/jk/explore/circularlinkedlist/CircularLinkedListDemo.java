package com.jk.explore.circularlinkedlist;

/**
 * Tells the story of the circular linked list in five acts, printing the real step counts.
 *
 * <p>The worked example is players taking turns round a board game.
 */
public final class CircularLinkedListDemo {

    static final String[] PLAYERS = {"Ann", "Ben", "Cat", "Dan"};

    private CircularLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static CircularLinkedList table() {
        CircularLinkedList list = new CircularLinkedList();
        for (String p : PLAYERS) {
            list.insertAtEnd(p);
        }
        list.steps().reset();
        return list;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Players in a straight line.");
        out.add("  Ann -> Ben -> Cat -> Dan -> null");
        StringBuilder six = new StringBuilder();
        int specialCases = 0;
        int at = 0;
        for (int turn = 0; turn < 6; turn++) {
            six.append(turn > 0 ? ", " : "").append(PLAYERS[at]);
            at++;
            if (at == PLAYERS.length) {         // fell off the end: back to the first player
                at = 0;
                specialCases++;
            }
        }
        out.add("  6 turns: " + six + "; after Dan the line ends, and " + specialCases
                + " special case sends it back to Ann");

        out.add("");
        out.add("TWO. Join the ends: last.next is the first node.");
        CircularLinkedList table = new CircularLinkedList();
        for (String p : PLAYERS) {
            table.insertAtEnd(p);
        }
        out.add("  built with insertAtEnd: " + table.steps().steps() + " steps, " + table.steps().pointerChanges()
                + " pointer changes; the list keeps only last");
        table.steps().reset();
        out.add("  display(): " + table.display());
        out.add("  last = " + table.last().data() + ", last.next = " + table.first().data()
                + ": both ends are one step from one pointer");

        out.add("");
        out.add("THREE. Turns, insertion and deletion.");
        table.steps().reset();
        out.add("  6 turns: " + table.turns(6) + "; " + table.steps().steps() + " steps, no special case");
        table.steps().reset();
        table.insertAtBeginning("Eve");
        out.add("  insertAtBeginning(\"Eve\"): " + table.steps().steps() + " steps, " + table.steps().pointerChanges() + " pointer changes");
        table.steps().reset();
        table.insertAtEnd("Fay");
        out.add("  insertAtEnd(\"Fay\"): " + table.steps().steps() + " steps, " + table.steps().pointerChanges()
                + " pointer changes: insert at the beginning, then move last on");
        table.steps().reset();
        table.insertAtPosition(3, "Gus");
        out.add("  insertAtPosition(3, \"Gus\"): " + table.steps().steps() + " steps, " + table.steps().pointerChanges() + " pointer changes");
        out.add("  " + table);
        table.steps().reset();
        String first = table.deleteAtBeginning();
        out.add("  deleteAtBeginning() removed " + first + ": " + table.steps().pointerChanges() + " pointer change");
        table.steps().reset();
        String lastGone = table.deleteAtEnd();
        out.add("  deleteAtEnd() removed " + lastGone + ": " + table.steps().steps()
                + " steps round to the node before last");
        table.steps().reset();
        table.deleteByKey("Cat");
        out.add("  deleteByKey(\"Cat\"): " + table.steps().compares() + " comparisons, " + table.steps().pointerChanges() + " pointer change");
        out.add("  " + table);

        out.add("");
        out.add("FOUR. Waiting for null.");
        out.add("  a loop \"while (current != null)\" walked " + table().walkUntilNull(1000)
                + " steps and never found null; it was stopped");
        out.add("  a circular traversal is a do-while that stops when it is back at the first node");
        CircularLinkedList one = new CircularLinkedList();
        one.insertAtEnd("Zoe");
        out.add("  a list of one node: " + one + "; its next points to itself");
        one.deleteAtEnd();
        out.add("  and when that player leaves: " + one + ", last is null");

        out.add("");
        out.add("FIVE. Counting out, and the bill.");
        CircularLinkedList game = table();
        game.insertAtEnd("Eve");
        game.steps().reset();
        out.add("  counting out every third player: " + game.countOut(3));
        out.add("  " + game.steps().steps() + " steps and " + game.steps().pointerChanges() + " pointer changes for the whole game");
        out.add("  the bill: no end to stop at, so every traversal needs a starting node to come back to");
        out.add("  already in Java: no circular linked list in java.util; ArrayDeque goes round a circular array instead");
        return out.text();
    }
}
