package com.jk.explore.circularlinkedlistrecursive;

/**
 * Tells the story of the recursive circular linked list in five acts, printing the real counts and
 * the real depth of every recursion.
 *
 * <p>The worked example is the board game of the circular-linked-list project.
 */
public final class RecursiveCircularLinkedListDemo {

    static final String[] PLAYERS = {"Ann", "Ben", "Cat", "Dan"};

    private RecursiveCircularLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static RecursiveCircularLinkedList table() {
        RecursiveCircularLinkedList list = new RecursiveCircularLinkedList();
        for (String p : PLAYERS) {
            list.insertAtEnd(p);
        }
        list.steps().reset();
        return list;
    }

    /** Prints how count(node) unfolds round one lap, stopping at last instead of null. */
    static void traceCount(Node node, Node last, String indent, Lines out) {
        if (node == last) {
            out.add(indent + "count(" + node.data() + ") = 1   <- base case: this is last, the lap is over");
            return;
        }
        out.add(indent + "count(" + node.data() + ") = 1 + count(" + node.next().data() + ")");
        traceCount(node.next(), last, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. The base case is last, not null.");
        RecursiveCircularLinkedList table = table();
        traceCount(table.first(), table.last(), "  ", out);
        out.add("  count() = " + table.count() + ", depth " + table.steps().maxDepth()
                + "; waiting for null would recurse for ever and overflow");

        out.add("");
        out.add("TWO. One lap, recursively.");
        table.steps().reset();
        out.add("  display(): " + table.display() + ", depth " + table.steps().maxDepth());
        table.steps().reset();
        table.insertAtBeginning("Eve");
        table.insertAtEnd("Fay");
        out.add("  insertAtBeginning(\"Eve\") and insertAtEnd(\"Fay\"): depth " + table.steps().maxDepth()
                + ", " + table.steps().pointerChanges() + " pointer changes");

        out.add("");
        out.add("THREE. Find recursively, link in O(1).");
        table.steps().reset();
        out.add("  search(\"Dan\") = position " + table.search("Dan") + ": " + table.steps().compares()
                + " comparisons, depth " + table.steps().maxDepth());
        table.steps().reset();
        table.insertAtPosition(3, "Gus");
        out.add("  insertAtPosition(3, \"Gus\"): the node before found at depth " + table.steps().maxDepth()
                + ", " + table.steps().pointerChanges() + " pointers changed");
        table.steps().reset();
        table.deleteByKey("Cat");
        out.add("  deleteByKey(\"Cat\"): " + table.steps().compares() + " comparisons, depth " + table.steps().maxDepth());
        table.steps().reset();
        String gone = table.deleteAtEnd();
        out.add("  deleteAtEnd() removed " + gone + ": the node before last found at depth " + table.steps().maxDepth());
        out.add("  " + table);

        out.add("");
        out.add("FOUR. Counting out, by a recursive formula.");
        RecursiveCircularLinkedList game = table();
        game.insertAtEnd("Eve");
        game.steps().reset();
        int w = game.winnerPosition(5, 3);
        out.add("  J(1) = 0, J(n) = (J(n - 1) + k) mod n: winner at position " + w + " of 5 with k = 3, depth "
                + game.steps().maxDepth());
        out.add("  position " + w + " is " + PLAYERS[w] + ": the same winner the list simulation finds, without the list");
        game.steps().reset();
        game.winnerPosition(41, 3);
        out.add("  41 players, k = 3: winner at position " + game.winnerPosition(41, 3) + ", depth " + game.steps().maxDepth());

        out.add("");
        out.add("FIVE. The limit of recursion.");
        RecursiveCircularLinkedList big = new RecursiveCircularLinkedList();
        for (int i = 0; i < 1_000_000; i++) {
            big.insertAtEnd("player");
        }
        out.add("  1,000,000 players, joined with insertAtEnd: no recursion, O(1) each");
        try {
            out.add("  count() = " + big.count());
        } catch (StackOverflowError e) {
            out.add("  count(): StackOverflowError, one frame per player in the lap");
        }
        out.add("  the do-while loops of the circular-linked-list project need O(1) extra space");
        return out.text();
    }
}
