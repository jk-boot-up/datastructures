/**
 * A dynamic array (a resizable array) of any element type: a block of bytes with an element size,
 * replaced by a block twice as big when it is full.
 *
 * This is the dynamic-array project with the element type left open, the way C's standard library
 * handles any type: element i starts at data + i x elem_size, elements are copied with memcpy, and
 * comparisons go through a comparison function. It doubles when full and halves when a quarter
 * full; a resize copies size x elem_size bytes into a new block.
 */
#ifndef GENERIC_DYNAMIC_ARRAY_H
#define GENERIC_DYNAMIC_ARRAY_H

#include <stddef.h>

enum { INITIAL_CAPACITY = 4 };

typedef enum {
    STATUS_OK,
    STATUS_UNDERFLOW,
    STATUS_OUT_OF_RANGE,
    STATUS_NO_MEMORY
} Status;

typedef int (*CompareFn)(const void *a, const void *b);
typedef void (*PrintFn)(const void *elem);

typedef struct {
    unsigned char *data;    /* capacity x elem_size bytes; elements 0 to size - 1 in use */
    size_t elem_size;
    int size;
    int capacity;
} GenericDynamicArray;

int    create(GenericDynamicArray *a, size_t elem_size);    /* capacity INITIAL_CAPACITY; 1 on success */
void   destroy(GenericDynamicArray *a);
void  *element_at(const GenericDynamicArray *a, int i);
void   traverse(const GenericDynamicArray *a, PrintFn print);
Status get(const GenericDynamicArray *a, int i, void *out);
Status update(GenericDynamicArray *a, int i, const void *elem);
Status append(GenericDynamicArray *a, const void *elem);
Status insert_at(GenericDynamicArray *a, int pos, const void *elem);
Status delete_at_end(GenericDynamicArray *a, void *deleted);
Status delete_at(GenericDynamicArray *a, int pos, void *deleted);
int    linear_search(const GenericDynamicArray *a, const void *key, CompareFn compare);
Status shrink_to_fit(GenericDynamicArray *a);

#endif
