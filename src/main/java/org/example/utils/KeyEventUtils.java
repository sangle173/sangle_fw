package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.ConfigReader;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KeyEventUtils {

    private static final Logger logger = LogManager.getLogger(KeyEventUtils.class);

    // Prevent instantiation
    private KeyEventUtils() {
    }

    // Press a single key once
    public static void pressKey(AndroidDriver driver, AndroidKey key, String description) throws InterruptedException {
        logger.info("Pressing key: {}, Description: {}", key.name(), description);
        driver.pressKey(new KeyEvent(key));
        Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
    }

    // Press a single key multiple times
    public static void pressKey(AndroidDriver driver, AndroidKey key, String description, int times) throws InterruptedException {
        logger.info("Pressing key: {}, Description: {}, Times: {}", key.name(), description, times);
        for (int i = 0; i < times; i++) {
            driver.pressKey(new KeyEvent(key));
            Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
        }
    }

    // Specific key press methods
    public static void pressHome(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.HOME, "Pressing Home Key");
    }

    public static void pressHome(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.HOME, "Pressing Home Key", times);
    }

    public static void pressPower(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.POWER, "Pressing Power Key");
    }

    public static void pressPower(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.POWER, "Pressing Power Key", times);
    }

    public static void pressVolumeUp(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.VOLUME_UP, "Pressing Volume Up Key");
    }

    public static void pressVolumeUp(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.VOLUME_UP, "Pressing Volume Up Key", times);
    }

    public static void pressVolumeDown(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.VOLUME_DOWN, "Pressing Volume Down Key");
    }

    public static void pressVolumeDown(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.VOLUME_DOWN, "Pressing Volume Down Key", times);
    }

    public static void pressMute(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.VOLUME_MUTE, "Pressing Mute Key");
    }

    public static void pressRight(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_RIGHT, "Pressing Right Key");
    }

    public static void pressRight(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_RIGHT, "Pressing Right Key", times);
    }

    public static void pressLeft(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_LEFT, "Pressing Left Key");
    }

    public static void pressLeft(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_LEFT, "Pressing Left Key", times);
    }

    public static void pressUp(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_UP, "Pressing Up Key");
    }

    public static void pressUp(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_UP, "Pressing Up Key", times);
    }

    public static void pressDown(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_DOWN, "Pressing Down Key");
    }

    public static void pressDown(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_DOWN, "Pressing Down Key", times);
    }

    public static void pressCenter(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_CENTER, "Pressing Center Key (OK)");
    }

    public static void pressCenter(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.DPAD_CENTER, "Pressing Center Key (OK)", times);
    }

    public static void pressBack(AndroidDriver driver) {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public static void pressBack(AndroidDriver driver, int times) throws InterruptedException {
        pressKey(driver, AndroidKey.BACK, "Pressing Back Key", times);
    }

    public static void pressPlay(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.MEDIA_PLAY, "Pressing Play Key");
    }

    public static void pressPause(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.MEDIA_PAUSE, "Pressing Pause Key");
    }

    public static void pressStop(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.MEDIA_STOP, "Pressing Stop Key");
    }

    public static void pressRewind(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.MEDIA_REWIND, "Pressing Rewind Key");
    }

    public static void pressFastForward(AndroidDriver driver) throws InterruptedException {
        pressKey(driver, AndroidKey.MEDIA_FAST_FORWARD, "Pressing Fast Forward Key");
    }

    public void inputText(AndroidDriver driver, String text) {
        logger.info("Inputting text: {}", text);
        for (char c : text.toCharArray()) {
            AndroidKey key = getAndroidKeyForCharacter(c);
            if (key != null) {
                logger.info("Pressing key for character: {}", c);
                driver.pressKey(new KeyEvent(key));
            }
        }
    }

    public static void enterWatchlistName(AndroidDriver driver, String locator, String textToInput) {
        logger.info("Entering text into textbox with locator: {}, Text: {}", locator, textToInput);
        WebElement textBox = driver.findElement(AppiumBy.androidUIAutomator(locator));
        textBox.sendKeys(textToInput);
    }

    public static void longPressOKButtonWithADB() {
        logger.info("Performing long press on OK button using ADB");
        try {
            Process process = new ProcessBuilder("adb", "shell", "input", "keyevent", "--longpress", "23").start();
            process.waitFor();
            logger.info("Long press on OK button completed");
        } catch (IOException | InterruptedException e) {
            logger.error("Error performing long press on OK button via ADB: {}", e.getMessage(), e);
        }
    }

    public static String getUniqueName() {
        logger.info("Generating unique name with current date");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String uniqueName = "Watchlist" + now.format(formatter);
        logger.info("Generated unique name: {}", uniqueName);
        return uniqueName;
    }

    private AndroidKey getAndroidKeyForCharacter(char c) {
        // Character mapping logic
        return null; // Implement mapping logic here
    }
}
