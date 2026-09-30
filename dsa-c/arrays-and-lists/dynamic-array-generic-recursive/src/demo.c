/**
 * Tells the story of the generic, recursive dynamic array in five acts.
 *
 * The worked example is the playlist of Song structs. A resize copies recursively, one call per
 * element; comparisons are counted by a comparison function that counts its calls. Act five
 * appends a million ints in a child process, because the resize that overflows the call stack
 * cannot be caught in C.
 */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used only in act five */

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#include "elements.h"
#include "generic_dynamic_array.h"

static const Song SONGS[] = {{"Blue Sky", 214}, {"Rain Dance", 187}, {"Night Drive", 243},
                             {"Sunrise", 198}, {"Paper Moon", 176}, {"Wild Honey", 225},
                             {"Echoes", 201}, {"Firefly", 169}, {"Last Train", 232}};

static int calls = 0;

static int counting_compare_song(const void *a, const void *b) {
    calls++;
    return compare_song(a, b);
}

static void trace_copy(int n, int i, int indent) {
    if (i == n) {
        printf("%*scopy_from(%d): no elements left   <- base case\n", indent, "", i);
        return;
    }
    printf("%*scopy_from(%d): memcpy %s (20 bytes), then copy_from(%d)\n", indent, "", i, SONGS[i].title, i + 1);
    trace_copy(n, i + 1, indent + 2);
}

static int append_counting(GenericDynamicArray *a, const void *elem) {
    int size = a->size, capacity = a->capacity;
    append(a, elem);
    return a->capacity != capacity ? size : 0;
}

static const char *a_million_appends(void) {
    fflush(stdout);
    pid_t child = fork();
    if (child == 0) {
        GenericDynamicArray big;
        create(&big, sizeof(int));
        for (int i = 0; i < 1000000; i++) {
            append(&big, &i);
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
    printf("ONE. A recursive copy of bytes.\n");
    GenericDynamicArray playlist;
    create(&playlist, sizeof(Song));
    for (int i = 0; i < 4; i++) {
        append(&playlist, &SONGS[i]);
    }
    printf("  capacity %d, size %d, elem_size %zu: full\n", playlist.capacity, playlist.size, playlist.elem_size);
    trace_copy(4, 0, 4);
    int copies = append_counting(&playlist, &SONGS[4]);
    printf("  append(Paper Moon): %d songs copied, one call each, depth %d; capacity %d\n", copies, copies, playlist.capacity);

    printf("\nTWO. Growing, and any type.\n");
    int total = copies, deepest = copies;
    for (int i = 5; i < 9; i++) {
        int c = append_counting(&playlist, &SONGS[i]);
        total += c;
        deepest = c > deepest ? c : deepest;
    }
    printf("  9 songs: 2 resizes, capacity %d, %d songs copied, the deepest copy %d calls\n", playlist.capacity, total, deepest);
    GenericDynamicArray plays;
    create(&plays, sizeof(int));
    for (int i = 1; i <= 5; i++) {
        int count = i * 10;
        append(&plays, &count);
    }
    printf("  play counts (int): ");
    traverse(&plays, print_int);
    printf("  that traversal was %d calls deep\n", plays.size);
    destroy(&plays);

    printf("\nTHREE. The middle, and a comparison function, recursively.\n");
    Song intro = {"Intro", 42};
    int size = playlist.size;
    insert_at(&playlist, 0, &intro);
    printf("  insert_at(0, Intro (0:42)): %d shifts, depth %d\n", size, size);
    Song removed;
    size = playlist.size;
    delete_at(&playlist, 5, &removed);
    printf("  delete_at(5) removed %s: %d shifts, depth %d\n", removed.title, size - 5 - 1, size - 5 - 1);
    Song echoes = {"Echoes", 201};
    calls = 0;
    int at = linear_search(&playlist, &echoes, counting_compare_song);
    printf("  linear_search(Echoes (3:21)) = index %d: %d comparisons, one call each, depth %d\n", at, calls, calls);

    printf("\nFOUR. Shrinking, recursively.\n");
    Song last;
    delete_at_end(&playlist, &last);
    printf("  delete_at_end() removed %s\n", last.title);
    size = playlist.size;
    shrink_to_fit(&playlist);
    printf("  shrink_to_fit(): capacity %d, %d songs copied at depth %d\n", playlist.capacity, size, size);
    destroy(&playlist);

    printf("\nFIVE. The limit of recursion.\n");
    printf("  1,000,000 appends of ints: %s\n", a_million_appends());
    printf("  void * changes what is stored; recursion changes how much stack a resize needs\n");
    return 0;
}
