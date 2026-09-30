/** The static array's operations, each a loop using O(1) extra space. */
#include "static_array.h"

#include <stdio.h>
#include <stdlib.h>

/** Takes memory for capacity ints; the capacity never changes afterwards. */
int create(StaticArray *a, int capacity) {
    a->arr = malloc((size_t) (capacity > 0 ? capacity : 1) * sizeof(int));
    a->capacity = capacity;
    a->n = 0;
    return a->arr != NULL;
}

/** Gives the memory back. Every create is matched by a destroy. */
void destroy(StaticArray *a) {
    free(a->arr);
    a->arr = NULL;
    a->capacity = a->n = 0;
}

/** Traversal: visits arr[0] to arr[n - 1] once, printing each. O(n) time, O(1) space. */
void traverse(const StaticArray *a) {
    printf("[");
    for (int i = 0; i < a->n; i++) {
        printf(i > 0 ? ", %d" : "%d", a->arr[i]);
    }
    printf("]\n");
}

/** Access: the element at index i, by address calculation. O(1). */
Status get(const StaticArray *a, int i, int *value) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    *value = a->arr[i];
    return STATUS_OK;
}

/** Update: replaces the element at index i. O(1). */
Status update(StaticArray *a, int i, int value) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    a->arr[i] = value;
    return STATUS_OK;
}

/**
 * Insertion at position pos (0 <= pos <= n): shift arr[pos..n-1] one place right, starting from
 * the end, then store the value. n - pos shifts.
 */
Status insert_at(StaticArray *a, int pos, int value) {
    if (a->n == a->capacity) {
        return STATUS_OVERFLOW;
    }
    if (pos < 0 || pos > a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    for (int i = a->n - 1; i >= pos; i--) {
        a->arr[i + 1] = a->arr[i];
    }
    a->arr[pos] = value;
    a->n++;
    return STATUS_OK;
}

/** Deletion at position pos (0 <= pos < n): shift arr[pos+1..n-1] one place left. n - pos - 1 shifts. */
Status delete_at(StaticArray *a, int pos, int *deleted) {
    if (a->n == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    *deleted = a->arr[pos];
    for (int i = pos; i < a->n - 1; i++) {
        a->arr[i] = a->arr[i + 1];
    }
    a->n--;
    return STATUS_OK;
}

/** Linear search: the index of the first element equal to key, or -1. O(n), O(1) space. */
int linear_search(const StaticArray *a, int key) {
    for (int i = 0; i < a->n; i++) {
        if (a->arr[i] == key) {
            return i;
        }
    }
    return -1;
}

/**
 * Binary search, for an array sorted in ascending order: compare with the middle element and
 * discard the half that cannot hold key. O(log n) comparisons, O(1) space.
 */
int binary_search(const StaticArray *a, int key) {
    int low = 0;
    int high = a->n - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;     /* not (low + high) / 2, which can overflow */
        if (a->arr[mid] == key) {
            return mid;
        } else if (a->arr[mid] < key) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;
}

/** The index of the largest element (the first, on a tie), or -1 when empty. n - 1 comparisons. */
int find_max(const StaticArray *a) {
    if (a->n == 0) {
        return -1;
    }
    int max = 0;
    for (int i = 1; i < a->n; i++) {
        if (a->arr[i] > a->arr[max]) {
            max = i;
        }
    }
    return max;
}

/** The sum of the elements. O(n), O(1) space. */
long sum(const StaticArray *a) {
    long total = 0;
    for (int i = 0; i < a->n; i++) {
        total += a->arr[i];
    }
    return total;
}

/** Reverses the elements in place by swapping arr[i] and arr[n - 1 - i]: n / 2 swaps. */
void reverse(StaticArray *a) {
    for (int i = 0, j = a->n - 1; i < j; i++, j--) {
        int t = a->arr[i];
        a->arr[i] = a->arr[j];
        a->arr[j] = t;
    }
}

/**
 * Growing is impossible in place: a new block of new_capacity ints is allocated and every element
 * copied into it. O(n). Returns 1 on success, 0 if there is no memory.
 */
int copy_with_capacity(const StaticArray *a, StaticArray *bigger, int new_capacity) {
    if (!create(bigger, new_capacity)) {
        return 0;
    }
    int m = a->n < new_capacity ? a->n : new_capacity;
    for (int i = 0; i < m; i++) {
        bigger->arr[i] = a->arr[i];
    }
    bigger->n = m;
    return 1;
}
