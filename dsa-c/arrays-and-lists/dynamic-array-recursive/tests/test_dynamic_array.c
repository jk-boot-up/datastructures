/** Every behaviour the recursive dynamic array must have, including a real stack overflow. */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used by the stack-overflow test */

#include <sys/wait.h>
#include <unistd.h>

#include "check.h"
#include "dynamic_array.h"

static const char *ABCD[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

static void make(DynamicArray *a, int n) {
    create(a, INITIAL_CAPACITY);
    for (int i = 0; i < n; i++) {
        append(a, ABCD[i]);
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

static void a_recursive_resize_keeps_every_element(void) {
    DynamicArray a;
    make(&a, 9);
    CHECK_INT(a.capacity, 16);
    CHECK(holds(&a, ABCD, 9));
    destroy(&a);
}

static void access_and_update_are_not_recursive(void) {
    DynamicArray a;
    const char *v;
    make(&a, 2);
    CHECK_INT(update(&a, 1, "z"), STATUS_OK);
    CHECK_INT(get(&a, 1, &v), STATUS_OK);
    CHECK_STR(v, "z");
    CHECK_INT(get(&a, 2, &v), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void insertion_shifts_recursively_and_grows(void) {
    DynamicArray a;
    make(&a, 4);
    CHECK_INT(insert_at(&a, 1, "x"), STATUS_OK);
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, (const char *[]) {"a", "x", "b", "c", "d"}, 5));
    CHECK_INT(insert_at(&a, 9, "y"), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void deletion_shifts_recursively_and_shrinks(void) {
    DynamicArray a;
    const char *gone;
    make(&a, 9);
    CHECK_INT(delete_at(&a, 1, &gone), STATUS_OK);
    CHECK_STR(gone, "b");
    CHECK(holds(&a, (const char *[]) {"a", "c", "d", "e", "f", "g", "h", "i"}, 8));
    for (int i = 0; i < 4; i++) {
        delete_at_end(&a, &gone);
    }
    CHECK_INT(a.capacity, 8);
    CHECK(holds(&a, (const char *[]) {"a", "c", "d", "e"}, 4));
    destroy(&a);
}

static void deleting_from_an_empty_array_is_an_underflow(void) {
    DynamicArray a;
    const char *gone;
    create(&a, INITIAL_CAPACITY);
    CHECK_INT(delete_at_end(&a, &gone), STATUS_UNDERFLOW);
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_UNDERFLOW);
    destroy(&a);
}

static void linear_search_and_shrink_to_fit(void) {
    DynamicArray a;
    make(&a, 5);
    CHECK_INT(linear_search(&a, "c"), 2);
    CHECK_INT(linear_search(&a, "zz"), -1);
    CHECK_INT(shrink_to_fit(&a), STATUS_OK);
    CHECK_INT(a.capacity, 5);
    CHECK(holds(&a, ABCD, 5));
    destroy(&a);
}

static void a_million_appends_overflow_inside_a_resize(void) {
    fflush(stdout);
    pid_t child = fork();
    if (child == 0) {
        DynamicArray big;
        create(&big, INITIAL_CAPACITY);
        for (int i = 0; i < 1000000; i++) {
            append(&big, "x");
        }
        _exit(0);
    }
    int status;
    waitpid(child, &status, 0);
    CHECK(WIFSIGNALED(status));
}

void run_tests(void) {
    RUN(a_recursive_resize_keeps_every_element);
    RUN(access_and_update_are_not_recursive);
    RUN(insertion_shifts_recursively_and_grows);
    RUN(deletion_shifts_recursively_and_shrinks);
    RUN(deleting_from_an_empty_array_is_an_underflow);
    RUN(linear_search_and_shrink_to_fit);
    RUN(a_million_appends_overflow_inside_a_resize);
}
