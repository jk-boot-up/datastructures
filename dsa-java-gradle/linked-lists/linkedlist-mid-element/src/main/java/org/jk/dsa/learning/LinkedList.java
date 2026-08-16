package org.jk.dsa.learning;

public class LinkedList<T> {

    Node<T> head;
    Node<T> tail;
    int size;

    public LinkedList() {

    }

    public LinkedList(T data) {
        Node<T> node = new Node<>(data);
        head = tail = node;
        size++;
    }

    public void addLast(T data) {
        Node<T> node = new Node<>(data);
        if(isEmpty()) {
            head = tail = node;
            size++;
            return;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        if(isEmpty()) {
            head = tail = node;
            size++;
            return;
        }
        node.next = head;
        head = node;
        size++;
    }

    public void add(T data) {
        addLast(data);
    }

    public void addAt(int index, T data) {
        if(index < 0 || index > size) {
            throw new RuntimeException(String.format("Index out of bound for size: %s", size));
        }
        if(index == 0) {
            addFirst(data);
            return;
        }
        if(index == size) {
            addLast(data);
            return;
        }
        Node<T> node = new Node<>(data);
        Node<T> cursorNode = head;
        Node<T> preCursorNode = null;
        int cursorIndex = 0;
        while(cursorNode != null && cursorIndex < index) {
            preCursorNode = cursorNode;
            cursorNode = cursorNode.next;
            cursorIndex++;
        }
        if(preCursorNode != null) {
            preCursorNode.next = node;
            node.next = cursorNode;
            size++;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T findMidElement() {

        if(isEmpty()) {
            return null;
        }
        if(head.next == null) {
            return head.data;
        }
        Node<T> slow = head;
        Node<T> fast = head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isEmpty()) {
            sb.append("[ ]");
            return sb.toString();
        }
        sb = sb.append("[");
        Node<T> cursor = head;
        while (cursor != null) {
            sb.append(cursor.data);
            if(cursor.next != null) {
                sb.append(", ");
            }
            cursor = cursor.next;
        }
        sb.append("]");
        return sb.toString();
    }
    
    static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }
}
