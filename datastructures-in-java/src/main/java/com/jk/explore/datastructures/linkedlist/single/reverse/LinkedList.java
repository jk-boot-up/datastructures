package com.jk.explore.datastructures.linkedlist.single.reverse;

import lombok.ToString;

public class LinkedList {

    private Node head;
    private Node tail;
    private int length;

    public LinkedList() {
    }

    public int getLength() {
        return length;
    }

    public void append(int value) {
        Node node = new Node(value);
        if(length == 0) {
            head = node;
            tail = node;
            length++;
            return;
        }
        tail.next = node;
        tail = node;
        length++;
    }

    public void reverseInBetween(int start, int end) {
        if(start < 0) {
            throw new IllegalArgumentException("start Index < 0 : " + start);
        }
        if(length == 0 || length == 1) {
            return;
        }
        Node dummyHead = new Node();
        dummyHead.next = head;
        Node previous = null;
        // starting index convention 0; head element at 0 index;
        int i=0;
        previous = dummyHead;
        Node currentNode = head;
        while (i < start) {
            previous = previous.next;
            currentNode = currentNode.next;
            i++;
        }
        Node startNode = currentNode;
        previous.next = null;
        Node currentPrevious = null;
        int j = start;
        Node temp = null;
        while(j <= end) {
            temp = currentNode.next;
            currentNode.next = currentPrevious;
            currentPrevious = currentNode;
            currentNode = temp;
            j++;
        }

        if(dummyHead.next == null) {
            startNode.next = currentNode;
            head = currentPrevious;
        } else {
            previous.next = currentPrevious;
            startNode.next = currentNode;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        Node current = head;
        while (current != null) {
            sb.append(current.getValue()).append(System.lineSeparator());
            current = current.next;
        }
        return sb.toString();
    }

    @ToString
    public class Node {
        int value;

        @ToString.Exclude
        Node next;

        public Node() {

        }

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
