package com.jk.explore.doublylinkedlist;

/**
 * Tells the story of the doubly linked list in five acts, printing the real step counts.
 *
 * <p>The worked example is a photo viewer with Previous and Next buttons.
 */
public final class DoublyLinkedListDemo {

    static final String[] ALBUM = {"beach", "cake", "dog", "forest", "mountain"};

    private DoublyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static DoublyLinkedList album() {
        DoublyLinkedList list = new DoublyLinkedList();
        for (String p : ALBUM) {
            list.insertAtEnd(p);
        }
        list.steps().reset();
        return list;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. A viewer with next pointers only.");
        DoublyLinkedList oneWay = album();
        Node at = oneWay.head();
        int steps = 0;
        while (!at.next.data.equals("mountain")) {    // only next pointers may be followed here
            at = at.next;
            steps++;
        }
        out.add("  looking at \"mountain\", press Previous: walk from \"beach\", " + steps + " steps to \"" + at.data + "\"");
        out.add("  deleting the photo on screen means finding the one before it the same way");

        out.add("");
        out.add("TWO. Pointers both ways, and both ends.");
        DoublyLinkedList album = new DoublyLinkedList();
        for (String p : ALBUM) {
            album.insertAtEnd(p);
        }
        out.add("  built with insertAtEnd through tail: " + album.steps().steps() + " steps, "
                + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        out.add("  displayForward():  " + album.displayForward());
        out.add("  displayBackward(): " + album.displayBackward());
        out.add("  each node holds data, a prev pointer and a next pointer; the list keeps head and tail");

        out.add("");
        out.add("THREE. Insertion and deletion at both ends and in the middle.");
        album.steps().reset();
        album.insertAtBeginning("airport");
        out.add("  insertAtBeginning(\"airport\"): " + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        album.insertAtEnd("river");
        out.add("  insertAtEnd(\"river\"): " + album.steps().steps() + " steps, " + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        album.insertAtPosition(3, "garden");
        out.add("  insertAtPosition(3, \"garden\"): " + album.steps().steps() + " steps, "
                + album.steps().pointerChanges() + " pointer changes");
        album.steps().reset();
        String end = album.deleteAtEnd();
        out.add("  deleteAtEnd() removed \"" + end + "\": " + album.steps().steps() + " steps, "
                + album.steps().pointerChanges() + " pointer changes, through tail");
        album.steps().reset();
        album.deleteByKey("cake");
        out.add("  deleteByKey(\"cake\"): " + album.steps().compares() + " comparisons, "
                + album.steps().pointerChanges() + " pointer changes, no prev pointer to keep");
        out.add("  " + album.displayForward());

        out.add("");
        out.add("FOUR. The pointer nobody set.");
        DoublyLinkedList broken = album();
        broken.insertAfterForgettingPrev(broken.head(), "garden");
        out.add("  insert \"garden\" after \"beach\", setting 3 of the 4 pointers");
        out.add("  forwards:  " + broken + ", " + broken.count() + " photos");
        out.add("  backwards: " + broken.countBackward() + " photos; \"cake\".prev still points to \"beach\"");
        DoublyLinkedList one = new DoublyLinkedList();
        one.insertAtEnd("solo");
        one.deleteAtEnd();
        out.add("  delete the only photo: head is " + one.head() + ", tail is " + one.tail());

        out.add("");
        out.add("FIVE. Reversal, and the bill.");
        DoublyLinkedList rev = album();
        rev.reverse();
        out.add("  reverse(): " + rev.steps().pointerChanges() + " pointer changes (prev and next swapped in 5 nodes, then head and tail)");
        out.add("  " + rev.displayForward());
        out.add("  every node carries two pointers: one more pointer per photo than a singly linked list");
        out.add("  an insertion in the middle sets 4 pointers, where a singly linked list sets 2");
        out.add("  already in Java: java.util.LinkedList is a doubly linked list with head and tail");
        return out.text();
    }
}
