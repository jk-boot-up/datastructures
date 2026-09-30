package com.jk.explore.singlylinkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the singly linked list must have, and the steps each operation takes. */
class SinglyLinkedListTest {

    private static SinglyLinkedList of(String... values) {
        SinglyLinkedList list = new SinglyLinkedList();
        for (String v : values) {
            list.insertAtEnd(v);
        }
        list.steps().reset();
        return list;
    }

    @Test
    void aNewListIsEmptyWithANullHead() {
        SinglyLinkedList list = new SinglyLinkedList();
        assertTrue(list.isEmpty());
        assertNull(list.head());
        assertEquals(0, list.count());
        assertEquals("head -> null", list.display());
    }

    @Test
    void displayAndCountTraverseEveryNode() {
        SinglyLinkedList list = of("a", "b", "c");
        assertEquals("head -> a -> b -> c -> null", list.display());
        assertEquals(3, list.steps().steps());
        list.steps().reset();
        assertEquals(3, list.count());
        assertEquals(3, list.steps().steps());
    }

    @Test
    void insertAtBeginningChangesTwoPointersWhateverTheLength() {
        SinglyLinkedList list = of("a", "b", "c", "d");
        list.insertAtBeginning("x");
        assertEquals("head -> x -> a -> b -> c -> d -> null", list.display());
        assertEquals(2, list.steps().pointerChanges());
        assertEquals("x", list.head().data());
    }

    @Test
    void insertAtEndWalksToTheLastNode() {
        SinglyLinkedList list = of("a", "b", "c");
        list.insertAtEnd("d");
        assertEquals(2, list.steps().steps());
        assertEquals(1, list.steps().pointerChanges());
        assertEquals("[a -> b -> c -> d -> null]", list.toString());
    }

    @Test
    void insertAtPositionLinksWithoutMovingData() {
        SinglyLinkedList list = of("a", "b", "c");
        list.insertAtPosition(2, "x");
        assertEquals("[a -> b -> x -> c -> null]", list.toString());
        assertEquals(1, list.steps().steps());
        assertEquals(2, list.steps().pointerChanges());
        list.insertAtPosition(0, "y");
        list.insertAtPosition(5, "z");
        assertEquals("[y -> a -> b -> x -> c -> z -> null]", list.toString());
    }

    @Test
    void insertAtAMissingPositionIsRefused() {
        SinglyLinkedList list = of("a");
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(3, "x"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(-1, "x"));
    }

    @Test
    void theWrongPointerOrderLosesTheRestOfTheList() {
        SinglyLinkedList list = SinglyLinkedListDemo.hunt();
        list.insertAtPositionWrongOrder(2, "the mill");
        SinglyLinkedList.Reach r = list.reach(100);
        assertTrue(r.selfLoop());
        assertEquals("the mill", r.loopData());
        assertEquals(3, r.reachable());
    }

    @Test
    void deleteAtBeginningMovesTheHead() {
        SinglyLinkedList list = of("a", "b");
        assertEquals("a", list.deleteAtBeginning());
        assertEquals(1, list.steps().pointerChanges());
        assertEquals("b", list.deleteAtBeginning());
        assertTrue(list.isEmpty());
    }

    @Test
    void deleteAtEndWalksToTheSecondToLastNode() {
        SinglyLinkedList list = of("a", "b", "c", "d");
        assertEquals("d", list.deleteAtEnd());
        assertEquals(2, list.steps().steps());
        assertEquals("[a -> b -> c -> null]", list.toString());
        SinglyLinkedList one = of("x");
        assertEquals("x", one.deleteAtEnd());
        assertTrue(one.isEmpty());
    }

    @Test
    void deleteAtPositionPointsPastTheNode() {
        SinglyLinkedList list = of("a", "b", "c");
        assertEquals("b", list.deleteAtPosition(1));
        assertEquals("[a -> c -> null]", list.toString());
        assertEquals("a", list.deleteAtPosition(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAtPosition(1));
    }

    @Test
    void deletingFromAnEmptyListIsAnUnderflow() {
        SinglyLinkedList list = new SinglyLinkedList();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtBeginning);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtEnd);
        assertThrows(IllegalStateException.class, () -> list.deleteAtPosition(0));
    }

    @Test
    void deleteByKeyHandlesTheHeadTheMiddleAndAMiss() {
        SinglyLinkedList list = of("a", "b", "c");
        assertTrue(list.deleteByKey("b"));
        assertEquals("[a -> c -> null]", list.toString());
        assertTrue(list.deleteByKey("a"));
        assertEquals("[c -> null]", list.toString());
        assertFalse(list.deleteByKey("zz"));
        assertFalse(new SinglyLinkedList().deleteByKey("x"));
    }

    @Test
    void searchComparesUntilTheMatch() {
        SinglyLinkedList list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(3, list.steps().compares());
        assertEquals(-1, list.search("zz"));
    }

    @Test
    void getFollowsPosNextPointers() {
        SinglyLinkedList list = of("a", "b", "c");
        assertEquals("c", list.get(2));
        assertEquals(2, list.steps().steps());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void reverseTurnsEveryPointerAround() {
        SinglyLinkedList list = of("a", "b", "c");
        list.reverse();
        assertEquals("[c -> b -> a -> null]", list.toString());
        assertEquals(4, list.steps().pointerChanges());
        SinglyLinkedList empty = new SinglyLinkedList();
        empty.reverse();
        assertTrue(empty.isEmpty());
    }
}
