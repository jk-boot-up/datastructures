# Reversing a Singly Linked List in Java

> A complete guide to reversing a generic singly linked list using the
> **iterative approach** in Java.

## Overview

This project demonstrates how to reverse a generic singly linked list in
Java using an iterative approach.

------------------------------------------------------------------------

# Understanding a Singly Linked List

A singly linked list consists of nodes where each node stores:

-   Data
-   A reference to the next node

## Structure

``` mermaid
flowchart LR
    H([head]) --> N10["10"]
    N10 --> N20["20"]
    N20 --> N30["30"]
    N30 --> N40["40"]
    N40 --> NULL((null))
    T([tail]) --> N40
```

------------------------------------------------------------------------

# Before and After Reverse

## Before Reverse

``` mermaid
flowchart LR
    H([head]) --> N10["10"]
    N10 --> N20["20"]
    N20 --> N30["30"]
    N30 --> N40["40"]
    N40 --> NULL((null))
    T([tail]) --> N40
```

## After Reverse

``` mermaid
flowchart LR
    H([head]) --> N40["40"]
    N40 --> N30["30"]
    N30 --> N20["20"]
    N20 --> N10["10"]
    N10 --> NULL((null))
    T([tail]) --> N10
```

------------------------------------------------------------------------

# Reverse Algorithm

``` mermaid
flowchart TD
    A([Start]) --> B[Save oldHead and oldTail]
    B --> C[previous = null]
    C --> D[current = head]
    D --> E{current != null?}
    E -- Yes --> F[Save next node]
    F --> G[Reverse current.next]
    G --> H[previous = current]
    H --> I[current = next]
    I --> E
    E -- No --> J[head = oldTail]
    J --> K[tail = oldHead]
    K --> L([End])
```

------------------------------------------------------------------------

# Pointer Movement

## Initial State

``` mermaid
flowchart LR
    P["previous = null"]
    C["current = 10"]
    N["next = null"]

    H([head]) --> N10["10"]
    N10 --> N20["20"]
    N20 --> N30["30"]
    N30 --> N40["40"]
    N40 --> NULL((null))
    T([tail]) --> N40
```

## Iteration 1

``` mermaid
flowchart LR
    P["previous = 10"]
    C["current = 20"]
    N["next = 20"]

    N10["10"] --> NULL((null))

    N20["20"] --> N30["30"]
    N30 --> N40["40"]
    N40 --> END((null))
```

## Iteration 2

``` mermaid
flowchart LR
    P["previous = 20"]
    C["current = 30"]

    N20["20"] --> N10["10"]
    N10 --> NULL((null))

    N30["30"] --> N40["40"]
    N40 --> END((null))
```

## Iteration 3

``` mermaid
flowchart LR
    P["previous = 30"]
    C["current = 40"]

    N30["30"] --> N20["20"]
    N20 --> N10["10"]
    N10 --> NULL((null))

    N40["40"] --> END((null))
```

## Iteration 4 (Completed)

``` mermaid
flowchart LR
    H([head]) --> N40["40"]
    N40 --> N30["30"]
    N30 --> N20["20"]
    N20 --> N10["10"]
    N10 --> NULL((null))
    T([tail]) --> N10
```

------------------------------------------------------------------------

# Java Implementation

``` java
public void reverse() {

    if (isEmpty() || size == 1) {
        return;
    }

    Node<T> cursorNode = head;
    Node<T> preCursorNode = null;
    Node<T> postCursorNode = null;

    Node<T> oldHeadNode = head;
    Node<T> oldTailNode = tail;

    while (cursorNode != null) {
        postCursorNode = cursorNode.next;
        cursorNode.next = preCursorNode;
        preCursorNode = cursorNode;
        cursorNode = postCursorNode;
    }

    head = oldTailNode;
    tail = oldHeadNode;
}
```

------------------------------------------------------------------------

# Time and Space Complexity

  Operation   Time     Space
  ----------- ------ -------
  reverse()   O(n)      O(1)

The algorithm visits every node exactly once and uses only a fixed
number of reference variables.

------------------------------------------------------------------------

# Comparison

  Feature               Iterative   Recursive
  --------------------- ----------- -------------------
  Time Complexity       O(n)        O(n)
  Extra Space           O(1)        O(n)
  Stack Overflow Risk   No          Yes
  Recommended           ✅ Yes      Only for learning

------------------------------------------------------------------------

# Summary

-   Generic implementation using Java Generics.
-   Three pointers (`previous`, `current`, `next`) reverse the links
    safely.
-   `head` becomes the original `tail`.
-   `tail` becomes the original `head`.
-   Runs in **O(n)** time and **O(1)** extra space.
-   All structural diagrams are rendered using Mermaid, which GitHub
    supports natively.
