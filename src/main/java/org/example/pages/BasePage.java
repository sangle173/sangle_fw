package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;

public class BasePage {
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
            KeyEventUtils.longPressOKButtonWithADB();
            return new ContextMenu(driver); // Return a generic ContextMenu instance
        } catch (Exception e) {
            throw new RuntimeException("Failed to open context menu: " + e.getMessage());
        }
    }

    /**
     * Moves to the specified item and opens the context menu using a long press action.
     *
     * @param locator The locator of the item to move to.
     * @param direction The direction to navigate to the item.
     * @return An instance of ContextMenu.
     */
    public ContextMenu moveToItemAndOpenContextMenu(String locator, Direction direction) {
        try {
            NavigationUtils.moveToElement(driver, locator, direction);
            KeyEventUtils.longPressOKButtonWithADB();
            return new ContextMenu(driver); // Return a generic ContextMenu instance
        } catch (Exception e) {
            throw new RuntimeException("Failed to open context menu: " + e.getMessage());
        }
    }
}
