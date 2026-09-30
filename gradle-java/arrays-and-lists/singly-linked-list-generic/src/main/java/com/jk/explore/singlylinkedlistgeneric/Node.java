package com.jk.explore.singlylinkedlistgeneric;

/**
 * A node of a generic singly linked list: the data it holds, of any type {@code T}, and a pointer
 * to the next node.
 *
 * <p>The {@code struct node { data; struct node *next; }} of a C textbook, with the data's type a
 * parameter. The last node's
 * {@code next} is {@code null}.
 */
public final class Node<T> {

    /** The data this node holds: a reference to an object of type T. */
    final T data;

    /** Pointer to the next node, or {@code null} when this is the last node. */
    Node<T> next;

    Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    /** The data in this node. */
    public T data() {
        return data;
    }

    /** The next node, or {@code null}. */
    public Node<T> next() {
        return next;
    }
}
