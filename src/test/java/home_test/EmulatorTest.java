package home_test;

import org.example.framework.BaseTest;
import org.example.utils.Constant;
import org.example.utils.ElementUtil;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtil;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class EmulatorTest extends BaseTest {
    @Test
    public void moveDownTest() throws Exception {
        String locator = "xpath=//android.widget.TextView[@content-desc=\"Phone\"]";
        WebElement element = ElementUtil.findElement(driver, locator);
        System.out.println(element.isDisplayed());

        String message = "uiautomator=new UiSelector().text(\"Messaging\")";
        System.out.println(ElementUtil.findElement(driver, message).isDisplayed());
        String robot = "className=android.widget.TextView";
        System.out.println(ElementUtil.findElement(driver, robot).isDisplayed());
    }

    @Test
    public void navigationTest() throws Exception {
        KeyEventUtils.pressHome(driver);
        String customize = "id=com.google.android.tvlauncher:id/configure_tab_card";
        String feedback = "com.google.android.tvlauncher:id/send_feedback_card";
        NavigationUtil.moveToItemOnMenu(driver, customize, Constant.DIRECTION_DOWN);
        WebElement customizeElement = ElementUtil.findElement(driver, customize);
        Assert.assertTrue(Boolean.parseBoolean(customizeElement.getAttribute("focused")), "customize is focused");
        NavigationUtil.moveToItemOnMenu(driver, feedback, Constant.DIRECTION_RIGHT);
        WebElement feedbackElement = ElementUtil.findElement(driver, feedback);
        Assert.assertTrue(Boolean.parseBoolean(feedbackElement.getAttribute("focused")), "feedbackElement is focused");
        System.out.println(feedbackElement.getAttribute("focused"));
    }

    @Test
    public void checkActiveElement() throws Exception {
        KeyEventUtils.pressHome(driver);
        WebElement currentActive = driver.switchTo().activeElement();
        String customize = "id=com.google.android.tvlauncher:id/send_feedback_card";
        NavigationUtil.moveToItemOnMenu(driver, customize, Constant.DIRECTION_DOWN);
        WebElement customizeEle = ElementUtil.findElement(driver,customize);
        System.out.println(currentActive.equals(customizeEle));
    }
}
