package com.jk.dsa.linkedlists.single.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedListTest {

    @Test
    void reverseBetween1() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        linkedList.append(70);
        System.out.println("Original List:");
        System.out.println(linkedList);
        linkedList.reverseBetween(2, 5);
        assertEquals("[ 10 -> 20 -> 60 -> 50 -> 40 -> 30 -> 70 ]", linkedList.toString());
        System.out.println("Reversed List between 2 and 5:");
        System.out.println(linkedList);
    }

    @Test
    void reverseBetween2() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        linkedList.append(70);
        System.out.println("Original List:");
        System.out.println(linkedList);
        linkedList.reverseBetween(0, 5);
        assertEquals("[ 60 -> 50 -> 40 -> 30 -> 20 -> 10 -> 70 ]", linkedList.toString());
        System.out.println("Reversed List between 0 and 5:");
        System.out.println(linkedList);
    }

    @Test
    void reverseBetween3() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        linkedList.append(70);
        System.out.println("Original List:");
        System.out.println(linkedList);
        linkedList.reverseBetween(1, 6);
        assertEquals("[ 10 -> 70 -> 60 -> 50 -> 40 -> 30 -> 20 ]", linkedList.toString());
        System.out.println("Reversed List between 1 and 6:");
        System.out.println(linkedList);
    }

    @Test
    void reverseBetween4() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        linkedList.append(70);
        System.out.println("Original List:");
        System.out.println(linkedList);
        linkedList.reverseBetween(0, 6);
        assertEquals("[ 70 -> 60 -> 50 -> 40 -> 30 -> 20 -> 10 ]", linkedList.toString());
        System.out.println("Reversed List between 0 and 6:");
        System.out.println(linkedList);
    }

    @Test
    void reverseBetween5() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        System.out.println("Original List:");
        System.out.println(linkedList);
        linkedList.reverseBetween(0, 1);
        assertEquals("[ 20 -> 10 ]", linkedList.toString());
        System.out.println("Reversed List between 0 and 1:");
        System.out.println(linkedList);
    }

}
