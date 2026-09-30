package com.jk.explore.doublylinkedlistgenericrecursive;

/**
 * A generic doubly linked list, holding data of any type {@code T}, whose walks are written
 * recursively.
 *
 * <p>It combines doubly-linked-list-generic ({@code Node<T>}, references, {@code equals}) with
 * doubly-linked-list-recursive (every walk a recursion along {@code next} or {@code prev}).
 *
 * <p>The same structure as the doubly-linked-list project: nodes with {@code prev} and {@code next},
 * and a list holding {@code head} and {@code tail}. Every walk is a recursion: forwards, a list is
 * empty or a node followed by the list from {@code node.next}; backwards, the same with
 * {@code node.prev}, starting from the tail. Finding a node is recursive; linking and unlinking it
 * is the usual O(1) pointer work, because in a doubly linked list a node already reached can be
 * changed without its predecessor. Work at both ends needs no walk and no recursion. Each call still
 * waiting keeps a call-stack frame, so a recursive walk over n nodes uses O(n) extra space.
 */
public class GenericRecursiveDoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private final StepCounter steps = new StepCounter();

    public Node<T> head() {
        return head;
    }

    public Node<T> tail() {
        return tail;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public StepCounter steps() {
        return steps;
    }

    /** Display forward: this node, then the list from node.next. O(n) time and stack. */
    public String displayForward() {
        StringBuilder s = new StringBuilder("head -> ");
        if (head == null) {
            s.append("null");
        }
        displayForward(head, s);
        return s.append(" <- tail").toString();
    }

    private void displayForward(Node<T> node, StringBuilder s) {
        if (node == null) {            // base case: past the tail
            return;
        }
        steps.enter();
        steps.step();
        s.append(node.data).append(node.next != null ? " <-> " : "");
        displayForward(node.next, s);
        steps.exit();
    }

    /** Display backward: the same recursion, starting at the tail and following prev. O(n) time and stack. */
    public String displayBackward() {
        StringBuilder s = new StringBuilder("tail -> ");
        if (tail == null) {
            s.append("null");
        }
        displayBackward(tail, s);
        return s.append(" <- head").toString();
    }

    private void displayBackward(Node<T> node, StringBuilder s) {
        if (node == null) {            // base case: past the head
            return;
        }
        steps.enter();
        steps.step();
        s.append(node.data).append(node.prev != null ? " <-> " : "");
        displayBackward(node.prev, s);
        steps.exit();
    }

    /** Count: 0 for the empty list, otherwise 1 + the count of the rest. O(n) time and stack. */
    public int count() {
        return count(head);
    }

    private int count(Node<T> node) {
        if (node == null) {
            return 0;
        }
        steps.enter();
        steps.step();
        int c = 1 + count(node.next);
        steps.exit();
        return c;
    }

    /** Insertion at the beginning: 3 pointers, no walk. O(1). */
    public Node<T> insertAtBeginning(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            steps.pointer(2);
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            steps.pointer(3);
        }
        return newNode;
    }

    /** Insertion at the end, through the tail: 3 pointers, no walk. O(1). */
    public Node<T> insertAtEnd(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
            steps.pointer(2);
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            steps.pointer(3);
        }
        return newNode;
    }

    /**
     * Insertion at position {@code pos}: find the node before it recursively, then set four
     * pointers. O(pos) time and stack.
     */
    public Node<T> insertAtPosition(int pos, T data) {
        if (pos == 0) {
            return insertAtBeginning(data);
        }
        Node<T> before = nodeAt(pos - 1);
        if (before == tail) {
            return insertAtEnd(data);
        }
        Node<T> after = before.next;
        Node<T> newNode = new Node<>(data);
        newNode.prev = before;
        newNode.next = after;
        before.next = newNode;
        after.prev = newNode;
        steps.pointer(4);
        return newNode;
    }

    /** Deletion at the beginning. O(1). */
    public T deleteAtBeginning() {
        if (head == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        T data = head.data;
        deleteNode(head);
        return data;
    }

    /** Deletion at the end, through the tail. O(1). */
    public T deleteAtEnd() {
        if (tail == null) {
            throw new IllegalStateException("underflow: the list is empty");
        }
        T data = tail.data;
        deleteNode(tail);
        return data;
    }

    /** Deletion by key: find the node recursively, then unlink it in O(1). O(n) time and stack. */
    public boolean deleteByKey(T key) {
        Node<T> node = find(head, key);
        if (node == null) {
            return false;
        }
        deleteNode(node);
        return true;
    }

    /** Unlinks a node already reached: two pointer changes, no search. O(1). */
    public void deleteNode(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        steps.pointer(2);
    }

    /** Search: -1 in the empty list, pos if this node holds the key, otherwise search the rest. O(n) time and stack. */
    public int search(T key) {
        return search(head, key, 0);
    }

    private int search(Node<T> node, T key, int pos) {
        if (node == null) {
            return -1;
        }
        steps.enter();
        steps.compare();
        int result = node.data.equals(key) ? pos : search(node.next, key, pos + 1);
        steps.exit();
        return result;
    }

    private Node<T> find(Node<T> node, T key) {
        if (node == null) {
            return null;               // base case: not found
        }
        steps.enter();
        steps.compare();
        Node<T> result = node.data.equals(key) ? node : find(node.next, key);
        steps.exit();
        return result;
    }

    /** The node at position {@code pos}: this node at 0, otherwise position pos - 1 of the rest. O(pos). */
    public Node<T> nodeAt(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException("no node at position " + pos);
        }
        return nodeAt(head, pos);
    }

    private Node<T> nodeAt(Node<T> node, int pos) {
        if (node == null) {
            throw new IndexOutOfBoundsException("no node at this position");
        }
        if (pos == 0) {
            return node;               // base case
        }
        steps.enter();
        steps.step();
        Node<T> result = nodeAt(node.next, pos - 1);
        steps.exit();
        return result;
    }

    public T get(int pos) {
        return nodeAt(pos).data;
    }

    /**
     * Reverses the list: swap this node's prev and next, then reverse from the old next; finally
     * swap head and tail. O(n) time and stack.
     */
    public void reverse() {
        reverse(head);
        Node<T> t = head;
        head = tail;
        tail = t;
        steps.pointer(2);
    }

    private void reverse(Node<T> node) {
        if (node == null) {            // base case: past the old tail
            return;
        }
        steps.enter();
        Node<T> oldNext = node.next;
        node.next = node.prev;
        node.prev = oldNext;
        steps.pointer(2);
        reverse(oldNext);
        steps.exit();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (Node<T> current = head; current != null; current = current.next) {
            s.append(current.data).append(current.next != null ? " <-> " : "");
        }
        return s.append(']').toString();
    }
}
