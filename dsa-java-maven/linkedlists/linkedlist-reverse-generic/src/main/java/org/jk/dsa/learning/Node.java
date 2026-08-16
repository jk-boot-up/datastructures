package org.jk.dsa.learning;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * One single box (a "node") of a singly linked list.
 *
 * <p>A singly linked list is nothing more than a chain of these boxes. Each box
 * holds two things:</p>
 * <ol>
 *   <li>{@code value} -- the actual data the student wants to store.</li>
 *   <li>{@code next}  -- an arrow pointing at the following box, or {@code null}
 *       if this box is the last one in the chain.</li>
 * </ol>
 *
 * <pre>
 *   head
 *    |
 *    v
 *  +-----+----+   +-----+----+   +-----+------+
 *  |  A  | ---+-->|  B  | ---+-->|  C  | null |
 *  +-----+----+   +-----+----+   +-----+------+
 * </pre>
 *
 * <p>{@code <T>} is a <em>generic type parameter</em>. It is a placeholder for
 * "whatever type the caller wants". Writing {@code Node<String>} produces a node
 * whose {@code value} is a {@code String}; {@code Node<Integer>} produces one
 * that holds an {@code Integer}. The compiler substitutes the real type for us,
 * which is why this list can store any data type without any casting.</p>
 *
 * <p>The class is package-private (no {@code public} keyword) on purpose: nodes
 * are an internal implementation detail of {@link SinglyLinkedList}. Users of
 * the list should never have to know that nodes exist.</p>
 *
 * @param <T> the type of value stored in this node
 */
@AllArgsConstructor
@ToString(of = "value")
final class Node<T> {

    /** The payload. May be {@code null} -- this list explicitly allows nulls. */
    T value;

    /** Arrow to the next node, or {@code null} when this is the last node. */
    Node<T> next;

    /**
     * Creates a detached node (one that points at nothing yet).
     *
     * <p>This delegates to the two-argument constructor that
     * {@link AllArgsConstructor @AllArgsConstructor} generated for us. The
     * fields are assigned there -- run {@code exec:exec@show-generated} to see
     * the constructor Lombok actually wrote.</p>
     *
     * <p>Time complexity:  O(1) -- just assigns two fields.<br>
     * Space complexity: O(1) -- allocates exactly one node object.</p>
     *
     * @param value the payload to store
     */
    Node(T value) {
        this(value, null);
    }

    // Note {@code (of = "value")} above: it tells Lombok to print ONLY the
    // value field. A plain @ToString would also print `next`, whose toString
    // prints ITS next, and so on down the whole chain -- turning one debugger
    // hover into an O(n) string, or an infinite loop if the list ever contains
    // a cycle. Excluding the link field is the standard fix for linked
    // structures, and it is the kind of trap Lombok makes easy to walk into.
}
