package com.jk.explore.singlylinkedlist;

/**
 * A node of a singly linked list: the data it holds and a pointer to the next node.
 *
 * <p>Exactly the {@code struct node { data; struct node *next; }} of a C textbook. The last node's
 * {@code next} is {@code null}.
 */
public final class Node {

    /** The data this node holds: here, one clue of the treasure hunt. */
    final String data;

    /** Pointer to the next node, or {@code null} when this is the last node. */
    Node next;

    Node(String data, Node next) {
        this.data = data;
        this.next = next;
    }

    /** The data in this node. */
    public String data() {
        return data;
    }

    /** The next node, or {@code null}. */
    public Node next() {
        return next;
    }
}
