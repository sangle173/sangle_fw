package org.example.utils;

import com.codeborne.selenide.Selenide;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AllureAssert {
    private static AndroidDriver driver;

    /**
     * Initialize the AndroidDriver.
     * This should be called in the test setup to set the driver instance.
     *
     * @param androidDriver The Appium AndroidDriver instance.
     */
    public static void setDriver(AndroidDriver androidDriver) {
        driver = androidDriver;
    }

    /**
     * Logs a checkpoint into the Allure report and performs a soft assertion.
     *
     * @param description The description of the checkpoint.
     * @param condition   The condition to assert.
     */
    public static void assertTrue(boolean condition, String description) {
        try {
            logCheckpoint(description);
            Assert.assertTrue(condition, description);
            // Log the step as a success if no error
            Allure.step(description + " passed");
        } catch (AssertionError e) {
            logFailedCheckpoint(description);
            throw e; // Re-throw the assertion error to let the test framework handle it.
        }
    }

    /**
     * Logs a checkpoint into the Allure report and performs a hard equality assertion.
     *
     * @param description The description of the checkpoint.
     * @param actual      The actual value.
     * @param expected    The expected value.
     */
    public static void assertEquals(Object actual, Object expected, String description) {
        try {
            logCheckpoint(description);
            Assert.assertEquals(actual, expected, description);
            // Log success in Allure
            Allure.step(description + " passed");
        } catch (AssertionError e) {
            logFailedCheckpoint(description);
            attachText("Actual vs Expected", "Actual: " + actual + "\nExpected: " + expected);
            throw e;
        }
    }

    /**
     * Logs a custom checkpoint into Allure.
     *
     * @param description The description of the checkpoint.
     */
    private static void logCheckpoint(String description) {
        Allure.step(description);
    }

    /**
     * Logs a failed checkpoint into Allure and attaches a screenshot.
     *
     * @param description The description of the failed checkpoint.
     */
    private static void logFailedCheckpoint(String description) {
        // Log failure in Allure as a failed step (red cross)
        Allure.step(description + " failed", () -> {
            attachScreenshot("Failed Checkpoint: " + description);
            // Log the failed step
            throw new AssertionError(description + " failed");
        });
    }

    /**
     * Attaches a screenshot to the Allure report.
     *
     * @param name The name of the screenshot.
     */
    private static void attachScreenshot(String name) {
        if (driver != null) {
            try {
                byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));
            } catch (Exception e) {
                Allure.step("Unable to capture screenshot: " + e.getMessage());
            }
        } else {
            Allure.step("AndroidDriver not initialized; screenshot not captured.");
        }
    }

    /**
     * Attaches custom text information to the Allure report.
     *
     * @param name    The name of the attachment.
     * @param content The content to attach.
     */
    private static void attachText(String name, String content) {
        Allure.addAttachment(name, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
    }
}