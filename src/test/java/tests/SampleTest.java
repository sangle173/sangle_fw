package tests;

import io.qameta.allure.Allure;
import org.example.framework.BaseTest;
import utils.KeyEventUtils;
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
        Allure.step("Get Current Active Element"); // Custom allure step message
        System.out.println(currentElement.getText());
    }
}
