/** Every behaviour the static array must have, including its edge cases. */
#include <stdlib.h>

#include "check.h"
#include "static_array.h"

static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

static void make_week(StaticArray *a) {
    create(a, 10);
    for (int i = 0; i < 7; i++) {
        insert_at(a, i, WEEK[i]);
    }
}

/* The array holds exactly these n values, in order. */
static int holds(const StaticArray *a, const int *expected, int n) {
    if (a->n != n) {
        return 0;
    }
    for (int i = 0; i < n; i++) {
        if (a->arr[i] != expected[i]) {
            return 0;
        }
    }
    return 1;
}

static void a_new_array_is_empty_with_its_capacity(void) {
    StaticArray a;
    CHECK(create(&a, 5));
    CHECK_INT(a.n, 0);
    CHECK_INT(a.capacity, 5);
    destroy(&a);
}

static void access_and_update(void) {
    StaticArray a;
    int v;
    make_week(&a);
    CHECK_INT(get(&a, 3, &v), STATUS_OK);
    CHECK_INT(v, 25);
    CHECK_INT(update(&a, 5, 26), STATUS_OK);
    get(&a, 5, &v);
    CHECK_INT(v, 26);
    destroy(&a);
}

static void indexes_outside_the_elements_are_refused(void) {
    StaticArray a;
    int v;
    make_week(&a);
    CHECK_INT(get(&a, 7, &v), STATUS_OUT_OF_RANGE);
    CHECK_INT(get(&a, -1, &v), STATUS_OUT_OF_RANGE);
    CHECK_INT(update(&a, 7, 1), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void insertion_shifts_later_elements_right(void) {
    StaticArray a;
    make_week(&a);
    CHECK_INT(insert_at(&a, 2, 18), STATUS_OK);
    CHECK(holds(&a, (const int[]) {21, 23, 18, 19, 25, 24, 22, 20}, 8));
    CHECK_INT(insert_at(&a, a.n, 30), STATUS_OK);
    CHECK(holds(&a, (const int[]) {21, 23, 18, 19, 25, 24, 22, 20, 30}, 9));
    destroy(&a);
}

static void insertion_into_a_full_array_is_an_overflow(void) {
    StaticArray a;
    create(&a, 2);
    insert_at(&a, 0, 1);
    insert_at(&a, 1, 2);
    CHECK_INT(insert_at(&a, 0, 3), STATUS_OVERFLOW);
    CHECK(holds(&a, (const int[]) {1, 2}, 2));
    destroy(&a);
}

static void insertion_outside_zero_to_n_is_refused(void) {
    StaticArray a;
    make_week(&a);
    CHECK_INT(insert_at(&a, 8, 1), STATUS_OUT_OF_RANGE);
    CHECK_INT(insert_at(&a, -1, 1), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void deletion_shifts_left_and_returns_the_element(void) {
    StaticArray a;
    int gone;
    make_week(&a);
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_OK);
    CHECK_INT(gone, 21);
    CHECK(holds(&a, (const int[]) {23, 19, 25, 24, 22, 20}, 6));
    CHECK_INT(delete_at(&a, 5, &gone), STATUS_OK);
    CHECK_INT(gone, 20);
    destroy(&a);
}

static void deletion_from_an_empty_array_is_an_underflow(void) {
    StaticArray a;
    int gone;
    create(&a, 3);
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_UNDERFLOW);
    destroy(&a);
}

static void linear_search_finds_the_first_match(void) {
    StaticArray a;
    make_week(&a);
    CHECK_INT(linear_search(&a, 24), 4);
    CHECK_INT(linear_search(&a, 30), -1);
    insert_at(&a, a.n, 24);
    CHECK_INT(linear_search(&a, 24), 4);
    destroy(&a);
}

static void binary_search_on_sorted_data(void) {
    StaticArray a;
    create(&a, 10);
    const int up[] = {19, 20, 21, 23, 24, 25, 26};
    for (int i = 0; i < 7; i++) {
        insert_at(&a, i, up[i]);
    }
    CHECK_INT(binary_search(&a, 24), 4);
    CHECK_INT(binary_search(&a, 19), 0);
    CHECK_INT(binary_search(&a, 26), 6);
    CHECK_INT(binary_search(&a, 22), -1);
    destroy(&a);
    StaticArray empty;
    create(&empty, 1);
    CHECK_INT(binary_search(&empty, 1), -1);
    destroy(&empty);
}

static void binary_search_on_a_million(void) {
    StaticArray a;
    create(&a, 1000000);
    for (int i = 0; i < 1000000; i++) {
        a.arr[i] = i * 2;
    }
    a.n = 1000000;
    CHECK_INT(binary_search(&a, 1333332), 666666);
    CHECK_INT(binary_search(&a, 1333333), -1);
    destroy(&a);
}

static void find_max_sum_and_reverse(void) {
    StaticArray a;
    make_week(&a);
    CHECK_INT(find_max(&a), 3);
    CHECK_INT(sum(&a), 154);
    reverse(&a);
    CHECK(holds(&a, (const int[]) {20, 22, 24, 25, 19, 23, 21}, 7));
    destroy(&a);
    StaticArray empty;
    create(&empty, 3);
    CHECK_INT(find_max(&empty), -1);
    CHECK_INT(sum(&empty), 0);
    destroy(&empty);
}

static void growing_copies_every_element_into_a_new_block(void) {
    StaticArray a, bigger;
    make_week(&a);
    CHECK(copy_with_capacity(&a, &bigger, 31));
    CHECK_INT(bigger.capacity, 31);
    CHECK(holds(&bigger, WEEK, 7));
    CHECK(bigger.arr != a.arr);
    destroy(&a);
    destroy(&bigger);
}

void run_tests(void) {
    RUN(a_new_array_is_empty_with_its_capacity);
    RUN(access_and_update);
    RUN(indexes_outside_the_elements_are_refused);
    RUN(insertion_shifts_later_elements_right);
    RUN(insertion_into_a_full_array_is_an_overflow);
    RUN(insertion_outside_zero_to_n_is_refused);
    RUN(deletion_shifts_left_and_returns_the_element);
    RUN(deletion_from_an_empty_array_is_an_underflow);
    RUN(linear_search_finds_the_first_match);
    RUN(binary_search_on_sorted_data);
    RUN(binary_search_on_a_million);
    RUN(find_max_sum_and_reverse);
    RUN(growing_copies_every_element_into_a_new_block);
}
