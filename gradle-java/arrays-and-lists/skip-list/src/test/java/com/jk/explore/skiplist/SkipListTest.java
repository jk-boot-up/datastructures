package com.jk.explore.skiplist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SkipListTest {

    @Test
    void anEmptyLineFindsNothing() {
        SkipList line = new SkipList(Coin.seeded(1));
        assertNull(line.find(5));
        assertEquals(0, line.size());
        assertEquals(1, line.levels());
    }

    @Test
    void stationsAreKeptInKilometreOrderWhateverTheInsertOrder() {
        SkipList line = new SkipList(Coin.seeded(7));
        line.insert(30, "C");
        line.insert(10, "A");
        line.insert(20, "B");
        assertTrue(line.describe().endsWith("level 1: A B C"));
        assertEquals("B", line.find(20));
    }

    @Test
    void theRegularLineHasFourLevels() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        assertEquals(4, line.levels());
        assertEquals(30, line.pointers());
        assertTrue(line.describe().startsWith("level 4: Hailey Pinner"));
    }

    @Test
    void theExpressFindsMardenInThreeHopsTheStoppingServiceInThirteen() {
        SkipList express = SkipListDemo.line(SkipListDemo.REGULAR);
        assertEquals("Marden", express.find(57));
        assertEquals(3, express.steps().steps());
        int[] ones = new int[16];
        for (int i = 0; i < ones.length; i++) {
            ones[i] = 1;
        }
        SkipList stopping = SkipListDemo.line(ones);
        assertEquals("Marden", stopping.find(57));
        assertEquals(13, stopping.steps().steps());
    }

    @Test
    void aMissingKilometreIsReportedAsNoStation() {
        assertNull(SkipListDemo.line(SkipListDemo.REGULAR).find(60));
    }

    @Test
    void insertingSetsTwoArrowsPerLevel() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        line.insertWithLevel(50, "Quarry", 3);
        assertEquals(6, line.steps().pointerChanges());
        assertEquals(3, line.heightOf(50));
        assertEquals("Quarry", line.find(50));
    }

    @Test
    void aTallerStationThanAnyAddsALevel() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        line.insertWithLevel(80, "Rye", 6);
        assertEquals(6, line.levels());
        assertEquals("Rye", line.find(80));
    }

    @Test
    void aDuplicateKilometreIsRefused() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        assertThrows(IllegalArgumentException.class, () -> line.insert(57, "Again"));
    }

    @Test
    void removingUnlinksTheStationFromEveryLevel() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        assertTrue(line.remove(35));
        assertEquals(4, line.steps().pointerChanges());
        assertNull(line.find(35));
        assertEquals(15, line.size());
        assertFalse(line.describe().contains("Hailey"));
    }

    @Test
    void removingAMissingStationChangesNothing() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        assertFalse(line.remove(60));
        assertEquals(16, line.size());
    }

    @Test
    void removingTheOnlyTallStationsLowersTheLine() {
        SkipList line = SkipListDemo.line(SkipListDemo.REGULAR);
        line.remove(35);
        line.remove(71);
        assertEquals(3, line.levels());
    }

    @Test
    void anAlwaysTailsCoinBuildsAPlainList() {
        SkipList line = new SkipList(Coin.alwaysTails());
        for (int i = 0; i < 20; i++) {
            line.insert(i, "s" + i);
        }
        assertEquals(1, line.levels());
        assertEquals(20, line.pointers());
    }

    @Test
    void theSameSeedTossesTheSameCoins() {
        Coin a = Coin.seeded(99);
        Coin b = Coin.seeded(99);
        for (int i = 0; i < 50; i++) {
            assertEquals(a.heads(), b.heads());
        }
    }

    @Test
    void aFairCoinComesUpHeadsAboutHalfTheTime() {
        Coin coin = Coin.seeded(2026);
        int heads = 0;
        for (int i = 0; i < 10_000; i++) {
            if (coin.heads()) {
                heads++;
            }
        }
        assertTrue(heads > 4_800 && heads < 5_200, "heads: " + heads);
    }

    @Test
    void aThousandStationsAreFoundInAboutTenHops() {
        assertEquals(97, SkipListDemo.averageHopsTimesTen(1000, Coin.seeded(2026)));
    }
}
