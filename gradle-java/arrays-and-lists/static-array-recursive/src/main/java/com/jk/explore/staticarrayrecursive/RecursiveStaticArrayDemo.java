package com.jk.explore.staticarrayrecursive;

/**
 * Tells the story of the recursive static array in five acts, printing the real counts and the real
 * depth of every recursion.
 *
 * <p>The worked example is the same week of daily temperatures as the non-recursive project, in an
 * array of capacity 10.
 */
public final class RecursiveStaticArrayDemo {

    static final int[] WEEK = {21, 23, 19, 25, 24, 22, 20};
    static final int CAPACITY = 10;

    private RecursiveStaticArrayDemo() {
    }

    public static void main(String[] args) {
        System.out.print(run());
    }

    /** Prints how sum(i) unfolds: each call waits for the one below it, down to the base case. */
    static void traceSum(int[] a, int i, String indent, Lines out) {
        if (i == a.length) {
            out.add(indent + "sum(" + i + ") = 0   <- base case: no elements left");
            return;
        }
        out.add(indent + "sum(" + i + ") = " + a[i] + " + sum(" + (i + 1) + ")");
        traceSum(a, i + 1, indent + "  ", out);
    }

    /** Every line the demo prints, in order, each ending in a newline, so the tests can check them. */
    public static String run() {
        Lines out = new Lines();

        out.add("ONE. Thinking recursively.");
        out.add("  the sum of the week = the first element + the sum of the rest");
        traceSum(WEEK, 0, "  ", out);
        RecursiveStaticArray a = RecursiveStaticArray.of(CAPACITY, WEEK);
        a.steps().reset();
        out.add("  sum() = " + a.sum() + ", after " + a.steps().maxDepth() + " calls waiting on the stack at once");

        out.add("");
        out.add("TWO. The call stack.");
        a.steps().reset();
        out.add("  traverse: " + a.traverse());
        out.add("  call-stack depth " + a.steps().maxDepth() + ": one frame per element, each waiting for the rest");
        a.steps().reset();
        int max = a.findMax();
        out.add("  findMax = index " + max + " (" + a.get(max) + " C): depth " + a.steps().maxDepth()
                + ", " + a.steps().compares() + " comparisons on the way back up");

        out.add("");
        out.add("THREE. Searching recursively.");
        a.steps().reset();
        int at = a.linearSearch(24);
        out.add("  linearSearch(24) = index " + at + ": " + a.steps().compares() + " comparisons, depth "
                + a.steps().maxDepth());
        RecursiveStaticArray sorted = RecursiveStaticArray.of(CAPACITY, 19, 20, 21, 23, 24, 25, 26);
        sorted.steps().reset();
        int found = sorted.binarySearch(24);
        out.add("  sorted " + sorted + ": binarySearch(24) = index " + found + ", "
                + sorted.steps().compares() + " comparisons, depth " + sorted.steps().maxDepth());

        out.add("");
        out.add("FOUR. Insertion, deletion and reversal, recursively.");
        a.steps().reset();
        a.insertAt(2, 18);
        out.add("  insertAt(2, 18): " + a.steps().shifts() + " shifts, depth " + a.steps().maxDepth() + "; " + a);
        a.steps().reset();
        a.deleteAt(0);
        out.add("  deleteAt(0): " + a.steps().shifts() + " shifts, depth " + a.steps().maxDepth() + "; " + a);
        a.steps().reset();
        a.reverse();
        out.add("  reverse: " + a.steps().swaps() + " swaps, depth " + a.steps().maxDepth() + "; " + a);

        out.add("");
        out.add("FIVE. The limit of recursion.");
        int million = 1_000_000;
        int[] big = new int[million];
        for (int i = 0; i < million; i++) {
            big[i] = i * 2;
        }
        RecursiveStaticArray bigR = RecursiveStaticArray.of(million, big);
        bigR.steps().reset();
        bigR.binarySearch(1_333_332);
        out.add("  binarySearch on 1,000,000: " + bigR.steps().compares() + " comparisons, depth "
                + bigR.steps().maxDepth() + ": halving keeps the stack small");
        bigR.steps().reset();
        try {
            bigR.linearSearch(-1);
            out.add("  linearSearch on 1,000,000: finished");
        } catch (StackOverflowError e) {
            out.add("  linearSearch on 1,000,000: StackOverflowError, one frame per element");
        }
        out.add("  the same loop in the static-array project needs O(1) extra space and finishes");
        out.add("  Java does not remove tail calls, so even tail-recursive code keeps every frame");
        return out.text();
    }
}
