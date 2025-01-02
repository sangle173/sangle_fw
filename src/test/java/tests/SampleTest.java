package tests;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Allure;
import org.example.framework.BaseTest;
import org.example.utils.KeyEventUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class SampleTest extends BaseTest {
    @Test
    public void testFocused() throws Exception {
        KeyEventUtils keyEventUtils = new KeyEventUtils(driver);
        keyEventUtils.pressHome();
        keyEventUtils.pressDown();
        keyEventUtils.pressRight();
        keyEventUtils.pressRight();
        WebElement currentElement = driver.switchTo().activeElement();
        Allure.step("Get Current Active Element"); // Custom allure step message
//        WebElement childElement = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().childSelector(\"new UiSelector().description(\"TÁI SINH - Tùng Dương (ST: Tăng Duy Tân) | Liveshow Người Đàn Ông Hát | Chìm vào trong ánh mắt...\").instance(1)\"))"));
        System.out.println(currentElement.getText());
    }

    @Test
    public void testDemo() {
        KeyEventUtils keyEventUtils = new KeyEventUtils(driver);
        keyEventUtils.pressHome();
        keyEventUtils.pressDown();
        keyEventUtils.pressDown();
        keyEventUtils.pressDown();
        keyEventUtils.pressRight();
        keyEventUtils.pressRight();
        keyEventUtils.pressRight();
        keyEventUtils.pressRight();
        WebElement currentElement = driver.switchTo().activeElement();
        Allure.step("Get Current Active Element"); // Custom allure step message
        WebElement childElement = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().childSelector(\"new UiSelector().description(\"TÁI SINH - Tùng Dương (ST: Tăng Duy Tân) | Liveshow Người Đàn Ông Hát | Chìm vào trong ánh mắt...\").instance(1)\"))"));
        System.out.println(currentElement.getText());
    }
}
