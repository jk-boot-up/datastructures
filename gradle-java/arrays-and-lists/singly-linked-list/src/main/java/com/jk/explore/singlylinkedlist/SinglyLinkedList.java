package com.jk.explore.singlylinkedlist;

/**
 * A singly linked list: nodes joined by next pointers, and a pointer to the first node, the head.
 *
 * <p>Written the way a C textbook writes it: the list is just {@code head}; the last node's next
 * is {@code null}; the number of nodes is found by traversal ({@link #count()}), and every operation
 * walks the list with a {@code current} pointer (and a {@code prev} pointer when it must change the
 * node before). Insertion and deletion change one or two pointers and move no data. The price is
 * that there is no indexing: reaching position i means following i next pointers from the head.
 * Positions are counted from 0, like array indexes.
 */
public class SinglyLinkedList {

    private Node head;
    private final StepCounter steps = new StepCounter();

    /** The first node, or {@code null} when the list is empty. */
    public Node head() {
        return head;
    }

    /** True when {@code head} is {@code null}. */
    public boolean isEmpty() {
        return head == null;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Display (traversal): visits every node from the head and lists the data. O(n). */
    public String display() {
        StringBuilder s = new StringBuilder("head -> ");
        Node current = head;
        while (current != null) {
            s.append(current.data).append(" -> ");
            current = current.next;
            steps.step();
        }
        return s.append("null").toString();
    }

    /** Count (length): the number of nodes, found by traversing the whole list. O(n). */
    public int count() {
        int c = 0;
        Node current = head;
        while (current != null) {
            c++;
            current = current.next;
            steps.step();
        }
        return c;
    }

    /** Insertion at the beginning: the new node points to the old head, then becomes the head. O(1). */
    public void insertAtBeginning(String data) {
        Node newNode = new Node(data, null);
        newNode.next = head;
        steps.pointer();
        head = newNode;
        steps.pointer();
    }

    /** Insertion at the end: traverse to the last node, then link the new node after it. O(n). */
    public void insertAtEnd(String data) {
        Node newNode = new Node(data, null);
        if (head == null) {
            head = newNode;
            steps.pointer();
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
            steps.step();
        }
        current.next = newNode;
        steps.pointer();
    }

    /**
     * Insertion at position {@code pos} (0 = before the head): traverse to the node before the
     * position, {@code prev}, then change two pointers in this order: the new node points to the
     * rest of the list, then {@code prev} points to the new node. O(pos).
     *
     * @throws IndexOutOfBoundsException when pos is negative or past the end of the list
     */
    public void insertAtPosition(int pos, String data) {
        if (pos == 0) {
            insertAtBeginning(data);
            return;
        }
        Node prev = nodeBefore(pos);
        Node newNode = new Node(data, null);
        newNode.next = prev.next;   // 1: the new node points to the rest of the list
        steps.pointer();
        prev.next = newNode;        // 2: only now does the list point to the new node
        steps.pointer();
    }

    /**
     * The same insertion with its two pointer changes in the wrong order, kept to show the bug:
     * {@code prev.next} is overwritten first, so the rest of the list is no longer referenced, and
     * the new node's next is set to the new node itself.
     */
    public void insertAtPositionWrongOrder(int pos, String data) {
        Node prev = nodeBefore(pos);
        Node newNode = new Node(data, null);
        prev.next = newNode;        // the old rest of the list is now unreachable
        steps.pointer();
        newNode.next = prev.next;   // prev.next is newNode itself: a node pointing to itself
        steps.pointer();
    }

    /**
     * Deletion at the beginning: the head moves to the second node. O(1).
     *
     * @return the deleted data
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public String deleteAtBeginning() {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        String data = head.data;
        head = head.next;
        steps.pointer();
        return data;
    }

    /**
     * Deletion at the end: traverse to the second-to-last node and set its next to null. O(n).
     *
     * @return the deleted data
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public String deleteAtEnd() {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        if (head.next == null) {
            String data = head.data;
            head = null;
            steps.pointer();
            return data;
        }
        Node prev = head;
        while (prev.next.next != null) {
            prev = prev.next;
            steps.step();
        }
        String data = prev.next.data;
        prev.next = null;
        steps.pointer();
        return data;
    }

    /**
     * Deletion at position {@code pos}: traverse to the node before it and point past it. O(pos).
     *
     * @return the deleted data
     * @throws IllegalStateException "underflow" when the list is empty
     * @throws IndexOutOfBoundsException when there is no node at pos
     */
    public String deleteAtPosition(int pos) {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        if (pos == 0) {
            return deleteAtBeginning();
        }
        Node prev = nodeBefore(pos);
        if (prev.next == null) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        String data = prev.next.data;
        prev.next = prev.next.next;   // skip over the deleted node
        steps.pointer();
        return data;
    }

    /**
     * Deletion by key: the first node holding {@code key}. Keeps a {@code prev} pointer one node
     * behind {@code current}, because the node before the deleted one must be changed. O(n).
     *
     * @return true if a node was deleted
     */
    public boolean deleteByKey(String key) {
        Node prev = null;
        Node current = head;
        while (current != null) {
            steps.compare();
            if (current.data.equals(key)) {
                if (prev == null) {
                    head = current.next;      // deleting the head
                } else {
                    prev.next = current.next; // point past the deleted node
                }
                steps.pointer();
                return true;
            }
            prev = current;
            current = current.next;
            steps.step();
        }
        return false;
    }

    /** Search: the position of the first node holding {@code key}, or -1. O(n). */
    public int search(String key) {
        Node current = head;
        int pos = 0;
        while (current != null) {
            steps.compare();
            if (current.data.equals(key)) {
                return pos;
            }
            current = current.next;
            steps.step();
            pos++;
        }
        return -1;
    }

    /** The data at position {@code pos}: follow pos next pointers from the head. O(pos). */
    public String get(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        Node current = head;
        for (int i = 0; i < pos && current != null; i++) {
            current = current.next;
            steps.step();
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        return current.data;
    }

    /**
     * Reverses the list in place with three pointers, prev, current and next: each node's next is
     * turned to point backwards. One pointer change per node, O(n) time, O(1) extra space.
     */
    public void reverse() {
        Node prev = null;
        Node current = head;
        while (current != null) {
            Node next = current.next;   // save the rest before changing the pointer
            current.next = prev;
            steps.pointer();
            prev = current;
            current = next;
        }
        head = prev;
        steps.pointer();
    }

    /** The node just before position {@code pos} (pos &gt;= 1), reached in pos - 1 steps. */
    private Node nodeBefore(int pos) {
        if (pos < 1) {
            throw new IndexOutOfBoundsException("no position " + pos);
        }
        Node prev = head;
        for (int i = 0; i < pos - 1 && prev != null; i++) {
            prev = prev.next;
            steps.step();
        }
        if (prev == null) {
            throw new IndexOutOfBoundsException("no position " + pos + " in this list");
        }
        return prev;
    }

    /**
     * How many nodes can actually be reached from the head, stopping at {@code limit} so a loop
     * cannot run for ever, and a node found pointing to itself, if any. Used to show a broken list.
     */
    public Reach reach(int limit) {
        int n = 0;
        Node current = head;
        while (current != null && n < limit) {
            n++;
            if (current.next == current) {
                return new Reach(n, true, current.data);
            }
            current = current.next;
        }
        return new Reach(n, false, null);
    }

    /** What a traversal found: the nodes reached, and a node pointing to itself, if any. */
    public record Reach(int reachable, boolean selfLoop, String loopData) {
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        Node current = head;
        int guard = 0;
        while (current != null && guard++ < 10_000) {
            s.append(current.data).append(" -> ");
            current = current.next;
        }
        return s.append("null]").toString();
    }
}
