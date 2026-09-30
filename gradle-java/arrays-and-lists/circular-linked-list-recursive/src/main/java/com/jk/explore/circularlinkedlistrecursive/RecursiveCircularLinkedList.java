package com.jk.explore.circularlinkedlistrecursive;

/**
 * A circular singly linked list whose walks are written recursively.
 *
 * <p>The same structure as the circular-linked-list project: the last node points back to the
 * first, and the list keeps only {@code last}, with the first node at {@code last.next}. A
 * recursion over a circular list cannot stop at {@code null}, because there is none: its base case
 * is reaching {@code last}, the end of one lap. Work at the beginning and at the end needs no
 * recursion; finding a node (to search, to insert at a position, to delete by key, or to find the
 * node before last) is recursive. Each call still waiting keeps a call-stack frame, so a recursive
 * lap of n nodes uses O(n) extra space.
 */
public class RecursiveCircularLinkedList {

    private Node last;
    private final StepCounter steps = new StepCounter();

    public Node last() {
        return last;
    }

    public Node first() {
        return last == null ? null : last.next;
    }

    public boolean isEmpty() {
        return last == null;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Display: this node, then the rest of the lap, stopping at last. O(n) time and stack. */
    public String display() {
        if (last == null) {
            return "(empty)";
        }
        StringBuilder s = new StringBuilder();
        display(last.next, s);
        return s.append("(back to ").append(last.next.data).append(')').toString();
    }

    private void display(Node node, StringBuilder s) {
        steps.enter();
        steps.step();
        s.append(node.data).append(" -> ");
        if (node != last) {            // base case: last is the end of the lap
            display(node.next, s);
        }
        steps.exit();
    }

    /** Count: 1 for last, otherwise 1 + the count of the rest of the lap. O(n) time and stack. */
    public int count() {
        return last == null ? 0 : count(last.next);
    }

    private int count(Node node) {
        steps.enter();
        steps.step();
        int c = node == last ? 1 : 1 + count(node.next);
        steps.exit();
        return c;
    }

    /** Insertion at the beginning: between last and the old first node. O(1), no recursion. */
    public void insertAtBeginning(String data) {
        Node newNode = new Node(data);
        if (last == null) {
            newNode.next = newNode;
            last = newNode;
            steps.pointer(2);
            return;
        }
        newNode.next = last.next;
        last.next = newNode;
        steps.pointer(2);
    }

    /** Insertion at the end: insert at the beginning, then move last on. O(1), no recursion. */
    public void insertAtEnd(String data) {
        insertAtBeginning(data);
        last = last.next;
        steps.pointer(1);
    }

    /**
     * Insertion at position {@code pos}: find the node before it recursively, then two pointers.
     * O(pos) time and stack.
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
        Node prev = nodeAt(last.next, pos - 1);
        Node newNode = new Node(data);
        newNode.next = prev.next;
        prev.next = newNode;
        steps.pointer(2);
        if (prev == last) {
            last = newNode;
            steps.pointer(1);
        }
    }

    /** The node {@code pos} places after {@code node}, not going past last. */
    private Node nodeAt(Node node, int pos) {
        if (pos == 0) {
            return node;               // base case
        }
        if (node == last) {
            throw new IndexOutOfBoundsException("no such position in this list");
        }
        steps.enter();
        steps.step();
        Node result = nodeAt(node.next, pos - 1);
        steps.exit();
        return result;
    }

    /** Deletion at the beginning: last points past the first node. O(1). */
    public String deleteAtBeginning() {
        if (last == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        Node first = last.next;
        if (first == last) {
            last = null;
        } else {
            last.next = first.next;
        }
        steps.pointer(1);
        return first.data;
    }

    /**
     * Deletion at the end: find the node before last recursively; it points to the first node and
     * becomes last. O(n) time and stack.
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
        Node prev = nodeBeforeLast(last.next);
        prev.next = last.next;
        last = prev;
        steps.pointer(2);
        return data;
    }

    private Node nodeBeforeLast(Node node) {
        if (node.next == last) {
            return node;               // base case: the next node is last
        }
        steps.enter();
        steps.step();
        Node result = nodeBeforeLast(node.next);
        steps.exit();
        return result;
    }

    /**
     * Deletion by key: find the node before the one holding {@code key}, recursively round one
     * lap, then point it past. O(n) time and stack.
     */
    public boolean deleteByKey(String key) {
        if (last == null) {
            return false;
        }
        Node prev = beforeKey(last, key);
        if (prev == null) {
            return false;
        }
        Node gone = prev.next;
        if (gone == prev) {             // the only node
            last = null;
        } else {
            prev.next = gone.next;
            if (gone == last) {
                last = prev;
            }
        }
        steps.pointer(1);
        return true;
    }

    /** The node whose next holds {@code key}, starting from {@code prev}, or null after one lap. */
    private Node beforeKey(Node prev, String key) {
        steps.enter();
        steps.compare();
        Node result;
        if (prev.next.data.equals(key)) {
            result = prev;             // base case: found
        } else if (prev.next == last) {
            result = null;             // base case: a whole lap, not found
        } else {
            result = beforeKey(prev.next, key);
        }
        steps.exit();
        return result;
    }

    /** Search: the position of the first node holding {@code key} in one lap, or -1. O(n) time and stack. */
    public int search(String key) {
        return last == null ? -1 : search(last.next, key, 0);
    }

    private int search(Node node, String key, int pos) {
        steps.enter();
        steps.compare();
        int result;
        if (node.data.equals(key)) {
            result = pos;
        } else if (node == last) {
            result = -1;               // base case: the lap is over
        } else {
            result = search(node.next, key, pos + 1);
        }
        steps.exit();
        return result;
    }

    /**
     * The counting-out game worked out without the list: the winner's position (from 0) among
     * {@code n} players when every {@code k}-th leaves, by the recursive formula
     * J(1) = 0, J(n) = (J(n - 1) + k) mod n. O(n) time and stack.
     */
    public int winnerPosition(int n, int k) {
        steps.enter();
        int result = n == 1 ? 0 : (winnerPosition(n - 1, k) + k) % n;
        steps.exit();
        return result;
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
