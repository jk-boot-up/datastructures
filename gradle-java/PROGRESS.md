# Progress — resume here

Working notes for continuing the course across sessions. The plan and every standing rule are in
[PLAN.md](PLAN.md); the live status per project is in [index.md](index.md) (`tools/dskit/dskit.sh index`).

## How to build

```bash
cd /Users/jk/gitbase/datastructures/gradle-java
tools/dskit/dskit.sh next                 # the next catalogue entry with no project yet
tools/dskit/dskit.sh scaffold <slug> <package> <MainClass>
tools/dskit/dskit.sh build <slug>         # docs, tests, thumbnail, video, animation, index
```

Write the Java (iterative class + recursive class + demo + contract tests) and `ds.toml` first, then
`build`. A project is **done** when `docs/youtube.md` exists.

## The standard every project must meet (the "v2" standard)

- C-style, hand-written: no `java.util` in main code; demo output via `Lines` (StringBuilder).
- Textbook names: `Node` with `data`/`next`/`prev`, `head`/`tail`, `top`, `front`/`rear`; operations
  named as in CS&E books (`insertAtBeginning`, `deleteByKey`, `push`, `enqueue`, `linearSearch`...).
- Separate projects per version (catalogue.variants): `<slug>` iterative non-generic, `<slug>-recursive`,
  `<slug>-generic`, `<slug>-generic-recursive`. Each is self-contained (no shared code between them).
  Recursive projects show recursion depth and a real `StackOverflowError`.
- `ds.toml`: `[[operation]]` with `time` (best / average / worst), `space`, `recursive_space`,
  `counted`; `[[recursion]]` pairs (`iterative = "Class#method"`, `recursive = "Class#method"`, `note`);
  `[compare]` table (`columns`, `rows`); `pros`, `cons`.
- Plain projects use one concrete type; a `<slug>-generic` sibling (see `catalogue.GENERIC`) follows
  each applicable plain project, built straight after it.
- Narration: amy-slow voice, textbook terms ("node", "next pointer", not "box"/"arrow"), no
  gendered pronouns for named people, outro never names the next video.

## Status

Work follows `tools/dskit/dskit.sh next`, which walks the catalogue structure by structure:
`<slug>`, `<slug>-recursive`, `<slug>-generic`, `<slug>-generic-recursive`.

| Project | State |
| --- | --- |
| static-array, -recursive, -generic, -generic-recursive | v2, built |
| dynamic-array, -recursive, -generic, -generic-recursive | v2, built |
| singly-linked-list, -recursive, -generic, -generic-recursive | v2, built |
| doubly-linked-list, -recursive, -generic, -generic-recursive | v2, built |
| circular-linked-list, -recursive | v2, built |
| circular-linked-list-generic / -generic-recursive | next (paused for the user's review) |
| skip-list | textbook names applied (key/value/forward/update); needs v2 ds.toml, rebuild; then its variants |
| stack | built with v1 code (generic `ArrayStack<E>`); needs plain `String` stack with `top = -1`, v2 ds.toml, rebuild; then `stack-generic` |
| everything from `queue` onwards | not started |

Tool changes made along the way (already in `tools/dskit`): `[[listing]]` `method` accepts
`Class#a,b` and shows every overload of each name (so recursive helpers appear); `[[operation]]`
with `recursive_space` adds a call-stack column; stack pictures size their label; diagram boxes and
columns widen to fit their text; `group` pictures stack vertically when too wide; `linked` pictures
take `cut = [i]` (no link after node i) and `back = [i]` (the link after node i points left). Projects built before the diagram change (dynamic-array, the
linked lists, skip-list, stack) get the new layout on their next `build`.

Nothing in `gradle-java/` has been committed yet. The repository already had unrelated changes
staged by the user before this work began; never include those in a commit.
