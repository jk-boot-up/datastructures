package com.jk.explore.doublylinkedlistgeneric;

/**
 * One photo in the viewer: a type of our own, to show that the generic list holds any type. A
 * record's {@code equals} compares its fields, so two photos with the same name and size are equal
 * even when they are different objects.
 */
public record Photo(String name, int kilobytes) {

    @Override
    public String toString() {
        return name + " " + kilobytes + "KB";
    }
}
