/**
 * A static (fixed-capacity) array of integers whose operations are written recursively.
 *
 * The array arr has a fixed capacity and holds n elements in arr[0] to arr[n - 1], exactly as in
 * the static-array project. Every operation that can be written recursively is: each solves the
 * problem for one element (or one half) and calls itself for the rest, stopping at a base case.
 * Every call waits on the call stack until the calls below it return, so a recursive operation
 * uses O(depth) extra space: O(n) for traversal, linear search, sum, maximum and shifting,
 * O(n / 2) for reversal, and only O(log n) for binary search. Access and update are a single
 * address calculation and are not recursive.
 */
#ifndef STATIC_ARRAY_H
#define STATIC_ARRAY_H

typedef enum {
    STATUS_OK,
    STATUS_OVERFLOW,
    STATUS_UNDERFLOW,
    STATUS_OUT_OF_RANGE
} Status;

typedef struct {
    int *arr;
    int capacity;
    int n;
} StaticArray;

int    create(StaticArray *a, int capacity);        /* 1 on success, 0 if there is no memory */
void   destroy(StaticArray *a);
void   traverse(const StaticArray *a);              /* prints [21, 23, ...] and a newline */
Status get(const StaticArray *a, int i, int *value);
Status update(StaticArray *a, int i, int value);
Status insert_at(StaticArray *a, int pos, int value);
Status delete_at(StaticArray *a, int pos, int *deleted);
int    linear_search(const StaticArray *a, int key);
int    binary_search(const StaticArray *a, int key);
int    find_max(const StaticArray *a);
long   sum(const StaticArray *a);
void   reverse(StaticArray *a);

#endif
