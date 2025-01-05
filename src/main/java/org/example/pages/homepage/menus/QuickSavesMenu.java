package org.example.pages.homepage.menus;

import io.appium.java_client.android.AndroidDriver;

public class QuickSavesMenu {

    private AndroidDriver driver;

    // Constructor
    public QuickSavesMenu(AndroidDriver driver) {
        this.driver = driver;
    }

    // Add methods to interact with Source Row Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Source Row Menu
    }
}
