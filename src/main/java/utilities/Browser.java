package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.time.Duration;

public class Browser {

	// Thread-safe WebDriver instance for parallel execution
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// Thread-safe browser name for the current thread
	private static final ThreadLocal<String> browserName = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	// Return current browser name in this thread (e.g. "chrome", "firefox")
	public static String getBrowserName() {
		return browserName.get();
	}

	// Initializes WebDriver based on the provided browser name
	public static void initializeDriver(String browser) {
		WebDriver localDriver = null;

		if (browser.equalsIgnoreCase("chrome")) {
			// Setup and configure ChromeDriver with English (US) language
			WebDriverManager.chromedriver().setup(); //
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--lang=en-US"); // or en-GB as preferred
			localDriver = new ChromeDriver(chromeOptions);
		} else if (browser.equalsIgnoreCase("firefox")) {
			// Setup and configure FirefoxDriver with English (US) language
			WebDriverManager.firefoxdriver().setup(); //
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setPreference("intl.accept_languages", "en-US, en");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setProfile(firefoxProfile);
			localDriver = new FirefoxDriver(firefoxOptions);
		} else if (browser.equalsIgnoreCase("edge")) {
			// Setup and configure EdgeDriver with English (US) language
			WebDriverManager.edgedriver().setup(); //
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--lang=en-US");
			localDriver = new EdgeDriver(edgeOptions);
		}

		if (localDriver != null) {
			localDriver.manage().window().maximize();
			localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.set(localDriver);

			// Save browser name for this thread for later reference
			browserName.set(browser.toLowerCase());
		}
	}

	// Quits and removes the WebDriver instance for the current thread
	public static void stopDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
			browserName.remove();
		}
	}
}