package com.jk.explore.circularlinkedlistrecursive;

/**
 * A node of a circular linked list: the data it holds and a pointer to the next node. In a
 * circular list no node's {@code next} is {@code null}: the last node's next is the first node.
 */
public final class Node {

    /** The data this node holds: here, one player's name. */
    final String data;

    /** Pointer to the next node; for the last node, the first node. */
    Node next;

    Node(String data) {
        this.data = data;
    }

    public String data() {
        return data;
    }

    public Node next() {
        return next;
    }
}
