package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.example.utils.ElementUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.WaitUtils;
import org.example.utils.enums.ContextMenuOptions;
import org.example.utils.enums.Direction;
import org.openqa.selenium.By;

public class ContextMenu {

    private AndroidDriver driver;

    // Locators for the context menu options
    private static final String CONTEXT_MENU_LOCATOR = "";
    private static final String PLAY_MOVIE_LOCATOR = "//xpath_to_play_movie";
    private static final String DELETE_LOCATOR = "uiautomator=new UiSelector().text(\"Delete\")";
    private static final String OPEN_CONTENT_DETAILS_LOCATOR = "//xpath_to_open_content_details";
    private static final String APP_DETAILS_LOCATOR = "xpath=//android.widget.TextView[@text=\"App Details\"]/..";

    // Constructor
    public ContextMenu(AndroidDriver driver) {
        this.driver = driver;
    }

    // Method to check if the context menu is displayed
    public boolean isDisplayed() {
        try {
            return driver.findElement(By.xpath(CONTEXT_MENU_LOCATOR)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAppDetailsOptionDisplayed() {
        try {
            return ElementUtils.findElement(driver, APP_DETAILS_LOCATOR).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void moveToAppDetailsOptionAndOpen() throws InterruptedException {
        WaitUtils.waitForElementToBeVisible(driver, APP_DETAILS_LOCATOR);
        KeyEventUtils.pressDown(driver,2);
        KeyEventUtils.pressCenter(driver);
    }

    // Method to move to and select an option from the context menu
    public void moveToAndSelectTheOption(ContextMenuOptions option) throws InterruptedException {
        String optionLocator = getOptionLocator(option);
        WaitUtils.waitForElementToBeVisible(driver,optionLocator);
        System.out.println(optionLocator);
        if (optionLocator != null) {
            NavigationUtils.goToItemOnMenuByDown(driver, optionLocator);
            KeyEventUtils.pressCenter(driver);
        } else {
            throw new IllegalArgumentException("Invalid context menu option: " + option.getOptionName());
        }
    }

    // Get the locator for the context menu option
    private String getOptionLocator(ContextMenuOptions option) {
        switch (option) {
            case PLAY_MOVIE:
                return PLAY_MOVIE_LOCATOR;
            case DELETE:
                return DELETE_LOCATOR;
            case OPEN_CONTENT_DETAILS:
                return OPEN_CONTENT_DETAILS_LOCATOR;
            case APP_DETAILS:
                return APP_DETAILS_LOCATOR;
            default:
                return null;
        }
    }


}

