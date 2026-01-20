package com.jk.explore.dsa.single;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link LinkedList}.
 *
 * <p>
 * This test class validates the behavior of a custom singly linked list
 * implementation, covering:
 * </p>
 * <ul>
 *     <li>Insertion at head and tail</li>
 *     <li>Insertion at arbitrary index</li>
 *     <li>Removal by position and value</li>
 *     <li>Search operations</li>
 *     <li>Edge cases (empty list, single element list)</li>
 * </ul>
 *
 * <p>
 * JUnit 5 is used for writing and executing the test cases.
 * </p>
 */
public class LinkedListTest {

    /**
     * Verifies adding an element to an empty list using {@code addLast()}.
     * Ensures length is updated and string representation is correct.
     */
    @Test
    void addFirst0() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        assertEquals(1, linkedList.getLength());
        assertEquals("[  10 ]", linkedList.toString());
    }

    /**
     * Tests adding multiple elements at the end of the list.
     * Confirms order, size, and formatting.
     */
    @Test
    void addLast0() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(20);
        assertEquals(1, linkedList.getLength());
        assertEquals("[  20 ]", linkedList.toString());
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals(3, linkedList.getLength());
        assertEquals("[  20, 30, 40 ]", linkedList.toString());
    }

    /**
     * Validates sequential insertions at the end of the list.
     */
    @Test
    void addLast() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        assertEquals(2, linkedList.getLength());
        assertEquals("[  10, 20 ]", linkedList.toString());
        linkedList.addLast(30);
        assertEquals(3, linkedList.getLength());
        assertEquals("[  10, 20, 30 ]", linkedList.toString());
    }

    /**
     * Tests insertion at the beginning of the list using {@code addFirst()}.
     * Ensures new elements become the head.
     */
    @Test
    void addFirst() {
        LinkedList linkedList = new LinkedList();
        linkedList.addFirst(5);
        assertEquals(1, linkedList.getLength());
        assertEquals("[  5 ]", linkedList.toString());
        linkedList.addFirst(20);
        assertEquals(2, linkedList.getLength());
        assertEquals("[  20, 5 ]", linkedList.toString());
        linkedList.addFirst(30);
        assertEquals(3, linkedList.getLength());
        assertEquals("[  30, 20, 5 ]", linkedList.toString());
    }

    /**
     * Tests removing the first element when the list contains only one node.
     * Ensures the list becomes empty.
     */
    @Test
    void removeFirstIn1() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        assertEquals(1, linkedList.getLength());
        linkedList.removeFirst(10);
        assertEquals(0, linkedList.getLength());
        assertEquals("[  ]", linkedList.toString());
    }

    /**
     * Tests inserting a new element at index 0.
     * Confirms the element becomes the new head.
     */
    @Test
    void insertAtFirst() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals("[  10, 20, 30, 40 ]", linkedList.toString());
        linkedList.insert(0, 5);
        assertEquals("[  5, 10, 20, 30, 40 ]", linkedList.toString());
    }

    /**
     * Tests inserting an element at the end of the list using {@code insert()}.
     */
    @Test
    void insertAtLast() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals("[  10, 20, 30, 40 ]", linkedList.toString());
        linkedList.insert(4, 50);
        assertEquals("[  10, 20, 30, 40, 50 ]", linkedList.toString());
    }

    /**
     * Tests inserting an element in the middle of the list.
     */
    @Test
    void insert() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals("[  10, 20, 30, 40 ]", linkedList.toString());
        linkedList.insert(2, 25);
        assertEquals("[  10, 20, 25, 30, 40 ]", linkedList.toString());
    }

    /**
     * Tests searching for existing and non-existing elements in the list.
     */
    @Test
    void contains() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(30);
        linkedList.addLast(60);
        linkedList.addLast(70);
        linkedList.addLast(100);
        assertTrue(linkedList.contains(10));
        assertTrue(linkedList.contains(30));
        assertTrue(linkedList.contains(60));
        assertTrue(linkedList.contains(70));
        assertTrue(linkedList.contains(100));
        assertFalse(linkedList.contains(50));
    }

    /**
     * Tests {@code contains()} on a single-element list.
     */
    @Test
    void containsIn1() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        assertTrue(linkedList.contains(10));
    }

    /**
     * Tests {@code contains()} when searching for the tail element.
     */
    @Test
    void containsIn2() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        assertTrue(linkedList.contains(20));
    }

    /**
     * Tests removing the last element from a single-element list.
     */
    @Test
    void removeLastIn1() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        assertEquals("[  10 ]", linkedList.toString());
        linkedList.removeLast();
        assertEquals("[  ]", linkedList.toString());
    }

    /**
     * Tests removing the last element from a two-element list.
     */
    @Test
    void removeLastIn2() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        assertEquals("[  10, 20 ]", linkedList.toString());
        linkedList.removeLast();
        assertEquals("[  10 ]", linkedList.toString());
    }

    /**
     * Tests removing the last element from a multi-element list.
     */
    @Test
    void removeLast() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals("[  10, 20, 30, 40 ]", linkedList.toString());
        linkedList.removeLast();
        assertEquals("[  10, 20, 30 ]", linkedList.toString());
    }

    /**
     * Tests removing a specific value that exists at the end of the list.
     */
    @Test
    void removeLastData() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals("[  10, 20, 30, 40 ]", linkedList.toString());
        linkedList.remove(40);
        assertEquals("[  10, 20, 30 ]", linkedList.toString());
    }

    /**
     * Tests removing a specific value that exists at the beginning of the list.
     */
    @Test
    void removeFirstData() {
        LinkedList linkedList = new LinkedList();
        linkedList.addLast(10);
        linkedList.addLast(20);
        linkedList.addLast(30);
        linkedList.addLast(40);
        assertEquals("[  10, 20, 30, 40 ]", linkedList.toString());
        linkedList.remove(10);
        assertEquals("[  20, 30, 40 ]", linkedList.toString());
    }
}
