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
 * {@code ./mvnw test -Dtest='SinglyLinkedListTest$FindMiddle'}.</p>
 */
@DisplayName("SinglyLinkedList")
class SinglyLinkedListTest {

    @Nested
    @DisplayName("findMiddle() -- the star, one pass, O(n) time / O(1) space")
    class FindMiddle {

        @Test
        @DisplayName("returns the true middle of an odd-length list")
        void returnsTheTrueMiddleOfAnOddLengthList() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D", "E");

            assertEquals("C", list.findMiddle());
        }

        @Test
        @DisplayName("returns the SECOND of the two middles of an even-length list")
        void returnsTheSecondOfTwoMiddles() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D");

            assertEquals("C", list.findMiddle());
        }

        @Test
        @DisplayName("a single-node list is its own middle")
        void singleNodeIsItsOwnMiddle() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A");

            assertEquals("A", list.findMiddle());
        }

        @Test
        @DisplayName("a two-node list returns the second node, not the first")
        void twoNodeListReturnsSecondNode() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B");

            assertEquals("B", list.findMiddle());
        }

        @Test
        @DisplayName("an empty list has no middle")
        void emptyListThrows() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            assertThrows(NoSuchElementException.class, list::findMiddle);
        }

        @Test
        @DisplayName("middleIndex() reports the zero-based position findMiddle() lands on")
        void middleIndexMatchesFindMiddle() {
            SinglyLinkedList<String> odd = SinglyLinkedList.of("A", "B", "C", "D", "E");
            SinglyLinkedList<String> even = SinglyLinkedList.of("A", "B", "C", "D");

            assertEquals(2, odd.middleIndex());
            assertEquals(odd.get(odd.middleIndex()), odd.findMiddle());
            assertEquals(2, even.middleIndex());
            assertEquals(even.get(even.middleIndex()), even.findMiddle());
        }

        @ParameterizedTest(name = "matches a reference implementation for n = {0}")
        @ValueSource(ints = {1, 2, 3, 4, 5, 8, 9, 16, 17, 32, 33, 50})
        void matchesReferenceForEveryLength(int n) {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            List<Integer> reference = new ArrayList<>();
            IntStream.range(0, n).forEach(i -> {
                list.addLast(i);
                reference.add(i);
            });

            Integer expected = reference.get(reference.size() / 2);

            assertEquals(expected, list.findMiddle(), "findMiddle() wrong for n=" + n);
            assertEquals(expected, list.findMiddleTwoPass(), "findMiddleTwoPass() wrong for n=" + n);
            assertEquals(expected, list.findMiddleFromSize(), "findMiddleFromSize() wrong for n=" + n);
            assertEquals(reference.size() / 2, list.middleIndex(), "middleIndex() wrong for n=" + n);
        }
    }

    @Nested
    @DisplayName("findMiddleTwoPass() / findMiddleFromSize() -- must agree with findMiddle()")
    class AlternativeImplementations {

        @Test
        @DisplayName("all three agree on a hand-picked list")
        void allThreeAgree() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D", "E", "F", "G");

            assertEquals(list.findMiddle(), list.findMiddleTwoPass());
            assertEquals(list.findMiddle(), list.findMiddleFromSize());
        }

        @Test
        @DisplayName("findMiddleTwoPass() does not read the cached size field")
        void twoPassIgnoresCachedSize() {
            // Not directly observable from outside, but this pins the contract:
            // the two-pass version must still be correct even immediately after
            // a removal, when a bug that peeked at a stale `size` would show up
            // as an off-by-one.
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D", "E");
            list.removeLast();

            assertEquals("C", list.findMiddleTwoPass());
            assertEquals(list.findMiddle(), list.findMiddleTwoPass());
        }

        @Test
        @DisplayName("all three throw on an empty list")
        void allThreeThrowOnEmptyList() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            assertThrows(NoSuchElementException.class, list::findMiddleTwoPass);
            assertThrows(NoSuchElementException.class, list::findMiddleFromSize);
        }
    }

    @Nested
    @DisplayName("insertions")
    class Insertions {

        @Test
        void addFirstPutsTheValueAtIndexZero() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("B", "C");

            list.addFirst("A");

            assertEquals(List.of("A", "B", "C"), list.toList());
            assertEquals("A", list.first());
        }

        @Test
        void addLastAppendsAndFixesTail() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B");

            list.addLast("C");

            assertEquals(List.of("A", "B", "C"), list.toList());
            assertEquals("C", list.last());
        }

        @Test
        void insertAtMiddleSplicesInTheValue() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "C");

            list.insertAt(1, "B");

            assertEquals(List.of("A", "B", "C"), list.toList());
        }

        @Test
        void insertAtZeroDelegatesToAddFirst() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("B");

            list.insertAt(0, "A");

            assertEquals("A", list.first());
        }

        @Test
        void insertAtSizeDelegatesToAddLast() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A");

            list.insertAt(1, "B");

            assertEquals("B", list.last());
        }

        @Test
        void insertAtRejectsOutOfRangeIndexes() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A");

            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(-1, "X"));
            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(2, "X"));
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
        @DisplayName("finds the middle of a list of Lombok beans")
        void findsMiddleOfLombokBeans() {
            SinglyLinkedList<Book> books = SinglyLinkedList.of(
                    new Book("DSA", 300), new Book("Java", 450), new Book("Go", 220));

            assertEquals("Java", books.findMiddle().getTitle());   // generated getter
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
