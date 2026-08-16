package org.jk.dsa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListTest {

    @Test
    void testReverse1() {
        LinkedList<Integer> linkedList = new LinkedList<>(10);
        linkedList.add(20);
        linkedList.add(30);
        Assertions.assertEquals("[10, 20, 30]", linkedList.toString());
        System.out.println(linkedList);
        linkedList.reverse();
        System.out.println(linkedList);
        Assertions.assertEquals("[30, 20, 10]", linkedList.toString());
    }

    @Test
    void testReverse2() {
        LinkedList<Integer> linkedList = new LinkedList<>(10);
        linkedList.add(20);
        Assertions.assertEquals("[10, 20]", linkedList.toString());
        System.out.println(linkedList);
        linkedList.reverse();
        System.out.println(linkedList);
        Assertions.assertEquals("[20, 10]", linkedList.toString());
    }

    @Test
    void testReverse3() {
        LinkedList<Integer> linkedList = new LinkedList<>(10);
        linkedList.add(20);
        linkedList.add(30);
        linkedList.add(40);
        linkedList.add(50);
        Assertions.assertEquals("[10, 20, 30, 40, 50]", linkedList.toString());
        System.out.println(linkedList);
        linkedList.reverse();
        System.out.println(linkedList);
        Assertions.assertEquals("[50, 40, 30, 20, 10]", linkedList.toString());
    }
}
