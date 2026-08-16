# Reversing a Singly Linked List in Java

## Overview

This project demonstrates how to reverse a **generic singly linked
list** in Java using an **iterative approach**.

The implementation works for any data type because the linked list is
implemented using Java Generics.

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

## Understanding a Singly Linked List

Each node contains:

-   Data
-   A reference to the next node

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

Unlike an array, a linked list stores elements in separate memory
locations. Each node maintains a reference to the next node, forming a
chain.

------------------------------------------------------------------------

## Goal of the Reverse Operation

Original list

``` text
head
 |
 v
10 --> 20 --> 30 --> 40 --> null
                   ^
                   |
                 tail
```

After reversing

``` text
                                  head
                                   |
                                   v
40 --> 30 --> 20 --> 10 --> null
                   ^
                   |
                 tail
```

Every `next` reference between adjacent nodes is reversed.

------------------------------------------------------------------------

## Reverse Algorithm

The algorithm traverses the linked list exactly once while reversing the
direction of every `next` reference.

Your implementation uses three pointer variables:

  -----------------------------------------------------------------------
  Variable               Common Name                 Purpose
  ---------------------- --------------------------- --------------------
  `preCursorNode`        Previous                    Points to the
                                                     previous node

  `cursorNode`           Current                     Points to the
                                                     current node being
                                                     processed

  `postCursorNode`       Next                        Stores the next node
                                                     before reversing the
                                                     link
  -----------------------------------------------------------------------

The names used in the implementation (`preCursorNode`, `cursorNode`, and
`postCursorNode`) correspond to the commonly used pointer names
**previous**, **current**, and **next** found in most linked list
algorithms.

Before starting the reversal, references to the original `head` and
`tail` are saved. These references are later used to update the list
after all links have been reversed.

``` java
Node<T> oldHeadNode = head;
Node<T> oldTailNode = tail;
```

After all links have been reversed, the head and tail are updated.

``` java
head = oldTailNode;
tail = oldHeadNode;
```

Updating the `head` and `tail` references takes constant time because
only two references are reassigned.

------------------------------------------------------------------------

## Algorithm

1.  Save the original head and tail.
2.  Initialize:
    -   `preCursorNode` to `null`
    -   `cursorNode` to `head`
3.  Repeat until `cursorNode` becomes `null`:
    -   Save the next node in `postCursorNode`.
    -   Reverse the current node's `next` reference.
    -   Move `preCursorNode` forward.
    -   Move `cursorNode` to the saved next node.
4.  Set `head` to the original tail.
5.  Set `tail` to the original head.

------------------------------------------------------------------------

## Step-by-Step Example

Consider the following linked list:

``` text
10 -> 20 -> 30 -> 40 -> null
```

Initially

``` text
preCursorNode  = null
cursorNode     = 10
postCursorNode = null
```

------------------------------------------------------------------------

### Iteration 1

Current list

``` text
null    10 -> 20 -> 30 -> 40
        ^
     cursor
```

Save the next node.

``` text
postCursorNode = 20
```

Reverse the current node's `next` reference.

``` text
10 -> null
```

Advance the pointers.

``` text
preCursorNode = 10
cursorNode = 20
```

Current state

``` text
10 -> null

20 -> 30 -> 40
```

------------------------------------------------------------------------

### Iteration 2

Current list

``` text
10 <- 20 -> 30 -> 40
```

Save the next node.

``` text
postCursorNode = 30
```

Reverse the current node's `next` reference.

``` text
20 -> 10
```

Advance the pointers.

``` text
preCursorNode = 20
cursorNode = 30
```

Current state

``` text
20 -> 10 -> null

30 -> 40
```

------------------------------------------------------------------------

### Iteration 3

Save the next node.

``` text
postCursorNode = 40
```

Reverse the current node's `next` reference.

``` text
30 -> 20 -> 10
```

Advance the pointers.

``` text
cursorNode = 40
```

------------------------------------------------------------------------

### Iteration 4

Save the next node.

``` text
postCursorNode = null
```

Reverse the current node's `next` reference.

``` text
40 -> 30 -> 20 -> 10 -> null
```

Advance the pointers.

``` text
cursorNode = null
```

The loop terminates because every node has now been processed.

------------------------------------------------------------------------

## Updating Head and Tail

Originally

``` text
head
 |
 v
10 --> 20 --> 30 --> 40 --> null
                   ^
                   |
                 tail
```

After reversing

``` java
head = oldTailNode;
tail = oldHeadNode;
```

Result

``` text
                                  head
                                   |
                                   v
40 --> 30 --> 20 --> 10 --> null
                   ^
                   |
                 tail
```

The original head becomes the last node after reversal, so it naturally
becomes the new tail.

------------------------------------------------------------------------

## Reverse Method

``` java
public void reverse() {

    if (isEmpty()) {
        return;
    }

    if (size == 1) {
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

## Why Are Three Pointers Required?

Suppose we directly reverse the link without first saving the next node.

``` java
cursorNode.next = preCursorNode;
```

Consider the following list:

``` text
10 -> 20 -> 30
```

If we immediately execute

``` text
10 -> null
```

the references to

``` text
20 -> 30
```

are lost permanently because no variable points to the remaining nodes.

By first saving

``` java
postCursorNode = cursorNode.next;
```

we preserve access to the remainder of the list before reversing the
current link.

------------------------------------------------------------------------

## Dry Run Summary

  Iteration   Reversed Portion    Remaining Portion
  ----------- ------------------- -------------------
  Start       ---                 10 → 20 → 30 → 40
  1           10                  20 → 30 → 40
  2           20 → 10             30 → 40
  3           30 → 20 → 10        40
  4           40 → 30 → 20 → 10   ---

------------------------------------------------------------------------

## Time Complexity

### Reverse Operation

The algorithm visits every node exactly once.

``` text
10 -> 20 -> 30 -> ... -> n
```

If the linked list contains **n** nodes, the total running time is

``` text
O(n)
```

where **n** is the number of nodes.

No algorithm can reverse a linked list faster than **O(n)** because
every node must be visited at least once. Therefore, this solution is
asymptotically optimal.

------------------------------------------------------------------------

### Space Complexity

The algorithm uses only a fixed number of reference variables.

``` text
preCursorNode
cursorNode
postCursorNode
oldHeadNode
oldTailNode
```

No additional linked list or array is created.

Therefore, the extra space required is

``` text
O(1)
```

which is constant regardless of the number of nodes.

------------------------------------------------------------------------

## Complexity of All Operations

  Operation      Time Complexity   Space Complexity
  -------------- ----------------- ------------------
  Constructor    O(1)              O(1)
  `addFirst()`   O(1)              O(1)
  `addLast()`    O(1)              O(1)
  `add()`        O(1)              O(1)
  `add(index)`   O(n)              O(1)
  `reverse()`    O(n)              O(1)
  `isEmpty()`    O(1)              O(1)
  `getSize()`    O(1)              O(1)
  `toString()`   O(n)              O(n)\*

### Notes

-   **Constructor:** Creates the initial node and initializes both the
    `head` and `tail`.
-   **`toString()`:** Traverses the list once to build the string
    representation. The resulting string requires **O(n)** additional
    space.

------------------------------------------------------------------------

## Why Is the Reverse Algorithm Efficient?

The iterative approach is considered optimal because:

-   Every node is visited exactly once.
-   No recursion is used, eliminating the risk of stack overflow.
-   No additional linked list is created.
-   Only a fixed number of reference variables are used.
-   It achieves the optimal time complexity of **O(n)**.
-   It requires only **O(1)** additional space.

------------------------------------------------------------------------

## Summary

-   A singly linked list is reversed by changing the direction of every
    `next` reference.
-   Three pointer variables (`previous`, `current`, and `next`) ensure
    that no nodes are lost while reversing the links.
-   The original head becomes the new tail.
-   The original tail becomes the new head.
-   The algorithm visits each node exactly once, resulting in **O(n)**
    time complexity.
-   It uses only constant extra memory (**O(1)**), making it the optimal
    iterative solution for reversing a singly linked list.
