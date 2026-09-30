package com.jk.explore.singlylinkedlist;

/**
 * Tells the story of the singly linked list in five acts, printing the real step counts.
 *
 * <p>The worked example is a treasure hunt: each clue says where the next one is hidden.
 */
public final class SinglyLinkedListDemo {

    static final String[] HUNT = {"the oak tree", "the old well", "the red gate", "the bridge", "the fountain", "the chest"};

    private SinglyLinkedListDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    static SinglyLinkedList hunt() {
        SinglyLinkedList list = new SinglyLinkedList();
        for (String clue : HUNT) {
            list.insertAtEnd(clue);
        }
        list.steps().reset();
        return list;
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. The hunt in an array.");
        String[] arr = new String[HUNT.length + 1];
        for (int i = 0; i < HUNT.length; i++) {
            arr[i] = HUNT[i];
        }
        StepCounter a = new StepCounter();
        for (int i = HUNT.length - 1; i >= 1; i--) {      // make room at index 1
            arr[i + 1] = arr[i];
            a.shift();
        }
        arr[1] = "the mill";
        out.add("  " + HUNT.length + " clues at indexes 0 to " + (HUNT.length - 1));
        out.add("  a new clue at index 1: " + a.shifts() + " clues shifted one place right");
        out.add("  every insertion near the front shifts nearly everything");

        out.add("");
        out.add("TWO. Nodes and next pointers.");
        SinglyLinkedList list = new SinglyLinkedList();
        for (String clue : HUNT) {
            list.insertAtEnd(clue);
        }
        out.add("  built with insertAtEnd: " + list.steps().steps() + " steps in all, each walking to the last node");
        list.steps().reset();
        out.add("  " + list.display());
        list.steps().reset();
        out.add("  count() = " + list.count() + ": " + list.steps().steps() + " steps, because only head is stored");

        out.add("");
        out.add("THREE. Search, insertion and deletion.");
        list.steps().reset();
        out.add("  search(\"the fountain\") = position " + list.search("the fountain") + ": "
                + list.steps().compares() + " comparisons");
        list.steps().reset();
        out.add("  get(3) = \"" + list.get(3) + "\": " + list.steps().steps() + " steps from the head");
        list.steps().reset();
        list.insertAtPosition(1, "the mill");
        out.add("  insertAtPosition(1, \"the mill\"): " + list.steps().pointerChanges() + " pointers changed, 0 clues shifted");
        list.steps().reset();
        list.insertAtBeginning("the start");
        out.add("  insertAtBeginning(\"the start\"): " + list.steps().pointerChanges() + " pointers changed");
        list.steps().reset();
        list.deleteByKey("the red gate");
        out.add("  deleteByKey(\"the red gate\"): " + list.steps().compares() + " comparisons, "
                + list.steps().pointerChanges() + " pointer changed");
        list.steps().reset();
        String first = list.deleteAtBeginning();
        out.add("  deleteAtBeginning() removed \"" + first + "\": " + list.steps().pointerChanges() + " pointer changed");
        list.steps().reset();
        String last = list.deleteAtEnd();
        out.add("  deleteAtEnd() removed \"" + last + "\": " + list.steps().steps()
                + " steps to reach the second-to-last node");
        list.steps().reset();
        out.add("  " + list.display());

        out.add("");
        out.add("FOUR. Two pointers in the wrong order.");
        SinglyLinkedList broken = hunt();
        broken.insertAtPositionWrongOrder(2, "the mill");
        SinglyLinkedList.Reach r = broken.reach(100);
        out.add("  prev.next = newNode first, then newNode.next = prev.next");
        out.add("  \"" + r.loopData() + "\" now points at itself");
        out.add("  " + r.reachable() + " of 7 clues reachable; the other " + (7 - r.reachable()) + " are lost");
        try {
            new SinglyLinkedList().deleteAtBeginning();
            out.add("  deleting from an empty list works");
        } catch (IllegalStateException e) {
            out.add("  an empty list, deleteAtBeginning(): " + e.getMessage());
        }

        out.add("");
        out.add("FIVE. Reversal, and the bill.");
        SinglyLinkedList rev = hunt();
        rev.reverse();
        out.add("  reverse(): " + rev.steps().pointerChanges() + " pointer changes (6 next pointers turned around, then head), no data moved");
        out.add("  " + rev.display());
        SinglyLinkedList thousand = new SinglyLinkedList();
        for (int i = 0; i < 1000; i++) {
            thousand.insertAtBeginning("clue " + i);
        }
        thousand.steps().reset();
        thousand.get(999);
        out.add("  get(999) of 1,000: " + thousand.steps().steps() + " steps (an array: 1 step)");
        thousand.steps().reset();
        thousand.insertAtEnd("the finish");
        out.add("  insertAtEnd on 1,000 with only a head: " + thousand.steps().steps() + " steps");
        out.add("  each node also holds a next pointer and an object header: about 24 bytes, against 4 per int in an array");
        out.add("  already in Java: java.util.LinkedList, which links both ways");
        return out.text();
    }
}
