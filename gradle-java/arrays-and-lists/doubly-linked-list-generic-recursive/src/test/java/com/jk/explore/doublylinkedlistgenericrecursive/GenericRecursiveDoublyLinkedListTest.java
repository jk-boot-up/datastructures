package com.jk.explore.doublylinkedlistgenericrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the generic recursive doubly linked list must have, and how deep each recursion goes. */
class GenericRecursiveDoublyLinkedListTest {

    private static GenericRecursiveDoublyLinkedList<String> of(String... values) {
        GenericRecursiveDoublyLinkedList<String> list = new GenericRecursiveDoublyLinkedList<String>();
        for (String v : values) {
            list.insertAtEnd(v);
        }
        list.steps().reset();
        return list;
    }

    private static void assertConsistent(GenericRecursiveDoublyLinkedList<String> list) {
        Node<String> prev = null;
        for (Node<String> current = list.head(); current != null; current = current.next()) {
            assertSame(prev, current.prev());
            prev = current;
        }
        assertSame(prev, list.tail());
    }

    @Test
    void anEmptyListIsTheBaseCase() {
        GenericRecursiveDoublyLinkedList<String> list = new GenericRecursiveDoublyLinkedList<String>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.count());
        assertEquals("head -> null <- tail", list.displayForward());
        assertEquals("tail -> null <- head", list.displayBackward());
        assertEquals(0, list.steps().maxDepth());
    }

    @Test
    void displayRecursesBothWays() {
        GenericRecursiveDoublyLinkedList<String> list = of("a", "b", "c");
        assertEquals("head -> a <-> b <-> c <- tail", list.displayForward());
        assertEquals(3, list.steps().maxDepth());
        assertEquals("tail -> c <-> b <-> a <- head", list.displayBackward());
    }

    @Test
    void workAtTheEndsIsNotRecursive() {
        GenericRecursiveDoublyLinkedList<String> list = of("b");
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
        GenericRecursiveDoublyLinkedList<String> list = new GenericRecursiveDoublyLinkedList<String>();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtEnd);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtBeginning);
    }

    @Test
    void insertAtPositionFindsRecursivelyAndLinksFourPointers() {
        GenericRecursiveDoublyLinkedList<String> list = of("a", "b", "c");
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
        GenericRecursiveDoublyLinkedList<String> list = of("a", "b", "c");
        assertTrue(list.deleteByKey("b"));
        assertEquals(2, list.steps().maxDepth());
        assertEquals(2, list.steps().pointerChanges());
        assertFalse(list.deleteByKey("zz"));
        assertEquals("[a <-> c]", list.toString());
        assertConsistent(list);
    }

    @Test
    void searchCountAndGetRecurse() {
        GenericRecursiveDoublyLinkedList<String> list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(3, list.steps().maxDepth());
        assertEquals(-1, list.search("zz"));
        assertEquals(3, list.count());
        assertEquals("b", list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    void reverseSwapsPointersRecursively() {
        GenericRecursiveDoublyLinkedList<String> list = of("a", "b", "c", "d");
        list.reverse();
        assertEquals("[d <-> c <-> b <-> a]", list.toString());
        assertEquals(4, list.steps().maxDepth());
        assertConsistent(list);
    }

    @Test
    void aMillionNodesOverflowTheCallStack() {
        GenericRecursiveDoublyLinkedList<String> list = new GenericRecursiveDoublyLinkedList<String>();
        for (int i = 0; i < 1_000_000; i++) {
            list.insertAtEnd("x");
        }
        assertThrows(StackOverflowError.class, list::count);
    }

    @Test
    void recordsAreFoundRecursivelyWithEquals() {
        GenericRecursiveDoublyLinkedList<Photo> album = GenericRecursiveDoublyLinkedListDemo.album();
        assertEquals(2, album.search(new Photo("dog", 610)));
        assertEquals(3, album.steps().maxDepth());
        assertTrue(album.deleteByKey(new Photo("mountain", 1200)));
        assertEquals(new Photo("forest", 930), album.tail().data());
    }
}
