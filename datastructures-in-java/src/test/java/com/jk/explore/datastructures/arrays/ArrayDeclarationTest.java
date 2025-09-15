package com.jk.explore.datastructures.arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ArrayDeclarationTest {

    @Test
    void declare() {
        int [] actual = new int[3];
        actual[0] = 10;
        actual[1] = 20;
        actual[2] = 30;
        int [] expected = {10, 20, 30};
        for(int i=0; i < actual.length; i++) {
            System.out.println(actual[i]);
        }
        for(int i : expected) {
            System.out.println(i);
        }
        assertArrayEquals(actual, expected);
        Stream.of(expected).forEach(System.out::println);
        List<Integer> integers = List.of(10, 20, 30);
        integers.forEach(System.out::println);
        Arrays.stream(actual).forEach(System.out::println);
    }



    @Test
    void declaration2() {
        int[] numbers1 = {10, 20, 30};
        int[] numbers2 = {10, 20, 30};
        assertArrayEquals(numbers1, numbers2);
    }

}
