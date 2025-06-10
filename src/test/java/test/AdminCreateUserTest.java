package test;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.AdminPage;
import pages.LoginPage;
import java.util.Objects;
import utilities.Browser;
import utilities.TestData;

@Epic("Admin Features")
@Feature("User Management")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class AdminCreateUserTest extends TestBase {

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

	@Test(priority = 1, description = "Verify that the admin can log into the dashboard")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Admin Login")
	@Description("Login as admin and verify navigation to dashboard page.")
	public void adminCanLogin()
	{
		loginPage.enterUsername(TestData.adminUsername);
		loginPage.enterPassword(TestData.adminPassword);
		loginPage.clickLoginButton();
		Assert.assertTrue(Objects.requireNonNull((getDriver()).getCurrentUrl()).contains("/dashboard"),
				"Admin login failed.");
	}

	@Test(priority = 2, description = "Create a new user from the Admin panel")
	@Severity(SeverityLevel.BLOCKER)
	@Story("User Creation")
	@Description("Admin adds a new user with a unique username and a predefined password.")
	public void adminCanCreateUser()
	{
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

	@Test(priority = 3, description = "Verify success message appears after user creation")
	@Severity(SeverityLevel.NORMAL)
	@Story("User Creation")
	@Description("Check that a success toast appears confirming the user was created.")
	public void confirmSuccessToast()
	{
		WebElement toast = adminPage.getSuccessToast();
		// Verify success message contains "Successfully Saved"
		Assert.assertTrue(toast.getText().contains("Successfully Saved"), "User creation failed!");
	}

	@Test(priority = 4, description = "Log out from the admin account")
	@Severity(SeverityLevel.MINOR)
	@Story("Admin Logout")
	@Description("Admin logs out using the profile dropdown.")
	public void adminCanLogout()
	{
		// Open Profile dropdown
		adminDashboardPage.openProfileDropdown();
		// Optional sleep just to visually observe the dropdown (not necessary in headless/test mode)
		utilities.Helper.sleep(500);
		// Click on Logout
		adminDashboardPage.clickLogoutButton();
	}

	@Test(priority = 5, description = "Log in with the newly created user account")
	@Severity(SeverityLevel.CRITICAL)
	@Story("New User Login")
	@Description("Newly created user logs in and profile name is validated.")
	public void newUserCanLogin()
	{
		loginPage.enterUsername(uniqueUsername);  // use unique instance username
		loginPage.enterPassword(TestData.newPassword);
		loginPage.clickLoginButton();

		String actualProfileName = adminDashboardPage.getProfileName();
		System.out.println("Actual profile name after login: " + actualProfileName);
		// Assert that the new profile name is not empty
		Assert.assertFalse(actualProfileName.isEmpty(), "Profile name is empty!");

		// Store profile name for future assertions if needed
		TestData.loggedInProfileName = actualProfileName;

		// Assert using the captured name
		Assert.assertEquals(actualProfileName, TestData.loggedInProfileName, "Profile name does not match.");
	}


	@Test(priority = 6, description = "Log out from the new user account")
	@Severity(SeverityLevel.MINOR)
	@Story("New User Logout")
	@Description("New user logs out using the profile dropdown.")
	public void newUserCanLogout()
	{
		// Open Profile dropdown
		adminDashboardPage.openProfileDropdown();
		// Optional sleep just to visually observe the dropdown (not necessary in headless/test mode)
		utilities.Helper.sleep(500);
		// Click on Logout
		adminDashboardPage.clickLogoutButton();
	}
}