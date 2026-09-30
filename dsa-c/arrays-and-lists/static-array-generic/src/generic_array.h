/**
 * A static (fixed-capacity) array that holds elements of any type, the way C's own qsort and
 * bsearch handle any type: a block of bytes, an element size, and comparison functions.
 *
 * This is the static-array project with the element type left open. The array does not know what
 * it holds; it knows only that every element is elem_size bytes, so element i starts at
 * data + i x elem_size. Elements are copied in and out with memcpy, and anything that must
 * compare two elements is given a comparison function, int compare(const void *, const void *),
 * returning negative, zero or positive, as qsort expects. The price is type safety: through
 * void *, the compiler cannot check that the right type is passed.
 */
#ifndef GENERIC_ARRAY_H
#define GENERIC_ARRAY_H

#include <stddef.h>

typedef enum {
    STATUS_OK,
    STATUS_OVERFLOW,
    STATUS_UNDERFLOW,
    STATUS_OUT_OF_RANGE
} Status;

/** Compares two elements: negative if a comes first, zero if equal, positive if a comes after. */
typedef int (*CompareFn)(const void *a, const void *b);

/** Prints one element. */
typedef void (*PrintFn)(const void *elem);

typedef struct {
    unsigned char *data;    /* capacity x elem_size bytes */
    size_t elem_size;       /* bytes per element: sizeof(int), sizeof(struct reading), ... */
    int capacity;
    int n;
} GenericArray;

int   create(GenericArray *a, int capacity, size_t elem_size);
void  destroy(GenericArray *a);
void *element_at(const GenericArray *a, int i);       /* the address of element i */

void   traverse(const GenericArray *a, PrintFn print);   /* prints [x, y, ...] and a newline */
Status get(const GenericArray *a, int i, void *out);
Status update(GenericArray *a, int i, const void *elem);
Status insert_at(GenericArray *a, int pos, const void *elem);
Status delete_at(GenericArray *a, int pos, void *deleted);
int    linear_search(const GenericArray *a, const void *key, CompareFn compare);
int    binary_search(const GenericArray *a, const void *key, CompareFn compare);
int    find_max(const GenericArray *a, CompareFn compare);
void   reverse(GenericArray *a);
int    copy_with_capacity(const GenericArray *a, GenericArray *bigger, int new_capacity);

#endif
