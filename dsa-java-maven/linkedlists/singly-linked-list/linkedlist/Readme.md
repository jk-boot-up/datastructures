# Singly Linked List -- Java Implementation

A simple and well-documented **Singly Linked List** implementation in
**Java**, designed for **Data Structures & Algorithms (DSA)** learning,
interview preparation, and clean code practice.

------------------------------------------------------------------------

## 📌 Overview

This project provides a custom implementation of a **singly linked
list** that stores integer values.\
It maintains references to both the **head** and **tail** nodes,
enabling efficient insertions at both ends.

The implementation focuses on: - Correct pointer manipulation - Edge
case handling - Clear time complexity guarantees - Readable and
maintainable code

------------------------------------------------------------------------

## 🧱 Project Structure

    src
    └── main
        └── java
            └── com
                └── jk
                    └── explore
                        └── dsa
                            └── single
                                └── LinkedList.java

------------------------------------------------------------------------

## ✨ Features

✔ Insert elements at the beginning and end\
✔ Insert elements at a specific index\
✔ Remove elements by value\
✔ Remove the last element\
✔ Search for elements using `contains()`\
✔ Maintains list length efficiently\
✔ Clean `toString()` representation

------------------------------------------------------------------------

## 🧠 Data Structure Design

### Node

Each node contains: - `data` → integer value - `next` → reference to the
next node

### LinkedList

The list maintains: - `head` → first node\
- `tail` → last node\
- `length` → number of elements

------------------------------------------------------------------------

## 🔧 Supported Operations

Operation         Method               Time Complexity
  ----------------- -------------------- -----------------
Insert at end     `addLast(int)`       O(1)
Insert at start   `addFirst(int)`      O(1)
Insert at index   `insert(int, int)`   O(n)
Remove by value   `remove(int)`        O(n)
Remove last       `removeLast()`       O(n)
Search            `contains(int)`      O(n)
Get size          `getLength()`        O(1)

------------------------------------------------------------------------

## 🧪 Example Usage

``` java
LinkedList list = new LinkedList();

list.addLast(10);
list.addLast(20);
list.addFirst(5);

System.out.println(list);
// [  5, 10, 20 ]

list.insert(2, 15);
System.out.println(list);
// [  5, 10, 15, 20 ]

list.remove(10);
System.out.println(list);
// [  5, 15, 20 ]

System.out.println(list.contains(15));
// true
```

------------------------------------------------------------------------

## 🚨 Edge Case Handling

-   Adding to an empty list
-   Removing from an empty list
-   Removing the only element
-   Inserting at index `0`
-   Inserting at index `length`
-   Invalid index insertion (ignored safely)

------------------------------------------------------------------------

## 📦 Dependencies

This project uses **Lombok** to reduce boilerplate code.

### Lombok Annotations Used

-   `@Getter`
-   `@Setter`
-   `@ToString`
-   `@EqualsAndHashCode`

Ensure Lombok is enabled in your IDE.

------------------------------------------------------------------------

## 🎯 Learning Objectives

This project helps you understand: - Singly linked list internals -
Pointer/reference manipulation - Time vs space trade-offs - Defensive
programming - Clean API and method design

------------------------------------------------------------------------

## 🚀 Possible Enhancements

-   Convert to a generic implementation (`LinkedList<T>`)
-   Add iterator support
-   Add unit tests using JUnit 5
-   Implement reverse, middle, and cycle detection
-   Make the list thread-safe

------------------------------------------------------------------------

## 📄 License

This project is intended for educational purposes and may be freely
used, modified, and shared.

------------------------------------------------------------------------

## 🙌 Author

**Jayasekhar K**\
Java \| Spring Boot \| DSA Enthusiast
