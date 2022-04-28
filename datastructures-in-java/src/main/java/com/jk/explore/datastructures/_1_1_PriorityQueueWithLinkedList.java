package com.jk.explore.datastructures;


public class _1_1_PriorityQueueWithLinkedList {

    final int size;

    public _1_1_PriorityQueueWithLinkedList(int size) {
        this.size = size;
    }

    public <T> void add(DataWithPriority<T> data) {

    }

    public <T> DataWithPriority<T> remove() {
        return null;
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
