package home_test;

import org.example.framework.BaseTest;
import org.example.pages.homepage.Homepage;
import org.example.pages.homepage.menus.UpNextMenu;
import org.example.utils.ElementUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class EmulatorTest extends BaseTest {
    @Test
    public void moveDownTest() throws Exception {
        String locator = "xpath=//android.widget.TextView[@content-desc=\"Phone\"]";
        WebElement element = ElementUtils.findElement(driver, locator);
        System.out.println(element.isDisplayed());

        String message = "uiautomator=new UiSelector().text(\"Messaging\")";
        System.out.println(ElementUtils.findElement(driver, message).isDisplayed());
        String robot = "className=android.widget.TextView";
        System.out.println(ElementUtils.findElement(driver, robot).isDisplayed());
    }

    @Test
    public void navigationTest() throws Exception {
        KeyEventUtils.pressHome(driver);
        String customize = "id=com.google.android.tvlauncher:id/configure_tab_card";
        String feedback = "com.google.android.tvlauncher:id/send_feedback_card";
        NavigationUtils.moveToElement(driver, customize, Direction.DOWN);
        WebElement customizeElement = ElementUtils.findElement(driver, customize);
        Assert.assertTrue(Boolean.parseBoolean(customizeElement.getAttribute("focused")), "customize is focused");
        NavigationUtils.moveToElement(driver, feedback, Direction.RIGHT);
        WebElement feedbackElement = ElementUtils.findElement(driver, feedback);
        Assert.assertTrue(Boolean.parseBoolean(feedbackElement.getAttribute("focused")), "feedbackElement is focused");
        System.out.println(feedbackElement.getAttribute("focused"));
    }

    @Test
    public void checkActiveElement() throws Exception {
        KeyEventUtils.pressHome(driver);
        WebElement currentActive = driver.switchTo().activeElement();
        String customize = "id=com.google.android.tvlauncher:id/send_feedback_card";
        NavigationUtils.moveToElement(driver, customize, Direction.DOWN);
        WebElement customizeEle = ElementUtils.findElement(driver,customize);
        System.out.println(currentActive.equals(customizeEle));
    }

    @Test
    public void moveToEndTest() throws Exception {
        KeyEventUtils.pressHome(driver);
        NavigationUtils.moveToTheEnd(driver, Direction.DOWN);
        WebElement currentActive = driver.switchTo().activeElement();
        System.out.println(currentActive.getText());
        KeyEventUtils.longPressOKButtonWithADB();
    }

    @Test
    public void moveRightToEndTest() throws Exception {
        KeyEventUtils.pressHome(driver);
        KeyEventUtils.pressDown(driver,4);
        NavigationUtils.moveToTheEnd(driver, Direction.RIGHT);
        WebElement currentActive = driver.switchTo().activeElement();
        System.out.println(currentActive.getText());
        KeyEventUtils.longPressOKButtonWithADB();
    }

    @Test
    public void homepageTest() throws InterruptedException {
        Homepage homepage = new Homepage(driver);

        // Navigate to the Up Next Menu
        UpNextMenu upNextMenu = homepage.goToUpNextMenu();
        upNextMenu.selectItem("[@text='Featured Show']");

        // Navigate to the Source Row Menu
        homepage.goToSourceRowMenu().selectItem("[@text='Source Show']");
    }
}
