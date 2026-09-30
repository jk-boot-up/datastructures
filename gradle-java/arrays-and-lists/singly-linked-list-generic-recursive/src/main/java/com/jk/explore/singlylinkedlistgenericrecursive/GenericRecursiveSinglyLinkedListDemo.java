package com.jk.explore.singlylinkedlistgenericrecursive;

/**
 * Tells the story of the generic, recursive singly linked list in five acts, printing the real
 * counts and the real depth of every recursion.
 *
 * <p>The worked example is the treasure hunt, held as {@code Clue} records, distances and place names.
 */
public final class GenericRecursiveSinglyLinkedListDemo {

    static final String[] PLACES = {"the oak tree", "the old well", "the red gate", "the bridge", "the fountain", "the chest"};
    static final int[] METRES = {40, 25, 60, 35, 50, 20};

    private GenericRecursiveSinglyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static GenericRecursiveSinglyLinkedList<Clue> clues() {
        GenericRecursiveSinglyLinkedList<Clue> list = new GenericRecursiveSinglyLinkedList<>();
        for (int i = PLACES.length - 1; i >= 0; i--) {
            list.insertAtBeginning(new Clue(PLACES[i], METRES[i]));
        }
        list.steps().reset();
        return list;
    }

    /** Prints how search(node, key) unfolds: equals at each node, until a match or the empty list. */
    static <T> void traceSearch(Node<T> node, T key, int pos, String indent, Lines out) {
        if (node == null) {
            out.add(indent + "search(null): -1   <- base case: the empty list");
            return;
        }
        if (node.data().equals(key)) {
            out.add(indent + "search(" + node.data() + "): equals -> position " + pos + "   <- base case: found");
            return;
        }
        out.add(indent + "search(" + node.data() + "): not equal -> search(rest)");
        traceSearch(node.next(), key, pos + 1, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Recursion over Node<T>, with equals.");
        GenericRecursiveSinglyLinkedList<Clue> clues = clues();
        Clue key = new Clue("the red gate", 60);
        traceSearch(clues.head(), key, 0, "  ", out);
        out.add("  search(new Clue(\"the red gate\", 60)) = position " + clues.search(key) + ": "
                + clues.steps().compares() + " equals calls, depth " + clues.steps().maxDepth());

        out.add("");
        out.add("TWO. The same recursion, any type.");
        GenericRecursiveSinglyLinkedList<Integer> metres = new GenericRecursiveSinglyLinkedList<>();
        GenericRecursiveSinglyLinkedList<String> places = new GenericRecursiveSinglyLinkedList<>();
        for (int i = PLACES.length - 1; i >= 0; i--) {
            metres.insertAtBeginning(METRES[i]);
            places.insertAtBeginning(PLACES[i]);
        }
        out.add("  <Integer> count() = " + metres.count() + ", depth " + metres.steps().maxDepth());
        out.add("  <Integer> displayReverse(): " + metres.displayReverse());
        out.add("  <String>  count() = " + places.count() + ", depth " + places.steps().maxDepth());

        out.add("");
        out.add("THREE. Insertion, recursively.");
        clues.steps().reset();
        clues.insertAtEnd(new Clue("the island", 70));
        out.add("  insertAtEnd(the island (70 m)): depth " + clues.steps().maxDepth()
                + "; the empty list at the end is replaced by the new node");
        clues.steps().reset();
        clues.insertAtPosition(1, new Clue("the mill", 30));
        out.add("  insertAtPosition(1, the mill (30 m)): depth " + clues.steps().maxDepth() + ", "
                + clues.steps().pointerChanges() + " pointers changed");
        out.add("  " + clues.display());

        out.add("");
        out.add("FOUR. Deletion and reversal, recursively.");
        clues.steps().reset();
        boolean gone = clues.deleteByKey(new Clue("the red gate", 60));
        out.add("  deleteByKey(new Clue(\"the red gate\", 60)) = " + gone + ": " + clues.steps().compares()
                + " equals calls, depth " + clues.steps().maxDepth() + "; the matching call returns node.next");
        clues.steps().reset();
        Clue last = clues.deleteAtEnd();
        out.add("  deleteAtEnd() = " + last + ": depth " + clues.steps().maxDepth());
        places.steps().reset();
        places.reverse();
        out.add("  <String> reverse(): depth " + places.steps().maxDepth() + "; " + places.display());

        out.add("");
        out.add("FIVE. The limit of recursion.");
        GenericRecursiveSinglyLinkedList<Integer> big = new GenericRecursiveSinglyLinkedList<>();
        Integer one = 1;
        for (int i = 0; i < 1_000_000; i++) {
            big.insertAtBeginning(one);
        }
        try {
            out.add("  count() of 1,000,000 = " + big.count());
        } catch (StackOverflowError e) {
            out.add("  count() of 1,000,000 nodes: StackOverflowError, one frame per node");
        }
        out.add("  generics change what each node refers to; recursion changes how much stack each walk needs");
        return out.text();
    }
}
