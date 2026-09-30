/** The generic dynamic array's operations: the dynamic array's, moving elem_size bytes at a time. */
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

/** The address of element i. O(1). */
void *element_at(const GenericDynamicArray *a, int i) {
    return a->data + (size_t) i * a->elem_size;
}

/** A new block of new_capacity elements; the bytes of every element copied; the old block freed. O(n). */
static Status resize(GenericDynamicArray *a, int new_capacity) {
    unsigned char *new_data = malloc((size_t) (new_capacity > 0 ? new_capacity : 1) * a->elem_size);
    if (new_data == NULL) {
        return STATUS_NO_MEMORY;
    }
    for (int i = 0; i < a->size; i++) {
        memcpy(new_data + (size_t) i * a->elem_size, element_at(a, i), a->elem_size);
    }
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

void traverse(const GenericDynamicArray *a, PrintFn print) {
    printf("[");
    for (int i = 0; i < a->size; i++) {
        if (i > 0) {
            printf(", ");
        }
        print(element_at(a, i));
    }
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

/** Insertion at the end: copy elem into place size, doubling first when full. O(1) amortised. */
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

/** Insertion at pos: double when full, shift elements pos..size-1 right from the end, copy elem in. */
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
    for (int i = a->size - 1; i >= pos; i--) {
        memcpy(element_at(a, i + 1), element_at(a, i), a->elem_size);
    }
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

Status delete_at(GenericDynamicArray *a, int pos, void *deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    memcpy(deleted, element_at(a, pos), a->elem_size);
    for (int i = pos; i < a->size - 1; i++) {
        memcpy(element_at(a, i), element_at(a, i + 1), a->elem_size);
    }
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

/** Linear search: the first index whose element compares equal to key, or -1. O(n). */
int linear_search(const GenericDynamicArray *a, const void *key, CompareFn compare) {
    for (int i = 0; i < a->size; i++) {
        if (compare(element_at(a, i), key) == 0) {
            return i;
        }
    }
    return -1;
}

Status shrink_to_fit(GenericDynamicArray *a) {
    if (a->capacity > a->size) {
        return resize(a, a->size);
    }
    return STATUS_OK;
}
