/**
 * Tells the story of the generic dynamic array in five acts.
 *
 * The worked example is the growing playlist of the dynamic-array project, now holding Song structs
 * of our own, beside the same code holding titles and play counts. Copies are counted from outside:
 * whenever an append changes the capacity, every element already there was copied.
 */
#include <stdio.h>

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

/** Appends elem and returns how many elements the append copied (0 unless it resized). */
static int append_counting(GenericDynamicArray *a, const void *elem) {
    int size = a->size, capacity = a->capacity;
    append(a, elem);
    return a->capacity != capacity ? size : 0;
}

int main(void) {
    printf("ONE. One array type, any element type.\n");
    GenericDynamicArray titles, plays, playlist;
    create(&titles, sizeof(const char *));
    create(&plays, sizeof(int));
    create(&playlist, sizeof(Song));
    for (int i = 0; i < 3; i++) {
        const char *t = SONGS[i].title;
        int count = 10 * (i + 1);
        append(&titles, &t);
        append(&plays, &count);
        append(&playlist, &SONGS[i]);
    }
    printf("  titles (char *): ");
    traverse(&titles, print_title);
    printf("  plays (int):     ");
    traverse(&plays, print_int);
    printf("  songs (Song):    ");
    traverse(&playlist, print_song);
    printf("  elem_size %zu, %zu and %zu bytes; capacity %d, size %d each\n",
           titles.elem_size, plays.elem_size, playlist.elem_size, playlist.capacity, playlist.size);

    printf("\nTWO. Full? Double it, copying bytes.\n");
    append(&playlist, &SONGS[3]);
    int copies = append_counting(&playlist, &SONGS[4]);
    printf("  append(Paper Moon): full, new block of %d songs, %d songs copied (%zu bytes)\n",
           playlist.capacity, copies, copies * playlist.elem_size);
    for (int i = 5; i < 9; i++) {
        append(&playlist, &SONGS[i]);
    }
    printf("  9 songs appended, capacity %d\n", playlist.capacity);

    printf("\nTHREE. The middle, and comparison functions.\n");
    Song intro = {"Intro", 42};
    int size = playlist.size;
    insert_at(&playlist, 0, &intro);
    printf("  insert_at(0, Intro (0:42)): %d songs shifted right\n", size);
    Song removed;
    size = playlist.size;
    delete_at(&playlist, 5, &removed);
    printf("  delete_at(5) removed %s: %d songs shifted left\n", removed.title, size - 5 - 1);
    Song echoes = {"Echoes", 201};
    calls = 0;
    int at = linear_search(&playlist, &echoes, counting_compare_song);
    printf("  linear_search(Echoes (3:21), compare_song) = index %d after %d comparisons: title and length compared\n",
           at, calls);
    Song other = {"Echoes", 200};
    printf("  linear_search(Echoes (3:20)) = %d: a different length is a different song\n",
           linear_search(&playlist, &other, compare_song));

    printf("\nFOUR. Deleting, and giving memory back.\n");
    printf("  capacity %d, size %d: %d places spare\n", playlist.capacity, playlist.size, playlist.capacity - playlist.size);
    Song last;
    delete_at_end(&playlist, &last);
    printf("  delete_at_end() removed %s; its bytes were copied out to the caller\n", last.title);
    size = playlist.size;
    shrink_to_fit(&playlist);
    printf("  shrink_to_fit(): capacity %d, %d songs copied\n", playlist.capacity, size);

    printf("\nFIVE. The bill.\n");
    GenericDynamicArray big;
    create(&big, sizeof(int));
    long total = 0;
    for (int i = 0; i < 1000; i++) {
        total += append_counting(&big, &i);
    }
    printf("  1,000 appends of ints: %ld,%03ld elements copied, about 1 per append\n", total / 1000, total % 1000);
    int gone;
    int before = big.capacity;
    while (big.size > 256) {
        delete_at_end(&big, &gone);
    }
    printf("  delete_at_end down to 256 of capacity %d: capacity halves to %d\n", before, big.capacity);
    destroy(&big);
    destroy(&titles);
    destroy(&plays);
    destroy(&playlist);
    printf("  the same costs as the char * dynamic array: void * changes the type, not the algorithm\n");
    printf("  already in C: realloc does the grow-and-copy for any block of bytes\n");
    return 0;
}
