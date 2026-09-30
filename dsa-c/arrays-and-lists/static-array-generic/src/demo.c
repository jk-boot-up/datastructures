/**
 * Tells the story of the generic static array in five acts.
 *
 * The worked example is the same week of temperatures as the static-array project, stored three
 * ways by one array type: as ints, as day names (char *), and as Reading structs of our own.
 * Comparisons are counted from outside the array: the demo passes comparison functions that
 * count their own calls.
 */
#include <stdio.h>

#include "elements.h"
#include "generic_array.h"

static const char *DAYS[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
static const int WEEK[] = {21, 23, 19, 25, 24, 22, 20};

/* Comparison functions that count their calls: the array never knows it is being watched. */
static int calls = 0;

static int counting_compare_int(const void *a, const void *b) {
    calls++;
    return compare_int(a, b);
}

static int counting_compare_name(const void *a, const void *b) {
    calls++;
    return compare_name(a, b);
}

static int counting_compare_reading(const void *a, const void *b) {
    calls++;
    return compare_reading(a, b);
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

int main(void) {
    GenericArray temps, days, readings;

    printf("ONE. One array type, any element type.\n");
    create(&temps, 10, sizeof(int));
    create(&days, 10, sizeof(const char *));
    for (int i = 0; i < 7; i++) {
        insert_at(&temps, i, &WEEK[i]);
        insert_at(&days, i, &DAYS[i]);
    }
    make_readings(&readings);
    printf("  ints:     ");
    traverse(&temps, print_int);
    printf("  names:    ");
    traverse(&days, print_name);
    printf("  readings: ");
    traverse(&readings, print_reading);
    printf("  the same functions for all three; insert_at(&temps, 0, &DAYS[0]) would compile too: void * is never checked\n");

    printf("\nTWO. Inside: bytes and an element size.\n");
    printf("  elem_size: int %zu, char * %zu, Reading %zu bytes\n", temps.elem_size, days.elem_size, readings.elem_size);
    printf("  element 3 of the readings is at data + 3 x %zu = data + %td\n",
           readings.elem_size, (unsigned char *) element_at(&readings, 3) - readings.data);
    GenericArray copy;
    copy_with_capacity(&days, &copy, 31);
    const char *original, *copied;
    get(&days, 0, &original);
    get(&copy, 0, &copied);
    printf("  copy_with_capacity(31): %d elements copied; the names are pointers, so both arrays point at the same text: %s\n",
           copy.n, original == copied ? "yes" : "no");
    destroy(&copy);

    printf("\nTHREE. Searching with comparison functions.\n");
    int key = 24;
    calls = 0;
    int at = linear_search(&temps, &key, counting_compare_int);
    printf("  linear_search(24, compare_int) = index %d: %d comparisons\n", at, calls);
    char typed[8] = "Fri";                  /* the same text, in a different place in memory */
    const char *fri = typed;
    printf("  linear_search(\"Fri\" typed by the user, compare_name) = index %d: strcmp compares the text\n",
           linear_search(&days, &fri, compare_name));
    const char *stored;
    get(&days, 4, &stored);
    printf("  the stored \"Fri\" == the typed \"Fri\" is %s: == compares addresses\n", stored == fri ? "true" : "false");
    GenericArray sorted;
    create(&sorted, 10, sizeof(int));
    const int up[] = {19, 20, 21, 23, 24, 25, 26};
    for (int i = 0; i < 7; i++) {
        insert_at(&sorted, i, &up[i]);
    }
    calls = 0;
    int found = binary_search(&sorted, &key, counting_compare_int);
    printf("  sorted: ");
    traverse(&sorted, print_int);
    printf("  binary_search(24) = index %d, %d comparisons\n", found, calls);
    destroy(&sorted);
    GenericArray names;
    create(&names, 10, sizeof(const char *));
    const char *alpha[] = {"Fri", "Mon", "Sat", "Sun", "Thu", "Tue", "Wed"};
    for (int i = 0; i < 7; i++) {
        insert_at(&names, i, &alpha[i]);
    }
    const char *thu = "Thu";
    calls = 0;
    int t = binary_search(&names, &thu, counting_compare_name);
    printf("  sorted: ");
    traverse(&names, print_name);
    printf("  binary_search(\"Thu\") = index %d, %d comparisons\n", t, calls);
    destroy(&names);

    printf("\nFOUR. Insertion, deletion, overflow.\n");
    Reading late = {"Wed*", 18};
    int n = readings.n;
    insert_at(&readings, 2, &late);
    printf("  insert_at(2, Wed* 18 C): %d shifts of %zu bytes each, n = %d\n", n - 2, readings.elem_size, readings.n);
    Reading gone;
    n = readings.n;
    delete_at(&readings, 0, &gone);
    printf("  delete_at(0) removed %s %d C: %d shifts\n", gone.day, gone.celsius, n - 1);
    printf("  ");
    traverse(&readings, print_reading);
    Reading more[] = {{"Mon", 27}, {"Tue", 28}, {"Wed", 29}};
    for (int i = 0; i < 3; i++) {
        insert_at(&readings, readings.n, &more[i]);
    }
    Reading hot = {"Thu", 30};
    Status s = insert_at(&readings, 0, &hot);
    printf("  n = %d, insert_at(0, Thu 30 C): %s\n", readings.n, s == STATUS_OVERFLOW ? "overflow: the array is full" : "ok");
    destroy(&readings);

    printf("\nFIVE. One algorithm, many orders.\n");
    GenericArray week;
    make_readings(&week);
    calls = 0;
    int hottest = find_max(&week, counting_compare_reading);
    Reading h;
    get(&week, hottest, &h);
    printf("  readings, find_max(compare_reading): %s %d C, %d comparisons\n", h.day, h.celsius, calls);
    const char *last_name;
    get(&days, find_max(&days, compare_name), &last_name);
    printf("  day names, find_max(compare_name): %s\n", last_name);
    reverse(&week);
    printf("  reverse: %d swaps of %zu bytes; ", week.n / 2, week.elem_size);
    traverse(&week, print_reading);
    destroy(&week);
    destroy(&temps);
    destroy(&days);
    printf("  no sum(): adding needs numbers, and a void * element can be anything\n");
    printf("  already in C: qsort and bsearch take exactly this: a void * block, an element size and a compare function\n");
    return 0;
}
