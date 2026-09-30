# Singly Linked List (Recursive) — Cheat Sheet

**Seen recursively, a list is either empty or a node followed by a smaller list, so every operation handles one node and calls itself on node.next until the empty list or its target; changing operations return the new first node, as in node.next = deleteByKey(node.next, key); the time is the same as with loops, the extra space is one frame per node visited.**

![count(oak tree) waits for count(old well) ... down to count(null) = 0](images/structure.png)

| | |
| --- | --- |
| **What it is** | A recursive singly linked list treats a list as empty or a node followed by a smaller list, so each operation handles one node and calls itself on node.next, returning the new first node when it changes the list; the time is unchanged and the stack grows by one frame per node. |
| **Everyday picture** | Each clue-finder asks the next finder to handle the rest of the trail |
| **Use it when** | Printing a list in reverse, or any operation whose work happens on the way back; Short lists, where the depth is small; Learning: the same patterns are used on trees, where the depth is only the height |
| **Avoid it when** | Long lists: a million nodes overflow Java's call stack. Use the loops of `singly-linked-list`; When O(1) extra space matters |
| **Already in Java** | Nothing: `java.util.LinkedList` uses loops |

## Costs

| Operation | What it does | Time: best / average / worst | Extra space (call stack) | Same operation as a loop | Steps counted in the demo |
| --- | --- | --- | --- | --- | --- |
| Display / display in reverse | This node, then the rest (or the rest, then this node) | O(n) / O(n) / O(n) | O(n) | O(1); in reverse, O(n) for a stack | depth 6 for 6 clues |
| Count | 0 for null, otherwise 1 + count(node.next) | O(n) / O(n) / O(n) | O(n) | O(1) | 6, depth 6 |
| Insert at beginning / delete at beginning | Change head: no recursion needed | O(1) / O(1) / O(1) | O(1) | O(1) | 2 and 1 pointer changes |
| Insert at end | null becomes the new node; otherwise node.next = insertAtEnd(node.next) | O(n) / O(n) / O(n) | O(n) | O(1) | depth 6 |
| Insert at position | pos 0: new node in front; otherwise insert at pos - 1 of the rest | O(1) at 0 / O(n) / O(n) | O(pos) | O(1) | depth 1 for position 1 |
| Delete at end | The last node is replaced by null | O(n) / O(n) / O(n) | O(n) | O(1) | depth 6 on 7 nodes |
| Delete by key | If this node matches, return node.next; otherwise delete from the rest | O(1) / O(n) / O(n) | O(n) | O(1) | 4 comparisons, depth 4 |
| Search / get | Match here, or search the rest with pos + 1 | O(1) / O(n) / O(n) | O(n) | O(1) | 5 comparisons, depth 5; get(3) depth 3 |
| Reverse | Reverse the rest, then node.next.next = node; node.next = null | O(n) / O(n) / O(n) | O(n) | O(1) | depth 5 on 6 nodes |

## Compared with related structures

The recursive singly linked list against the loop version and its neighbours (n nodes):

| Operation | Recursive singly list | Singly list (loops) | Recursive static array |
| --- | --- | --- | --- |
| Insert / delete at the beginning | O(1), no recursion | O(1) | O(n) shifts, O(n) stack |
| Insert / delete at the end | O(n) time and stack | O(n) time, O(1) space | O(1) at the end |
| Search, count, display | O(n) time and stack | O(n) time, O(1) space | O(n) time and stack |
| Display in reverse | O(n) time and stack, natural | O(n) and an explicit stack | O(n) time and stack |
| Delete by key | no prev pointer needed | needs prev | shifts |
| A million elements | StackOverflowError | fine | StackOverflowError |

## Watch out for

- **Forgetting to store the returned node**: Always `node.next = insertAtEnd(node.next, data);` and `head = insertAtEnd(head, data);`.
- **No base case for the empty list**: Check `node == null` first in every recursive method.
- **In reverse, forgetting `node.next = null`**: After `node.next.next = node`, set `node.next = null`.
- **Recursing over long lists**: Use loops for long lists; recursion where the depth is small.
