package com.jk.explore.datastructures;

public class LinkedList {

    Node head;
    Node tail;
    private int length;

    public LinkedList() {
        length = 0;
        // default
    }

    public LinkedList(int value) {
        Node node = new Node();
        node.value = value;
        head = node;
        tail = node;
        this.length = 1;
    }

    public int getLength() {
        return length;
    }

    public void printList() {
        Node current = head;
        while(current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    public int getHead() {
        if(head == null) {
            throw new IndexOutOfBoundsException("Empty Linked List");
        }
        System.out.println(head.value);
        return head.value;
    }

    public int getTail() {
        if(tail == null) {
            throw new IndexOutOfBoundsException("Empty Linked List");
        }
        System.out.println(tail.value);
        return tail.value;
    }

    Node getTailNode() {
        return tail;
    }

    Node getHeadNode() {
        return head;
    }

    public void append(int value) {
        Node newNode = new Node();
        newNode.value = value;
        if(length == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        length++;
    }

    Node removeLastNode() {
        Node lastNode = null;
        // Empty Linked List
        if(tail == null) {
            return null;
        }
        // Linked List with one Node
        if(head == tail) {
            length--;
            lastNode = tail;
            head = null;
            tail = null;
            return lastNode;
        }
        // Linked List with size greater than 1
        Node preLast = head;
        Node cursor = head;
        while (cursor.next != null) {
            preLast = cursor;
            cursor = cursor.next;
            lastNode = cursor;
        }
        tail = preLast;
        tail.next = null;
        length--;
        return lastNode;
    }

    public int removeLast() {
        Node lastNode = removeLastNode();
        if(lastNode == null) {
            throw new IndexOutOfBoundsException("Empty Linked List");
        }
        return lastNode.value;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if(head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        length++;
    }

    Node removeFirstNode() {
        Node firstNode = null;
        if(head == null) {
            return null;
        }
        if(length ==1) {
            firstNode = head;
            head = null;
            tail = null;
            length = 0;
            return firstNode;
        }
        firstNode = head;
        head = firstNode.next;
        length--;
        return firstNode;
    }

    int removeFirst() {
        Node firstNode = removeFirstNode();
        if(firstNode == null) {
            throw new IndexOutOfBoundsException("Empty Linked List");
        }
        return firstNode.value;
    }

    Node getNode(int targetIndex) {
        if(targetIndex < 0 || targetIndex >= length) {
            throw new IndexOutOfBoundsException("Index can't be < 0 or grater than or equal to it's current length");
        }
        Node cursor = head;
        int cursorIndex = 0;
        while (cursorIndex < targetIndex) {
            cursor = cursor.next;
            cursorIndex++;
        }
        return cursor;
    }

    public int get(int index) {
        Node node = getNode(index);
        if(node != null) {
            return node.value;
        }
        throw new IndexOutOfBoundsException("No element found at index: "+index);
    }

    void insertNode(int index, int value) {
        if(index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index can't be < 0 or > length of the list");
        }
        if(index == 0) {
            prepend(value);
            length++;
            return;
        }
        if(index == length) {
            append(value);
            length++;
            return;
        }
        Node newNode = new Node(value);
        Node preTargetNode = getNode(index-1);
        newNode.next = preTargetNode.next;
        preTargetNode.next = newNode;
        length++;
    }

    boolean set(int index, int value) {
        if(index < 0 || index >= length) {
            return false;
        }
        Node targetNode = getNode(index);
        if(targetNode != null) {
            targetNode.value = value;
            return true;
        }
        return false;
    }

    class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
        }
        Node() {
            // Default
        }
    }
}
