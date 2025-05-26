package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Helper;


public class AdminPage {

	private final WebDriver driver;

	public AdminPage(WebDriver driver)
	{
		this.driver = driver;
	}

	// Scenario 1 - Create User

	public void clickAdminTab() {
		WebElement adminTabLocator = driver.findElement(By.xpath("//span[text()='Admin']"));
		adminTabLocator.click();
	}

	public void clickAddButton() {
		WebElement addBtnLocator = driver.findElement(By.cssSelector("button.oxd-button.oxd-button--secondary i.bi-plus"));
		addBtnLocator.click();
	}

	public void clickUserRoleDropdown() {
		WebElement userRoleDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[1]"));
		Helper.waitAndClick(driver, userRoleDropdownLocator, 10);
		WebElement essOption = driver.findElement(By.xpath("//span[text()='ESS']"));
		Helper.waitAndClick(driver, essOption, 10);
	}

	public void enterEmployeeName(String employeeName) {
		WebElement employeeNameLocator = driver.findElement(By.cssSelector("input[placeholder='Type for hints...']"));
		employeeNameLocator.clear();
		employeeNameLocator.sendKeys(employeeName);
		WebElement employeeNameSuggestion = driver.findElement(By.xpath("//div[@class='oxd-autocomplete-dropdown --positon-bottom']//span"));
		Helper.waitAndClick(driver, employeeNameSuggestion, 10);
	}

	public void clickStatusDropdown() {
		WebElement statusDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[2]"));
		statusDropdownLocator.click();
		WebElement enabledOption = driver.findElement(By.xpath("//span[text()='Enabled']"));
		Helper.waitAndClick(driver, enabledOption, 10);
	}

	public void enterNewUsername(String newUsername) {
		WebElement newUsernameLocator = driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]"));
		newUsernameLocator.clear();
		newUsernameLocator.sendKeys(newUsername);
	}

	public void enterNewPassword(String newPassword) {
		WebElement newPasswordLocator = driver.findElement(By.xpath("(//input[@type='password'])[1]"));
		newPasswordLocator.clear();
		newPasswordLocator.sendKeys(newPassword);
	}

	public void confirmNewPassword(String newPassword) {
		WebElement confirmNewPasswordLocator = driver.findElement(By.xpath("(//input[@type='password'])[2]"));
		confirmNewPasswordLocator.clear();
		confirmNewPasswordLocator.sendKeys(newPassword);
	}

	public void clickSaveButton() {
		WebElement saveBtnLocator = driver.findElement(By.cssSelector("button[type='submit']"));
		saveBtnLocator.click();
	}

	public WebElement getSuccessToast()
	{
		return driver.findElement(By.cssSelector("div.oxd-toast-content--success"));
	}

	// Scenario 2 - Disable User

	public void filterByUsername(String username) {
		WebElement filterUsernameLocator = driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]"));
		filterUsernameLocator.clear();
		filterUsernameLocator.sendKeys(username);
	}

	public void filterByUserRole() {
		WebElement filterUserRoleDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[1]"));
		Helper.waitAndClick(driver, filterUserRoleDropdownLocator, 10);
		WebElement essOption = driver.findElement(By.xpath("//span[text()='ESS']"));
		Helper.waitAndClick(driver, essOption, 10);
	}

	public void filterByEmployeeName(String employeeName) {
		WebElement filterEmployeeNameLocator = driver.findElement(By.xpath("//input[@placeholder='Type for hints...']"));
		filterEmployeeNameLocator.clear();
		filterEmployeeNameLocator.sendKeys(employeeName);
		WebElement employeeNameSuggestion = driver.findElement(By.xpath("//div[@class='oxd-autocomplete-dropdown --positon-bottom']//span"));
		Helper.waitAndClick(driver, employeeNameSuggestion, 10);
	}

	public void filterByStatus() {
		WebElement filterStatusDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[2]"));
		Helper.waitAndClick(driver, filterStatusDropdownLocator, 10);
		WebElement enabledOption = driver.findElement(By.xpath("//span[text()='Enabled']"));
		Helper.waitAndClick(driver, enabledOption, 10);
	}

	public void clickSearchButton() {
		WebElement searchBtnLocator = driver.findElement(By.xpath("//button[@type='submit']"));
		Helper.waitAndClick(driver, searchBtnLocator, 10);
	}

	public void clickEditAction() {
		WebElement editIconLocator = driver.findElement(By.cssSelector("i.bi-pencil-fill"));
		Helper.waitAndClick(driver, editIconLocator, 10);
	}

	public void changeStatusToDisabled() {
		WebElement changeStatusDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[2]"));
		changeStatusDropdownLocator.click();
		WebElement disabledOption = driver.findElement(By.xpath("//span[text()='Disabled']"));
		Helper.waitAndClick(driver, disabledOption, 10);
	}

	public void clickSaveButtonAfterEdit() {
		WebElement saveBtnAfterEditLocator = driver.findElement(By.cssSelector("button[type='submit']"));
		Helper.waitAndClick(driver, saveBtnAfterEditLocator, 10);
	}

	public WebElement getSuccessToastAfterEdit() {
		return driver.findElement(By.cssSelector("div.oxd-toast-content--success"));
	}
}

