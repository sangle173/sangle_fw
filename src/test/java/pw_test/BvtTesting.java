package pw_test;

import base.BaseTest;
import io.appium.java_client.pagefactory.AndroidBy;
import io.qameta.allure.Allure;
import org.example.pages.AppPage;
import org.example.pages.BasePage;
import org.example.pages.ContextMenu;
import org.example.pages.homepage.HomePage;
import org.example.pages.homepage.menus.SourceRowMenu;
import org.example.utils.AllureAssert;
import org.example.utils.ElementUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.ContextMenuOptions;
import org.example.utils.enums.Direction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(org.example.utils.TestListener.class)
public class BvtTesting extends BaseTest {

    @Test(testName = "Install apps", description = "Install a new app, verify if it installs properly.")
    public void addAppTest() throws InterruptedException {
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        HomePage homePage = new HomePage(driver);
        Allure.step("Go to source row");
        homePage.goToSourceRowMenu();
        Allure.step("Go to add app button and open the apps page");
        NavigationUtils.moveToTheEnd(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AppPage appPage = new AppPage(driver);
        AllureAssert.assertTrue(appPage.isAppPagePresent(),"Verify the app page displays");
        Allure.step("Go through each app and active the app");
        List<String> appActiveList = appPage.navigateToAndAddAppThroughGridApps();
        System.out.println(appActiveList.size());

        //Verify the apps active properly
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        Allure.step("Go to source row");
        homePage.goToSourceRowMenu();
        appPage.navigateToAndVerifyTheAppActiveProperly(appActiveList);
    }

    @Test(testName = "Uninstall apps", description = "Uninstall the app, verify if it uninstalls properly.")
    public void removeAppTesting() throws InterruptedException {
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        HomePage homePage = new HomePage(driver);
        Allure.step("Go to source row");
        SourceRowMenu sourceRowMenu = homePage.goToSourceRowMenu();
        Allure.step("Go to add app button and open the apps page");
        NavigationUtils.moveToTheEnd(driver, Direction.RIGHT);
        KeyEventUtils.pressCenter(driver);
        AppPage appPage = new AppPage(driver);
        AllureAssert.assertTrue(appPage.isAppPagePresent(),"Verify the app page displays");
        Allure.step("Go through each app and de-active the app");
        appPage.navigateToAndRemoveThroughGridApps();
    }

    @Test(testName = "testing", description = "Uninstall the app, verify if it uninstalls properly.")
    public void testing() throws InterruptedException {
        Allure.step("Go to Home page");
        KeyEventUtils.pressHome(driver);
        HomePage homePage = new HomePage(driver);
        Allure.step("Go to source row");
        SourceRowMenu sourceRowMenu = homePage.goToSourceRowMenu();
        Allure.step("Go to add app button and open the apps page");
        NavigationUtils.moveToTheEnd(driver, Direction.LEFT);
        ContextMenu contextMenu = new BasePage(driver).openContextMenu();
        contextMenu.moveToAndSelectTheOption(ContextMenuOptions.APP_DETAILS);
    }
}
