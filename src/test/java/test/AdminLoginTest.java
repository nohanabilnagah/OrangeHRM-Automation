package test;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.LoginPage;
import java.util.Objects;

import utilities.Browser;
import utilities.TestData;

@Epic("Admin Features")
@Feature("User Management")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class AdminLoginTest extends TestBase {

	public WebDriver getDriver() {
		return Browser.getDriver();
	}

	LoginPage loginPage;
	AdminDashboardPage adminDashboardPage;

	@BeforeMethod
	public void setup() {
		loginPage = new LoginPage(getDriver());
		adminDashboardPage = new AdminDashboardPage(getDriver());
	}

	@Test(priority = 1, description = "Verify that the admin user can log in successfully")
	@Severity(SeverityLevel.CRITICAL)
	@Story("Admin Login")
	@Description("Admin logs in using valid credentials and is redirected to the dashboard.")
	public void adminCanLogin()
	{
		loginPage.enterUsername(TestData.adminUsername);
		loginPage.enterPassword(TestData.adminPassword);
		loginPage.clickLoginButton();
		Assert.assertTrue(Objects.requireNonNull((getDriver()).getCurrentUrl()).contains("/dashboard"),
				"Admin login failed.");
	}

	@Test(priority = 2, description = "Verify that the admin user can log out")
	@Severity(SeverityLevel.MINOR)
	@Story("Admin Logout")
	@Description("Admin opens the profile dropdown and logs out successfully.")
	public void adminCanLogout()
	{
		// Open Profile dropdown
		adminDashboardPage.openProfileDropdown();
		// Optional sleep just to visually observe the dropdown (not necessary in headless/test mode)
		utilities.Helper.sleep(500);
		// Click on Logout
		adminDashboardPage.clickLogoutButton();
	}
}
