/**
 * Tells the story of the static array in five acts.
 *
 * The worked example is a week of daily temperatures in degrees Celsius, Monday at index 0
 * through Sunday at index 6, in an array of capacity 10. The counts are worked out from what the
 * operations return: a search that finds index i made i + 1 comparisons, an insertion at pos
 * shifts n - pos elements, and a binary search of n elements needs at most floor(log2 n) + 1
 * comparisons.
 */
#include <stdio.h>
#include <stdlib.h>

#include "static_array.h"
#include "week.h"

static const char *DAYS[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

static const char *reason(Status s) {
    switch (s) {
        case STATUS_OVERFLOW: return "overflow: the array is full";
        case STATUS_UNDERFLOW: return "underflow: the array is empty";
        case STATUS_OUT_OF_RANGE: return "out of range: no element at that index";
        default: return "ok";
    }
}

/** The most comparisons binary search can make on n elements: how often n can be halved, plus one. */
static int max_comparisons(int n) {
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

int main(void) {
    StaticArray a;
    int value;

    printf("ONE. Seven separate variables.\n");
    WeekInVariables by_hand = {21, 23, 19, 25, 24, 22, 20};
    printf("  mon=21 tue=23 wed=19 thu=25 fri=24 sat=22 sun=20\n");
    printf("  the maximum, by hand: %d comparisons written out one by one, answer %d C\n",
           HAND_WRITTEN_COMPARISONS, hottest(&by_hand));
    printf("  \"day number 3\" needs a switch with 7 cases: %d C\n", day(&by_hand, 3));

    printf("\nTWO. One array: int arr[10], n = 7.\n");
    create(&a, 10);
    fill(&a, WEEK, 7);
    printf("  traverse: ");
    traverse(&a);
    printf("  capacity %d, n = %d, %zu bytes in one block\n", a.capacity, a.n, a.capacity * sizeof(int));
    printf("  &arr[3] - &arr[0] = %td ints = %td bytes: arr[3] is computed, not searched\n",
           &a.arr[3] - &a.arr[0], (char *) &a.arr[3] - (char *) &a.arr[0]);

    printf("\nTHREE. Access, update, search.\n");
    get(&a, 3, &value);
    printf("  get(3) = %d C (%s): 1 step, one address calculation\n", value, DAYS[3]);
    update(&a, 5, 26);
    printf("  update(5, 26) (%s): 1 step\n", DAYS[5]);
    int at = linear_search(&a, 24);
    printf("  linear_search(24): index %d, so %d comparisons (indexes 0 to %d)\n", at, at + 1, at);
    printf("  linear_search(30): %d, so all %d elements compared\n", linear_search(&a, 30), a.n);
    StaticArray sorted;
    create(&sorted, 10);
    fill(&sorted, (const int[]) {19, 20, 21, 23, 24, 25, 26}, 7);
    printf("  sorted: ");
    traverse(&sorted);
    printf("  binary_search(24) = index %d, at most %d comparisons for %d elements\n",
           binary_search(&sorted, 24), max_comparisons(sorted.n), sorted.n);
    destroy(&sorted);

    printf("\nFOUR. Insertion, deletion, overflow.\n");
    int n = a.n;
    insert_at(&a, 2, 18);
    printf("  insert_at(2, 18): %d elements shifted right (n - pos = %d - 2), n = %d\n", n - 2, n, a.n);
    printf("  ");
    traverse(&a);
    int gone;
    n = a.n;
    delete_at(&a, 0, &gone);
    printf("  delete_at(0) removed %d: %d elements shifted left (n - pos - 1), n = %d\n", gone, n - 1, a.n);
    insert_at(&a, a.n, 27);
    insert_at(&a, a.n, 28);
    insert_at(&a, a.n, 29);
    printf("  n = %d, insert_at(0, 30): %s\n", a.n, reason(insert_at(&a, 0, 30)));
    printf("  get(%d): %s\n", a.n, reason(get(&a, a.n, &value)));
    destroy(&a);

    printf("\nFIVE. The bill.\n");
    StaticArray big;
    create(&big, 1000000);
    for (int i = 0; i < 1000000; i++) {
        big.arr[i] = i * 2;
    }
    big.n = 1000000;
    printf("  binary_search on 1,000,000 sorted elements: index %d, at most %d comparisons\n",
           binary_search(&big, 1333332), max_comparisons(big.n));
    printf("  linear_search on 1,000,000: %d, so 1,000,000 comparisons, with O(1) extra space\n",
           linear_search(&big, -1));
    destroy(&big);
    StaticArray week, month;
    create(&week, 7);
    fill(&week, WEEK, 7);
    copy_with_capacity(&week, &month, 31);
    printf("  growing 7 to 31: a new block and %d copies; capacity %d\n", week.n, month.capacity);
    destroy(&week);
    destroy(&month);
    printf("  every loop here uses O(1) extra space; the static-array-recursive project writes them recursively\n");
    printf("  already in C: int arr[n] itself, and qsort and bsearch in <stdlib.h>\n");
    return 0;
}
