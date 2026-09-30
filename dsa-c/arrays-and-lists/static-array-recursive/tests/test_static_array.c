/** Every behaviour the recursive static array must have, including a real stack overflow. */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used by the stack-overflow test */

#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#include "check.h"
#include "static_array.h"

static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

static void make(StaticArray *a, int capacity, const int *values, int n) {
    create(a, capacity);
    for (int i = 0; i < n; i++) {
        insert_at(a, i, values[i]);
    }
}

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

static void sum_and_find_max(void) {
    StaticArray a;
    make(&a, 10, WEEK, 7);
    CHECK_INT(sum(&a), 154);
    CHECK_INT(find_max(&a), 3);
    destroy(&a);
    StaticArray empty;
    create(&empty, 2);
    CHECK_INT(sum(&empty), 0);
    CHECK_INT(find_max(&empty), -1);
    destroy(&empty);
}

static void access_and_update_are_not_recursive(void) {
    StaticArray a;
    int v;
    make(&a, 10, WEEK, 7);
    CHECK_INT(get(&a, 3, &v), STATUS_OK);
    CHECK_INT(v, 25);
    CHECK_INT(update(&a, 3, 26), STATUS_OK);
    CHECK_INT(get(&a, 7, &v), STATUS_OUT_OF_RANGE);
    destroy(&a);
}

static void insertion_shifts_recursively_from_the_end(void) {
    StaticArray a;
    make(&a, 10, WEEK, 7);
    CHECK_INT(insert_at(&a, 2, 18), STATUS_OK);
    CHECK(holds(&a, (const int[]) {21, 23, 18, 19, 25, 24, 22, 20}, 8));
    CHECK_INT(insert_at(&a, 9, 1), STATUS_OUT_OF_RANGE);
    destroy(&a);
    StaticArray full;
    make(&full, 1, (const int[]) {1}, 1);
    CHECK_INT(insert_at(&full, 0, 2), STATUS_OVERFLOW);
    destroy(&full);
}

static void deletion_shifts_recursively(void) {
    StaticArray a;
    int gone;
    make(&a, 10, WEEK, 7);
    CHECK_INT(delete_at(&a, 0, &gone), STATUS_OK);
    CHECK_INT(gone, 21);
    CHECK(holds(&a, (const int[]) {23, 19, 25, 24, 22, 20}, 6));
    destroy(&a);
    StaticArray empty;
    create(&empty, 2);
    CHECK_INT(delete_at(&empty, 0, &gone), STATUS_UNDERFLOW);
    destroy(&empty);
}

static void searches_reach_their_base_cases(void) {
    StaticArray a;
    make(&a, 10, WEEK, 7);
    CHECK_INT(linear_search(&a, 24), 4);
    CHECK_INT(linear_search(&a, 30), -1);
    destroy(&a);
    StaticArray s;
    make(&s, 7, (const int[]) {19, 20, 21, 23, 24, 25, 26}, 7);
    CHECK_INT(binary_search(&s, 24), 4);
    CHECK_INT(binary_search(&s, 19), 0);
    CHECK_INT(binary_search(&s, 26), 6);
    CHECK_INT(binary_search(&s, 22), -1);
    destroy(&s);
}

static void reversal_handles_odd_and_even_lengths(void) {
    StaticArray a;
    make(&a, 8, (const int[]) {1, 2, 3, 4, 5, 6, 7, 8}, 8);
    reverse(&a);
    CHECK(holds(&a, (const int[]) {8, 7, 6, 5, 4, 3, 2, 1}, 8));
    destroy(&a);
    StaticArray b;
    make(&b, 3, (const int[]) {1, 2, 3}, 3);
    reverse(&b);
    CHECK(holds(&b, (const int[]) {3, 2, 1}, 3));
    destroy(&b);
}

static void a_million_deep_recursion_overflows_the_stack(void) {
    StaticArray big;
    create(&big, 1000000);
    for (int i = 0; i < 1000000; i++) {
        big.arr[i] = i * 2;
    }
    big.n = 1000000;
    CHECK_INT(binary_search(&big, 1333332), 666666);    /* only about 20 deep: fine */
    fflush(stdout);
    pid_t child = fork();
    if (child == 0) {
        linear_search(&big, -1);                         /* a million deep: overflows */
        _exit(0);
    }
    int status;
    waitpid(child, &status, 0);
    CHECK(WIFSIGNALED(status));
    destroy(&big);
}

void run_tests(void) {
    RUN(sum_and_find_max);
    RUN(access_and_update_are_not_recursive);
    RUN(insertion_shifts_recursively_from_the_end);
    RUN(deletion_shifts_recursively);
    RUN(searches_reach_their_base_cases);
    RUN(reversal_handles_odd_and_even_lengths);
    RUN(a_million_deep_recursion_overflows_the_stack);
}
