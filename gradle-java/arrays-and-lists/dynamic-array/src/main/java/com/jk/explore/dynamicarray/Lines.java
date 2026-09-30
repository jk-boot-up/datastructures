package com.jk.explore.dynamicarray;

/**
 * The demo's printed lines, kept in one {@code StringBuilder} so the tests can check them.
 *
 * <p>Deliberately not an {@code ArrayList}: this project is about building a growable list
 * yourself, so the demo does not lean on the one Java already has.
 */
final class Lines {

    private final StringBuilder text = new StringBuilder();

    void add(String line) {
        text.append(line).append('\n');
    }

    /** Every line, each ending in a newline. */
    String text() {
        return text.toString();
    }
}
