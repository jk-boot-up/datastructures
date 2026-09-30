/** Every behaviour the generic dynamic array must have, for more than one element type. */
#include "check.h"
#include "elements.h"
#include "generic_dynamic_array.h"

static void make_ints(GenericDynamicArray *a, int n) {
    create(a, sizeof(int));
    for (int i = 0; i < n; i++) {
        append(a, &i);
    }
}

static int holds(const GenericDynamicArray *a, const int *expected, int n) {
    if (a->size != n) {
        return 0;
    }
    for (int i = 0; i < n; i++) {
        if (*(const int *) element_at(a, i) != expected[i]) {
            return 0;
        }
    }
    return 1;
}

static void a_new_array_has_four_places_of_the_element_size(void) {
    GenericDynamicArray a;
    CHECK(create(&a, sizeof(Song)));
    CHECK_INT(a.capacity, 4);
    CHECK_INT(a.size, 0);
    CHECK_INT(a.elem_size, sizeof(Song));
    destroy(&a);
}

static void appending_doubles_and_keeps_every_element(void) {
    GenericDynamicArray a;
    make_ints(&a, 9);
    CHECK_INT(a.capacity, 16);
    CHECK(holds(&a, (const int[]) {0, 1, 2, 3, 4, 5, 6, 7, 8}, 9));
    destroy(&a);
}

static void structs_are_copied_whole(void) {
    GenericDynamicArray a;
    create(&a, sizeof(Song));
    Song s = {"Echoes", 201}, out;
    append(&a, &s);
    s.seconds = 999;                       /* the array holds its own copy */
    CHECK_INT(get(&a, 0, &out), STATUS_OK);
    CHECK_STR(out.title, "Echoes");
    CHECK_INT(out.seconds, 201);
    CHECK_INT(get(&a, 1, &out), STATUS_OUT_OF_RANGE);
    Song t = {"Firefly", 169};
    CHECK_INT(update(&a, 0, &t), STATUS_OK);
    get(&a, 0, &out);
    CHECK_STR(out.title, "Firefly");
    destroy(&a);
}

static void insertion_and_deletion_shift(void) {
    GenericDynamicArray a;
    int x = 99, gone;
    make_ints(&a, 4);
    CHECK_INT(insert_at(&a, 1, &x), STATUS_OK);
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, (const int[]) {0, 99, 1, 2, 3}, 5));
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_OK);
    CHECK_INT(gone, 0);
    CHECK(holds(&a, (const int[]) {99, 1, 2, 3}, 4));
    CHECK_INT(insert_at(&a, 9, &x), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void the_capacity_halves_when_a_quarter_full(void) {
    GenericDynamicArray a;
    int gone;
    make_ints(&a, 9);
    for (int i = 0; i < 5; i++) {
        delete_at_end(&a, &gone);
    }
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, (const int[]) {0, 1, 2, 3}, 4));
    destroy(&a);
}

static void deleting_from_an_empty_array_is_an_underflow(void) {
    GenericDynamicArray a;
    int gone;
    create(&a, sizeof(int));
    CHECK_INT(delete_at_end(&a, &gone), STATUS_UNDERFLOW);
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_UNDERFLOW);
    destroy(&a);
}

static void linear_search_uses_the_comparison_function(void) {
    GenericDynamicArray a;
    create(&a, sizeof(Song));
    Song x = {"Blue Sky", 214}, y = {"Echoes", 201};
    append(&a, &x);
    append(&a, &y);
    Song key = {"Echoes", 201}, near = {"Echoes", 200};
    CHECK_INT(linear_search(&a, &key, compare_song), 1);
    CHECK_INT(linear_search(&a, &near, compare_song), -1);
    destroy(&a);
}

static void shrink_to_fit_gives_back_the_spare_places(void) {
    GenericDynamicArray a;
    make_ints(&a, 5);
    CHECK_INT(shrink_to_fit(&a), STATUS_OK);
    CHECK_INT(a.capacity, 5);
    CHECK(holds(&a, (const int[]) {0, 1, 2, 3, 4}, 5));
    destroy(&a);
}

void run_tests(void) {
    RUN(a_new_array_has_four_places_of_the_element_size);
    RUN(appending_doubles_and_keeps_every_element);
    RUN(structs_are_copied_whole);
    RUN(insertion_and_deletion_shift);
    RUN(the_capacity_halves_when_a_quarter_full);
    RUN(deleting_from_an_empty_array_is_an_underflow);
    RUN(linear_search_uses_the_comparison_function);
    RUN(shrink_to_fit_gives_back_the_spare_places);
}
