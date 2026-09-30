/** The element types of the demo, and the comparison and print functions for each. */
#ifndef ELEMENTS_H
#define ELEMENTS_H

/** One song in the playlist: a type of our own. */
typedef struct {
    char title[16];
    int seconds;
} Song;

int  compare_song(const void *a, const void *b);        /* equal when title and length match */
int  compare_int(const void *a, const void *b);
void print_song(const void *elem);
void print_int(const void *elem);
void print_title(const void *elem);                     /* elements are const char * */

#endif
