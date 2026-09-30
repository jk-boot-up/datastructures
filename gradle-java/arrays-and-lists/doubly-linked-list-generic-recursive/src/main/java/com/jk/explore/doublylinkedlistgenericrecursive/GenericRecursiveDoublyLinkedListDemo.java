package com.jk.explore.doublylinkedlistgenericrecursive;

/**
 * Tells the story of the generic, recursive doubly linked list in five acts, printing the real
 * counts and the real depth of every recursion.
 *
 * <p>The worked example is the photo viewer, holding {@code Photo} records, names and sizes.
 */
public final class GenericRecursiveDoublyLinkedListDemo {

    static final String[] NAMES = {"beach", "cake", "dog", "forest", "mountain"};
    static final int[] KB = {820, 540, 610, 930, 1200};

    private GenericRecursiveDoublyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static GenericRecursiveDoublyLinkedList<Photo> album() {
        GenericRecursiveDoublyLinkedList<Photo> list = new GenericRecursiveDoublyLinkedList<>();
        for (int i = 0; i < NAMES.length; i++) {
            list.insertAtEnd(new Photo(NAMES[i], KB[i]));
        }
        list.steps().reset();
        return list;
    }

    /** Prints how a recursive search along prev unfolds from the tail, comparing with equals. */
    static <T> void traceBackwardSearch(Node<T> node, T key, String indent, Lines out) {
        if (node == null) {
            out.add(indent + "past the head: not found   <- base case");
            return;
        }
        if (node.data().equals(key)) {
            out.add(indent + node.data() + ": equals   <- base case: found");
            return;
        }
        out.add(indent + node.data() + ": not equal, recurse on prev");
        traceBackwardSearch(node.prev(), key, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Recursion along prev, with equals.");
        GenericRecursiveDoublyLinkedList<Photo> album = album();
        traceBackwardSearch(album.tail(), new Photo("dog", 610), "  ", out);
        out.add("  " + album.displayBackward() + ", depth " + album.steps().maxDepth());

        out.add("");
        out.add("TWO. The same recursion, any type.");
        GenericRecursiveDoublyLinkedList<Integer> sizes = new GenericRecursiveDoublyLinkedList<>();
        GenericRecursiveDoublyLinkedList<String> names = new GenericRecursiveDoublyLinkedList<>();
        for (int i = 0; i < NAMES.length; i++) {
            sizes.insertAtEnd(KB[i]);
            names.insertAtEnd(NAMES[i]);
        }
        out.add("  <Integer> count() = " + sizes.count() + ", depth " + sizes.steps().maxDepth());
        out.add("  <String>  " + names.displayForward() + ", depth " + names.steps().maxDepth());

        out.add("");
        out.add("THREE. Both ends without recursion; the middle found recursively.");
        album.steps().reset();
        album.insertAtBeginning(new Photo("airport", 700));
        album.insertAtEnd(new Photo("river", 880));
        out.add("  insertAtBeginning and insertAtEnd: depth " + album.steps().maxDepth() + ", "
                + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        out.add("  search(new Photo(\"dog\", 610)) = position " + album.search(new Photo("dog", 610)) + ": "
                + album.steps().compares() + " equals calls, depth " + album.steps().maxDepth());
        album.steps().reset();
        album.insertAtPosition(3, new Photo("garden", 760));
        out.add("  insertAtPosition(3, garden 760KB): found at depth " + album.steps().maxDepth() + ", "
                + album.steps().pointerChanges() + " pointers set");
        album.steps().reset();
        album.deleteByKey(new Photo("cake", 540));
        out.add("  deleteByKey(new Photo(\"cake\", 540)): found at depth " + album.steps().maxDepth() + ", "
                + album.steps().pointerChanges() + " pointers changed");

        out.add("");
        out.add("FOUR. Reversal, recursively.");
        album.steps().reset();
        album.reverse();
        out.add("  reverse(): depth " + album.steps().maxDepth() + ", " + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        out.add("  " + album.displayForward());

        out.add("");
        out.add("FIVE. The limit of recursion.");
        GenericRecursiveDoublyLinkedList<Integer> big = new GenericRecursiveDoublyLinkedList<>();
        Integer one = 1;
        for (int i = 0; i < 1_000_000; i++) {
            big.insertAtEnd(one);
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
