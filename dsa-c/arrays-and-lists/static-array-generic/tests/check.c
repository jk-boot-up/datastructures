/** The test harness's counters, and main: runs the tests and reports. */
#include "check.h"

int check_tests = 0;
int check_failures = 0;

void run_tests(void);

int main(void) {
    run_tests();
    printf("%d tests, %d failed checks\n", check_tests, check_failures);
    return check_failures == 0 ? 0 : 1;
}
