package org.example.utils;

import java.io.File;

public class FileUtils {
    public static void ensureDirectoryExists(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + folderPath);
            } else {
                System.out.println("Failed to create directory: " + folderPath);
            }
        }
    }
}
