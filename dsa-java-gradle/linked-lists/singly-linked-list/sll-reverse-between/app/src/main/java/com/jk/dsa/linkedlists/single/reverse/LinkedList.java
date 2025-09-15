package com.jk.dsa.linkedlists.single.reverse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class LinkedList {

    private Node head;

    private Node tail;

    public LinkedList() {

    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        sb.append("[ ");
        while(current != null) {
            sb.append(current.value);
            current = current.next;
            if(current != null) {
                sb.append(" -> ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public void append(int value) {
        Node newNode = new Node(value);
        if(head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    public void reverseBetween(int start, int end) {
        if(head == null || start == end || start < 0 || end < 0 || start > end) {
            return;
        }
        Node leftPartitionHead = null;
        Node leftPartitionTail = null;
        Node reversePartitionHead = null;
        Node reversePartitionTail = null;
        Node rightPartitionHead = null;
        Node current = head;
        int index = 0;
        while(index < start) {
            if(leftPartitionHead == null) {
                leftPartitionHead = current;
            }
            index++;
            leftPartitionTail = current;
            current = current.next;
        }
        if(leftPartitionTail != null) {
            leftPartitionTail.next = null;
        }
        Node prev = null;
        Node temp = null;
        reversePartitionTail = current;
        while(index <= end) {
            temp = current.next;
            current.next = prev;
            prev = current;
            reversePartitionHead = current;
            current = temp;
            index++;
        }
        rightPartitionHead = current;
        if(leftPartitionTail != null) {
            leftPartitionTail.next = reversePartitionHead;
        } else {
            head = reversePartitionHead;
        }
        reversePartitionTail.next = rightPartitionHead;
        if(rightPartitionHead == null) {
            tail = reversePartitionTail;
        }
    }



    public void reverseBetween2(int left, int right) {
        if (head == null || left == right) {
            return;
        }

        Node dummy = new Node(0);
        dummy.next = head;
        Node prev = dummy;

        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        Node current = prev.next;
        Node next = null;
        Node prevSublist = null;

        for (int i = 0; i <= right - left; i++) {
            next = current.next;
            current.next = prevSublist;
            prevSublist = current;
            current = next;
        }

        prev.next.next = current;
        prev.next = prevSublist;

        head = dummy.next;
    }
    

    @Getter
    @NoArgsConstructor
    @ToString
    static class Node {

        int value;

        @Setter
        @ToString.Exclude
        Node next;

        Node(int value) {
            this.value = value;
        }

    }
}
