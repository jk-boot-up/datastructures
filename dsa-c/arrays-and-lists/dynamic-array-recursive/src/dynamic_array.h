/**
 * A dynamic array (a resizable array) of strings whose operations are written recursively.
 *
 * The array inside, arr, has a capacity (its length); the dynamic array has a size, the number of
 * elements in use, stored in arr[0] to arr[size - 1]. Appending is one step while
 * size < capacity. When the array is full, a new array of twice the capacity is allocated and
 * every element copied across; doubling makes those copies rare, so an append costs O(1)
 * amortised. When deletions leave the array only a quarter full, it is halved. Every loop of the
 * dynamic-array project is written here as a recursion with a base case: traversal, linear search,
 * the shifts of insertion and deletion, and the copy inside a resize. The time is unchanged; the
 * extra space is one call-stack frame per element visited, shifted or copied.
 */
#ifndef DYNAMIC_ARRAY_H
#define DYNAMIC_ARRAY_H

enum { INITIAL_CAPACITY = 4 };

typedef enum {
    STATUS_OK,
    STATUS_UNDERFLOW,       /* deleting from an empty array */
    STATUS_OUT_OF_RANGE,    /* an index outside the elements */
    STATUS_NO_MEMORY        /* malloc could not give a bigger array */
} Status;

typedef struct {
    const char **arr;       /* the elements, in arr[0] to arr[size - 1] */
    int size;               /* elements in use */
    int capacity;           /* places in arr */
} DynamicArray;

int    create(DynamicArray *a, int initial_capacity);   /* 1 on success */
void   destroy(DynamicArray *a);
void   traverse(const DynamicArray *a);                    /* prints [x, y, ...] and a newline */
Status get(const DynamicArray *a, int i, const char **value);
Status update(DynamicArray *a, int i, const char *value);
Status append(DynamicArray *a, const char *value);
Status insert_at(DynamicArray *a, int pos, const char *value);
Status delete_at_end(DynamicArray *a, const char **deleted);
Status delete_at(DynamicArray *a, int pos, const char **deleted);
int    linear_search(const DynamicArray *a, const char *key);
Status shrink_to_fit(DynamicArray *a);

#endif
