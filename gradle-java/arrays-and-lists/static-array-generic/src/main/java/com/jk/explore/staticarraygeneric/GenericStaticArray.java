package com.jk.explore.staticarraygeneric;

/**
 * A static (fixed-capacity) array that holds elements of any type {@code T}, with the operations
 * of a data-structures textbook, written with loops.
 *
 * <p>This is the static-array project with one change: the element type is a <em>type parameter</em>
 * instead of {@code int}. {@code GenericStaticArray<Integer>} holds integers,
 * {@code GenericStaticArray<String>} holds strings, and the compiler checks that only that type goes
 * in. The array stores <em>references</em> to objects, so equality is tested with {@code equals}
 * and order with {@code compareTo}: {@code T extends Comparable<T>} means "any type whose elements
 * can be put in order", which binary search and finding the maximum need.
 *
 * @param <T> the element type
 */
public class GenericStaticArray<T extends Comparable<T>> {

    private final T[] arr;
    private int n;
    private final StepCounter steps = new StepCounter();

    /**
     * An empty array of the given capacity.
     *
     * @throws IllegalArgumentException if {@code capacity} is negative
     */
    @SuppressWarnings("unchecked")
    public GenericStaticArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must not be negative: " + capacity);
        }
        // Java cannot create "new T[capacity]": T is erased when the program runs. Every T is a
        // Comparable, so an array of Comparable can hold them, and the cast is safe because only
        // T is ever stored.
        this.arr = (T[]) new Comparable[capacity];
    }

    /** An array of {@code capacity} places holding {@code values} at the front. */
    @SafeVarargs
    public static <T extends Comparable<T>> GenericStaticArray<T> of(int capacity, T... values) {
        if (values.length > capacity) {
            throw new IllegalStateException("overflow: " + values.length + " values, capacity " + capacity);
        }
        GenericStaticArray<T> a = new GenericStaticArray<>(capacity);
        for (int i = 0; i < values.length; i++) {
            a.arr[i] = checkValue(values[i]);
        }
        a.n = values.length;
        return a;
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
    public T get(int i) {
        checkIndex(i);
        steps.read();
        return arr[i];
    }

    /** Update: replaces the element at index {@code i}. O(1). */
    public void update(int i, T value) {
        checkIndex(i);
        arr[i] = checkValue(value);
        steps.write();
    }

    /**
     * Insertion at position {@code pos} (0 &lt;= pos &lt;= n): shifts {@code arr[pos..n-1]} one place
     * right, from the end backwards, then stores the value. O(n - pos) shifts.
     *
     * @throws IllegalStateException "overflow" when the array is full
     * @throws IndexOutOfBoundsException when pos is outside 0..n
     */
    public void insertAt(int pos, T value) {
        checkValue(value);
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
     * left, then clears the freed place. O(n - pos - 1) shifts.
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
        for (int i = pos; i < n - 1; i++) {
            arr[i] = arr[i + 1];
            steps.shift();
        }
        arr[n - 1] = null;    // no reference left behind, so the object can be garbage-collected
        n--;
        return deleted;
    }

    /**
     * Linear search: the index of the first element equal to {@code key}, or -1. Uses
     * {@code equals}, which compares contents; {@code ==} would compare references. O(n), O(1) space.
     */
    public int linearSearch(T key) {
        for (int i = 0; i < n; i++) {
            steps.compare();
            if (arr[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Binary search, for an array sorted in ascending order by {@code compareTo}: compare with the
     * middle element and discard the half that cannot hold {@code key}. O(log n), O(1) space.
     *
     * @return an index holding {@code key}, or -1
     */
    public int binarySearch(T key) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;    // not (low + high) / 2, which can overflow
            steps.compare();
            int c = arr[mid].compareTo(key);     // negative: arr[mid] < key; zero: equal; positive: >
            if (c == 0) {
                return mid;
            } else if (c < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /** The index of the largest element by {@code compareTo} (the first, on a tie), or -1 when empty. O(n). */
    public int findMax() {
        if (n == 0) {
            return -1;
        }
        int max = 0;
        for (int i = 1; i < n; i++) {
            steps.compare();
            if (arr[i].compareTo(arr[max]) > 0) {
                max = i;
            }
        }
        return max;
    }

    /** Reverses the elements in place by swapping arr[i] and arr[n - 1 - i]. O(n), O(1) space. */
    public void reverse() {
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            T t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            steps.swap();
        }
    }

    /**
     * Growing is impossible in place: a new array of {@code newCapacity} is allocated and every
     * reference copied. The objects themselves are not copied; both arrays point at the same ones. O(n).
     */
    public GenericStaticArray<T> copyWithCapacity(int newCapacity) {
        GenericStaticArray<T> bigger = new GenericStaticArray<>(newCapacity);
        int m = n < newCapacity ? n : newCapacity;
        for (int i = 0; i < m; i++) {
            bigger.arr[i] = arr[i];
            steps.copy();
        }
        bigger.n = m;
        return bigger;
    }

    /** True when index {@code i} of both arrays refers to the very same object (not just an equal one). */
    public boolean sameObjectAt(int i, GenericStaticArray<T> other) {
        return arr[i] == other.arr[i];
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
