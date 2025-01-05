package org.example.pages.homepage;

import io.appium.java_client.android.AndroidDriver;
import org.example.pages.BasePage;
import org.example.pages.homepage.menus.QuickSavesMenu;
import org.example.pages.homepage.menus.SourceRowMenu;
import org.example.pages.homepage.menus.UpNextMenu;
import org.example.pages.homepage.menus.WatchlistMenu;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;

public class HomePage extends BasePage {

    // Locators for menus
    private static final String UP_NEXT_MENU = "//xpath_to_up_next_menu";
    private static final String SOURCE_ROW_MENU = "//xpath_to_source_row_menu";
    private static final String QUICK_SAVES_MENU = "//xpath_to_quick_saves_menu";
    private static final String WATCHLIST_MENU = "//xpath_to_watchlist_menu";

    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Navigate to an item in the Up Next Menu.
     *
     * @throws InterruptedException If interrupted during navigation.
     */
    public UpNextMenu goToUpNextMenu() throws InterruptedException {
        System.out.println("Navigating to Up Next Menu...");
        NavigationUtils.goToMenu(driver, UP_NEXT_MENU, Direction.DOWN);
        return new UpNextMenu(driver); // Return the UpNextMenu instance
    }

    /**
     * Navigate to an item in the Source Row Menu.
     *
     * @throws InterruptedException If interrupted during navigation.
     */
    public SourceRowMenu goToSourceRowMenu() throws InterruptedException {
        System.out.println("Navigating to Source Row Menu...");
        NavigationUtils.goToMenu(driver, SOURCE_ROW_MENU, Direction.DOWN);
        return new SourceRowMenu(driver);
    }

    /**
     * Navigate to an item in the Quick Saves Menu.
     *
     * @throws InterruptedException If interrupted during navigation.
     */
    public QuickSavesMenu goToQuickSavesMenu() throws InterruptedException {
        System.out.println("Navigating to Quick Saves Menu...");
        NavigationUtils.goToMenu(driver, QUICK_SAVES_MENU, Direction.DOWN);
        return new QuickSavesMenu(driver);
    }

    /**
     * Navigate to an item in the Watchlist Menu.
     *
     * @throws InterruptedException If interrupted during navigation.
     */
    public WatchlistMenu goToWatchlistMenu() throws InterruptedException {
        System.out.println("Navigating to Watchlist Menu...");
        NavigationUtils.goToMenu(driver, WATCHLIST_MENU, Direction.DOWN);
        return new WatchlistMenu(driver);
    }
}
