package com.jk.explore.stack;

/**
 * Tells the story of the stack in five acts, printing the real step counts.
 *
 * <p>The worked examples are a text editor's undo and a web browser's back button.
 */
public final class StackDemo {

    static final String[] EDITS = {"type H", "type i", "bold", "type !"};

    private StackDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Undo from the wrong end.");
        String[] history = {EDITS[0], EDITS[1], EDITS[2], EDITS[3]};
        out.add("  edits, oldest first: " + String.join(", ", history));
        out.add("  undo takes the first edit ever made: \"" + history[0] + "\", not the last");
        out.add("  undo must take back the most recent edit first");

        out.add("");
        out.add("TWO. A pile with one open end.");
        ArrayStack<String> undo = new ArrayStack<>(5);
        for (String e : EDITS) {
            undo.push(e);
        }
        out.add("  push 4 edits: " + undo);
        out.add("  " + undo.size() + " of " + undo.capacity() + " slots used; the top is slot " + (undo.size() - 1));

        out.add("");
        out.add("THREE. Undo, peek, and Back.");
        undo.steps().reset();
        String first = undo.pop();
        String second = undo.pop();
        out.add("  undo twice: \"" + first + "\", then \"" + second + "\"; " + undo.steps().steps() + " steps");
        undo.steps().reset();
        out.add("  peek: \"" + undo.peek() + "\" is next to undo, " + undo.steps().steps() + " step, nothing removed");
        ArrayStack<String> back = new ArrayStack<>(10);
        back.push("home");
        back.push("news");
        back.push("sport");
        String leaving = back.pop();
        out.add("  browser: home, news, sport; Back leaves \"" + leaving + "\" and shows \"" + back.peek() + "\"");
        ArrayStack<Integer> big = new ArrayStack<>(1_000_000);
        for (int i = 0; i < 1_000_000; i++) {
            big.push(i);
        }
        big.steps().reset();
        big.pop();
        out.add("  a stack of 1,000,000: one pop is " + big.steps().steps() + " step");

        out.add("");
        out.add("FOUR. Empty and full.");
        undo.pop();
        undo.pop();
        try {
            undo.pop();
            out.add("  undo on nothing works");
        } catch (IllegalStateException e) {
            out.add("  undo with nothing left: " + e.getMessage());
        }
        ArrayStack<String> small = new ArrayStack<>(5);
        for (int i = 1; i <= 5; i++) {
            small.push("edit " + i);
        }
        try {
            small.push("edit 6");
            out.add("  a sixth edit fits");
        } catch (IllegalStateException e) {
            out.add("  a sixth edit: " + e.getMessage());
        }

        out.add("");
        out.add("FIVE. Brackets, and the bill.");
        out.add("  \"({[]})\" balanced: " + Brackets.balanced("({[]})"));
        out.add("  \"([)]\" balanced: " + Brackets.balanced("([)]"));
        out.add("  \"((\" balanced: " + Brackets.balanced("(("));
        out.add("  the bill: only the top can be reached, and a fixed array can fill up");
        out.add("  already in Java: java.util.ArrayDeque (push, pop, peek); the old java.util.Stack is best avoided");
        return out.text();
    }
}
