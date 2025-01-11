package org.example.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.driver_manager.AppiumDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    // Capture and attach the screenshot to Allure
    public static void attachScreenshot(String screenshotName) {
        try {
            logger.info("Attempting to capture a screenshot...");
            byte[] screenshotBytes = ((TakesScreenshot) AppiumDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
            logger.info("Screenshot '{}' captured and attached to Allure report.", screenshotName);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
        }
    }
}
