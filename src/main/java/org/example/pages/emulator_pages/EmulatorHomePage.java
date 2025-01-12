package org.example.pages.emulator_pages;

import io.appium.java_client.android.AndroidDriver;
import org.example.pages.BasePage;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.WaitUtils;
import org.example.utils.enums.Direction;

public class EmulatorHomePage extends BasePage {

    private static final String APP_PAGES = "id=com.google.android.tvlauncher:id/end_tab_title_view";
    private static final String FIRST_MENU = "id=com.google.android.tvlauncher:id/tab_title_container";


    public EmulatorHomePage(AndroidDriver driver) {
        super(driver);
    }

    public void goToAppPage() throws InterruptedException {
        NavigationUtils.goToItemOnMenuByRight(driver, APP_PAGES);
        WaitUtils.waitForElementToBeVisible(driver,APP_PAGES);
        KeyEventUtils.pressCenter(driver);
    }

    public void goToFirstMenu() throws InterruptedException {
        KeyEventUtils.pressHome(driver);
        NavigationUtils.goToMenu(driver, FIRST_MENU, Direction.UP);
    }

}
