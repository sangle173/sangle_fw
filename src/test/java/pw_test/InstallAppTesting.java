package pw_test;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.example.pages.AppPage;
import org.example.pages.homepage.HomePage;
import org.example.pages.homepage.menus.SourceRowMenu;
import org.example.utils.AllureAssert;
import org.example.utils.KeyEventUtils;
import org.example.utils.ListUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(org.example.utils.TestListener.class)
public class InstallAppTesting extends BaseTest {

    @Test(testName = "Install all apps", description = "Install a new app, verify if it installs properly.")
    public void addAppTest() throws InterruptedException {
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
        HomePage homePage = new HomePage(driver);
        Allure.step("Go to source row");
        homePage.goToSourceRowMenu();
        Allure.step("Go to add app button and open the apps page");
        NavigationUtils.moveToEndOfMenu(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AppPage appPage = new AppPage(driver);
        AllureAssert.assertTrue(appPage.isAppPagePresent(), "Verify the app page displays");
        Allure.step("Go through each app and active the app");
        List<String> expectedActiveAppsList = appPage.navigateToAndAddApp();

        //Verify the apps active properly
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
        Allure.step("Go to source row");
        homePage.goToSourceRowMenu();
        Allure.step("Get active apps in Source Row");
        List<String> actualActiveAppsOnSourceRow = new SourceRowMenu(driver).getActiveAppOnSourceRow();
        Assert.assertTrue(ListUtils.areListsEqualIgnoringOrder(expectedActiveAppsList,actualActiveAppsOnSourceRow),"Verify the apps is active properly");
    }

    @Test(testName = "Uninstall all apps", description = "Uninstall the app, verify if it uninstalls properly.")
    public void removeAppTesting() throws InterruptedException {
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
        HomePage homePage = new HomePage(driver);
        Allure.step("Go to source row");
        SourceRowMenu sourceRowMenu = homePage.goToSourceRowMenu();
        Allure.step("Go to add app button and open the apps page");
        NavigationUtils.moveToEndOfMenu(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AppPage appPage = new AppPage(driver);
        AllureAssert.assertTrue(appPage.isAppPagePresent(), "Verify the app page displays");
        Allure.step("Go through each app and de-active the app");
        appPage.navigateToAndRemoveThroughGridApps();


        //Verify the apps uninstall properly
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
        Allure.step("Go to source row");
        homePage.goToSourceRowMenu();
        Allure.step("Get active apps in Source Row");
        List<String> actualActiveAppsOnSourceRow = new SourceRowMenu(driver).getActiveAppOnSourceRow();
        Assert.assertTrue(actualActiveAppsOnSourceRow.isEmpty(),"Verify the apps is de-active properly");
    }


//    @Test(testName = "Uninstall all active apps", description = "Uninstall all active app, verify if it uninstalls properly.")
//    public void removeAppAllActiveApp() throws InterruptedException {
//        Allure.step("Go to Home page");
//        KeyEventUtils.pressHome(driver);
//        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
//        HomePage homePage = new HomePage(driver);
//        Allure.step("Go to source row");
//        SourceRowMenu sourceRowMenu = homePage.goToSourceRowMenu();
//        Allure.step("Go to add app button and open the apps page");
//        NavigationUtils.moveToEndOfMenu(driver, Direction.RIGHT);
//        KeyEventUtils.pressCenter(driver);
//        AppPage appPage = new AppPage(driver);
//        AllureAssert.assertTrue(appPage.isAppPagePresent(), "Verify the app page displays");
//        Allure.step("Go through each app and de-active the app");
//        appPage.navigateToAndRemoveActiveApps();
//
//
//        //Verify the apps uninstall properly
//        Allure.step("Go to Home page");
//        KeyEventUtils.pressHome(driver);
//        KeyEventUtils.pressHome(driver); //by pass due to bug: PINE-4433
//        Allure.step("Go to source row");
//        homePage.goToSourceRowMenu();
//        Allure.step("Get active apps in Source Row");
//        List<String> actualActiveAppsOnSourceRow = new SourceRowMenu(driver).getActiveAppOnSourceRow();
//        Assert.assertTrue(actualActiveAppsOnSourceRow.isEmpty(),"Verify the apps is de-active properly");
//    }
}
