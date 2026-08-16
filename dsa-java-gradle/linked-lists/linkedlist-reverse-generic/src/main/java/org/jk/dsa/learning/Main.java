package org.jk.dsa.learning;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Console demo for {@link SinglyLinkedList}.
 *
 * <p>Run it with:</p>
 * <pre>
 *   ./gradlew run          (macOS / Linux)
 *   gradlew.bat run        (Windows)
 * </pre>
 *
 * <p>Every section prints what it is about to do, so you can read the output
 * side by side with the source file. If you want to watch it happen one line at
 * a time, put a breakpoint on the first line of {@link #main(String[])} and
 * debug it -- see docs/README.md, section "Debugging".</p>
 */
public final class Main {

    /** Utility class: never instantiated. */
    private Main() {
    }

    /**
     * Entry point. Runs five short demos in order.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        demoStrings();
        demoIntegers();
        demoCustomType();
        demoReverseTrace();
        demoOtherOperations();
    }

    /** Demo 1: the list holding {@code String} values. */
    private static void demoStrings() {
        banner("1. Reversing a list of Strings");

        SinglyLinkedList<String> letters = SinglyLinkedList.of("A", "B", "C", "D");
        System.out.println("before reverse : " + letters);

        letters.reverse();                    // O(n) time, O(1) space
        System.out.println("after  reverse : " + letters);
        System.out.println("first = " + letters.first() + ", last = " + letters.last());
    }

    /** Demo 2: the SAME class holding {@code Integer} values -- that is generics. */
    private static void demoIntegers() {
        banner("2. The same class, now holding Integers");

        SinglyLinkedList<Integer> numbers = SinglyLinkedList.of(10, 20, 30, 40, 50);
        System.out.println("before reverse : " + numbers);

        numbers.reverseRecursive();           // O(n) time, O(n) stack space
        System.out.println("after  reverse : " + numbers + "   (recursive version)");

        // No casting anywhere: get(0) already IS an Integer.
        int head = numbers.get(0);
        System.out.println("numbers.get(0) + 5 = " + (head + 5));
    }

    /** Demo 3: any type at all works, including your own. */
    private static void demoCustomType() {
        banner("3. A list of your own type (a Lombok bean)");

        SinglyLinkedList<Student> students = new SinglyLinkedList<>();
        students.addLast(new Student("Asha", 91));
        students.addLast(new Student("Ravi", 78));
        students.addLast(new Student("Meera", 85));

        System.out.println("before reverse : " + students);
        students.reverse();
        System.out.println("after  reverse : " + students);

        // reversedCopy() leaves the original alone: O(n) time AND O(n) space.
        SinglyLinkedList<Student> copy = students.reversedCopy();
        System.out.println("reversedCopy   : " + copy);
        System.out.println("original still : " + students);

        // getName() and setMarks() were written by Lombok, not by us.
        Student top = students.first();
        System.out.println("top student    : " + top.getName() + " (Lombok getter)");
        top.setMarks(top.getMarks() + 5);
        System.out.println("after +5 marks : " + students + " (Lombok setter)");
    }

    /**
     * Demo 4: the three-pointer reversal printed one step at a time.
     *
     * <p>This mirrors, line for line, the loop inside
     * {@link SinglyLinkedList#reverse()}. It is written out here with raw
     * {@link Node} objects only so the demo can print the cursors after every
     * single step -- the real implementation does not print anything.</p>
     */
    private static void demoReverseTrace() {
        banner("4. Step-by-step trace of the three-pointer reversal");

        Node<String> a = new Node<>("A");
        Node<String> b = new Node<>("B");
        Node<String> c = new Node<>("C");
        a.next = b;
        b.next = c;

        Node<String> previous = null;
        Node<String> current = a;
        int step = 0;

        System.out.printf("step %d  previous=%-4s current=%-4s reversed=%s%n",
                step, describe(previous), describe(current), chain(previous));

        while (current != null) {
            Node<String> nextHop = current.next; // save the rest of the list
            current.next = previous;             // flip the arrow
            previous = current;                  // grow the reversed part
            current = nextHop;                   // walk forward
            step++;

            System.out.printf("step %d  previous=%-4s current=%-4s reversed=%s%n",
                    step, describe(previous), describe(current), chain(previous));
        }

        System.out.println("done    new head = " + describe(previous)
                + ", new tail = " + describe(a));
    }

    /** Demo 5: the other list operations, with their costs. */
    private static void demoOtherOperations() {
        banner("5. The other operations");

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

    /** Renders a node reference as its value, or "null" when there is none. */
    private static String describe(Node<String> node) {
        return node == null ? "null" : node.value;
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
     * A tiny domain type used by demo 3 to prove the list really is generic.
     *
     * <p>This one is written with <b>Lombok</b>. The three annotations below
     * replace roughly forty lines you would otherwise type by hand:</p>
     * <ul>
     *   <li>{@code @Getter} / {@code @Setter} -- one getter and one setter per
     *       field ({@code getName()}, {@code setMarks(int)}, ...).</li>
     *   <li>{@code @AllArgsConstructor} -- the
     *       {@code Student(String, int)} constructor.</li>
     * </ul>
     *
     * <p>Run {@code ./gradlew showGenerated} to print the real, generated
     * method list. If your IDE underlines {@code getName()} in red while
     * Gradle builds happily, you are missing the Lombok IDE plugin -- see the
     * troubleshooting table in README.md.</p>
     *
     * <p>Compare it with the {@code record Point} in the test file: a record is
     * Java's own built-in answer to the same boilerplate, but it is immutable.
     * Lombok is the tool of choice when you need a <em>mutable</em> bean, as
     * here where {@code setMarks} is used.</p>
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
