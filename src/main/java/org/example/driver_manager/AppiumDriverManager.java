package org.example.driver_manager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.example.config.ConfigReader;
import org.example.utils.CapabilityLoader;
import org.example.utils.Constant;
import org.example.utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppiumDriverManager {
    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            try {
                // Load config file
                String deviceName = ConfigReader.getProperty("deviceName");
                // Load desired capabilities
                DesiredCapabilities capabilities = CapabilityLoader.getCapabilities(deviceName, Constant.DEVICES_JSON_PATH);

                // Fetch the Appium server URL from conf.properties
                String appiumServerUrl = ConfigReader.getAppiumServerUrl();
                driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
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


}