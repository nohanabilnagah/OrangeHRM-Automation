package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironmentInfo(String browserName) {
        try {
            Properties props = new Properties();
            props.setProperty("Browser", browserName);
            props.setProperty("Environment", "Staging");
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("Language", "en-US");
            props.setProperty("BaseURL", "https://opensource-demo.orangehrmlive.com/");

            File resultsDir = new File("allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdir(); // Create allure-results directory if missing
            }

            FileWriter writer = new FileWriter("allure-results/environment.properties");
            props.store(writer, "Allure Environment Properties");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
