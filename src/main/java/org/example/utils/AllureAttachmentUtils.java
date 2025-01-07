package org.example.utils;

import io.qameta.allure.Allure;

import java.io.*;

public class AllureAttachmentUtils {
    public static void attachLog(String logPath) {
        try (FileInputStream fis = new FileInputStream(new File(logPath))) {
            Allure.addAttachment("ADB Log Info", "txt", fis, ".txt");
        } catch (IOException e) {
            System.err.println("Failed to attach log to Allure report: " + e.getMessage());
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
