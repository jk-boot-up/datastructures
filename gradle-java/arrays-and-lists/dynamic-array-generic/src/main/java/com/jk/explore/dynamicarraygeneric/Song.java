package com.jk.explore.dynamicarraygeneric;

/**
 * One song in the playlist: a type of our own, to show that the generic dynamic array holds any
 * type. A record's {@code equals} compares its fields, so two songs with the same title and length
 * are equal even when they are different objects.
 */
public record Song(String title, int seconds) {

    @Override
    public String toString() {
        return title + " (" + seconds / 60 + ":" + (seconds % 60 < 10 ? "0" : "") + seconds % 60 + ")";
    }
}
