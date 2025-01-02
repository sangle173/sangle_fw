package org.example.utils;

import org.example.framework.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        // Attach screenshot to Allure report
        ScreenshotUtil.attachScreenshot("Failure Screenshot - " + testName);
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Code for test start (optional)
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Code for test success (optional)
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Code for test skipped (optional)
    }

    @Override
    public void onFinish(ITestContext context) {
        // Code for test completion (optional)
    }
}
