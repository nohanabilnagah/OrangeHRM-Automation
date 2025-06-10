package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;

public class Helper {

	// Boolean flag to make sure that the folder cleanup method:
	private static boolean screenshotsCleaned = false;

	// Captures a screenshot and saves it under the ./Screenshots folder
	public static void captureScreenshot(WebDriver driver, String screenshotName) {
		// Clean screenshots folder only once per run
		if (!screenshotsCleaned) {
			clearScreenshotsFolder();
			screenshotsCleaned = true;
		}

		// Add a timestamp to the filename for uniqueness
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = screenshotName + "_" + timestamp + ".png"; // PNG format
		Path destination = Paths.get("./Screenshots", fileName);

		try {
			Files.createDirectories(destination.getParent());
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage image = ImageIO.read(screenshot);
			ImageIO.write(image, "png", destination.toFile());
			System.out.println("Screenshot saved: " + destination.toAbsolutePath());
		} catch (IOException e) {
			System.out.println("Error while saving screenshot: " + e.getMessage());
		}
	}

	// Deletes all files from the ./Screenshots folder before the first test runs
	private static void clearScreenshotsFolder() {
		Path screenshotsPath = Paths.get("./Screenshots");

		if (Files.exists(screenshotsPath)) {
			try (Stream<Path> files = Files.list(screenshotsPath)) {
				files.filter(Files::isRegularFile).forEach(file -> {
					try {
						Files.delete(file);
					} catch (IOException e) {
						System.out.println("Failed to delete: " + file + " (" + e.getMessage() + ")");
					}
				});
				System.out.println("Old screenshots deleted.");
			} catch (IOException e) {
				System.out.println("Failed to clean Screenshots folder: " + e.getMessage());
			}
		}
	}

	// Returns an explicit wait with the specified timeout
	public static WebDriverWait waitFor(WebDriver driver, int seconds)
	{
		return new WebDriverWait(driver, Duration.ofSeconds(seconds));
	}

	// Waits for an element to be clickable, then clicks it using Actions
	public static void waitAndClick(WebDriver driver, WebElement element, int timeoutSeconds)
	{
		waitFor(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
		new Actions(driver).moveToElement(element).click().perform();
	}

	// Waits for an element located by the given locator to be clickable and returns it
	public static WebElement waitForElementClickable(WebDriver driver, By locator, int timeoutSeconds) {
		return waitFor(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
	}

	// /Waits for an element located by the given locator to be visible on the page, then returns it
	public static WebElement waitForElementVisible(WebDriver driver, By locator, int timeoutSeconds) {
		return waitFor(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Waits for multiple elements to be visible and returns them as a list
	public static List<WebElement> waitForElementsVisible(WebDriver driver, By locator, int timeoutSeconds) {
		return waitFor(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/*
	// Waits until the specified element is no longer visible or removed from the page, up to the given timeout
	public static void waitForElementInvisible(WebDriver driver, By locator, int timeoutSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	*/

	// Pause for a short number of milliseconds
	public static void sleep(long milliseconds)
	{
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// Pause test execution for a specified number of seconds
	public static void waitForNextTestClass(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			// Restore interrupted status and ignore
			Thread.currentThread().interrupt();
		}
	}
}