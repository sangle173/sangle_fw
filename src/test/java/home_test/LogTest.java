package home_test;

import io.qameta.allure.Allure;
import base.BaseTest;
import org.example.utils.AllureAssert;
import org.example.utils.LogUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Set;

@Listeners({org.example.utils.TestListener.class, org.example.utils.AllureLogAttachmentListener.class})
public class LogTest extends BaseTest {

    @Test
    public void logTest() {
        Allure.step("Get Log Info");
        LogUtils.info("Starting the test...");
        LogUtils.debug("This is a debug message.");
        LogUtils.error("An error occurred!", new RuntimeException("Sample exception"));
    }

    @Test
    public void testAppLaunch() {
        // Get all available contexts (NATIVE_APP and WEBVIEW)
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            System.out.println("Available context: " + context);
        }
    }
}
