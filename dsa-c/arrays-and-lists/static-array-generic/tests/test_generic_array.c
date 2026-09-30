/** Every behaviour the generic static array must have, for more than one element type. */
#include "check.h"
#include "elements.h"
#include "generic_array.h"

static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

static void make_week(GenericArray *a) {
    create(a, 10, sizeof(int));
    for (int i = 0; i < 7; i++) {
        insert_at(a, i, &WEEK[i]);
    }
}

/* The int array holds exactly these n values, in order. */
static int holds(const GenericArray *a, const int *expected, int n) {
    if (a->n != n) {
        return 0;
    }
    for (int i = 0; i < n; i++) {
        if (*(const int *) element_at(a, i) != expected[i]) {
            return 0;
        }
    }
    return 1;
}

static void a_new_array_knows_its_element_size(void) {
    GenericArray a;
    CHECK(create(&a, 5, sizeof(Reading)));
    CHECK_INT(a.n, 0);
    CHECK_INT(a.capacity, 5);
    CHECK_INT(a.elem_size, sizeof(Reading));
    destroy(&a);
}

static void element_addresses_are_computed(void) {
    GenericArray a;
    make_week(&a);
    CHECK((unsigned char *) element_at(&a, 3) == a.data + 3 * sizeof(int));
    CHECK_INT(*(int *) element_at(&a, 3), 25);
    destroy(&a);
}

static void get_and_update_copy_whole_elements(void) {
    GenericArray a;
    create(&a, 3, sizeof(Reading));
    Reading r = {"Mon", 21}, out;
    insert_at(&a, 0, &r);
    CHECK_INT(get(&a, 0, &out), STATUS_OK);
    CHECK_STR(out.day, "Mon");
    CHECK_INT(out.celsius, 21);
    Reading s = {"Tue", 23};
    CHECK_INT(update(&a, 0, &s), STATUS_OK);
    get(&a, 0, &out);
    CHECK_STR(out.day, "Tue");
    CHECK_INT(get(&a, 1, &out), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void insertion_and_deletion_shift_elements(void) {
    GenericArray a;
    make_week(&a);
    int x = 18, gone;
    CHECK_INT(insert_at(&a, 2, &x), STATUS_OK);
    CHECK(holds(&a, (const int[]) {21, 23, 18, 19, 25, 24, 22, 20}, 8));
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_OK);
    CHECK_INT(gone, 21);
    CHECK(holds(&a, (const int[]) {23, 18, 19, 25, 24, 22, 20}, 7));
    CHECK_INT(insert_at(&a, 9, &x), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void overflow_and_underflow_are_reported(void) {
    GenericArray a;
    int x = 1, gone;
    create(&a, 1, sizeof(int));
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_UNDERFLOW);
    insert_at(&a, 0, &x);
    CHECK_INT(insert_at(&a, 0, &x), STATUS_OVERFLOW);
    destroy(&a);
}

static void linear_search_uses_the_comparison_function(void) {
    GenericArray a;
    make_week(&a);
    int key = 24, missing = 30;
    CHECK_INT(linear_search(&a, &key, compare_int), 4);
    CHECK_INT(linear_search(&a, &missing, compare_int), -1);
    destroy(&a);
}

static void strings_are_compared_by_text_not_address(void) {
    GenericArray a;
    const char *names[] = {"Mon", "Tue", "Wed"};
    create(&a, 3, sizeof(const char *));
    for (int i = 0; i < 3; i++) {
        insert_at(&a, i, &names[i]);
    }
    char typed[4] = "Wed";
    const char *key = typed;
    CHECK_INT(linear_search(&a, &key, compare_name), 2);
    destroy(&a);
}

static void binary_search_follows_the_comparison(void) {
    GenericArray a;
    const int up[] = {19, 20, 21, 23, 24, 25, 26};
    create(&a, 7, sizeof(int));
    for (int i = 0; i < 7; i++) {
        insert_at(&a, i, &up[i]);
    }
    int key = 24, absent = 22;
    CHECK_INT(binary_search(&a, &key, compare_int), 4);
    CHECK_INT(binary_search(&a, &absent, compare_int), -1);
    destroy(&a);
}

static void find_max_follows_the_comparison(void) {
    GenericArray r;
    const char *days[] = {"Mon", "Tue", "Wed", "Thu"};
    create(&r, 4, sizeof(Reading));
    for (int i = 0; i < 4; i++) {
        Reading x = {"", WEEK[i]};
        snprintf(x.day, sizeof x.day, "%s", days[i]);
        insert_at(&r, i, &x);
    }
    CHECK_INT(find_max(&r, compare_reading), 3);
    destroy(&r);
    GenericArray n;
    create(&n, 4, sizeof(const char *));
    for (int i = 0; i < 4; i++) {
        insert_at(&n, i, &days[i]);
    }
    CHECK_INT(find_max(&n, compare_name), 2);
    destroy(&n);
    GenericArray empty;
    create(&empty, 1, sizeof(int));
    CHECK_INT(find_max(&empty, compare_int), -1);
    destroy(&empty);
}

static void reverse_swaps_whole_elements(void) {
    GenericArray a;
    make_week(&a);
    reverse(&a);
    CHECK(holds(&a, (const int[]) {20, 22, 24, 25, 19, 23, 21}, 7));
    destroy(&a);
}

static void copying_copies_bytes_so_pointers_are_shared(void) {
    GenericArray a, b;
    const char *names[] = {"Mon", "Tue"};
    create(&a, 2, sizeof(const char *));
    insert_at(&a, 0, &names[0]);
    insert_at(&a, 1, &names[1]);
    CHECK(copy_with_capacity(&a, &b, 31));
    CHECK_INT(b.capacity, 31);
    CHECK_INT(b.n, 2);
    const char *x, *y;
    get(&a, 1, &x);
    get(&b, 1, &y);
    CHECK(x == y);
    destroy(&a);
    destroy(&b);
}

void run_tests(void) {
    RUN(a_new_array_knows_its_element_size);
    RUN(element_addresses_are_computed);
    RUN(get_and_update_copy_whole_elements);
    RUN(insertion_and_deletion_shift_elements);
    RUN(overflow_and_underflow_are_reported);
    RUN(linear_search_uses_the_comparison_function);
    RUN(strings_are_compared_by_text_not_address);
    RUN(binary_search_follows_the_comparison);
    RUN(find_max_follows_the_comparison);
    RUN(reverse_swaps_whole_elements);
    RUN(copying_copies_bytes_so_pointers_are_shared);
}
