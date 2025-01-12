package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;

public class BasePage {

    private static final Logger logger = LogManager.getLogger(BasePage.class);
    protected AndroidDriver driver;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Opens the context menu using a long press action.
     *
     * @return An instance of ContextMenu.
     */
    public ContextMenu openContextMenu() {
        try {
            logger.info("Attempting to open the context menu...");
            KeyEventUtils.longPressOKButtonWithADB();
            logger.info("Context menu opened successfully.");
            return new ContextMenu(driver); // Return a generic ContextMenu instance
        } catch (Exception e) {
            logger.error("Failed to open context menu: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to open context menu: " + e.getMessage());
        }
    }

    /**
     * Moves to the specified item and opens the context menu using a long press action.
     *
     * @param locator   The locator of the item to move to.
     * @param direction The direction to navigate to the item.
     * @return An instance of ContextMenu.
     */
    public ContextMenu moveToItemAndOpenContextMenu(String locator, Direction direction) {
        try {
            logger.info("Attempting to move to item with locator '{}' in direction '{}'...", locator, direction);
            NavigationUtils.goToItemOnMenuByDown(driver, locator);
            logger.info("Successfully navigated to the item. Attempting to open the context menu...");
            KeyEventUtils.longPressOKButtonWithADB();
            logger.info("Context menu opened successfully.");
            return new ContextMenu(driver); // Return a generic ContextMenu instance
        } catch (Exception e) {
            logger.error("Failed to open context menu after moving to item: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to open context menu: " + e.getMessage());
        }
    }
}
