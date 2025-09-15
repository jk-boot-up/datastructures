package com.jk.explore.datastructures.linkedlist.single;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedListTest {

    @Test
    void append() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);
        linkedList.printList();
        assertEquals("10\n20\n30\n", bos.toString());
        assertEquals(3, linkedList.size());
    }

    @Test
    void prepend() {
        LinkedList linkedList = new LinkedList();
        linkedList.prepend(50);
        linkedList.prepend(40);
        linkedList.prepend(30);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);
        linkedList.printList();
        assertEquals("30\n40\n50\n", bos.toString());
        assertEquals(3, linkedList.size());
    }

    void assertElements(int[] elements, LinkedList linkedList) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(bos);
        System.setOut(printStream);
        if(elements == null || elements.length == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < elements.length; i++) {
            sb.append(elements[i]).append("\n");
        }
        linkedList.printList();
        assertEquals(sb.toString(), bos.toString());
    }

    @Test
    void reverse1() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.reverse1();
        assertElements(new int[]{40, 30, 20, 10}, linkedList);
        linkedList.reverse1();
        assertElements(new int[]{10, 20, 30, 40}, linkedList);
        linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.reverse1();
        assertElements(new int[] {20, 10}, linkedList);
    }

    @Test
    void findKthElementFromLast() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        assertEquals(50, linkedList.findKthElementFromLast(1));
        assertEquals(40, linkedList.findKthElementFromLast(2));
        assertEquals(30, linkedList.findKthElementFromLast(3));
        assertEquals(20, linkedList.findKthElementFromLast(4));
        assertEquals(10, linkedList.findKthElementFromLast(5));
    }

    @Test
    void findMiddleNode() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        LinkedList.Node middleNode = linkedList.findMiddleNode();
        System.out.println(middleNode.value);
        linkedList.append(20);
        middleNode = linkedList.findMiddleNode();
        System.out.println(middleNode.value);
        linkedList.append(30);
        middleNode = linkedList.findMiddleNode();
        System.out.println(middleNode.value);
        linkedList.append(40);
        middleNode = linkedList.findMiddleNode();
        System.out.println(middleNode.value);
        linkedList.append(50);
        middleNode = linkedList.findMiddleNode();
        System.out.println(middleNode.value);
    }

    @Test
    void reverseBetween() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        linkedList.append(70);
        linkedList.printList();
        linkedList.reverseBetween(0, 6);
        linkedList.reverseBetween(1, 6);
        linkedList.reverseBetween(3, 5);
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
        linkedList.printList();
        linkedList.reverseBetween(0, 6);
        linkedList.reverseBetween(1, 6);
        linkedList.reverseBetween(3, 5);
    }

    @Test
    void reverse3() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        linkedList.append(70);
        linkedList.printList();
        linkedList.reverse3();
        linkedList.printList();

    }

}
