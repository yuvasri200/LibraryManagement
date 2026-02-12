# 📚 Library Management System

This is a **Java Swing + MySQL** Library Management System project.  
It includes features for managing books, users, issuing and returning books.

---

## 🌟 Features

- **Login System**
  - Admin login with username and password
- **Dashboard**
  - Add Book ![Add Icon](icons/add.png)
  - View Books ![View Icon](icons/view.png)
  - Issue Book ![Issue Icon](icons/issue.png)
  - Return Book ![Return Icon](icons/return.png)
  - Logout ![Logout Icon](icons/logout.png)
- **Add Book**
  - Add new books with title, author, publisher, and quantity
- **View Books**
  - Display all books in a table with ID, Title, Author, Publisher, Quantity
- **Issue / Return Books**
  - Manage book lending and returning
- **Database Connection**
  - MySQL database connection for storing books and users data

---

## 🛠 Technology / Tech Stack

- **Language:** Java  
- **GUI:** Swing (JFrame, JButton, JTable, JTextField, etc.)  
- **Database:** MySQL  
- **Build Tools:** IntelliJ IDEA, Maven

---

## 🗄 Database

- **Database Name:** `library_db`
- **Tables:**
  - `users` → id, username, password, role
  - `books` → id, title, author, publisher, quantity
  - `issued_books` → id, book_id, user_id, issue_date, return_date

---

## 🚀 How to Run

1. Import project in **IntelliJ IDEA** or any Java IDE.
2. Make sure **MySQL server** is running and database is created.
3. Update `DBConnection.java` with your MySQL credentials.
4. Run `LoginForm.java` to start the application.

---

## 👤 Author

- **Yuvasri S**
  
