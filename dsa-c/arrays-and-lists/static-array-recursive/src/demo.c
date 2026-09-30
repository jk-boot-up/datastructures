/**
 * Tells the story of the recursive static array in five acts.
 *
 * The worked example is the same week of daily temperatures as the static-array project, in an
 * array of capacity 10. Depths are worked out from what the operations do: a recursion that
 * handles one element per call over k elements has k calls waiting at its deepest, and binary
 * search is at most floor(log2 n) + 1 calls deep. Act five runs a million-deep recursion in a
 * child process: a stack overflow cannot be caught in C, so the child is allowed to crash and the
 * demo reports how.
 */
#define _POSIX_C_SOURCE 200809L    /* for fork and waitpid, used only in act five */

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#include "static_array.h"

static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

/** Prints how sum(i) unfolds: each call waits for the one below it, down to the base case. */
static void trace_sum(const int *a, int n, int i, int indent) {
    if (i == n) {
        printf("%*ssum(%d) = 0   <- base case: no elements left\n", indent, "", i);
        return;
    }
    printf("%*ssum(%d) = %d + sum(%d)\n", indent, "", i, a[i], i + 1);
    trace_sum(a, n, i + 1, indent + 2);
}

/** The most calls binary search can make on n elements: how often n can be halved, plus one. */
static int max_depth_halving(int n) {
    int c = 0;
    while (n > 0) {
        n /= 2;
        c++;
    }
    return c;
}

static void fill(StaticArray *a, const int *values, int count) {
    for (int i = 0; i < count; i++) {
        insert_at(a, i, values[i]);
    }
}

/**
 * Runs linear_search on a million elements in a child process and says how the child ended: a
 * normal exit means the search finished; a signal means the stack overflowed and the operating
 * system stopped the child.
 */
static const char *million_deep(const StaticArray *big) {
    fflush(stdout);                 /* so the child does not print the parent's buffered text again */
    pid_t child = fork();
    if (child == 0) {
        linear_search(big, -1);
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
    StaticArray a;

    printf("ONE. Thinking recursively.\n");
    printf("  the sum of the week = the first element + the sum of the rest\n");
    trace_sum(WEEK, 7, 0, 2);
    create(&a, 10);
    fill(&a, WEEK, 7);
    printf("  sum() = %ld, with %d calls waiting on the stack at once, one per element\n", sum(&a), a.n);

    printf("\nTWO. The call stack.\n");
    printf("  traverse: ");
    traverse(&a);
    printf("  call-stack depth %d: one frame per element, each waiting for the rest\n", a.n);
    int max = find_max(&a);
    printf("  find_max = index %d (%d C): depth %d, %d comparisons on the way back up\n",
           max, a.arr[max], a.n - 1, a.n - 1);

    printf("\nTHREE. Searching recursively.\n");
    int at = linear_search(&a, 24);
    printf("  linear_search(24) = index %d: %d comparisons, one call each, depth %d\n", at, at + 1, at + 1);
    StaticArray sorted;
    create(&sorted, 10);
    fill(&sorted, (const int[]) {19, 20, 21, 23, 24, 25, 26}, 7);
    printf("  sorted: ");
    traverse(&sorted);
    printf("  binary_search(24) = index %d: at most %d calls deep, one comparison each\n",
           binary_search(&sorted, 24), max_depth_halving(sorted.n));
    destroy(&sorted);

    printf("\nFOUR. Insertion, deletion and reversal, recursively.\n");
    int n = a.n;
    insert_at(&a, 2, 18);
    printf("  insert_at(2, 18): %d shifts, one call each, depth %d; ", n - 2, n - 2);
    traverse(&a);
    int gone;
    n = a.n;
    delete_at(&a, 0, &gone);
    printf("  delete_at(0): %d shifts, depth %d; ", n - 1, n - 1);
    traverse(&a);
    reverse(&a);
    printf("  reverse: %d swaps, depth %d; ", a.n / 2, a.n / 2);
    traverse(&a);
    destroy(&a);

    printf("\nFIVE. The limit of recursion.\n");
    StaticArray big;
    create(&big, 1000000);
    for (int i = 0; i < 1000000; i++) {
        big.arr[i] = i * 2;
    }
    big.n = 1000000;
    printf("  binary_search on 1,000,000: index %d, at most %d calls deep: halving keeps the stack small\n",
           binary_search(&big, 1333332), max_depth_halving(big.n));
    printf("  linear_search on 1,000,000: %s\n", million_deep(&big));
    destroy(&big);
    printf("  the same loop in the static-array project needs O(1) extra space and finishes\n");
    printf("  C cannot catch a stack overflow: the program is stopped, so this ran in a child process\n");
    return 0;
}
