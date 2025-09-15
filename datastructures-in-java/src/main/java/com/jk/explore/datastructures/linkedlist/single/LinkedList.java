package com.jk.explore.datastructures.linkedlist.single;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
public class LinkedList {

    private Node head;
    private Node tail;
    private int length;


    public void reverse1() {
        if(length == 1 || isEmpty()) {
            return;
        }
        Node currentNode = head;
        Node oldHead = head;
        Node left = tail;
        Node right = null;
        Node oldTail = tail;
        while (currentNode != oldTail) {
            head = head.next;
            left.next = currentNode;
            currentNode.next = right;
            right = currentNode;
            currentNode = head;
        }
        tail = oldHead;
    }
    public void append(int value) {
        Node node = new Node(value);
        if(isEmpty()) {
            head = node;
            tail = node;
            length = 1;
        } else {
            tail.next = node;
            tail = node;
            length++;
        }
    }

    public int size() {
        return length;
    }

    public void prepend(int value) {
        Node node = new Node(value);
        if(isEmpty()) {
            head = node;
            tail = node;
            length = 1;
        } else {
            node.next = head;
            head = node;
            length++;
        }
    }

    public int get(int index) {
        if(index < 0 || index >= length) {
            throw new RuntimeException("Index out of bound: "+index);
        }
        if(isEmpty()) {
            throw new RuntimeException("Empty List");
        }
        int currentIndex = 0;
        Node currentNode = head;
        while (currentIndex < index) {
            currentIndex++;
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    public Node removeFirstNode() {
        if(isEmpty()) {
            return null;
        }
        if(length == 1) {
            Node target = head;
            head = null;
            tail = null;
            length = 0;
            return target;
        } else {
            Node target = head;
            head = head.next;
            length--;
            return target;
        }
    }

    public int removeFirst() {
        if(isEmpty()) {
            throw new RuntimeException("Empty List");
        }
        Node first = removeFirstNode();
        return first.value;
    }

    public Node removeLastNode() {
        if(isEmpty()) {
            return null;
        }
        if(length == 1) {
            Node node = head;
            head = null;
            tail = null;
            return node;
        }
        Node currentNode = head;
        Node preLastNode = null;
        while(currentNode != tail) {
            preLastNode = currentNode;
            currentNode = currentNode.next;
        }
        tail = preLastNode;
        return currentNode;
    }

    public int removeLast() {
        if(isEmpty()) {
            throw new RuntimeException("Empty List");
        }
        Node lastNode = removeLastNode();
        return lastNode.value;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public void printList() {
        if(!isEmpty()) {
            Node currentNode = head;
            while (currentNode != null) {
                System.out.println(currentNode.value);
                currentNode = currentNode.next;
            }
        }
    }

    public Node findKthNodeFromLast(int k) {
        if(head == null || k < 1) {
            return null;
        }
        Node lazy;
        Node fast;
        lazy = head;
        fast = head;
        int i = 1;
        while(i < k & fast != null) {
            fast = fast.next;
            i++;
        }
        while(fast.next != null) {
            lazy = lazy.next;
            fast = fast.next;
        }
        return lazy;
    }

    public Node findMiddleNode() {
        if(head == null) {
            return null;
        }
        Node slow = head;
        Node fast = head;
        while(fast != null) {
            // if next 2 nodes are not null then jump by 2 nodes
            if(fast.next !=null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                continue;
            }
            // do not jump for slow. Jumping for fast alone to terminate loop
            fast = fast.next;
        }
        return slow;
    }


    public int findKthElementFromLast(int k) {
        Node kthNode = findKthNodeFromLast(k);
        if(kthNode != null) {
            return kthNode.value;
        }
        throw new RuntimeException("No kth element");
    }

    public void reverseBetween(int start, int end) {
        if(head == null) {
            return;
        }
        if(start >= end || start < 0) {
            return;
        }
        Node dummyStart = new Node();
        dummyStart.next = head;
        Node p1left = dummyStart;
        Node p1right = dummyStart;
        Node p2left = dummyStart;
        Node p2right = null;
        Node p3left = null;
        Node p3right = tail;
        int currentIndex = -1;
        Node currentNode = dummyStart;
        // find p2 left
        while (currentIndex < start) {
            p1right = currentNode;
            p2left = p1right.next;
            currentNode = currentNode.next;
            currentIndex++;
        }
        System.out.println("-----------------------------------------");
        System.out.println("-----------------------------------------");
        System.out.println("p1left: " + p1left);
        System.out.println("p1right: " + p1right);
        System.out.println("p2left: " + p2left);
        System.out.println("-----------------------------------------");
        System.out.println("-----------------------------------------");
        p1right.next = null;
    }

    public void reverse3() {
        if(head == null || head.next == null) {
            return;
        }
        Node previous = null;
        Node current = head;
        Node next = current.next;
        while (current != null) {
            current.next = previous;
            previous = current;
            current = next;
            if(next != null) {
                next = next.next;
            }
        }
        tail = head;
        head = previous;
    }


    @Getter
    @Setter
    @ToString
    class Node {

        int value;
        @ToString.Exclude
        Node next;

        public Node(int value) {
            this.value = value;
        }

        public Node() {

        }

    }
}
