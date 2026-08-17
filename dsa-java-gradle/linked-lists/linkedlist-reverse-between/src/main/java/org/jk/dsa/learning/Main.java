package org.jk.dsa.learning;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Console demo for {@link SinglyLinkedList#reverseBetween(int, int)}.
 *
 * <p>Run it with:</p>
 * <pre>
 *   ./gradlew run          (macOS / Linux)
 *   gradlew.bat run        (Windows)
 * </pre>
 *
 * <p>Every section prints what it is about to do, so you can read the output
 * side by side with the source file. To watch it happen one step at a time, put
 * a breakpoint on the first line of {@link #main(String[])} and debug it -- see
 * README.md, section "Debugging".</p>
 */
public final class Main {

    /** Utility class: never instantiated. */
    private Main() {
    }

    /**
     * Entry point. Runs six short demos in order.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        demoTheSlice();
        demoEverySlice();
        demoEdgeCases();
        demoIntegers();
        demoCustomType();
        demoSliceTrace();
        demoOtherOperations();
    }

    /** Demo 1: the headline example. */
    private static void demoTheSlice() {
        banner("1. Reversing just the middle of the list");

        SinglyLinkedList<String> letters = SinglyLinkedList.of("A", "B", "C", "D", "E");
        System.out.println("before                : " + letters);

        letters.reverseBetween(1, 3);          // O(to) time, O(1) space
        System.out.println("reverseBetween(1, 3)  : " + letters);
        System.out.println("A and E never moved. Only the slice B C D was flipped.");
    }

    /** Demo 2: what every possible slice does to the same list. */
    private static void demoEverySlice() {
        banner("2. Every slice of A B C D E");

        for (int from = 0; from < 5; from++) {
            for (int to = from; to < 5; to++) {
                SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D", "E");
                list.reverseBetween(from, to);
                System.out.printf("reverseBetween(%d, %d) -> %s%n", from, to, list);
            }
        }
    }

    /** Demo 3: the cases that break naive implementations. */
    private static void demoEdgeCases() {
        banner("3. The edge cases worth knowing");

        SinglyLinkedList<String> fromHead = SinglyLinkedList.of("A", "B", "C", "D");
        fromHead.reverseBetween(0, 2);
        System.out.println("slice starts at head  : " + fromHead
                + "   (head had to change: now " + fromHead.first() + ")");

        SinglyLinkedList<String> toTail = SinglyLinkedList.of("A", "B", "C", "D");
        toTail.reverseBetween(1, 3);
        toTail.addLast("Z");   // only works if `tail` was fixed up
        System.out.println("slice ends at tail    : " + toTail
                + "   (addLast still works, so tail is correct)");

        SinglyLinkedList<String> single = SinglyLinkedList.of("A", "B", "C");
        single.reverseBetween(1, 1);
        System.out.println("one-node slice        : " + single + "   (nothing to do)");

        SinglyLinkedList<String> whole = SinglyLinkedList.of("A", "B", "C", "D");
        whole.reverseBetween(0, whole.size() - 1);
        System.out.println("the whole list        : " + whole + "   (same as reverse())");
    }

    /** Demo 4: the SAME class holding Integers -- that is generics. */
    private static void demoIntegers() {
        banner("4. The same class, now holding Integers");

        SinglyLinkedList<Integer> numbers = SinglyLinkedList.of(10, 20, 30, 40, 50, 60);
        System.out.println("before                : " + numbers);

        numbers.reverseBetweenWithoutSentinel(2, 4);
        System.out.println("reverseBetween(2, 4)  : " + numbers + "   (sentinel-free version)");

        // No casting anywhere: get(0) already IS an Integer.
        int head = numbers.get(0);
        System.out.println("numbers.get(0) + 5    : " + (head + 5));
    }

    /** Demo 5: any type at all works, including your own. */
    private static void demoCustomType() {
        banner("5. A list of your own type (a Lombok bean)");

        SinglyLinkedList<Student> students = new SinglyLinkedList<>();
        students.addLast(new Student("Asha", 91));
        students.addLast(new Student("Ravi", 78));
        students.addLast(new Student("Meera", 85));
        students.addLast(new Student("Dev", 66));

        System.out.println("before                : " + students);
        students.reverseBetween(1, 2);
        System.out.println("reverseBetween(1, 2)  : " + students);

        // reversedBetweenCopy() leaves the original alone: O(n) time AND O(n) space.
        SinglyLinkedList<Student> copy = students.reversedBetweenCopy(0, 3);
        System.out.println("reversedBetweenCopy   : " + copy);
        System.out.println("original still        : " + students);

        // getName() and setMarks() were written by Lombok, not by us.
        Student top = students.first();
        System.out.println("first student         : " + top.getName() + " (Lombok getter)");
        top.setMarks(top.getMarks() + 5);
        System.out.println("after +5 marks        : " + students + " (Lombok setter)");
    }

    /**
     * Demo 6: the slice reversal printed one step at a time.
     *
     * <p>This mirrors the loop inside
     * {@link SinglyLinkedList#reverseBetween(int, int)}. It is written out here
     * with raw {@link Node} objects only so the demo can print the cursors after
     * every single step -- the real implementation prints nothing.</p>
     */
    private static void demoSliceTrace() {
        banner("6. Step-by-step trace of reverseBetween(1, 3) on A B C D E");

        Node<String> a = new Node<>("A");
        Node<String> b = new Node<>("B");
        Node<String> c = new Node<>("C");
        Node<String> d = new Node<>("D");
        Node<String> e = new Node<>("E");
        a.next = b; b.next = c; c.next = d; d.next = e;

        Node<String> sentinel = new Node<>(null, a);

        // 1. walk to the node before the slice
        Node<String> beforeSlice = sentinel;
        for (int i = 0; i < 1; i++) {
            beforeSlice = beforeSlice.next;
        }
        System.out.println("beforeSlice = " + describe(beforeSlice)
                + "   (the node that must point at the new first node)");

        // 2. remember the node that will end up last
        Node<String> sliceTail = beforeSlice.next;
        System.out.println("sliceTail   = " + describe(sliceTail)
                + "   (starts the slice, so it will END the slice)");
        System.out.println();

        // 3. the ordinary reversal loop, run a fixed number of times
        Node<String> previous = null;
        Node<String> current = sliceTail;
        for (int i = 0; i <= 3 - 1; i++) {
            Node<String> nextHop = current.next;
            current.next = previous;
            previous = current;
            current = nextHop;
            System.out.printf("flip %d   previous=%-4s current=%-4s reversed slice=%s%n",
                    i + 1, describe(previous), describe(current), chain(previous));
        }

        // 4. stitch
        System.out.println();
        beforeSlice.next = previous;
        sliceTail.next = current;
        System.out.println("stitch  beforeSlice(" + describe(beforeSlice) + ") -> "
                + describe(previous));
        System.out.println("stitch  sliceTail(" + describe(sliceTail) + ") -> "
                + describe(current));
        System.out.println("result  " + chain(sentinel.next));
    }

    /** Demo 7: the other list operations, with their costs. */
    private static void demoOtherOperations() {
        banner("7. The other operations");

        SinglyLinkedList<String> list = SinglyLinkedList.of("B", "C");
        System.out.println("start                 : " + list);

        list.addFirst("A");                       // O(1)
        System.out.println("addFirst(\"A\")         : " + list);

        list.addLast("E");                        // O(1)
        System.out.println("addLast(\"E\")          : " + list);

        list.insertAt(3, "D");                    // O(index)
        System.out.println("insertAt(3, \"D\")      : " + list);

        System.out.println("get(2)                : " + list.get(2));        // O(index)
        System.out.println("indexOf(\"D\")          : " + list.indexOf("D")); // O(n)
        System.out.println("contains(\"Z\")         : " + list.contains("Z"));

        System.out.println("removeFirst()         : " + list.removeFirst() + "  -> " + list);
        System.out.println("removeLast()          : " + list.removeLast() + "  -> " + list);
        System.out.println("removeAt(1)           : " + list.removeAt(1) + "  -> " + list);

        List<String> asJavaList = list.toList();  // O(n)
        System.out.println("toList()              : " + asJavaList);
        System.out.println("size()                : " + list.size());

        list.clear();                             // O(1)
        System.out.println("after clear()         : " + list + ", isEmpty=" + list.isEmpty());
    }

    // -----------------------------------------------------------------------
    // Small printing helpers
    // -----------------------------------------------------------------------

    /** Prints a section title so the console output stays readable. */
    private static void banner(String title) {
        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println(title);
        System.out.println("=".repeat(70));
    }

    /** Renders a node reference as its value, or "null" when there is none. */
    private static String describe(Node<String> node) {
        return node == null ? "null" : String.valueOf(node.value);
    }

    /** Renders a chain starting at {@code node} as {@code A -> B -> null}. */
    private static String chain(Node<String> node) {
        StringBuilder sb = new StringBuilder();
        for (Node<String> cursor = node; cursor != null; cursor = cursor.next) {
            sb.append(cursor.value).append(" -> ");
        }
        return sb.append("null").toString();
    }

    /**
     * A tiny domain type used by demo 5 to prove the list really is generic.
     *
     * <p>Written with <b>Lombok</b>: the three annotations below replace the
     * constructor, two getters and two setters. Run
     * {@code ./gradlew showGenerated} to print what Lombok actually wrote. If
     * your IDE underlines {@code getName()} in red while Gradle builds happily,
     * you are missing the Lombok IDE plugin -- see the troubleshooting table in
     * README.md.</p>
     */
    @Getter
    @Setter
    @AllArgsConstructor
    static class Student {

        private String name;
        private int marks;

        /** Hand-written on purpose: Lombok's version would print every field. */
        @Override
        public String toString() {
            return name + "(" + marks + ")";
        }
    }
}
