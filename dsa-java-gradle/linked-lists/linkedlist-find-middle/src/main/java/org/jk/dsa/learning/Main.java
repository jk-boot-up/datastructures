package org.jk.dsa.learning;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Console demo for {@link SinglyLinkedList#findMiddle()}.
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
 * README.md, section 10.</p>
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
        demoOddAndEven();
        demoAllThreeAgree();
        demoCustomType();
        demoRace();
        demoEdgeCases();
        demoOtherOperations();
    }

    /** Demo 1: the star method, on lists of odd and of even length. */
    private static void demoOddAndEven() {
        banner("1. The middle of an odd-length and an even-length list");

        SinglyLinkedList<String> odd = SinglyLinkedList.of("A", "B", "C", "D", "E");
        System.out.println("odd  " + odd + "   findMiddle() -> " + odd.findMiddle());

        SinglyLinkedList<String> even = SinglyLinkedList.of("A", "B", "C", "D");
        System.out.println("even " + even + "   findMiddle() -> " + even.findMiddle()
                + "   (the SECOND of the two middle nodes)");
    }

    /** Demo 2: the three implementations agree, for every length from 1 to 8. */
    private static void demoAllThreeAgree() {
        banner("2. Three ways to find the middle -- all agree");

        System.out.printf("%-28s %-14s %-18s %-18s%n",
                "list", "findMiddle()", "findMiddleTwoPass()", "findMiddleFromSize()");
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int length = 1; length <= letters.length; length++) {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();
            for (int i = 0; i < length; i++) {
                list.addLast(letters[i]);
            }
            System.out.printf("%-28s %-14s %-18s %-18s%n",
                    list, list.findMiddle(), list.findMiddleTwoPass(), list.findMiddleFromSize());
        }
        System.out.println("One pass (~1.5n steps), two passes (~2n steps), and a cached-size");
        System.out.println("lookup (~0.5n steps) -- three different costs, the same answer.");
    }

    /** Demo 3: any type at all works, including your own. */
    private static void demoCustomType() {
        banner("3. A list of your own type (a Lombok bean)");

        SinglyLinkedList<Student> students = new SinglyLinkedList<>();
        students.addLast(new Student("Asha", 91));
        students.addLast(new Student("Ravi", 78));
        students.addLast(new Student("Meera", 85));
        students.addLast(new Student("Dev", 66));
        students.addLast(new Student("Zoe", 73));

        System.out.println("class            : " + students);
        Student middle = students.findMiddle();
        System.out.println("findMiddle()     : " + middle);

        // getName() was written by Lombok, not by us.
        System.out.println("middle student   : " + middle.getName() + " (Lombok getter)");
        middle.setMarks(middle.getMarks() + 5);
        System.out.println("after +5 marks   : " + students + " (Lombok setter)");
    }

    /**
     * Demo 4: the slow/fast race printed one step at a time.
     *
     * <p>This mirrors, line for line, the loop inside
     * {@link SinglyLinkedList#findMiddle()}. It is written out here with raw
     * {@link Node} objects only so the demo can print the two cursors after
     * every single step -- the real implementation does not print anything.</p>
     */
    private static void demoRace() {
        banner("4. Step-by-step trace of the slow/fast race on A B C D E");

        Node<String> a = new Node<>("A");
        Node<String> b = new Node<>("B");
        Node<String> c = new Node<>("C");
        Node<String> d = new Node<>("D");
        Node<String> e = new Node<>("E");
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        Node<String> slow = a;
        Node<String> fast = a;
        int step = 0;
        System.out.printf("step %d   slow=%-4s fast=%s%n", step, slow.value, fast.value);

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            step++;
            System.out.printf("step %d   slow=%-4s fast=%s%n",
                    step, slow.value, fast == null ? "null" : fast.value);
        }
        System.out.println("fast ran out of room for two more steps -- slow is the answer: "
                + slow.value);
    }

    /** Demo 5: the edge cases worth knowing. */
    private static void demoEdgeCases() {
        banner("5. The edge cases worth knowing");

        SinglyLinkedList<String> one = SinglyLinkedList.of("A");
        System.out.println("single node    : " + one + "   findMiddle() -> " + one.findMiddle());

        SinglyLinkedList<String> two = SinglyLinkedList.of("A", "B");
        System.out.println("two nodes      : " + two + "   findMiddle() -> " + two.findMiddle()
                + "   (the SECOND node, not the first)");

        SinglyLinkedList<String> empty = new SinglyLinkedList<>();
        try {
            empty.findMiddle();
        } catch (java.util.NoSuchElementException ex) {
            System.out.println("empty list     : findMiddle() throws " + ex.getClass().getSimpleName()
                    + " -- \"" + ex.getMessage() + "\"");
        }
    }

    /** Demo 6: the other list operations, with their costs. */
    private static void demoOtherOperations() {
        banner("6. The other operations");

        SinglyLinkedList<String> list = SinglyLinkedList.of("B", "C");
        System.out.println("start                 : " + list);

        list.addFirst("A");                       // O(1)
        System.out.println("addFirst(\"A\")         : " + list);

        list.addLast("E");                        // O(1)
        System.out.println("addLast(\"E\")          : " + list);

        list.insertAt(3, "D");                    // O(index)
        System.out.println("insertAt(3, \"D\")      : " + list);

        System.out.println("get(2)                : " + list.get(2));      // O(index)
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

    /**
     * A tiny domain type used by demo 3 to prove the list really is generic.
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
