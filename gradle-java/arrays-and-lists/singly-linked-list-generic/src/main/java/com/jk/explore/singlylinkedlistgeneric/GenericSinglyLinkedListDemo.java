package com.jk.explore.singlylinkedlistgeneric;

/**
 * Tells the story of the generic singly linked list in five acts, printing the real step counts.
 *
 * <p>The worked example is the treasure hunt of the singly-linked-list project, held three ways by
 * one class: place names, distances in metres, and {@code Clue} records of our own.
 */
public final class GenericSinglyLinkedListDemo {

    static final String[] PLACES = {"the oak tree", "the old well", "the red gate", "the bridge", "the fountain", "the chest"};
    static final int[] METRES = {40, 25, 60, 35, 50, 20};

    private GenericSinglyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static GenericSinglyLinkedList<Clue> clues() {
        GenericSinglyLinkedList<Clue> list = new GenericSinglyLinkedList<>();
        for (int i = PLACES.length - 1; i >= 0; i--) {
            list.insertAtBeginning(new Clue(PLACES[i], METRES[i]));
        }
        list.steps().reset();
        return list;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. One class, any element type.");
        GenericSinglyLinkedList<String> places = new GenericSinglyLinkedList<>();
        GenericSinglyLinkedList<Integer> metres = new GenericSinglyLinkedList<>();
        for (int i = PLACES.length - 1; i >= 0; i--) {
            places.insertAtBeginning(PLACES[i]);
            metres.insertAtBeginning(METRES[i]);
        }
        GenericSinglyLinkedList<Clue> clues = clues();
        out.add("  <String>:  " + places.display());
        out.add("  <Integer>: " + metres.display());
        out.add("  <Clue>:    " + clues.display());
        out.add("  one class, three element types; metres.insertAtEnd(\"far\") does not compile");

        out.add("");
        out.add("TWO. Node<T>: data is a reference.");
        clues.steps().reset();
        out.add("  count() = " + clues.count() + ": " + clues.steps().steps() + " steps; each Node<Clue> holds a reference to a Clue and a next pointer");
        Clue first = clues.head().data();
        out.add("  head.data is the Clue object " + first + "; the node points at it, it does not contain a copy");

        out.add("");
        out.add("THREE. Search and deletion with equals.");
        Clue lookFor = new Clue("the fountain", 50);
        clues.steps().reset();
        int at = clues.search(lookFor);
        out.add("  search(new Clue(\"the fountain\", 50)) = position " + at + ": " + clues.steps().compares()
                + " comparisons; a record's equals compares place and metres");
        out.add("  the same object? " + (clues.get(at) == lookFor) + ": equals, not ==, is what matches");
        clues.steps().reset();
        clues.insertAtPosition(1, new Clue("the mill", 30));
        out.add("  insertAtPosition(1, the mill (30 m)): " + clues.steps().pointerChanges() + " pointers changed");
        clues.steps().reset();
        boolean gone = clues.deleteByKey(new Clue("the red gate", 60));
        out.add("  deleteByKey(new Clue(\"the red gate\", 60)) = " + gone + ": " + clues.steps().compares()
                + " comparisons, " + clues.steps().pointerChanges() + " pointer changed");
        out.add("  " + clues.display());

        out.add("");
        out.add("FOUR. Deleting at both ends.");
        clues.steps().reset();
        Clue firstGone = clues.deleteAtBeginning();
        out.add("  deleteAtBeginning() = " + firstGone + ": " + clues.steps().pointerChanges() + " pointer changed");
        clues.steps().reset();
        Clue lastGone = clues.deleteAtEnd();
        out.add("  deleteAtEnd() = " + lastGone + ": " + clues.steps().steps() + " steps to the second-to-last node");
        out.add("  no node refers to them now, so the garbage collector can free both nodes and both Clues");
        try {
            new GenericSinglyLinkedList<Clue>().deleteAtEnd();
            out.add("  deleting from an empty list works");
        } catch (IllegalStateException e) {
            out.add("  an empty list, deleteAtEnd(): " + e.getMessage());
        }

        out.add("");
        out.add("FIVE. The same algorithm for every type.");
        metres.steps().reset();
        metres.reverse();
        out.add("  <Integer> reverse(): " + metres.steps().pointerChanges() + " pointer changes; " + metres.display());
        places.steps().reset();
        places.reverse();
        out.add("  <String>  reverse(): " + places.steps().pointerChanges() + " pointer changes, the very same code");
        out.add("  every cost equals the String-only list: generics change the type, not the algorithm");
        out.add("  already in Java: java.util.LinkedList<E>, generic the same way (and linked both ways)");
        return out.text();
    }
}
