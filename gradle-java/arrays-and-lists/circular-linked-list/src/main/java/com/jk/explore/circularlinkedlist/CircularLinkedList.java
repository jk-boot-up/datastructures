package com.jk.explore.circularlinkedlist;

/**
 * A circular singly linked list: the last node's next pointer points back to the first node, so
 * the list has no end and no {@code null}.
 *
 * <p>Written the way a C textbook writes it, keeping a single pointer, {@code last}. The first node
 * is then {@code last.next}, so both ends are one step away: insertion at the beginning and at the
 * end are O(1) from one pointer. Every traversal starts at {@code last.next} and stops when it
 * comes back round to it (a do-while loop), because there is no {@code null} to stop at. An empty
 * list has {@code last == null}; a list of one node points to itself.
 */
public class CircularLinkedList {

    private Node last;
    private final StepCounter steps = new StepCounter();

    /** The last node, or {@code null} when the list is empty. */
    public Node last() {
        return last;
    }

    /** The first node, {@code last.next}, or {@code null} when the list is empty. */
    public Node first() {
        return last == null ? null : last.next;
    }

    public boolean isEmpty() {
        return last == null;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Display: from the first node round to the last, then back to the first. O(n). */
    public String display() {
        if (last == null) {
            return "(empty)";
        }
        StringBuilder s = new StringBuilder();
        Node current = last.next;
        do {
            s.append(current.data).append(" -> ");
            current = current.next;
            steps.step();
        } while (current != last.next);     // stop when back at the first node
        return s.append("(back to ").append(last.next.data).append(')').toString();
    }

    /** Count: go round once from the first node. O(n). */
    public int count() {
        if (last == null) {
            return 0;
        }
        int c = 0;
        Node current = last.next;
        do {
            c++;
            current = current.next;
            steps.step();
        } while (current != last.next);
        return c;
    }

    /**
     * Insertion at the beginning: the new node goes between last and the old first node. O(1).
     * In an empty list, the new node points to itself and becomes last.
     */
    public void insertAtBeginning(String data) {
        Node newNode = new Node(data);
        if (last == null) {
            newNode.next = newNode;     // a list of one node points to itself
            last = newNode;
            steps.pointer(2);
            return;
        }
        newNode.next = last.next;       // the new node points to the old first node
        last.next = newNode;            // last points to the new first node
        steps.pointer(2);
    }

    /** Insertion at the end: insert at the beginning, then move last on to the new node. O(1). */
    public void insertAtEnd(String data) {
        insertAtBeginning(data);
        last = last.next;               // the new node, now after last, becomes last
        steps.pointer(1);
    }

    /**
     * Insertion at position {@code pos} (0 = before the first node): walk to the node before the
     * position, then change two pointers. O(pos).
     *
     * @throws IndexOutOfBoundsException when pos is negative or past the end
     */
    public void insertAtPosition(int pos, String data) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException("no position " + pos);
        }
        if (pos == 0) {
            insertAtBeginning(data);
            return;
        }
        if (last == null) {
            throw new IndexOutOfBoundsException("no position " + pos + " in an empty list");
        }
        Node prev = last.next;
        for (int i = 0; i < pos - 1; i++) {
            prev = prev.next;
            steps.step();
            if (prev == last.next) {
                throw new IndexOutOfBoundsException("no position " + pos + " in this list");
            }
        }
        Node newNode = new Node(data);
        newNode.next = prev.next;
        prev.next = newNode;
        steps.pointer(2);
        if (prev == last) {
            last = newNode;             // inserted after the last node: it is the new last
            steps.pointer(1);
        }
    }

    /**
     * Deletion at the beginning: last points past the first node. O(1).
     *
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public String deleteAtBeginning() {
        if (last == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        Node first = last.next;
        if (first == last) {            // the only node
            last = null;
        } else {
            last.next = first.next;
        }
        steps.pointer(1);
        return first.data;
    }

    /**
     * Deletion at the end: walk round to the node before last, point it at the first node, and make
     * it last. O(n): the node before last can only be found by walking.
     *
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public String deleteAtEnd() {
        if (last == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        String data = last.data;
        if (last.next == last) {
            last = null;
            steps.pointer(1);
            return data;
        }
        Node prev = last.next;
        while (prev.next != last) {
            prev = prev.next;
            steps.step();
        }
        prev.next = last.next;
        last = prev;
        steps.pointer(2);
        return data;
    }

    /**
     * Deletion by key: go round once with prev and current; point prev past the node holding the
     * key. O(n).
     *
     * @return true if a node was deleted
     */
    public boolean deleteByKey(String key) {
        if (last == null) {
            return false;
        }
        Node prev = last;
        Node current = last.next;
        do {
            steps.compare();
            if (current.data.equals(key)) {
                if (current == prev) {          // the only node
                    last = null;
                    steps.pointer(1);
                    return true;
                }
                prev.next = current.next;
                steps.pointer(1);
                if (current == last) {
                    last = prev;
                    steps.pointer(1);
                }
                return true;
            }
            prev = current;
            current = current.next;
            steps.step();
        } while (current != last.next);
        return false;
    }

    /** Search: the position of the first node holding {@code key}, going round once, or -1. O(n). */
    public int search(String key) {
        if (last == null) {
            return -1;
        }
        Node current = last.next;
        int pos = 0;
        do {
            steps.compare();
            if (current.data.equals(key)) {
                return pos;
            }
            current = current.next;
            steps.step();
            pos++;
        } while (current != last.next);
        return -1;
    }

    /** The data of the next {@code n} turns, starting from the first node and going round as often as needed. */
    public String turns(int n) {
        StringBuilder s = new StringBuilder();
        Node current = last.next;
        for (int i = 0; i < n; i++) {
            s.append(i > 0 ? ", " : "").append(current.data);
            current = current.next;
            steps.step();
        }
        return s.toString();
    }

    /**
     * A loop written for a straight list, {@code while (current != null)}: on a circular list it
     * never ends, so it is stopped after {@code limit} steps. Returns the steps walked.
     */
    public int walkUntilNull(int limit) {
        int n = 0;
        Node current = last == null ? null : last.next;
        while (current != null && n < limit) {
            current = current.next;
            n++;
        }
        return n;
    }

    /**
     * The counting-out game: starting from the first node, count {@code k} players round the
     * circle and delete the k-th, again and again, until one is left. Returns the players in the
     * order they left, then the winner.
     */
    public String countOut(int k) {
        StringBuilder out = new StringBuilder();
        Node prev = last;
        while (last != null && last.next != last) {
            for (int i = 1; i < k; i++) {
                prev = prev.next;
                steps.step();
            }
            Node gone = prev.next;
            prev.next = gone.next;              // the player before points past the one who is out
            steps.pointer(1);
            if (gone == last) {
                last = prev;
            }
            out.append(out.length() > 0 ? ", " : "").append(gone.data);
        }
        return "out: " + out + "; winner " + (last == null ? "none" : last.data);
    }

    @Override
    public String toString() {
        if (last == null) {
            return "(empty)";
        }
        StringBuilder s = new StringBuilder();
        Node current = last.next;
        do {
            s.append(current.data).append(" -> ");
            current = current.next;
        } while (current != last.next);
        return s.append("(back to ").append(last.next.data).append(')').toString();
    }
}
