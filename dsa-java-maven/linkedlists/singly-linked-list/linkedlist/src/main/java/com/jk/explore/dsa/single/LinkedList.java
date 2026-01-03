package com.jk.explore.dsa.single;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A custom implementation of a singly linked list that stores integer values.
 *
 * <p>
 * This linked list maintains references to both the {@code head} and {@code tail}
 * nodes to allow efficient insertions at both ends.
 * </p>
 *
 * <p>
 * Supported operations include:
 * </p>
 * <ul>
 *     <li>Insertion at the beginning and end</li>
 *     <li>Insertion at a specific index</li>
 *     <li>Removal by value and from the end</li>
 *     <li>Search (contains)</li>
 * </ul>
 *
 * <p>
 * The list does not allow null elements and uses zero-based indexing.
 * </p>
 */
public class LinkedList {

    /** Reference to the first node in the list */
    private Node head;

    /** Reference to the last node in the list */
    private Node tail;

    /** Number of elements currently stored in the list */
    @Getter
    private int length;

    /**
     * Creates an empty linked list.
     */
    public LinkedList() {
    }

    /**
     * Appends a new element at the end of the list.
     *
     * <p>
     * Time Complexity: O(1)
     * </p>
     *
     * @param data the value to be added
     */
    public void addLast(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        incrementLength();
    }

    /**
     * Inserts a new element at the beginning of the list.
     *
     * <p>
     * Time Complexity: O(1)
     * </p>
     *
     * @param data the value to be added
     */
    public void addFirst(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        incrementLength();
    }

    /**
     * Removes the first occurrence of the specified value from the list.
     *
     * <p>
     * If the value is not present, the list remains unchanged.
     * </p>
     *
     * <p>
     * Time Complexity: O(n)
     * </p>
     *
     * @param data the value to be removed
     */
    public void removeFirst(int data) {
        if (head == null) {
            return;
        }

        if (head.data == data) {
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
            }
            decrementLength();
            return;
        }

        Node prev = head;
        Node current = head.next;

        while (current != null) {
            if (current.data == data) {
                prev.next = current.next;
                if (current.next == null) {
                    tail = prev;
                }
                decrementLength();
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    /**
     * Removes the first occurrence of the specified value from the list.
     *
     * <p>
     * This method is an alias for {@link #removeFirst(int)}.
     * </p>
     *
     * @param data the value to be removed
     */
    public void remove(int data) {
        removeFirst(data);
    }

    /**
     * Removes the last element from the list.
     *
     * <p>
     * If the list is empty, no action is performed.
     * </p>
     *
     * <p>
     * Time Complexity: O(n)
     * </p>
     */
    public void removeLast() {
        if (head == null) {
            return;
        }

        if (head == tail) {
            head = tail = null;
            decrementLength();
            return;
        }

        Node prev = head;
        Node current = head.next;

        while (current.next != null) {
            prev = current;
            current = current.next;
        }

        tail = prev;
        tail.next = null;
        decrementLength();
    }

    /**
     * Checks whether the list contains the specified value.
     *
     * <p>
     * Time Complexity: O(n)
     * </p>
     *
     * @param data the value to search for
     * @return {@code true} if the value exists, {@code false} otherwise
     */
    public boolean contains(int data) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.data == data) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    /**
     * Inserts a new element at the specified index.
     *
     * <p>
     * Index is zero-based. If the index is invalid, the operation is ignored.
     * </p>
     *
     * <p>
     * Time Complexity: O(n)
     * </p>
     *
     * @param index the position where the element should be inserted
     * @param data  the value to be inserted
     */
    public void insert(int index, int data) {
        if (index <= -1 || index > length) {
            return;
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == length) {
            addLast(data);
            return;
        }

        int currentIndex = 1;
        Node currentNode = head.next;
        Node prevNode = head;

        while (currentIndex < index && currentNode != null) {
            currentIndex++;
            prevNode = currentNode;
            currentNode = currentNode.next;
        }

        Node newNode = new Node(data);
        newNode.next = currentNode;
        prevNode.next = newNode;

        if (currentNode == null) {
            tail = newNode;
        }

        incrementLength();
    }

    /**
     * Increments the length of the list.
     */
    protected void incrementLength() {
        length++;
    }

    /**
     * Decrements the length of the list.
     */
    protected void decrementLength() {
        length--;
    }

    /**
     * Returns a string representation of the list.
     *
     * <p>
     * Format example: {@code [  10, 20, 30 ]}
     * </p>
     *
     * @return formatted string representing the list contents
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node currentNode = head;
        sb.append("[ ");

        if (length != 0) {
            while (currentNode != null) {
                sb.append(" ").append(currentNode.data);
                if (currentNode.next != null) {
                    sb.append(",");
                }
                currentNode = currentNode.next;
            }
        }

        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Represents a single node in the linked list.
     *
     * <p>
     * Each node contains data and a reference to the next node.
     * </p>
     */
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    static class Node {

        /** Value stored in the node */
        private int data;

        /** Reference to the next node */
        @ToString.Exclude
        private Node next;

        /**
         * Creates an empty node.
         */
        public Node() {
        }

        /**
         * Creates a node with the specified value.
         *
         * @param data the value to store
         */
        public Node(int data) {
            this.data = data;
        }
    }
}
