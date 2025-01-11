package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ADBLogUtils {

    private static final Logger logger = LogManager.getLogger(ADBLogUtils.class);

    /**
     * Captures ADB logs and saves them to a file.
     *
     * @return The full path of the log file.
     */
    public static String captureADBLogs() {
        // Format the current date and time
        String currentDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        // Define the log file name
        String logFileName = "adb-log_" + currentDate + ".txt";

        // Define the full path for the log file
        FileUtils.ensureDirectoryExists("adb-logs");
        String logFilePath = "adb-logs/" + logFileName;
        Process process = null;

        try {
            // Ensure the logs directory exists
            File logDir = new File("adb-logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
                logger.info("Logs directory created: {}", logDir.getAbsolutePath());
            }

            // Run adb logcat command and capture logs
            logger.info("Capturing ADB logs...");
            process = new ProcessBuilder("adb", "logcat", "-d").start(); // -d flag dumps the logs and exits
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            FileWriter writer = new FileWriter(logFilePath);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }

            writer.close();
            logger.info("ADB logs captured at: {}", logFilePath);
        } catch (IOException e) {
            logger.error("Error capturing ADB logs: {}", e.getMessage(), e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return logFilePath; // Return the log file path
    }
}
