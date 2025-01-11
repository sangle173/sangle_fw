package org.example.utils;

import org.slf4j.LoggerFactory;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtils {
    // Create a logger for the class
    private static final Logger logger = LogManager.getLogger(FileUtils.class);

    public static void ensureDirectoryExists(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Directory created: {}", folderPath);
            } else {
                logger.error("Failed to create directory: {}", folderPath);
            }
        } else {
            logger.info("Directory already exists: {}", folderPath);
        }
    }
}
