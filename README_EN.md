# Digital Library Management System (Educational Project)

## Description
This project is a web application designed for transitioning a local library to a digital book management system.

## Key Features
- **Reader Registration**: Ability to add new readers to the system.
- **Book Management**: Issuing and returning books to readers.

## Technical Details
- **Entities**:
    - *Person*: Full Name (unique), Year of Birth.
    - *Book*: Title, Author, Year of Publication.
- **Entity Relationships**: One-to-Many (a person can have multiple books, but a book belongs to only one person).
- **Database**:
    - Two tables: `Person` and `Book` with automatic ID generation.
    - Create a new database named `Library`.

## Web Pages
1. **User Management**: Adding, modifying, and deleting a person.
2. **Book Management**: Adding, modifying, and deleting books.