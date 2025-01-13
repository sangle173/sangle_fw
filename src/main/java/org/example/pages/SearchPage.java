package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.example.utils.ElementUtils;
import org.example.utils.WaitUtils;

public class SearchPage extends BasePage {
    private static final String SEARCH_PAGE_TITLE_LOCATOR = "xpath=//android.widget.TextView[@text=\"Find your favorites\"]/..";
    private static final String SEARCH_PAGE_DESC_LOCATOR = "xpath=//android.widget.TextView[@text=\"Search for TV series, movies and more\"]/..";
    // Constructor
    public SearchPage(AndroidDriver driver) {
        super(driver);
    }

    // Add methods to interact with Source Row Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Source Row Menu
    }

    public boolean isSearchPageLabelDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, SEARCH_PAGE_TITLE_LOCATOR);
        return ElementUtils.findElement(driver, SEARCH_PAGE_TITLE_LOCATOR).isDisplayed();
    }

    public boolean isSearchPageDescDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, SEARCH_PAGE_DESC_LOCATOR);
        return ElementUtils.findElement(driver, SEARCH_PAGE_DESC_LOCATOR).isDisplayed();
    }

    public boolean isSearchPageDisplayed() {
        return isSearchPageLabelDisplayed() && isSearchPageDescDisplayed();
    }
}
