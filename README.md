# 🧪 OrangeHRM Automation Framework

A **Selenium + TestNG** automation framework built to validate the OrangeHRM demo site with a modular Page Object Model (POM) design and multi-browser execution support.

---

## 🚀 Tech Stack

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Allure Reports
- WebDriverManager

---

## 🧪 Test Scenarios Covered

- **Admin Login** – Validates login functionality for an admin user.
- **Create User** – Admin creates a new employee/user.
- **Disable User** – Admin disables the user account.
- **Login as Disabled User** – Verifies that the disabled user cannot log in.

---

## 🧱 Project Structure

```
OrangeHRM/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── pages/                 # Page Object classes
│   │       └── utilities/            # Core utilities (Browser setup, helpers)
│   │           ├── Browser.java
│   │           ├── Helper.java
│   │           └── AllureEnvironmentWriter.java
│   │
│   └── test/
│       ├── java/
│       │   ├── test/                 # Test classes for different scenarios
│       │   │   ├── TestBase.java
│       │   └── utilities/           # Test data class
│       │       └── TestData.java
│       │
│       └── resources/
│           └── testng.xml           # TestNG suite configuration
```

---

## ✅ Prerequisites

- Java 17 installed and configured (`JAVA_HOME`)
- Maven installed and configured
- Allure CLI installed and added to system `PATH`  
  [👉 How to install Allure CLI](https://docs.qameta.io/allure/#_installing_a_commandline)
- Chrome & Firefox browsers installed

---

## 🧭 How to Run the Tests

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/nohanabilnagah/OrangeHRM-Automation.git
cd OrangeHRM-Automation
```

### 2️⃣ Run Tests Using Maven

```bash
mvn clean test
```

> 📄 Test execution is controlled by `testng.xml` located in `src/test/resources`.

---

## 🌐 Parallel Browser Execution

The current `testng.xml` file runs **sequentially** across two browsers (Chrome and Firefox):

- Each test class (`AdminLoginTest`, `AdminCreateUserTest`, `AdminDisableEmployeeTest`) is executed **one after the other** in **Chrome**, followed by the same sequence in **Firefox**.
- This avoids shared state issues and ensures test reliability across browsers.

📁 Located at:  
`src/test/resources/testng.xml`

### 🧪 Sample (Sequential) TestNG Structure:

```xml
<suite name="OrangeHRM Sequential Suite" parallel="false">

  <test name="Chrome - Login">
    <parameter name="browser" value="chrome"/>
    <classes>
      <class name="test.AdminLoginTest"/>
    </classes>
  </test>

  <!-- More Chrome and Firefox tests follow... -->

</suite>
```

---

### 🚀 Want to Enable Full Parallel Execution?

You can update the suite to run tests **in parallel** by changing this:

```xml
<suite name="OrangeHRM Parallel Suite" parallel="tests" thread-count="2">
```

💡 This will run tests simultaneously for different browsers, but you must ensure:

- Your WebDriver setup is **thread-safe** (your `Browser` class already supports this).
- Shared data (like `TestData`) does not cause race conditions.

---

✅ Tip: Start by testing only 2 classes in parallel to verify stability before scaling up.



---

## 📊 Allure Report (After Test Execution)

### 🔧 Generate the Report

```bash
allure generate allure-results --clean -o allure-report
```

### 🔍 View the Report

```bash
allure open allure-report
```

> 💡 **Tip:** Ensure Allure CLI is installed and accessible via terminal.

---

## 👩‍💻 Author

**Noha Nabil**

---