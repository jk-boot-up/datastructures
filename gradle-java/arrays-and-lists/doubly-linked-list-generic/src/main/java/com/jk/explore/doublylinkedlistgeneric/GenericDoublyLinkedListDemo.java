package com.jk.explore.doublylinkedlistgeneric;

/**
 * Tells the story of the generic doubly linked list in five acts, printing the real step counts.
 *
 * <p>The worked example is the photo viewer of the doubly-linked-list project, now holding
 * {@code Photo} records, beside the same class holding names and sizes.
 */
public final class GenericDoublyLinkedListDemo {

    static final String[] NAMES = {"beach", "cake", "dog", "forest", "mountain"};
    static final int[] KB = {820, 540, 610, 930, 1200};

    private GenericDoublyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static GenericDoublyLinkedList<Photo> album() {
        GenericDoublyLinkedList<Photo> list = new GenericDoublyLinkedList<>();
        for (int i = 0; i < NAMES.length; i++) {
            list.insertAtEnd(new Photo(NAMES[i], KB[i]));
        }
        list.steps().reset();
        return list;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. One class, any element type.");
        GenericDoublyLinkedList<String> names = new GenericDoublyLinkedList<>();
        GenericDoublyLinkedList<Integer> sizes = new GenericDoublyLinkedList<>();
        for (int i = 0; i < NAMES.length; i++) {
            names.insertAtEnd(NAMES[i]);
            sizes.insertAtEnd(KB[i]);
        }
        GenericDoublyLinkedList<Photo> album = album();
        out.add("  <String>:  " + names.displayForward());
        out.add("  <Integer>: " + sizes.displayBackward());
        out.add("  <Photo>:   " + album.displayForward());

        out.add("");
        out.add("TWO. Node<T>: prev, a reference to the data, next.");
        Node<Photo> first = album.head();
        out.add("  head.data = " + first.data() + ", head.prev = " + first.prev() + ", head.next.data = " + first.next().data());
        Node<Photo> last = album.tail();
        out.add("  tail.data = " + last.data() + ", tail.prev.data = " + last.prev().data() + ", tail.next = " + last.next());

        out.add("");
        out.add("THREE. Both ends, and equals in the middle.");
        album.steps().reset();
        album.insertAtBeginning(new Photo("airport", 700));
        album.insertAtEnd(new Photo("river", 880));
        out.add("  insertAtBeginning and insertAtEnd: " + album.steps().steps() + " steps, "
                + album.steps().pointerChanges() + " pointer changes");
        Photo dog = new Photo("dog", 610);
        album.steps().reset();
        int at = album.search(dog);
        out.add("  search(new Photo(\"dog\", 610)) = position " + at + ": " + album.steps().compares()
                + " comparisons; equals compares name and size");
        album.steps().reset();
        album.insertAtPosition(3, new Photo("garden", 760));
        out.add("  insertAtPosition(3, garden 760KB): " + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        boolean gone = album.deleteByKey(new Photo("cake", 540));
        out.add("  deleteByKey(new Photo(\"cake\", 540)) = " + gone + ": " + album.steps().compares()
                + " comparisons, " + album.steps().pointerChanges() + " pointer changes");
        out.add("  " + album.displayForward());

        out.add("");
        out.add("FOUR. Deleting at the ends through head and tail.");
        album.steps().reset();
        Photo a = album.deleteAtBeginning();
        Photo r = album.deleteAtEnd();
        out.add("  deleteAtBeginning() = " + a + ", deleteAtEnd() = " + r + ": " + album.steps().steps()
                + " steps, " + album.steps().pointerChanges() + " pointer changes");
        try {
            new GenericDoublyLinkedList<Photo>().deleteAtBeginning();
            out.add("  deleting from an empty list works");
        } catch (IllegalStateException e) {
            out.add("  an empty list, deleteAtBeginning(): " + e.getMessage());
        }

        out.add("");
        out.add("FIVE. The same algorithm for every type.");
        sizes.steps().reset();
        sizes.reverse();
        out.add("  <Integer> reverse(): " + sizes.steps().pointerChanges() + " pointer changes; " + sizes.displayForward());
        names.steps().reset();
        names.reverse();
        out.add("  <String>  reverse(): " + names.steps().pointerChanges() + " pointer changes, the very same code");
        out.add("  every cost equals the String-only list: generics change the type, not the algorithm");
        out.add("  already in Java: java.util.LinkedList<E>, a generic doubly linked list with head and tail");
        return out.text();
    }
}
