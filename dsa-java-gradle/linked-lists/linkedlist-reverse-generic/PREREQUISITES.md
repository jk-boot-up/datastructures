# Prerequisites — what to know before you start

Read this **before** [`README.md`](README.md).

You do not need to be good at Java. You need about six specific ideas, and one
of them (**references**) matters more than all the others put together. This
page tells you exactly what those ideas are, how to check whether you already
have them, and where to go if you do not.

**Time to work through this page:** 45–90 minutes if most of it is new to you.

---

## Table of contents

1. [Software you must install](#1-software-you-must-install)
2. [Verify your setup in 3 commands](#2-verify-your-setup-in-3-commands)
3. [The one idea that matters most: references](#3-the-one-idea-that-matters-most-references)
4. [Java checklist](#4-java-checklist)
5. [Big-O in five minutes](#5-big-o-in-five-minutes)
6. [Command-line basics](#6-command-line-basics)
7. [What you do NOT need to know](#7-what-you-do-not-need-to-know)
8. [Self-check quiz](#8-self-check-quiz)
9. [Suggested learning path](#9-suggested-learning-path)

---

## 1. Software you must install

| Tool | Version | Why | Where |
| --- | --- | --- | --- |
| **JDK** (Java Development Kit) | **21** or newer | compiles and runs the code | [Adoptium Temurin](https://adoptium.net/) — pick "JDK 21 LTS" |
| **A terminal** | any | to type `./gradlew ...` | macOS: Terminal · Windows: PowerShell · Linux: your shell |
| **A web browser** | any modern one | to open the animation | you already have one |
| **An IDE** *(optional but strongly recommended)* | current | to debug and step through code | [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/) (free) or VS Code + Extension Pack for Java |
| **Git** *(optional)* | any | only if you want to clone/commit | [git-scm.com](https://git-scm.com/) |

**You do NOT need to install Gradle.** This project ships the *Gradle Wrapper*
(`gradlew`), which downloads the correct Gradle version by itself. That is
explained in [README section 7.2](README.md#72-the-wrapper--why-you-do-not-install-gradle).

**You do NOT need to install Lombok or JUnit.** Gradle downloads both. You *do*
need the Lombok IDE plugin if you use an IDE — see
[README section 7.7](README.md#77-lombok-annotation-processing).

> **JDK vs JRE:** a JRE only *runs* Java; a JDK also *compiles* it. You need the
> JDK. If `javac -version` fails but `java -version` works, you installed the
> wrong one.

---

## 2. Verify your setup in 3 commands

Open a terminal, `cd` into this project folder, and run these. If all three
work, you are ready.

```bash
# 1. Do I have a JDK 21+?
java -version
# expect something containing:  openjdk version "21..."  (or 22, 23, ...)

# 2. Can the wrapper build the project?
./gradlew build          # Windows: gradlew.bat build
# expect:  BUILD SUCCESSFUL

# 3. Does the demo run?
./gradlew run -q         # Windows: gradlew.bat run -q
# expect:  before reverse : [A -> B -> C -> D]
#          after  reverse : [D -> C -> B -> A]
```

The **first** run downloads Gradle, JUnit and Lombok, so it needs an internet
connection and may take a couple of minutes. Every run after that is offline and
fast.

If any step fails, check the
[troubleshooting table](README.md#12-troubleshooting) — `permission denied` on
`./gradlew` and "command not found" are both covered there.

---

## 3. The one idea that matters most: references

**If you learn only one thing from this page, learn this.** Linked lists are
*made of* references. Almost every linked-list bug a beginner writes comes from
misunderstanding this single point.

In Java, a variable of a class type does **not** hold the object. It holds a
*reference* — an arrow pointing at an object that lives elsewhere in memory.

```java
Node<String> a = new Node<>("A");   // creates an object, `a` points at it
Node<String> b = a;                 // b points at THE SAME object. No copy!
b.value = "CHANGED";
System.out.println(a.value);        // prints CHANGED, not A
```

```mermaid
flowchart LR
    a(("a")) --> obj["the one Node object<br/>value = CHANGED"]
    b(("b")) --> obj
```

Two consequences that drive this whole project:

**(a) Assigning a variable moves the arrow, it does not move the object.**

```java
current = current.next;   // `current` now points somewhere else.
                          // No node was created, moved or destroyed.
```

**(b) Overwriting a `next` field can strand the rest of the list.**

```java
current.next = previous;  // the arrow to the rest of the list is GONE
                          // unless you saved it first
```

That second point is *exactly* why
[`reverse()`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L447)
saves `nextHop` before flipping anything. When you get to
[README section 4](README.md#4-the-star-operation-reverse), this will already
make sense.

### Prove it to yourself (2 minutes)

Save this as `Warmup.java` **anywhere** — you do not need this project or Gradle
for it. Java 11+ can run a single file directly:

```java
public class Warmup {
    static class Box { String label; Box next; }

    public static void main(String[] args) {
        Box first  = new Box(); first.label  = "first";
        Box second = new Box(); second.label = "second";
        first.next = second;

        Box alias = first;           // same object, second arrow
        alias.label = "renamed";
        System.out.println(first.label);        // renamed  <- same object!

        Box cursor = first;
        cursor = cursor.next;                   // move the arrow
        System.out.println(cursor.label);       // second
        System.out.println(first.label);        // renamed  <- first is untouched

        first.next = null;                      // strand the second node
        System.out.println(cursor.label);       // second   <- cursor still holds it
        System.out.println(first.next);         // null     <- but the chain lost it
    }
}
```

```bash
java Warmup.java
```

If every line of that output makes sense to you, you are ready for this project.
If not, re-read this section — it is worth the time.

> **Note on `==` vs `.equals()`:** `==` asks "are these the same object?";
> `.equals()` asks "do these have equal contents?". The list uses
> `Objects.equals(...)` in
> [`indexOf`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L200) for
> contents, and `==` in
> [`removeAt`](src/main/java/org/jk/dsa/learning/SinglyLinkedList.java#L370)
> when it asks "is this node literally the tail node?". Both usages are
> deliberate.

---

## 4. Java checklist

Tick these off honestly. **Must know** = you will be lost without it.
**Nice to have** = the code comments will carry you.

### Must know

| Concept | You should be able to… | Used in |
| --- | --- | --- |
| Variables & types | declare `int x = 5;`, `String s = "hi";` | everywhere |
| **References vs values** | explain [section 3](#3-the-one-idea-that-matters-most-references) | the entire project |
| `null` | say what `NullPointerException` means and when it happens | end of every chain |
| Classes & objects | write a class with fields and use `new` | [`Node`](src/main/java/org/jk/dsa/learning/Node.java) |
| Fields, constructors, methods | know what `this.value = value;` does | `Node`, `SinglyLinkedList` |
| `if` / `while` / `for` | write a loop that walks until a condition | `reverse`, `nodeAt`, `indexOf` |
| Access modifiers | know roughly what `public` / `private` mean | the whole API |

### Nice to have (the README explains these as it goes)

| Concept | One-line summary | Used in |
| --- | --- | --- |
| **Generics** `<T>` | a placeholder for "whatever type the caller picks" | `SinglyLinkedList<T>` — [README §3](README.md#3-why-generics) |
| `static` | belongs to the class, not to one object | `SinglyLinkedList.of(...)`, `main` |
| Interfaces | a contract a class promises to fulfil | `implements Iterable<T>` |
| Enhanced `for` loop | `for (String s : list) { ... }` | works because of `Iterable` |
| Exceptions | `throw new ...`, and what a stack trace is | index/empty guards |
| **Recursion** | a method that calls itself, with a base case | `reverseRecursive` — [README §4.4](README.md#44-the-recursive-version) |
| `toString()` / `equals()` | how objects print and compare | list printing, `indexOf` |
| Records | `record Point(int x, int y) {}` — a short immutable class | test element types |
| Annotations | `@Test`, `@Getter` — metadata tools react to | JUnit and Lombok |
| Generic arrays / `@SafeVarargs` | why varargs + generics needs a promise | `SinglyLinkedList.of` |

**If several "must know" rows are unfamiliar,** work through a beginner Java
course first — the official
[Java Tutorials: Classes and Objects](https://docs.oracle.com/javase/tutorial/java/javaOO/index.html)
is free and covers every one of them.

**Recursion specifically:** if it feels like magic, do not worry. Read the
iterative `reverse()` first, get it fully, and only then read
`reverseRecursive()`. The
[animation](docs/animation.html#reverseRecursive:12) shows the call stack
growing and shrinking, which is usually the moment it clicks.

---

## 5. Big-O in five minutes

Big-O describes **how the cost grows as the input grows**, ignoring constants
and hardware.

| Notation | Means | Example here |
| --- | --- | --- |
| **O(1)** | cost does not change with list length | `addFirst` — always the same three assignments |
| **O(n)** | double the list, double the work | `reverse` — touches every node once |
| **O(index)** | proportional to how far in you go | `get(i)` — walks `i` arrows |

Two rules that cover everything in this project:

1. **A loop over the whole list is O(n).** Nested loops would be O(n²) — there
   are none here.
2. **"Space" means *extra* memory you allocate, beyond the data itself.**
   `reverse()` is O(1) space because it only ever uses three reference
   variables, no matter how long the list is. `reverseRecursive()` is O(n) space
   because the JVM stores one stack frame per pending call.

That is genuinely all you need. Every method in the code carries its own
complexity comment, and there is a full table in
[README section 6](README.md#6-complexity-summary).

---

## 6. Command-line basics

You need four things.

```bash
pwd                       # where am I?
ls                        # what is here?           (Windows: dir)
cd path/to/folder         # go somewhere
cd ..                     # go up one level
```

Then, **from inside this project folder** (the one containing
`build.gradle.kts`):

```bash
./gradlew build           # macOS / Linux
gradlew.bat build         # Windows
```

The `./` matters on macOS and Linux: it means "the `gradlew` in *this* folder",
not a program installed system-wide. If you get `permission denied`, run
`chmod +x gradlew` once.

---

## 7. What you do NOT need to know

Do not let any of these stop you starting:

- **Gradle.** [README section 7](README.md#7-how-gradle-works-and-how-to-build-this-project)
  teaches it from zero. You only ever type `./gradlew` plus a word.
- **JUnit.** [README section 9](README.md#9-how-to-run-the-tests) explains the
  handful of annotations used.
- **Lombok.** [README section 7.7](README.md#77-lombok-annotation-processing)
  explains it, and the data structure itself deliberately does not use it.
- **Other data structures.** Arrays, stacks, trees, hash maps — none are needed.
- **Threads, I/O, networking, Spring, build pipelines, Maven.** None. This
  project has exactly one dependency (JUnit) and one compile-time tool (Lombok).
- **Any maths beyond counting.** Big-O here never goes past O(n).

---

## 8. Self-check quiz

Answer these before you start. Answers are below — no peeking.

1. After `Node<String> b = a;`, how many `Node` objects exist?
2. What does `cursor = cursor.next;` change — the object, or the variable?
3. Why must `reverse()` save `current.next` into `nextHop` *before* running
   `current.next = previous;`?
4. What is the difference between `==` and `.equals()`?
5. A method loops over every element of a list of length n. Time complexity?
6. Why is `removeLast()` O(n) on a singly linked list, when `removeFirst()` is
   O(1)?
7. What does `<T>` in `SinglyLinkedList<T>` mean?
8. A recursive method uses O(n) space even though it allocates no objects. Why?

<details>
<summary><b>Answers</b></summary>

1. **One.** Both `a` and `b` are arrows pointing at the same object.
2. **The variable.** It moves the arrow to a different existing node. No object
   is created, changed or destroyed.
3. Because that assignment **overwrites** the only arrow to the rest of the
   list. Without the saved copy, everything after `current` becomes unreachable.
4. `==` compares references ("the same object?"); `.equals()` compares contents
   ("equal values?").
5. **O(n).**
6. A node has no arrow back to its predecessor, so to delete the last node you
   must walk from `head` to the second-last node. `removeFirst()` needs no walk
   because `head` already points where you need to be.
7. It is a **type parameter** — a placeholder for whatever element type the
   caller chooses, checked at compile time.
8. Each pending call keeps a **stack frame** (its parameters and local
   variables) alive on the call stack until it returns. n nested calls = n
   frames.

</details>

**Scored 6+?** Go straight to the README.
**Scored below 6?** Re-read [section 3](#3-the-one-idea-that-matters-most-references)
and run `Warmup.java`. Questions 1–3 and 6 are all the same idea wearing
different hats.

---

## 9. Suggested learning path

```mermaid
flowchart TD
    A["1. Install a JDK 21<br/><i>section 1</i>"] --> B["2. Verify: build + run<br/><i>section 2</i>"]
    B --> C["3. Understand references<br/>run Warmup.java<br/><i>section 3</i>"]
    C --> D["4. Play the animation<br/>docs/animation.html"]
    D --> E["5. Read README §2–§4<br/>lists, generics, reverse"]
    E --> F["6. Read SinglyLinkedList.reverse()<br/>with the animation open beside it"]
    F --> G["7. Run the tests<br/>./gradlew test"]
    G --> H["8. Break it on purpose<br/>delete 'tail = oldHead;' and watch a test fail"]
    H --> I["9. Debug it<br/>breakpoint on line 447<br/><i>README §10</i>"]
    I --> J["10. Do the exercises<br/><i>README §11</i>"]
```

Step 8 is not optional decoration — deliberately breaking working code and
watching a test catch you is the fastest way to understand both why the `tail`
pointer matters and what tests are actually for.

When you finish step 10, the real test of understanding is this: delete
`SinglyLinkedList.java` entirely and write `reverse()` again from a blank file.
If you can, you have learned it.

---

**Ready?** Go to [`README.md`](README.md).
