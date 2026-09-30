package com.jk.explore.dynamicarraygenericrecursive;

/**
 * A dynamic array (a resizable array) of any element type {@code T}, whose operations are
 * written recursively.
 *
 * <p>The same structure as the dynamic-array project: a static array {@code arr} inside, holding
 * {@code size} elements in {@code arr[0]} to {@code arr[size - 1]}, replaced by one of twice the
 * capacity when full and halved when only a quarter is in use. Every loop of that project is written
 * here as a recursion with a base case: traversal, linear search, the shifts of insertion and
 * deletion, and the copy inside a resize. The time of each operation is unchanged; its extra space is
 * the recursion depth, one call-stack frame per element visited, shifted or copied.
 *
 * <p>As in dynamic-array-generic, the array holds references to objects of type {@code T}, created
 * as an {@code Object[]} cast to {@code T[]}; a resize copies references, and search uses
 * {@code equals}.
 *
 * @param <T> the element type
 */
public class GenericRecursiveDynamicArray<T> {

    /** The capacity a new, empty dynamic array starts with. */
    public static final int INITIAL_CAPACITY = 4;

    private T[] arr;
    private int size;
    private final StepCounter steps = new StepCounter();
    private int resizes;

    /** An empty dynamic array of capacity {@link #INITIAL_CAPACITY}. */
    public GenericRecursiveDynamicArray() {
        this.arr = newArray(INITIAL_CAPACITY);
    }

    /** Java cannot create "new T[n]" (T is erased at run time), so an Object[] is cast to T[]. */
    @SuppressWarnings("unchecked")
    private static <T> T[] newArray(int capacity) {
        return (T[]) new Object[capacity];
    }

    public int size() {
        return size;
    }

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

    /** Traversal: element i, then the rest. O(n) time, O(n) stack. */
    public String traverse() {
        StringBuilder s = new StringBuilder("[");
        traverse(0, s);
        return s.append(']').toString();
    }

    private void traverse(int i, StringBuilder s) {
        if (i == size) {               // base case: no elements left
            return;
        }
        steps.enter();
        steps.read();
        if (i > 0) {
            s.append(", ");
        }
        s.append(arr[i]);
        traverse(i + 1, s);
        steps.exit();
    }

    /** Access: the element at index {@code i}. O(1), not recursive. */
    public T get(int i) {
        checkIndex(i);
        steps.read();
        return arr[i];
    }

    /** Update: replaces the element at index {@code i}. O(1), not recursive. */
    public void update(int i, T value) {
        checkIndex(i);
        arr[i] = checkValue(value);
        steps.write();
    }

    /**
     * Insertion at the end: one write, after a recursive resize to double the capacity when full.
     * O(1) amortised; the resizing append costs O(n) time and O(n) stack.
     */
    public void append(T value) {
        checkValue(value);
        if (size == arr.length) {
            resize(arr.length == 0 ? 1 : arr.length * 2);
        }
        arr[size] = value;
        steps.write();
        size++;
    }

    /** Insertion at {@code pos}: resize when full, then shiftRight from the last element down to pos. */
    public void insertAt(int pos, T value) {
        checkValue(value);
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("position " + pos + " outside 0.." + size);
        }
        if (size == arr.length) {
            resize(arr.length == 0 ? 1 : arr.length * 2);
        }
        shiftRight(size - 1, pos);
        arr[pos] = value;
        steps.write();
        size++;
    }

    private void shiftRight(int i, int pos) {
        if (i < pos) {                 // base case: every element from pos onwards has moved
            return;
        }
        steps.enter();
        arr[i + 1] = arr[i];
        steps.shift();
        shiftRight(i - 1, pos);
        steps.exit();
    }

    /**
     * Deletion at the end: O(1), apart from the occasional recursive shrink.
     *
     * @throws IllegalStateException "underflow" when the array is empty
     */
    public T deleteAtEnd() {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        T deleted = arr[size - 1];
        arr[size - 1] = null;
        size--;
        shrinkIfQuarterFull();
        return deleted;
    }

    /**
     * Deletion at {@code pos}: shiftLeft from pos to the end, then clear the freed place.
     *
     * @throws IllegalStateException "underflow" when the array is empty
     */
    public T deleteAt(int pos) {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        checkIndex(pos);
        T deleted = arr[pos];
        shiftLeft(pos);
        arr[size - 1] = null;
        size--;
        shrinkIfQuarterFull();
        return deleted;
    }

    private void shiftLeft(int i) {
        if (i >= size - 1) {           // base case: reached the last element
            return;
        }
        steps.enter();
        arr[i] = arr[i + 1];
        steps.shift();
        shiftLeft(i + 1);
        steps.exit();
    }

    /** Linear search with {@code equals}: is the key at i? If not, search from i + 1. O(n) time, O(n) stack. */
    public int linearSearch(T key) {
        return linearSearch(key, 0);
    }

    private int linearSearch(T key, int i) {
        if (i == size) {
            return -1;                 // base case: searched everything
        }
        steps.enter();
        steps.compare();
        int result = arr[i].equals(key) ? i : linearSearch(key, i + 1);
        steps.exit();
        return result;
    }

    /** Shrinks the capacity to the size: a recursive copy of every element. */
    public void shrinkToFit() {
        if (arr.length > size) {
            resize(size);
        }
    }

    private void shrinkIfQuarterFull() {
        if (size > 0 && size == arr.length / 4) {
            resize(arr.length / 2);
        }
    }

    /** Allocates a new array and copies the references into it recursively. O(n) time and stack. */
    private void resize(int newCapacity) {
        T[] newArr = newArray(newCapacity);
        copy(0, newArr);
        arr = newArr;
        resizes++;
    }

    private void copy(int i, T[] newArr) {
        if (i == size) {               // base case: every element copied
            return;
        }
        steps.enter();
        newArr[i] = arr[i];
        steps.copy();
        copy(i + 1, newArr);
        steps.exit();
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds for length " + size);
        }
    }

    private static <T> T checkValue(T value) {
        if (value == null) {
            throw new IllegalArgumentException("elements must not be null");
        }
        return value;
    }

    /** True when index {@code i} holds the very object {@code o} (not merely an equal one). */
    public boolean holdsSameObject(int i, T o) {
        checkIndex(i);
        return arr[i] == o;
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
