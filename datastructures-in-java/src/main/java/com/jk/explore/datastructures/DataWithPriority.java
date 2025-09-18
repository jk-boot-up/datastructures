package com.jk.explore.datastructures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

@Getter
@RequiredArgsConstructor
public class DataWithPriority<P> implements Comparable<DataWithPriority<P>> {

//    private final P data;
//    private final Integer priority;

    @Override
    public int compareTo(DataWithPriority<P> o) {
        return 1;
        //return this.getPriority().compareTo(o.getPriority());
    }
}
