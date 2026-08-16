package org.jk.dsa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    @Getter
    private int size;

    public LinkedList(T data) {
        Node<T> node = new Node<>(data);
        head = tail = node;
        size++;
    }

    public void add(T data) {
        addLast(data);
    }

    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        if(size == 0) {
            head = tail = node;
            size++;
            return;
        }
        node.next = head;
        head = node;
        size++;
    }

    public void addLast(T data) {
        Node<T> node = new Node<T>(data);
        if(isEmpty()) {
            head = tail = node;
            size++;
            return;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    public void add(int index, T data) {
        if(index < 0 || index > size) {
            throw new RuntimeException("index out of bound");
        }
        if(index == 0) {
            addFirst(data);
            return;
        }
        if(index == size) {
            addLast(data);
            return;
        }
        int cursorIndex = 0;
        Node<T> indexNode = head;
        Node<T> indexBeforeNode = null;
        while(indexNode != null && cursorIndex < index) {
            indexBeforeNode = indexNode;
            cursorIndex++;
            indexNode = indexNode.next;
        }
        Node<T> node = new Node<>(data);
        if(indexBeforeNode != null) {
            indexBeforeNode.next = node;
        }
        node.next = indexNode;
        size++;
    }

    public void reverse() {
        if(isEmpty()) {
            return;
        }
        if(size == 1) {
            return;
        }
        Node<T> cursorNode = head;
        Node<T> preCursorNode = null;
        Node<T> postCursorNode = null;
        Node<T> oldHeadNode = head;
        Node<T> oldTailNode = tail;
        while(cursorNode != null) {
            postCursorNode = cursorNode.next;
            cursorNode.next = preCursorNode;
            preCursorNode = cursorNode;
            cursorNode = postCursorNode;
        }
        head = oldTailNode;
        tail = oldHeadNode;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Getter
    @Setter
    @ToString
    static class Node<T> {
        T data;
        @ToString.Exclude
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isEmpty()) {
            sb.append("[ ]");
            return sb.toString();
        }
        sb.append("[");
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
}
