package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.example.config.ConfigReader;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KeyEventUtils {

    // Prevent instantiation
    private KeyEventUtils() {}

    // Press a single key once
    public static void pressKey(AndroidDriver driver, AndroidKey key, String description) {
        Allure.step(description, () -> {
            driver.pressKey(new KeyEvent(key));
            Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
        });
    }

    // Press a single key multiple times
    public static void pressKey(AndroidDriver driver, AndroidKey key, String description, int times) {
        Allure.step(String.format("Pressing %s key %d times", key.name(), times), () -> {
            for (int i = 0; i < times; i++) {
                driver.pressKey(new KeyEvent(key));
                Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
            }
        });
    }

    // Specific key press methods
    public static void pressHome(AndroidDriver driver) {
        pressKey(driver, AndroidKey.HOME, "Pressing Home Key");
    }

    public static void pressHome(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.HOME, "Pressing Home Key", times);
    }

    // Power key
    public static void pressPower(AndroidDriver driver) {
        pressKey(driver, AndroidKey.POWER, "Pressing Power Key");
    }

    public static void pressPower(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.POWER, "Pressing Power Key", times);
    }

    // Volume control keys
    public static void pressVolumeUp(AndroidDriver driver) {
        pressKey(driver, AndroidKey.VOLUME_UP, "Pressing Volume Up Key");
    }

    public static void pressVolumeUp(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.VOLUME_UP, "Pressing Volume Up Key", times);
    }

    public static void pressVolumeDown(AndroidDriver driver) {
        pressKey(driver, AndroidKey.VOLUME_DOWN, "Pressing Volume Down Key");
    }

    public static void pressVolumeDown(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.VOLUME_DOWN, "Pressing Volume Down Key", times);
    }

    public static void pressMute(AndroidDriver driver) {
        pressKey(driver, AndroidKey.VOLUME_MUTE, "Pressing Mute Key");
    }

    // Directional keys
    public static void pressRight(AndroidDriver driver) {
        pressKey(driver, AndroidKey.DPAD_RIGHT, "Pressing Right Key");
    }

    public static void pressRight(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.DPAD_RIGHT, "Pressing Right Key", times);
    }

    public static void pressLeft(AndroidDriver driver) {
        pressKey(driver, AndroidKey.DPAD_LEFT, "Pressing Left Key");
    }

    public static void pressLeft(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.DPAD_LEFT, "Pressing Left Key", times);
    }

    public static void pressUp(AndroidDriver driver) {
        pressKey(driver, AndroidKey.DPAD_UP, "Pressing Up Key");
    }

    public static void pressUp(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.DPAD_UP, "Pressing Up Key", times);
    }

    public static void pressDown(AndroidDriver driver) {
        pressKey(driver, AndroidKey.DPAD_DOWN, "Pressing Down Key");
    }

    public static void pressDown(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.DPAD_DOWN, "Pressing Down Key", times);
    }

    // Select key
    public static void pressCenter(AndroidDriver driver) {
        pressKey(driver, AndroidKey.DPAD_CENTER, "Pressing Center Key (OK)");
    }

    public static void pressCenter(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.DPAD_CENTER, "Pressing Center Key (OK)", times);
    }

    // Channel control keys
    public static void pressChannelUp(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_NEXT, "Pressing Channel Up Key");
    }

    public static void pressChannelUp(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.MEDIA_NEXT, "Pressing Channel Up Key", times);
    }

    public static void pressChannelDown(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_PREVIOUS, "Pressing Channel Down Key");
    }

    public static void pressChannelDown(AndroidDriver driver, int times) {
        pressKey(driver, AndroidKey.MEDIA_PREVIOUS, "Pressing Channel Down Key", times);
    }

    // Media control keys
    public static void pressPlay(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_PLAY, "Pressing Play Key");
    }

    public static void pressPause(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_PAUSE, "Pressing Pause Key");
    }

    public static void pressStop(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_STOP, "Pressing Stop Key");
    }

    public static void pressRewind(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_REWIND, "Pressing Rewind Key");
    }

    public static void pressFastForward(AndroidDriver driver) {
        pressKey(driver, AndroidKey.MEDIA_FAST_FORWARD, "Pressing Fast Forward Key");
    }


    // Method to input text by pressing the Android keys
    public void inputText(AndroidDriver driver,String text) {
        for (char c : text.toCharArray()) {
            AndroidKey key = getAndroidKeyForCharacter(c);
            if (key != null) {
                driver.pressKey(new KeyEvent(key));  // Press the corresponding key
            }
        }
    }

    // Helper method to map characters to AndroidKey codes
    private AndroidKey getAndroidKeyForCharacter(char c) {
        switch (c) {
            case 'a': return AndroidKey.A;
            case 'b': return AndroidKey.B;
            case 'c': return AndroidKey.C;
            case 'd': return AndroidKey.D;
            case 'e': return AndroidKey.E;
            case 'f': return AndroidKey.F;
            case 'g': return AndroidKey.G;
            case 'h': return AndroidKey.H;
            case 'i': return AndroidKey.I;
            case 'j': return AndroidKey.J;
            case 'k': return AndroidKey.K;
            case 'l': return AndroidKey.L;
            case 'm': return AndroidKey.M;
            case 'n': return AndroidKey.N;
            case 'o': return AndroidKey.O;
            case 'p': return AndroidKey.P;
            case 'q': return AndroidKey.Q;
            case 'r': return AndroidKey.R;
            case 's': return AndroidKey.S;
            case 't': return AndroidKey.T;
            case 'u': return AndroidKey.U;
            case 'v': return AndroidKey.V;
            case 'w': return AndroidKey.W;
            case 'x': return AndroidKey.X;
            case 'y': return AndroidKey.Y;
            case 'z': return AndroidKey.Z;
            case '1': return AndroidKey.NUMPAD_1;  // KeyEvent.KEYCODE_1 for number 1
            case '2': return AndroidKey.NUMPAD_2; // KeyEvent.KEYCODE_2 for number 2
            case '3': return AndroidKey.NUMPAD_3;  // KeyEvent.KEYCODE_3 for number 3
            case '4': return AndroidKey.NUMPAD_4;  // KeyEvent.KEYCODE_4 for number 4
            case '5': return AndroidKey.NUMPAD_5;  // KeyEvent.KEYCODE_5 for number 5
            case '6': return AndroidKey.NUMPAD_6;  // KeyEvent.KEYCODE_6 for number 6
            case '7': return AndroidKey.NUMPAD_7;  // KeyEvent.KEYCODE_7 for number 7
            case '8': return AndroidKey.NUMPAD_8;  // KeyEvent.KEYCODE_8 for number 8
            case '9': return AndroidKey.NUMPAD_9;  // KeyEvent.KEYCODE_9 for number 9
            case '0': return AndroidKey.NUMPAD_0;  // KeyEvent.KEYCODE_0 for number 0
            case ' ': return AndroidKey.SPACE;  // Space key
            case '.': return AndroidKey.PERIOD; // Period key
            case '@': return AndroidKey.AT;     // At key (special character)
            default: return null; // Unsupported characters
        }
    }

    // Method to input "Auto" + current date
    public static String getUniqueName() {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date to a string, e.g., "2025-01-03 14:23"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = now.format(formatter);

        // Combine "Auto" with the formatted date
        return "Watchlist" + formattedDate;
    }

    // Method to input text into the TextBox
    public static void enterWatchlistName(AndroidDriver driver, String locator, String textToInput) {
        WebElement textBox = driver.findElement(AppiumBy.androidUIAutomator(locator));
        // Input the text into the TextBox
        textBox.sendKeys(textToInput);
    }

    // Simulate long press using adb
    public static void longPressOKButtonWithADB() {
        try {
            System.out.println("Performing long press on OK button using ADB...");
            Process process = new ProcessBuilder("adb", "shell", "input", "keyevent", "--longpress", "23").start();
            process.waitFor();
            System.out.println("Long press on OK button completed.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error performing long press on OK button via ADB: " + e.getMessage());
        }
    }
}
