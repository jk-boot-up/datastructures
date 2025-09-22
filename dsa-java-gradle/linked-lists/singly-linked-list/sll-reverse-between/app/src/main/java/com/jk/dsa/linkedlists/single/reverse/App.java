package com.jk.dsa.linkedlists.single.reverse;

public class App {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.append(0);
        list.append(10);
        list.append(20);
        list.append(30);
        list.append(40);
        list.append(50);
        list.append(60);
        list.append(70);
        System.out.println("-----------------------------------------------------");
        System.out.println("Sample 1: Original List: " + list);
        list.reverseBetween(0, 7);
        System.out.println("Linked List after reversing between positions 0 and 7: " + list);
        list.reverseBetween(0, 7);
        System.out.println("-----------------------------------------------------");
        System.out.println("Sample 2: Original List: " + list);
        list.reverseBetween(1, 6);
        System.out.println("Linked List after reversing between positions 1 and 6: " + list);
        list.reverseBetween(1, 6);
        System.out.println("-----------------------------------------------------");
        System.out.println("Sample 3: Original List: " + list);
        list.reverseBetween(0, 6);
        System.out.println("Linked List after reversing between positions 0 and 6: " + list);
        list.reverseBetween(0, 6);
        System.out.println("-----------------------------------------------------");
        System.out.println("Sample 4: Original List: " + list);
        list.reverseBetween(1, 7);
        System.out.println("Linked List after reversing between positions 1 and 7: " + list);
        list.reverseBetween(1, 7);
        System.out.println("-----------------------------------------------------");
    }
}
