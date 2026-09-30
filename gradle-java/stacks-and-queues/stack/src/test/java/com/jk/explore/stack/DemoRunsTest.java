package com.jk.explore.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = StackDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneUndoesTheWrongEdit() {
        prints("  undo takes the first edit ever made: \"type H\", not the last");
    }

    @Test
    void actTwoPushes() {
        prints("  push 4 edits: [type H, type i, bold, type ! <- top]");
        prints("  4 of 5 slots used; the top is slot 3");
    }

    @Test
    void actThreeUndoesAndGoesBack() {
        prints("  undo twice: \"type !\", then \"bold\"; 2 steps");
        prints("  peek: \"type i\" is next to undo, 1 step, nothing removed");
        prints("  browser: home, news, sport; Back leaves \"sport\" and shows \"news\"");
        prints("  a stack of 1,000,000: one pop is 1 step");
    }

    @Test
    void actFourHitsTheEdges() {
        prints("  undo with nothing left: stack is empty");
        prints("  a sixth edit: stack is full: 5 of 5");
    }

    @Test
    void actFiveChecksBrackets() {
        prints("  \"({[]})\" balanced: true");
        prints("  \"([)]\" balanced: false");
        prints("  \"((\" balanced: false");
    }
}
