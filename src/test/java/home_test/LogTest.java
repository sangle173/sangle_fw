package home_test;

import io.qameta.allure.Allure;
import base.BaseTest;
import org.example.utils.LogUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({org.example.utils.TestListener.class, org.example.utils.AllureLogAttachmentListener.class})
public class LogTest extends BaseTest {

    @Test
    public void logTest() {
        Allure.step("Get Log Info");
        LogUtils.info("Starting the test...");
        LogUtils.debug("This is a debug message.");
        LogUtils.error("An error occurred!", new RuntimeException("Sample exception"));
    }
}
