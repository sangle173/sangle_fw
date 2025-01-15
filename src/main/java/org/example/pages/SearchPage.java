package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.example.utils.ElementUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage extends BasePage {
    private static final String SEARCH_PAGE_TITLE_LOCATOR = "xpath=//android.widget.TextView[@text=\"Find your favorites\"]/..";
    private static final String SEARCH_PAGE_DESC_LOCATOR = "xpath=//android.widget.TextView[@text=\"Search for TV series, movies and more\"]/..";
    private static final String SEARCH_TEXTBOX = "xpath=//android.view.View[@resource-id=\"search_screen:search_input\"]/android.widget.TextView";
    private static final String MOVIE_RESULT_SECTION_LABEL = "xpath=//android.view.View[@resource-id=\"search_screen:search_results\"]//android.widget.TextView[@text=\"Movies\"]";
    private static final String MOVIE_SEARCH_RESULT_SECTION = "xpath=//android.view.View[@resource-id=\"search_screen:search_results:movies\"]";
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

    public void inputSearchKeyword(String keyword) {
        WaitUtils.waitForElementToBeVisible(driver,SEARCH_TEXTBOX);
        KeyEventUtils.inputString(driver,keyword);
    }
    public boolean isMoviesSearchResultDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, MOVIE_RESULT_SECTION_LABEL);
        return ElementUtils.findElement(driver, MOVIE_RESULT_SECTION_LABEL).isDisplayed();
    }

    public boolean isMoviesSearchResultDisplayedProperly() {
        WaitUtils.waitForElementToBeVisible(driver,MOVIE_SEARCH_RESULT_SECTION);
        WebElement searchSectionEle = ElementUtils.findElement(driver,MOVIE_SEARCH_RESULT_SECTION);
        System.out.println(searchSectionEle.isDisplayed());
        List<WebElement> movieList= searchSectionEle.findElements(By.xpath("./android.view.View"));
        for (WebElement element : movieList) {
           List<WebElement> movieNameAndYearList = element.findElements(By.xpath("./android.widget.TextView"));
        }
        return true;
    }

}
