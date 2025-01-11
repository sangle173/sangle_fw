package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.ConfigReader;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    private static final int MAX_RETRIES = Integer.parseInt(ConfigReader.getProperty("max.retries"));  // Maximum retries for waiting for device to be available
    private static final int RETRY_INTERVAL_TIME = Integer.parseInt(ConfigReader.getProperty("retry.interval.time"));  // Interval between retries

    // Method to reboot the device using adb command
    public static void rebootDevice() throws IOException {
        logger.info("Rebooting the device...");
        Process process = new ProcessBuilder("adb", "reboot").start();
        try {
            process.waitFor();
            logger.info("Device rebooted.");
        } catch (InterruptedException e) {
            logger.error("Error occurred while rebooting the device: {}", e.getMessage(), e);
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
                logger.info("Device is available.");
                break;
            } else {
                logger.warn("Device not available, retrying... Attempt {}/{}", retries + 1, MAX_RETRIES);
                retries++;
                TimeUnit.SECONDS.sleep(RETRY_INTERVAL_TIME);
            }
        }

        if (!deviceAvailable) {
            logger.error("Device did not become available after {} retries.", MAX_RETRIES);
            throw new RuntimeException("Device did not become available after " + MAX_RETRIES + " retries.");
        }
    }

    // Method to check if device is available (based on the adb devices output)
    public static boolean isDeviceAvailable(Process process) throws IOException {
        byte[] output = process.getInputStream().readAllBytes();
        String outputString = new String(output);

        // Check if the device is listed as online
        boolean isAvailable = outputString.contains("device") && !outputString.contains("offline");
        logger.debug("Device availability check output: {}, Is available: {}", outputString.trim(), isAvailable);
        return isAvailable;
    }

    // Method to reboot the device and wait for it to be available again
    public static void rebootDeviceAndWaitForAvailability() throws IOException, InterruptedException {
        logger.info("Rebooting the device and waiting for it to become available...");
        rebootDevice();
        waitForDeviceToBeAvailable();
    }

    // Method to wait for the element with specific resourceId to be displayed
    public static void waitForElementToBeVisible(AndroidDriver driver, String locator) {
        boolean elementDisplayed = false;
        int retryCount = 0;

        while (retryCount < MAX_RETRIES && !elementDisplayed) {
            try {
                WebElement element = ElementUtils.findElement(driver, locator);
                // Check if the element is displayed
                elementDisplayed = element.isDisplayed();
            } catch (Exception e) {
                retryCount++;
                logger.warn("Element is not displayed, retrying in {} seconds... Attempt {}/{}", RETRY_INTERVAL_TIME, retryCount, MAX_RETRIES);
                try {
                    Thread.sleep(RETRY_INTERVAL_TIME * 1000);  // Wait for the specified interval before retrying
                } catch (InterruptedException ie) {
                    logger.error("Interrupted while waiting to retry: {}", ie.getMessage(), ie);
                }
            }
        }

        if (!elementDisplayed) {
            logger.error("Element with locator '{}' is not displayed after {} retries.", locator, MAX_RETRIES);
            throw new RuntimeException("Element not displayed after " + MAX_RETRIES + " retries.");
        } else {
            logger.info("Element with locator '{}' is displayed.", locator);
        }
    }
}
