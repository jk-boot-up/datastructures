/**
 * Tells the story of the dynamic array in five acts.
 *
 * The worked example is a music playlist that grows as songs are added. Copies are counted from
 * outside the array: whenever an append changes the capacity, every element already there was
 * copied into the new array.
 */
#include <stdio.h>

#include "dynamic_array.h"

static const char *SONGS[] = {"Blue Sky", "Rain Dance", "Night Drive", "Sunrise", "Paper Moon",
                              "Wild Honey", "Echoes", "Firefly", "Last Train"};

/** Appends value and returns how many elements the append copied (0 unless it resized). */
static long append_counting(DynamicArray *a, const char *value) {
    int size = a->size;
    int capacity = a->capacity;
    append(a, value);
    return a->capacity != capacity ? size : 0;
}

/** Copies needed to append n titles one by one from capacity 4, doubling or growing by one. */
static long copies_to_append(int n, int doubling) {
    DynamicArray a;
    create(&a, INITIAL_CAPACITY, doubling);
    long copies = 0;
    for (int i = 0; i < n; i++) {
        copies += append_counting(&a, "song");
    }
    destroy(&a);
    return copies;
}

static const char *with_commas(long n, char *buf) {
    if (n >= 1000) {
        sprintf(buf, "%ld,%03ld", n / 1000, n % 1000);
    } else {
        sprintf(buf, "%ld", n);
    }
    return buf;
}

int main(void) {
    char c1[32], c2[32];

    printf("ONE. A playlist in a fixed array.\n");
    const char *fixed[4] = {SONGS[0], SONGS[1], SONGS[2], SONGS[3]};
    printf("  const char *playlist[4] = {%s, %s, %s, %s}\n", fixed[0], fixed[1], fixed[2], fixed[3]);
    printf("  a fifth song would be playlist[4]: past the end, which C does not check\n");
    long plus_one = copies_to_append(1000, 0);
    printf("  growing by one place each time, 1,000 songs cost %s copies\n", with_commas(plus_one, c1));

    printf("\nTWO. Size and capacity.\n");
    DynamicArray playlist;
    create(&playlist, INITIAL_CAPACITY, 1);
    for (int i = 0; i < 3; i++) {
        append(&playlist, SONGS[i]);
    }
    printf("  capacity %d, size %d: ", playlist.capacity, playlist.size);
    traverse(&playlist);
    long copies = append_counting(&playlist, SONGS[3]);
    printf("  append(\"%s\"): 1 write, %ld copies, capacity %d, size %d, now full\n",
           SONGS[3], copies, playlist.capacity, playlist.size);

    printf("\nTHREE. Full? Double it.\n");
    copies = append_counting(&playlist, SONGS[4]);
    printf("  append(\"%s\"): full, new array of %d places, %ld songs copied, then 1 write\n",
           SONGS[4], playlist.capacity, copies);
    long total = copies;
    for (int i = 5; i < 8; i++) {
        total += append_counting(&playlist, SONGS[i]);
    }
    printf("  3 more songs: 1 write each, capacity %d, size %d\n", playlist.capacity, playlist.size);
    copies = append_counting(&playlist, SONGS[8]);
    total += copies;
    printf("  append(\"%s\"): full again, new array of %d places, %ld songs copied\n", SONGS[8], playlist.capacity, copies);
    printf("  9 songs appended, 2 resizes, %ld copies in total\n", total);
    long doubling = copies_to_append(1000, 1);
    printf("  1,000 appends by doubling: %s copies, about %ld per song (growing by one: %s)\n",
           with_commas(doubling, c1), (doubling + 500) / 1000, with_commas(plus_one, c2));

    printf("\nFOUR. The middle, and the spare places.\n");
    int size = playlist.size;
    insert_at(&playlist, 0, "Intro");
    printf("  insert_at(0, \"Intro\"): %d songs shifted right\n", size);
    const char *removed;
    size = playlist.size;
    delete_at(&playlist, 5, &removed);
    printf("  delete_at(5) removed \"%s\": %d songs shifted left\n", removed, size - 5 - 1);
    printf("  capacity %d, size %d: %d places spare\n", playlist.capacity, playlist.size, playlist.capacity - playlist.size);
    const char *value;
    printf("  get(%d), a spare place: %s\n", playlist.size,
           get(&playlist, playlist.size, &value) == STATUS_OUT_OF_RANGE ? "out of range: not an element" : value);
    size = playlist.size;
    shrink_to_fit(&playlist);
    printf("  shrink_to_fit(): capacity %d, %d songs copied\n", playlist.capacity, size);
    destroy(&playlist);

    printf("\nFIVE. The bill.\n");
    DynamicArray big;
    create(&big, INITIAL_CAPACITY, 1);
    for (int i = 0; i < 512; i++) {
        append(&big, "song");
    }
    printf("  the one append that resizes at 512 songs copies %ld of them\n", append_counting(&big, "song"));
    printf("  513 songs: capacity %d, %d places spare, almost half\n", big.capacity, big.capacity - big.size);
    int before = big.capacity;
    const char *gone;
    while (big.size > 256) {
        delete_at_end(&big, &gone);
    }
    printf("  delete_at_end down to 256 songs, a quarter of %d: capacity halves to %d\n", before, big.capacity);
    destroy(&big);
    printf("  already in C: realloc, which does the grow-and-copy; the doubling policy is up to you\n");
    return 0;
}
