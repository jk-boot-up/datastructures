package com.jk.explore.dsa.linkedlists.single;

import java.util.NoSuchElementException;

/**
 * Generic Singly Linked List Implementation
 *
 * Overall Space Complexity: O(n)
 * - Each node stores data + next reference
 */
public class LinkedList<T> {

    /**
     * Node class
     *
     * Space Complexity: O(1) per node
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // =========================
    // Add Operations
    // =========================

    /**
     * Adds element at the end of the list
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Adds element at the beginning
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Adds element at a specific index
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public void addAt(int index, T data) {
        checkPositionIndex(index);

        if (index == 0) {
            addFirst(data);
            return;
        }
        if (index == size) {
            add(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> prev = getNode(index - 1);

        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    // =========================
    // Access Operations
    // =========================

    /**
     * Retrieves element at a given index
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).data;
    }

    // =========================
    // Remove Operations
    // =========================

    /**
     * Removes the first element
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        T data = head.data;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        size--;
        return data;
    }

    /**
     * Removes the last element
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public T removeLast() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        if (size == 1) {
            return removeFirst();
        }

        Node<T> prev = getNode(size - 2);
        T data = tail.data;

        prev.next = null;
        tail = prev;
        size--;

        return data;
    }

    /**
     * Removes element at a specific index
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public T removeAt(int index) {
        checkElementIndex(index);

        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node<T> prev = getNode(index - 1);
        Node<T> current = prev.next;

        prev.next = current.next;
        size--;

        return current.data;
    }

    // =========================
    // Utility Operations
    // =========================

    /**
     * Prints the linked list
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public void print() {
        Node<T> current = head;

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    /**
     * Returns size of the list
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public int size() {
        return size;
    }

    // =========================
    // Helper Methods
    // =========================

    /**
     * Retrieves node at index
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private Node<T> getNode(int index) {
        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Validates element index
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    /**
     * Validates position index
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
