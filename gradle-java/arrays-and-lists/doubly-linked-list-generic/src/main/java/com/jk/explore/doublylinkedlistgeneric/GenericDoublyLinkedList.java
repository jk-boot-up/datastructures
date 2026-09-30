package com.jk.explore.doublylinkedlistgeneric;

/**
 * A generic doubly linked list, holding data of any type {@code T}: every node has a {@code prev} and a {@code next} pointer, and the list
 * keeps pointers to both ends, {@code head} and {@code tail}.
 *
 * <p>Written the way a C textbook writes it, with the textbook operations. Because each node knows
 * the node before it, the list can be displayed backwards from the tail, deleting at the end is
 * O(1), and a node already reached can be deleted in O(1) without searching for its predecessor.
 * The price is a second pointer in every node, and four pointers to set on every insertion in the
 * middle. The number of nodes is found by traversal ({@link #count()}); positions count from 0.
 *
 * <p>This is the doubly-linked-list project with the element type as a type parameter: each
 * {@code Node<T>} refers to a {@code T}, and nodes are matched with {@code equals}. It is the shape of
 * {@code java.util.LinkedList<E>}.
 *
 * @param <T> the element type
 */
public class GenericDoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private final StepCounter steps = new StepCounter();

    public Node<T> head() {
        return head;
    }

    public Node<T> tail() {
        return tail;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Display forward: follow next pointers from head to null. O(n). */
    public String displayForward() {
        StringBuilder s = new StringBuilder("head -> ");
        for (Node<T> current = head; current != null; current = current.next) {
            s.append(current.data).append(current.next != null ? " <-> " : "");
            steps.step();
        }
        return s.append(" <- tail").toString().replace("head ->  <- tail", "head -> null <- tail");
    }

    /** Display backward: follow prev pointers from tail to null. O(n); impossible in a singly linked list. */
    public String displayBackward() {
        StringBuilder s = new StringBuilder("tail -> ");
        for (Node<T> current = tail; current != null; current = current.prev) {
            s.append(current.data).append(current.prev != null ? " <-> " : "");
            steps.step();
        }
        return s.append(" <- head").toString().replace("tail ->  <- head", "tail -> null <- head");
    }

    /** Count: the number of nodes, by traversal. O(n). */
    public int count() {
        int c = 0;
        for (Node<T> current = head; current != null; current = current.next) {
            c++;
            steps.step();
        }
        return c;
    }

    /** Insertion at the beginning: newNode.next = head, head.prev = newNode, head = newNode. O(1). */
    public Node<T> insertAtBeginning(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            steps.pointer(2);
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            steps.pointer(3);
        }
        return newNode;
    }

    /** Insertion at the end, through the tail pointer: no traversal. O(1). */
    public Node<T> insertAtEnd(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
            steps.pointer(2);
        } else {
            newNode.prev = tail;        // the new node's prev is the old last node
            tail.next = newNode;        // the old last node's next is the new node
            tail = newNode;
            steps.pointer(3);
        }
        return newNode;
    }

    /**
     * Insertion at position {@code pos} (0 = before the head): walk to the node before the
     * position, then set four pointers. O(pos).
     *
     * @throws IndexOutOfBoundsException when pos is negative or past the end
     */
    public Node<T> insertAtPosition(int pos, T data) {
        if (pos == 0) {
            return insertAtBeginning(data);
        }
        Node<T> prevNode = nodeAt(pos - 1);
        return insertAfter(prevNode, data);
    }

    /**
     * Inserts a new node after {@code node}. Four pointers: the new node's {@code prev} and
     * {@code next}, then {@code node.next} and the following node's {@code prev}. O(1).
     */
    public Node<T> insertAfter(Node<T> node, T data) {
        if (node == tail) {
            return insertAtEnd(data);
        }
        Node<T> following = node.next;
        Node<T> newNode = new Node<>(data);
        newNode.prev = node;            // 1
        newNode.next = following;       // 2
        node.next = newNode;            // 3
        following.prev = newNode;       // 4
        steps.pointer(4);
        return newNode;
    }

    /**
     * The same insertion, forgetting pointer 4 ({@code following.prev}), kept to show the bug:
     * traversing forwards finds the new node, traversing backwards skips it.
     */
    public Node<T> insertAfterForgettingPrev(Node<T> node, T data) {
        Node<T> following = node.next;
        Node<T> newNode = new Node<>(data);
        newNode.prev = node;
        newNode.next = following;
        node.next = newNode;
        steps.pointer(3);               // following.prev still points to node
        return newNode;
    }

    /**
     * Deletion at the beginning: head moves on, and the new head's prev becomes null. O(1).
     *
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public T deleteAtBeginning() {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        T data = head.data;
        deleteNode(head);
        return data;
    }

    /**
     * Deletion at the end, through the tail pointer: tail moves back, no traversal. O(1), where a
     * singly linked list needs O(n) to find the second-to-last node.
     *
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public T deleteAtEnd() {
        if (tail == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        T data = tail.data;
        deleteNode(tail);
        return data;
    }

    /**
     * Deletion at position {@code pos}: walk to the node, then unlink it. O(pos).
     *
     * @throws IllegalStateException "underflow" when the list is empty
     */
    public T deleteAtPosition(int pos) {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        Node<T> node = nodeAt(pos);
        deleteNode(node);
        return node.data;
    }

    /** Deletion by key: search for the first node holding {@code key}, then unlink it. O(n). */
    public boolean deleteByKey(T key) {
        for (Node<T> current = head; current != null; current = current.next) {
            steps.compare();
            if (current.data.equals(key)) {
                deleteNode(current);
                return true;
            }
            steps.step();
        }
        return false;
    }

    /**
     * Deletes a node already reached: its predecessor's {@code next} and its successor's
     * {@code prev} are set past it (or head and tail, at the ends). O(1): the node knows its
     * predecessor, so no search is needed.
     */
    public void deleteNode(Node<T> node) {
        if (node.prev == null) {
            head = node.next;           // deleting the first node
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;           // deleting the last node
        } else {
            node.next.prev = node.prev;
        }
        steps.pointer(2);
    }

    /** Search: the position of the first node holding {@code key}, or -1. O(n). */
    public int search(T key) {
        int pos = 0;
        for (Node<T> current = head; current != null; current = current.next) {
            steps.compare();
            if (current.data.equals(key)) {
                return pos;
            }
            steps.step();
            pos++;
        }
        return -1;
    }

    /** The node at position {@code pos}, walking from the head. O(pos). */
    public Node<T> nodeAt(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        Node<T> current = head;
        for (int i = 0; i < pos && current != null; i++) {
            current = current.next;
            steps.step();
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        return current;
    }

    /** The data at position {@code pos}. O(pos). */
    public T get(int pos) {
        return nodeAt(pos).data;
    }

    /**
     * Reverses the list in place: swap every node's prev and next, then swap head and tail. Two
     * pointer changes per node, O(n) time, O(1) extra space.
     */
    public void reverse() {
        Node<T> current = head;
        while (current != null) {
            Node<T> t = current.next;
            current.next = current.prev;
            current.prev = t;
            steps.pointer(2);
            current = t;                // the old next, now stored in prev
        }
        Node<T> t = head;
        head = tail;
        tail = t;
        steps.pointer(2);
    }

    /** The nodes reachable walking backwards from the tail, for showing a broken prev pointer. */
    public int countBackward() {
        int c = 0;
        for (Node<T> current = tail; current != null; current = current.prev) {
            c++;
        }
        return c;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (Node<T> current = head; current != null; current = current.next) {
            s.append(current.data).append(current.next != null ? " <-> " : "");
        }
        return s.append(']').toString();
    }
}
