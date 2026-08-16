package com.jk.explore.dsa.linkedlists.single.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListTest {

    @Test
    void constructorInitializesSingleElementList() {
        LinkedList<Integer> list = new LinkedList<>(1);

        assertEquals(1, list.getSize());
        assertFalse(list.isEmpty());
        assertEquals("[1]", list.toString());
    }

    @Test
    void addAppendsToEnd() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.getSize());
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void addFirstPrependsToStart() {
        LinkedList<Integer> list = new LinkedList<>(2);
        list.add(3);
        list.addFirst(1);

        assertEquals(3, list.getSize());
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void addLastAppendsToEnd() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(3, list.getSize());
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void addAtIndexZeroBehavesLikeAddFirst() {
        LinkedList<Integer> list = new LinkedList<>(2);
        list.add(3);
        list.add(0, 1);

        assertEquals("[1, 2, 3]", list.toString());
        assertEquals(3, list.getSize());
    }

    @Test
    void addAtIndexEqualToSizeBehavesLikeAddLast() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(2, 3);

        assertEquals("[1, 2, 3]", list.toString());
        assertEquals(3, list.getSize());
    }

    @Test
    void addAtMiddleIndexInsertsBetweenNodes() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(4);
        list.add(2, 3);

        assertEquals("[1, 2, 3, 4]", list.toString());
        assertEquals(4, list.getSize());
    }

    @Test
    void addAtNegativeIndexThrows() {
        LinkedList<Integer> list = new LinkedList<>(1);

        assertThrows(RuntimeException.class, () -> list.add(-1, 99));
    }

    @Test
    void addAtIndexGreaterThanSizeThrows() {
        LinkedList<Integer> list = new LinkedList<>(1);

        assertThrows(RuntimeException.class, () -> list.add(5, 99));
    }

    @Test
    void reverseOnSingleElementListIsNoOp() {
        LinkedList<Integer> list = new LinkedList<>(42);
        list.reverse();

        assertEquals("[42]", list.toString());
        assertEquals(1, list.getSize());
    }

    @Test
    void reverseOnMultiElementListReversesOrder() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.reverse();

        assertEquals("[5, 4, 3, 2, 1]", list.toString());
        assertEquals(5, list.getSize());
    }

    @Test
    void reversingTwiceRestoresOriginalOrder() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.reverse();
        list.reverse();

        assertEquals("[1, 2, 3, 4]", list.toString());
    }

    @Test
    void addLastAfterReverseUsesUpdatedTail() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(3);

        list.reverse();
        list.addLast(99);

        assertEquals("[3, 2, 1, 99]", list.toString());
        assertEquals(4, list.getSize());
    }

    @Test
    void addFirstAfterReverseUsesUpdatedHead() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(3);

        list.reverse();
        list.addFirst(0);

        assertEquals("[0, 3, 2, 1]", list.toString());
        assertEquals(4, list.getSize());
    }

    @Test
    void addAtMiddleIndexAfterReverseInsertsCorrectly() {
        LinkedList<Integer> list = new LinkedList<>(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.reverse();
        list.add(2, 100);

        assertEquals("[4, 3, 100, 2, 1]", list.toString());
        assertEquals(5, list.getSize());
    }

    @Test
    void isEmptyReflectsSize() {
        LinkedList<Integer> list = new LinkedList<>(1);

        assertFalse(list.isEmpty());
        assertTrue(list.getSize() > 0);
    }
}