# Changelog

All notable changes to this project will be documented in this file.  
This project adheres to [Semantic Versioning](https://semver.org/).

---
## [1.1.0] - 2025-08-16

### Second release

- **Improved user interface flow for billing**
    - On login, the system now automatically routes staff to the "Add Bill" page, reducing steps and making the billing process faster and more user-friendly.


## [1.0.0] - 2025-08-15

### 🎉 Initial Release
- **Customer Management**
    - Added ability to add, edit, and view customer details.
- **Item Management**
    - Added ability to add, edit, and manage items with stock quantity.
- **Billing**
    - Implemented bill creation with multiple item selection.
    - Added bill listing and bill detail views.
- **Authentication**
    - Added staff login system with basic validation.
- **UI**
    - Created JSP-based pages for all core functions.
    - Added navigation via main menu.
- **Database**
    - Created MySQL schema for customers, items, bills, and bill items.
- **Error Handling**
    - Added custom error page for invalid requests.

---

## 📌 Notes
- This is the first stable release ready for production deployment.
- Tested on **Apache Tomcat 10** and **MySQL 8.0**.
- Works with **Java 17** and Maven-based build.

