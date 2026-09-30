/** The element types of the demo, and the comparison and print functions for each. */
#ifndef ELEMENTS_H
#define ELEMENTS_H

/** One day's temperature reading: a type of our own. */
typedef struct {
    char day[8];
    int celsius;
} Reading;

int  compare_int(const void *a, const void *b);         /* by value */
int  compare_name(const void *a, const void *b);        /* elements are char *: compare the text */
int  compare_reading(const void *a, const void *b);     /* by temperature */
void print_int(const void *elem);
void print_name(const void *elem);
void print_reading(const void *elem);

#endif
