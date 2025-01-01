package org.example.driver_manager;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AppiumDriverManager {
    private static AndroidDriver driver;

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
}