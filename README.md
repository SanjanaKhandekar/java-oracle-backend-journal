#  Java & Oracle SQL Engineering Journal

A collection of optimized code snippets, production configurations, and real-world backend fixes I implement while building enterprise-ready full-stack applications.

---

##  1. Advanced Java & Spring Boot Patterns

### 🔹 Stream API: Dynamic Data Transformation
Instead of traditional loops, I leverage Java Streams for declarative, clean, and highly readable data processing.

```java
// Filtering active premium users and extracting their emails into a list
List<String> premiumUserEmails = users.stream()
    .filter(user -> user.isActive() && "PREMIUM".equals(user.getRole()))
    .map(User::getEmail)
    .collect(Collectors.toList());
```

### 🔹 Spring Boot Global Exception Handling
A professional backend must never expose raw stack traces to the client. I implement centralized error management using `@RestControllerAdvice`.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

---

##  2. Oracle SQL Optimization Snippets

### 🔹 Analytical Queries: Row Numbering & Deduplication
Using analytical functions like `ROW_NUMBER()` over subqueries to fetch distinct, high-priority records efficiently.

```sql
SELECT employee_id, department_id, salary
FROM (
    SELECT employee_id, department_id, salary,
           ROW_NUMBER() OVER (PARTITION BY department_id ORDER BY salary DESC) as rank
    FROM employees
) 
WHERE rank = 1; -- Fetches the highest-paid employee in every department
```

### 🔹 Handling Database Sequences in Hibernate
Optimizing database round-trips when generating primary keys in Oracle Database using sequence allocation sizes.

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
@SequenceGenerator(name = "order_seq", sequenceName = "ORCL_ORDER_SEQ", allocationSize = 1)
private Long id;
```

---

##  3. Modern DevOps & Deployment (Docker)

### 🔹 Multi-Stage full-stack Dockerfile
Instead of installing software on a local machine, I containerize environments to guarantee consistent behavior across development and testing servers.

```dockerfile
# Stage 1: Build the Spring Boot Application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the Application in a Lightweight Environment
FROM openjdk:17-jdk-slim
COPY --from=build /target/app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

##  4. Real Bug Fixes (My Debugging Logs)

###  Issue: `ORA-02291: integrity constraint violated - parent key not found`
* **The Problem:** The Spring Boot backend tried to insert an order mapping to a user ID that did not exist in the parent `USERS` table yet.
* **The Fix:** Implemented transactional safety using `@Transactional` and introduced a strict backend check to validate the parent resource's existence before triggering the save method.

```java
@Transactional
public Order placeOrder(OrderRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));
        
    return orderRepository.save(new Order(user, request.getAmount()));
}
```

---

###  Motivation
I keep this repository updated weekly to track my progress, document performance workarounds, and establish solid software architecture foundation habits. 

📫 **Feel free to connect or review my other pinned repositories!**
