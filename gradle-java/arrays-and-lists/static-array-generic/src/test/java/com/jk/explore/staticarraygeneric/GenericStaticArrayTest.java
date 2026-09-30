package com.jk.explore.staticarraygeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the generic static array must have, for more than one element type. */
class GenericStaticArrayTest {

    private static GenericStaticArray<Integer> week() {
        return GenericStaticArray.of(10, 21, 23, 19, 25, 24, 22, 20);
    }

    @Test
    void aNewArrayIsEmptyWithItsCapacity() {
        GenericStaticArray<String> a = new GenericStaticArray<>(5);
        assertTrue(a.isEmpty());
        assertFalse(a.isFull());
        assertEquals(0, a.size());
        assertEquals(5, a.capacity());
    }

    @Test
    void theSameClassHoldsIntegersStringsAndRecords() {
        assertEquals("[21, 23, 19, 25, 24, 22, 20]", week().traverse());
        assertEquals("[a, b]", GenericStaticArray.of(3, "a", "b").traverse());
        assertEquals("[Mon 21 C]", GenericStaticArray.of(1, new Reading("Mon", 21)).traverse());
    }

    @Test
    void traversalReadsEveryElementOnce() {
        GenericStaticArray<Integer> a = week();
        a.traverse();
        assertEquals(7, a.steps().reads());
    }

    @Test
    void accessAndUpdateAreOneStepEach() {
        GenericStaticArray<Integer> a = week();
        assertEquals(25, a.get(3));
        a.update(5, 26);
        assertEquals(26, a.get(5));
        assertEquals(2, a.steps().reads());
        assertEquals(1, a.steps().writes());
    }

    @Test
    void indexesOutsideTheElementsAreRefused() {
        GenericStaticArray<Integer> a = week();
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> a.get(7));
        assertEquals("Index 7 out of bounds for length 7", e.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.update(7, 1));
    }

    @Test
    void insertionShiftsFromTheEnd() {
        GenericStaticArray<Integer> a = week();
        a.insertAt(2, 18);
        assertEquals("[21, 23, 18, 19, 25, 24, 22, 20]", a.toString());
        assertEquals(5, a.steps().shifts());
    }

    @Test
    void insertionIntoAFullArrayIsAnOverflow() {
        GenericStaticArray<String> a = GenericStaticArray.of(2, "a", "b");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> a.insertAt(0, "c"));
        assertEquals("overflow: the array is full (2 of 2)", e.getMessage());
    }

    @Test
    void insertionOutsideZeroToNIsRefused() {
        GenericStaticArray<Integer> a = week();
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(8, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(-1, 1));
    }

    @Test
    void deletionShiftsLeftAndReturnsTheElement() {
        GenericStaticArray<Integer> a = week();
        assertEquals(21, a.deleteAt(0));
        assertEquals("[23, 19, 25, 24, 22, 20]", a.toString());
        assertEquals(6, a.steps().shifts());
    }

    @Test
    void deletionFromAnEmptyArrayIsAnUnderflow() {
        GenericStaticArray<Integer> a = new GenericStaticArray<>(3);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> a.deleteAt(0));
        assertEquals("underflow: the array is empty", e.getMessage());
    }

    @Test
    void linearSearchUsesEqualsNotReferences() {
        GenericStaticArray<String> a = GenericStaticArray.of(3, "Mon", "Tue", "Wed");
        assertEquals(2, a.linearSearch(new String("Wed")));
        assertEquals(3, a.steps().compares());
        assertEquals(-1, a.linearSearch("Sun"));
    }

    @Test
    void binarySearchUsesCompareTo() {
        GenericStaticArray<Integer> a = GenericStaticArray.of(10, 19, 20, 21, 23, 24, 25, 26);
        assertEquals(4, a.binarySearch(24));
        assertEquals(3, a.steps().compares());
        assertEquals(-1, a.binarySearch(22));
        GenericStaticArray<String> s = GenericStaticArray.of(7, "Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed");
        assertEquals(4, s.binarySearch("Thu"));
    }

    @Test
    void binarySearchOnAMillionElementsTakesAboutTwentyComparisons() {
        Integer[] values = new Integer[1_000_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = i * 2;
        }
        GenericStaticArray<Integer> a = GenericStaticArray.of(values.length, values);
        assertEquals(666_666, a.binarySearch(1_333_332));
        assertEquals(20, a.steps().compares());
    }

    @Test
    void findMaxFollowsTheElementTypesOwnOrder() {
        assertEquals(3, week().findMax());
        GenericStaticArray<Reading> r = GenericStaticArrayDemo.readings();
        assertEquals(new Reading("Thu", 25), r.get(r.findMax()));
        assertEquals(6, r.steps().compares());
        assertEquals(2, GenericStaticArray.of(7, "Mon", "Tue", "Wed", "Thu").findMax());
        assertEquals(-1, new GenericStaticArray<Integer>(3).findMax());
    }

    @Test
    void reverseSwapsPairsInward() {
        GenericStaticArray<Integer> a = week();
        a.reverse();
        assertEquals("[20, 22, 24, 25, 19, 23, 21]", a.toString());
        assertEquals(3, a.steps().swaps());
    }

    @Test
    void copyingCopiesReferencesNotObjects() {
        GenericStaticArray<Reading> a = GenericStaticArrayDemo.readings();
        GenericStaticArray<Reading> b = a.copyWithCapacity(31);
        assertEquals(31, b.capacity());
        assertEquals(7, a.steps().copies());
        assertTrue(a.sameObjectAt(0, b));
        assertSame(a.get(6), b.get(6));
    }

    @Test
    void nullElementsAreRefused() {
        GenericStaticArray<String> a = new GenericStaticArray<>(3);
        assertThrows(IllegalArgumentException.class, () -> a.insertAt(0, null));
        a.insertAt(0, "a");
        assertThrows(IllegalArgumentException.class, () -> a.update(0, null));
    }

    @Test
    void aNegativeCapacityIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> new GenericStaticArray<String>(-1));
    }
}
