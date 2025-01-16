package pw_test;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.example.pages.SearchPage;
import org.example.pages.SettingPage;
import org.example.pages.homepage.HomePage;
import org.example.pages.homepage.menus.WatchlistMenu;
import org.example.utils.AllureAssert;
import org.example.utils.DataUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.VirtualKeyboardUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class SettingTest extends BaseTest {
    HomePage homePage = new HomePage(driver);
    SettingPage settingPage;

    @Test(testName = "Setting Page testing", description = "Setting Page testing")
    public void settingPageTesting() throws InterruptedException {
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        homePage = new HomePage(driver);
        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433

        Allure.step("Go to setting page");
        KeyEventUtils.pressUp(driver);
        KeyEventUtils.pressRight(driver);
        KeyEventUtils.pressCenter(driver);
        settingPage = new SettingPage(driver);

        AllureAssert.assertTrue(settingPage.isSettingPageDisplayed(),
        "Verify the new setting page is displayed");

        AllureAssert.assertTrue(settingPage.isItemsDisplayedProperly(),
                "Verify the new setting items displayed properly");
//        String keyword = DataUtils.getRandomMovie().toLowerCase();
//        VirtualKeyboardUtils.inputStringViaKeyboard(driver,"ava");
//
//        AllureAssert.assertTrue(searchPage.isMoviesSearchResultDisplayed(),"Verify the Movies Search section is displayed");
//        AllureAssert.assertTrue(searchPage.isMoviesSearchResultDisplayedProperly(), "Verify the search movie results is displayed properly ");
    }
}
