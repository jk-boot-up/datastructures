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
 * the result. Run them all with {@code ./gradlew test}; run a single one with
 * {@code ./gradlew test --tests "*reversesAThreeElementList"}.</p>
 */
@DisplayName("SinglyLinkedList")
class SinglyLinkedListTest {
    @Nested
    @DisplayName("reverseBetween(from, to) -- the star, O(to) time / O(1) space")
    class ReverseBetween {

        @Test
        @DisplayName("reverses only the middle slice, leaving the ends alone")
        void reversesOnlyTheSlice() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D", "E");

            list.reverseBetween(1, 3);

            assertEquals(List.of("A", "D", "C", "B", "E"), list.toList());
            assertEquals("[A -> D -> C -> B -> E]", list.toString());
        }

        @Test
        @DisplayName("a slice starting at the head moves the head")
        void sliceAtHeadMovesHead() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D");

            list.reverseBetween(0, 2);

            assertEquals(List.of("C", "B", "A", "D"), list.toList());
            assertEquals("C", list.first());
            assertEquals("D", list.last());
        }

        @Test
        @DisplayName("a slice ending at the tail keeps addLast working")
        void sliceAtTailFixesTail() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D");

            list.reverseBetween(1, 3);
            list.addLast("Z");        // only correct if `tail` was updated

            assertEquals(List.of("A", "D", "C", "B", "Z"), list.toList());
            assertEquals("Z", list.last());
            assertEquals(5, list.size());
        }

        @Test
        @DisplayName("reversing the whole range equals reversing the list")
        void wholeRangeIsAPlainReverse() {
            SinglyLinkedList<String> slice = SinglyLinkedList.of("A", "B", "C", "D");
            SinglyLinkedList<String> whole = SinglyLinkedList.of("A", "B", "C", "D");

            slice.reverseBetween(0, 3);
            whole.reverse();

            assertEquals(whole.toList(), slice.toList());
            assertEquals(List.of("D", "C", "B", "A"), slice.toList());
        }

        @Test
        @DisplayName("a one-node slice changes nothing")
        void singleNodeSliceIsANoOp() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            list.reverseBetween(1, 1);

            assertEquals(List.of("A", "B", "C"), list.toList());
            assertEquals(3, list.size());
        }

        @Test
        @DisplayName("a two-node slice swaps exactly those two")
        void twoNodeSliceSwaps() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D");

            list.reverseBetween(1, 2);

            assertEquals(List.of("A", "C", "B", "D"), list.toList());
        }

        @Test
        @DisplayName("works on a single element list")
        void singleElementList() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("only");

            list.reverseBetween(0, 0);

            assertEquals(List.of("only"), list.toList());
            assertEquals("only", list.first());
            assertEquals("only", list.last());
        }

        @Test
        @DisplayName("reversing the same slice twice restores the original")
        void reversingTwiceIsIdentity() {
            SinglyLinkedList<Integer> list = SinglyLinkedList.of(1, 2, 3, 4, 5, 6);

            list.reverseBetween(1, 4);
            list.reverseBetween(1, 4);

            assertEquals(List.of(1, 2, 3, 4, 5, 6), list.toList());
        }

        @Test
        @DisplayName("size never changes")
        void sizeIsPreserved() {
            SinglyLinkedList<Integer> list = SinglyLinkedList.of(1, 2, 3, 4, 5);

            list.reverseBetween(1, 3);

            assertEquals(5, list.size());
        }

        @Test
        @DisplayName("handles null elements inside the slice")
        void handlesNullElements() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", null, "C", "D");

            list.reverseBetween(1, 2);

            assertEquals(java.util.Arrays.asList("A", "C", null, "D"), list.toList());
        }

        @Test
        @DisplayName("the list stays fully traversable from head to tail")
        void chainStaysIntact() {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            IntStream.range(0, 50).forEach(list::addLast);

            list.reverseBetween(10, 39);

            // walking with the iterator must still visit exactly 50 elements
            // and must end on the node `tail` points at
            List<Integer> seen = list.toList();
            assertEquals(50, seen.size());
            assertEquals(seen.get(49), list.last());
            assertEquals(seen.get(0), list.first());
        }

        @ParameterizedTest(name = "every slice of a {0} element list is correct")
        @ValueSource(ints = {1, 2, 3, 5, 8})
        void everySliceMatchesAReferenceImplementation(int n) {
            for (int from = 0; from < n; from++) {
                for (int to = from; to < n; to++) {
                    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
                    IntStream.range(0, n).forEach(list::addLast);

                    list.reverseBetween(from, to);

                    assertEquals(referenceReverseBetween(n, from, to), list.toList(),
                            "failed for n=" + n + " from=" + from + " to=" + to);
                    assertEquals(n, list.size());
                    assertEquals(referenceReverseBetween(n, from, to).get(n - 1), list.last(),
                            "tail wrong for n=" + n + " from=" + from + " to=" + to);
                }
            }
        }

        @Test
        @DisplayName("rejects indexes outside the list")
        void rejectsBadIndexes() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            assertThrows(IndexOutOfBoundsException.class, () -> list.reverseBetween(-1, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> list.reverseBetween(0, 3));
            assertThrows(IndexOutOfBoundsException.class, () -> list.reverseBetween(3, 3));
        }

        @Test
        @DisplayName("rejects a backwards slice")
        void rejectsBackwardsSlice() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C");

            assertThrows(IllegalArgumentException.class, () -> list.reverseBetween(2, 1));
        }

        @Test
        @DisplayName("rejects any slice of an empty list")
        void rejectsSliceOfEmptyList() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();

            assertThrows(IndexOutOfBoundsException.class, () -> list.reverseBetween(0, 0));
        }
    }

    @Nested
    @DisplayName("reverseBetweenWithoutSentinel -- must behave identically")
    class WithoutSentinel {

        @ParameterizedTest(name = "sentinel and sentinel-free agree for n = {0}")
        @ValueSource(ints = {1, 2, 3, 5, 8})
        void bothImplementationsAgree(int n) {
            for (int from = 0; from < n; from++) {
                for (int to = from; to < n; to++) {
                    SinglyLinkedList<Integer> withSentinel = new SinglyLinkedList<>();
                    SinglyLinkedList<Integer> without = new SinglyLinkedList<>();
                    IntStream.range(0, n).forEach(i -> { withSentinel.addLast(i); without.addLast(i); });

                    withSentinel.reverseBetween(from, to);
                    without.reverseBetweenWithoutSentinel(from, to);

                    assertEquals(withSentinel.toList(), without.toList(),
                            "differ for n=" + n + " from=" + from + " to=" + to);
                    assertEquals(withSentinel.first(), without.first());
                    assertEquals(withSentinel.last(), without.last());
                }
            }
        }

        @Test
        @DisplayName("the sentinel-free version also fixes head and tail")
        void fixesHeadAndTail() {
            SinglyLinkedList<String> list = SinglyLinkedList.of("A", "B", "C", "D");

            list.reverseBetweenWithoutSentinel(0, 3);
            list.addLast("Z");

            assertEquals(List.of("D", "C", "B", "A", "Z"), list.toList());
            assertEquals("D", list.first());
            assertEquals("Z", list.last());
        }
    }

    @Nested
    @DisplayName("reversedBetweenCopy -- O(n) time / O(n) space")
    class ReversedBetweenCopy {

        @Test
        @DisplayName("returns a reversed slice and leaves the original untouched")
        void doesNotMutateTheOriginal() {
            SinglyLinkedList<String> original = SinglyLinkedList.of("A", "B", "C", "D");

            SinglyLinkedList<String> copy = original.reversedBetweenCopy(1, 2);

            assertEquals(List.of("A", "C", "B", "D"), copy.toList());
            assertEquals(List.of("A", "B", "C", "D"), original.toList());
        }

        @Test
        @DisplayName("changing the copy does not change the original")
        void copyIsIndependent() {
            SinglyLinkedList<String> original = SinglyLinkedList.of("A", "B", "C");

            SinglyLinkedList<String> copy = original.reversedBetweenCopy(0, 2);
            copy.addLast("Z");

            assertEquals(3, original.size());
            assertEquals(4, copy.size());
        }
    }

    /**
     * An obviously-correct reference implementation, used to check the real one.
     * It builds the expected answer with plain Java lists, where reversing a
     * slice is trivial -- so any disagreement is a bug in the linked list.
     */
    private static List<Integer> referenceReverseBetween(int n, int from, int to) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            out.add(i);
        }
        List<Integer> slice = new ArrayList<>(out.subList(from, to + 1));
        java.util.Collections.reverse(slice);
        for (int i = from; i <= to; i++) {
            out.set(i, slice.get(i - from));
        }
        return out;
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
         * source set too ({@code testAnnotationProcessor} in build.gradle.kts) --
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
