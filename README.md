# 📚 Pahana Edu - Customer & Billing Management System (JSP/Servlet)

## 📌 Overview
Pahana Edu is a **Java JSP/Servlet-based web application** designed for managing customers, items, and billing for a bookshop.  
It follows a **layered architecture** (DAO, Model, Service, Servlet) and uses **MySQL** for persistent storage.

This project allows staff to:
- Manage customer records
- Manage items in stock
- Create bills with multiple items
- Store transaction history in the database

---

## 🚀 Features
- **Customer Management**
    - Add, update, delete, and list customers
- **Item Management**
    - Add, update, delete, and list items
- **Billing**
    - Create bills for selected customers
    - Add multiple items per bill
    - Auto-calculate totals
- **Audit Logging**
    - Record all transactions in MySQL

---

## 🛠️ Tech Stack
- **Frontend:** JSP, HTML, CSS, Bootstrap
- **Backend:** Java (Servlets)
- **Database:** MySQL
- **Server:** Apache Tomcat 10+
- **Build Tool:** IntelliJ IDEA / Eclipse

---

## 📂 Project Structure
Pahana-edu/
│
├── src/main/java/com/icbt
│ ├── model/ # Data models (Customer, Item, Bill, BillItem)
│ ├── dao/ # Data Access Objects (MySQL queries)
│ ├── service/ # Business logic layer
│ └── servlet/ # Controllers (JSP/Servlets)
│
├── src/main/webapp/
│ ├── WEB-INF/ # web.xml and JSP configs
│ ├── pages/ # JSP views
│ └── assets/ # CSS/JS
│
└── README.md # Project documentation

2. Import the pahana_edu.sql file from the database folder (if available).


3. Update DB credentials in DBConnection.java:


4. Run the Project

Deploy the project to Tomcat in your IDE.

Start the Tomcat server.

Open in browser:


🧪 Example Workflow

Login to the system.

Add Customers via the Customers page.

Add Items to inventory.

Create a Bill:

Select customer

Add multiple items

Confirm bill to save it in the database

🐞 Known Issues

If NumberFormatException: Cannot parse null string occurs during billing, ensure form fields are not empty and request parameters are correctly passed.

Ensure database connection is closed after each operation to avoid AbandonedConnectionCleanupThread warnings.

📈 Future Improvements

Add user authentication & role-based access

Export bills as PDF

Add search and filtering for items/customers

Implement REST API for external integration