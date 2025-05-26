package test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utilities.Browser;
import utilities.Helper;

public class TestBase {
	
	protected WebDriver driver;
	String baseUrl = "https://opensource-demo.orangehrmlive.com/";
	
	@BeforeSuite
	@Parameters ({"browser"})
	public void setUpDriver(@Optional("chrome") String browser)
	{
		Browser.initializeDriver(browser);
		driver = Browser.getDriver();
		driver.get(baseUrl);
	}


	@AfterSuite
	public void stopDriver() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// Safe to ignore or handle
			Thread.currentThread().interrupt(); // Optional: restore interrupted status
		}
		Browser.stopDriver();
	}

	
	// Take screenshot when test case fail and add it in the Screenshot folder
	@AfterMethod
	public void screenshotOnFailure(ITestResult result)
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			System.out.println("Failed! Taking Screenshot...");
			Helper.captureScreenshot(driver, result.getName());
		}
	}
}
