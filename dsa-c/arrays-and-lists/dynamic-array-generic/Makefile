# Builds, runs and tests this project with the system C compiler. No libraries are used.
#
#   make run     compile and run the demo (src/demo.c)
#   make test    compile and run the tests, then check that the demo still prints exactly
#                tests/expected-output.txt (every number the docs quote comes from there)
#   make clean   remove build/
#
# -O0 keeps every function call a real call, so the recursion the recursive projects describe
# (and the stack overflow they show) is what the code says, not what an optimiser made of it.
# `ulimit -s 8192` runs the programs with the usual 8 MB call stack: some versions of make (the
# one macOS ships, for example) raise the limit for everything they run, and a recursion that
# should overflow would then quietly succeed.
CC      ?= cc
CFLAGS  ?= -std=c17 -Wall -Wextra -Wpedantic -Werror -O0 -g
LIB     := $(filter-out src/demo.c, $(wildcard src/*.c))
HEADERS := $(wildcard src/*.h) $(wildcard tests/*.h)

.PHONY: all run test clean
all: build/demo build/tests

build/demo: src/demo.c $(LIB) $(HEADERS) | build
	$(CC) $(CFLAGS) -Isrc -o $@ src/demo.c $(LIB)

build/tests: $(wildcard tests/*.c) $(LIB) $(HEADERS) | build
	$(CC) $(CFLAGS) -Isrc -Itests -o $@ $(wildcard tests/*.c) $(LIB)

build:
	mkdir -p build

run: build/demo
	@ulimit -s 8192 && ./build/demo

test: build/tests build/demo
	@ulimit -s 8192 && ./build/tests
	@ulimit -s 8192 && ./build/demo | diff -u tests/expected-output.txt - \
		&& echo "demo output matches tests/expected-output.txt"

clean:
	rm -rf build
