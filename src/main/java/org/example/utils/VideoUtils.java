package org.example.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class VideoUtils {

    private static final Logger logger = LogManager.getLogger(VideoUtils.class);

    // Method to generate a unique filename based on the current date and time
    public static String getVideoFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date()); // Current date and time
        FileUtils.ensureDirectoryExists("test-recordings");
        String videoFileName = "test-recordings/testRecording_" + timestamp + ".mp4"; // Full filename
        logger.info("Generated video file name: {}", videoFileName);
        return videoFileName;
    }

    // Method to save the video as an MP4 file
    public static void saveVideo(String videoBase64, String videoFilePath) {
        try {
            byte[] decodedVideo = Base64.getDecoder().decode(videoBase64);
            try (FileOutputStream fos = new FileOutputStream(videoFilePath)) {
                fos.write(decodedVideo); // Write the decoded bytes to file
                logger.info("Video saved successfully at: {}", videoFilePath);
            }
        } catch (Exception e) {
            logger.error("Error while saving video: {}", e.getMessage(), e);
        }
    }

    // Method to attach the video to the Allure report
    public static void attachVideo(String videoPath) {
        try (FileInputStream fis = new FileInputStream(new File(videoPath))) {
            Allure.addAttachment("Test Recording", "video/mp4", fis, ".mp4");
            logger.info("Video attached to Allure report successfully from path: {}", videoPath);
        } catch (IOException e) {
            logger.error("Failed to attach video to Allure report: {}", e.getMessage(), e);
        }
    }
}
