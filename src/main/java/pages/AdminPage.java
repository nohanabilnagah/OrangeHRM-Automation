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
		// Locate the user role dropdown toggle element
		WebElement userRoleDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[1]"));

		// Add extra wait if browser is Firefox to help with flaky dropdown clicks
		if (utilities.Browser.getBrowserName().equals("firefox")) {
			Helper.sleep(1500);  // 1.5 seconds pause before clicking dropdown
		}

		// Wait until the dropdown toggle is clickable and click it to open the dropdown list
		Helper.waitAndClick(driver, userRoleDropdownLocator, 10);

		// Wait until the dropdown list container is visible on the page
		WebElement dropdownList = Helper.waitForElementVisible(driver, By.xpath("//div[@role='listbox']"), 10);

		// Find the "ESS" option inside the visible dropdown list (no explicit wait here)
		WebElement essOption = dropdownList.findElement(By.xpath(".//span[text()='ESS']"));

		// Wait until the ESS option is clickable and click it
		Helper.waitAndClick(driver, essOption, 10);
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
		Helper.waitAndClick(driver, suggestions.get(0), 10);
	}


	public void clickStatusDropdown() {
		WebElement statusDropdownLocator = driver.findElement(By.xpath("(//div[@class='oxd-select-text--after'])[2]"));
		Helper.waitAndClick(driver, statusDropdownLocator, 10);
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
		Helper.waitAndClick(driver, saveBtnLocator, 10);
	}

	public WebElement getSuccessToast()
	{
		// Wait for the success toast to be visible, then return it
		return utilities.Helper.waitForElementVisible(driver,
				By.cssSelector("div.oxd-toast-content--success"), 10);
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
		WebElement employeeNameSuggestion = Helper.waitForElementVisible(driver,
				By.xpath("//div[@class='oxd-autocomplete-dropdown --positon-bottom']//span"), 10);
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
		// 1. Locate the Status dropdown toggle element
		WebElement statusDropdownToggle = Helper.waitForElementClickable(driver,
				By.xpath("//label[text()='Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text')]"),
				10);

		// 2. Click the dropdown toggle to open the list
		// Use JavaScript click as a fallback if normal click fails
		try {
			statusDropdownToggle.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", statusDropdownToggle);
		}

		// 3. Wait briefly for dropdown options to render
		Helper.sleep(700);

		// 4. Now locate the dropdown options container
		WebElement dropdownList = Helper.waitForElementVisible(driver,
				By.xpath("//div[@role='listbox']"),
				10);

		// 5. Locate all options inside the dropdown list
		List<WebElement> options = dropdownList.findElements(By.xpath(".//div[@role='option']"));

		// 6. Debug output of options
		System.out.println("Status dropdown options:");
		for (WebElement option : options) {
			System.out.println("- " + option.getText());
		}

		// 7. Loop through options and click "Disabled"
		boolean disabledFound = false;
		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase("Disabled")) {
				try {
					option.click();
				} catch (Exception e) {
					// fallback: JS click
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
				}
				disabledFound = true;
				break;
			}
		}

		if (!disabledFound) {
			throw new RuntimeException("Could not find 'Disabled' option in Status dropdown.");
		}

		// 8. Optional: wait to ensure status is set
		Helper.sleep(500);
	}

	public void clickSaveButtonAfterEdit() {
		WebElement saveBtnAfterEditLocator = driver.findElement(By.cssSelector("button[type='submit']"));
		Helper.waitAndClick(driver, saveBtnAfterEditLocator, 10);
	}

	public WebElement getSuccessToastAfterEdit() {
		return driver.findElement(By.cssSelector("div.oxd-toast-content--success"));
	}
}

