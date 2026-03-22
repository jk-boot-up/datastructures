package com.jk.explore.dsa.linkedlists.single;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    // =========================
    // Add Tests
    // =========================

    @Test
    void testAdd() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddFirst() {
        list.addFirst(10);
        list.addFirst(20);

        assertEquals(2, list.size());
        assertEquals(20, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void testAddAt() {
        list.add(10);
        list.add(30);

        list.addAt(1, 20);

        assertEquals(3, list.size());
        assertEquals(20, list.get(1));
    }

    @Test
    void testAddAtBeginning() {
        list.add(10);
        list.addAt(0, 5);

        assertEquals(5, list.get(0));
    }

    @Test
    void testAddAtEnd() {
        list.add(10);
        list.addAt(1, 20);

        assertEquals(20, list.get(1));
    }

    // =========================
    // Get Tests
    // =========================

    @Test
    void testGet() {
        list.add(10);
        list.add(20);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testGetInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    // =========================
    // Remove Tests
    // =========================

    @Test
    void testRemoveFirst() {
        list.add(10);
        list.add(20);

        int removed = list.removeFirst();

        assertEquals(10, removed);
        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void testRemoveFirstSingleElement() {
        list.add(10);

        int removed = list.removeFirst();

        assertEquals(10, removed);
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveFirstEmpty() {
        assertThrows(RuntimeException.class, () -> list.removeFirst());
    }

    @Test
    void testRemoveLast() {
        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.removeLast();

        assertEquals(30, removed);
        assertEquals(2, list.size());
        assertEquals(20, list.get(1));
    }

    @Test
    void testRemoveLastSingleElement() {
        list.add(10);

        int removed = list.removeLast();

        assertEquals(10, removed);
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveAt() {
        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.removeAt(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    void testRemoveAtInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(0));
    }

    // =========================
    // Size Tests
    // =========================

    @Test
    void testSize() {
        assertEquals(0, list.size());

        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
    }

    // =========================
    // Mixed Operations
    // =========================

    @Test
    void testMixedOperations() {
        list.add(10);
        list.addFirst(5);
        list.add(20);
        list.addAt(2, 15);

        // List: 5 -> 10 -> 15 -> 20

        assertEquals(4, list.size());
        assertEquals(15, list.get(2));

        list.removeAt(1); // remove 10

        assertEquals(3, list.size());
        assertEquals(15, list.get(1));
    }
}
