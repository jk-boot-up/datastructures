package com.jk.explore.staticarray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the static array must have, and the space its loops use. */
class StaticArrayTest {

    StaticArray make(int capacity, int... values) {
        StaticArray a = new StaticArray(capacity);
        a.fill(values);
        return a;
    }

    @Test
    void traversalListsTheElementsInOrder() {
        assertEquals("[21, 23, 19]", make(5, 21, 23, 19).traverse());
        assertEquals("[]", make(5).traverse());
    }

    @Test
    void linearSearchFindsTheFirstMatch() {
        StaticArray a = make(8, 4, 9, 2, 9, 5);
        assertEquals(1, a.linearSearch(9));
        assertEquals(-1, a.linearSearch(7));
        assertEquals(-1, make(3).linearSearch(1));
    }

    @Test
    void linearSearchComparesUntilItFinds() {
        StaticArray a = make(8, 21, 23, 19, 25, 24);
        a.steps().reset();
        a.linearSearch(24);
        assertEquals(5, a.steps().compares());
    }

    @Test
    void binarySearchFindsEveryElementOfASortedArray() {
        StaticArray a = make(10, 3, 8, 12, 17, 21, 26, 30);
        int[] keys = {3, 8, 12, 17, 21, 26, 30};
        for (int i = 0; i < keys.length; i++) {
            assertEquals(i, a.binarySearch(keys[i]));
        }
        assertEquals(-1, a.binarySearch(1));
        assertEquals(-1, a.binarySearch(20));
        assertEquals(-1, a.binarySearch(99));
        assertEquals(-1, make(3).binarySearch(5));
    }

    @Test
    void binarySearchNeedsAboutLog2OfNComparisons() {
        int[] values = new int[1024];
        for (int i = 0; i < values.length; i++) {
            values[i] = i;
        }
        StaticArray a = make(1024, values);
        a.steps().reset();
        a.binarySearch(1023);
        assertTrue(a.steps().compares() <= 11, "compares: " + a.steps().compares());
    }

    @Test
    void findMaxReturnsTheIndexOfTheLargest() {
        assertEquals(3, make(8, 21, 23, 19, 25, 24).findMax());
        assertEquals(0, make(3, 7).findMax());
        assertEquals(-1, make(3).findMax());
    }

    @Test
    void sumAddsEveryElement() {
        assertEquals(154, make(10, 21, 23, 19, 25, 24, 22, 20).sum());
        assertEquals(0, make(2).sum());
    }

    @Test
    void reverseSwapsTheEndsInward() {
        StaticArray odd = make(5, 1, 2, 3, 4, 5);
        odd.reverse();
        assertArrayEquals(new int[] {5, 4, 3, 2, 1}, odd.toArray());
        StaticArray even = make(4, 1, 2, 3, 4);
        even.reverse();
        assertArrayEquals(new int[] {4, 3, 2, 1}, even.toArray());
        StaticArray one = make(2, 9);
        one.reverse();
        assertArrayEquals(new int[] {9}, one.toArray());
    }

    @Test
    void accessAndUpdateAreOneStep() {
        StaticArray a = make(5, 21, 23, 19);
        a.steps().reset();
        assertEquals(23, a.get(1));
        a.update(2, 30);
        assertEquals(1, a.steps().reads());
        assertEquals(1, a.steps().writes());
        assertArrayEquals(new int[] {21, 23, 30}, a.toArray());
    }

    @Test
    void insertionShiftsLaterElementsRight() {
        StaticArray a = make(10, 21, 23, 19, 25, 24, 22, 20);
        a.steps().reset();
        a.insertAt(2, 18);
        assertArrayEquals(new int[] {21, 23, 18, 19, 25, 24, 22, 20}, a.toArray());
        assertEquals(5, a.steps().shifts());
    }

    @Test
    void insertionAtTheEndShiftsNothing() {
        StaticArray a = make(5, 1, 2);
        a.steps().reset();
        a.insertAt(2, 3);
        assertEquals(0, a.steps().shifts());
        assertArrayEquals(new int[] {1, 2, 3}, a.toArray());
    }

    @Test
    void insertionIntoAFullArrayIsAnOverflow() {
        StaticArray a = make(2, 1, 2);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> a.insertAt(0, 3));
        assertTrue(e.getMessage().startsWith("overflow"));
    }

    @Test
    void insertionOutsideZeroToNIsRefused() {
        StaticArray a = make(5, 1, 2);
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(3, 9));
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(-1, 9));
    }

    @Test
    void deletionShiftsLaterElementsLeft() {
        StaticArray a = make(5, 1, 2, 3, 4);
        a.steps().reset();
        assertEquals(2, a.deleteAt(1));
        assertArrayEquals(new int[] {1, 3, 4}, a.toArray());
        assertEquals(2, a.steps().shifts());
    }

    @Test
    void deletionFromAnEmptyArrayIsAnUnderflow() {
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> make(3).deleteAt(0));
        assertTrue(e.getMessage().startsWith("underflow"));
    }

    @Test
    void positionsOutsideTheElementsAreRefused() {
        StaticArray a = make(10, 1, 2, 3);
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> a.get(3));
        assertEquals("Index 3 out of bounds for length 3", e.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> a.update(-1, 0));
    }

    @Test
    void growingCopiesEveryElementIntoANewArray() {
        StaticArray a = make(3, 1, 2, 3);
        a.steps().reset();
        StaticArray b = a.copyWithCapacity(6);
        assertEquals(6, b.capacity());
        assertArrayEquals(new int[] {1, 2, 3}, b.toArray());
        assertEquals(3, a.steps().copies());
        assertEquals(3, a.capacity(), "the original keeps its capacity");
    }

    @Test
    void aNegativeCapacityIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> make(-1));
    }

    @Test
    void theIterativeSearchHandlesAMillionElements() {
        int[] values = new int[1_000_000];
        StaticArray a = make(values.length, values);
        assertEquals(-1, a.linearSearch(1));
    }
}
