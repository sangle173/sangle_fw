package pw_test;

import base.BaseTest;
import org.example.pages.AppPage;
import org.example.pages.homepage.HomePage;
import org.example.pages.homepage.menus.SourceRowMenu;
import org.example.utils.AllureAssert;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class BvtTesting extends BaseTest {

    @Test
    public void addAppTest() throws InterruptedException {
        KeyEventUtils.pressHome(driver);
        HomePage homePage = new HomePage(driver);
        SourceRowMenu sourceRowMenu = homePage.goToSourceRowMenu();
        NavigationUtils.moveToTheEnd(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AppPage appPage = new AppPage(driver);
        AllureAssert.assertTrue(appPage.isAppPagePresent(),"Verify the app page displays");
        appPage.navigateToAndAddAppThroughGridApps();
    }

    @Test
    public void removeAppTesting() throws InterruptedException {
        KeyEventUtils.pressHome(driver);
        HomePage homePage = new HomePage(driver);
        SourceRowMenu sourceRowMenu = homePage.goToSourceRowMenu();
        NavigationUtils.moveToTheEnd(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AppPage appPage = new AppPage(driver);
        AllureAssert.assertTrue(appPage.isAppPagePresent(),"Verify the app page displays");
        appPage.navigateToAndRemoveThroughGridApps();
    }
}
