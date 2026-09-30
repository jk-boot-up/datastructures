/**
 * Tells the story of the generic, recursive static array in five acts.
 *
 * The worked example is the same week, held as day names (char *), ints and Reading structs.
 * Comparisons are counted from outside by comparison functions that count their calls; the
 * recursion depth follows from them (one call per comparison here). Act five runs a million-deep
 * recursion in a child process, because a stack overflow cannot be caught in C.
 */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used only in act five */

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#include "elements.h"
#include "generic_array.h"

static const char *DAYS[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

static int calls = 0;

static int counting_compare_name(const void *a, const void *b) {
    calls++;
    return compare_name(a, b);
}

static int counting_compare_reading(const void *a, const void *b) {
    calls++;
    return compare_reading(a, b);
}

static int counting_compare_int(const void *a, const void *b) {
    calls++;
    return compare_int(a, b);
}

/** Prints how the recursive search for key unfolds over the day names, one call per line. */
static void trace_search(const char *const *names, int n, const char *key, int i, int indent) {
    if (i == n) {
        printf("%*slinear_search(%d): no elements left, -1   <- base case\n", indent, "", i);
        return;
    }
    if (compare_name(&names[i], &key) == 0) {
        printf("%*slinear_search(%d): %s equals %s -> found, index %d   <- base case\n", indent, "", i, names[i], key, i);
        return;
    }
    printf("%*slinear_search(%d): %s is not %s -> linear_search(%d)\n", indent, "", i, names[i], key, i + 1);
    trace_search(names, n, key, i + 1, indent + 2);
}

static void make_readings(GenericArray *r) {
    create(r, 10, sizeof(Reading));
    for (int i = 0; i < 7; i++) {
        Reading x;
        snprintf(x.day, sizeof x.day, "%s", DAYS[i]);
        x.celsius = WEEK[i];
        insert_at(r, i, &x);
    }
}

static const char *million_deep(const GenericArray *big) {
    fflush(stdout);
    pid_t child = fork();
    if (child == 0) {
        int missing = -1;
        linear_search(big, &missing, compare_int);
        _exit(0);
    }
    int status;
    waitpid(child, &status, 0);
    if (WIFSIGNALED(status)) {
        int sig = WTERMSIG(status);
        return sig == SIGSEGV ? "the child process was killed by SIGSEGV: the call stack overflowed"
             : sig == SIGBUS ? "the child process was killed by SIGBUS: the call stack overflowed"
             : "the child process was killed by a signal: the call stack overflowed";
    }
    return "finished";
}

int main(void) {
    GenericArray days, temps, readings;

    printf("ONE. Recursion, for any element type.\n");
    trace_search(DAYS, 7, "Thu", 0, 2);
    create(&days, 10, sizeof(const char *));
    create(&temps, 10, sizeof(int));
    for (int i = 0; i < 7; i++) {
        insert_at(&days, i, &DAYS[i]);
        insert_at(&temps, i, &WEEK[i]);
    }
    const char *thu = "Thu";
    calls = 0;
    int at = linear_search(&days, &thu, counting_compare_name);
    printf("  linear_search(\"Thu\") = index %d: %d comparisons, one call each, depth %d\n", at, calls, calls);

    printf("\nTWO. The same recursion, three types.\n");
    make_readings(&readings);
    printf("  ints:     ");
    traverse(&temps, print_int);
    printf("  names:    ");
    traverse(&days, print_name);
    printf("  readings: ");
    traverse(&readings, print_reading);
    printf("  each traversal is %d calls deep, whatever the element type\n", days.n);

    printf("\nTHREE. Comparison functions, recursively.\n");
    GenericArray names;
    create(&names, 10, sizeof(const char *));
    const char *alpha[] = {"Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed"};
    for (int i = 0; i < 7; i++) {
        insert_at(&names, i, &alpha[i]);
    }
    calls = 0;
    int t = binary_search(&names, &thu, counting_compare_name);
    printf("  sorted: ");
    traverse(&names, print_name);
    printf("  binary_search(\"Thu\") = index %d: %d comparisons, one call each, depth %d\n", t, calls, calls);
    destroy(&names);
    calls = 0;
    int hottest = find_max(&readings, counting_compare_reading);
    Reading h;
    get(&readings, hottest, &h);
    printf("  find_max(compare_reading) = %s %d C: %d comparisons on the way back up, depth %d\n",
           h.day, h.celsius, calls, readings.n - 1);
    const char *last_name;
    get(&days, find_max(&days, compare_name), &last_name);
    printf("  find_max(compare_name) = %s: the same function, alphabetical order\n", last_name);

    printf("\nFOUR. Insertion, deletion and reversal, recursively.\n");
    Reading late = {"Wed*", 18};
    int n = readings.n;
    insert_at(&readings, 2, &late);
    printf("  insert_at(2, Wed* 18 C): %d shifts, depth %d\n", n - 2, n - 2);
    Reading gone;
    n = readings.n;
    delete_at(&readings, 0, &gone);
    printf("  delete_at(0) removed %s %d C: %d shifts, depth %d\n", gone.day, gone.celsius, n - 1, n - 1);
    reverse(&readings);
    printf("  reverse: %d swaps, depth %d; ", readings.n / 2, readings.n / 2);
    traverse(&readings, print_reading);
    destroy(&readings);
    destroy(&days);
    destroy(&temps);

    printf("\nFIVE. The limit of recursion.\n");
    GenericArray big;
    create(&big, 1000000, sizeof(int));
    int *values = (int *) big.data;
    for (int i = 0; i < 1000000; i++) {
        values[i] = i * 2;
    }
    big.n = 1000000;
    int key = 1333332;
    calls = 0;
    int where = binary_search(&big, &key, counting_compare_int);
    printf("  binary_search on 1,000,000 ints: index %d, %d comparisons, depth %d\n", where, calls, calls);
    printf("  linear_search on 1,000,000: %s\n", million_deep(&big));
    destroy(&big);
    printf("  void * changes what is stored; recursion changes how much stack is used; neither changes the time\n");
    return 0;
}
