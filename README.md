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

## 🧭 How to Run the Tests

### 1️⃣ **Clone the Repository**

```bash
git clone https://github.com/nohanabilnagah/OrangeHRM-Automation.git
cd OrangeHRM-Automation
```

### 2️⃣ **Run Tests Using Maven**

```bash
mvn clean test
```

Tests are executed based on the `testng.xml` suite located in `src/test/resources`.

---

## 🌐 Parallel Browser Execution

- Chrome and Firefox are supported.  
- Each test runs sequentially per browser as configured in `testng.xml`.

---

## 📊 Allure Report (After Test Execution)

### 🔧 Generate Allure Report:

```bash
allure generate allure-results --clean -o allure-report
```

### 🔍 View Allure Report:

```bash
allure open allure-report
```

> 💡 **Note:** Make sure the [Allure CLI](https://docs.qameta.io/allure/#_installing_a_commandline) is installed.

---

## 👩‍💻 Author

**Noha Nabil**
