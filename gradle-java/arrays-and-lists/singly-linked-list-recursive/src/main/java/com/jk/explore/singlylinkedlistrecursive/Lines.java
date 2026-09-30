package com.jk.explore.singlylinkedlistrecursive;

/**
 * The demo's printed lines, kept in one {@code StringBuilder} so the tests can check them.
 *
 * <p>Deliberately not a Java collection: the course builds its data structures by hand, so the
 * demos do not lean on ready-made ones either.
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
