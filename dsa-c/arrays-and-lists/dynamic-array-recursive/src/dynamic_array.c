/** The dynamic array's operations, written recursively: each loop has become a function calling itself. */
#include "dynamic_array.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int create(DynamicArray *a, int initial_capacity) {
    a->arr = malloc((size_t) (initial_capacity > 0 ? initial_capacity : 1) * sizeof(const char *));
    a->size = 0;
    a->capacity = initial_capacity;
    return a->arr != NULL;
}

void destroy(DynamicArray *a) {
    free(a->arr);
    a->arr = NULL;
    a->size = a->capacity = 0;
}

/** Copies element i into new_arr, then the rest from i + 1. */
static void copy_from(const DynamicArray *a, const char **new_arr, int i) {
    if (i == a->size) {             /* base case: every element copied */
        return;
    }
    new_arr[i] = a->arr[i];
    copy_from(a, new_arr, i + 1);
}

/** Replaces arr with a new array of new_capacity places, copied recursively. O(n) time and stack. */
static Status resize(DynamicArray *a, int new_capacity) {
    const char **new_arr = malloc((size_t) (new_capacity > 0 ? new_capacity : 1) * sizeof(const char *));
    if (new_arr == NULL) {
        return STATUS_NO_MEMORY;
    }
    copy_from(a, new_arr, 0);
    free(a->arr);
    a->arr = new_arr;
    a->capacity = new_capacity;
    return STATUS_OK;
}

/** Halves the capacity when only a quarter is in use. */
static void shrink_if_quarter_full(DynamicArray *a) {
    if (a->size > 0 && a->size == a->capacity / 4) {
        resize(a, a->capacity / 2);
    }
}

/** Print element i, then the rest. */
static void traverse_from(const DynamicArray *a, int i) {
    if (i == a->size) {             /* base case: no elements left */
        return;
    }
    printf(i > 0 ? ", %s" : "%s", a->arr[i]);
    traverse_from(a, i + 1);
}

/** Traversal, recursively. O(n) time and stack. */
void traverse(const DynamicArray *a) {
    printf("[");
    traverse_from(a, 0);
    printf("]\n");
}

/** Access: the element at index i. O(1), not recursive. */
Status get(const DynamicArray *a, int i, const char **value) {
    if (i < 0 || i >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    *value = a->arr[i];
    return STATUS_OK;
}

/** Update: replaces the element at index i. O(1), not recursive. */
Status update(DynamicArray *a, int i, const char *value) {
    if (i < 0 || i >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    a->arr[i] = value;
    return STATUS_OK;
}

/** Insertion at the end: one write, after a recursive resize when full. O(1) amortised. */
Status append(DynamicArray *a, const char *value) {
    if (a->size == a->capacity) {
        Status s = resize(a, a->capacity == 0 ? 1 : a->capacity * 2);
        if (s != STATUS_OK) {
            return s;
        }
    }
    a->arr[a->size] = value;
    a->size++;
    return STATUS_OK;
}

/** Moves element i one place right, then shifts the elements before it, down to pos. */
static void shift_right(DynamicArray *a, int i, int pos) {
    if (i < pos) {                  /* base case: every element from pos onwards has moved */
        return;
    }
    a->arr[i + 1] = a->arr[i];
    shift_right(a, i - 1, pos);
}

/** Insertion at pos: resize when full, then shift_right from the last element down to pos. */
Status insert_at(DynamicArray *a, int pos, const char *value) {
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
    a->arr[pos] = value;
    a->size++;
    return STATUS_OK;
}

/** Deletion at the end: O(1), apart from the occasional recursive shrink. */
Status delete_at_end(DynamicArray *a, const char **deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    *deleted = a->arr[a->size - 1];
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

/** Moves element i + 1 into place i, then does the same from i + 1. */
static void shift_left(DynamicArray *a, int i) {
    if (i >= a->size - 1) {         /* base case: reached the last element */
        return;
    }
    a->arr[i] = a->arr[i + 1];
    shift_left(a, i + 1);
}

/** Deletion at pos: shift_left from pos. O(n - pos) time and stack. */
Status delete_at(DynamicArray *a, int pos, const char **deleted) {
    if (a->size == 0) {
        return STATUS_UNDERFLOW;
    }
    if (pos < 0 || pos >= a->size) {
        return STATUS_OUT_OF_RANGE;
    }
    *deleted = a->arr[pos];
    shift_left(a, pos);
    a->size--;
    shrink_if_quarter_full(a);
    return STATUS_OK;
}

/** Is the key at i? If not, search from i + 1. */
static int linear_search_from(const DynamicArray *a, const char *key, int i) {
    if (i == a->size) {
        return -1;                  /* base case: searched everything */
    }
    if (strcmp(a->arr[i], key) == 0) {
        return i;                   /* base case: found */
    }
    return linear_search_from(a, key, i + 1);
}

/** Linear search, recursively. O(n) time and stack. */
int linear_search(const DynamicArray *a, const char *key) {
    return linear_search_from(a, key, 0);
}

/** Shrinks the capacity to the size: a recursive copy of every element. */
Status shrink_to_fit(DynamicArray *a) {
    if (a->capacity > a->size) {
        return resize(a, a->size);
    }
    return STATUS_OK;
}
