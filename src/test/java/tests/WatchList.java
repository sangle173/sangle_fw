package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.example.driver_manager.AppiumDriverManager;
import org.example.framework.BaseTest;
import org.example.utils.KeyEventUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Listeners(org.example.utils.TestListener.class)
public class WatchList extends BaseTest {

    @Test
    public void createWatchlist() throws Exception {
        KeyEventUtils keyEventUtils = new KeyEventUtils(driver);
        Allure.step("Get Current Active Element");
        keyEventUtils.pressHome();
        String firstItemOnWatchlistMenu = "//android.view.View[@resource-id='home:content_guide:watchlists:item_0']/..";

        this.moveToRowOnHomepage(firstItemOnWatchlistMenu);
        List<WebElement> watchlistNo = driver.findElements(By.xpath("//android.view.View[contains(@resource-id, 'home:content_guide:watchlists:item')]"));
        System.out.println(watchlistNo.size());
        keyEventUtils.moveRight(watchlistNo.size() + 2);
        keyEventUtils.pressCenter();
        String watchlistNameTextbox = "new UiSelector().className(\"android.widget.EditText\")";
        String newWatchlistName = keyEventUtils.getUniqueName();
        keyEventUtils.enterWatchlistName(watchlistNameTextbox, newWatchlistName);
        keyEventUtils.pressLeft();
        keyEventUtils.pressCenter();
        Thread.sleep(2000);
        WebElement watchlistLabel = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Watchlists\")"));
        Assert.assertTrue(watchlistLabel.isDisplayed(), "watchList Menu is displayed");
        String createdWatchlistLocator = String.format("new UiSelector().text(\"%s\")", newWatchlistName);
        AppiumDriverManager.waitForElementToBeVisible(createdWatchlistLocator);
        WebElement createWatchlistElement = driver.findElement(AppiumBy.androidUIAutomator(createdWatchlistLocator));
        Assert.assertTrue(createWatchlistElement.isDisplayed(), "new watchList Menu is displayed");
        this.moveLeftToItemOnMenu(String.format("//android.widget.TextView[@text=\"%s\"]/../../../..", newWatchlistName));
        KeyEventUtils.longPressOKButtonWithADB();
        List<WebElement> noOfContextItem = driver.findElements(By.xpath("//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View"));

        keyEventUtils.moveDown(3);
        keyEventUtils.pressCenter();
        WebElement confirmDeleteLabel = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Are you sure you want to delete?\")"));
        WebElement deleteButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Delete\"]/.."));
        Assert.assertTrue(confirmDeleteLabel.isDisplayed());
        Assert.assertTrue(deleteButton.isDisplayed());
        if (Objects.equals(deleteButton.getAttribute("focused"), "true")){
            keyEventUtils.pressCenter();
        } else {
            keyEventUtils.pressCenter();
            keyEventUtils.pressCenter();
        }

        //Delete create watchlist after create
    }


    public void moveToItemOnMenu(String locator) throws InterruptedException {
        boolean isFocused = false;

        while (!isFocused) {
            try {
                WebElement element = driver.findElement(By.xpath(locator));
                // Check if the element is focused
                String focused = element.getAttribute("focused");
                isFocused = "true".equals(focused);

                if (isFocused) {
                    // If focused, enter the text
                    new KeyEventUtils(driver).pressCenter();
                } else {
                    // If not focused, press the "Right" key
                    System.out.println("Element not focused. Pressing Right key.");
                    new KeyEventUtils(driver).pressRight();
                }
            } catch (Exception e) {
                System.out.println("Element not found. Retrying...");
                new KeyEventUtils(driver).pressRight();
            }
        }
    }

    public void moveLeftToItemOnMenu(String locator) throws InterruptedException {
        boolean isFocused = false;

        while (true) {
            try {
                WebElement element = driver.findElement(By.xpath(locator));
                // Check if the element is focused
                String focused = element.getAttribute("focused");
                isFocused = "true".equals(focused);

                if (isFocused) {
                    // If focused, enter the text
                    break;
                } else {
                    // If not focused, press the "Right" key
                    System.out.println("Element not focused. Pressing Right key.");
                    new KeyEventUtils(driver).pressLeft();
                }
            } catch (Exception e) {
                System.out.println("Element not found. Retrying...");
                new KeyEventUtils(driver).pressLeft();
            }
        }
    }

    public void moveToRowOnHomepage(String locator) throws InterruptedException {
        boolean isFocused = false;

        while (!isFocused) {
            try {
                // Find the element
                WebElement element = driver.findElement(By.xpath(locator));
                // Check if the element is focused
                String focused = element.getAttribute("focused");
                isFocused = "true".equals(focused);

                if (isFocused) {
                    // If focused, stop move
                    break;
                } else {
                    // If not focused, press the "Down" key
                    System.out.println("Element not focused. Pressing Down key.");
                    new KeyEventUtils(driver).pressDown();
                }
            } catch (Exception e) {
                System.out.println("Element not found. Retrying...");
                new KeyEventUtils(driver).pressDown();
            }
        }
    }




}
