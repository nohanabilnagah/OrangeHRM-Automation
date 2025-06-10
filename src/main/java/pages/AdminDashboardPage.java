package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Helper;

public class AdminDashboardPage {

	private final WebDriver driver;

	public AdminDashboardPage(WebDriver driver)
	{
		this.driver = driver;
	}

	public void openProfileDropdown() {
		// Wait until the profile dropdown tab is clickable, then click it
		WebElement profileDropdownLocator = Helper.waitForElementClickable(
				driver,
				By.cssSelector("p.oxd-userdropdown-name"),
				10
		);
		profileDropdownLocator.click();
	}

	public void clickLogoutButton() {
		// Wait until the “Logout” link is clickable, then click it
		WebElement logoutBtnLocator = Helper.waitForElementClickable(
				driver,
				By.linkText("Logout"),
				10
		);
		logoutBtnLocator.click();
	}

	public String getProfileName() {
		// Wait until that username element is visible, then return its text
		WebElement profileNameElem = Helper.waitForElementVisible(
				driver,
				By.cssSelector("p.oxd-userdropdown-name"),
				10
		);
		return profileNameElem.getText();
	}
}