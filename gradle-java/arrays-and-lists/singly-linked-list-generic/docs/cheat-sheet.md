# Singly Linked List (Generic) — Cheat Sheet

**A generic singly linked list is the singly linked list written once with a type parameter T: each Node<T> holds a reference to a T and a next pointer, nodes are matched with equals, and every operation keeps its cost: O(1) at the beginning, O(n) to reach a position.**

![GenericSinglyLinkedList<Clue>: each Node<Clue> refers to a Clue and to the next node](images/structure.png)

| | |
| --- | --- |
| **What it is** | A generic singly linked list is the singly linked list with a type parameter: each Node<T> refers to a T and to the next node, elements are matched with equals, and every operation keeps its cost. |
| **Everyday picture** | A trail of signposts that can point at anything |
| **Use it when** | A linked list of objects of one type, when inserting and deleting at the beginning is common; The building block for generic stacks and queues |
| **Avoid it when** | Reading by position: use an array or `ArrayList<E>`; Deleting at the end or walking backwards often: use a doubly linked list |
| **Already in Java** | `java.util.LinkedList<E>` (generic in the same way, and doubly linked) |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space | Steps counted in the demo |
| --- | --- | --- | --- | --- |
| Display / count | Follow next pointers from head to null | O(n) / O(n) / O(n) | O(1) | 6 steps for 6 clues |
| Insert at beginning | newNode.next = head; head = newNode | O(1) / O(1) / O(1) | O(1) | 2 pointer changes |
| Insert at end | Walk to the last node, link after it | O(n) / O(n) / O(n) | O(1) | n - 1 steps |
| Insert at position | Walk to prev; newNode.next = prev.next; prev.next = newNode | O(1) at 0 / O(n) / O(n) | O(1) | 2 pointer changes |
| Delete at beginning | head = head.next | O(1) / O(1) / O(1) | O(1) | 1 pointer change |
| Delete at end | Walk to the second-to-last node, set its next to null | O(n) / O(n) / O(n) | O(1) | 3 steps on 5 nodes |
| Delete by key / at position | Walk with prev and current (equals), then prev.next = current.next | O(1) at the head / O(n) / O(n) | O(1) | 4 comparisons, 1 pointer change |
| Search / get | Compare each node's data with equals / follow pos pointers | O(1) / O(n) / O(n) | O(1) | 5 comparisons to find position 4 |
| Reverse | prev, current, next: turn each next pointer around | O(n) / O(n) / O(n) | O(1) | 7 pointer changes on 6 nodes |

## Compared with related structures

The generic singly linked list against its neighbours (n nodes):

| Property | Generic singly list | Singly list of String | Generic dynamic array |
| --- | --- | --- | --- |
| Element types | any T | String only | any T |
| Access position i | O(n) | O(n) | O(1) |
| Insert / delete at the beginning | O(1) | O(1) | O(n) shifts |
| Insert at the end | O(n) (O(1) with a tail) | O(n) (O(1) with a tail) | O(1) amortised |
| Matches elements with | equals | equals | equals |
| Needs a cast inside | no: nodes are created as Node<T> | no | yes: (T[]) new Object[n] |
| Java's own | LinkedList<E> (doubly linked) |  | ArrayList<E> |

## Watch out for

- **Matching with `==`**: Use `equals`; for your own types, a record or a correct `equals` method.
- **A raw `Node` or raw list**: Always write `Node<T>` and `GenericSinglyLinkedList<Clue>`.
- **Setting the two insertion pointers in the wrong order**: `newNode.next = prev.next` first, then `prev.next = newNode`.
- **Changing an object after inserting it**: Store immutable objects, such as records, or remember that the list shares them.
