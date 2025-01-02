package org.example.framework;

import io.appium.java_client.android.AndroidDriver;
import org.example.driver_manager.AppiumDriverManager;
import org.example.utils.RecordVideo;
import org.example.utils.VideoUtil;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class BaseTest {
    protected AndroidDriver driver;
    private String videoFilePath;
    @BeforeClass
    public void setUp() throws Exception {
        driver = AppiumDriverManager.getDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        // Start screen recording before each test method
        driver.startRecordingScreen();

        // Generate dynamic video filename with date and time
        videoFilePath = VideoUtil.getVideoFileName();
    }

    @AfterMethod
    public void afterMethod() {
        // Stop the screen recording after each test method
        String videoBase64 = driver.stopRecordingScreen();

        // Save the video file with the generated filename
        VideoUtil.saveVideo(videoBase64, videoFilePath);

        // Attach the video to the Allure report
        VideoUtil.attachVideo(videoFilePath);
    }


    @AfterClass
    public void tearDown() throws Exception {
        AppiumDriverManager.quitDriver();
    }
}