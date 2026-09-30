/**
 * A tiny test harness: RUN(test) runs one test function, CHECK(condition) records a failure.
 *
 * Written by hand so the course needs no test library. A failed CHECK prints the file, line and
 * condition; main returns 1 if any check failed, which makes `make test` fail.
 */
#ifndef CHECK_H
#define CHECK_H

#include <stdio.h>
#include <string.h>

extern int check_tests;
extern int check_failures;

#define CHECK(cond) \
    do { \
        if (!(cond)) { \
            check_failures++; \
            printf("FAILED %s:%d: %s\n", __FILE__, __LINE__, #cond); \
        } \
    } while (0)

#define CHECK_INT(actual, expected) \
    do { \
        long long a_ = (long long) (actual), e_ = (long long) (expected); \
        if (a_ != e_) { \
            check_failures++; \
            printf("FAILED %s:%d: %s is %lld, expected %lld\n", __FILE__, __LINE__, #actual, a_, e_); \
        } \
    } while (0)

#define CHECK_STR(actual, expected) \
    do { \
        const char *a_ = (actual), *e_ = (expected); \
        if (strcmp(a_, e_) != 0) { \
            check_failures++; \
            printf("FAILED %s:%d: %s is \"%s\", expected \"%s\"\n", __FILE__, __LINE__, #actual, a_, e_); \
        } \
    } while (0)

#define RUN(test) \
    do { \
        check_tests++; \
        test(); \
    } while (0)

#endif
