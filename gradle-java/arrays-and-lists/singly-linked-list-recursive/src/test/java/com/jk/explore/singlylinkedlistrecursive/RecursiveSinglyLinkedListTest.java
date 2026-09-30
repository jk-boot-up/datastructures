package com.jk.explore.singlylinkedlistrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the recursive singly linked list must have, and how deep each recursion goes. */
class RecursiveSinglyLinkedListTest {

    private static RecursiveSinglyLinkedList of(String... values) {
        RecursiveSinglyLinkedList list = new RecursiveSinglyLinkedList();
        for (int i = values.length - 1; i >= 0; i--) {
            list.insertAtBeginning(values[i]);
        }
        list.steps().reset();
        return list;
    }

    @Test
    void anEmptyListIsTheBaseCaseOfEverything() {
        RecursiveSinglyLinkedList list = new RecursiveSinglyLinkedList();
        assertTrue(list.isEmpty());
        assertNull(list.head());
        assertEquals(0, list.count());
        assertEquals("head -> null", list.display());
        assertEquals(-1, list.search("x"));
        assertEquals(0, list.steps().maxDepth());
    }

    @Test
    void countAndDisplayRecurseOncePerNode() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        assertEquals(3, list.count());
        assertEquals(3, list.steps().maxDepth());
        assertEquals("head -> a -> b -> c -> null", list.display());
    }

    @Test
    void displayReverseWorksOnTheWayBack() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        assertEquals("c <- b <- a <- (head)", list.displayReverse());
        assertEquals("[a -> b -> c -> null]", list.toString());
    }

    @Test
    void insertAtBeginningIsNotRecursive() {
        RecursiveSinglyLinkedList list = of("a", "b");
        list.insertAtBeginning("x");
        assertEquals("[x -> a -> b -> null]", list.toString());
        assertEquals(0, list.steps().maxDepth());
        assertEquals(2, list.steps().pointerChanges());
    }

    @Test
    void insertAtEndRecursesToTheEmptyListAtTheEnd() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        list.insertAtEnd("d");
        assertEquals("[a -> b -> c -> d -> null]", list.toString());
        assertEquals(3, list.steps().maxDepth());
        RecursiveSinglyLinkedList empty = new RecursiveSinglyLinkedList();
        empty.insertAtEnd("x");
        assertEquals("[x -> null]", empty.toString());
    }

    @Test
    void insertAtPositionRecursesPosDeep() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        list.insertAtPosition(2, "x");
        assertEquals("[a -> b -> x -> c -> null]", list.toString());
        assertEquals(2, list.steps().maxDepth());
        list.insertAtPosition(0, "y");
        list.insertAtPosition(5, "z");
        assertEquals("[y -> a -> b -> x -> c -> z -> null]", list.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(9, "w"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(-1, "w"));
    }

    @Test
    void deleteAtBeginningAndAtEnd() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        assertEquals("a", list.deleteAtBeginning());
        assertEquals("c", list.deleteAtEnd());
        assertEquals(1, list.steps().maxDepth());
        assertEquals("b", list.deleteAtEnd());
        assertTrue(list.isEmpty());
    }

    @Test
    void deletingFromAnEmptyListIsAnUnderflow() {
        RecursiveSinglyLinkedList list = new RecursiveSinglyLinkedList();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtBeginning);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtEnd);
    }

    @Test
    void deleteByKeyReturnsTheRestInPlaceOfTheMatch() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        assertTrue(list.deleteByKey("b"));
        assertEquals("[a -> c -> null]", list.toString());
        assertEquals(2, list.steps().maxDepth());
        assertTrue(list.deleteByKey("a"));
        assertEquals("[c -> null]", list.toString());
        assertFalse(list.deleteByKey("zz"));
    }

    @Test
    void searchAndGetStopAtTheirBaseCases() {
        RecursiveSinglyLinkedList list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(3, list.steps().compares());
        assertEquals(-1, list.search("zz"));
        assertEquals("c", list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void reverseHangsEachNodeOnTheEndOfTheReversedRest() {
        RecursiveSinglyLinkedList list = of("a", "b", "c", "d");
        list.reverse();
        assertEquals("[d -> c -> b -> a -> null]", list.toString());
        assertEquals(3, list.steps().maxDepth());
        RecursiveSinglyLinkedList one = of("x");
        one.reverse();
        assertEquals("[x -> null]", one.toString());
    }

    @Test
    void aMillionNodesOverflowTheCallStack() {
        RecursiveSinglyLinkedList list = new RecursiveSinglyLinkedList();
        for (int i = 0; i < 1_000_000; i++) {
            list.insertAtBeginning("x");
        }
        assertThrows(StackOverflowError.class, list::count);
    }
}
