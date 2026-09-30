package com.jk.explore.dynamicarraygeneric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Every behaviour the generic dynamic array must have, for more than one element type. */
class GenericDynamicArrayTest {

    private static GenericDynamicArray<String> of(String... values) {
        GenericDynamicArray<String> a = new GenericDynamicArray<>();
        for (String v : values) {
            a.append(v);
        }
        return a;
    }

    @Test
    void aNewArrayIsEmptyWithFourSparePlaces() {
        GenericDynamicArray<String> a = new GenericDynamicArray<>();
        assertTrue(a.isEmpty());
        assertEquals(0, a.size());
        assertEquals(4, a.capacity());
    }

    @Test
    void appendingWhileThereIsRoomIsOneWriteAndNoCopies() {
        GenericDynamicArray<String> a = of("a", "b", "c");
        assertEquals(3, a.steps().writes());
        assertEquals(0, a.steps().copies());
        assertEquals(0, a.resizes());
    }

    @Test
    void appendingToAFullArrayDoublesTheCapacityAndCopiesEveryElement() {
        GenericDynamicArray<String> a = of("a", "b", "c", "d");
        a.steps().reset();
        a.append("e");
        assertEquals(8, a.capacity());
        assertEquals(4, a.steps().copies());
        assertEquals("[" + String.join(", ", new String[] {"a", "b", "c", "d", "e"}) + "]", a.toString());
    }

    @Test
    void nineAppendsResizeTwiceAndCopyTwelveElements() {
        GenericDynamicArray<String> a = of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        assertEquals(2, a.resizes());
        assertEquals(12, a.steps().copies());
        assertEquals(16, a.capacity());
    }

    @Test
    void traversalReadsEveryElementOnce() {
        GenericDynamicArray<String> a = of("a", "b", "c");
        a.steps().reset();
        assertEquals("[a, b, c]", a.traverse());
        assertEquals(3, a.steps().reads());
    }

    @Test
    void accessAndUpdateAreOneStepEach() {
        GenericDynamicArray<String> a = of("a", "b");
        a.steps().reset();
        assertEquals("b", a.get(1));
        a.update(1, "z");
        assertEquals(2, a.steps().total());
        assertEquals("z", a.get(1));
    }

    @Test
    void sparePlacesAreNotPartOfTheArray() {
        GenericDynamicArray<String> a = of("a", "b");
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> a.get(2));
        assertEquals("Index 2 out of bounds for length 2", e.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.update(2, "x"));
    }

    @Test
    void insertingAtTheFrontShiftsEveryElementRight() {
        GenericDynamicArray<String> a = of("a", "b", "c");
        a.steps().reset();
        a.insertAt(0, "x");
        assertEquals("[" + String.join(", ", new String[] {"x", "a", "b", "c"}) + "]", a.toString());
        assertEquals(3, a.steps().shifts());
    }

    @Test
    void insertingAtTheEndShiftsNothing() {
        GenericDynamicArray<String> a = of("a", "b");
        a.steps().reset();
        a.insertAt(2, "c");
        assertEquals(0, a.steps().shifts());
        assertEquals("[" + String.join(", ", new String[] {"a", "b", "c"}) + "]", a.toString());
    }

    @Test
    void insertingIntoAFullArrayGrowsItFirst() {
        GenericDynamicArray<String> a = of("a", "b", "c", "d");
        a.insertAt(1, "x");
        assertEquals(8, a.capacity());
        assertEquals("[" + String.join(", ", new String[] {"a", "x", "b", "c", "d"}) + "]", a.toString());
    }

    @Test
    void insertingOutsideTheElementsIsRefused() {
        GenericDynamicArray<String> a = of("a");
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(2, "x"));
        assertThrows(IndexOutOfBoundsException.class, () -> a.insertAt(-1, "x"));
    }

    @Test
    void deletingShiftsLaterElementsLeftAndReturnsTheDeletedOne() {
        GenericDynamicArray<String> a = of("a", "b", "c", "d");
        a.steps().reset();
        assertEquals("b", a.deleteAt(1));
        assertEquals("[" + String.join(", ", new String[] {"a", "c", "d"}) + "]", a.toString());
        assertEquals(2, a.steps().shifts());
        assertEquals(4, a.capacity(), "three of four in use: no shrink");
    }

    @Test
    void deletingAtTheEndShiftsNothing() {
        GenericDynamicArray<String> a = of("a", "b", "c");
        a.steps().reset();
        assertEquals("c", a.deleteAtEnd());
        assertEquals(0, a.steps().shifts());
        assertEquals("[" + String.join(", ", new String[] {"a", "b"}) + "]", a.toString());
    }

    @Test
    void theCapacityHalvesWhenOnlyAQuarterIsInUse() {
        GenericDynamicArray<String> a = of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        assertEquals(16, a.capacity());
        for (int i = 0; i < 4; i++) {
            a.deleteAtEnd();
        }
        assertEquals(16, a.capacity(), "5 of 16 is more than a quarter");
        a.deleteAtEnd();
        assertEquals(8, a.capacity(), "4 of 16 is a quarter: halve");
        assertEquals("[" + String.join(", ", new String[] {"1", "2", "3", "4"}) + "]", a.toString());
    }

    @Test
    void deletingFromAnEmptyArrayIsAnUnderflow() {
        GenericDynamicArray<String> a = new GenericDynamicArray<>();
        IllegalStateException e = assertThrows(IllegalStateException.class, a::deleteAtEnd);
        assertEquals("underflow: the array is empty", e.getMessage());
        assertThrows(IllegalStateException.class, () -> a.deleteAt(0));
    }

    @Test
    void linearSearchComparesElementsInOrder() {
        GenericDynamicArray<String> a = of("a", "b", "c");
        a.steps().reset();
        assertEquals(2, a.linearSearch("c"));
        assertEquals(3, a.steps().compares());
        assertEquals(-1, a.linearSearch("zz"));
    }

    @Test
    void nullElementsAreRefused() {
        GenericDynamicArray<String> a = new GenericDynamicArray<>();
        assertThrows(IllegalArgumentException.class, () -> a.append(null));
        assertThrows(IllegalArgumentException.class, () -> a.insertAt(0, null));
    }

    @Test
    void shrinkToFitGivesBackTheSparePlaces() {
        GenericDynamicArray<String> a = of("1", "2", "3", "4", "5");
        a.steps().reset();
        a.shrinkToFit();
        assertEquals(5, a.capacity());
        assertEquals(5, a.steps().copies());
    }

    @Test
    void aNegativeCapacityIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> new GenericDynamicArray<String>(-1));
    }

    @Test
    void aZeroCapacityStillGrows() {
        GenericDynamicArray<String> a = new GenericDynamicArray<String>(0);
        a.append("a");
        a.append("b");
        assertEquals(2, a.size());
        assertEquals(2, a.capacity());
    }

    @Test
    void printingShowsOnlyTheElementsInUse() {
        assertEquals("[a, b]", of("a", "b").toString());
        assertEquals("[]", new GenericDynamicArray<String>().toString());
    }

    @Test
    void anyTypeCanBeStored() {
        GenericDynamicArray<Song> songs = new GenericDynamicArray<>();
        songs.append(new Song("Echoes", 201));
        GenericDynamicArray<Integer> counts = new GenericDynamicArray<>();
        counts.append(7);
        assertEquals("[Echoes (3:21)]", songs.traverse());
        assertEquals(7, counts.get(0));
    }

    @Test
    void searchUsesEqualsSoAnEqualRecordIsFound() {
        GenericDynamicArray<Song> songs = new GenericDynamicArray<>();
        Song original = new Song("Echoes", 201);
        songs.append(original);
        Song lookalike = new Song("Echoes", 201);
        assertEquals(0, songs.linearSearch(lookalike));
        assertTrue(songs.holdsSameObject(0, original));
        assertFalse(songs.holdsSameObject(0, lookalike));
    }

    @Test
    void aResizeCopiesReferencesNotObjects() {
        GenericDynamicArray<Song> songs = new GenericDynamicArray<>();
        for (Song s : GenericDynamicArrayDemo.SONGS) {
            songs.append(s);
        }
        for (int i = 0; i < GenericDynamicArrayDemo.SONGS.length; i++) {
            assertTrue(songs.holdsSameObject(i, GenericDynamicArrayDemo.SONGS[i]));
        }
    }
}
