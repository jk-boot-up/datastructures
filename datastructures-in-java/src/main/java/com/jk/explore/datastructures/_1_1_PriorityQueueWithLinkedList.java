package com.jk.explore.datastructures;


public class _1_1_PriorityQueueWithLinkedList<T extends Comparable<T>> {

    final int limit;
    private int currentSize = 0;
    private LinkedList<T> linkedList = new LinkedList<T>();

    public _1_1_PriorityQueueWithLinkedList(int limit) {
        this.limit = limit;
    }

    public void add(T data) {
        if(limit == currentSize) {
            throw new RuntimeException("Q is full");
        }
        if(linkedList.isEmpty()) {
            linkedList.add(data);
            currentSize++;
        } else {
            LinkedList.Node<T> prev = null;
            LinkedList.Node<T> current = linkedList.first;
            T currentSmall = current.data;
            while(currentSmall.compareTo(data) < 1 && current != null) {
                prev = current;
                current = current.next;
                if(current != null) {
                    currentSmall = current.data;
                }
            }
            if(currentSmall.compareTo(data) < 0) {
                linkedList.addAtLast(data);
                currentSize++;
                return;
            }
            if(currentSmall.compareTo(data) > 0) {
                LinkedList.Node<T> newNode = LinkedList.Node.of(data);
                prev.next = newNode;
                newNode.next = current;
                currentSize++;
                return;
            }
        }
    }

    public T remove() {
        if(currentSize == 0) {
            return null;
        }
        return linkedList.removeLast();
    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean isFull() {
        return false;
    }

    protected static class LinkedList<T> {

        private Node<T> first;
        private Node<T> last;
        private int size;

        public void add(T element) {
            if(first == null) {
                first = new Node<>(element);
                last = first;
            }
            addAtLast(element);
        }

        public boolean isEmpty() {
            return first == null;
        }

        public void addAtFirst(T data) {
            Node<T> newNode = Node.of(data);
            Node<T> temp = first;
            first = newNode;
            newNode.next = temp;
            if(last == null) {
                last = first;
            }
        }

        public void addAtLast(T data) {
            Node<T> newNode = Node.of(data);
            if(last == null) {
                first = last = newNode;
                return;
            }
            last.next = newNode;
            last = newNode;
        }

        public T removeFirst() {
            if(isEmpty()) {
                return null;
            }
            Node<T> temp = first;
            if(first == last) {
                first = last = null;
            } else {
                first = first.next;
            }
            return temp.data;
        }

        public T removeLast() {
            if(isEmpty()) {
                return null;
            }
            Node<T> temp = last;
            if(first == last) {
                first = last = null;
            } else {
                Node<T> currentLast = first;
                while(currentLast.next != last) {
                    currentLast = currentLast.next;
                }
                last = currentLast;
            }
            return temp.data;
        }


        protected static class Node<T> {
            T data;
            private Node<T> next;
            private Node(T data) {
                this.data = data;
            }

            public static <T> Node<T> of(T data) {
                return new Node<>(data);
            }

        }

    }



}
