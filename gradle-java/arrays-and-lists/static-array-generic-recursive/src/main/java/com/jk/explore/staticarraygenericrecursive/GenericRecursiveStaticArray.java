package com.jk.explore.staticarraygenericrecursive;

/**
 * A static (fixed-capacity) array of any element type {@code T}, whose operations are written
 * recursively.
 *
 * <p>This combines the two changes made to the static-array project: the element type is a type
 * parameter, as in static-array-generic, so elements are compared with {@code equals} and
 * {@code compareTo}; and the operations are recursive, as in static-array-recursive, so each has a
 * base case and uses one call-stack frame per call still waiting.
 *
 * <p>The array {@code arr} has a fixed capacity and holds {@code n} elements in {@code arr[0]} to
 * {@code arr[n - 1]}, exactly as in the non-recursive static-array project. Every operation that
 * can be expressed recursively is: each solves the problem for one element (or one half) and calls
 * itself for the rest, stopping at a <em>base case</em>. Every call waits on the call stack until
 * the calls below it return, so a recursive operation uses O(depth) extra space: O(n) for traversal,
 * linear search, maximum and shifting, O(n / 2) for reversal, and only O(log n) for binary
 * search. Access and update are a single address calculation and are not recursive.
 */
public class GenericRecursiveStaticArray<T extends Comparable<T>> {

    private final T[] arr;
    private int n;
    private final StepCounter steps = new StepCounter();

    /**
     * An empty array of the given capacity.
     *
     * @throws IllegalArgumentException if {@code capacity} is negative
     */
    @SuppressWarnings("unchecked")
    public GenericRecursiveStaticArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must not be negative: " + capacity);
        }
        // "new T[capacity]" is not allowed (T is erased at run time); every T is a Comparable.
        this.arr = (T[]) new Comparable[capacity];
    }

    /** An array of {@code capacity} places holding {@code values} at the front. */
    @SafeVarargs
    public static <T extends Comparable<T>> GenericRecursiveStaticArray<T> of(int capacity, T... values) {
        if (values.length > capacity) {
            throw new IllegalStateException("overflow: " + values.length + " values, capacity " + capacity);
        }
        GenericRecursiveStaticArray<T> a = new GenericRecursiveStaticArray<>(capacity);
        for (int i = 0; i < values.length; i++) {
            a.arr[i] = checkValue(values[i]);
        }
        a.n = values.length;
        return a;
    }

    public int size() {
        return n;
    }

    public int capacity() {
        return arr.length;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean isFull() {
        return n == arr.length;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Traversal: element i, then the rest from i + 1. O(n) time, O(n) stack. */
    public String traverse() {
        StringBuilder s = new StringBuilder("[");
        traverse(0, s);
        return s.append(']').toString();
    }

    private void traverse(int i, StringBuilder s) {
        if (i == n) {                  // base case: no elements left
            return;
        }
        steps.enter();
        steps.read();
        if (i > 0) {
            s.append(", ");
        }
        s.append(arr[i]);
        traverse(i + 1, s);            // recursive case: the rest of the array
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
     * Insertion at {@code pos}: shift arr[pos..n-1] right recursively, then store. The recursion
     * starts at the last element, so each element is moved before its place is overwritten.
     * O(n - pos) time and stack.
     *
     * @throws IllegalStateException "overflow" when the array is full
     */
    public void insertAt(int pos, T value) {
        checkValue(value);
        if (isFull()) {
            throw new IllegalStateException("overflow: the array is full (" + arr.length + " of " + arr.length + ")");
        }
        if (pos < 0 || pos > n) {
            throw new IndexOutOfBoundsException("position " + pos + " outside 0.." + n);
        }
        shiftRight(n - 1, pos);
        arr[pos] = value;
        steps.write();
        n++;
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
     * Deletion at {@code pos}: shift arr[pos+1..n-1] left recursively, then clear the freed place.
     * O(n - pos) time and stack.
     *
     * @return the deleted element
     * @throws IllegalStateException "underflow" when the array is empty
     */
    public T deleteAt(int pos) {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        checkIndex(pos);
        T deleted = arr[pos];
        shiftLeft(pos);
        arr[n - 1] = null;             // no reference left behind, so the object can be freed
        n--;
        return deleted;
    }

    private void shiftLeft(int i) {
        if (i >= n - 1) {              // base case: reached the last element
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
        if (i == n) {
            return -1;                 // base case: searched everything
        }
        steps.enter();
        steps.compare();
        int result = arr[i].equals(key) ? i : linearSearch(key, i + 1);
        steps.exit();
        return result;
    }

    /** Binary search with {@code compareTo} on a sorted array: search only the half that can hold the key. O(log n) time and stack. */
    public int binarySearch(T key) {
        return binarySearch(key, 0, n - 1);
    }

    private int binarySearch(T key, int low, int high) {
        if (low > high) {
            return -1;                 // base case: the range is empty
        }
        steps.enter();
        int mid = low + (high - low) / 2;
        steps.compare();
        int c = arr[mid].compareTo(key);   // negative: arr[mid] < key; zero: equal; positive: >
        int result;
        if (c == 0) {
            result = mid;
        } else if (c < 0) {
            result = binarySearch(key, mid + 1, high);
        } else {
            result = binarySearch(key, low, mid - 1);
        }
        steps.exit();
        return result;
    }

    /** The index of the largest element by {@code compareTo}: the larger of arr[i] and the largest of the rest. O(n) time and stack. */
    public int findMax() {
        return n == 0 ? -1 : findMax(0);
    }

    private int findMax(int i) {
        if (i == n - 1) {
            return i;                  // base case: one element is its own maximum
        }
        steps.enter();
        int restMax = findMax(i + 1);
        steps.compare();
        int result = arr[i].compareTo(arr[restMax]) >= 0 ? i : restMax;
        steps.exit();
        return result;
    }

    /** Reverses in place: swap the two ends, then reverse what is between them. O(n) time, O(n / 2) stack. */
    public void reverse() {
        reverse(0, n - 1);
    }

    private void reverse(int i, int j) {
        if (i >= j) {
            return;                    // base case: zero or one element in the middle
        }
        steps.enter();
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        steps.swap();
        reverse(i + 1, j - 1);
        steps.exit();
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds for length " + n);
        }
    }

    private static <T> T checkValue(T value) {
        if (value == null) {
            throw new IllegalArgumentException("elements must not be null");
        }
        return value;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(arr[i]);
        }
        return s.append(']').toString();
    }
}
