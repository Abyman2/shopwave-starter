# shopwave-starter
# SE4801 Assignment 1 – ShopWave Starter

## Student Information

* **Name:** Abel Seleshe
* **Student Number:** ATE/6743/14

---

## Project Description

This project is a Spring Boot 3.x enterprise application developed as part of SE4801 Assignment 1.
It implements a simple **ShopWave product catalogue system** with REST APIs, database integration, and proper layered architecture.

---

## Technologies Used

* Java 21
* Spring Boot 3.x
* Spring Web
* Spring Data JPA
* H2 Database (in-memory)
* Lombok
* Maven

---

## How to Build the Project

Open terminal in the project root (where `pom.xml` is located) and run:

```
mvn clean install
```

---

## How to Run the Application

```
mvn spring-boot:run
```

OR run the main class:

```
ShopwaveStarterApplication.java
```

The application will start on:

```
http://localhost:8080
```

---

## How to Run Tests

```
mvn test
```

---

## API Endpoints

### Get All Products (Paginated)

```
GET /api/products?page=0&size=10
```

### Get Product by ID

```
GET /api/products/{id}
```

### Create Product

```
POST /api/products
```

Example JSON:

```
{
  "name": "Test Product",
  "description": "Sample description",
  "price": 25.50,
  "stock": 10
}
```

### Search Products

```
GET /api/products/search?keyword=&maxPrice=
```

### Update Stock

```
PATCH /api/products/{id}/stock
```

Example:

```
{
  "delta": -5
}
```

---

## Error Handling

The application uses a global exception handler that returns JSON responses in the following format:

```
{
  "timestamp": "...",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: X",
  "path": "/api/products/X"
}
```

---

## Academic Integrity Declaration

By submitting this assignment, I declare that all work is my own and has not been submitted for assessment in any other course.

---

## AI Assistance Disclosure

Some parts of this assignment were developed with the assistance of AI tools.
These tools were used to:

* Understand concepts (Spring Boot, Streams, OOP)
* Generate initial code examples
* Debug errors and improve structure

All AI-generated content was carefully reviewed, tested, and modified to ensure correctness and full understanding before submission.

---
