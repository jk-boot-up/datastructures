package com.jk.explore.stack;

/**
 * Checks that brackets are balanced, using a stack: every opening bracket is pushed, and every
 * closing bracket must match the one on top, which is popped. The last one opened must be the first
 * one closed: exactly last in, first out.
 */
public final class Brackets {

    private Brackets() {
    }

    /** True if every bracket in {@code text} is closed, by the right kind, in the right order. */
    public static boolean balanced(String text) {
        ArrayStack<Character> open = new ArrayStack<>(text.length() + 1);
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                open.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (open.isEmpty()) {
                    return false;                   // a closing bracket with nothing open
                }
                char top = open.pop();
                if (!pair(top, ch)) {
                    return false;                   // closed by the wrong kind
                }
            }
        }
        return open.isEmpty();                      // anything still open was never closed
    }

    private static boolean pair(char open, char close) {
        return (open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}');
    }
}
