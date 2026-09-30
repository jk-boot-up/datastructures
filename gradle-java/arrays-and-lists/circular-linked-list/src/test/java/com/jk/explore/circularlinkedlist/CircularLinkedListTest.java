package com.jk.explore.circularlinkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the circular linked list must have, and the steps each operation takes. */
class CircularLinkedListTest {

    private static CircularLinkedList of(String... values) {
        CircularLinkedList list = new CircularLinkedList();
        for (String v : values) {
            list.insertAtEnd(v);
        }
        list.steps().reset();
        return list;
    }

    @Test
    void anEmptyListHasANullLast() {
        CircularLinkedList list = new CircularLinkedList();
        assertTrue(list.isEmpty());
        assertNull(list.last());
        assertNull(list.first());
        assertEquals(0, list.count());
        assertEquals("(empty)", list.display());
        assertEquals(-1, list.search("x"));
    }

    @Test
    void theLastNodePointsBackToTheFirst() {
        CircularLinkedList list = of("a", "b", "c");
        assertSame(list.first(), list.last().next());
        assertEquals("a -> b -> c -> (back to a)", list.display());
        assertEquals(3, list.count());
    }

    @Test
    void aListOfOneNodePointsToItself() {
        CircularLinkedList list = of("z");
        assertSame(list.last(), list.last().next());
    }

    @Test
    void bothEndsAreConstantTimeFromOnePointer() {
        CircularLinkedList list = of("b");
        list.insertAtBeginning("a");
        list.insertAtEnd("c");
        assertEquals(0, list.steps().steps());
        assertEquals("a -> b -> c -> (back to a)", list.toString());
        assertEquals("c", list.last().data());
    }

    @Test
    void insertAtPositionWalksToTheNodeBefore() {
        CircularLinkedList list = of("a", "b", "c");
        list.insertAtPosition(2, "x");
        assertEquals("a -> b -> x -> c -> (back to a)", list.toString());
        assertEquals(1, list.steps().steps());
        list.insertAtPosition(4, "y");
        assertEquals("y", list.last().data());
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(9, "w"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAtPosition(-1, "w"));
    }

    @Test
    void deleteAtBeginningIsOnePointer() {
        CircularLinkedList list = of("a", "b");
        assertEquals("a", list.deleteAtBeginning());
        assertEquals(1, list.steps().pointerChanges());
        assertEquals("b", list.deleteAtBeginning());
        assertTrue(list.isEmpty());
    }

    @Test
    void deleteAtEndWalksRoundToTheNodeBeforeLast() {
        CircularLinkedList list = of("a", "b", "c", "d");
        assertEquals("d", list.deleteAtEnd());
        assertEquals(2, list.steps().steps());
        assertEquals("a -> b -> c -> (back to a)", list.toString());
        CircularLinkedList one = of("z");
        assertEquals("z", one.deleteAtEnd());
        assertTrue(one.isEmpty());
    }

    @Test
    void deletingFromAnEmptyListIsAnUnderflow() {
        CircularLinkedList list = new CircularLinkedList();
        IllegalStateException e = assertThrows(IllegalStateException.class, list::deleteAtBeginning);
        assertEquals("underflow: the list is empty", e.getMessage());
        assertThrows(IllegalStateException.class, list::deleteAtEnd);
    }

    @Test
    void deleteByKeyHandlesTheFirstTheLastTheOnlyAndAMiss() {
        CircularLinkedList list = of("a", "b", "c");
        assertTrue(list.deleteByKey("c"));
        assertEquals("b", list.last().data());
        assertTrue(list.deleteByKey("a"));
        assertEquals("b -> (back to b)", list.toString());
        assertFalse(list.deleteByKey("zz"));
        assertTrue(list.deleteByKey("b"));
        assertTrue(list.isEmpty());
    }

    @Test
    void searchGoesRoundOnce() {
        CircularLinkedList list = of("a", "b", "c");
        assertEquals(2, list.search("c"));
        assertEquals(3, list.steps().compares());
        assertEquals(-1, list.search("zz"));
    }

    @Test
    void turnsGoRoundWithoutASpecialCase() {
        assertEquals("a, b, a, b, a", of("a", "b").turns(5));
    }

    @Test
    void aLoopWaitingForNullNeverStops() {
        assertEquals(50, of("a", "b").walkUntilNull(50));
    }

    @Test
    void theCountingOutGameLeavesOneWinner() {
        CircularLinkedList game = of("Ann", "Ben", "Cat", "Dan", "Eve");
        assertEquals("out: Cat, Ann, Eve, Ben; winner Dan", game.countOut(3));
        assertEquals(1, game.count());
    }
}
