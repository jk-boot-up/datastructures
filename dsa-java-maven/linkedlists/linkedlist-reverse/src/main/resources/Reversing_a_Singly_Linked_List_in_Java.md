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

```{=html}
<!-- -->
```
    head
     |
     v
    +----+     +----+     +----+     +----+
    | 10 | --> | 20 | --> | 30 | --> | 40 | --> null
    +----+     +----+     +----+     +----+
                                      ^
                                      |
                                    tail

Unlike an array, a linked list stores elements in separate memory
locations. Each node maintains a reference to the next node, forming a
chain.

------------------------------------------------------------------------

## Goal of the Reverse Operation

Original list

    head
     |
     v
    10 --> 20 --> 30 --> 40 --> null
                       ^
                       |
                     tail

After reversing

                                      head
                                       |
                                       v
    40 --> 30 --> 20 --> 10 --> null
                       ^
                       |
                     tail

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

## Updating Head and Tail

Originally

    head
     |
     v
    10 --> 20 --> 30 --> 40 --> null
                       ^
                       |
                     tail

After reversing

``` java
head = oldTailNode;
tail = oldHeadNode;
```

Result

                                      head
                                       |
                                       v
    40 --> 30 --> 20 --> 10 --> null
                       ^
                       |
                     tail

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

    10 -> 20 -> 30

If we immediately execute

    10 -> null

the references to

    20 -> 30

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

-   **Time Complexity:** **O(n)**
-   **Space Complexity:** **O(1)**

Every node is visited exactly once, making this algorithm asymptotically
optimal.

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
  `toString()`   O(n)              O(n)

------------------------------------------------------------------------

## Summary

-   A singly linked list is reversed by changing the direction of every
    `next` reference.
-   Three pointer variables (`previous`, `current`, and `next`) ensure
    that no nodes are lost.
-   The original head becomes the new tail.
-   The original tail becomes the new head.
-   The algorithm runs in **O(n)** time using **O(1)** extra space.
