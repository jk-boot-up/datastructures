package com.jk.explore.staticarraygenericrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the generic recursive static array must have, and how deep each recursion goes. */
class GenericRecursiveStaticArrayTest {

    private static GenericRecursiveStaticArray<Integer> week() {
        return GenericRecursiveStaticArray.of(10, 21, 23, 19, 25, 24, 22, 20);
    }

    @Test
    void aNewArrayIsEmptyWithItsCapacity() {
        GenericRecursiveStaticArray<String> a = new GenericRecursiveStaticArray<>(4);
        assertTrue(a.isEmpty());
        assertEquals(4, a.capacity());
        assertEquals("[]", a.traverse());
    }

    @Test
    void traversalRecursesOnceAnElement() {
        GenericRecursiveStaticArray<Integer> a = week();
        assertEquals("[21, 23, 19, 25, 24, 22, 20]", a.traverse());
        assertEquals(7, a.steps().maxDepth());
        assertEquals(7, a.steps().reads());
    }

    @Test
    void accessAndUpdateAreNotRecursive() {
        GenericRecursiveStaticArray<Integer> a = week();
        assertEquals(25, a.get(3));
        a.update(3, 26);
        assertEquals(26, a.get(3));
        assertEquals(0, a.steps().maxDepth());
    }

    @Test
    void indexesOutsideTheElementsAreRefused() {
        GenericRecursiveStaticArray<Integer> a = week();
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(7));
        assertThrows(IndexOutOfBoundsException.class, () -> a.update(-1, 1));
    }

    @Test
    void linearSearchUsesEqualsAndStopsAtTheMatch() {
        GenericRecursiveStaticArray<String> a = GenericRecursiveStaticArray.of(7, "Mon", "Tue", "Wed", "Thu");
        assertEquals(3, a.linearSearch(new String("Thu")));
        assertEquals(4, a.steps().compares());
        assertEquals(4, a.steps().maxDepth());
        assertEquals(-1, a.linearSearch("Sun"));
    }

    @Test
    void binarySearchUsesCompareToAndRecursesLogNDeep() {
        GenericRecursiveStaticArray<String> a = GenericRecursiveStaticArray.of(7, "Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed");
        assertEquals(4, a.binarySearch("Thu"));
        assertEquals(3, a.steps().compares());
        assertEquals(3, a.steps().maxDepth());
        assertEquals(-1, a.binarySearch("Xyz"));
    }

    @Test
    void findMaxComparesOnTheWayBackUp() {
        GenericRecursiveStaticArray<Reading> r = GenericRecursiveStaticArrayDemo.readings();
        assertEquals(new Reading("Thu", 25), r.get(r.findMax()));
        assertEquals(6, r.steps().compares());
        assertEquals(6, r.steps().maxDepth());
        assertEquals(-1, new GenericRecursiveStaticArray<Integer>(2).findMax());
    }

    @Test
    void insertionShiftsRecursivelyFromTheEnd() {
        GenericRecursiveStaticArray<Integer> a = week();
        a.insertAt(2, 18);
        assertEquals("[21, 23, 18, 19, 25, 24, 22, 20]", a.toString());
        assertEquals(5, a.steps().shifts());
        assertEquals(5, a.steps().maxDepth());
    }

    @Test
    void insertionIntoAFullArrayIsAnOverflow() {
        GenericRecursiveStaticArray<String> a = GenericRecursiveStaticArray.of(1, "a");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> a.insertAt(0, "b"));
        assertEquals("overflow: the array is full (1 of 1)", e.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> week().insertAt(8, 1));
    }

    @Test
    void deletionShiftsRecursivelyAndReturnsTheElement() {
        GenericRecursiveStaticArray<Integer> a = week();
        assertEquals(21, a.deleteAt(0));
        assertEquals("[23, 19, 25, 24, 22, 20]", a.toString());
        assertEquals(6, a.steps().shifts());
        assertEquals(6, a.steps().maxDepth());
    }

    @Test
    void deletionFromAnEmptyArrayIsAnUnderflow() {
        GenericRecursiveStaticArray<Integer> a = new GenericRecursiveStaticArray<>(2);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> a.deleteAt(0));
        assertEquals("underflow: the array is empty", e.getMessage());
    }

    @Test
    void reverseRecursesHalfTheLength() {
        GenericRecursiveStaticArray<Integer> a = GenericRecursiveStaticArray.of(8, 1, 2, 3, 4, 5, 6, 7, 8);
        a.reverse();
        assertEquals("[8, 7, 6, 5, 4, 3, 2, 1]", a.toString());
        assertEquals(4, a.steps().swaps());
        assertEquals(4, a.steps().maxDepth());
    }

    @Test
    void nullElementsAndNegativeCapacityAreRefused() {
        GenericRecursiveStaticArray<String> a = new GenericRecursiveStaticArray<>(2);
        assertThrows(IllegalArgumentException.class, () -> a.insertAt(0, null));
        assertThrows(IllegalArgumentException.class, () -> new GenericRecursiveStaticArray<String>(-1));
    }

    @Test
    void binarySearchOnAMillionElementsIsTwentyDeep() {
        Integer[] values = new Integer[1_000_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = i * 2;
        }
        GenericRecursiveStaticArray<Integer> a = GenericRecursiveStaticArray.of(values.length, values);
        assertEquals(666_666, a.binarySearch(1_333_332));
        assertEquals(20, a.steps().maxDepth());
    }

    @Test
    void aMillionDeepLinearRecursionOverflowsTheCallStack() {
        Integer[] values = new Integer[1_000_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = i;
        }
        GenericRecursiveStaticArray<Integer> a = GenericRecursiveStaticArray.of(values.length, values);
        assertThrows(StackOverflowError.class, () -> a.linearSearch(-1));
    }
}
