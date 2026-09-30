/** Every behaviour the generic recursive static array must have, including a real stack overflow. */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used by the stack-overflow test */

#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>

#include "check.h"
#include "elements.h"
#include "generic_array.h"

static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

static void make_ints(GenericArray *a, int capacity, const int *values, int n) {
    create(a, capacity, sizeof(int));
    for (int i = 0; i < n; i++) {
        insert_at(a, i, &values[i]);
    }
}

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

static void insertion_and_deletion_shift_recursively(void) {
    GenericArray a;
    int x = 18, gone;
    make_ints(&a, 10, WEEK, 7);
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

static void searches_use_the_comparison_function(void) {
    GenericArray a;
    make_ints(&a, 10, WEEK, 7);
    int key = 24, missing = 30;
    CHECK_INT(linear_search(&a, &key, compare_int), 4);
    CHECK_INT(linear_search(&a, &missing, compare_int), -1);
    destroy(&a);
    GenericArray names;
    const char *alpha[] = {"Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed"};
    create(&names, 7, sizeof(const char *));
    for (int i = 0; i < 7; i++) {
        insert_at(&names, i, &alpha[i]);
    }
    char typed[4] = "Thu";
    const char *thu = typed;
    CHECK_INT(binary_search(&names, &thu, compare_name), 4);
    const char *absent = "Abc";
    CHECK_INT(binary_search(&names, &absent, compare_name), -1);
    destroy(&names);
}

static void find_max_follows_the_comparison(void) {
    GenericArray r;
    create(&r, 7, sizeof(Reading));
    const char *days[] = {"Mon", "Tue", "Wed", "Thu"};
    for (int i = 0; i < 4; i++) {
        Reading x = {"", WEEK[i]};
        snprintf(x.day, sizeof x.day, "%s", days[i]);
        insert_at(&r, i, &x);
    }
    CHECK_INT(find_max(&r, compare_reading), 3);
    destroy(&r);
    GenericArray empty;
    create(&empty, 1, sizeof(int));
    CHECK_INT(find_max(&empty, compare_int), -1);
    destroy(&empty);
}

static void reversal_swaps_whole_elements(void) {
    GenericArray a;
    make_ints(&a, 8, (const int[]) {1, 2, 3, 4, 5, 6, 7, 8}, 8);
    reverse(&a);
    CHECK(holds(&a, (const int[]) {8, 7, 6, 5, 4, 3, 2, 1}, 8));
    destroy(&a);
}

static void a_million_deep_recursion_overflows_the_stack(void) {
    GenericArray big;
    create(&big, 1000000, sizeof(int));
    int *values = (int *) big.data;
    for (int i = 0; i < 1000000; i++) {
        values[i] = i * 2;
    }
    big.n = 1000000;
    int key = 1333332, missing = -1;
    CHECK_INT(binary_search(&big, &key, compare_int), 666666);
    fflush(stdout);
    pid_t child = fork();
    if (child == 0) {
        linear_search(&big, &missing, compare_int);
        _exit(0);
    }
    int status;
    waitpid(child, &status, 0);
    CHECK(WIFSIGNALED(status));
    destroy(&big);
}

void run_tests(void) {
    RUN(insertion_and_deletion_shift_recursively);
    RUN(overflow_and_underflow_are_reported);
    RUN(searches_use_the_comparison_function);
    RUN(find_max_follows_the_comparison);
    RUN(reversal_swaps_whole_elements);
    RUN(a_million_deep_recursion_overflows_the_stack);
}
