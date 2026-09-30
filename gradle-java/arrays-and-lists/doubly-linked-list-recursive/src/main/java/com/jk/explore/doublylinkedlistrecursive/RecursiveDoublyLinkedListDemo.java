package com.jk.explore.doublylinkedlistrecursive;

/**
 * Tells the story of the recursive doubly linked list in five acts, printing the real counts and
 * the real depth of every recursion.
 *
 * <p>The worked example is the photo viewer of the doubly-linked-list project.
 */
public final class RecursiveDoublyLinkedListDemo {

    static final String[] ALBUM = {"beach", "cake", "dog", "forest", "mountain"};

    private RecursiveDoublyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static RecursiveDoublyLinkedList album() {
        RecursiveDoublyLinkedList list = new RecursiveDoublyLinkedList();
        for (String p : ALBUM) {
            list.insertAtEnd(p);
        }
        list.steps().reset();
        return list;
    }

    /** Prints how displayBackward(node) unfolds from the tail, one call per node. */
    static void traceBackward(Node node, String indent, Lines out) {
        if (node == null) {
            out.add(indent + "displayBackward(null): past the head   <- base case");
            return;
        }
        out.add(indent + "displayBackward(" + node.data() + "): print it, then displayBackward(prev)");
        traceBackward(node.prev(), indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. The same recursion in both directions.");
        RecursiveDoublyLinkedList album = album();
        traceBackward(album.tail(), "  ", out);
        out.add("  " + album.displayBackward() + ", depth " + album.steps().maxDepth());
        album.steps().reset();
        out.add("  " + album.displayForward() + ", depth " + album.steps().maxDepth());

        out.add("");
        out.add("TWO. Both ends need no recursion.");
        album.steps().reset();
        album.insertAtBeginning("airport");
        album.insertAtEnd("river");
        out.add("  insertAtBeginning(\"airport\") and insertAtEnd(\"river\"): depth " + album.steps().maxDepth()
                + ", " + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        String end = album.deleteAtEnd();
        out.add("  deleteAtEnd() removed \"" + end + "\": depth " + album.steps().maxDepth() + ", through tail");
        album.steps().reset();
        out.add("  count() = " + album.count() + ", depth " + album.steps().maxDepth());

        out.add("");
        out.add("THREE. Find recursively, link in O(1).");
        album.steps().reset();
        out.add("  search(\"forest\") = position " + album.search("forest") + ": " + album.steps().compares()
                + " comparisons, depth " + album.steps().maxDepth());
        album.steps().reset();
        album.insertAtPosition(3, "garden");
        out.add("  insertAtPosition(3, \"garden\"): the node before found at depth " + album.steps().maxDepth()
                + ", then " + album.steps().pointerChanges() + " pointers set");
        album.steps().reset();
        album.deleteByKey("cake");
        out.add("  deleteByKey(\"cake\"): found at depth " + album.steps().maxDepth() + ", then "
                + album.steps().pointerChanges() + " pointers changed");
        out.add("  " + album.displayForward());

        out.add("");
        out.add("FOUR. Reversal, recursively.");
        RecursiveDoublyLinkedList rev = album();
        rev.reverse();
        out.add("  reverse(): swap this node's prev and next, then reverse from the old next: depth "
                + rev.steps().maxDepth() + ", " + rev.steps().pointerChanges() + " pointer changes");
        rev.steps().reset();
        out.add("  " + rev.displayForward());

        out.add("");
        out.add("FIVE. The limit of recursion.");
        RecursiveDoublyLinkedList big = new RecursiveDoublyLinkedList();
        for (int i = 0; i < 1_000_000; i++) {
            big.insertAtEnd("photo");
        }
        out.add("  1,000,000 photos, built with insertAtEnd: no recursion, O(1) each");
        try {
            out.add("  count() = " + big.count());
        } catch (StackOverflowError e) {
            out.add("  count(): StackOverflowError, one frame per node");
        }
        out.add("  the loops of the doubly-linked-list project need O(1) extra space");
        return out.text();
    }
}
