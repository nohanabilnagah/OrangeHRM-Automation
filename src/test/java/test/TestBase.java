package test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.AllureEnvironmentWriter;
import utilities.Browser;
import utilities.Helper;

public class TestBase {

	protected WebDriver driver;
	String baseUrl = "https://opensource-demo.orangehrmlive.com/";

	// This method runs before any test methods in the class
	@BeforeClass
	@Parameters("browser")  // Accept browser parameter from testng.xml
	public void setUpDriver(@Optional("chrome") String browser)
	{
		// Initialize WebDriver using the custom Browser utility
		Browser.initializeDriver(browser);
		AllureEnvironmentWriter.writeEnvironmentInfo(Browser.getBrowserName());
		driver = Browser.getDriver();
		driver.get(baseUrl);
	}


	// This method runs after each test method
	@AfterMethod
	public void screenshotOnFailure(ITestResult result) {
		// Capture a screenshot if the test fails and add it in the Screenshot folder
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Failed! Taking Screenshot...");
			Helper.captureScreenshot(driver, result.getName());
		}
	}

	// This method runs after all test methods in the class
	@AfterClass(alwaysRun = true)   // ensures it runs even if previous steps failed
	public void tearDown() {
		Helper.sleep(1000); // Wait 1 second to view the result before closing
		Browser.stopDriver(); // Stop the browser
	}
}

