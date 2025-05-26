package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.AdminPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.Objects;
import static test.AdminCreateUserTest.*;


public class AdminDisableEmployeeTest extends TestBase {

    LoginPage loginPage;
    AdminPage adminPage;
    AdminDashboardPage adminDashboardPage;


    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
        adminDashboardPage = new AdminDashboardPage(driver);
    }

    @Test(priority = 1)
    public void adminCanLogin() {
        loginPage.enterUsername(adminUsername);
        loginPage.enterPassword(adminPassword);
        loginPage.clickLoginButton();
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/dashboard"),
                "Admin login failed.");
    }

    @Test(priority = 2, dependsOnMethods = "adminCanLogin")
    public void adminCanCreateUser() {
        adminPage.clickAdminTab();
        adminPage.clickAddButton();
        adminPage.clickUserRoleDropdown();
        adminPage.enterEmployeeName(newUsername);
        adminPage.clickStatusDropdown();
        adminPage.enterNewUsername(employeeName);
        adminPage.enterNewPassword(newPassword);
        adminPage.confirmNewPassword(newPassword);
        adminPage.clickSaveButton();
    }

    @Test(priority = 3, dependsOnMethods = "adminCanCreateUser")
    public void confirmSuccessToast() {
        Assert.assertTrue(adminPage.getSuccessToast().getText().contains("Successfully Saved"),
                "User creation failed!");
    }

    @Test(priority = 4, dependsOnMethods = "confirmSuccessToast")
    public void disableEmployeeUser() {
        adminPage.clickAdminTab();
        adminPage.filterByUsername(employeeName);
        adminPage.filterByUserRole();
        adminPage.filterByEmployeeName(newUsername);
        adminPage.filterByStatus();
        adminPage.clickSearchButton();
        adminPage.clickEditAction();
        adminPage.changeStatusToDisabled();
        adminPage.clickSaveButtonAfterEdit();
    }

    @Test(priority = 5, dependsOnMethods = "disableEmployeeUser")
    public void confirmSuccessToastAfterDisable()
    {
        adminPage.getSuccessToast();
        Assert.assertTrue(adminPage.getSuccessToastAfterEdit().getText().contains("Successfully Updated"),
                "User status update failed!");
    }

    @Test(priority = 6, dependsOnMethods = "confirmSuccessToastAfterDisable")
    public void adminCanLogout() {
        adminDashboardPage.openProfileDropdown();
        adminDashboardPage.clickLogoutButton();
    }

    @Test(priority = 7, dependsOnMethods = "adminCanLogout")
    public void disabledUserCannotLogin() {
        loginPage.enterUsername(employeeName);
        loginPage.enterPassword(newPassword);
        loginPage.clickLoginButton();

        // Login failure assertion
        boolean stillOnLoginPage = Objects.requireNonNull(driver.getCurrentUrl()).contains("auth/login");
        Assert.assertTrue(stillOnLoginPage, "Disabled user should not be able to login.");

        // Wait for the error message to appear
        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("p.oxd-alert-content-text")));
        // Assert on the error message text
        String errorMessageText = errorMessage.getText().trim();
        Assert.assertEquals(errorMessageText, "Account disabled", "Expected 'Account disabled' error message not found.");
    }
}
