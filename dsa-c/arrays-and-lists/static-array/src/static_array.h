/**
 * A static (fixed-capacity) array of integers with the operations of a data-structures textbook,
 * written with loops.
 *
 * The array arr has a capacity fixed when it is created (MAX in a C textbook) and holds n
 * elements in arr[0] to arr[n - 1]. Reaching arr[i] is one address calculation, base + i x
 * sizeof(int), so access is O(1). Inserting or deleting in the middle shifts the later elements;
 * inserting into a full array is an overflow. The static-array-recursive project writes the same
 * operations recursively.
 */
#ifndef STATIC_ARRAY_H
#define STATIC_ARRAY_H

/** Whether an operation succeeded, and if not, why. */
typedef enum {
    STATUS_OK,
    STATUS_OVERFLOW,        /* inserting into a full array */
    STATUS_UNDERFLOW,       /* deleting from an empty array */
    STATUS_OUT_OF_RANGE     /* an index outside the elements */
} Status;

typedef struct {
    int *arr;               /* the elements, in arr[0] to arr[n - 1] */
    int capacity;           /* places, fixed when the array is created */
    int n;                  /* elements in use */
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
int    copy_with_capacity(const StaticArray *a, StaticArray *bigger, int new_capacity);

#endif
