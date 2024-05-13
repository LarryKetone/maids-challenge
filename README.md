# Library Management System using Spring Boot

## Project Overview

This project is a Library Management System (LMS) API developed using Spring Boot. It provides functionalities for managing books, patrons, and borrowing records within a library. The API endpoints allow users to perform CRUD operations on books and patrons, as well as borrowing and returning books.

### Features

- **Book Management**: Add, retrieve, update, and delete books.
- **Patron Management**: Add, retrieve, update, and delete patrons.
- **Borrowing Records**: Allow patrons to borrow books and record their returns.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- MySQL, PostgreSQL, or H2 database (optional)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/LarryKetone/maids-challenge.git
   ```

2. Navigate to the project directory:

   ```bash
   cd maids-challenge
   ```

3. Build the project:

   ```bash
   mvn clean package
   ```

4. Run the application:

   ```bash
   java -jar target/maids-challenge.jar
   ```

5. Access the API at `http://localhost:8082`.

## API Endpoints

### Book Management

- `GET /api/books`: Retrieve all books.
- `GET /api/books/{id}`: Retrieve details of a specific book.
- `POST /api/books`: Add a new book.
- `PUT /api/books/{id}`: Update an existing book.
- `DELETE /api/books/{id}`: Delete a book.

### Patron Management

- `GET /api/patrons`: Retrieve all patrons.
- `GET /api/patrons/{id}`: Retrieve details of a specific patron.
- `POST /api/patrons`: Add a new patron.
- `PUT /api/patrons/{id}`: Update an existing patron.
- `DELETE /api/patrons/{id}`: Delete a patron.

### Borrowing

- `POST /api/borrow/{bookId}/patron/{patronId}`: Allow a patron to borrow a book.
- `PUT /api/return/{bookId}/patron/{patronId}`: Record the return of a borrowed book.
