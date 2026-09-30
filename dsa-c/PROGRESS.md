# Progress — resume here

Working notes for continuing the C course across sessions. The plan and every standing rule are in
[PLAN.md](PLAN.md); the live status per project is in [index.md](index.md)
(`tools/dskit/dskit.sh index`).

## How to build

```bash
cd /Users/jk/gitbase/datastructures/dsa-c
tools/dskit/dskit.sh next                     # the next catalogue entry with no project yet
tools/dskit/dskit.sh scaffold <slug> <module>  # Makefile, src/, tests/ (check harness), video/
tools/dskit/dskit.sh build <slug>             # docs, make test + make run, thumbnail, video, animation, index
```

Per project: write `src/<module>.[ch]` (pure textbook code), `src/demo.c` (a `main` that prints the
five acts with `printf`), `tests/test_<module>.c` (`void run_tests(void)` with `RUN(...)` and
`CHECK...`), then record the golden output with `make build/demo && ./build/demo >
tests/expected-output.txt` (use `ulimit -s 8192` first for recursive projects), and write
`ds.toml`. A project is **done** when `docs/youtube.md` exists.

## The standard

- No instrumentation in the structure's code; the demo works counts out from return values, from
  counting comparison functions (generic versions), or from the formula, and says which.
- Recursive demos show a real stack overflow in a child process (`fork`, `waitpid`), because C
  cannot catch one; the Makefile runs everything with `ulimit -s 8192` and `-O0`.
- Generic versions: a byte block with `elem_size`, `memcpy`, and `CompareFn` / `PrintFn` function
  pointers, as `qsort` and `bsearch` do it.
- Textbook snake_case names without prefixes; status codes (`STATUS_OVERFLOW`, ...), never printing
  from the structure except in `traverse`/`display`.

## Status (first batch: 10 projects, then pause for review)

| Project | State |
| --- | --- |
| static-array, -recursive, -generic, -generic-recursive | built |
| dynamic-array, -recursive, -generic | built |
| dynamic-array-generic-recursive | code and demo written; next: tests, `tests/expected-output.txt`, ds.toml, build |
| singly-linked-list, -recursive | not started; they complete the first batch of 10 |

After the C batch: rework the Java course (`../gradle-java`) the same way: remove `Lines` and
`StepCounter`, print with `System.out.println`, and check the demo against a golden
`src/test/resources/expected-output.txt`.

Nothing in `dsa-c/` has been committed yet.

HTML: every .md has a themed (Light / Dim / Dark) .html twin; `tools/dskit/dskit.sh html-all` regenerates them all.
