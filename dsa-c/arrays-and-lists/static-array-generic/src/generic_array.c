/** The generic array's operations: the static-array loops, moving elem_size bytes at a time. */
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

/** Traversal: prints every element from index 0 to n - 1 with the given print function. O(n). */
void traverse(const GenericArray *a, PrintFn print) {
    printf("[");
    for (int i = 0; i < a->n; i++) {
        if (i > 0) {
            printf(", ");
        }
        print(element_at(a, i));
    }
    printf("]\n");
}

/** Access: copies element i into out. O(1). */
Status get(const GenericArray *a, int i, void *out) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(out, element_at(a, i), a->elem_size);
    return STATUS_OK;
}

/** Update: copies elem over element i. O(1). */
Status update(GenericArray *a, int i, const void *elem) {
    if (i < 0 || i >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(element_at(a, i), elem, a->elem_size);
    return STATUS_OK;
}

/** Insertion at pos: shift elements pos..n-1 one place right, from the end, then copy elem in. O(n - pos). */
Status insert_at(GenericArray *a, int pos, const void *elem) {
    if (a->n == a->capacity) {
        return STATUS_OVERFLOW;
    }
    if (pos < 0 || pos > a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    for (int i = a->n - 1; i >= pos; i--) {
        memcpy(element_at(a, i + 1), element_at(a, i), a->elem_size);
    }
    memcpy(element_at(a, pos), elem, a->elem_size);
    a->n++;
    return STATUS_OK;
}

/** Deletion at pos: copy the element out, then shift the later elements one place left. O(n - pos). */
Status delete_at(GenericArray *a, int pos, void *deleted) {
    if (a->n == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->n) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(deleted, element_at(a, pos), a->elem_size);
    for (int i = pos; i < a->n - 1; i++) {
        memcpy(element_at(a, i), element_at(a, i + 1), a->elem_size);
    }
    a->n--;
    return STATUS_OK;
}

/** Linear search: the first index whose element compares equal to key, or -1. O(n). */
int linear_search(const GenericArray *a, const void *key, CompareFn compare) {
    for (int i = 0; i < a->n; i++) {
        if (compare(element_at(a, i), key) == 0) {
            return i;
        }
    }
    return -1;
}

/** Binary search on an array sorted by compare: discard the half that cannot hold key. O(log n). */
int binary_search(const GenericArray *a, const void *key, CompareFn compare) {
    int low = 0;
    int high = a->n - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        int c = compare(element_at(a, mid), key);   /* negative: middle < key; zero: equal; positive: > */
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

/** The index of the largest element by compare (the first, on a tie), or -1 when empty. O(n). */
int find_max(const GenericArray *a, CompareFn compare) {
    if (a->n == 0) {
        return -1;
    }
    int max = 0;
    for (int i = 1; i < a->n; i++) {
        if (compare(element_at(a, i), element_at(a, max)) > 0) {
            max = i;
        }
    }
    return max;
}

/** Swaps two elements byte by byte, so no temporary of the element's type is needed. */
static void swap_bytes(unsigned char *x, unsigned char *y, size_t size) {
    for (size_t k = 0; k < size; k++) {
        unsigned char t = x[k];
        x[k] = y[k];
        y[k] = t;
    }
}

/** Reverses the elements in place, swapping from both ends inward. O(n), O(1) space. */
void reverse(GenericArray *a) {
    for (int i = 0, j = a->n - 1; i < j; i++, j--) {
        swap_bytes(element_at(a, i), element_at(a, j), a->elem_size);
    }
}

/** A new block of new_capacity elements, with every element's bytes copied across. O(n). */
int copy_with_capacity(const GenericArray *a, GenericArray *bigger, int new_capacity) {
    if (!create(bigger, new_capacity, a->elem_size)) {
        return 0;
    }
    int m = a->n < new_capacity ? a->n : new_capacity;
    for (int i = 0; i < m; i++) {
        memcpy(element_at(bigger, i), element_at(a, i), a->elem_size);
    }
    bigger->n = m;
    return 1;
}
