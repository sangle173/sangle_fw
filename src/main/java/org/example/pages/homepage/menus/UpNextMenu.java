package org.example.pages.homepage.menus;

import io.appium.java_client.android.AndroidDriver;
import org.example.pages.ContextMenu;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;

public class UpNextMenu {

    private AndroidDriver driver;

    // Constructor
    public UpNextMenu(AndroidDriver driver) {
        this.driver = driver;
    }

    // Add methods to interact with items within the Up Next Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Up Next Menu
    }

    // Example: Method to validate an item exists in the menu
    public boolean isItemPresent(String itemLocator) {
        // Logic to check if the item is present
        return true; // Placeholder
    }


    // Method to open the context menu using a long press action
    public ContextMenu openUpNextContextMenu() {
        try {
            KeyEventUtils.longPressOKButtonWithADB();
            // Return an instance of UpNextContextMenu class
            return new ContextMenu(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to open context menu: " + e.getMessage());
        }
    }

    // Method to open the context menu using a long press action
    public ContextMenu moveToItemAndOpenContextMenu(String locator) {
        try {
            NavigationUtils.moveToElement(driver,locator, Direction.RIGHT);
            KeyEventUtils.longPressOKButtonWithADB();
            // Return an instance of UpNextContextMenu class
            return new ContextMenu(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to open context menu: " + e.getMessage());
        }
    }
}

