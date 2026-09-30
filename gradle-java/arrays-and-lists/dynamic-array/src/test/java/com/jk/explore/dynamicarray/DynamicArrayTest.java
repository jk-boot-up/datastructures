package com.jk.explore.dynamicarray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the dynamic array must have, and the steps each operation takes. */
class DynamicArrayTest {

    private static DynamicArray of(String... values) {
        DynamicArray a = new DynamicArray();
        for (String v : values) {
            a.append(v);
        }
        return a;
    }

    @Test
    void aNewArrayIsEmptyWithFourSparePlaces() {
        DynamicArray a = new DynamicArray();
        assertTrue(a.isEmpty());
        assertEquals(0, a.size());
        assertEquals(4, a.capacity());
    }

    @Test
    void appendingWhileThereIsRoomIsOneWriteAndNoCopies() {
        DynamicArray a = of("a", "b", "c");
        assertEquals(3, a.steps().writes());
        assertEquals(0, a.steps().copies());
        assertEquals(0, a.resizes());
    }

    @Test
    void appendingToAFullArrayDoublesTheCapacityAndCopiesEveryElement() {
        DynamicArray a = of("a", "b", "c", "d");
        a.steps().reset();
        a.append("e");
        assertEquals(8, a.capacity());
        assertEquals(4, a.steps().copies());
        assertArrayEquals(new String[] {"a", "b", "c", "d", "e"}, a.toArray());
    }

    @Test
    void nineAppendsResizeTwiceAndCopyTwelveElements() {
        DynamicArray a = of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        assertEquals(2, a.resizes());
        assertEquals(12, a.steps().copies());
        assertEquals(16, a.capacity());
    }

    @Test
    void doublingKeepsAThousandAppendsToAboutOneCopyEach() {
        assertEquals(1_020, DynamicArrayDemo.copiesToAdd(1000, true));
    }

    @Test
    void growingByOnePlaceCopiesAlmostHalfAMillionElements() {
        assertEquals(499_494, DynamicArrayDemo.copiesToAdd(1000, false));
    }

    @Test
    void traversalReadsEveryElementOnce() {
        DynamicArray a = of("a", "b", "c");
        a.steps().reset();
        assertEquals("[a, b, c]", a.traverse());
        assertEquals(3, a.steps().reads());
    }

    @Test
    void accessAndUpdateAreOneStepEach() {
        DynamicArray a = of("a", "b");
        a.steps().reset();
        assertEquals("b", a.get(1));
        a.update(1, "z");
        assertEquals(2, a.steps().total());
        assertEquals("z", a.get(1));
    }

    @Test
    void sparePlacesAreNotPartOfTheArray() {
        DynamicArray a = of("a", "b");
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> a.get(2));
        assertEquals("Index 2 out of bounds for length 2", e.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.update(2, "x"));
    }

    @Test
    void insertingAtTheFrontShiftsEveryElementRight() {
        DynamicArray a = of("a", "b", "c");
        a.steps().reset();
        a.insertAt(0, "x");
        assertArrayEquals(new String[] {"x", "a", "b", "c"}, a.toArray());
        assertEquals(3, a.steps().shifts());
    }

    @Test
    void insertingAtTheEndShiftsNothing() {
        DynamicArray a = of("a", "b");
        a.steps().reset();
        a.insertAt(2, "c");
        assertEquals(0, a.steps().shifts());
        assertArrayEquals(new String[] {"a", "b", "c"}, a.toArray());
    }

    @Test
    void insertingIntoAFullArrayGrowsItFirst() {
        DynamicArray a = of("a", "b", "c", "d");
        a.insertAt(1, "x");
        assertEquals(8, a.capacity());
        assertArrayEquals(new String[] {"a", "x", "b", "c", "d"}, a.toArray());
    }

    @Test
    void insertingOutsideTheElementsIsRefused() {
        DynamicArray a = of("a");
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(2, "x"));
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(-1, "x"));
    }

    @Test
    void deletingShiftsLaterElementsLeftAndReturnsTheDeletedOne() {
        DynamicArray a = of("a", "b", "c", "d");
        a.steps().reset();
        assertEquals("b", a.deleteAt(1));
        assertArrayEquals(new String[] {"a", "c", "d"}, a.toArray());
        assertEquals(2, a.steps().shifts());
        assertEquals(4, a.capacity(), "three of four in use: no shrink");
    }

    @Test
    void deletingAtTheEndShiftsNothing() {
        DynamicArray a = of("a", "b", "c");
        a.steps().reset();
        assertEquals("c", a.deleteAtEnd());
        assertEquals(0, a.steps().shifts());
        assertArrayEquals(new String[] {"a", "b"}, a.toArray());
    }

    @Test
    void theCapacityHalvesWhenOnlyAQuarterIsInUse() {
        DynamicArray a = of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        assertEquals(16, a.capacity());
        for (int i = 0; i < 4; i++) {
            a.deleteAtEnd();
        }
        assertEquals(16, a.capacity(), "5 of 16 is more than a quarter");
        a.deleteAtEnd();
        assertEquals(8, a.capacity(), "4 of 16 is a quarter: halve");
        assertArrayEquals(new String[] {"1", "2", "3", "4"}, a.toArray());
    }

    @Test
    void deletingFromAnEmptyArrayIsAnUnderflow() {
        DynamicArray a = new DynamicArray();
        IllegalStateException e = assertThrows(IllegalStateException.class, a::deleteAtEnd);
        assertEquals("underflow: the array is empty", e.getMessage());
        assertThrows(IllegalStateException.class, () -> a.deleteAt(0));
    }

    @Test
    void linearSearchComparesElementsInOrder() {
        DynamicArray a = of("a", "b", "c");
        a.steps().reset();
        assertEquals(2, a.linearSearch("c"));
        assertEquals(3, a.steps().compares());
        assertEquals(-1, a.linearSearch("zz"));
    }

    @Test
    void nullElementsAreRefused() {
        DynamicArray a = new DynamicArray();
        assertThrows(IllegalArgumentException.class, () -> a.append(null));
        assertThrows(IllegalArgumentException.class, () -> a.insertAt(0, null));
    }

    @Test
    void shrinkToFitGivesBackTheSparePlaces() {
        DynamicArray a = of("1", "2", "3", "4", "5");
        a.steps().reset();
        a.shrinkToFit();
        assertEquals(5, a.capacity());
        assertEquals(5, a.steps().copies());
    }

    @Test
    void aNegativeCapacityIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray(-1, true));
    }

    @Test
    void aZeroCapacityStillGrows() {
        DynamicArray a = new DynamicArray(0, true);
        a.append("a");
        a.append("b");
        assertEquals(2, a.size());
        assertEquals(2, a.capacity());
    }

    @Test
    void printingShowsOnlyTheElementsInUse() {
        assertEquals("[a, b]", of("a", "b").toString());
        assertEquals("[]", new DynamicArray().toString());
    }
}
