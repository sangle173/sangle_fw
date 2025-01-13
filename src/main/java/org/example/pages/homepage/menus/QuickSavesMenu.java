package org.example.pages.homepage.menus;

import io.appium.java_client.android.AndroidDriver;
import org.example.pages.BasePage;

public class QuickSavesMenu extends BasePage {
    // Constructor
    public QuickSavesMenu(AndroidDriver driver) {
        super(driver);
    }

    // Add methods to interact with Source Row Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Source Row Menu
    }
}
