package org.example.framework;

import io.qameta.allure.Allure;
import org.example.driver_manager.AppiumDriverManager;
import org.example.driver_manager.SeleniumDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class ScreenshotUtil {
    // Capture and attach the screenshot to Allure
    public static void attachScreenshot(String screenshotName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) AppiumDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            System.out.println("Sc capture");
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
