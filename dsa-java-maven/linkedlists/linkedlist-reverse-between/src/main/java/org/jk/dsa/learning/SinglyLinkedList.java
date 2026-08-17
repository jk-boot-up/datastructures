package org.jk.dsa.learning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A teaching implementation of a <b>generic singly linked list</b> whose star
 * feature is {@link #reverseBetween(int, int)} -- reversing only a <em>slice</em>
 * of the chain, in place, and leaving everything outside that slice untouched.
 *
 * <pre>
 *   before:  A -&gt; B -&gt; C -&gt; D -&gt; E      reverseBetween(1, 3)
 *   after:   A -&gt; D -&gt; C -&gt; B -&gt; E
 * </pre>
 *
 * <p>The reversal loop itself is the ordinary three-pointer loop. What makes
 * this problem worth studying is the <em>bookkeeping</em> around it: finding the
 * node before the slice, remembering which node will end up last, and stitching
 * the two ends back together. A <b>sentinel</b> node removes the one nasty edge
 * case; see {@link #reverseBetween(int, int)} for the details.</p>
 *
 * <h2>What "generic" means here</h2>
 * <p>The {@code <T>} after the class name is a type placeholder. You pick the
 * real type when you create the list:</p>
 * <pre>{@code
 * SinglyLinkedList<String>  names   = new SinglyLinkedList<>();
 * SinglyLinkedList<Integer> numbers = new SinglyLinkedList<>();
 * SinglyLinkedList<Student> class10 = new SinglyLinkedList<>();
 * }</pre>
 * <p>The compiler then guarantees you cannot put a {@code Student} into
 * {@code names}, and {@code names.get(0)} already <em>is</em> a {@code String}
 * -- no casting needed. That is the whole point of Java generics.</p>
 *
 * <h2>Internal shape</h2>
 * <p>Three fields do all the work:</p>
 * <ul>
 *   <li>{@code head} -- first node, or {@code null} when the list is empty.</li>
 *   <li>{@code tail} -- last node. Kept only so {@link #addLast(Object)} can be
 *       O(1) instead of O(n). Every method that changes the shape of the list
 *       must remember to keep {@code tail} correct.</li>
 *   <li>{@code size} -- cached element count so {@link #size()} is O(1).</li>
 * </ul>
 *
 * <h2>Complexity cheat-sheet</h2>
 * <pre>
 *   addFirst      O(1) time, O(1) space
 *   addLast       O(1) time, O(1) space   (thanks to the tail pointer)
 *   insertAt(i)   O(i) time, O(1) space
 *   removeFirst   O(1) time, O(1) space
 *   removeLast    O(n) time, O(1) space   (must walk to the second-last node)
 *   removeAt(i)   O(i) time, O(1) space
 *   get(i)/set(i) O(i) time, O(1) space
 *   indexOf       O(n) time, O(1) space
 *
 *   reverseBetween(from, to)        O(to) time, O(1) space  <-- the star
 *   reverseBetweenWithoutSentinel   O(to) time, O(1) space  <-- same, harder to read
 *   reverse()                       O(n)  time, O(1) space  <-- the whole-list case
 *   reversedBetweenCopy(from, to)   O(n)  time, O(n) space  <-- non-destructive
 * </pre>
 *
 * <p>This class is deliberately <b>not</b> thread-safe and does not implement
 * {@code java.util.List}; it is written to be read and understood, not to be a
 * production collection.</p>
 *
 * @param <T> type of the elements stored in this list
 */
public class SinglyLinkedList<T> implements Iterable<T> {

    /** First node of the chain, or {@code null} if the list is empty. */
    private Node<T> head;

    /** Last node of the chain, or {@code null} if the list is empty. */
    private Node<T> tail;

    /** How many elements the list currently holds. */
    private int size;

    /**
     * Creates an empty list.
     *
     * <p>Time: O(1). Space: O(1).</p>
     */
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Convenience factory that builds a list from plain values, handy in tests
     * and demos: {@code SinglyLinkedList.of("A", "B", "C")}.
     *
     * <p>Time: O(n) -- one O(1) {@code addLast} per value.<br>
     * Space: O(n) -- one node per value.</p>
     *
     * @param values the values to append, in order
     * @param <E>    element type, inferred from the arguments
     * @return a new list containing {@code values} in the given order
     */
    @SafeVarargs
    public static <E> SinglyLinkedList<E> of(E... values) {
        SinglyLinkedList<E> list = new SinglyLinkedList<>();
        for (E value : values) {
            list.addLast(value);
        }
        return list;
    }

    // -----------------------------------------------------------------------
    // Queries
    // -----------------------------------------------------------------------

    /**
     * Number of elements currently stored.
     *
     * <p>Time: O(1) -- we keep a running counter instead of walking the chain.<br>
     * Space: O(1).</p>
     *
     * @return the element count
     */
    public int size() {
        return size;
    }

    /**
     * Whether the list holds no elements.
     *
     * <p>Time: O(1). Space: O(1).</p>
     *
     * @return {@code true} when the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the element at {@code index}, counting from 0.
     *
     * <p>Unlike an array, a linked list cannot jump straight to a position: the
     * only way in is through {@code head}, following {@code next} arrows one at
     * a time. That walk is what makes this O(index) rather than O(1).</p>
     *
     * <p>Time: O(index), worst case O(n).<br>
     * Space: O(1) -- a single cursor variable.</p>
     *
     * @param index zero-based position
     * @return the element stored at that position
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
     */
    public T get(int index) {
        return nodeAt(index).value;
    }

    /**
     * Overwrites the element at {@code index} and returns the old value.
     *
     * <p>Time: O(index). Space: O(1).</p>
     *
     * @param index zero-based position
     * @param value the new value
     * @return the value that was previously stored there
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
     */
    public T set(int index, T value) {
        Node<T> node = nodeAt(index);
        T previous = node.value;
        node.value = value;
        return previous;
    }

    /**
     * First element of the list.
     *
     * <p>Time: O(1). Space: O(1).</p>
     *
     * @return the head value
     * @throws NoSuchElementException if the list is empty
     */
    public T first() {
        requireNonEmpty();
        return head.value;
    }

    /**
     * Last element of the list.
     *
     * <p>Time: O(1) -- only because we cache {@code tail}. Without that field
     * this would be an O(n) walk.<br>
     * Space: O(1).</p>
     *
     * @return the tail value
     * @throws NoSuchElementException if the list is empty
     */
    public T last() {
        requireNonEmpty();
        return tail.value;
    }

    /**
     * Position of the first node whose value equals {@code value}, or -1.
     *
     * <p>{@link Objects#equals(Object, Object)} is used so that {@code null}
     * elements are handled without a {@code NullPointerException}.</p>
     *
     * <p>Time: O(n) -- in the worst case every node is compared.<br>
     * Space: O(1).</p>
     *
     * @param value the value to look for (may be {@code null})
     * @return the zero-based index, or -1 when not present
     */
    public int indexOf(T value) {
        int index = 0;
        for (Node<T> cursor = head; cursor != null; cursor = cursor.next) {
            if (Objects.equals(cursor.value, value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Whether {@code value} appears anywhere in the list.
     *
     * <p>Time: O(n). Space: O(1).</p>
     *
     * @param value the value to look for (may be {@code null})
     * @return {@code true} when the value is present
     */
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    // -----------------------------------------------------------------------
    // Insertions
    // -----------------------------------------------------------------------

    /**
     * Inserts {@code value} at the very front of the list.
     *
     * <p>Three lines, no walking: make a node, point it at the old head, then
     * call it the new head. This is why a linked list beats an array for
     * front-insertion (an {@code ArrayList} must shift every element).</p>
     *
     * <p>Time: O(1). Space: O(1) -- one new node.</p>
     *
     * @param value the value to prepend (may be {@code null})
     */
    public void addFirst(T value) {
        Node<T> fresh = new Node<>(value);
        fresh.next = head;      // new node points at the old first node
        head = fresh;           // ... and becomes the new first node
        if (tail == null) {     // list was empty: the one node is also the tail
            tail = fresh;
        }
        size++;
    }

    /**
     * Appends {@code value} to the end of the list.
     *
     * <p>Time: O(1) because {@code tail} tells us where the end is. If we had
     * no tail pointer we would have to walk the whole chain: O(n).<br>
     * Space: O(1) -- one new node.</p>
     *
     * @param value the value to append (may be {@code null})
     */
    public void addLast(T value) {
        Node<T> fresh = new Node<>(value);
        if (tail == null) {     // empty list: the new node is both head and tail
            head = fresh;
        } else {
            tail.next = fresh;  // old last node now points at the new node
        }
        tail = fresh;
        size++;
    }

    /**
     * Inserts {@code value} so that it ends up at position {@code index}.
     *
     * <p>{@code index == size()} is allowed and means "append".</p>
     *
     * <p>Time: O(index) -- we walk to the node just before the target slot.<br>
     * Space: O(1).</p>
     *
     * @param index where the value should land, 0..size()
     * @param value the value to insert (may be {@code null})
     * @throws IndexOutOfBoundsException if {@code index < 0 || index > size()}
     */
    public void insertAt(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "insertAt index " + index + " is outside 0.." + size);
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        Node<T> previous = nodeAt(index - 1);
        Node<T> fresh = new Node<>(value);
        fresh.next = previous.next;   // new node takes over the rest of the chain
        previous.next = fresh;        // ... and gets hooked in after `previous`
        size++;
    }

    // -----------------------------------------------------------------------
    // Removals
    // -----------------------------------------------------------------------

    /**
     * Removes and returns the first element.
     *
     * <p>Time: O(1). Space: O(1).</p>
     *
     * @return the removed value
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        requireNonEmpty();
        Node<T> removed = head;
        head = removed.next;    // second node becomes the first
        removed.next = null;    // unlink so the garbage collector can reclaim it
        if (head == null) {     // we removed the only node
            tail = null;
        }
        size--;
        return removed.value;
    }

    /**
     * Removes and returns the last element.
     *
     * <p>This is the operation a singly linked list is bad at. A node has no
     * arrow back to its predecessor, so to delete the tail we must walk from the
     * head to the second-last node. A <em>doubly</em> linked list would do this
     * in O(1).</p>
     *
     * <p>Time: O(n). Space: O(1).</p>
     *
     * @return the removed value
     * @throws NoSuchElementException if the list is empty
     */
    public T removeLast() {
        requireNonEmpty();
        if (head == tail) {           // exactly one node
            return removeFirst();
        }
        Node<T> cursor = head;
        while (cursor.next != tail) { // stop on the node before the tail
            cursor = cursor.next;
        }
        T value = tail.value;
        cursor.next = null;           // new end of chain
        tail = cursor;
        size--;
        return value;
    }

    /**
     * Removes and returns the element at {@code index}.
     *
     * <p>Time: O(index). Space: O(1).</p>
     *
     * @param index zero-based position
     * @return the removed value
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
     */
    public T removeAt(int index) {
        checkElementIndex(index);
        if (index == 0) {
            return removeFirst();
        }
        Node<T> previous = nodeAt(index - 1);
        Node<T> removed = previous.next;
        previous.next = removed.next;   // skip over the removed node
        if (removed == tail) {          // we deleted the last node
            tail = previous;
        }
        removed.next = null;
        size--;
        return removed.value;
    }

    /**
     * Removes the first node whose value equals {@code value}.
     *
     * <p>Time: O(n). Space: O(1).</p>
     *
     * @param value the value to remove (may be {@code null})
     * @return {@code true} if something was removed
     */
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * Empties the list.
     *
     * <p>Dropping {@code head} is enough: nothing references the first node any
     * more, so the whole chain becomes garbage-collectible.</p>
     *
     * <p>Time: O(1). Space: O(1).</p>
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    // -----------------------------------------------------------------------
    // The main event: reversing a slice of the list
    // -----------------------------------------------------------------------

    /**
     * Reverses <b>only the slice</b> from index {@code from} to index
     * {@code to}, both inclusive, leaving every node outside that slice exactly
     * where it was. The work is done <b>in place</b>: no new nodes, no copying.
     *
     * <p>Indexes are <b>zero-based</b>, to match {@link #get(int)} and
     * {@link #insertAt(int, Object)} in this class. (Be careful: the classic
     * textbook and interview statement of this problem numbers the positions
     * from <b>one</b>. The algorithm is identical; only the arithmetic on the
     * bounds changes.)</p>
     *
     * <pre>
     *   before:  A -&gt; B -&gt; C -&gt; D -&gt; E      reverseBetween(1, 3)
     *                  ^         ^
     *                from        to
     *
     *   after:   A -&gt; D -&gt; C -&gt; B -&gt; E
     * </pre>
     *
     * <h3>How it works</h3>
     * <p>The loop in the middle is exactly the three-pointer reversal you would
     * write for a whole list. The only differences are bookkeeping:</p>
     * <ol>
     *   <li>Walk to the node <em>before</em> the slice. Call it {@code beforeSlice}.</li>
     *   <li>Remember the node that <em>starts</em> the slice. After reversing it
     *       becomes the slice's <em>last</em> node, so it is the one that must
     *       point at whatever follows the slice.</li>
     *   <li>Run the ordinary reversal loop exactly {@code to - from + 1} times.</li>
     *   <li>Re-attach both ends.</li>
     * </ol>
     *
     * <h3>The sentinel trick</h3>
     * <p>Step 1 has an awkward case: when {@code from == 0} there <em>is</em> no
     * node before the slice, and the list's {@code head} itself has to change.
     * Rather than writing an {@code if} for it, we put a temporary throwaway
     * node -- a <b>sentinel</b>, also called a dummy head -- in front of the
     * list. Now "the node before the slice" always exists, even for index 0, and
     * the special case disappears. At the end we simply read the real head back
     * out of {@code sentinel.next} and drop the sentinel.</p>
     *
     * <p>Compare this method with
     * {@link #reverseBetweenWithoutSentinel(int, int)}, which is the same
     * algorithm written the hard way, to see exactly how much the sentinel buys.</p>
     *
     * <p><b>Time: O(to)</b> -- one walk to the start of the slice, then one pass
     * over the slice. That is at worst a single pass over the list, O(n).<br>
     * <b>Space: O(1)</b> -- a fixed handful of references plus one sentinel node,
     * no matter how long the list or the slice is.</p>
     *
     * @param from zero-based index of the first node to reverse
     * @param to   zero-based index of the last node to reverse (inclusive)
     * @throws IndexOutOfBoundsException if either index is outside 0..size()-1
     * @throws IllegalArgumentException  if {@code from > to}
     */
    public void reverseBetween(int from, int to) {
        checkSliceBounds(from, to);
        if (from == to) {
            return;                       // a one-node slice is already reversed
        }

        // The sentinel sits in front of the real head, so `beforeSlice` below is
        // never null -- even when the slice starts at index 0.
        Node<T> sentinel = new Node<>(null, head);

        // 1. Walk to the node just before the slice. O(from) steps.
        Node<T> beforeSlice = sentinel;
        for (int i = 0; i < from; i++) {
            beforeSlice = beforeSlice.next;
        }

        // 2. The first node of the slice. After the flip it becomes the LAST
        //    node of the slice, so it is the one that must point at whatever
        //    comes after. Saving it now is the key bookkeeping step.
        Node<T> sliceTail = beforeSlice.next;

        // 3. The ordinary three-pointer reversal -- but run a fixed number of
        //    times instead of until the end of the list.
        Node<T> previous = null;
        Node<T> current = sliceTail;
        for (int i = 0; i <= to - from; i++) {
            Node<T> nextHop = current.next; // save the rest before we overwrite
            current.next = previous;        // flip this node's arrow backwards
            previous = current;             // grow the reversed part
            current = nextHop;              // walk forward
        }
        // `previous` is now the slice's new first node.
        // `current`  is now the first node AFTER the slice (possibly null).

        // 4. Stitch the reversed slice back in between its two neighbours.
        beforeSlice.next = previous;   // the node before now points at the new first
        sliceTail.next = current;      // the new last points at the node after

        // Repair the list's own bookkeeping.
        head = sentinel.next;          // cheap, and correct even when from == 0
        if (current == null) {
            tail = sliceTail;          // the slice ran to the end of the list
        }
        // `size` is unchanged: reversing moves no nodes in or out.
    }

    /**
     * The same algorithm as {@link #reverseBetween(int, int)}, written
     * <b>without</b> a sentinel node, purely so you can compare the two.
     *
     * <p>Everything is identical except that "the node before the slice" may not
     * exist. That single fact forces the two {@code if} statements marked below,
     * and those two branches are the most common source of bugs in this problem.
     * Prefer the sentinel version in real code.</p>
     *
     * <p>Time: O(to), worst case O(n). Space: O(1).</p>
     *
     * @param from zero-based index of the first node to reverse
     * @param to   zero-based index of the last node to reverse (inclusive)
     * @throws IndexOutOfBoundsException if either index is outside 0..size()-1
     * @throws IllegalArgumentException  if {@code from > to}
     */
    public void reverseBetweenWithoutSentinel(int from, int to) {
        checkSliceBounds(from, to);
        if (from == to) {
            return;
        }

        // SPECIAL CASE 1: there is no node before the slice when from == 0.
        Node<T> beforeSlice = null;
        if (from > 0) {
            beforeSlice = head;
            for (int i = 0; i < from - 1; i++) {
                beforeSlice = beforeSlice.next;
            }
        }

        Node<T> sliceTail = (beforeSlice == null) ? head : beforeSlice.next;

        Node<T> previous = null;
        Node<T> current = sliceTail;
        for (int i = 0; i <= to - from; i++) {
            Node<T> nextHop = current.next;
            current.next = previous;
            previous = current;
            current = nextHop;
        }

        // SPECIAL CASE 2: re-attaching depends on whether beforeSlice exists.
        if (beforeSlice == null) {
            head = previous;           // the slice started at the head, so head moved
        } else {
            beforeSlice.next = previous;
        }
        sliceTail.next = current;

        if (current == null) {
            tail = sliceTail;
        }
    }

    /**
     * Reverses the whole list, in place.
     *
     * <p>Included to make the relationship obvious: reversing everything is just
     * the slice {@code [0, size-1]}. Reading this method and
     * {@link #reverseBetween(int, int)} side by side is the fastest way to see
     * that the loop is the same and only the bookkeeping differs.</p>
     *
     * <p>Time: O(n). Space: O(1).</p>
     */
    public void reverse() {
        if (size > 1) {
            reverseBetween(0, size - 1);
        }
    }

    /**
     * Returns a <b>new</b> list with the slice reversed, leaving this list
     * untouched. Useful when the original order still matters.
     *
     * <p>Time: O(n). Space: O(n) -- it allocates a whole second chain, which is
     * exactly the trade-off {@link #reverseBetween(int, int)} avoids.</p>
     *
     * @param from zero-based index of the first element of the slice
     * @param to   zero-based index of the last element of the slice (inclusive)
     * @return a copy of this list with the given slice reversed
     * @throws IndexOutOfBoundsException if either index is outside 0..size()-1
     * @throws IllegalArgumentException  if {@code from > to}
     */
    public SinglyLinkedList<T> reversedBetweenCopy(int from, int to) {
        checkSliceBounds(from, to);
        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
        for (T value : this) {
            copy.addLast(value);
        }
        copy.reverseBetween(from, to);
        return copy;
    }

    /**
     * Validates a slice, so that every reversing method reports the same errors.
     *
     * <p>Time: O(1). Space: O(1).</p>
     *
     * @param from zero-based index of the first element of the slice
     * @param to   zero-based index of the last element of the slice (inclusive)
     * @throws IndexOutOfBoundsException if either index is outside 0..size()-1
     * @throws IllegalArgumentException  if {@code from > to}
     */
    private void checkSliceBounds(int from, int to) {
        if (from < 0 || from >= size) {
            throw new IndexOutOfBoundsException(
                    "from index " + from + " is outside 0.." + (size - 1)
                            + " (size = " + size + ")");
        }
        if (to < 0 || to >= size) {
            throw new IndexOutOfBoundsException(
                    "to index " + to + " is outside 0.." + (size - 1)
                            + " (size = " + size + ")");
        }
        if (from > to) {
            throw new IllegalArgumentException(
                    "from (" + from + ") must not be greater than to (" + to + ")");
        }
    }

    // -----------------------------------------------------------------------
    // Conversion / iteration
    // -----------------------------------------------------------------------

    /**
     * Copies the elements into a {@link List}, in list order.
     *
     * <p>Time: O(n). Space: O(n).</p>
     *
     * @return a new {@code ArrayList} with the same elements
     */
    public List<T> toList() {
        List<T> out = new ArrayList<>(size);
        for (T value : this) {
            out.add(value);
        }
        return out;
    }

    /**
     * Lets the list be used in an enhanced for-loop:
     * {@code for (String s : list) { ... }}.
     *
     * <p>The returned iterator walks the chain lazily -- it holds one cursor and
     * follows {@code next} arrows.</p>
     *
     * <p>Time: O(1) to create, O(n) to walk fully. Space: O(1).</p>
     *
     * @return an iterator over the elements, front to back
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> cursor = head;

            @Override
            public boolean hasNext() {
                return cursor != null;
            }

            @Override
            public T next() {
                if (cursor == null) {
                    throw new NoSuchElementException("iterator moved past the end");
                }
                T value = cursor.value;
                cursor = cursor.next;
                return value;
            }
        };
    }

    /**
     * Human-readable form such as {@code [A -> B -> C]}, or {@code []} when
     * empty. Handy for {@code System.out.println(list)} while learning.
     *
     * <p>Time: O(n). Space: O(n) for the produced string.</p>
     *
     * @return the rendered list
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" -> ", "[", "]");
        for (T value : this) {
            joiner.add(String.valueOf(value));
        }
        return joiner.toString();
    }

    // -----------------------------------------------------------------------
    // Internal helpers
    // -----------------------------------------------------------------------

    /**
     * Walks to the node at {@code index}.
     *
     * <p>Time: O(index). Space: O(1).</p>
     *
     * @param index zero-based position
     * @return the node living at that position
     * @throws IndexOutOfBoundsException if the index is not a valid element slot
     */
    private Node<T> nodeAt(int index) {
        checkElementIndex(index);
        Node<T> cursor = head;
        for (int i = 0; i < index; i++) {
            cursor = cursor.next;
        }
        return cursor;
    }

    /**
     * Guards against indexes that do not point at an existing element.
     *
     * <p>Time: O(1). Space: O(1).</p>
     *
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
     */
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "index " + index + " is outside 0.." + (size - 1)
                            + " (size = " + size + ")");
        }
    }

    /**
     * Guards operations that make no sense on an empty list.
     *
     * <p>Time: O(1). Space: O(1).</p>
     *
     * @throws NoSuchElementException if the list is empty
     */
    private void requireNonEmpty() {
        if (head == null) {
            throw new NoSuchElementException("the list is empty");
        }
    }
}
