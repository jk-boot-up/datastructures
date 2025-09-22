# sll-reverse-between

A simple Java application demonstrating reversing nodes in a **singly linked list** between two given positions.

## 📦 Project Structure
```
.
├── Dockerfile
├── docker-compose.yml
├── src/
│   └── main/java/com/jk/dsa/linkedlists/single/reverse/
│       ├── App.java
│       └── LinkedList.java
└── build.gradle (or settings.gradle if multi-module)
```

## 🛠 Build & Run with Docker

### Build the runtime image
```bash
docker compose build app
```

### Run the app
```bash
docker compose up app
```

This runs the jar built in the multi-stage Dockerfile:
```dockerfile
FROM gradle:9.0.0-jdk24 AS build
# ...
FROM eclipse-temurin:24-jdk
```

The container executes:
```bash
java -jar sll-reverse-between.jar
```

---

## 🧪 Running Tests

Run tests with Gradle inside a container:

```bash
docker compose run --rm test
```

This uses the official Gradle image (`gradle:9.0.0-jdk24`) and runs:

```bash
./gradlew clean test --no-daemon --stacktrace
```

Test reports (if generated) will be in:
```
app/build/reports/tests/
```

---

## 💻 Running Locally (without Docker)

1. Compile:
   ```bash
   ./gradlew clean build
   ```

2. Run:
   ```bash
   java -jar app/build/libs/sll-reverse-between.jar 1 2 3 4 5 1 3
   ```

   - The first arguments (`1 2 3 4 5`) are the list elements.
   - The last two arguments (`1 3`) are the **start** and **end** indices for reversal.

Example:
```
Original Linked List:
1
2
3
4
5

After reverseBetween(1, 3):
1
4
3
2
5
```

---

## 🐳 Useful Docker Commands

- Rebuild everything from scratch:
  ```bash
  docker compose build --no-cache
  ```

- Stop all containers:
  ```bash
  docker compose down
  ```

- View logs:
  ```bash
  docker compose logs -f
  ```

---

## 📖 Notes

- The Dockerfile uses a **multi-stage build**:
  - Stage 1 (`gradle:9.0.0-jdk24`) compiles the project.
  - Stage 2 (`eclipse-temurin:24-jdk`) runs the built jar with a smaller, production-ready image.
- If you want a simpler single-stage build (bigger image), you can merge both stages into one.

---

✍️ Author: *Your Name*
