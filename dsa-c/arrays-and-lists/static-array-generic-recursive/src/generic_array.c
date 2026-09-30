/** The generic array's operations, written recursively: each handles one element or one half. */
#include "generic_array.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int create(GenericArray *a, int capacity, size_t elem_size) {
    a->data = malloc((size_t) (capacity > 0 ? capacity : 1) * elem_size);
    a->elem_size = elem_size;
    a->capacity = capacity;
    a->n = 0;
    return a->data != NULL;
}

void destroy(GenericArray *a) {
    free(a->data);
    a->data = NULL;
    a->capacity = a->n = 0;
}

/** The address of element i: the start of the block plus i elements of elem_size bytes. O(1). */
void *element_at(const GenericArray *a, int i) {
    return a->data + (size_t) i * a->elem_size;
}

/** Print element i, then the rest from i + 1. */
static void traverse_from(const GenericArray *a, int i, PrintFn print) {
    if (i == a->n) {                /* base case: no elements left */
        return;
    }
    if (i > 0) {
        printf(", ");
    }
    print(element_at(a, i));
    traverse_from(a, i + 1, print);
}

/** Traversal, recursively. O(n) time and stack. */
void traverse(const GenericArray *a, PrintFn print) {
    printf("[");
    traverse_from(a, 0, print);
    printf("]\n");
}

/** Access: copies element i into out. O(1), not recursive. */
Status get(const GenericArray *a, int i, void *out) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(out, element_at(a, i), a->elem_size);
    return STATUS_OK;
}

/** Update: copies elem over element i. O(1), not recursive. */
Status update(GenericArray *a, int i, const void *elem) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(element_at(a, i), elem, a->elem_size);
    return STATUS_OK;
}

/** Moves element i one place right, then shifts the elements before it, down to pos. */
static void shift_right(GenericArray *a, int i, int pos) {
    if (i < pos) {                  /* base case: every element from pos onwards has moved */
        return;
    }
    memcpy(element_at(a, i + 1), element_at(a, i), a->elem_size);
    shift_right(a, i - 1, pos);
}

/** Insertion at pos: shift recursively from the last element, then copy elem in. O(n - pos) time and stack. */
Status insert_at(GenericArray *a, int pos, const void *elem) {
    if (a->n == a->capacity) {
        return STATUS_OVERFLOW;
    }
    if (pos < 0 || pos > a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    shift_right(a, a->n - 1, pos);
    memcpy(element_at(a, pos), elem, a->elem_size);
    a->n++;
    return STATUS_OK;
}

/** Moves element i + 1 into place i, then does the same from i + 1. */
static void shift_left(GenericArray *a, int i) {
    if (i >= a->n - 1) {            /* base case: reached the last element */
        return;
    }
    memcpy(element_at(a, i), element_at(a, i + 1), a->elem_size);
    shift_left(a, i + 1);
}

/** Deletion at pos: copy the element out, then shift left recursively. O(n - pos) time and stack. */
Status delete_at(GenericArray *a, int pos, void *deleted) {
    if (a->n == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(deleted, element_at(a, pos), a->elem_size);
    shift_left(a, pos);
    a->n--;
    return STATUS_OK;
}

/** Does element i compare equal to key? If not, search from i + 1. */
static int linear_search_from(const GenericArray *a, const void *key, CompareFn compare, int i) {
    if (i == a->n) {
        return -1;                  /* base case: searched everything */
    }
    if (compare(element_at(a, i), key) == 0) {
        return i;                   /* base case: found */
    }
    return linear_search_from(a, key, compare, i + 1);
}

/** Linear search, recursively. O(n) time and stack. */
int linear_search(const GenericArray *a, const void *key, CompareFn compare) {
    return linear_search_from(a, key, compare, 0);
}

/** Compare with the middle of the range, then search only the half that can hold key. */
static int binary_search_range(const GenericArray *a, const void *key, CompareFn compare, int low, int high) {
    if (low > high) {
        return -1;                  /* base case: the range is empty */
    }
    int mid = low + (high - low) / 2;
    int c = compare(element_at(a, mid), key);
    if (c == 0) {
        return mid;
    } else if (c < 0) {
        return binary_search_range(a, key, compare, mid + 1, high);
    } else {
        return binary_search_range(a, key, compare, low, mid - 1);
    }
}

/** Binary search on an array sorted by compare, recursively. O(log n) time and stack. */
int binary_search(const GenericArray *a, const void *key, CompareFn compare) {
    return binary_search_range(a, key, compare, 0, a->n - 1);
}

/** The index of the larger of element i and the largest of the rest. */
static int find_max_from(const GenericArray *a, CompareFn compare, int i) {
    if (i == a->n - 1) {
        return i;                   /* base case: one element is its own maximum */
    }
    int rest_max = find_max_from(a, compare, i + 1);
    return compare(element_at(a, i), element_at(a, rest_max)) >= 0 ? i : rest_max;
}

/** The index of the largest element by compare, compared on the way back up. O(n) time and stack. */
int find_max(const GenericArray *a, CompareFn compare) {
    return a->n == 0 ? -1 : find_max_from(a, compare, 0);
}

/** Swaps two elements byte by byte, so no temporary of the element's type is needed. */
static void swap_bytes(unsigned char *x, unsigned char *y, size_t size) {
    for (size_t k = 0; k < size; k++) {
        unsigned char t = x[k];
        x[k] = y[k];
        y[k] = t;
    }
}

/** Swap the two ends, then reverse what is between them. */
static void reverse_range(GenericArray *a, int i, int j) {
    if (i >= j) {
        return;                     /* base case: zero or one element in the middle */
    }
    swap_bytes(element_at(a, i), element_at(a, j), a->elem_size);
    reverse_range(a, i + 1, j - 1);
}

/** Reverses in place, recursively. O(n) time, O(n / 2) stack. */
void reverse(GenericArray *a) {
    reverse_range(a, 0, a->n - 1);
}
