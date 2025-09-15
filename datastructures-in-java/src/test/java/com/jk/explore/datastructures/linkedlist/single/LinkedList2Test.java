package com.jk.explore.datastructures.linkedlist.single;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LinkedList2Test {

    @Test
    void printListWhenNonEmpty() {
        LinkedList2 linkedList2 = new LinkedList2(10);
        linkedList2.append(20);
        linkedList2.append(30);
        linkedList2.append(40);
        linkedList2.append(50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream listPrintStream = new PrintStream(outputStream);
        System.setOut(listPrintStream);
        linkedList2.printList();
        String expectedOutput = "10\n20\n30\n40\n50\n";
        Assertions.assertEquals(outputStream.toString(), expectedOutput);
    }

    @Test
    void reverse2() {
        LinkedList2 linkedList2 = new LinkedList2(10);
        linkedList2.append(20);
        linkedList2.append(30);
        linkedList2.append(40);
        linkedList2.append(50);
        linkedList2.append(60);
        linkedList2.append(70);
        linkedList2.reverse2();
        linkedList2.printList();
    }
}
