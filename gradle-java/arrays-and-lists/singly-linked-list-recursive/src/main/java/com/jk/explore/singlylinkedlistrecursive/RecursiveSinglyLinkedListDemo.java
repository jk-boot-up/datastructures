package com.jk.explore.singlylinkedlistrecursive;

/**
 * Tells the story of the recursive singly linked list in five acts, printing the real counts and
 * the real depth of every recursion.
 *
 * <p>The worked example is the treasure hunt of the singly-linked-list project.
 */
public final class RecursiveSinglyLinkedListDemo {

    static final String[] HUNT = {"the oak tree", "the old well", "the red gate", "the bridge", "the fountain", "the chest"};

    private RecursiveSinglyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static RecursiveSinglyLinkedList hunt() {
        RecursiveSinglyLinkedList list = new RecursiveSinglyLinkedList();
        for (int i = HUNT.length - 1; i >= 0; i--) {
            list.insertAtBeginning(HUNT[i]);
        }
        list.steps().reset();
        return list;
    }

    /** Prints how count(node) unfolds: one call per node, each waiting for the count of the rest. */
    static void traceCount(Node node, String indent, Lines out) {
        if (node == null) {
            out.add(indent + "count(null) = 0   <- base case: the empty list");
            return;
        }
        out.add(indent + "count(" + node.data() + ") = 1 + count(rest)");
        traceCount(node.next(), indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. A list is a node followed by a smaller list.");
        RecursiveSinglyLinkedList list = hunt();
        traceCount(list.head(), "  ", out);
        out.add("  count() = " + list.count() + ", with " + list.steps().maxDepth() + " calls waiting at once");

        out.add("");
        out.add("TWO. Forwards and backwards.");
        list.steps().reset();
        out.add("  display():        " + list.display() + "   depth " + list.steps().maxDepth());
        list.steps().reset();
        out.add("  displayReverse(): " + list.displayReverse() + "   depth " + list.steps().maxDepth());
        out.add("  printing after the recursive call instead of before it reverses the order");

        out.add("");
        out.add("THREE. Search and insertion, recursively.");
        list.steps().reset();
        out.add("  search(\"the fountain\") = position " + list.search("the fountain") + ": "
                + list.steps().compares() + " comparisons, depth " + list.steps().maxDepth());
        list.steps().reset();
        out.add("  get(3) = \"" + list.get(3) + "\": depth " + list.steps().maxDepth());
        list.steps().reset();
        list.insertAtEnd("the island");
        out.add("  insertAtEnd(\"the island\"): depth " + list.steps().maxDepth()
                + "; the base case, the empty list at the end, becomes the new node");
        list.steps().reset();
        list.insertAtPosition(1, "the mill");
        out.add("  insertAtPosition(1, \"the mill\"): depth " + list.steps().maxDepth() + ", "
                + list.steps().pointerChanges() + " pointers changed");
        out.add("  " + list.display());

        out.add("");
        out.add("FOUR. Deletion and reversal, recursively.");
        list.steps().reset();
        list.deleteByKey("the red gate");
        out.add("  deleteByKey(\"the red gate\"): " + list.steps().compares() + " comparisons, depth "
                + list.steps().maxDepth() + "; that call returns node.next instead of itself");
        list.steps().reset();
        String last = list.deleteAtEnd();
        out.add("  deleteAtEnd() removed \"" + last + "\": depth " + list.steps().maxDepth());
        list.steps().reset();
        list.reverse();
        out.add("  reverse(): depth " + list.steps().maxDepth() + "; reverse the rest, then hang this node on its end");
        out.add("  " + list.display());

        out.add("");
        out.add("FIVE. The limit of recursion.");
        RecursiveSinglyLinkedList big = new RecursiveSinglyLinkedList();
        for (int i = 0; i < 1_000_000; i++) {
            big.insertAtBeginning("clue");
        }
        out.add("  1,000,000 nodes, built with insertAtBeginning: no recursion, O(1) each");
        try {
            out.add("  count() = " + big.count());
        } catch (StackOverflowError e) {
            out.add("  count(): StackOverflowError, one frame per node");
        }
        out.add("  the loop in the singly-linked-list project counts them in O(1) extra space");
        return out.text();
    }
}
