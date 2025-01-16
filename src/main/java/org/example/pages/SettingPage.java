package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SettingPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(NavigationUtils.class);
    private static final String SETTING_ITEMS_SECTION = "xpath=//android.widget.TextView[@text=\"Settings\"]//following-sibling::android.view.View/android.view.View";
    private static final String SETTING_LABEL = "xpath=//android.widget.TextView[@text=\"Settings\"]";

    // Constructor
    public SettingPage(AndroidDriver driver) {
        super(driver);
    }

    // Add methods to interact with Source Row Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Source Row Menu
    }

    public boolean isSettingItemsDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, SETTING_ITEMS_SECTION);
        return ElementUtils.findElement(driver, SETTING_ITEMS_SECTION).isDisplayed();
    }

    public boolean isSettingLabelDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, SETTING_LABEL);
        return ElementUtils.findElement(driver, SETTING_LABEL).isDisplayed();
    }

    public boolean isSettingPageDisplayed() {
        return isSettingItemsDisplayed() && isSettingLabelDisplayed();
    }

    public boolean isItemsDisplayedProperly() {
        // Get the expected settings list from the JSON file
        List<String> expectedSettingItems = DataUtils.getAllSettings();

        if (expectedSettingItems == null || expectedSettingItems.isEmpty()) {
            logger.error("Expected setting items list is empty or not loaded properly.");
            return false;
        }

        // Get all setting items
        List<WebElement> settingItems = ElementUtils.findElements(driver,SETTING_ITEMS_SECTION);
        if (settingItems.isEmpty()) {
            logger.error("No setting items found in the settings section.");
            return false;
        }

        // Extract the text from each setting item
        List<String> actualSettingItems = new ArrayList<>();
        for (WebElement element : settingItems) {
            try {
                String text = element.findElements(By.xpath(".//android.widget.TextView")).get(0).getText();
                logger.info(text + " setting item is displayed");
                if (text != null && !text.isEmpty()) {
                    actualSettingItems.add(text);
                } else {
                    logger.warn("A setting item has no text or is empty.");
                }
            } catch (Exception e) {
                logger.error("Error extracting text from a setting item: {}");
            }
        }

        // Compare the actual list with the expected list
        if (actualSettingItems.equals(expectedSettingItems)) {
            logger.info("All setting items are displayed properly.");
            return true;
        } else {
            logger.error("Mismatch between expected and actual setting items.");
            logger.error("Expected: {}", expectedSettingItems);
            logger.error("Actual: {}", actualSettingItems);
            return false;
        }
    }

}
