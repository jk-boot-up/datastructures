package com.jk.explore.dynamicarrayrecursive;

/**
 * A dynamic array (a resizable array) of strings whose operations are written recursively.
 *
 * <p>The same structure as the dynamic-array project: a static array {@code arr} inside, holding
 * {@code size} elements in {@code arr[0]} to {@code arr[size - 1]}, replaced by one of twice the
 * capacity when full and halved when only a quarter is in use. Every loop of that project is written
 * here as a recursion with a base case: traversal, linear search, the shifts of insertion and
 * deletion, and the copy inside a resize. The time of each operation is unchanged; its extra space is
 * the recursion depth, one call-stack frame per element visited, shifted or copied.
 */
public class RecursiveDynamicArray {

    /** The capacity a new, empty dynamic array starts with. */
    public static final int INITIAL_CAPACITY = 4;

    private String[] arr;
    private int size;
    private final StepCounter steps = new StepCounter();
    private int resizes;

    /** An empty dynamic array of capacity {@link #INITIAL_CAPACITY}. */
    public RecursiveDynamicArray() {
        this.arr = new String[INITIAL_CAPACITY];
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
    public String get(int i) {
        checkIndex(i);
        steps.read();
        return arr[i];
    }

    /** Update: replaces the element at index {@code i}. O(1), not recursive. */
    public void update(int i, String value) {
        checkIndex(i);
        arr[i] = checkValue(value);
        steps.write();
    }

    /**
     * Insertion at the end: one write, after a recursive resize to double the capacity when full.
     * O(1) amortised; the resizing append costs O(n) time and O(n) stack.
     */
    public void append(String value) {
        checkValue(value);
        if (size == arr.length) {
            resize(arr.length == 0 ? 1 : arr.length * 2);
        }
        arr[size] = value;
        steps.write();
        size++;
    }

    /** Insertion at {@code pos}: resize when full, then shiftRight from the last element down to pos. */
    public void insertAt(int pos, String value) {
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
    public String deleteAtEnd() {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        String deleted = arr[size - 1];
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
    public String deleteAt(int pos) {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        checkIndex(pos);
        String deleted = arr[pos];
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

    /** Linear search: is the key at i? If not, search from i + 1. O(n) time, O(n) stack. */
    public int linearSearch(String key) {
        return linearSearch(key, 0);
    }

    private int linearSearch(String key, int i) {
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

    /** Allocates a new array and copies the elements into it recursively. O(n) time and stack. */
    private void resize(int newCapacity) {
        String[] newArr = new String[newCapacity];
        copy(0, newArr);
        arr = newArr;
        resizes++;
    }

    private void copy(int i, String[] newArr) {
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

    private static String checkValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("elements must not be null");
        }
        return value;
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
