/** Every behaviour the dynamic array must have, including resizing both ways. */
#include "check.h"
#include "dynamic_array.h"

static void make(DynamicArray *a, const char *const *values, int n) {
    create(a, INITIAL_CAPACITY, 1);
    for (int i = 0; i < n; i++) {
        append(a, values[i]);
    }
}

static int holds(const DynamicArray *a, const char *const *expected, int n) {
    if (a->size != n) {
        return 0;
    }
    for (int i = 0; i < n; i++) {
        if (strcmp(a->arr[i], expected[i]) != 0) {
            return 0;
        }
    }
    return 1;
}

static const char *ABCD[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

static void a_new_array_is_empty_with_four_places(void) {
    DynamicArray a;
    CHECK(create(&a, INITIAL_CAPACITY, 1));
    CHECK_INT(a.size, 0);
    CHECK_INT(a.capacity, 4);
    destroy(&a);
}

static void appending_to_a_full_array_doubles_it(void) {
    DynamicArray a;
    make(&a, ABCD, 4);
    CHECK_INT(a.capacity, 4);
    append(&a, "e");
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, ABCD, 5));
    destroy(&a);
}

static void nine_appends_resize_twice(void) {
    DynamicArray a;
    make(&a, ABCD, 9);
    CHECK_INT(a.capacity, 16);
    CHECK(holds(&a, ABCD, 9));
    destroy(&a);
}

static void growing_by_one_resizes_every_time(void) {
    DynamicArray a;
    create(&a, 4, 0);
    for (int i = 0; i < 6; i++) {
        append(&a, "x");
    }
    CHECK_INT(a.capacity, 6);
    destroy(&a);
}

static void access_and_update(void) {
    DynamicArray a;
    const char *v;
    make(&a, ABCD, 2);
    CHECK_INT(get(&a, 1, &v), STATUS_OK);
    CHECK_STR(v, "b");
    CHECK_INT(update(&a, 1, "z"), STATUS_OK);
    get(&a, 1, &v);
    CHECK_STR(v, "z");
    CHECK_INT(get(&a, 2, &v), STATUS_OUT_OF_RANGE);
    CHECK_INT(update(&a, -1, "x"), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void insertion_shifts_and_grows_when_full(void) {
    DynamicArray a;
    make(&a, ABCD, 4);
    CHECK_INT(insert_at(&a, 1, "x"), STATUS_OK);
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, (const char *[]) {"a", "x", "b", "c", "d"}, 5));
    CHECK_INT(insert_at(&a, 5, "y"), STATUS_OK);
    CHECK_INT(insert_at(&a, 9, "z"), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void deletion_shifts_and_returns_the_element(void) {
    DynamicArray a;
    const char *gone;
    make(&a, ABCD, 4);
    CHECK_INT(delete_at(&a, 1, &gone), STATUS_OK);
    CHECK_STR(gone, "b");
    CHECK(holds(&a, (const char *[]) {"a", "c", "d"}, 3));
    CHECK_INT(delete_at_end(&a, &gone), STATUS_OK);
    CHECK_STR(gone, "d");
    destroy(&a);
}

static void the_capacity_halves_when_a_quarter_full(void) {
    DynamicArray a;
    const char *gone;
    make(&a, ABCD, 9);
    for (int i = 0; i < 4; i++) {
        delete_at_end(&a, &gone);
    }
    CHECK_INT(a.capacity, 16);
    delete_at_end(&a, &gone);
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, ABCD, 4));
    destroy(&a);
}

static void deleting_from_an_empty_array_is_an_underflow(void) {
    DynamicArray a;
    const char *gone;
    create(&a, INITIAL_CAPACITY, 1);
    CHECK_INT(delete_at_end(&a, &gone), STATUS_UNDERFLOW);
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_UNDERFLOW);
    destroy(&a);
}

static void linear_search_compares_text(void) {
    DynamicArray a;
    char typed[4] = "c";
    make(&a, ABCD, 4);
    CHECK_INT(linear_search(&a, typed), 2);
    CHECK_INT(linear_search(&a, "zz"), -1);
    destroy(&a);
}

static void shrink_to_fit_gives_back_the_spare_places(void) {
    DynamicArray a;
    make(&a, ABCD, 5);
    CHECK_INT(shrink_to_fit(&a), STATUS_OK);
    CHECK_INT(a.capacity, 5);
    CHECK(holds(&a, ABCD, 5));
    destroy(&a);
}

void run_tests(void) {
    RUN(a_new_array_is_empty_with_four_places);
    RUN(appending_to_a_full_array_doubles_it);
    RUN(nine_appends_resize_twice);
    RUN(growing_by_one_resizes_every_time);
    RUN(access_and_update);
    RUN(insertion_shifts_and_grows_when_full);
    RUN(deletion_shifts_and_returns_the_element);
    RUN(the_capacity_halves_when_a_quarter_full);
    RUN(deleting_from_an_empty_array_is_an_underflow);
    RUN(linear_search_compares_text);
    RUN(shrink_to_fit_gives_back_the_spare_places);
}
