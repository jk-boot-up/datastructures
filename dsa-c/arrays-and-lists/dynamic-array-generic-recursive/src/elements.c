/** Comparison and print functions: the only code that knows what the elements really are. */
#include "elements.h"

#include <stdio.h>
#include <string.h>

int compare_song(const void *a, const void *b) {
    const Song *x = a, *y = b;
    int c = strcmp(x->title, y->title);
    if (c != 0) {
        return c;
    }
    return (x->seconds > y->seconds) - (x->seconds < y->seconds);
}

int compare_int(const void *a, const void *b) {
    int x = *(const int *) a, y = *(const int *) b;
    return (x > y) - (x < y);
}

void print_song(const void *elem) {
    const Song *s = elem;
    printf("%s (%d:%02d)", s->title, s->seconds / 60, s->seconds % 60);
}

void print_int(const void *elem) {
    printf("%d", *(const int *) elem);
}

void print_title(const void *elem) {
    printf("%s", *(const char *const *) elem);
}
