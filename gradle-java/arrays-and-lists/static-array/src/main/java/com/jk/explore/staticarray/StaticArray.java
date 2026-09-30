package com.jk.explore.staticarray;

/**
 * A static (fixed-capacity) array of integers with the operations of a data-structures textbook,
 * written without recursion.
 *
 * <p>The array {@code arr} has a fixed capacity (its length, {@code MAX} in C textbooks) and holds
 * {@code n} elements in {@code arr[0]} to {@code arr[n - 1]}. Accessing {@code arr[i]} is O(1)
 * because the address is computed: base + i &times; element size. Inserting or deleting in the
 * middle shifts the later elements. The capacity never changes: inserting into a full array is an
 * overflow. Every operation here is a loop; the static-array-recursive project implements the
 * same operations recursively.
 */
public class StaticArray {

    /** Bytes used by one {@code int} element. */
    public static final int ELEMENT_BYTES = 4;

    protected final int[] arr;
    protected int n;
    protected final StepCounter steps = new StepCounter();

    /**
     * An empty array of the given capacity.
     *
     * @throws IllegalArgumentException if {@code capacity} is negative
     */
    public StaticArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must not be negative: " + capacity);
        }
        this.arr = new int[capacity];
    }

    /** An array of exactly {@code capacity} places holding {@code values} at the front. */
    public static StaticArray of(int capacity, int... values) {
        StaticArray a = new StaticArray(capacity);
        a.fill(values);
        return a;
    }

    void fill(int... values) {
        if (values.length > arr.length) {
            throw new IllegalStateException("overflow: " + values.length + " values, capacity " + arr.length);
        }
        for (int i = 0; i < values.length; i++) {
            arr[i] = values[i];
        }
        n = values.length;
    }

    /** The number of elements, n. */
    public int size() {
        return n;
    }

    /** The fixed capacity: the array's length. */
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

    /** Traversal: visits every element from index 0 to n - 1 and lists them. O(n) time, O(1) space. */
    public String traverse() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < n; i++) {
            steps.read();
            if (i > 0) {
                s.append(", ");
            }
            s.append(arr[i]);
        }
        return s.append(']').toString();
    }

    /** Access: the element at index {@code i}. O(1). */
    public int get(int i) {
        checkIndex(i);
        steps.read();
        return arr[i];
    }

    /** Update: replaces the element at index {@code i}. O(1). */
    public void update(int i, int value) {
        checkIndex(i);
        arr[i] = value;
        steps.write();
    }

    /**
     * Insertion at position {@code pos} (0 &lt;= pos &lt;= n): shifts {@code arr[pos..n-1]} one place
     * right, from the end backwards, then stores the value. O(n - pos) shifts.
     *
     * @throws IllegalStateException "overflow" when the array is full
     * @throws IndexOutOfBoundsException when pos is outside 0..n
     */
    public void insertAt(int pos, int value) {
        if (isFull()) {
            throw new IllegalStateException("overflow: the array is full (" + arr.length + " of " + arr.length + ")");
        }
        if (pos < 0 || pos > n) {
            throw new IndexOutOfBoundsException("position " + pos + " outside 0.." + n);
        }
        for (int i = n - 1; i >= pos; i--) {
            arr[i + 1] = arr[i];
            steps.shift();
        }
        arr[pos] = value;
        steps.write();
        n++;
    }

    /**
     * Deletion at position {@code pos} (0 &lt;= pos &lt; n): shifts {@code arr[pos+1..n-1]} one place
     * left. O(n - pos - 1) shifts.
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
        for (int i = pos; i < n - 1; i++) {
            arr[i] = arr[i + 1];
            steps.shift();
        }
        n--;
        return deleted;
    }

    /** Linear search: the index of the first element equal to {@code key}, or -1. O(n), O(1) space. */
    public int linearSearch(int key) {
        for (int i = 0; i < n; i++) {
            steps.compare();
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Binary search, for an array sorted in ascending order: compare with the middle element and
     * discard the half that cannot hold {@code key}. O(log n) comparisons, O(1) space.
     *
     * @return an index holding {@code key}, or -1
     */
    public int binarySearch(int key) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;    // not (low + high) / 2, which can overflow
            steps.compare();
            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /** The index of the largest element (the first, on a tie), or -1 when empty. O(n). */
    public int findMax() {
        if (n == 0) {
            return -1;
        }
        int max = 0;
        for (int i = 1; i < n; i++) {
            steps.compare();
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return max;
    }

    /** The sum of the elements. O(n), O(1) space. */
    public long sum() {
        long total = 0;
        for (int i = 0; i < n; i++) {
            steps.read();
            total += arr[i];
        }
        return total;
    }

    /** Reverses the elements in place by swapping arr[i] and arr[n - 1 - i]. O(n), O(1) space. */
    public void reverse() {
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            steps.swap();
        }
    }

    /**
     * Growing is impossible in place: a new array of {@code newCapacity} is allocated and every
     * element copied. O(n).
     */
    public StaticArray copyWithCapacity(int newCapacity) {
        StaticArray bigger = new StaticArray(newCapacity);
        int m = n < newCapacity ? n : newCapacity;
        for (int i = 0; i < m; i++) {
            bigger.arr[i] = arr[i];
            steps.copy();
        }
        bigger.n = m;
        return bigger;
    }

    /** The bytes the elements take: capacity &times; 4. */
    public int bytes() {
        return arr.length * ELEMENT_BYTES;
    }

    /** A copy of the n elements, for printing and testing; not counted. */
    public int[] toArray() {
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    protected void checkIndex(int i) {
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
