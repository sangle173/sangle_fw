package org.example.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class VideoUtils {
    // Method to generate a unique filename based on the current date and time
    public static String getVideoFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date()); // Current date and time
        FileUtils.ensureDirectoryExists("test-recordings");
        return "test-recordings/testRecording_" + timestamp + ".mp4"; // Full filename
    }

    // Method to save the video as an MP4 file
    public static void saveVideo(String videoBase64, String videoFilePath) {
        try {
            byte[] decodedVideo = Base64.getDecoder().decode(videoBase64);
            try (FileOutputStream fos = new FileOutputStream(videoFilePath)) {
                fos.write(decodedVideo); // Write the decoded bytes to file
            }
        } catch (Exception e) {
            System.err.println("Error while saving video: " + e.getMessage());
        }
    }

    // Method to attach the video to the Allure report
    public static void attachVideo(String videoPath) {
        try (FileInputStream fis = new FileInputStream(new File(videoPath))) {
            Allure.addAttachment("Test Recording", "video/mp4", fis, ".mp4");
        } catch (IOException e) {
            System.err.println("Failed to attach video to Allure report: " + e.getMessage());
        }
    }

}
