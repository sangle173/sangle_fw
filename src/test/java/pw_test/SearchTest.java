package pw_test;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.example.pages.SearchPage;
import org.example.pages.homepage.HomePage;
import org.example.pages.homepage.menus.WatchlistMenu;
import org.example.utils.AllureAssert;
import org.example.utils.DataUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.VirtualKeyboardUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Locale;

@Listeners(org.example.utils.TestListener.class)
public class SearchTest extends BaseTest {
    HomePage homePage = new HomePage(driver);
    WatchlistMenu watchlistMenu;
    SearchPage searchPage;

    @Test(testName = "Search Movie Function", description = "Search Movie Function")
    public void searchMovieTest() throws InterruptedException {
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
        String keyword = DataUtils.getRandomMovie().toLowerCase();
        VirtualKeyboardUtils.inputStringViaKeyboard(driver,"ava");

        AllureAssert.assertTrue(searchPage.isMoviesSearchResultDisplayed(),"Verify the Movies Search section is displayed");
        AllureAssert.assertTrue(searchPage.isMoviesSearchResultDisplayedProperly(), "Verify the search movie results is displayed properly ");
    }

    @Test(testName = "Search TV Shows Function", description = "Search TV Shows Function")
    public void searchTVShowsTest() throws InterruptedException {
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
        String keyword = DataUtils.getRandomTvShow().toLowerCase();
        VirtualKeyboardUtils.inputStringViaKeyboard(driver,keyword);
    }
}
