package org.example.utils;

import io.qameta.allure.Allure;

import java.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class AllureAttachmentUtils {
    private static final Logger logger = LogManager.getLogger(AllureAttachmentUtils.class);

    /**
     * Attaches the log to Allure and counts occurrences of "FATAL EXCEPTION."
     *
     * @param adbLogPath Path to the log file
     */
    public static void attachLog(String adbLogPath) {
        File adbLogFile = new File(adbLogPath);

        if (!adbLogFile.exists()) {
            logger.error("ADB log file does not exist at path: {}", adbLogPath);
            return;
        }

        StringBuilder fatalSummary = new StringBuilder();
        int fatalCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(adbLogFile))) {
            String line;
            StringBuilder currentFatal = null;
            int linesToCapture = 0;

            // Process the log file line by line
            while ((line = reader.readLine()) != null) {
                if (line.contains("FATAL EXCEPTION")) {
                    // Start capturing a new FATAL EXCEPTION
                    if (currentFatal != null) {
                        fatalSummary.append(currentFatal).append(System.lineSeparator());
                    }
                    currentFatal = new StringBuilder();
                    currentFatal.append(line).append(System.lineSeparator());
                    linesToCapture = 20; // Capture 20 lines below
                    fatalCount++;
                } else if (linesToCapture > 0 && currentFatal != null) {
                    // Add lines below the FATAL EXCEPTION
                    currentFatal.append(line).append(System.lineSeparator());
                    linesToCapture--;
                }
            }

            // Add the last captured FATAL EXCEPTION
            if (currentFatal != null) {
                fatalSummary.append(currentFatal).append(System.lineSeparator());
            }

            // Attach full ADB log file to Allure
            try (FileInputStream fis = new FileInputStream(adbLogFile)) {
                Allure.addAttachment("Full ADB Log", "text/plain", fis, ".txt");
                logger.info("Successfully attached full ADB log to Allure.");
            }

            // Attach summarized FATAL EXCEPTION log to Allure
            if (fatalCount > 0) {
                Allure.addAttachment("FATAL EXCEPTIONS Summary" + ", found: " + fatalCount +" FATAL_EXCEPTION", "text/plain", fatalSummary.toString());
                logger.info("Attached FATAL EXCEPTIONS summary to Allure. Total exceptions: {}", fatalCount);
            } else {
                logger.info("No FATAL EXCEPTIONS found in the ADB log.");
            }

        } catch (IOException e) {
            logger.error("Failed to read ADB log file at path '{}': {}", adbLogPath, e.getMessage(), e);
        }
    }

    public static void attachDeviceBuild() {
        try {
            // Run the ADB command
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "getprop", "ro.build.fingerprint");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capture the command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder fingerprintBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fingerprintBuilder.append(line).append("\n");
            }

            process.waitFor();

            // Get the fingerprint result
            String fingerprint = fingerprintBuilder.toString().trim();

            // Attach the fingerprint to Allure
            if (!fingerprint.isEmpty()) {
                Allure.addAttachment("#NSUD Build:", fingerprint);
            } else {
                Allure.addAttachment("#NSUD Build:", "No build data retrieved.");
            }

        } catch (Exception e) {
            // Handle errors and attach a failure message
            Allure.addAttachment("#NSUD Build:", "Failed to retrieve build: " + e.getMessage());
        }
    }

    /**
     * Runs an ADB command and attaches its result to the Allure report.
     *
     * @param attachmentName The name of the attachment in the Allure report.
     * @param adbCommand     The ADB command to execute.
     */
    public static void attachADBCommandResult(String attachmentName, String adbCommand) {
        try {
            // Split the command into its parts for ProcessBuilder
            String[] commandParts = adbCommand.split(" ");

            // Execute the ADB command
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            process.waitFor();

            // Attach the command output to Allure
            String output = outputBuilder.toString().trim();
            if (!output.isEmpty()) {
                Allure.addAttachment(attachmentName, output);
            } else {
                Allure.addAttachment(attachmentName, "No data retrieved for " + attachmentName + ".");
            }

        } catch (Exception e) {
            // Attach error details to Allure in case of failure
            Allure.addAttachment(attachmentName, "Failed to retrieve data: " + e.getMessage());
        }
    }
}
