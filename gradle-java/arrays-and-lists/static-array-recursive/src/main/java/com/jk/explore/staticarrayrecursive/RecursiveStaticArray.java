package com.jk.explore.staticarrayrecursive;

/**
 * A static (fixed-capacity) array of integers whose operations are written recursively.
 *
 * <p>The array {@code arr} has a fixed capacity and holds {@code n} elements in {@code arr[0]} to
 * {@code arr[n - 1]}, exactly as in the non-recursive static-array project. Every operation that
 * can be expressed recursively is: each solves the problem for one element (or one half) and calls
 * itself for the rest, stopping at a <em>base case</em>. Every call waits on the call stack until
 * the calls below it return, so a recursive operation uses O(depth) extra space: O(n) for traversal,
 * linear search, sum, maximum and shifting, O(n / 2) for reversal, and only O(log n) for binary
 * search. Access and update are a single address calculation and are not recursive.
 */
public class RecursiveStaticArray {

    /** Bytes used by one {@code int} element. */
    public static final int ELEMENT_BYTES = 4;

    private final int[] arr;
    private int n;
    private final StepCounter steps = new StepCounter();

    /**
     * An empty array of the given capacity.
     *
     * @throws IllegalArgumentException if {@code capacity} is negative
     */
    public RecursiveStaticArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must not be negative: " + capacity);
        }
        this.arr = new int[capacity];
    }

    /** An array of {@code capacity} places holding {@code values} at the front. */
    public static RecursiveStaticArray of(int capacity, int... values) {
        if (values.length > capacity) {
            throw new IllegalStateException("overflow: " + values.length + " values, capacity " + capacity);
        }
        RecursiveStaticArray a = new RecursiveStaticArray(capacity);
        for (int i = 0; i < values.length; i++) {
            a.arr[i] = values[i];
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
    public int get(int i) {
        checkIndex(i);
        steps.read();
        return arr[i];
    }

    /** Update: replaces the element at index {@code i}. O(1), not recursive. */
    public void update(int i, int value) {
        checkIndex(i);
        arr[i] = value;
        steps.write();
    }

    /**
     * Insertion at {@code pos}: shift arr[pos..n-1] right recursively, then store. The recursion
     * starts at the last element, so each element is moved before its place is overwritten.
     * O(n - pos) time and stack.
     *
     * @throws IllegalStateException "overflow" when the array is full
     */
    public void insertAt(int pos, int value) {
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
     * Deletion at {@code pos}: shift arr[pos+1..n-1] left recursively. O(n - pos) time and stack.
     *
     * @return the deleted element
     * @throws IllegalStateException "underflow" when the array is empty
     */
    public int deleteAt(int pos) {
        if (isEmpty()) {
            throw new IllegalStateException("underflow: the array is empty");
        }
        checkIndex(pos);
        int deleted = arr[pos];
        shiftLeft(pos);
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

    /** Linear search: is the key at i? If not, search from i + 1. O(n) time, O(n) stack. */
    public int linearSearch(int key) {
        return linearSearch(key, 0);
    }

    private int linearSearch(int key, int i) {
        if (i == n) {
            return -1;                 // base case: searched everything
        }
        steps.enter();
        steps.compare();
        int result = arr[i] == key ? i : linearSearch(key, i + 1);
        steps.exit();
        return result;
    }

    /** Binary search on a sorted array: search only the half that can hold the key. O(log n) time and stack. */
    public int binarySearch(int key) {
        return binarySearch(key, 0, n - 1);
    }

    private int binarySearch(int key, int low, int high) {
        if (low > high) {
            return -1;                 // base case: the range is empty
        }
        steps.enter();
        int mid = low + (high - low) / 2;
        steps.compare();
        int result;
        if (arr[mid] == key) {
            result = mid;
        } else if (arr[mid] < key) {
            result = binarySearch(key, mid + 1, high);
        } else {
            result = binarySearch(key, low, mid - 1);
        }
        steps.exit();
        return result;
    }

    /** The index of the largest element: the larger of arr[i] and the largest of the rest. O(n) time and stack. */
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
        int result = arr[i] >= arr[restMax] ? i : restMax;
        steps.exit();
        return result;
    }

    /** The sum: arr[i] plus the sum of the rest. O(n) time and stack. */
    public long sum() {
        return sum(0);
    }

    private long sum(int i) {
        if (i == n) {
            return 0;                  // base case: the sum of no elements is 0
        }
        steps.enter();
        steps.read();
        long result = arr[i] + sum(i + 1);
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
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        steps.swap();
        reverse(i + 1, j - 1);
        steps.exit();
    }

    /** A copy of the n elements, for printing and testing; not counted. */
    public int[] toArray() {
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds for length " + n);
        }
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
