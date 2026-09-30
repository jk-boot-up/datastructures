package com.jk.explore.doublylinkedlistrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the recursive doubly linked list must have, and how deep each recursion goes. */
class RecursiveDoublyLinkedListTest {

    private static RecursiveDoublyLinkedList of(String... values) {
        RecursiveDoublyLinkedList list = new RecursiveDoublyLinkedList();
        for (String v : values) {
            list.insertAtEnd(v);
        }
        list.steps().reset();
        return list;
    }

    private static void assertConsistent(RecursiveDoublyLinkedList list) {
        Node prev = null;
        for (Node current = list.head(); current != null; current = current.next()) {
            assertSame(prev, current.prev());
            prev = current;
        }
        assertSame(prev, list.tail());
    }

    @Test
    void anEmptyListIsTheBaseCase() {
        RecursiveDoublyLinkedList list = new RecursiveDoublyLinkedList();
        assertTrue(list.isEmpty());
        assertEquals(0, list.count());
        assertEquals("head -> null <- tail", list.displayForward());
        assertEquals("tail -> null <- head", list.displayBackward());
        assertEquals(0, list.steps().maxDepth());
    }

    @Test
    void displayRecursesBothWays() {
        RecursiveDoublyLinkedList list = of("a", "b", "c");
        assertEquals("head -> a <-> b <-> c <- tail", list.displayForward());
        assertEquals(3, list.steps().maxDepth());
        assertEquals("tail -> c <-> b <-> a <- head", list.displayBackward());
    }

    @Test
    void workAtTheEndsIsNotRecursive() {
        RecursiveDoublyLinkedList list = of("b");
        list.insertAtBeginning("a");
        list.insertAtEnd("c");
        assertEquals("a", list.deleteAtBeginning());
        assertEquals("c", list.deleteAtEnd());
        assertEquals(0, list.steps().maxDepth());
        assertConsistent(list);
        assertEquals("b", list.deleteAtEnd());
        assertNull(list.head());
        assertNull(list.tail());
    }

    @Test
    void deletingFromAnEmptyListIsAnUnderflow() {
        RecursiveDoublyLinkedList list = new RecursiveDoublyLinkedList();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtEnd);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtBeginning);
    }

    @Test
    void insertAtPositionFindsRecursivelyAndLinksFourPointers() {
        RecursiveDoublyLinkedList list = of("a", "b", "c");
        list.insertAtPosition(2, "x");
        assertEquals("[a <-> b <-> x <-> c]", list.toString());
        assertEquals(1, list.steps().maxDepth());
        assertEquals(4, list.steps().pointerChanges());
        list.insertAtPosition(0, "y");
        list.insertAtPosition(5, "z");
        assertEquals("[y <-> a <-> b <-> x <-> c <-> z]", list.toString());
        assertConsistent(list);
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(9, "w"));
    }

    @Test
    void deleteByKeyFindsRecursivelyAndUnlinksInConstantTime() {
        RecursiveDoublyLinkedList list = of("a", "b", "c");
        assertTrue(list.deleteByKey("b"));
        assertEquals(2, list.steps().maxDepth());
        assertEquals(2, list.steps().pointerChanges());
        assertFalse(list.deleteByKey("zz"));
        assertEquals("[a <-> c]", list.toString());
        assertConsistent(list);
    }

    @Test
    void searchCountAndGetRecurse() {
        RecursiveDoublyLinkedList list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(3, list.steps().maxDepth());
        assertEquals(-1, list.search("zz"));
        assertEquals(3, list.count());
        assertEquals("b", list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    void reverseSwapsPointersRecursively() {
        RecursiveDoublyLinkedList list = of("a", "b", "c", "d");
        list.reverse();
        assertEquals("[d <-> c <-> b <-> a]", list.toString());
        assertEquals(4, list.steps().maxDepth());
        assertConsistent(list);
    }

    @Test
    void aMillionNodesOverflowTheCallStack() {
        RecursiveDoublyLinkedList list = new RecursiveDoublyLinkedList();
        for (int i = 0; i < 1_000_000; i++) {
            list.insertAtEnd("x");
        }
        assertThrows(StackOverflowError.class, list::count);
    }
}
