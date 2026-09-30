/**
 * Tells the story of the recursive dynamic array in five acts.
 *
 * The worked example is the growing music playlist of the dynamic-array project. A resize copies
 * recursively, one call per element, so an append that resizes an array of k elements goes k calls
 * deep. Act five appends a million songs in a child process, because the resize that overflows the
 * call stack cannot be caught in C.
 */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used only in act five */

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#include "dynamic_array.h"

static const char *SONGS[] = {"Blue Sky", "Rain Dance", "Night Drive", "Sunrise", "Paper Moon",
                              "Wild Honey", "Echoes", "Firefly", "Last Train"};

/** Prints how copy_from(i) unfolds during a resize: one call per element, down to the base case. */
static void trace_copy(const char *const *a, int n, int i, int indent) {
    if (i == n) {
        printf("%*scopy_from(%d): no elements left   <- base case\n", indent, "", i);
        return;
    }
    printf("%*scopy_from(%d): new_arr[%d] = %s, then copy_from(%d)\n", indent, "", i, i, a[i], i + 1);
    trace_copy(a, n, i + 1, indent + 2);
}

/** Appends value and returns how many elements the append copied (0 unless it resized). */
static int append_counting(DynamicArray *a, const char *value) {
    int size = a->size;
    int capacity = a->capacity;
    append(a, value);
    return a->capacity != capacity ? size : 0;
}

static const char *a_million_appends(void) {
    fflush(stdout);
    pid_t child = fork();
    if (child == 0) {
        DynamicArray big;
        create(&big, INITIAL_CAPACITY);
        for (int i = 0; i < 1000000; i++) {
            append(&big, "song");
        }
        _exit(0);
    }
    int status;
    waitpid(child, &status, 0);
    if (WIFSIGNALED(status)) {
        int sig = WTERMSIG(status);
        return sig == SIGSEGV ? "the child process was killed by SIGSEGV inside a resize, long before the end"
             : sig == SIGBUS ? "the child process was killed by SIGBUS inside a resize, long before the end"
             : "the child process was killed by a signal inside a resize, long before the end";
    }
    return "finished";
}

int main(void) {
    printf("ONE. A recursive copy.\n");
    DynamicArray playlist;
    create(&playlist, INITIAL_CAPACITY);
    for (int i = 0; i < 4; i++) {
        append(&playlist, SONGS[i]);
    }
    printf("  capacity %d, size %d: full\n", playlist.capacity, playlist.size);
    printf("  append(\"%s\") must resize to 8, copying recursively:\n", SONGS[4]);
    trace_copy(SONGS, 4, 0, 4);
    int copies = append_counting(&playlist, SONGS[4]);
    printf("  %d copies, one call each, depth %d, then 1 write; capacity %d\n", copies, copies, playlist.capacity);

    printf("\nTWO. Growing.\n");
    int total = copies;
    for (int i = 5; i < 8; i++) {
        total += append_counting(&playlist, SONGS[i]);
    }
    copies = append_counting(&playlist, SONGS[8]);
    total += copies;
    printf("  append(\"%s\"): capacity %d, %d copies at depth %d\n", SONGS[8], playlist.capacity, copies, copies);
    printf("  9 songs appended, 2 resizes, %d copies in total\n", total);
    printf("  traverse: ");
    traverse(&playlist);
    printf("  that traversal was %d calls deep\n", playlist.size);

    printf("\nTHREE. The middle, recursively.\n");
    int size = playlist.size;
    insert_at(&playlist, 0, "Intro");
    printf("  insert_at(0, \"Intro\"): %d shifts, depth %d\n", size, size);
    const char *removed;
    size = playlist.size;
    delete_at(&playlist, 5, &removed);
    printf("  delete_at(5) removed \"%s\": %d shifts, depth %d\n", removed, size - 5 - 1, size - 5 - 1);
    int at = linear_search(&playlist, "Firefly");
    printf("  linear_search(\"Firefly\") = index %d: %d comparisons, depth %d\n", at, at + 1, at + 1);

    printf("\nFOUR. Shrinking, recursively.\n");
    printf("  capacity %d, size %d\n", playlist.capacity, playlist.size);
    size = playlist.size;
    shrink_to_fit(&playlist);
    printf("  shrink_to_fit(): capacity %d, %d copies at depth %d\n", playlist.capacity, size, size);
    destroy(&playlist);
    DynamicArray many;
    create(&many, INITIAL_CAPACITY);
    for (int i = 0; i < 17; i++) {
        append(&many, "song");
    }
    const char *gone;
    int before = many.capacity;
    while (many.size > 8) {
        delete_at_end(&many, &gone);
    }
    printf("  17 songs in %d places, delete_at_end down to %d: capacity %d (a resize copying %d songs, depth %d)\n",
           before, many.size, many.capacity, many.size, many.size);
    destroy(&many);

    printf("\nFIVE. The limit of recursion.\n");
    printf("  1,000,000 appends: %s\n", a_million_appends());
    printf("  a recursive copy needs one frame per element, so a large resize overflows the call stack\n");
    printf("  the dynamic-array project copies with a loop, in O(1) extra space, and finishes\n");
    return 0;
}
