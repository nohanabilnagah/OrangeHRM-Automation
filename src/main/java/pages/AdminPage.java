package pages;

import org.openqa.selenium.*;
import utilities.Helper;
import java.util.List;
import java.util.NoSuchElementException;


public class AdminPage {

	private final WebDriver driver;

	public AdminPage(WebDriver driver)
	{
		this.driver = driver;
	}


	// Scenario 1 - Create User

	public void clickAdminTab() {
		WebElement adminTabLocator = driver.findElement(By.xpath("//span[text()='Admin']"));
		Helper.waitAndClick(driver, adminTabLocator, 10);
	}

	public void clickAddButton() {
		// Locate the “plus” icon inside the secondary button and click it
		WebElement addIcon = driver.findElement(By.cssSelector("button.oxd-button.oxd-button--secondary i.bi-plus"));
		Helper.waitAndClick(driver, addIcon, 10);
	}

	public void clickUserRoleDropdown() {
		By userRoleDropdownLocator = By.xpath("(//div[@class='oxd-select-text--after'])[1]");
		Helper.waitAndSelect(driver, userRoleDropdownLocator, "ESS", 10);
	}

	public void enterEmployeeName(String employeeName) {
		// Locate employee name input field (by placeholder)
		WebElement employeeNameInput = driver.findElement(By.cssSelector("input[placeholder='Type for hints...']"));

		// Clear existing text using CTRL+A + BACKSPACE (works better for dynamic inputs)
		employeeNameInput.click();
		employeeNameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		employeeNameInput.sendKeys(Keys.BACK_SPACE);

		// Type the full employee name
		employeeNameInput.sendKeys(employeeName);

		Helper.sleep(1500); // Wait for suggestions to appear

		// Get suggestions dropdown list spans
		List<WebElement> suggestions = driver.findElements(By.xpath("//div[contains(@class, 'oxd-autocomplete-dropdown')]//span"));

		// If no suggestions or first suggestion is 'No Records Found'
		if (suggestions.isEmpty() || suggestions.get(0).getText().equalsIgnoreCase("No Records Found")) {
			System.out.println("No valid suggestions found for '" + employeeName + "'. Trying random letters...");

			String[] randomLetters = {"a", "e", "i", "o", "u", "t", "s", "r", "n", "l"};
			boolean foundSuggestion = false;

			for (String letter : randomLetters) {
				// Re-find input fresh before every attempt
				employeeNameInput = driver.findElement(By.cssSelector("input[placeholder='Type for hints...']"));

				// Clear input using CTRL+A + BACKSPACE
				employeeNameInput.click();
				employeeNameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				employeeNameInput.sendKeys(Keys.BACK_SPACE);

				System.out.println("Typing random letter: " + letter);
				employeeNameInput.sendKeys(letter);

				Helper.sleep(1500); // Wait for suggestions again

				// Re-find suggestions fresh
				suggestions = driver.findElements(By.xpath("//div[contains(@class, 'oxd-autocomplete-dropdown')]//span"));

				if (!suggestions.isEmpty() && !suggestions.get(0).getText().equalsIgnoreCase("No Records Found")) {
					foundSuggestion = true;
					System.out.println("Suggestions found after typing letter: " + letter);
					break;
				}
			}

			if (!foundSuggestion) {
				throw new NoSuchElementException("No employee name suggestions found after trying random letters.");
			}
		}

		// Click on the first suggestion available
		System.out.println("Clicking first suggestion: " + suggestions.get(0).getText());
		Helper.waitAndClick(driver, suggestions.get(0), 20);
	}



	public void clickStatusDropdown() {
		By statusDropdownLocator = By.xpath("(//div[@class='oxd-select-text--after'])[2]");
		Helper.waitAndSelect(driver, statusDropdownLocator, "Enabled", 20);
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
		Helper.waitAndClick(driver, saveBtnLocator, 20);
	}

	public WebElement getSuccessToast()
	{
		// Wait for the success toast to be visible, then return it
		return utilities.Helper.waitForElementVisible(driver,
				By.cssSelector("div.oxd-toast-content--success"), 20);
	}

	// Scenario 2 - Disable User

	public void filterByUsername(String username) {
		WebElement filterUsernameLocator = driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]"));
		filterUsernameLocator.clear();
		filterUsernameLocator.sendKeys(username);
	}

	public void filterByUserRole() {
		By filterUserRoleDropdownLocator = By.xpath("(//div[@class='oxd-select-text--after'])[1]");
		Helper.waitAndSelect(driver, filterUserRoleDropdownLocator, "ESS", 20);
	}

	public void filterByStatus() {
		By filterStatusDropdownLocator = By.xpath("(//div[@class='oxd-select-text--after'])[2]");
		Helper.waitAndSelect(driver, filterStatusDropdownLocator, "Enabled", 20);
	}

	public void clickSearchButton() {
		WebElement searchBtnLocator = driver.findElement(By.xpath("//button[@type='submit']"));
		Helper.waitAndClick(driver, searchBtnLocator, 20);
	}

	public void clickEditAction() {
		WebElement editIconLocator = driver.findElement(By.cssSelector("i.bi-pencil-fill"));
		Helper.waitAndClick(driver, editIconLocator, 30);
	}

	public void changeStatusToDisabled() {
		By statusDropdownToggle = By.xpath("//label[text()='Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text')]");
		Helper.waitAndSelect(driver, statusDropdownToggle, "Disabled", 20);
	}

	public void clickSaveButtonAfterEdit() {
		WebElement saveBtnAfterEditLocator = driver.findElement(By.cssSelector("button[type='submit']"));
		Helper.waitAndClick(driver, saveBtnAfterEditLocator, 20);
	}

	public WebElement getSuccessToastAfterEdit() {
		return driver.findElement(By.cssSelector("div.oxd-toast-content--success"));
	}
}