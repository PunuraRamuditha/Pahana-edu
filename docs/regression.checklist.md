Regression Test Report – Version 1.0.0
Target Branch: regression
Source Branch: staging
Test Date: 13 August 2025
Tester: Punura Ramuditha


1. Objective

To validate that all existing functionalities remain intact, the system operates as expected, and the build is stable for production release prior to merging into main and tagging version 1.0.0.


2. Test Coverage & Results


| Module                    | Key Scenarios Tested                                   | Result |
|---------------------------|--------------------------------------------------------|--------|
| **Login**                 | Valid login, invalid credentials handling              | Pass   |
| **Logout**                | Session cleared post-logout                            | Pass   |
| **Customer Management**   | Create, update, delete, list; UI feedback verification | Pass   |
| **Item Management**       | Full CRUD; category & price validation                 | Pass   |
| **Billing – Creation**    | Generate multiple bills with mixed item types          | Pass   |
| **Billing – Calculation** | Verify correct totals per bill                         | Pass   |
| **Help Page**             | Content loads correctly for each module                | Pass   |
| **Navigation**            | All dashboard and sidebar links functional             | Pass   |
| **UI Theme**              | Dark theme applied consistently                        | Pass   |




3. Automated Unit Testing (JUnit)

All unit tests executed successfully (mvn test) – Pass

No skipped or ignored tests – Pass

Test logs contain minor System.out.println debug outputs – Warning, to be cleaned in future.


4. Security & Access Control

Unauthorized users cannot access protected views – Pass


5. Release Verification

pom.xml reflects version 1.0.0 – Confirmed

CHANGELOG.md matches implemented features – Confirmed

No typos or placeholder text in UI – Confirmed

Application stable in final deployment test – Confirmed


6. Observations

Minor debug print statements remain in code.
Plan: Remove or convert to proper logging in future releases.


7. Final Assessment

Release Status: ✅ Approved for merge into main branch.
Readiness: Product is stable, functional, and secure for production deployment.