package com.jk.explore.circularlinkedlistrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the recursive circular linked list must have, and how deep each recursion goes. */
class RecursiveCircularLinkedListTest {

    private static RecursiveCircularLinkedList of(String... values) {
        RecursiveCircularLinkedList list = new RecursiveCircularLinkedList();
        for (String v : values) {
            list.insertAtEnd(v);
        }
        list.steps().reset();
        return list;
    }

    @Test
    void anEmptyListNeedsNoRecursion() {
        RecursiveCircularLinkedList list = new RecursiveCircularLinkedList();
        assertTrue(list.isEmpty());
        assertEquals(0, list.count());
        assertEquals("(empty)", list.display());
        assertEquals(-1, list.search("x"));
        assertFalse(list.deleteByKey("x"));
    }

    @Test
    void aLapRecursesOncePerNodeAndStopsAtLast() {
        RecursiveCircularLinkedList list = of("a", "b", "c");
        assertEquals(3, list.count());
        assertEquals(3, list.steps().maxDepth());
        assertEquals("a -> b -> c -> (back to a)", list.display());
        assertSame(list.first(), list.last().next());
    }

    @Test
    void bothEndsNeedNoRecursion() {
        RecursiveCircularLinkedList list = of("b");
        list.insertAtBeginning("a");
        list.insertAtEnd("c");
        assertEquals("a", list.deleteAtBeginning());
        assertEquals(0, list.steps().maxDepth());
        assertEquals("b -> c -> (back to b)", list.toString());
    }

    @Test
    void insertAtPositionFindsTheNodeBeforeRecursively() {
        RecursiveCircularLinkedList list = of("a", "b", "c");
        list.insertAtPosition(2, "x");
        assertEquals("a -> b -> x -> c -> (back to a)", list.toString());
        assertEquals(1, list.steps().maxDepth());
        list.insertAtPosition(4, "y");
        assertEquals("y", list.last().data());
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(9, "w"));
    }

    @Test
    void deleteAtEndFindsTheNodeBeforeLastRecursively() {
        RecursiveCircularLinkedList list = of("a", "b", "c", "d");
        assertEquals("d", list.deleteAtEnd());
        assertEquals(2, list.steps().maxDepth());
        assertEquals("c", list.last().data());
        RecursiveCircularLinkedList one = of("z");
        assertEquals("z", one.deleteAtEnd());
        assertTrue(one.isEmpty());
    }

    @Test
    void deletingFromAnEmptyListIsAnUnderflow() {
        RecursiveCircularLinkedList list = new RecursiveCircularLinkedList();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtEnd);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtBeginning);
    }

    @Test
    void deleteByKeyHandlesFirstLastOnlyAndMiss() {
        RecursiveCircularLinkedList list = of("a", "b", "c");
        assertTrue(list.deleteByKey("c"));
        assertEquals("b", list.last().data());
        assertTrue(list.deleteByKey("a"));
        assertFalse(list.deleteByKey("zz"));
        assertTrue(list.deleteByKey("b"));
        assertTrue(list.isEmpty());
    }

    @Test
    void searchStopsAtTheEndOfOneLap() {
        RecursiveCircularLinkedList list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(-1, list.search("zz"));
        assertEquals(3, list.steps().maxDepth());
    }

    @Test
    void theRecursiveFormulaFindsTheCountingOutWinner() {
        RecursiveCircularLinkedList list = new RecursiveCircularLinkedList();
        assertEquals(3, list.winnerPosition(5, 3));
        assertEquals(30, list.winnerPosition(41, 3));
        assertEquals(2, list.winnerPosition(5, 2));
        assertEquals(0, list.winnerPosition(8, 2));
    }

    @Test
    void aMillionNodeLapOverflowsTheCallStack() {
        RecursiveCircularLinkedList list = new RecursiveCircularLinkedList();
        for (int i = 0; i < 1_000_000; i++) {
            list.insertAtEnd("x");
        }
        assertThrows(StackOverflowError.class, list::count);
    }
}
