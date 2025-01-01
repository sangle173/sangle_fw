package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;

public class KeyEventUtils {
    private AndroidDriver driver;

    public KeyEventUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    @Step("HOME button clicked.")
    public void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    @Step("DPAD_DOWN button clicked.")
    public void pressDown() {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
        System.out.println("DPAD_DOWN button clicked.");
    }

    @Step("DPAD_RIGHT button clicked.")
    public void pressRight() {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
        System.out.println("DPAD_RIGHT button clicked.");
    }

    public void pressLeft() {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
        System.out.println("DPAD_LEFT button clicked.");
    }

    public void pressUp() {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_UP));
        System.out.println("DPAD_UP button clicked.");
    }

    public void pressCenter() {
        driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
        System.out.println("DPAD_CENTER button clicked.");
    }
}
