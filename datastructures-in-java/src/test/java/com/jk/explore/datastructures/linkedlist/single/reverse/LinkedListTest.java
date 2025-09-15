package com.jk.explore.datastructures.linkedlist.single.reverse;

import org.junit.jupiter.api.Test;

public class LinkedListTest {

    @Test
    void reverse1() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        System.out.println("Before +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
        linkedList.reverseInBetween(1, 2);
        System.out.println("After +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
    }

    @Test
    void reverse2() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        System.out.println("Before +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
        linkedList.reverseInBetween(0, 3);
        System.out.println("After +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
    }

    @Test
    void reverse3() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        System.out.println("Before +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
        linkedList.reverseInBetween(0, 2);
        System.out.println("After +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
    }

    @Test
    void reverse4() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        System.out.println("Before +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
        linkedList.reverseInBetween(1, 3);
        System.out.println("After +++++++++++++++++++++++++++++");
        System.out.println(linkedList);
    }
}
