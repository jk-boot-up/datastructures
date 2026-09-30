package com.jk.explore.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ArrayStackTest {

    @Test
    void aNewStackIsEmpty() {
        ArrayStack<String> s = new ArrayStack<>(3);
        assertTrue(s.isEmpty());
        assertFalse(s.isFull());
        assertEquals("[]", s.toString());
    }

    @Test
    void theLastValueInIsTheFirstOut() {
        ArrayStack<String> s = new ArrayStack<>(3);
        s.push("a");
        s.push("b");
        s.push("c");
        assertEquals("c", s.pop());
        assertEquals("b", s.pop());
        assertEquals("a", s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    void peekLooksWithoutRemoving() {
        ArrayStack<String> s = new ArrayStack<>(3);
        s.push("a");
        s.push("b");
        assertEquals("b", s.peek());
        assertEquals(2, s.size());
    }

    @Test
    void everyOperationIsOneStep() {
        ArrayStack<String> s = new ArrayStack<>(3);
        s.push("a");
        s.peek();
        s.pop();
        assertEquals(3, s.steps().steps());
    }

    @Test
    void popOnAnEmptyStackIsRefused() {
        ArrayStack<String> s = new ArrayStack<>(2);
        IllegalStateException e = assertThrows(IllegalStateException.class, s::pop);
        assertEquals("stack is empty", e.getMessage());
        assertThrows(IllegalStateException.class, s::peek);
    }

    @Test
    void pushOnAFullStackIsRefused() {
        ArrayStack<String> s = new ArrayStack<>(2);
        s.push("a");
        s.push("b");
        assertTrue(s.isFull());
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> s.push("c"));
        assertEquals("stack is full: 2 of 2", e.getMessage());
    }

    @Test
    void aZeroCapacityIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayStack<String>(0));
    }

    @Test
    void printingShowsTheTop() {
        ArrayStack<String> s = new ArrayStack<>(3);
        s.push("a");
        s.push("b");
        assertEquals("[a, b <- top]", s.toString());
    }

    @Test
    void bracketsAreCheckedLastOpenedFirstClosed() {
        assertTrue(Brackets.balanced("({[]})"));
        assertTrue(Brackets.balanced("a(b)c[d]{e}"));
        assertTrue(Brackets.balanced(""));
        assertFalse(Brackets.balanced("([)]"));
        assertFalse(Brackets.balanced("(("));
        assertFalse(Brackets.balanced("())"));
        assertFalse(Brackets.balanced(")("));
    }
}
