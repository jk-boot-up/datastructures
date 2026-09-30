/** The generic dynamic array's operations, written recursively: one call per element. */
#include "generic_dynamic_array.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int create(GenericDynamicArray *a, size_t elem_size) {
    a->data = malloc(INITIAL_CAPACITY * elem_size);
    a->elem_size = elem_size;
    a->size = 0;
    a->capacity = INITIAL_CAPACITY;
    return a->data != NULL;
}

void destroy(GenericDynamicArray *a) {
    free(a->data);
    a->data = NULL;
    a->size = a->capacity = 0;
}

void *element_at(const GenericDynamicArray *a, int i) {
    return a->data + (size_t) i * a->elem_size;
}

/** Copies element i's bytes into new_data, then the rest from i + 1. */
static void copy_from(const GenericDynamicArray *a, unsigned char *new_data, int i) {
    if (i == a->size) {             /* base case: every element copied */
        return;
    }
    memcpy(new_data + (size_t) i * a->elem_size, element_at(a, i), a->elem_size);
    copy_from(a, new_data, i + 1);
}

/** A new block of new_capacity elements, copied recursively; the old block freed. O(n) time and stack. */
static Status resize(GenericDynamicArray *a, int new_capacity) {
    unsigned char *new_data = malloc((size_t) (new_capacity > 0 ? new_capacity : 1) * a->elem_size);
    if (new_data == NULL) {
        return STATUS_NO_MEMORY;
    }
    copy_from(a, new_data, 0);
    free(a->data);
    a->data = new_data;
    a->capacity = new_capacity;
    return STATUS_OK;
}

static void shrink_if_quarter_full(GenericDynamicArray *a) {
    if (a->size > 0 && a->size == a->capacity / 4) {
        resize(a, a->capacity / 2);
    }
}

static void traverse_from(const GenericDynamicArray *a, PrintFn print, int i) {
    if (i == a->size) {             /* base case: no elements left */
        return;
    }
    if (i > 0) {
        printf(", ");
    }
    print(element_at(a, i));
    traverse_from(a, print, i + 1);
}

/** Traversal, recursively. O(n) time and stack. */
void traverse(const GenericDynamicArray *a, PrintFn print) {
    printf("[");
    traverse_from(a, print, 0);
    printf("]\n");
}

Status get(const GenericDynamicArray *a, int i, void *out) {
    if (i < 0 || i >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(out, element_at(a, i), a->elem_size);
    return STATUS_OK;
}

Status update(GenericDynamicArray *a, int i, const void *elem) {
    if (i < 0 || i >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(element_at(a, i), elem, a->elem_size);
    return STATUS_OK;
}

/** Insertion at the end: memcpy into place size, after a recursive resize when full. */
Status append(GenericDynamicArray *a, const void *elem) {
    if (a->size == a->capacity) {
        Status s = resize(a, a->capacity == 0 ? 1 : a->capacity * 2);
        if (s != STATUS_OK) {
            return s;
        }
    }
    memcpy(element_at(a, a->size), elem, a->elem_size);
    a->size++;
    return STATUS_OK;
}

/** Moves element i one place right, then shifts the elements before it, down to pos. */
static void shift_right(GenericDynamicArray *a, int i, int pos) {
    if (i < pos) {                  /* base case */
        return;
    }
    memcpy(element_at(a, i + 1), element_at(a, i), a->elem_size);
    shift_right(a, i - 1, pos);
}

Status insert_at(GenericDynamicArray *a, int pos, const void *elem) {
    if (pos < 0 || pos > a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    if (a->size == a->capacity) {
        Status s = resize(a, a->capacity == 0 ? 1 : a->capacity * 2);
        if (s != STATUS_OK) {
            return s;
        }
    }
    shift_right(a, a->size - 1, pos);
    memcpy(element_at(a, pos), elem, a->elem_size);
    a->size++;
    return STATUS_OK;
}

Status delete_at_end(GenericDynamicArray *a, void *deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    memcpy(deleted, element_at(a, a->size - 1), a->elem_size);
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

/** Moves element i + 1 into place i, then does the same from i + 1. */
static void shift_left(GenericDynamicArray *a, int i) {
    if (i >= a->size - 1) {         /* base case: reached the last element */
        return;
    }
    memcpy(element_at(a, i), element_at(a, i + 1), a->elem_size);
    shift_left(a, i + 1);
}

Status delete_at(GenericDynamicArray *a, int pos, void *deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(deleted, element_at(a, pos), a->elem_size);
    shift_left(a, pos);
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

static int linear_search_from(const GenericDynamicArray *a, const void *key, CompareFn compare, int i) {
    if (i == a->size) {
        return -1;                  /* base case: searched everything */
    }
    if (compare(element_at(a, i), key) == 0) {
        return i;                   /* base case: found */
    }
    return linear_search_from(a, key, compare, i + 1);
}

/** Linear search with a comparison function, recursively. O(n) time and stack. */
int linear_search(const GenericDynamicArray *a, const void *key, CompareFn compare) {
    return linear_search_from(a, key, compare, 0);
}

Status shrink_to_fit(GenericDynamicArray *a) {
    if (a->capacity > a->size) {
        return resize(a, a->size);
    }
    return STATUS_OK;
}
