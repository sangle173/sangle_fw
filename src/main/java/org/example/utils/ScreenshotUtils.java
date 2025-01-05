package org.example.utils;

import io.qameta.allure.Allure;
import org.example.driver_manager.AppiumDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class ScreenshotUtils {
    // Capture and attach the screenshot to Allure
    public static void attachScreenshot(String screenshotName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) AppiumDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            System.out.println("Screenshot capture ...");
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }   
}
