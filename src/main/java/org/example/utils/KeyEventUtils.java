package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KeyEventUtils {
    private AndroidDriver driver;

    public KeyEventUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    public void pressHome() throws InterruptedException {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        Thread.sleep(1000);
        Allure.step("HOME button clicked."); // Custom allure step message
        System.out.println("HOME button clicked.");
    }

    public void pressDown() throws InterruptedException {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
        Thread.sleep(500);
        Allure.step("DPAD_DOWN button clicked."); // Custom allure step message
        System.out.println("DPAD_DOWN button clicked.");
    }

    @Step("DPAD_RIGHT button clicked.")
    public void pressRight() throws InterruptedException {
        Thread.sleep(500);
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
        System.out.println("DPAD_RIGHT button clicked.");
    }

    public void pressLeft() throws InterruptedException {
        Thread.sleep(500);
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
        System.out.println("DPAD_LEFT button clicked.");
    }

    public void pressUp() {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_UP));
        System.out.println("DPAD_UP button clicked.");
    }

    public void pressCenter() throws InterruptedException {
        Thread.sleep(500);
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
        System.out.println("DPAD_CENTER button clicked.");
    }

    public void moveRight(int times) {
        for (int i = 0; i < times; i++) {
            driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT)); // Send DPAD_RIGHT key
            try {
                Thread.sleep(200); // Optional: Add a small delay between moves for smooth navigation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void moveDown(int times) {
        for (int i = 0; i < times; i++) {
            driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN)); // Send DPAD_RIGHT key
            try {
                Thread.sleep(200); // Optional: Add a small delay between moves for smooth navigation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    // Method to input text by pressing the Android keys
    public void inputText(String text) {
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
    public String getUniqueName() {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date to a string, e.g., "2025-01-03 14:23"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = now.format(formatter);

        // Combine "Auto" with the formatted date
        return "Watchlist" + formattedDate;
    }

    // Method to input text into the TextBox
    public void enterWatchlistName(String locator, String textToInput) {
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
