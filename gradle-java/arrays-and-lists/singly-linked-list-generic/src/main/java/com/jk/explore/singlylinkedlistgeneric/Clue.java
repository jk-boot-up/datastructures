package com.jk.explore.singlylinkedlistgeneric;

/**
 * One clue of the treasure hunt: a type of our own, to show that the generic list holds any type.
 * A record's {@code equals} compares its fields, so two clues with the same place and hint are
 * equal even when they are different objects.
 */
public record Clue(String place, int metres) {

    @Override
    public String toString() {
        return place + " (" + metres + " m)";
    }
}
