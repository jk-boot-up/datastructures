package com.jk.explore.datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class _1_1_PriorityQueueWithLinkedListTest {

    @Test
    public void addAtFirst_Node() {
        _1_1_PriorityQueueWithLinkedList.LinkedList<Integer> cut = new _1_1_PriorityQueueWithLinkedList.LinkedList<>();
        // 4, 3, 2, 1, 0
        IntStream.range(0, 5).forEach(i -> cut.addAtFirst(i));
        List<Integer> elements = IntStream.range(0, 5).mapToObj(i -> 5 - 1- i).collect(Collectors.toList());
        List<Integer> results = IntStream.range(0, 5).mapToObj(i -> cut.removeFirst()).collect(Collectors.toList());
        Assertions.assertEquals(elements, results);
        Assertions.assertTrue(cut.isEmpty());
    }

    @Test
    public void addAtLast_Node() {
        _1_1_PriorityQueueWithLinkedList.LinkedList<Integer> cut = new _1_1_PriorityQueueWithLinkedList.LinkedList<>();
        // 4, 3, 2, 1, 0
        IntStream.range(0, 5).forEach(i -> cut.addAtFirst(i));
        List<Integer> elements = IntStream.range(0, 5).mapToObj(i -> 5 - 1- i).collect(Collectors.toList());
        List<Integer> results = IntStream.range(0, 5).mapToObj(i -> cut.removeFirst()).collect(Collectors.toList());
        Assertions.assertEquals(elements, results);
        Assertions.assertTrue(cut.isEmpty());
    }

    @Test
    public void addToPriorityQ() {
        _1_1_PriorityQueueWithLinkedList<Integer> cut = new _1_1_PriorityQueueWithLinkedList<>(10);
        // 4, 3, 2, 1, 0
        cut.add(10);
        cut.add(11);
        cut.add(12);
        cut.add(8);
        Assertions.assertEquals(cut.size(), 4);
        Assertions.assertEquals(cut.remove(), 12);
    }

}
