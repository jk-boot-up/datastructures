package com.jk.explore.doublylinkedlistgenericrecursive;

/**
 * A node of a doubly linked list: its data, a pointer to the previous node and a pointer to the
 * next node, as {@code struct node { data; struct node *prev, *next; }} in a C textbook. The head's
 * {@code prev} and the tail's {@code next} are {@code null}.
 */
public final class Node<T> {

    /** The data this node holds: here, the name of one photo. */
    final T data;

    /** Pointer to the previous node, or {@code null} for the head. */
    Node<T> prev;

    /** Pointer to the next node, or {@code null} for the tail. */
    Node<T> next;

    Node(T data) {
        this.data = data;
    }

    public T data() {
        return data;
    }

    public Node<T> prev() {
        return prev;
    }

    public Node<T> next() {
        return next;
    }
}
