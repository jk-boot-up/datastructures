# 📘 Generic Singly Linked List (Java)

## 📖 Definition

A **Linked List** is a linear data structure where elements (nodes) are stored in non-contiguous memory locations.  
Each node contains:
- Data
- A reference (pointer) to the next node in the sequence

Unlike arrays, linked lists do not require contiguous memory and allow efficient insertion and deletion.

---

## 📖 Overview

This project provides a **generic implementation of a Singly Linked List in Java**.

It is designed for:
- ✅ Learning Data Structures
- ✅ Teaching concepts in interviews/classrooms
- ✅ Understanding time & space trade-offs

---

## 🧱 Data Structure Design

Each node contains:
- `data` → Value of type `T`
- `next` → Reference to next node

```
Head → [10] → [20] → [30] → null
                           ↑
                          Tail
```

---

## 🚀 Features

- Generic (`<T>`) implementation
- Efficient **O(1)** insertion at head & tail (when reference is known)
- Index-based operations
- Safe boundary checks
- Clean and readable code

---

## 🛠️ Supported Operations

| Operation        | Description |
|-----------------|------------|
| `add(T data)`   | Add element at end |
| `addFirst(T)`   | Add element at beginning |
| `addAt(index, T)` | Insert at specific position |
| `get(index)`    | Retrieve element |
| `removeFirst()` | Remove first element |
| `removeLast()`  | Remove last element |
| `removeAt(index)` | Remove element at index |
| `size()`        | Get size |
| `print()`       | Print list |

---

## ⚠️ Important Complexity Note

Insertion and deletion in a linked list are:

- **O(1)** → If you already have a reference to the node (no traversal)
- **O(n)** → If you need to traverse from the head to find the position

👉 In most real-world scenarios (index-based operations), the complexity is **O(n)** due to traversal.

---

## ⏱️ Time & Space Complexity

### 🔹 Add Operations

| Method            | Time Complexity | Space Complexity | Explanation |
|------------------|----------------|------------------|------------|
| `add()`          | O(1)           | O(1)             | Uses tail pointer |
| `addFirst()`     | O(1)           | O(1)             | Direct head update |
| `addAt(index)`   | O(n)           | O(1)             | Traversal required |

---

### 🔹 Access Operation

| Method     | Time Complexity | Space Complexity | Explanation |
|------------|----------------|------------------|------------|
| `get()`    | O(n)           | O(1)             | Sequential traversal |

---

### 🔹 Remove Operations

| Method            | Time Complexity | Space Complexity | Explanation |
|------------------|----------------|------------------|------------|
| `removeFirst()`  | O(1)           | O(1)             | Head move |
| `removeLast()`   | O(n)           | O(1)             | Need second-last node |
| `removeAt()`     | O(n)           | O(1)             | Traversal required |

---

### 🔹 Utility Operations

| Method     | Time Complexity | Space Complexity | Explanation |
|------------|----------------|------------------|------------|
| `size()`   | O(1)           | O(1)             | Maintained variable |
| `print()`  | O(n)           | O(1)             | Traverse all nodes |

---

## 💾 Space Complexity (Overall)

- **Total Space:** `O(n)`
- Each node stores:
    - Data
    - Next reference

---

## 🧪 Example Usage

```java
public class Main {
    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<>();

        list.add(10);
        list.add(20);
        list.addFirst(5);
        list.addAt(1, 15);

        list.print(); 
        // Output: 5 -> 15 -> 10 -> 20 -> null

        list.removeAt(2);

        list.print(); 
        // Output: 5 -> 15 -> 20 -> null

        System.out.println("Size: " + list.size());
    }
}
```

---

## 🎯 When to Use Linked List?

Use when:
- Frequent insertions/deletions
- No need for random access

Avoid when:
- Frequent index-based access (use ArrayList instead)

---

## ⚖️ LinkedList vs ArrayList

| Feature            | LinkedList | ArrayList |
|------------------|----------|----------|
| Insert/Delete     | ⚠️ O(1) / O(n)* | ❌ O(n) |
| Random Access     | ❌ O(n) | ✅ O(1) |
| Memory Usage      | ❌ More | ✅ Less |

\* Depends on whether node reference is already known

---

## 🔥 Teaching Notes

- Focus on **pointer manipulation**
- Explain the difference between:
    - Modification → O(1)
    - Traversal → O(n)
- Use diagrams for better understanding

---

## 📦 Future Enhancements

- Doubly Linked List
- Reverse Linked List
- Loop detection (Floyd’s Algorithm)
- Iterator support

---

## 👨‍💻 Author

Designed for **learning and teaching Data Structures in Java**

**Author:** Jayasekhar K  
**GitHub:** https://github.com/jk-boot-up/datastructures
