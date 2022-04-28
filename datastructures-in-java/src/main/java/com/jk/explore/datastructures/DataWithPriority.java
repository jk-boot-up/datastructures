package com.jk.explore.datastructures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

@Getter
@RequiredArgsConstructor
public class DataWithPriority<T> {
    LinkedList l = new LinkedList();
    private final T data;
    private final Integer priority;

    private void test() {
        l.add(Integer.valueOf(10));
    }

}
