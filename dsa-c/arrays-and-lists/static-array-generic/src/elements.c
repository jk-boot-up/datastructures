/** Comparison and print functions: the only code that knows what the elements really are. */
#include "elements.h"

#include <stdio.h>
#include <string.h>

int compare_int(const void *a, const void *b) {
    int x = *(const int *) a;
    int y = *(const int *) b;
    return (x > y) - (x < y);          /* not x - y, which can overflow */
}

/* Each element is a char * (a pointer to text), so a and b point at pointers. */
int compare_name(const void *a, const void *b) {
    const char *x = *(const char *const *) a;
    const char *y = *(const char *const *) b;
    return strcmp(x, y);
}

int compare_reading(const void *a, const void *b) {
    const Reading *x = a;
    const Reading *y = b;
    return (x->celsius > y->celsius) - (x->celsius < y->celsius);
}

void print_int(const void *elem) {
    printf("%d", *(const int *) elem);
}

void print_name(const void *elem) {
    printf("%s", *(const char *const *) elem);
}

void print_reading(const void *elem) {
    const Reading *r = elem;
    printf("%s %d C", r->day, r->celsius);
}
