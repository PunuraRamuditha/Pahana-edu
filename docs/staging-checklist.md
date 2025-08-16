Staging Test Report – Version 1.0.0
Target Branch: staging
Source Branch: dev
Test Date: 13 August 2025
Tester: Punura Ramuditha

1. Objective

To validate that all new features merged from the dev branch function correctly, UI flows are intact, automated tests pass without errors, and the release versioning is correctly configured before merging into regression.

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




- Automated Unit Testing (JUnit)

All unit tests executed successfully (mvn test) – Pass

No skipped or ignored tests – Pass

Test logs contain minor System.out.println debug outputs – Warning, to be cleaned in future.

- Security & Access Control

Unauthorized users cannot access protected views – Pass

- Release Verification

pom.xml reflects version 1.0.0 – Confirmed

CHANGELOG.md matches implemented features – Confirmed

No typos or placeholder text in UI – Confirmed

Application stable in final deployment test – Confirmed

3. Observations

Minor debug print statements remain in code.
Plan: Remove or convert to proper logging in future releases.

4. Final Assessment

Release Status: ✅ Approved for merge into main branch.
Readiness: Product is stable, functional, and secure for production deployment.