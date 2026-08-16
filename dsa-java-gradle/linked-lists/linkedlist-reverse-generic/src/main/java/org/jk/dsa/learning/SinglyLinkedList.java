package org.jk.dsa.learning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A teaching implementation of a <b>generic singly linked list</b> whose star
 * feature is {@link #reverse()} -- reversing the chain in place.
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
 *   reverse       O(n) time, O(1) space   <-- iterative, the one to memorise
 *   reverseRec    O(n) time, O(n) space   <-- recursion uses the call stack
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
    // The main event: reversing
    // -----------------------------------------------------------------------

    /**
     * Reverses this list <b>in place</b> using the classic three-pointer loop.
     *
     * <p>No new nodes are created and no second list is built. We only flip the
     * direction of every {@code next} arrow, one node per loop iteration.</p>
     *
     * <p>The three cursors:</p>
     * <ul>
     *   <li>{@code previous} -- the part already reversed (starts as {@code null},
     *       because the old head must end up pointing at nothing).</li>
     *   <li>{@code current}  -- the node whose arrow we are flipping right now.</li>
     *   <li>{@code nextHop}  -- saved copy of {@code current.next}. Without this
     *       we would lose the rest of the list the instant we overwrite the
     *       arrow. Forgetting this line is the single most common bug.</li>
     * </ul>
     *
     * <p>Walk-through for {@code A -> B -> C}:</p>
     * <pre>
     *   start   previous=null  current=A
     *   step 1  A -> null                  previous=A  current=B
     *   step 2  B -> A -> null             previous=B  current=C
     *   step 3  C -> B -> A -> null        previous=C  current=null  (loop ends)
     *   finally head=C, tail=A
     * </pre>
     *
     * <p><b>Time: O(n)</b> -- every node is visited exactly once.<br>
     * <b>Space: O(1)</b> -- only three reference variables, no matter how long
     * the list is. This is the answer an interviewer is looking for.</p>
     */
    public void reverse() {
        Node<T> previous = null;
        Node<T> current = head;

        while (current != null) {
            Node<T> nextHop = current.next; // 1. remember where to go next
            current.next = previous;        // 2. flip this node's arrow backwards
            previous = current;             // 3. this node is now the reversed part
            current = nextHop;              // 4. move on
        }

        // The chain is flipped, so the two ends swap roles.
        Node<T> oldHead = head;
        head = previous;   // `previous` stopped on the last node we processed
        tail = oldHead;    // the original first node is now the last one
    }

    /**
     * Reverses this list in place using recursion instead of a loop.
     *
     * <p>Same result as {@link #reverse()}, shown because reversal is the
     * textbook example of "a loop and a recursion that do the same thing". The
     * recursion dives to the very last node (that node becomes the new head),
     * then flips arrows on the way back out of the calls.</p>
     *
     * <p><b>Time: O(n)</b> -- one call per node.<br>
     * <b>Space: O(n)</b> -- the JVM keeps one stack frame per pending call, so
     * a list of a few hundred thousand nodes can throw
     * {@link StackOverflowError}. That extra memory is exactly why
     * {@link #reverse()} is the version you should prefer in real code.</p>
     */
    public void reverseRecursive() {
        Node<T> oldHead = head;
        head = reverseFrom(head);
        tail = oldHead;
    }

    /**
     * Recursive helper: reverses the chain starting at {@code node} and returns
     * the new first node of that reversed chain.
     *
     * <p>Base case: an empty chain ({@code null}) or a single node is already
     * reversed, so return it unchanged.</p>
     *
     * <p>Recursive case: reverse everything <em>after</em> {@code node}, then
     * make {@code node}'s successor point back at {@code node} and cut
     * {@code node}'s own forward arrow.</p>
     *
     * <p>Time: O(n). Space: O(n) call stack.</p>
     *
     * @param node first node of the chain to reverse
     * @param <E>  element type
     * @return the new head of the reversed chain
     */
    private static <E> Node<E> reverseFrom(Node<E> node) {
        if (node == null || node.next == null) {
            return node;                    // base case: nothing left to flip
        }
        Node<E> newHead = reverseFrom(node.next); // reverse the tail part first
        node.next.next = node;              // make the successor point back at us
        node.next = null;                   // we are the new end of the chain
        return newHead;                     // the deepest node stays the new head
    }

    /**
     * Returns a <b>new</b> list holding the same elements in reverse order,
     * leaving this list untouched.
     *
     * <p>Useful when the original order still matters. Note the trade-off
     * against {@link #reverse()}: this allocates a second chain.</p>
     *
     * <p>Time: O(n) -- one O(1) {@code addFirst} per element.<br>
     * Space: O(n) -- n brand-new nodes.</p>
     *
     * @return a reversed copy of this list
     */
    public SinglyLinkedList<T> reversedCopy() {
        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
        for (Node<T> cursor = head; cursor != null; cursor = cursor.next) {
            copy.addFirst(cursor.value); // prepending each element reverses order
        }
        return copy;
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
