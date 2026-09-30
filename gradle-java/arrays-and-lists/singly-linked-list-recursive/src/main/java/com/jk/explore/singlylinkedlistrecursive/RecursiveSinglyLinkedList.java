package com.jk.explore.singlylinkedlistrecursive;

/**
 * A singly linked list whose operations are written recursively.
 *
 * <p>The recursive view of a list: a list is either empty ({@code null}) or a node followed by a
 * smaller list, {@code node.next}. So every operation has a base case, the empty list or the node
 * it is looking for, and a recursive case that handles one node and calls itself on
 * {@code node.next}. Operations that change the list are written the textbook way: the private
 * method returns the (possibly new) first node of the list it was given, and the caller stores it,
 * as in {@code node.next = deleteByKey(node.next, key)}. Each call still waiting keeps a frame on
 * the call stack, so a recursion over n nodes uses O(n) extra space, where the loops of the
 * singly-linked-list project use O(1). Insertion and deletion at the beginning need no recursion.
 */
public class RecursiveSinglyLinkedList {

    private Node head;
    private final StepCounter steps = new StepCounter();

    public Node head() {
        return head;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Display: this node's data, then the display of the rest. O(n) time and stack. */
    public String display() {
        StringBuilder s = new StringBuilder("head -> ");
        display(head, s);
        return s.append("null").toString();
    }

    private void display(Node node, StringBuilder s) {
        if (node == null) {            // base case: the empty list
            return;
        }
        steps.enter();
        s.append(node.data).append(" -> ");
        steps.step();
        display(node.next, s);
        steps.exit();
    }

    /**
     * Display in reverse: the rest of the list first, then this node. Printing after the recursive
     * call is all it takes; a loop would need a stack of its own. O(n) time and stack.
     */
    public String displayReverse() {
        StringBuilder s = new StringBuilder();
        displayReverse(head, s);
        return s.append("(head)").toString();
    }

    private void displayReverse(Node node, StringBuilder s) {
        if (node == null) {
            return;
        }
        steps.enter();
        steps.step();
        displayReverse(node.next, s);  // the rest first ...
        s.append(node.data).append(" <- ");  // ... then this node, on the way back
        steps.exit();
    }

    /** Count: 0 for the empty list, otherwise 1 + the count of the rest. O(n) time and stack. */
    public int count() {
        return count(head);
    }

    private int count(Node node) {
        if (node == null) {
            return 0;                  // base case: the empty list has no nodes
        }
        steps.enter();
        steps.step();
        int c = 1 + count(node.next);
        steps.exit();
        return c;
    }

    /** Insertion at the beginning: two pointer changes, no recursion needed. O(1). */
    public void insertAtBeginning(String data) {
        Node newNode = new Node(data, null);
        newNode.next = head;
        steps.pointer();
        head = newNode;
        steps.pointer();
    }

    /** Insertion at the end: the end of the empty list is a new node; otherwise insert into the rest. O(n). */
    public void insertAtEnd(String data) {
        head = insertAtEnd(head, data);
    }

    private Node insertAtEnd(Node node, String data) {
        if (node == null) {            // base case: reached the end
            steps.pointer();
            return new Node(data, null);
        }
        steps.enter();
        steps.step();
        node.next = insertAtEnd(node.next, data);
        steps.exit();
        return node;
    }

    /**
     * Insertion at position {@code pos}: at position 0 of any list, the new node goes in front;
     * otherwise insert at pos - 1 of the rest. O(pos) time and stack.
     *
     * @throws IndexOutOfBoundsException when pos is negative or past the end
     */
    public void insertAtPosition(int pos, String data) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException("no position " + pos);
        }
        head = insertAtPosition(head, pos, data);
    }

    private Node insertAtPosition(Node node, int pos, String data) {
        if (pos == 0) {                // base case: the new node becomes the first of this list
            Node newNode = new Node(data, node);
            steps.pointer();
            steps.pointer();
            return newNode;
        }
        if (node == null) {
            throw new IndexOutOfBoundsException("no position in this list");
        }
        steps.enter();
        steps.step();
        node.next = insertAtPosition(node.next, pos - 1, data);
        steps.exit();
        return node;
    }

    /**
     * Deletion at the beginning: head moves on one node. O(1).
     *
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
     * Deletion at the end: the last node is replaced by the empty list. O(n) time and stack.
     *
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public String deleteAtEnd() {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        String[] deleted = new String[1];
        head = deleteAtEnd(head, deleted);
        return deleted[0];
    }

    private Node deleteAtEnd(Node node, String[] deleted) {
        if (node.next == null) {       // base case: this is the last node, so the rest is empty
            deleted[0] = node.data;
            steps.pointer();
            return null;
        }
        steps.enter();
        steps.step();
        node.next = deleteAtEnd(node.next, deleted);
        steps.exit();
        return node;
    }

    /**
     * Deletion by key: if this node holds the key, the list becomes the rest; otherwise delete from
     * the rest. O(n) time and stack.
     *
     * @return true if a node was deleted
     */
    public boolean deleteByKey(String key) {
        boolean[] found = new boolean[1];
        head = deleteByKey(head, key, found);
        return found[0];
    }

    private Node deleteByKey(Node node, String key, boolean[] found) {
        if (node == null) {            // base case: not found
            return null;
        }
        steps.enter();
        steps.compare();
        Node result;
        if (node.data.equals(key)) {   // base case: skip this node
            found[0] = true;
            steps.pointer();
            result = node.next;
        } else {
            node.next = deleteByKey(node.next, key, found);
            result = node;
        }
        steps.exit();
        return result;
    }

    /** Search: -1 in the empty list, pos if this node holds the key, otherwise search the rest. O(n). */
    public int search(String key) {
        return search(head, key, 0);
    }

    private int search(Node node, String key, int pos) {
        if (node == null) {
            return -1;                 // base case: not found
        }
        steps.enter();
        steps.compare();
        int result = node.data.equals(key) ? pos : search(node.next, key, pos + 1);
        steps.exit();
        return result;
    }

    /** The data at position {@code pos}: position 0 is this node; position pos is pos - 1 of the rest. */
    public String get(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        return get(head, pos);
    }

    private String get(Node node, int pos) {
        if (node == null) {
            throw new IndexOutOfBoundsException("no node at this position");
        }
        if (pos == 0) {
            return node.data;          // base case
        }
        steps.enter();
        steps.step();
        String result = get(node.next, pos - 1);
        steps.exit();
        return result;
    }

    /**
     * Reverses the list: reverse the rest, then hang this node on the end of the reversed rest.
     * The last node is the base case and becomes the new head. O(n) time and stack.
     */
    public void reverse() {
        head = reverse(head);
        steps.pointer();
    }

    private Node reverse(Node node) {
        if (node == null || node.next == null) {
            return node;               // base case: an empty or one-node list is its own reverse
        }
        steps.enter();
        Node newHead = reverse(node.next);
        node.next.next = node;         // the node after this one now points back to it
        node.next = null;              // and this node is, for now, the last
        steps.pointer();
        steps.exit();
        return newHead;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            s.append(current.data).append(" -> ");
            current = current.next;
        }
        return s.append("null]").toString();
    }
}
