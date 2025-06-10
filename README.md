🧪 OrangeHRM Automation Framework
A Selenium + TestNG automation framework built to validate the OrangeHRM demo site with a modular Page Object Model (POM) design and multi-browser execution support.

🚀 Tech Stack
Java 17
Selenium WebDriver
TestNG
Maven
Allure Reports
WebDriverManager

📁 Project Structure

OrangeHRM/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── pages/                     # Page Object Model classes
│   │   │   │   ├── AdminDashboardPage.java
│   │   │   │   ├── AdminPage.java
│   │   │   │   └── LoginPage.java
│   │   │   └── utilities/
│   │   │       ├── Browser.java           # WebDriver setup and browser management
│   │   │       ├── Helper.java            # Common utility methods
│   │   │       └── AllureEnvironmentWriter.java
│
│   └── test/
│       ├── java/
│       │   ├── test/
│       │   │   ├── AdminCreateUserTest.java
│       │   │   ├── AdminDisableEmployeeTest.java
│       │   │   ├── AdminLoginTest.java
│       │   │   └── TestBase.java          # Common setup/teardown for all tests
│       │   └── utilities/
│       │       └── TestData.java          # Static test data
│
│       └── resources/
│           └── testng.xml                 # TestNG suite configuration
│
├── allure-results/                        # Allure results (auto-generated)
└── allure-report/                         # Allure HTML report (after generation)

🧪 Test Scenarios Covered
Admin Login – Validates login functionality for an admin user.
Create User – Admin creates a new employee/user.
Disable User – Admin disables the user account.
Login as Disabled User – Verifies that the disabled user cannot log in.

🧭 How to Run the Tests
1️⃣ Clone the Repository
git clone https://github.com/nohanabilnagah/OrangeHRM-Automation.git
cd OrangeHRM-Automation
2️⃣ Run Tests Using Maven
mvn clean test
Tests are executed based on the testng.xml suite located in src/test/resources.

🌐 Parallel Browser Execution
Chrome and Firefox are supported.

Each test runs sequentially per browser as configured in testng.xml.

📊 Allure Report (After Test Execution)
Generate Allure Report:
allure generate allure-results --clean -o allure-report
View Allure Report:
allure open allure-report
Ensure Allure CLI is installed. Install it via Allure CLI installation guide.

👩‍💻 Author
Noha Nabil
