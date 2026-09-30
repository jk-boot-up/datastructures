package com.jk.explore.stack;

/**
 * A pile you can only touch at the top: push puts a value on top, pop takes the top one off.
 *
 * <p>Built by hand on a fixed array, as it would be in C: the values sit in slots 0, 1, 2, ... and
 * {@code size} says how many are in use, so the top is always slot {@code size - 1}. Push, pop and
 * peek each touch one slot: one step, however tall the pile. The last value in is the first out,
 * which is why a stack is also called LIFO: last in, first out.
 *
 * @param <E> the kind of value kept, such as an editing action or a web page
 */
public final class ArrayStack<E> {

    private final Object[] slots;
    private int size;
    private final StepCounter steps = new StepCounter();

    /** An empty stack with room for {@code capacity} values. */
    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive: " + capacity);
        }
        this.slots = new Object[capacity];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return slots.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == slots.length;
    }

    public StepCounter steps() {
        return steps;
    }

    /**
     * Puts {@code value} on top: one step.
     *
     * @throws IllegalStateException "stack is full" when there is no room
     */
    public void push(E value) {
        if (size == slots.length) {
            throw new IllegalStateException("stack is full: " + slots.length + " of " + slots.length);
        }
        slots[size++] = value;
        steps.step();
    }

    /**
     * Takes the top value off and returns it: one step.
     *
     * @throws IllegalStateException "stack is empty" when there is nothing to take
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        if (size == 0) {
            throw new IllegalStateException("stack is empty");
        }
        E top = (E) slots[--size];
        slots[size] = null;         // let the popped value be garbage-collected
        steps.step();
        return top;
    }

    /** The top value, left where it is: one step. */
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("stack is empty");
        }
        steps.step();
        return (E) slots[size - 1];
    }

    /** The values from the bottom up, "[a, b, c <- top]", for printing; not counted. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(slots[i]);
        }
        if (size > 0) {
            s.append(" <- top");
        }
        return s.append(']').toString();
    }
}
