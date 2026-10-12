# Swag Labs Test Automation Assignment

A robust, enterprise-grade test automation framework built using Selenium WebDriver, **TestNG**, and **Google Gson** following the **Page Object Model (POM)** design pattern.

## Security & Architecture Decisions

### 1. Mitigation of Vulnerability (CVE-2020-15250)
Initially, the project considered using `json-simple` (v1.1.1) for externalizing data. However, during the architecture review, it was skipped due to safety and security configurations regarding **CVE-2020-15250**.
- **The Risk:** `json-simple` has known vulnerabilities where untrusted data parsing could lead to Denial of Service (DoS) or unexpected resource exhaustion.
- **The Solution:** Upgraded the framework to use **Google Gson (v2.14.0)**, which is actively maintained, secure, and production-safe against data-parsing exploits.

### 2. Clean Session Synchronization
To eliminate flaky test behavior and avoid the notorious `NoSuchSessionException` (invalid session id: session deleted), a **Clean Session Synchronization** strategy was implemented in the `BaseTest` configurations:
- Browser driver initializations are sandboxed per test method execution using isolated `ChromeDriver` instances.
- Added explicit synchronization capabilities using `ChromeOptions` (`--remote-allow-origins=*`) to safeguard websocket handshakes between Selenium and the Chrome renderer.
- Proper driver teardown logic ensures that no orphaned browser processes block socket connections for subsequent test cases.

---


## Project Structure
```text
SwagLabsAutomationAssignment/
├── .github/workflows/
│   └── allure.yml
├── testData/
│   └── testData.json
├── src/
│   ├── main/java/
│   │   ├── pages/
│   │   │   ├── LoginPage.java
│   │   │   ├── InventoryPage.java
│   │   └── utils/
│   │       ├── DataDriven.java
│   └── test/java/
│       ├── base/
│       │   └── BaseTest.java
│       └── tests/
│           ├── LoginTest.java
│           ├── InventoryTest.java
├── allure-report.png
├── pom.xml
└── testng.xml
```

---

## Test Coverage Matrix

| Test Suite | Test Case Name | Type | Validation Point |
| :--- | :--- | :--- | :--- |
| **Login Suite** | `testSuccessfulLogin` | Positive | Validates routing to `/inventory.html` on successful authorization. |
| **Login Suite** | `testInvalidLogin` | Negative | Asserts specific mismatch error message text block display. |
| **Login Suite** | `testLoginWithoutPassword` | Negative | Asserts missing credential handling alert layout. |
| **Inventory Suite**| `testInventoryPageElements` | Functional| Verifies structural items count (6 items) and active cart badge visibility. |

---
## Dependency Technical Specs

| Dependency Name | Group ID | Artifact ID | Version  | Purpose |
| :--- | :--- | :--- |:---------| :--- |
| **Java JDK** | N/A | N/A | `25.0.3` | Core programming platform |
| **Selenium Java** | `org.seleniumhq.selenium` | `selenium-java` | `4.20.0` | Browser automation core |
| **TestNG** | `org.testng` | `testng` | `7.10.2` | Test runner & assertions engine |
| **Google Gson** | `com.google.code.gson` | `gson` | `2.11.0` | Secure Data-Driven JSON parsing |
| **Allure TestNG** | `io.qameta.allure` | `allure-testng` | `2.27.0` | Test reporting framework listener |
| **Allure Maven** | `io.qameta.allure` | `allure-maven` | `2.14.0` | Local HTML report generation plugin |

---

## Framework Features
- **Page Object Model (POM):** Strict structural isolation between test logic and UI elements.
- **Data-Driven Testing:** Production credentials and dynamic product assertions are securely isolated inside `testData/testData.json`.
- **Advanced Locators & Asserts:** Implemented dynamic XPath wrappers to perform full data verification, including pricing text, structure bounds, and element visibility checking.

---

## Test Execution Report
Below is the execution dashboard demonstrating a **100% success rate** across all components:

![Allure Test Execution Report](./allure-report.png)

## Getting Started

### Prerequisites
- Java JDK 25
- Apache Maven

### How to Run Tests & Generate Reports Local View
1. Clone the repository to your machine.
2. Execute the following command in your terminal:
   ```bash
   mvn clean test allure:report
   ```
3. Expand `target/site/allure-maven-plugin/index.html` and open it inside any browser to interact with the graphics suite.