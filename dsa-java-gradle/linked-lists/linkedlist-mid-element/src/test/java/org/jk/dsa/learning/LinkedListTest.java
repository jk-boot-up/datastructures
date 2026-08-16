package org.jk.dsa.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListTest {

    @Test
    void midElement() {
        LinkedList<Integer> linkedList = new LinkedList<>(10);
        linkedList.add(20);
        linkedList.add(30);
        System.out.println(linkedList.findMidElement());
        Assertions.assertEquals(20, linkedList.findMidElement());
    }
}
