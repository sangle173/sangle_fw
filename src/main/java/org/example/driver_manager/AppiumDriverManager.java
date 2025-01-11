package org.example.driver_manager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.ConfigReader;
import org.example.utils.CapabilityLoader;
import org.example.utils.Constant;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AppiumDriverManager {

    private static final Logger logger = LogManager.getLogger(AppiumDriverManager.class);
    private static AndroidDriver driver;

    /**
     * Returns the initialized AndroidDriver instance. If the driver is not already initialized,
     * it initializes the driver with the desired capabilities and server URL.
     *
     * @return AndroidDriver instance
     */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            try {
                logger.info("Initializing Appium driver...");
                // Load config file
                String deviceName = ConfigReader.getProperty("deviceName");
                logger.info("Device name loaded: {}", deviceName);

                // Load desired capabilities
                DesiredCapabilities capabilities = CapabilityLoader.getCapabilities(deviceName, Constant.DEVICES_JSON_PATH);
                logger.info("Desired capabilities loaded successfully.");

                // Fetch the Appium server URL from conf.properties
                String appiumServerUrl = ConfigReader.getAppiumServerUrl();
                logger.info("Appium Server URL: {}", appiumServerUrl);

                // Initialize the AndroidDriver
                driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
                logger.info("Appium driver initialized successfully.");

            } catch (Exception e) {
                logger.error("Failed to initialize Appium driver: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to initialize Appium driver.");
            }
        }
        return driver;
    }

    /**
     * Quits the AndroidDriver instance if it is not null.
     */
    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting Appium driver...");
            driver.quit();
            driver = null;
            logger.info("Appium driver quit successfully.");
        } else {
            logger.warn("Attempted to quit Appium driver, but it was already null.");
        }
    }
}
