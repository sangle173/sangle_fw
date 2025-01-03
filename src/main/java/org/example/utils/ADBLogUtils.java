package org.example.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ADBLogUtils {
    public static String captureADBLogs() {
        // Format the current date and time
        String currentDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        // Define the log file name as adb-log_<currentDate>.txt
        String logFileName = "adb-log_" + currentDate + ".txt";

        // Define the full path for the log file
        String logFilePath = "logs/" + logFileName;
        Process process = null;

        try {
            // Ensure the logs directory exists
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Run adb logcat command and capture logs
            process = new ProcessBuilder("adb", "logcat", "-d").start();  // -d flag dumps the logs and exits
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            FileWriter writer = new FileWriter(logFilePath);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }

            writer.close();
            System.out.println("ADB logs captured at: " + logFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return logFilePath;  // Return the log file path
    }
}
