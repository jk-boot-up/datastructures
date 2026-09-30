package com.jk.explore.staticarraygenericrecursive;

/**
 * One day's temperature reading: a type of our own, to show that the generic array holds any
 * type that can be put in order. Readings are ordered by temperature.
 */
public record Reading(String day, int celsius) implements Comparable<Reading> {

    @Override
    public int compareTo(Reading other) {
        return Integer.compare(celsius, other.celsius);
    }

    @Override
    public String toString() {
        return day + " " + celsius + " C";
    }
}
