package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import java.util.Objects;

public class AdminLoginTest extends TestBase {
	
	LoginPage loginPage;
	String adminUsername = "Admin";
	String adminPassword = "admin123";
	
	@Test
	public void adminCanLogin()
	{
		loginPage = new LoginPage(driver);
		loginPage.enterUsername(adminUsername);
		loginPage.enterPassword(adminPassword);
		loginPage.clickLoginButton();
		Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/dashboard"),
				"Admin login failed.");
	}
}
