package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminDashboardPage {

	private final WebDriver driver;


	public AdminDashboardPage(WebDriver driver)
	{
		this.driver = driver;
	}
	

	public void openProfileDropdown() {
		WebElement profileDropdownLocator = driver.findElement(By.cssSelector("p.oxd-userdropdown-name"));
		profileDropdownLocator.click();
	}

	public void clickLogoutButton() {
		WebElement logoutBtnLocator = driver.findElement(By.linkText("Logout"));
		logoutBtnLocator.click();
	}

	public String getProfileName() {
		return driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).getText();
	}
	
}

