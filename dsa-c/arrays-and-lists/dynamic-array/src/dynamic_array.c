/** The dynamic array's operations: a static array's loops, plus resizing when full or a quarter full. */
#include "dynamic_array.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int create(DynamicArray *a, int initial_capacity, int doubling) {
    a->arr = malloc((size_t) (initial_capacity > 0 ? initial_capacity : 1) * sizeof(const char *));
    a->size = 0;
    a->capacity = initial_capacity;
    a->doubling = doubling;
    return a->arr != NULL;
}

void destroy(DynamicArray *a) {
    free(a->arr);
    a->arr = NULL;
    a->size = a->capacity = 0;
}

/**
 * Replaces arr with a new array of new_capacity places, copying the elements across one by one,
 * then frees the old one. O(n). (realloc does the same job, and can sometimes grow in place; the
 * loop is written out here so the copying can be seen.)
 */
static Status resize(DynamicArray *a, int new_capacity) {
    const char **new_arr = malloc((size_t) (new_capacity > 0 ? new_capacity : 1) * sizeof(const char *));
    if (new_arr == NULL) {
        return STATUS_NO_MEMORY;
    }
    for (int i = 0; i < a->size; i++) {
        new_arr[i] = a->arr[i];
    }
    free(a->arr);
    a->arr = new_arr;
    a->capacity = new_capacity;
    return STATUS_OK;
}

/** The capacity to grow to when full: double it, or (the costly way) one more place. */
static int new_capacity(const DynamicArray *a) {
    if (!a->doubling) {
        return a->capacity + 1;
    }
    return a->capacity == 0 ? 1 : a->capacity * 2;
}

/** Halves the capacity when only a quarter is in use, so the next append cannot resize at once. */
static void shrink_if_quarter_full(DynamicArray *a) {
    if (a->doubling && a->size > 0 && a->size == a->capacity / 4) {
        resize(a, a->capacity / 2);
    }
}

/** Traversal: visits arr[0] to arr[size - 1] once, printing each. O(n). */
void traverse(const DynamicArray *a) {
    printf("[");
    for (int i = 0; i < a->size; i++) {
        printf(i > 0 ? ", %s" : "%s", a->arr[i]);
    }
    printf("]\n");
}

/** Access: the element at index i. O(1). */
Status get(const DynamicArray *a, int i, const char **value) {
    if (i < 0 || i >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    *value = a->arr[i];
    return STATUS_OK;
}

/** Update: replaces the element at index i. O(1). */
Status update(DynamicArray *a, int i, const char *value) {
    if (i < 0 || i >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    a->arr[i] = value;
    return STATUS_OK;
}

/** Insertion at the end: one write, after a resize when full. O(1) amortised, O(n) worst case. */
Status append(DynamicArray *a, const char *value) {
    if (a->size == a->capacity) {
        Status s = resize(a, new_capacity(a));
        if (s != STATUS_OK) {
            return s;
        }
    }
    a->arr[a->size] = value;
    a->size++;
    return STATUS_OK;
}

/** Insertion at pos: resize when full, shift arr[pos..size-1] right from the end, store. O(n - pos). */
Status insert_at(DynamicArray *a, int pos, const char *value) {
    if (pos < 0 || pos > a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    if (a->size == a->capacity) {
        Status s = resize(a, new_capacity(a));
        if (s != STATUS_OK) {
            return s;
        }
    }
    for (int i = a->size - 1; i >= pos; i--) {
        a->arr[i + 1] = a->arr[i];
    }
    a->arr[pos] = value;
    a->size++;
    return STATUS_OK;
}

/** Deletion at the end: O(1), apart from the occasional shrink. */
Status delete_at_end(DynamicArray *a, const char **deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    *deleted = a->arr[a->size - 1];
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

/** Deletion at pos: shift arr[pos+1..size-1] one place left. O(n - pos). */
Status delete_at(DynamicArray *a, int pos, const char **deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    *deleted = a->arr[pos];
    for (int i = pos; i < a->size - 1; i++) {
        a->arr[i] = a->arr[i + 1];
    }
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

/** Linear search: the first index whose title equals key (compared with strcmp), or -1. O(n). */
int linear_search(const DynamicArray *a, const char *key) {
    for (int i = 0; i < a->size; i++) {
        if (strcmp(a->arr[i], key) == 0) {
            return i;
        }
    }
    return -1;
}

/** Shrinks the capacity to the size, freeing every spare place: one copy per element. */
Status shrink_to_fit(DynamicArray *a) {
    if (a->capacity > a->size) {
        return resize(a, a->size);
    }
    return STATUS_OK;
}
