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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;

public class Helper {


	// Method to take screenshot with timestamp and .jpg extension
	public static void captureScreenshot(WebDriver driver, String screenshotName) {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = screenshotName + "_" + timestamp + ".jpg";
		Path destination = Paths.get("./Screenshots", fileName);

		try {
			Files.createDirectories(destination.getParent());
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage image = ImageIO.read(screenshot);
			ImageIO.write(image, "jpg", destination.toFile());
		} catch (IOException e) {
			System.out.println("Exception while taking screenshot: " + e.getMessage());
		}
	}
		
		
		public static WebDriverWait waitFor(WebDriver driver, int seconds) 
		{
	        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
	    }

		
	    public static void waitAndClick(WebDriver driver, WebElement element, int timeoutSeconds) 
	    {
	        waitFor(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
	        new Actions(driver).moveToElement(element).click().perform();
	    }

	}
