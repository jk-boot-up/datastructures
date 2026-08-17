package org.jk.dsa.learning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A teaching implementation of a <b>generic singly linked list</b> whose star
 * feature is {@link #findMiddle()} -- finding the middle element in a single
 * pass, using two pointers that move at different speeds.
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
 *       O(1) instead of O(n).</li>
 *   <li>{@code size} -- cached element count so {@link #size()} is O(1). Note
 *       that {@link #findMiddle()} deliberately does <b>not</b> use this field
 *       -- see the class-level note on that method.</li>
 * </ul>
 *
 * <h2>Complexity cheat-sheet</h2>
 * <pre>
 *   addFirst          O(1) time, O(1) space
 *   addLast           O(1) time, O(1) space   (thanks to the tail pointer)
 *   insertAt(i)       O(i) time, O(1) space
 *   removeFirst       O(1) time, O(1) space
 *   removeLast        O(n) time, O(1) space   (must walk to the second-last node)
 *   removeAt(i)       O(i) time, O(1) space
 *   get(i)/set(i)     O(i) time, O(1) space
 *   indexOf           O(n) time, O(1) space
 *   findMiddle        O(n) time, O(1) space   <-- ONE pass, the one to memorise
 *   findMiddleTwoPass O(n) time, O(1) space   <-- TWO passes, for comparison
 *   findMiddleFromSize O(n) time, O(1) space  <-- trivial, but only because
 *                                                  size happens to be cached
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
    // The main event: finding the middle
    // -----------------------------------------------------------------------

    /**
     * Returns the value of the <b>middle node</b>, walking the list exactly
     * <b>once</b> using two pointers that move at different speeds -- the
     * "tortoise and hare" (a.k.a. slow/fast pointer) technique.
     *
     * <p>{@code slow} takes one step per loop iteration; {@code fast} takes two.
     * When {@code fast} has run out of room to take two more steps, it has
     * covered roughly twice the distance {@code slow} has, so {@code slow} is
     * sitting exactly on the middle node. No arithmetic on the length is ever
     * needed -- the length falls out of the race itself.</p>
     *
     * <p><b>Convention for even-length lists:</b> there are two middle nodes.
     * This method returns the <b>second</b> one (the same convention used by
     * the classic interview version of this problem). For {@code A,B,C,D} that
     * is {@code C}, at index 2.</p>
     *
     * <p>Walk-through for {@code A -> B -> C -> D -> E}:</p>
     * <pre>
     *   start   slow=A  fast=A
     *   step 1  slow=B  fast=C          (fast took 2 steps: A -> B -> C)
     *   step 2  slow=C  fast=E          (fast took 2 steps: C -> D -> E)
     *   loop ends: fast.next is null, so fast cannot take 2 more steps
     *   answer: slow = C   (the true middle of 5 elements)
     * </pre>
     *
     * <p>Why not just use {@link #size()}? Because {@code size} is a field this
     * particular class happens to cache. Plenty of sequences you meet in the
     * wild cannot be measured for free -- a network stream, a file read one
     * line at a time, an iterator that cannot be rewound. The slow/fast
     * technique finds the middle without ever knowing the length in advance,
     * and without visiting any node twice. That is also exactly the tool
     * behind cycle detection, palindrome checking and list reordering -- see
     * {@link #findMiddleTwoPass()} and {@link #findMiddleFromSize()} for the
     * two alternatives this method is worth comparing against.</p>
     *
     * <p><b>Time: O(n)</b> -- {@code fast} visits every node (or every other
     * node), so the walk is linear.<br>
     * <b>Space: O(1)</b> -- two reference variables, no matter how long the
     * list is.</p>
     *
     * @return the value stored in the middle node
     * @throws NoSuchElementException if the list is empty
     */
    public T findMiddle() {
        requireNonEmpty();
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;           // tortoise: one step
            fast = fast.next.next;      // hare: two steps
        }
        return slow.value;
    }

    /**
     * Same answer as {@link #findMiddle()}, but computed the "obvious" way: one
     * pass to count the nodes, then a second pass to walk to {@code count / 2}.
     *
     * <p>This deliberately does <b>not</b> read the cached {@link #size} field
     * -- it counts by walking, the way you would have to if the sequence did
     * not offer a length at all. It exists purely so you can compare it against
     * {@link #findMiddle()}: both are O(n) time, but this one walks the
     * sequence <b>twice</b>, and it only works at all if the sequence can be
     * traversed more than once. A one-shot stream cannot be counted and then
     * re-read; {@link #findMiddle()} needs no such guarantee.</p>
     *
     * <p><b>Time: O(n)</b> -- two full passes (about {@code 2n} node visits),
     * versus roughly {@code 1.5n} for the one-pass version above.<br>
     * <b>Space: O(1)</b>.</p>
     *
     * @return the value stored in the middle node
     * @throws NoSuchElementException if the list is empty
     */
    public T findMiddleTwoPass() {
        requireNonEmpty();
        int count = 0;
        for (Node<T> cursor = head; cursor != null; cursor = cursor.next) {
            count++;                    // pass 1: count the nodes
        }
        Node<T> cursor = head;
        for (int i = 0; i < count / 2; i++) {
            cursor = cursor.next;       // pass 2: walk to the middle
        }
        return cursor.value;
    }

    /**
     * Same answer again, but the version that is only this simple because
     * {@link SinglyLinkedList} happens to cache {@link #size}.
     *
     * <p>If you already know the length -- because it is cached, as it is
     * here, or because you are working with a plain array -- finding the
     * middle needs no cleverness at all: the index is {@code size / 2}. This
     * method exists to make an honest point: {@link #findMiddle()} is not the
     * "advanced" way to solve an easy problem. It is the <b>only</b> one-pass
     * option once you take the cached length away, which is the normal
     * situation for a real singly linked list (this class caches {@code size}
     * as a convenience; the classic textbook definition of the structure does
     * not).</p>
     *
     * <p><b>Time: O(n)</b> -- O(1) to read the cached length, then one walk of
     * {@code size / 2} steps.<br>
     * <b>Space: O(1)</b>.</p>
     *
     * @return the value stored in the middle node
     * @throws NoSuchElementException if the list is empty
     */
    public T findMiddleFromSize() {
        requireNonEmpty();
        Node<T> cursor = head;
        for (int i = 0; i < size / 2; i++) {
            cursor = cursor.next;
        }
        return cursor.value;
    }

    /**
     * The zero-based index the slow pointer lands on in {@link #findMiddle()}.
     *
     * <p>Written separately (rather than having {@code findMiddle} return an
     * index) so that {@code findMiddle}'s contract stays "returns a value",
     * consistent with the rest of this class. Runs the identical race, just
     * counting steps instead of discarding them.</p>
     *
     * <p>Time: O(n). Space: O(1).</p>
     *
     * @return the zero-based index of the middle node
     * @throws NoSuchElementException if the list is empty
     */
    public int middleIndex() {
        requireNonEmpty();
        int index = 0;
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            index++;
        }
        return index;
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
