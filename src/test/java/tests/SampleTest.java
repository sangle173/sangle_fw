package tests;

import io.appium.java_client.AppiumBy;
import org.example.framework.BaseTest;
import org.example.utils.KeyEventUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {
    @Test
    public void testFocused() {
        KeyEventUtils keyEventUtils = new KeyEventUtils(driver);
        keyEventUtils.pressHome();
        keyEventUtils.pressDown();
        keyEventUtils.pressDown();
        WebElement currentElement = driver.switchTo().activeElement();
        System.out.println(currentElement.getText());
    }
}
