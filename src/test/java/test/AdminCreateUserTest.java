package test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.AdminPage;
import pages.LoginPage;
import java.util.Objects;

public class AdminCreateUserTest extends TestBase {

	LoginPage loginPage;
	AdminPage adminPage;
	AdminDashboardPage adminDashboardPage;

	public static final String adminUsername = "Admin";
	public static final String adminPassword = "admin123";
	public static final String newUsername = "Orange Test";
	public static final String employeeName = "usertst" + System.currentTimeMillis();
	public static final String newPassword = "test123456";
	
	

    @BeforeMethod
    public void setup() {
		loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
        adminDashboardPage = new AdminDashboardPage(driver);
    }

	@Test(priority = 1)
	public void adminCanLogin()
	{
		loginPage.enterUsername(adminUsername);
		loginPage.enterPassword(adminPassword);
		loginPage.clickLoginButton();
		Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/dashboard"),
				"Admin login failed.");
	}

	@Test(priority = 2, dependsOnMethods = "adminCanLogin")
	public void adminCanCreateUser()
	{
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
	public void confirmSuccessToast()
	{
		adminPage.getSuccessToast();
		Assert.assertTrue(adminPage.getSuccessToast().getText().contains("Successfully Saved"),
				"User creation failed!");
	}

	@Test(priority = 4, dependsOnMethods = "confirmSuccessToast")
	public void adminCanLogout()
	{
		adminDashboardPage.openProfileDropdown();
		adminDashboardPage.clickLogoutButton();
	}

	@Test(priority = 5, dependsOnMethods = "adminCanLogout")
	public void newUserCanLogin()
	{
		loginPage.enterUsername(employeeName);
		loginPage.enterPassword(newPassword);
		loginPage.clickLoginButton();

		String expectedProfileName = "Orange Test";
		String actualProfileName = adminDashboardPage.getProfileName();
		Assert.assertEquals(actualProfileName, expectedProfileName, "Profile name does not match.");
	}
}