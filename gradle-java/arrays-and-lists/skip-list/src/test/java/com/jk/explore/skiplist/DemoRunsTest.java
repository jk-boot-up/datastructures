package com.jk.explore.skiplist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** The numbers the demo prints, and that the README, video and animation quote, are checked here. */
class DemoRunsTest {

    private final String out = SkipListDemo.run();

    private void prints(String line) {
        assertTrue(out.contains(line + "\n"), "missing: " + line + "\n" + out);
    }

    @Test
    void theDemoTellsFiveActs() {
        assertEquals(5, out.lines().filter(l -> l.matches("^[A-Z]+\\. .*")).count());
    }

    @Test
    void actOneStopsEverywhere() {
        prints("  find Marden (km 57): 13 steps, one station at a time");
    }

    @Test
    void actTwoAddsExpressLanes() {
        prints("  level 4: Hailey Pinner");
        prints("  level 3: Denby Hailey Lydd Pinner");
        prints("  30 forward pointers for 16 stations");
    }

    @Test
    void actThreeRidesTheExpress() {
        prints("  find Marden (km 57): 3 steps: express to Hailey, then Lydd, then step on");
        prints("  find km 60: no station, 4 steps to be sure");
        prints("  add Quarry (km 50): the coin gives height 1, 2 pointers set");
        prints("  remove Hailey (height 4): 4 pointers changed, one per level");
    }

    @Test
    void actFourHasBadLuck() {
        prints("  every toss is tails: 1 level, no express at all");
        prints("  find Marden: 13 steps, the same as the stopping service");
    }

    @Test
    void actFivePresentsTheBill() {
        prints("  1,000 stations: 11 levels, finding a station takes 9.7 steps on average (the stopping service: 500.5)");
        prints("  2,031 forward pointers: about 2 per station instead of 1");
    }
}
