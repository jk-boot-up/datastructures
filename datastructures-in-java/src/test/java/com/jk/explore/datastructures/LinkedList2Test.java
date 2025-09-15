package com.jk.explore.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedList2Test {

    @Test
    void append() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        assertEquals(1, linkedList.getLength());
        assertEquals(10, linkedList.getHead());
        linkedList.append(20);
        assertEquals(10, linkedList.getHeadNode().value);
        assertEquals(20, linkedList.getTail());
        assertEquals(20, linkedList.getTailNode().value);
        assertEquals(2, linkedList.getLength());
    }

    @Test
    void removeLastNode() {
        LinkedList linkedList = new LinkedList(1);
        assertEquals(1, linkedList.removeLastNode().value);
        linkedList.append(2);
        linkedList.append(3);
        assertEquals(3, linkedList.removeLastNode().value);
        assertEquals(2, linkedList.removeLastNode().value);
        assertNull(linkedList.removeLastNode());
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        linkedList.append(4);
        linkedList.append(8);
        assertEquals(5, linkedList.getLength());
        assertEquals(8, linkedList.removeLastNode().value);
        assertEquals(4, linkedList.getLength());
    }

    @Test
    void removeLast() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(40);
        linkedList.append(50);
        linkedList.append(60);
        assertEquals(60, linkedList.removeLast());
        assertEquals(50, linkedList.removeLast());
        assertEquals(40, linkedList.removeLast());
        assertEquals(0, linkedList.getLength());
    }

    @Test
    void prepend() {
        LinkedList linkedList = new LinkedList(10);
        linkedList.append(20);
        linkedList.prepend(5);
        linkedList.printList();
        assertEquals(5, linkedList.head.value);
    }

    @Test
    void removeFirstNode() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        assertEquals(10, linkedList.removeFirstNode().value);
        assertEquals(20, linkedList.removeFirst());
        assertEquals(0, linkedList.getLength());
    }

    @Test
    void removeFirst() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        assertEquals(20, linkedList.removeFirst());
        assertEquals(30, linkedList.removeFirst());
        assertEquals(40, linkedList.removeFirst());
        assertEquals(0, linkedList.getLength());
    }

    @Test
    void getNode() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.append(50);
        assertEquals(30, linkedList.getNode(2).value);
        assertEquals(10, linkedList.getNode(0).value);
        assertEquals(50, linkedList.getNode(4).value);
        assertEquals(20, linkedList.getNode(1).value);
        assertEquals(40, linkedList.getNode(3).value);
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.getNode(5)
        );
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.getNode(-1));
    }

    @Test
    void insertNode() {
        LinkedList linkedList = new LinkedList();
        linkedList.append(10);
        linkedList.append(20);
        linkedList.append(30);
        linkedList.append(40);
        linkedList.insertNode(3, 35);
        assertEquals(35, linkedList.get(3));
        assertEquals(40, linkedList.get(4));
        assertEquals(30, linkedList.get(2));
        linkedList.insertNode(0, 5);
        assertEquals(5, linkedList.get(0));
        assertEquals(10, linkedList.get(1));
        assertEquals(40, linkedList.get(5));
    }
}
