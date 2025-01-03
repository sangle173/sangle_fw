package org.example.driver_manager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.remote.MobileCapabilityType;
import org.example.utils.KeyEventUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AppiumDriverManager {
    private static AndroidDriver driver;
    private static final int MAX_RETRIES = 5;  // Maximum retries for waiting for device to be available
    private static final int RETRY_INTERVAL_SECONDS = 10;  // Interval between retries
    public static AndroidDriver getDriver() {
        if (driver == null) {
            try {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
//                caps.setCapability(MobileCapabilityType.DEVICE_NAME, "an4009099402d84501b13");
                caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                caps.setCapability("appWaitActivity", "*");
                caps.setCapability("noReset", true);

                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize Appium driver.");
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // Method to reboot the device using adb command
    public static void rebootDevice() throws IOException {
        System.out.println("Rebooting the device...");
        // ADB command to reboot the device
        Process process = new ProcessBuilder("adb", "reboot").start();
        try {
            process.waitFor();
            System.out.println("Device rebooted.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to wait for the device to be available again
    public static void waitForDeviceToBeAvailable() throws InterruptedException, IOException {
        int retries = 0;
        boolean deviceAvailable = false;

        while (retries < MAX_RETRIES) {
            // Check if the device is available using adb devices
            Process process = new ProcessBuilder("adb", "devices").start();
            process.waitFor();
            // Check if the device is listed as online
            deviceAvailable = isDeviceAvailable(process);

            if (deviceAvailable) {
                System.out.println("Device is available.");
                break;
            } else {
                System.out.println("Device not available, retrying...");
                retries++;
                TimeUnit.SECONDS.sleep(RETRY_INTERVAL_SECONDS);
            }
        }

        if (!deviceAvailable) {
            throw new RuntimeException("Device did not become available after " + MAX_RETRIES + " retries.");
        }
    }

    // Method to check if device is available (based on the adb devices output)
    private static boolean isDeviceAvailable(Process process) throws IOException {
        byte[] output = process.getInputStream().readAllBytes();
        String outputString = new String(output);

        // Check if the device is listed as online
        return outputString.contains("device") && !outputString.contains("offline");
    }

    // Method to reboot the device and wait for it to be available again
    public static void rebootDeviceAndWaitForAvailability() throws IOException, InterruptedException {
        // Step 1: Reboot the device using adb command
        rebootDevice();

        // Step 2: Wait for the device to become available
        waitForDeviceToBeAvailable();
    }

    // Method to wait for the element with specific resourceId to be displayed
    public static void waitForElementToBeVisible(String xpath) {
        boolean elementDisplayed = false;
        int retryCount = 0;

        while (retryCount < MAX_RETRIES && !elementDisplayed) {
            try {
                WebElement element = driver.findElement(AppiumBy.androidUIAutomator(xpath));
                // Check if the element is displayed
                elementDisplayed = element.isDisplayed();

            } catch (Exception e) {
                retryCount++;
                System.out.println("Element with resourceId Up Next is not displayed, retrying in " + RETRY_INTERVAL_SECONDS + " seconds... Attempt " + retryCount + "/" + MAX_RETRIES);
                try {
                    Thread.sleep(RETRY_INTERVAL_SECONDS * 1000);  // Wait for the specified interval before retrying
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        if (!elementDisplayed) {
            System.out.println("Element with resourceId Up Next is not displayed, after " + MAX_RETRIES + " retries.");
        }
    }
}