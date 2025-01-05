package org.example.utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.IOException;

public class AllureLogAttachmentListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        attachLogsToAllure();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachLogsToAllure();
    }

    public static void attachLogsToAllure() {
        try (FileInputStream fis = new FileInputStream("logs/application.log")) {
            Allure.addAttachment("Test Logs", fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

