package org.example.pages.homepage.menus;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.example.pages.BasePage;
import org.example.utils.*;
import org.example.utils.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class WatchlistMenu extends BasePage {
    private static final String ADD_WATCHLIST_PAGE = "xpath=//android.widget.TextView[@text=\"Add Watchlist\"]";
    private static final String WATCHLIST_ITEM = "xpath=//android.widget.TextView[@text=\"%s\"]";
    private static final String WATCHLIST_PAGE_LABEL = "xpath=//android.view.View[@resource-id=\"watchlist\"]//android.widget.TextView[@text=\"%s\"]";

    // Constructor
    public WatchlistMenu(AndroidDriver driver) {
        super(driver);
    }

    // Add methods to interact with Source Row Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Source Row Menu
    }

    public boolean isAddWatchlistPageDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, ADD_WATCHLIST_PAGE);
        return ElementUtils.findElement(driver, ADD_WATCHLIST_PAGE).isDisplayed();
    }

    public boolean isWatchlistPageDisplayed(String watchlistName) {
        String watchlistPageLabelLocator = String.format(WATCHLIST_PAGE_LABEL, watchlistName);
        WaitUtils.waitForElementToBeVisible(driver, watchlistPageLabelLocator);
        return ElementUtils.findElement(driver, watchlistPageLabelLocator).isDisplayed();
    }

    public void navigateToAndAddNewWatchlist(String watchlistName) throws InterruptedException {
        NavigationUtils.moveToEndOfMenu(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AllureAssert.assertTrue(isAddWatchlistPageDisplayed(), "Verify the Add Watchlist Page is displayed");
        ElementUtils.findElement(driver, "uiautomator=new UiSelector().className(\"android.widget.EditText\")").sendKeys(watchlistName);
        Thread.sleep(1000);
        KeyEventUtils.pressLeft(driver);
        KeyEventUtils.pressCenter(driver);
    }

    public void navigateToWatchlistItemByName(String watchlistName) throws InterruptedException {
        NavigationUtils.goToItemOnMenuByRight(driver, String.format(WATCHLIST_ITEM, watchlistName));
        KeyEventUtils.pressCenter(driver);
        AllureAssert.assertTrue(isWatchlistPageDisplayed(watchlistName), "Verify the Watchlist Page is displayed properly");
    }

    public boolean isWatchlistDisplayed(String watchlistName) {
        String watchlistLocator = String.format(WATCHLIST_ITEM, watchlistName);
        WaitUtils.waitForElementToBeVisible(driver, watchlistLocator);
        return ElementUtils.findElement(driver, watchlistLocator).isDisplayed();
    }
}
