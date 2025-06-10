package utilities;

public class TestData {

    public static final String adminUsername = "Admin";
    public static final String adminPassword = "admin123";
    public static final String employeeName = "Orange Test";
    public static final String newPassword = "test123456";

    // Provide a method to generate a unique username on demand
    public static String generateUniqueUsername() {
        return "user" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    // Store actual profile name captured during login
    public static String loggedInProfileName = "";
}
