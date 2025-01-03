package org.example.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AllureAttachmentUtils {
    public static void attachLog(String logPath) {
        try (FileInputStream fis = new FileInputStream(new File(logPath))) {
            Allure.addAttachment("ADB Log Info", "txt", fis, ".txt");
        } catch (IOException e) {
            System.err.println("Failed to attach log to Allure report: " + e.getMessage());
        }
    }
}
