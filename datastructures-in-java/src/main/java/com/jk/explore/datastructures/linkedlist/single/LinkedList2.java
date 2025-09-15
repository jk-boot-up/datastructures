package com.jk.explore.datastructures.linkedlist.single;

import lombok.*;

public class LinkedList2 {

    @Getter
    private Node head;
    @Getter
    private Node tail;
    @Getter
    private int length;

    public LinkedList2(int value) {
        Node node = new Node(value);
        this.head = node;
        this.tail = node;
        length = 1;
    }

    public LinkedList2() {
        this.length = 0;
    }

    void prepend(Node node) {
        if(length == 0) {
            head = node;
            tail = node;
            length = 1;
            return;
        }
        node.next = head;
        head = node;
        length++;
    }

    void append(Node node) {
        if(length == 0) {
            prepend(node);
            return;
        }
        tail.next = node;
        tail = node;
        length++;
    }

    void append(int value) {
        Node node = new Node(value);
        append(node);
    }

    Node findNode(int index) {
        if(index < 0 || index >= length || length == 0) {
            return null;
        }
        int currentIndex = 0;
        Node currentNode = head;
        while(currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        return currentNode;
    }

    int incrementLength() {
        return length++;
    }

    public void printList() {
        if(length == 0) {
            System.out.println("Linked List is empty");
            return;
        }
        Node currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    Node removeFirstNode() {
        if(length == 0) {
            return null;
        }
        Node firstNode = head;
        if(length == 1) {
            head = null;
            tail = null;
            length = 0;
            return firstNode;
        }
        head = head.next;
        return firstNode;
    }

    Node removeLastNode() {
        if(length == 0) {
            return null;
        }
        Node lastNode = tail;
        tail = findNode(length-1);
        return lastNode;
    }

    public void reverse() {
        if(length == 1) {
            return;
        }
        Node oldHead = head;
        Node oldTail = tail;
        Node currentNode = head;
        Node previouslyInserted = null;
        LinkedList2 reversed = new LinkedList2();
        for(int i=0; i < length; i++) {
            if(tail == oldTail) {
                append(currentNode);
                previouslyInserted = currentNode;
            }
            append(currentNode);
        }

    }

    public void reverse2() {
        if(length == 0 || length == 1) {
            return;
        }

        Node left = tail;
        Node right = null;
        Node currentNode = null;
        for(int i=0; i < (length-1); i++) {
            currentNode = head;
            head = head.next;
            left.next = currentNode;
            currentNode.next = right;
            if(right == null) {
                tail = currentNode;
            }
            right = currentNode;
        }
    }


    //@ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @Getter
    @Setter
    static class Node {

        int value;

        Node next;

        public Node(int value) {
            this.value = value;
        }
    }



}
