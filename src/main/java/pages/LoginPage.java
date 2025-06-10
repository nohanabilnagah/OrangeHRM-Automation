package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	private final WebDriver driver;

	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
	}


	public void enterUsername(String adminUsername) {
		WebElement usernameLocator = driver.findElement(By.name("username"));
		usernameLocator.clear();
		usernameLocator.sendKeys(adminUsername);
	}

	public void enterPassword(String adminPassword) {
		WebElement passwordLocator = driver.findElement(By.name("password"));
		passwordLocator.clear();
		passwordLocator.sendKeys(adminPassword);
	}

	public void clickLoginButton() {
		WebElement loginBtnLocator = driver.findElement(By.cssSelector("button[type='submit']"));
		loginBtnLocator.click();
	}
}