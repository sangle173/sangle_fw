package tests;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Allure;
import org.example.framework.BaseTest;
import org.example.utils.AllureAssert;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.WaitUtils;
import org.example.utils.enums.Direction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

@Listeners(org.example.utils.TestListener.class)
public class WatchlistTest extends BaseTest {

    @Test
    public void createWatchlist() throws Exception {
        KeyEventUtils.pressHome(driver);
        String firstItemOnWatchlistMenu = "xpath=//android.view.View[@resource-id='home:content_guide:watchlists:item_0']/..";

        NavigationUtils.moveToElement(driver,firstItemOnWatchlistMenu, Direction.DOWN);
        Allure.step("Move To Watchlist Menu");

        List<WebElement> watchlistNo = driver.findElements(By.xpath("//android.view.View[contains(@resource-id, 'home:content_guide:watchlists:item')]"));
        System.out.println(watchlistNo.size());
        Allure.step("Move to Add Watchlist Button and open the add new watchlist page");
        KeyEventUtils.pressRight(driver,watchlistNo.size() + 2);
        KeyEventUtils.pressCenter(driver);
        String watchlistNameTextbox = "new UiSelector().className(\"android.widget.EditText\")";
        String newWatchlistName = KeyEventUtils.getUniqueName();
        Allure.step("Enter Watchlist Name and press Done");

        KeyEventUtils.enterWatchlistName(driver,watchlistNameTextbox, newWatchlistName);
        KeyEventUtils.pressLeft(driver);
        KeyEventUtils.pressCenter(driver);
        Thread.sleep(2000);
        WebElement watchlistLabel = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Watchlists\")"));
        AllureAssert.assertTrue(watchlistLabel.isDisplayed(), "watchList Menu is displayed");
        String createdWatchlistLocator = String.format("new UiSelector().text(\"%s\")", newWatchlistName);
        WaitUtils.waitForElementToBeVisible(driver,createdWatchlistLocator);
        WebElement createWatchlistElement = driver.findElement(AppiumBy.androidUIAutomator(createdWatchlistLocator));
        AllureAssert.assertTrue(createWatchlistElement.isDisplayed(), "Check the new watchList Menu is displayed");
        NavigationUtils.moveToElement(driver,String.format("//android.widget.TextView[@text=\"%s\"]/../../../..", newWatchlistName),Direction.LEFT);
        KeyEventUtils.longPressOKButtonWithADB();
        List<WebElement> noOfContextItem = driver.findElements(By.xpath("//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View"));
        KeyEventUtils.pressDown(driver,3);
        KeyEventUtils.pressCenter(driver);
        WebElement confirmDeleteLabel = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Are you sure you want to delete?\")"));
        WebElement deleteButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Delete\"]/.."));
        Assert.assertTrue(confirmDeleteLabel.isDisplayed());
        Assert.assertTrue(deleteButton.isDisplayed());
        Allure.step("Delete created watchlist");

        if (Objects.equals(deleteButton.getAttribute("focused"), "true")){
            KeyEventUtils.pressCenter(driver);
        } else {
            KeyEventUtils.pressCenter(driver);
            KeyEventUtils.pressCenter(driver);
        }

        //Delete create watchlist after create
    }
}
