# Singly Linked List (Generic, Recursive) — Cheat Sheet

**The generic recursive singly linked list holds any type T in Node<T>s, matches with equals, and writes every walk as a recursion on node.next, returning the new first node when it changes the list; the time is that of the loop version and the extra space is one frame per node visited.**

![search(new Clue("the red gate", 60)): each call asks equals, then passes the rest on](images/structure.png)

| | |
| --- | --- |
| **What it is** | The generic recursive singly linked list matches Node<T> data with equals and walks by calling itself on node.next, so it holds any type, keeps the loop version's time, and uses a frame per node visited. |
| **Everyday picture** | A line of finders comparing cards of any kind |
| **Use it when** | Learning the generic recursive style used for trees; Short lists, or operations whose work is on the way back, such as display in reverse |
| **Avoid it when** | Long lists: one frame per node overflows the call stack. Use `singly-linked-list-generic` |
| **Already in Java** | Nothing: `java.util.LinkedList<E>` uses loops |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | Steps counted in the demo |
| --- | --- | --- | --- | --- | --- |
| Display / display in reverse / count | This node and the rest, recursively | O(n) / O(n) / O(n) | O(n) | O(1) | depth 6 for 6 nodes |
| Insert / delete at beginning | Change head; no recursion | O(1) / O(1) / O(1) | O(1) | O(1) | 2 and 1 pointer changes |
| Insert at end / at position | Recurse to the empty list (or position 0 of the rest), return the new node | O(n) / O(n) / O(n) | O(n), O(pos) | O(1) | depth 6; depth 1 for position 1 |
| Delete at end | The last node is replaced by null | O(n) / O(n) / O(n) | O(n) | O(1) | depth 6 on 7 nodes |
| Delete by key | The matching call returns node.next (equals) | O(1) / O(n) / O(n) | O(n) | O(1) | 4 equals calls, depth 4 |
| Search / get | equals here, or search the rest | O(1) / O(n) / O(n) | O(n) | O(1) | 3 equals calls, depth 3 |
| Reverse | Reverse the rest, hang this node on its end | O(n) / O(n) / O(n) | O(n) | O(1) | depth 5 on 6 nodes |

## Compared with related structures

The four singly-linked-list projects side by side (n nodes):

| Property | Generic, recursive (this) | Generic, loops | String, recursive | String, loops |
| --- | --- | --- | --- | --- |
| Element types | any T | any T | String | String |
| Insert / delete at the beginning | O(1) | O(1) | O(1) | O(1) |
| Walks (search, count, at end) | O(n) time and stack | O(n) time, O(1) space | O(n) time and stack | O(n) time, O(1) space |
| Matching | equals | equals | equals | equals |
| A million nodes | StackOverflowError | fine | StackOverflowError | fine |

## Watch out for

- **Not storing the returned node**: `node.next = insertAtEnd(node.next, data);` and `head = ...` at the top.
- **Matching with `==`**: Use `equals`.
- **No base case for the empty list**: Check `node == null` first.
- **Recursing over long lists**: Use the loop version for long lists.
