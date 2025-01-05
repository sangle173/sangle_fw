package org.example.framework;

import io.appium.java_client.android.AndroidDriver;
import org.example.config.ConfigReader;
import org.example.driver_manager.AppiumDriverManager;
import org.example.utils.ADBLogUtils;
import org.example.utils.AllureAttachmentUtils;
import org.example.utils.Constant;
import org.example.utils.VideoUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class BaseTest {
    protected AndroidDriver driver;
    private String videoFilePath;

    @BeforeClass
    public void setUp() throws Exception {
        driver = AppiumDriverManager.getDriver();
    }

    @BeforeMethod
    public void beforeMethod() throws IOException {
        // Generate dynamic video filename with date and time
        if (ConfigReader.isVideoRecordingEnabled()) {
            // Start screen recording before each test method
            driver.startRecordingScreen();
            videoFilePath = VideoUtil.getVideoFileName();
        }
    }

    @AfterMethod
    public void afterMethod() {
        // Stop video recording if enabled
        if (ConfigReader.isVideoRecordingEnabled()) {
            String videoBase64 = driver.stopRecordingScreen();
            // Save the video file with the generated filename
            VideoUtil.saveVideo(videoBase64, videoFilePath);
            // Attach the video to the Allure report
            VideoUtil.attachVideo(videoFilePath);
        }

        String logFilePath = ADBLogUtils.captureADBLogs();
        AllureAttachmentUtils.attachLog(logFilePath);
    }


    @AfterClass
    public void tearDown() {
        AppiumDriverManager.quitDriver();
    }
}