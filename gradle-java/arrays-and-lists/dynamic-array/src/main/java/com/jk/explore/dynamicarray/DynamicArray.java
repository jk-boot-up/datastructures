package com.jk.explore.dynamicarray;

/**
 * A dynamic array (a resizable array) of strings: a static array inside, replaced by a bigger one
 * when it is full.
 *
 * <p>The array inside, {@code arr}, has a <em>capacity</em> (its length); the dynamic array has a
 * <em>size</em>, the number of elements in use, stored in {@code arr[0]} to {@code arr[size - 1]}.
 * Appending is one step while {@code size < capacity}. When the array is full, a new array of twice
 * the capacity is allocated and every element is copied across; doubling makes those copies rare,
 * so an append costs O(1) amortised. When deletions leave the array only a quarter full, it is
 * halved, so the spare memory stays in proportion to the size. Written for one element type, the
 * way a C textbook writes it; the dynamic-array-generic project holds any type.
 */
public class DynamicArray {

    /** The capacity a new, empty dynamic array starts with. */
    public static final int INITIAL_CAPACITY = 4;

    private String[] arr;
    private int size;
    private final boolean doubling;
    private final StepCounter steps = new StepCounter();
    private int resizes;

    /** An empty dynamic array that doubles when full, starting with {@link #INITIAL_CAPACITY}. */
    public DynamicArray() {
        this(INITIAL_CAPACITY, true);
    }

    /**
     * An empty dynamic array of the given capacity. With {@code doubling} false it grows by one
     * place at a time instead, the natural first idea and a costly one.
     *
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     */
    public DynamicArray(int initialCapacity, boolean doubling) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("capacity must not be negative: " + initialCapacity);
        }
        this.arr = new String[initialCapacity];
        this.doubling = doubling;
    }

    /** The number of elements in use. */
    public int size() {
        return size;
    }

    /** The length of the array inside: always at least {@link #size()}. */
    public int capacity() {
        return arr.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public StepCounter steps() {
        return steps;
    }

    /** How many times the array inside has been replaced. */
    public int resizes() {
        return resizes;
    }

    /** Traversal: visits arr[0] to arr[size - 1] once. O(n). */
    public String traverse() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            steps.read();
            if (i > 0) {
                s.append(", ");
            }
            s.append(arr[i]);
        }
        return s.append(']').toString();
    }

    /** Access: the element at index {@code i}. O(1). */
    public String get(int i) {
        checkIndex(i);
        steps.read();
        return arr[i];
    }

    /** Update: replaces the element at index {@code i}. O(1). */
    public void update(int i, String value) {
        checkIndex(i);
        arr[i] = value;
        steps.write();
    }

    /** Insertion at the end: one write, after a resize when full. O(1) amortised, O(n) worst case. */
    public void append(String value) {
        checkValue(value);
        if (size == arr.length) {
            resize(newCapacity());
        }
        arr[size] = value;
        steps.write();
        size++;
    }

    /**
     * Insertion at {@code pos}: resize when full, shift arr[pos..size-1] one place right, starting
     * from the end, then store the value. O(n - pos).
     */
    public void insertAt(int pos, String value) {
        checkValue(value);
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("position " + pos + " outside 0.." + size);
        }
        if (size == arr.length) {
            resize(newCapacity());
        }
        for (int i = size - 1; i >= pos; i--) {
            arr[i + 1] = arr[i];
            steps.shift();
        }
        arr[pos] = value;
        steps.write();
        size++;
    }

    /**
     * Deletion at the end: O(1), apart from the occasional shrink.
     *
     * @throws IllegalStateException "underflow" when the array is empty
     */
    public String deleteAtEnd() {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        String deleted = arr[size - 1];
        arr[size - 1] = null;          // clear the freed place so the string can be garbage-collected
        size--;
        shrinkIfQuarterFull();
        return deleted;
    }

    /**
     * Deletion at {@code pos}: shift arr[pos+1..size-1] one place left. O(n - pos).
     *
     * @throws IllegalStateException "underflow" when the array is empty
     */
    public String deleteAt(int pos) {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        checkIndex(pos);
        String deleted = arr[pos];
        for (int i = pos; i < size - 1; i++) {
            arr[i] = arr[i + 1];
            steps.shift();
        }
        arr[size - 1] = null;
        size--;
        shrinkIfQuarterFull();
        return deleted;
    }

    /** Linear search: the first index holding {@code key}, or -1. O(n). */
    public int linearSearch(String key) {
        for (int i = 0; i < size; i++) {
            steps.compare();
            if (arr[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /** Shrinks the capacity to the size, freeing every spare place: one copy per element. */
    public void shrinkToFit() {
        if (arr.length > size) {
            resize(size);
        }
    }

    private int newCapacity() {
        if (!doubling) {
            return arr.length + 1;
        }
        return arr.length == 0 ? 1 : arr.length * 2;
    }

    /** Halves the capacity when only a quarter is in use, so the next append cannot resize at once. */
    private void shrinkIfQuarterFull() {
        if (doubling && size > 0 && size == arr.length / 4) {
            resize(arr.length / 2);
        }
    }

    /** Allocates a new array of {@code newCapacity} places and copies the elements across. O(n). */
    private void resize(int newCapacity) {
        String[] newArr = new String[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
            steps.copy();
        }
        arr = newArr;
        resizes++;
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds for length " + size);
        }
    }

    private static void checkValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("elements must not be null");
        }
    }

    /** A copy of the elements in use, for printing and testing; not counted. */
    public String[] toArray() {
        String[] copy = new String[size];
        for (int i = 0; i < size; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(arr[i]);
        }
        return s.append(']').toString();
    }
}
