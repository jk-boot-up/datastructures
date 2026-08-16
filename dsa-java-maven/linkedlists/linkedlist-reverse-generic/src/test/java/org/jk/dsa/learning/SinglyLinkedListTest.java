package org.jk.dsa.learning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * JUnit 5 tests for {@link SinglyLinkedList}.
 *
 * <p>How to read a test: each method follows <b>arrange / act / assert</b>.
 * First we build a list, then we call the one method under test, then we check
 * the result. Run them all with {@code ./mvnw test}; run a single one with
 * {@code ./mvnw test -Dtest='SinglyLinkedListTest$Reverse#...'}.</p>
 */
@DisplayName("SinglyLinkedList")
class SinglyLinkedListTest {

    @Nested
    @DisplayName("reverse() -- iterative, O(n) time / O(1) space")
    class Reverse {

        @Test
        @DisplayName("reverses a three element list")
        void reversesAThreeElementList() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            list.reverse();

            assertEquals(List.of("C", "B", "A"), list.toList());
            assertEquals("[C -> B -> A]", list.toString());
        }

        @Test
        @DisplayName("keeps an empty list empty")
        void handlesEmptyList() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            list.reverse();

            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
            assertEquals("[]", list.toString());
        }

        @Test
        @DisplayName("leaves a single element list unchanged")
        void handlesSingleElementList() {
            SinglyLinkedList<Integer> list = SinglyLinkedList.of(42);

            list.reverse();

            assertEquals(List.of(42), list.toList());
            assertEquals(42, list.first());
            assertEquals(42, list.last());
        }

        @Test
        @DisplayName("swaps head and tail so addLast still appends correctly")
        void fixesHeadAndTailPointers() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            list.reverse();
            list.addLast("Z");   // only works if `tail` was updated by reverse()

            assertEquals(List.of("C", "B", "A", "Z"), list.toList());
            assertEquals("C", list.first());
            assertEquals("Z", list.last());
            assertEquals(4, list.size());
        }

        @Test
        @DisplayName("reversing twice returns the original order")
        void reversingTwiceIsIdentity() {
            SinglyLinkedList<Integer> list = SinglyLinkedList.of(1, 2, 3, 4, 5);

            list.reverse();
            list.reverse();

            assertEquals(List.of(1, 2, 3, 4, 5), list.toList());
        }

        @ParameterizedTest(name = "list of {0} elements reverses correctly")
        @ValueSource(ints = {0, 1, 2, 3, 10, 1000})
        void reversesListsOfManySizes(int n) {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            IntStream.range(0, n).forEach(list::addLast);

            list.reverse();

            List<Integer> expected = new ArrayList<>();
            for (int i = n - 1; i >= 0; i--) {
                expected.add(i);
            }
            assertEquals(expected, list.toList());
            assertEquals(n, list.size());
        }

        @Test
        @DisplayName("handles null elements")
        void handlesNullElements() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", null, "C");

            list.reverse();

            assertEquals(java.util.Arrays.asList("C", null, "A"), list.toList());
        }
    }

    @Nested
    @DisplayName("reverseRecursive() -- O(n) time / O(n) stack")
    class ReverseRecursive {

        @Test
        @DisplayName("produces the same result as the iterative version")
        void matchesIterativeVersion() {
            SinglyLinkedList<String> recursive = SinglyLinkedList.of("A", "B", "C", "D");
            SinglyLinkedList<String> iterative = SinglyLinkedList.of("A", "B", "C", "D");

            recursive.reverseRecursive();
            iterative.reverse();

            assertEquals(iterative.toList(), recursive.toList());
        }

        @Test
        @DisplayName("handles empty and single element lists")
        void handlesTinyLists() {
            SinglyLinkedList<String> empty = new SinglyLinkedList<>();
            empty.reverseRecursive();
            assertTrue(empty.isEmpty());

            SinglyLinkedList<String> one = SinglyLinkedList.of("only");
            one.reverseRecursive();
            assertEquals(List.of("only"), one.toList());
            assertEquals("only", one.last());
        }

        @Test
        @DisplayName("keeps head and tail consistent")
        void fixesHeadAndTailPointers() {
            SinglyLinkedList<Integer> list = SinglyLinkedList.of(1, 2, 3);

            list.reverseRecursive();
            list.addLast(0);

            assertEquals(List.of(3, 2, 1, 0), list.toList());
            assertEquals(3, list.first());
            assertEquals(0, list.last());
        }
    }

    @Nested
    @DisplayName("reversedCopy() -- O(n) time / O(n) space")
    class ReversedCopy {

        @Test
        @DisplayName("returns a reversed list and leaves the original untouched")
        void doesNotMutateTheOriginal() {
            SinglyLinkedList<String> original = SinglyLinkedList.of("A", "B", "C");

            SinglyLinkedList<String> copy = original.reversedCopy();

            assertEquals(List.of("C", "B", "A"), copy.toList());
            assertEquals(List.of("A", "B", "C"), original.toList());
        }

        @Test
        @DisplayName("changing the copy does not change the original")
        void copyIsIndependent() {
            SinglyLinkedList<String> original = SinglyLinkedList.of("A", "B");

            SinglyLinkedList<String> copy = original.reversedCopy();
            copy.addLast("Z");

            assertEquals(2, original.size());
            assertEquals(3, copy.size());
        }
    }

    @Nested
    @DisplayName("generics -- one class, many element types")
    class Generics {

        @Test
        @DisplayName("stores Strings, Integers, Doubles and custom types")
        void storesAnyType() {
            SinglyLinkedList<String> strings = SinglyLinkedList.of("x", "y");
            SinglyLinkedList<Integer> integers = SinglyLinkedList.of(1, 2);
            SinglyLinkedList<Double> doubles = SinglyLinkedList.of(1.5, 2.5);
            SinglyLinkedList<Point> points = SinglyLinkedList.of(new Point(1, 2), new Point(3, 4));

            strings.reverse();
            integers.reverse();
            doubles.reverse();
            points.reverse();

            assertEquals(List.of("y", "x"), strings.toList());
            assertEquals(List.of(2, 1), integers.toList());
            assertEquals(List.of(2.5, 1.5), doubles.toList());
            assertEquals(List.of(new Point(3, 4), new Point(1, 2)), points.toList());
        }

        @Test
        @DisplayName("no cast is needed to use the retrieved element")
        void retrievedElementsAreTyped() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("hello");

            String value = list.get(0);   // compiles without a cast -- that is generics

            assertEquals(5, value.length());
        }

        /** A custom element type used to prove any object works. */
        record Point(int x, int y) {
        }
    }

    @Nested
    @DisplayName("insertions")
    class Insertions {

        @Test
        void addFirstPrepends() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            list.addFirst("B");
            list.addFirst("A");

            assertEquals(List.of("A", "B"), list.toList());
            assertEquals("A", list.first());
            assertEquals("B", list.last());
        }

        @Test
        void addLastAppends() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            list.addLast("A");
            list.addLast("B");

            assertEquals(List.of("A", "B"), list.toList());
            assertEquals("B", list.last());
        }

        @Test
        void insertAtPlacesElementAtTheGivenIndex() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "C");

            list.insertAt(1, "B");

            assertEquals(List.of("A", "B", "C"), list.toList());
        }

        @Test
        void insertAtSizeAppends() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B");

            list.insertAt(2, "C");

            assertEquals(List.of("A", "B", "C"), list.toList());
            assertEquals("C", list.last());
        }

        @Test
        void insertAtRejectsBadIndexes() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A");

            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(-1, "x"));
            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(2, "x"));
        }
    }

    @Nested
    @DisplayName("removals")
    class Removals {

        @Test
        void removeFirstReturnsAndDropsTheHead() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            assertEquals("A", list.removeFirst());
            assertEquals(List.of("B", "C"), list.toList());
        }

        @Test
        void removeLastReturnsAndDropsTheTail() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            assertEquals("C", list.removeLast());
            assertEquals(List.of("A", "B"), list.toList());
            assertEquals("B", list.last());
        }

        @Test
        void removingTheOnlyElementEmptiesTheList() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A");

            assertEquals("A", list.removeLast());
            assertTrue(list.isEmpty());
            assertThrows(NoSuchElementException.class, list::first);
        }

        @Test
        void removeAtDropsTheElementAtTheIndex() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            assertEquals("B", list.removeAt(1));
            assertEquals(List.of("A", "C"), list.toList());
        }

        @Test
        void removeAtLastIndexKeepsTailCorrect() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            list.removeAt(2);
            list.addLast("Z");

            assertEquals(List.of("A", "B", "Z"), list.toList());
        }

        @Test
        void removeByValueRemovesTheFirstMatchOnly() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "A");

            assertTrue(list.remove("A"));
            assertEquals(List.of("B", "A"), list.toList());
            assertFalse(list.remove("Z"));
        }

        @Test
        void clearEmptiesTheList() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B");

            list.clear();

            assertTrue(list.isEmpty());
            assertEquals("[]", list.toString());
            list.addLast("A");                 // list is still usable afterwards
            assertEquals(List.of("A"), list.toList());
        }

        @Test
        void removingFromAnEmptyListThrows() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            assertThrows(NoSuchElementException.class, list::removeFirst);
            assertThrows(NoSuchElementException.class, list::removeLast);
        }
    }

    @Nested
    @DisplayName("queries and iteration")
    class Queries {

        @Test
        void getReturnsTheElementAtTheIndex() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            assertEquals("A", list.get(0));
            assertEquals("C", list.get(2));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        }

        @Test
        void setOverwritesAndReturnsThePreviousValue() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B");

            assertEquals("B", list.set(1, "Z"));
            assertEquals(List.of("A", "Z"), list.toList());
        }

        @Test
        void indexOfAndContainsHandleNulls() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", null, "C");

            assertEquals(1, list.indexOf(null));
            assertTrue(list.contains(null));
            assertEquals(-1, list.indexOf("Z"));
            assertNull(list.get(1));
        }

        @Test
        void iteratorWalksFrontToBack() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B");

            List<String> seen = new ArrayList<>();
            for (String value : list) {
                seen.add(value);
            }

            assertEquals(List.of("A", "B"), seen);
        }

        @Test
        void iteratorThrowsWhenExhausted() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A");
            Iterator<String> it = list.iterator();

            assertEquals("A", it.next());
            assertFalse(it.hasNext());
            assertThrows(NoSuchElementException.class, it::next);
        }

        @Test
        void toStringRendersArrows() {
            assertEquals("[A -> B]", SinglyLinkedList.of("A", "B").toString());
            assertEquals("[]", new SinglyLinkedList<String>().toString());
        }
    }

    @Nested
    @DisplayName("Lombok -- generated code works inside the list")
    class LombokElements {

        /**
         * A mutable element type whose constructor, getters, setters,
         * {@code equals} and {@code hashCode} are all generated by Lombok. This
         * test exists partly to prove the data structure is happy with such
         * types, and partly to prove the build wires Lombok into the TEST
         * source set too ({@code annotationProcessorPaths} in pom.xml) --
         * if that line were missing, this class would not compile.
         */
        @Data
        @AllArgsConstructor
        static class Book {
            private String title;
            private int pages;
        }

        @Test
        @DisplayName("stores Lombok beans and reverses them")
        void reversesListOfLombokBeans() {
            SinglyLinkedList<Book> books = SinglyLinkedList.of(
                    new Book("DSA", 300), new Book("Java", 450));

            books.reverse();

            assertEquals("Java", books.first().getTitle());   // generated getter
            assertEquals(300, books.last().getPages());
        }

        @Test
        @DisplayName("uses the generated equals() for contains() and indexOf()")
        void generatedEqualsDrivesSearching() {
            SinglyLinkedList<Book> books = SinglyLinkedList.of(
                    new Book("DSA", 300), new Book("Java", 450));

            // A DIFFERENT object with the same field values: only Lombok's
            // generated equals() makes this succeed. Without @Data this would
            // fall back to identity comparison and return false / -1.
            assertTrue(books.contains(new Book("Java", 450)));
            assertEquals(1, books.indexOf(new Book("Java", 450)));
        }

        @Test
        @DisplayName("generated setter mutates the element in place")
        void generatedSetterMutatesInPlace() {
            SinglyLinkedList<Book> books = SinglyLinkedList.of(new Book("DSA", 300));

            books.first().setPages(320);

            assertEquals(320, books.get(0).getPages());
        }

        @Test
        @DisplayName("Node.toString prints only the value, never the whole chain")
        void nodeToStringDoesNotFollowTheChain() {
            // @ToString(of = "value") on Node keeps this O(1). A plain
            // @ToString would recurse through `next` and print the entire list.
            Node<String> a = new Node<>("A");
            Node<String> b = new Node<>("B");
            a.next = b;

            assertEquals("Node(value=A)", a.toString());
            assertFalse(a.toString().contains("B"));
        }
    }
}
