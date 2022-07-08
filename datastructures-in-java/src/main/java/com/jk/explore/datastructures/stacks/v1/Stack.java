package com.jk.explore.datastructures.stacks.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * @see java.util.Stack
 * @param <E> Stack element type
 */
public class Stack<E> {

    private List<Object> elements;

    private int capacity;

    private int top = -1;

    public Stack() {
        this(10);
    }

    public Stack(int capacity) {
        if(capacity < 1) {
            throw new RuntimeException("Stack size can't be less than 1");
        }
        this.capacity = capacity;
        elements = new ArrayList<>(capacity);
    }

    public E pop() {
        if(isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return (E) elements.get(top);
    }

    public E push(E element) {
        if(isFull()) {
            throw new RuntimeException("Stack is full");
        }
        elements.add(++top, element);
        return element;
    }

    public E peek() {
        if(isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return (E) elements.get(top);
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity -1;
    }

}
