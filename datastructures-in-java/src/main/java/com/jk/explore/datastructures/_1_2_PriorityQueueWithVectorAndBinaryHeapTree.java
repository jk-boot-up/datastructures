package com.jk.explore.datastructures;

import java.util.Vector;

public class _1_2_PriorityQueueWithVectorAndBinaryHeapTree {

    private Vector<Integer> vector;
    private int capacity;

    public _1_2_PriorityQueueWithVectorAndBinaryHeapTree(int capacity) {
        vector = new Vector<>(capacity);
        this.capacity = capacity;
    }

    public void push(Integer element) {
        vector.addElement(element);
        int elementIndex = vector.size() - 1;
        heapifyUp(elementIndex);
    }

    public Integer pop() {
        if(vector.size() == 0) {
            return null;
        }
        Integer element = vector.get(0);
        if(vector.size() == 1) {
            vector.remove(0);
        } else {
            vector.setElementAt(vector.lastElement(), 0);
            vector.remove(vector.size() - 1);
            heapifyDown(0);
        }
        return element;
    }

    private void heapifyDown(int elementIndex) {
        int leftChildIndex = getLeftChildIndex(elementIndex);
        int rightChildIndex = getRightChildIndex(elementIndex);
        int largeElementIndex = elementIndex;
        if(leftChildIndex < vector.size() && vector.elementAt(largeElementIndex) < vector.elementAt(leftChildIndex)) {
            largeElementIndex = leftChildIndex;
        }
        if(rightChildIndex < vector.size() && vector.elementAt(largeElementIndex) < vector.elementAt(rightChildIndex)) {
            largeElementIndex = rightChildIndex;
        }
        if(largeElementIndex != elementIndex) {
            swapElementsAt(largeElementIndex, elementIndex);
            heapifyDown(largeElementIndex);
        }
    }

    private int getParentIndex(int elementIndex) {
        if(elementIndex == 0) {
            return 0;
        }
        return (elementIndex - 1) / 2;
    }

    private int getLeftChildIndex(int elementIndex) {
        return (2 * elementIndex) + 1;
    }

    private int getRightChildIndex(int elementIndex) {
        return (2 * elementIndex) + 2;
    }

    private void swapElementsAt(int indexOne, int indexTwo) {
        Integer temp = vector.elementAt(indexOne);
        vector.setElementAt(vector.elementAt(indexTwo) , indexOne);
        vector.setElementAt(temp, indexTwo);
    }

    public void heapifyUp(int childIndex) {
        int parentIndex = getParentIndex(childIndex);
        if(childIndex > 0 && vector.elementAt(parentIndex) < vector.elementAt(childIndex)) {
            swapElementsAt(parentIndex, childIndex);
            heapifyUp(parentIndex);
        }

    }



}

