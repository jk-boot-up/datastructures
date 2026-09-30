package com.jk.explore.dynamicarrayrecursive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the recursive dynamic array must have, and how deep each recursion goes. */
class RecursiveDynamicArrayTest {

    private static RecursiveDynamicArray of(String... values) {
        RecursiveDynamicArray a = new RecursiveDynamicArray();
        for (String v : values) {
            a.append(v);
        }
        return a;
    }

    @Test
    void aNewArrayIsEmptyWithFourSparePlaces() {
        RecursiveDynamicArray a = new RecursiveDynamicArray();
        assertTrue(a.isEmpty());
        assertEquals(4, a.capacity());
    }

    @Test
    void appendingWhileThereIsRoomDoesNotRecurse() {
        RecursiveDynamicArray a = of("a", "b", "c");
        assertEquals(0, a.steps().copies());
        assertEquals(0, a.steps().maxDepth());
    }

    @Test
    void aResizeCopiesRecursivelyOneFramePerElement() {
        RecursiveDynamicArray a = of("a", "b", "c", "d");
        a.steps().reset();
        a.append("e");
        assertEquals(8, a.capacity());
        assertEquals(4, a.steps().copies());
        assertEquals(4, a.steps().maxDepth());
        assertEquals("[a, b, c, d, e]", a.toString());
    }

    @Test
    void nineAppendsResizeTwiceAndCopyTwelve() {
        RecursiveDynamicArray a = of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        assertEquals(2, a.resizes());
        assertEquals(12, a.steps().copies());
        assertEquals(8, a.steps().maxDepth());
    }

    @Test
    void traversalRecursesOncePerElement() {
        RecursiveDynamicArray a = of("a", "b", "c");
        a.steps().reset();
        assertEquals("[a, b, c]", a.traverse());
        assertEquals(3, a.steps().maxDepth());
    }

    @Test
    void accessAndUpdateAreNotRecursive() {
        RecursiveDynamicArray a = of("a", "b");
        a.steps().reset();
        a.update(1, "z");
        assertEquals("z", a.get(1));
        assertEquals(0, a.steps().maxDepth());
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(2));
    }

    @Test
    void insertionShiftsRecursively() {
        RecursiveDynamicArray a = of("a", "b", "c");
        a.steps().reset();
        a.insertAt(0, "x");
        assertEquals("[x, a, b, c]", a.toString());
        assertEquals(3, a.steps().shifts());
        assertEquals(3, a.steps().maxDepth());
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(9, "y"));
    }

    @Test
    void insertingIntoAFullArrayResizesFirst() {
        RecursiveDynamicArray a = of("a", "b", "c", "d");
        a.insertAt(1, "x");
        assertEquals(8, a.capacity());
        assertEquals("[a, x, b, c, d]", a.toString());
    }

    @Test
    void deletionShiftsRecursivelyAndReturnsTheElement() {
        RecursiveDynamicArray a = of("a", "b", "c", "d");
        a.steps().reset();
        assertEquals("b", a.deleteAt(1));
        assertEquals("[a, c, d]", a.toString());
        assertEquals(2, a.steps().shifts());
        assertEquals(2, a.steps().maxDepth());
    }

    @Test
    void theCapacityHalvesWhenAQuarterFull() {
        RecursiveDynamicArray a = of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        for (int i = 0; i < 5; i++) {
            a.deleteAtEnd();
        }
        assertEquals(8, a.capacity());
        assertEquals("[1, 2, 3, 4]", a.toString());
    }

    @Test
    void deletingFromAnEmptyArrayIsAnUnderflow() {
        RecursiveDynamicArray a = new RecursiveDynamicArray();
        IllegalStateException e = assertThrows(IllegalStateException.class, a::deleteAtEnd);
        assertEquals("underflow: the array is empty", e.getMessage());
        assertThrows(IllegalStateException.class, () -> a.deleteAt(0));
    }

    @Test
    void linearSearchRecursesUntilTheMatch() {
        RecursiveDynamicArray a = of("a", "b", "c");
        a.steps().reset();
        assertEquals(2, a.linearSearch("c"));
        assertEquals(3, a.steps().maxDepth());
        assertEquals(-1, a.linearSearch("zz"));
    }

    @Test
    void shrinkToFitCopiesRecursively() {
        RecursiveDynamicArray a = of("1", "2", "3", "4", "5");
        a.steps().reset();
        a.shrinkToFit();
        assertEquals(5, a.capacity());
        assertEquals(5, a.steps().maxDepth());
    }

    @Test
    void nullElementsAreRefused() {
        RecursiveDynamicArray a = new RecursiveDynamicArray();
        assertThrows(IllegalArgumentException.class, () -> a.append(null));
    }

    @Test
    void aMillionAppendsOverflowTheCallStackInsideAResize() {
        RecursiveDynamicArray a = new RecursiveDynamicArray();
        assertThrows(StackOverflowError.class, () -> {
            for (int i = 0; i < 1_000_000; i++) {
                a.append("x");
            }
        });
    }
}
