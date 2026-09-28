# Bank Automation — QA Automation Framework

## Overview

Bank Automation is a Java-based QA automation framework designed to test the **Dev Bank** application across multiple testing layers.

The project combines **UI automation, REST API testing, database validation, and end-to-end testing** to verify critical banking workflows and data integrity.

The framework is built using Selenium WebDriver, TestNG, REST Assured, JDBC, Maven, PostgreSQL, and ExtentReports.

---

## Application Under Test

**Application:** Dev Bank

The application provides banking functionality including:

* User authentication
* Account management
* Beneficiary management
* Money transfers
* Transaction processing
* Balance management

---

## QA Testing Scope

The automation framework covers:

### UI Testing

Automated browser-based testing using Selenium WebDriver.

Examples include:

* Login validation
* Account navigation
* Transfer workflows
* Successful transfers
* Same-account transfer validation
* Invalid recipient validation
* Insufficient funds validation

### API Testing

REST API tests validate backend functionality independently from the UI.

Examples include:

* API response validation
* HTTP status validation
* Beneficiary operations
* Transfer operations
* Response data validation

### Database Testing

JDBC and PostgreSQL are used to validate that application operations are correctly persisted in the database.

Examples include:

* Beneficiary persistence
* Account balances
* Transaction records
* Transaction counts
* Database state before and after operations

### End-to-End Testing

The framework validates complete business workflows across multiple system layers.

For example:

```text
UI Action
   ↓
Backend API
   ↓
Database
   ↓
Business Data Validation
```

A successful transfer can therefore be validated by checking:

* Source account balance
* Destination account balance
* Transaction ID
* Transaction count
* Database persistence

---

## Technology Stack

| Technology         | Purpose                                   |
| ------------------ | ----------------------------------------- |
| Java 17            | Programming language / compilation target |
| Selenium WebDriver | UI automation                             |
| TestNG             | Test framework                            |
| REST Assured       | API automation                            |
| JDBC               | Database validation                       |
| PostgreSQL         | Database                                  |
| Maven              | Build and dependency management           |
| ExtentReports      | Test reporting                            |
| ChromeDriver       | Browser automation                        |
| Git                | Version control                           |
| GitHub             | Source-code repository                    |

---

## Framework Architecture

```text
src
├── main
│   └── java
│       └── org.bankautomation
│           ├── api
│           ├── config
│           ├── database
│           ├── drivers
│           ├── pages
│           └── utils
│
└── test
    ├── java
    │   └── org.bankautomation
    │       ├── api
    │       ├── database
    │       ├── e2e
    │       └── ui
    │
    └── resources
        └── config.properties
```

The framework follows a Page Object Model approach for UI automation and separates application functionality from test implementation.

---

## UI Automation

Selenium WebDriver is used to automate browser interactions.

The framework uses page classes such as:

```text
LoginPage
TransferPage
```

These classes encapsulate:

* Locators
* Page navigation
* Form interactions
* Transfer operations
* Success and error validation

Explicit waits are used to improve synchronization between the test and application.

---

## API Automation

REST Assured is used to test backend REST endpoints.

API testing validates:

* HTTP responses
* Response payloads
* Business rules
* Created resources
* Backend behavior

API validation complements UI testing by allowing backend functionality to be tested independently.

---

## Database Validation

The framework uses JDBC to connect to PostgreSQL and verify application state directly in the database.

Example validation:

```text
Before Transfer
Source Balance:      R8600.00
Destination Balance: R17610.00

Transfer Amount:     R10.00

After Transfer
Source Balance:      R8590.00
Destination Balance: R17620.00
```

The framework also validates transaction persistence.

Example:

```text
Transaction count before: 36
Transfer transaction ID:  37
```

This confirms that the transfer resulted in the expected database changes.

---

## Beneficiary Database Validation

The framework validates beneficiary data across both the API and database.

Example:

```text
Beneficiary API response:
[
  {
    "id": 4,
    "name": "QA Recipient",
    "accountNumber": "1000000003",
    "bankName": "Dev Bank",
    "status": "ACTIVE"
  }
]

Beneficiaries in database: 1
Verified beneficiary ID: 4
```

The test confirms that beneficiary information returned by the API is also correctly persisted in the database.

---

## Negative Testing

The framework includes negative test scenarios for important banking business rules.

Examples include:

### Same Account Transfer

Attempts to transfer money from an account to itself.

Expected result:

```text
Cannot transfer money to the same account
```

### Insufficient Funds

Attempts to transfer an amount greater than the available account balance.

Expected result:

```text
Insufficient funds
```

### Invalid Recipient

Attempts to transfer money using an invalid recipient account.

The test verifies that the application rejects the operation and displays the appropriate validation message.

---

## Test Reporting

The framework uses **ExtentReports** to generate HTML test reports.

Reports are generated under:

```text
reports/ExtentReport.html
```

The report contains:

* Test names
* Test status
* Failure information
* Execution details
* System information
* Automation framework information

The report identifies the project as:

```text
Project:       Bank Automation
Application:   Dev Bank
Automation:    Selenium / REST Assured / JDBC
Framework:     TestNG
Build Tool:    Maven
Database:      PostgreSQL
```

---

## Screenshot on Failure

The framework automatically captures a screenshot when a UI test fails.

Screenshots are stored under:

```text
reports/screenshots/
```

The screenshot is attached to the ExtentReport so that a failed test can be investigated using both the failure message and the browser state at the time of failure.

---

## Test Results

The current automation suite successfully executes:

```text
Tests run: 59
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

The suite covers UI, API, database, and end-to-end automation scenarios.

---

## Running the Tests

### Run the complete test suite

From the project directory:

```bash
mvn clean test
```

### Run a specific TestNG suite

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

The TestNG suite is configured in:

```text
testng.xml
```

---

## Configuration

Environment-specific configuration is stored locally in:

```text
src/test/resources/config.properties
```

The file contains environment and database configuration and is intentionally excluded from version control.

A developer or tester should create their own local configuration before running the tests.

---

## QA Documentation

The project documentation covers areas such as:

```text
docs/
├── requirements.md
├── test-strategy.md
├── test-plan.md
├── test-scenarios.md
├── test-cases.md
├── risk-analysis.md
├── api-test-cases.md
└── database-test-cases.md
```

These documents demonstrate the complete QA lifecycle from requirements analysis and test planning through automation and reporting.

---

## Key QA Skills Demonstrated

This project demonstrates practical experience with:

* Test automation
* Selenium WebDriver
* Java
* TestNG
* Page Object Model
* REST API testing
* REST Assured
* SQL
* JDBC
* PostgreSQL
* Database validation
* End-to-end testing
* Positive testing
* Negative testing
* Regression testing
* Business-rule validation
* Test reporting
* Screenshot capture
* Maven
* Git
* GitHub
* Debugging automated tests
* Test failure analysis

---

## Project Objective

The objective of this project is to demonstrate the ability to design and implement a maintainable QA automation framework that validates functionality across the presentation, API, and database layers.

Rather than testing only individual UI elements, the framework validates complete business workflows and verifies that the expected data changes are persisted correctly in the backend.
