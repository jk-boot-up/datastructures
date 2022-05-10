package com.jk.explore.datastructures;

public class _2_Queue {
    private int[] queueEntries;
    private int capacity;
    private int size;
    private int front;
    private int rear;

    public _2_Queue(int capacity) {
        this.capacity = capacity;
        queueEntries = new int[capacity];
        front = 0;
        rear = -1;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean enqueue(int element) {
        if(isFull()) {
            return false;
        }
        rear = (rear + 1) % capacity;
        queueEntries[rear] = element;
        size++;
        return true;
    }

    public int dequeue() {
        if(isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        int element = queueEntries[front];
        size--;
        front = (front + 1);
        return element;
    }

}
