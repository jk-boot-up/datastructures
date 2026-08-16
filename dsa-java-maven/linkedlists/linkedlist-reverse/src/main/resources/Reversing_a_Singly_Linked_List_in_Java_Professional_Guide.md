# Reversing a Singly Linked List in Java

> A complete guide to reversing a generic singly linked list using the
> **iterative approach** in Java.

## Overview

This project demonstrates how to reverse a **generic singly linked
list** in Java using an **iterative approach**. The implementation works
for any data type because the linked list uses **Java Generics**.

``` java
LinkedList<Integer> list = new LinkedList<>(10);
list.add(20);
list.add(30);
list.add(40);

System.out.println(list);
// [10, 20, 30, 40]

list.reverse();

System.out.println(list);
// [40, 30, 20, 10]
```

------------------------------------------------------------------------

# Understanding a Singly Linked List

Each node contains:

-   Data
-   Reference to the next node

``` text
head
 |
 v
+----+     +----+     +----+     +----+
| 10 | --> | 20 | --> | 30 | --> | 40 | --> null
+----+     +----+     +----+     +----+
                                  ^
                                  |
                                tail
```

------------------------------------------------------------------------

# Before and After Reverse

## Before

``` text
head
 |
 v
+----+     +----+     +----+     +----+
| 10 | --> | 20 | --> | 30 | --> | 40 | --> null
+----+     +----+     +----+     +----+
                                  ^
                                  |
                                tail
```

## After

``` text
                                  head
                                   |
                                   v
+----+     +----+     +----+     +----+
| 40 | --> | 30 | --> | 20 | --> | 10 | --> null
+----+     +----+     +----+     +----+
                                  ^
                                  |
                                tail
```

------------------------------------------------------------------------

# Mermaid Diagram (GitHub Supported)

``` mermaid
flowchart LR
H([head]) --> A[10]
A --> B[20]
B --> C[30]
C --> D[40]
D --> N((null))
T([tail]) --> D
```

After reversal

``` mermaid
flowchart LR
H([head]) --> D[40]
D --> C[30]
C --> B[20]
B --> A[10]
A --> N((null))
T([tail]) --> A
```

------------------------------------------------------------------------

# Reverse Algorithm

1.  Save references to the original head and tail.
2.  Set `preCursorNode = null`.
3.  Set `cursorNode = head`.
4.  Repeat until `cursorNode == null`:
    -   Save `cursorNode.next` into `postCursorNode`.
    -   Reverse the current node's `next`.
    -   Advance all pointers.
5.  Set `head = oldTailNode`.
6.  Set `tail = oldHeadNode`.

Time Complexity: **O(n)**

Space Complexity: **O(1)**

------------------------------------------------------------------------

# Reverse Method

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

# Iteration Walkthrough

## Iteration 1

``` text
Reversed
10 -> null

Remaining
20 -> 30 -> 40 -> null
```

## Iteration 2

``` text
Reversed
20 -> 10 -> null

Remaining
30 -> 40 -> null
```

## Iteration 3

``` text
Reversed
30 -> 20 -> 10 -> null

Remaining
40 -> null
```

## Iteration 4

``` text
Reversed
40 -> 30 -> 20 -> 10 -> null

Remaining
—
```

------------------------------------------------------------------------

# Complexity of Operations

  Operation     Time   Space
  ------------- ------ -------
  Constructor   O(1)   O(1)
  addFirst()    O(1)   O(1)
  addLast()     O(1)   O(1)
  add(index)    O(n)   O(1)
  reverse()     O(n)   O(1)
  toString()    O(n)   O(n)

------------------------------------------------------------------------

# Common Interview Questions

## Why are three pointers required?

Without storing the next node before reversing the current link, the
remainder of the list becomes unreachable.

## Why is the time complexity O(n)?

Every node must be visited at least once. Therefore no algorithm can
reverse a linked list faster than **O(n)**.

## Why is the space complexity O(1)?

Only a fixed number of reference variables are used regardless of the
list size.

## Can it be implemented recursively?

Yes. A recursive solution exists, but it uses **O(n)** call stack space,
whereas the iterative solution uses only **O(1)** extra space.

------------------------------------------------------------------------

# Recursive vs Iterative

  Feature                   Iterative   Recursive
  ------------------------- ----------- ------------
  Time                      O(n)        O(n)
  Extra Space               O(1)        O(n)
  Stack Overflow Risk       No          Yes
  Preferred in Production   ✅ Yes      Usually No

------------------------------------------------------------------------

# Summary

-   Visits every node exactly once.
-   Uses three pointers (`previous`, `current`, `next`).
-   Updates `head` and `tail` in constant time.
-   Runs in **O(n)** time.
-   Uses **O(1)** extra memory.
-   Recommended for interviews and production code.
