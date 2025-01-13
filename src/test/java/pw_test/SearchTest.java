package pw_test;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.example.pages.BasePage;
import org.example.pages.ContextMenu;
import org.example.pages.SearchPage;
import org.example.pages.homepage.HomePage;
import org.example.pages.homepage.menus.WatchlistMenu;
import org.example.utils.AllureAssert;
import org.example.utils.DataUtils;
import org.example.utils.ElementUtils;
import org.example.utils.KeyEventUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class SearchTest extends BaseTest {
    HomePage homePage = new HomePage(driver);
    WatchlistMenu watchlistMenu;
    SearchPage searchPage;

    @Test(testName = "Verify Search Page", description = "Create Watchlist from Watchlist menu")
    public void searchTest() throws InterruptedException {


        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        homePage = new HomePage(driver);
        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433

        Allure.step("Go to search page");
        KeyEventUtils.pressUp(driver);
        KeyEventUtils.pressCenter(driver);
        searchPage = new SearchPage(driver);

        AllureAssert.assertTrue(searchPage.isSearchPageDisplayed(),
        "Verify the new search page is displayed");
        String keyword = DataUtils.getRandomTvShow();
        searchPage.inputSearchKeyword(keyword);
//        Allure.step("Navigate to add button and add new watchlist");
//        watchlistMenu.navigateToAndAddNewWatchlist(newWatchlistName);
//        AllureAssert.assertTrue(watchlistMenu.isWatchlistDisplayed(newWatchlistName), "Verify the new watchlist is created and appear on watchlist menu");
//
//        //Delete the created watchlist after creating
//        //Ensure the focus is from the end of menu
//        Allure.step("Go to Home page");
//        KeyEventUtils.pressHome(driver); //by pass due to the bug PINE-3396
//        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
//        Allure.step("Go to Watchlist menu");
//        watchlistMenu = homePage.goToWatchlistMenu();
//        AllureAssert.assertTrue(homePage.isActiveElementPlacedOnWatchlistMenu(), "Verify the focus is placed on the Watchlist Menu");
//        Allure.step("Go to created Watchlist and open the watchlist page");
//        watchlistMenu.navigateToWatchlistItemByName(newWatchlistName); //should be un-comment after the bug fix
//        KeyEventUtils.pressBack(driver);
//        Allure.step("Open the context menu and select delete option");
//        homePage.openContextMenu();
//        KeyEventUtils.pressDown(driver,3);
//        KeyEventUtils.pressCenter(driver);
//        AllureAssert.assertTrue(new BasePage(driver).isDeleteConfirmPopupDisplayed(), "Verify confirm delete popup is displayed");
//        Allure.step("Deleting watchlist ...");
//        KeyEventUtils.pressCenter(driver);
    }
}
