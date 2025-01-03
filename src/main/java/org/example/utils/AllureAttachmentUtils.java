package org.example.utils;

import io.qameta.allure.Attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AllureAttachmentUtils {
    @Attachment(value = "{attachmentName}", type = "text/plain")
    public static byte[] attachADBLogs(String attachmentName, String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            return fis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
