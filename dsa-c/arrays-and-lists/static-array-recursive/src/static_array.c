/** The static array's operations, written recursively, each with a base case. */
#include "static_array.h"

#include <stdio.h>
#include <stdlib.h>

int create(StaticArray *a, int capacity) {
    a->arr = malloc((size_t) (capacity > 0 ? capacity : 1) * sizeof(int));
    a->capacity = capacity;
    a->n = 0;
    return a->arr != NULL;
}

void destroy(StaticArray *a) {
    free(a->arr);
    a->arr = NULL;
    a->capacity = a->n = 0;
}

/** Traversal: print element i, then traverse the rest from i + 1. O(n) time, O(n) stack. */
static void traverse_from(const StaticArray *a, int i) {
    if (i == a->n) {                /* base case: no elements left */
        return;
    }
    printf(i > 0 ? ", %d" : "%d", a->arr[i]);
    traverse_from(a, i + 1);        /* recursive case: the rest of the array */
}

void traverse(const StaticArray *a) {
    printf("[");
    traverse_from(a, 0);
    printf("]\n");
}

/** Access: the element at index i. O(1), not recursive. */
Status get(const StaticArray *a, int i, int *value) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    *value = a->arr[i];
    return STATUS_OK;
}

/** Update: replaces the element at index i. O(1), not recursive. */
Status update(StaticArray *a, int i, int value) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    a->arr[i] = value;
    return STATUS_OK;
}

/** Moves arr[i] one place right, then shifts the elements before it, down to pos. */
static void shift_right(StaticArray *a, int i, int pos) {
    if (i < pos) {                  /* base case: every element from pos onwards has moved */
        return;
    }
    a->arr[i + 1] = a->arr[i];
    shift_right(a, i - 1, pos);
}

/**
 * Insertion at pos: shift arr[pos..n-1] right recursively, starting at the last element, then
 * store the value. O(n - pos) time and stack.
 */
Status insert_at(StaticArray *a, int pos, int value) {
    if (a->n == a->capacity) {
        return STATUS_OVERFLOW;
    }
    if (pos < 0 || pos > a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    shift_right(a, a->n - 1, pos);
    a->arr[pos] = value;
    a->n++;
    return STATUS_OK;
}

/** Moves arr[i + 1] into arr[i], then does the same from i + 1. */
static void shift_left(StaticArray *a, int i) {
    if (i >= a->n - 1) {            /* base case: reached the last element */
        return;
    }
    a->arr[i] = a->arr[i + 1];
    shift_left(a, i + 1);
}

/** Deletion at pos: shift arr[pos+1..n-1] left recursively. O(n - pos) time and stack. */
Status delete_at(StaticArray *a, int pos, int *deleted) {
    if (a->n == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    *deleted = a->arr[pos];
    shift_left(a, pos);
    a->n--;
    return STATUS_OK;
}

/** Is the key at i? If not, search from i + 1. */
static int linear_search_from(const StaticArray *a, int key, int i) {
    if (i == a->n) {
        return -1;                  /* base case: searched everything */
    }
    int result = a->arr[i] == key ? i : linear_search_from(a, key, i + 1);
    return result;
}

/** Linear search, recursively. O(n) time, O(n) stack. */
int linear_search(const StaticArray *a, int key) {
    return linear_search_from(a, key, 0);
}

/** Compare with the middle of arr[low..high], then search only the half that can hold the key. */
static int binary_search_range(const StaticArray *a, int key, int low, int high) {
    if (low > high) {
        return -1;                  /* base case: the range is empty */
    }
    int mid = low + (high - low) / 2;
    int result;
    if (a->arr[mid] == key) {
        result = mid;
    } else if (a->arr[mid] < key) {
        result = binary_search_range(a, key, mid + 1, high);
    } else {
        result = binary_search_range(a, key, low, mid - 1);
    }
    return result;
}

/** Binary search on a sorted array, recursively. O(log n) time and stack. */
int binary_search(const StaticArray *a, int key) {
    return binary_search_range(a, key, 0, a->n - 1);
}

/** The index of the larger of arr[i] and the largest of the rest. */
static int find_max_from(const StaticArray *a, int i) {
    if (i == a->n - 1) {
        return i;                   /* base case: one element is its own maximum */
    }
    int rest_max = find_max_from(a, i + 1);
    int result = a->arr[i] >= a->arr[rest_max] ? i : rest_max;
    return result;
}

/** The index of the largest element, compared on the way back up. O(n) time and stack. */
int find_max(const StaticArray *a) {
    return a->n == 0 ? -1 : find_max_from(a, 0);
}

/** arr[i] plus the sum of the rest; the sum of no elements is 0. */
static long sum_from(const StaticArray *a, int i) {
    if (i == a->n) {
        return 0;                   /* base case */
    }
    long result = a->arr[i] + sum_from(a, i + 1);
    return result;
}

/** The sum, recursively. O(n) time and stack. */
long sum(const StaticArray *a) {
    return sum_from(a, 0);
}

/** Swap the two ends, then reverse what is between them. */
static void reverse_range(StaticArray *a, int i, int j) {
    if (i >= j) {
        return;                     /* base case: zero or one element in the middle */
    }
    int t = a->arr[i];
    a->arr[i] = a->arr[j];
    a->arr[j] = t;
    reverse_range(a, i + 1, j - 1);
}

/** Reverses in place, recursively. O(n) time, O(n / 2) stack. */
void reverse(StaticArray *a) {
    reverse_range(a, 0, a->n - 1);
}
