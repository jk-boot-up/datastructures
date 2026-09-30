package com.jk.explore.doublylinkedlistgeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the generic doubly linked list must have, and the steps each operation takes. */
class GenericDoublyLinkedListTest {

    private static GenericDoublyLinkedList<String> of(String... values) {
        GenericDoublyLinkedList<String> list = new GenericDoublyLinkedList<String>();
        for (String v : values) {
            list.insertAtEnd(v);
        }
        list.steps().reset();
        return list;
    }

    /** Checks that every prev pointer mirrors the next pointer before it, and that tail is the last node. */
    private static void assertConsistent(GenericDoublyLinkedList<String> list) {
        Node<String> prev = null;
        for (Node<String> current = list.head(); current != null; current = current.next()) {
            assertSame(prev, current.prev());
            prev = current;
        }
        assertSame(prev, list.tail());
    }

    @Test
    void aNewListHasNullHeadAndTail() {
        GenericDoublyLinkedList<String> list = new GenericDoublyLinkedList<String>();
        assertTrue(list.isEmpty());
        assertNull(list.head());
        assertNull(list.tail());
        assertEquals(0, list.count());
        assertEquals("head -> null <- tail", list.displayForward());
    }

    @Test
    void displayRunsBothWays() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c");
        assertEquals("head -> a <-> b <-> c <- tail", list.displayForward());
        assertEquals("tail -> c <-> b <-> a <- head", list.displayBackward());
        assertConsistent(list);
    }

    @Test
    void insertAtBothEndsIsConstantTime() {
        GenericDoublyLinkedList<String> list = of("b");
        list.insertAtBeginning("a");
        list.insertAtEnd("c");
        assertEquals(0, list.steps().steps());
        assertEquals(6, list.steps().pointerChanges());
        assertEquals("[a <-> b <-> c]", list.toString());
        assertConsistent(list);
    }

    @Test
    void insertAtPositionSetsFourPointers() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c");
        list.insertAtPosition(2, "x");
        assertEquals("[a <-> b <-> x <-> c]", list.toString());
        assertEquals(4, list.steps().pointerChanges());
        assertEquals(1, list.steps().steps());
        list.insertAtPosition(0, "y");
        list.insertAtPosition(5, "z");
        assertEquals("[y <-> a <-> b <-> x <-> c <-> z]", list.toString());
        assertConsistent(list);
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(9, "w"));
    }

    @Test
    void forgettingThePrevPointerBreaksTheBackwardWalk() {
        GenericDoublyLinkedList<String> list = of("beach", "cake", "dog", "forest", "mountain");
        list.insertAfterForgettingPrev(list.head(), "garden");
        assertEquals(6, list.count());
        assertEquals(5, list.countBackward());
    }

    @Test
    void deleteAtBothEndsIsConstantTime() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c");
        assertEquals("a", list.deleteAtBeginning());
        assertEquals("c", list.deleteAtEnd());
        assertEquals(0, list.steps().steps());
        assertEquals("[b]", list.toString());
        assertConsistent(list);
        assertEquals("b", list.deleteAtEnd());
        assertNull(list.head());
        assertNull(list.tail());
    }

    @Test
    void deletingFromAnEmptyListIsAnUnderflow() {
        GenericDoublyLinkedList<String> list = new GenericDoublyLinkedList<String>();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtEnd);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtBeginning);
        assertThrows(IllegalStateException.class, () -> list.deleteAtPosition(0));
    }

    @Test
    void deleteAtPositionAndByKeyUnlinkTheNode() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c", "d");
        assertEquals("b", list.deleteAtPosition(1));
        assertTrue(list.deleteByKey("d"));
        assertFalse(list.deleteByKey("zz"));
        assertEquals("[a <-> c]", list.toString());
        assertConsistent(list);
    }

    @Test
    void deleteNodeNeedsNoSearch() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c");
        Node<String> b = list.head().next();
        list.steps().reset();
        list.deleteNode(b);
        assertEquals(0, list.steps().steps());
        assertEquals(2, list.steps().pointerChanges());
        assertEquals("[a <-> c]", list.toString());
        assertConsistent(list);
    }

    @Test
    void searchAndGet() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(3, list.steps().compares());
        assertEquals(-1, list.search("zz"));
        assertEquals("b", list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void reverseSwapsPrevAndNextEverywhere() {
        GenericDoublyLinkedList<String> list = of("a", "b", "c");
        list.reverse();
        assertEquals("[c <-> b <-> a]", list.toString());
        assertEquals("tail -> a <-> b <-> c <- head", list.displayBackward());
        assertEquals(8, list.steps().pointerChanges());
        assertConsistent(list);
    }

    @Test
    void recordsAreMatchedWithEquals() {
        GenericDoublyLinkedList<Photo> album = GenericDoublyLinkedListDemo.album();
        assertEquals(2, album.search(new Photo("dog", 610)));
        assertEquals(-1, album.search(new Photo("dog", 611)));
        assertTrue(album.deleteByKey(new Photo("beach", 820)));
        assertEquals(new Photo("cake", 540), album.head().data());
        assertNull(album.head().prev());
    }

    @Test
    void integersUseTheSameCode() {
        GenericDoublyLinkedList<Integer> list = new GenericDoublyLinkedList<>();
        list.insertAtEnd(1);
        list.insertAtEnd(2);
        list.insertAtBeginning(0);
        assertEquals("tail -> 2 <-> 1 <-> 0 <- head", list.displayBackward());
        assertEquals(2, list.deleteAtEnd());
    }
}
