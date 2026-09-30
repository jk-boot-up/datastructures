package com.jk.explore.doublylinkedlistrecursive;

/**
 * A node of a doubly linked list: its data, a pointer to the previous node and a pointer to the
 * next node, as {@code struct node { data; struct node *prev, *next; }} in a C textbook. The head's
 * {@code prev} and the tail's {@code next} are {@code null}.
 */
public final class Node {

    /** The data this node holds: here, the name of one photo. */
    final String data;

    /** Pointer to the previous node, or {@code null} for the head. */
    Node prev;

    /** Pointer to the next node, or {@code null} for the tail. */
    Node next;

    Node(String data) {
        this.data = data;
    }

    public String data() {
        return data;
    }

    public Node prev() {
        return prev;
    }

    public Node next() {
        return next;
    }
}
