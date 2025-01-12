package base;

import io.appium.java_client.android.AndroidDriver;
import org.example.config.ConfigReader;
import org.example.driver_manager.AppiumDriverManager;
import org.example.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.Set;

public class BaseTest {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected AndroidDriver driver;
    private String videoFilePath;

    @BeforeClass
    public void setUp() throws Exception {
        driver = AppiumDriverManager.getDriver();
        // Get all available contexts (NATIVE_APP and WEBVIEW)
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            log.info("Available context: {}", context);
        }
    }

    @BeforeMethod
    public void beforeMethod() throws IOException {
        // Generate dynamic video filename with date and time
        if (ConfigReader.isVideoRecordingEnabled()) {
            // Start screen recording before each test method
            driver.startRecordingScreen();
            videoFilePath = VideoUtils.getVideoFileName();
        }
        AllureAttachmentUtils.attachADBCommandResult("#NSUD Build", "adb shell getprop ro.build.fingerprint");
        AllureAttachmentUtils.attachADBCommandResult("Apex Version", "adb shell cat /apex/com.sonos.player/VERSION");
        AllureAttachmentUtils.attachADBCommandResult("Device", "adb devices");
        AllureAssert.setDriver(driver);
    }

    @AfterMethod
    public void afterMethod() {
        // Stop video recording if enabled
        if (ConfigReader.isVideoRecordingEnabled()) {
            String videoBase64 = driver.stopRecordingScreen();
            // Save the video file with the generated filename
            VideoUtils.saveVideo(videoBase64, videoFilePath);
            // Attach the video to the Allure report
            VideoUtils.attachVideo(videoFilePath);
        }

        String logFilePath = ADBLogUtils.captureADBLogs();
        AllureAttachmentUtils.attachLog(logFilePath);
        AllureLogAttachmentListener.attachLogsToAllure();
    }

    @AfterClass
    public void tearDown() {
        AppiumDriverManager.quitDriver();
    }
}