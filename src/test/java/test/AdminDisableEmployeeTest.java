package test;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.AdminPage;
import pages.LoginPage;
import java.time.Duration;
import java.util.Objects;

import utilities.Browser;
import utilities.TestData;

@Epic("Admin Features")
@Feature("User Management")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class AdminDisableEmployeeTest extends TestBase {

    public WebDriver getDriver() {
        return Browser.getDriver();
    }

    LoginPage loginPage;
    AdminPage adminPage;
    AdminDashboardPage adminDashboardPage;
    // Instance variable to generate a unique and dynamic username for each test instance
    private final String uniqueUsername = TestData.generateUniqueUsername();


    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(getDriver());
        adminPage = new AdminPage(getDriver());
        adminDashboardPage = new AdminDashboardPage(getDriver());
    }

    @Test(priority = 1, description = "Verify that admin can log in")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Admin Login")
    @Description("Admin logs in using valid credentials and lands on the dashboard.")
    public void adminCanLogin() {
        loginPage.enterUsername(TestData.adminUsername);
        loginPage.enterPassword(TestData.adminPassword);
        loginPage.clickLoginButton();
        Assert.assertTrue(Objects.requireNonNull((getDriver()).getCurrentUrl()).contains("/dashboard"),
                "Admin login failed.");
    }

    @Test(priority = 2, description = "Verify that admin can create a new user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create User")
    @Description("Admin navigates to user management and creates a new user account.")
    public void adminCanCreateUser() {
        adminPage.clickAdminTab();
        adminPage.clickAddButton();
        adminPage.clickUserRoleDropdown();
        adminPage.enterEmployeeName(TestData.employeeName);
        adminPage.clickStatusDropdown();
        adminPage.enterNewUsername(uniqueUsername);  // use unique instance username
        adminPage.enterNewPassword(TestData.newPassword);
        adminPage.confirmNewPassword(TestData.newPassword);
        adminPage.clickSaveButton();
    }

    @Test(priority = 3, description = "Confirm success toast after creating user")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create User")
    @Description("Verify that a toast appears with 'Successfully Saved' after creating user.")
    public void confirmSuccessToast() {
        WebElement toast = adminPage.getSuccessToast();
        // Verify success message contains "Successfully Saved"
        Assert.assertTrue(toast.getText().contains("Successfully Saved"), "User creation failed!");
    }

    @Test(priority = 4, description = "Disable the newly created employee user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Disable User")
    @Description("Admin searches for the created user and disables the account.")
    public void disableEmployeeUser() {
        adminPage.clickAdminTab();
        adminPage.filterByUsername(uniqueUsername);  // use unique instance username
        adminPage.filterByUserRole();
        //adminPage.filterByEmployeeName(TestData.employeeName);
        adminPage.filterByStatus();
        adminPage.clickSearchButton();
        adminPage.clickEditAction();
        adminPage.changeStatusToDisabled();
        adminPage.clickSaveButtonAfterEdit();
    }

    @Test(priority = 5, description = "Confirm success toast after disabling user")
    @Severity(SeverityLevel.NORMAL)
    @Story("Disable User")
    @Description("Verify that a toast appears with 'Successfully Updated' after disabling user.")
    public void confirmSuccessToastAfterDisable()
    {
        adminPage.getSuccessToast();
        Assert.assertTrue(adminPage.getSuccessToastAfterEdit().getText().contains("Successfully Updated"),
                "User status update failed!");
    }

    @Test(priority = 6, description = "Verify that admin can log out successfully")
    @Severity(SeverityLevel.MINOR)
    @Story("Admin Logout")
    @Description("Admin opens the profile dropdown and logs out.")
    public void adminCanLogout() {
        // Open Profile dropdown
        adminDashboardPage.openProfileDropdown();
        // Optional sleep just to visually observe the dropdown (not necessary in headless/test mode)
        utilities.Helper.sleep(500);
        // Click on Logout
        adminDashboardPage.clickLogoutButton();
    }

    @Test(priority = 7, description = "Verify that a disabled user cannot log in")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login Block")
    @Description("Attempt to log in using disabled user's credentials and verify error message.")
    public void disabledUserCannotLogin() {
        loginPage.enterUsername(uniqueUsername);  // use unique instance username
        loginPage.enterPassword(TestData.newPassword);
        loginPage.clickLoginButton();

        // Login failure assertion
        boolean stillOnLoginPage = Objects.requireNonNull((getDriver()).getCurrentUrl()).contains("auth/login");
        Assert.assertTrue(stillOnLoginPage, "Disabled user should not be able to login.");

        // Wait for the error message to appear
        WebElement errorMessage = new WebDriverWait((getDriver()), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("p.oxd-alert-content-text")));
        // Assert on the error message text
        String errorMessageText = errorMessage.getText().trim();
        Assert.assertEquals(errorMessageText, "Account disabled",
                "Expected 'Account disabled' error message not found.");
    }
}