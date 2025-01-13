package org.example.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.example.utils.*;
import org.example.utils.enums.Direction;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppPage extends BasePage {

    private static final int NO_OF_ROW = 3;

    private static final String DYNAMIC_APP_LOCATOR = "xpath=//android.view.View[@resource-id=\"%s\"]/android.view.View";
    private static final String APP_PAGE_LOCATOR = "xpath=//android.widget.FrameLayout[@resource-id=\"android:id/content\"]//android.widget.TextView[@text=\"Apps\"]";
    private static final String ALL_APP_LOCATOR = "xpath=//android.widget.FrameLayout[@resource-id=\"android:id/content\"]//android.widget.TextView[@text=\"Apps\"]/following-sibling::android.view.View/android.view.View";
    private static final String ADD_APP_LOCATOR = "uiautomator=new UiSelector().text(\"Add App\")";
    private static final String RESTART_APP_LOCATOR = "xpath=//android.widget.TextView[@text=\"Restart\"]";
    private static final String OPEN_APP_LOCATOR = "xpath=//android.widget.TextView[@text=\"Open\"]";
    private static final String REMOVE_APP_LOCATOR = "xpath=//android.widget.TextView[@text=\"Remove\"]";
    private static final String APP_NAME_LOCATOR = "xpath=(//android.widget.TextView[@text=\"Open\"]/../preceding-sibling::android.widget.TextView)[1]";

    public AppPage(AndroidDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllAppElement() {
        return ElementUtils.findElements(driver, ALL_APP_LOCATOR);
    }


    // Example: Method to validate an item exists in the menu
    public boolean isAppPagePresent() {
        WaitUtils.waitForElementToBeVisible(driver, APP_PAGE_LOCATOR);
        WebElement element = ElementUtils.findElement(driver, APP_PAGE_LOCATOR);
        return element.isDisplayed();
    }

    public boolean isAddAppButtonDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, ADD_APP_LOCATOR);
        return ElementUtils.findElement(driver, ADD_APP_LOCATOR).isDisplayed();
    }

    public boolean isRestartButtonDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, RESTART_APP_LOCATOR);
        return ElementUtils.findElement(driver, RESTART_APP_LOCATOR).isDisplayed();
    }

    public boolean isOpenButtonDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, OPEN_APP_LOCATOR);
        return ElementUtils.findElement(driver, OPEN_APP_LOCATOR).isDisplayed();
    }

    public boolean isRemoveButtonDisplayed() {
        WaitUtils.waitForElementToBeVisible(driver, REMOVE_APP_LOCATOR);
        return ElementUtils.findElement(driver, REMOVE_APP_LOCATOR).isDisplayed();
    }

    public boolean isAddAppButtonFocused() {
        return Boolean.parseBoolean(ElementUtils.findElement(driver, ADD_APP_LOCATOR).getAttribute("focused"));
    }

    public String getAppActiveName() {
        Assert.assertTrue(isRemoveButtonDisplayed(), "Verify remove button is displayed");
        Assert.assertTrue(isOpenButtonDisplayed(), "Verify open button is displayed");
        Assert.assertTrue(isRestartButtonDisplayed(), "Verify restart button is displayed");
        String appActiveName = null;
        if (ElementUtils.findElement(driver, APP_NAME_LOCATOR).isDisplayed()) {
            appActiveName = ElementUtils.findElement(driver, APP_NAME_LOCATOR).getText();
        }
        return appActiveName;
    }


    public List<String> navigateToAndAddApp() throws InterruptedException {
        List<String> appActiveList = new ArrayList<>();
        int numberOfRows = NO_OF_ROW; // Number of rows in the grid
        boolean moveRight = true; // Initial direction for movement

        // Iterate through each row
        for (int row = 0; row < numberOfRows; row++) {
            boolean isEndOfRow = false;

            // Navigate through each app in the current row
            while (!isEndOfRow) {
                // Perform action on the current app
                appActiveList.add(addAppAction());

                // Check if unable to move further in the current direction
                isEndOfRow = NavigationUtils.checkEndOfRowWithoutMoving(driver, moveRight ? Direction.RIGHT : Direction.LEFT);
            }

            // If it's the last row, stop navigating
            if (row == numberOfRows - 1) {
                break;
            }

            // Move down to the next row and change direction
            NavigationUtils.move(driver, Direction.DOWN);
            moveRight = !moveRight; // Alternate direction for the next row
        }
        return appActiveList;
    }

    public void navigateToAndRemoveThroughGridApps() throws InterruptedException {
        int numberOfRows = NO_OF_ROW; // Number of rows in the grid
        boolean moveRight = true; // Initial direction for movement

        // Iterate through each row
        for (int row = 0; row < numberOfRows; row++) {
            boolean isEndOfRow = false;

            // Navigate through each app in the current row
            while (!isEndOfRow) {
                // Perform action on the current app
                removeAppAction();

                // Check if unable to move further in the current direction
                isEndOfRow = NavigationUtils.checkEndOfRowWithoutMoving(driver, moveRight ? Direction.RIGHT : Direction.LEFT);
            }

            // If it's the last row, stop navigating
            if (row == numberOfRows - 1) {
                break;
            }

            // Move down to the next row and change direction
            NavigationUtils.move(driver, Direction.DOWN);
            moveRight = !moveRight; // Alternate direction for the next row
        }
    }

    public void navigateToAndRemoveActiveApps() throws InterruptedException {
        int numberOfRows = NO_OF_ROW; // Number of rows in the grid
        boolean moveRight = true; // Initial direction for movement

        // Iterate through each row
        for (int row = 0; row < numberOfRows; row++) {
            boolean isEndOfRow = false;

            // Navigate through each app in the current row
            while (!isEndOfRow) {
                // Perform action on the current app
                removeActiveAppAction();

                // Check if unable to move further in the current direction
                isEndOfRow = NavigationUtils.checkEndOfRowWithoutMoving(driver, moveRight ? Direction.RIGHT : Direction.LEFT);
            }

            // If it's the last row, stop navigating
            if (row == numberOfRows - 1) {
                break;
            }

            // Move down to the next row and change direction
            NavigationUtils.move(driver, Direction.DOWN);
            moveRight = !moveRight; // Alternate direction for the next row
        }
    }


    private String addAppAction() throws InterruptedException {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        String appName = null;
        if (isAddAppButtonDisplayed()) {
            KeyEventUtils.pressCenter(driver);
            assert isOpenButtonDisplayed();
            appName = ElementUtils.findElement(driver, APP_NAME_LOCATOR).getText();
            Allure.step("Adding the (" + appName + ") app...");
        }
        AllureAssert.assertTrue(isOpenButtonDisplayed() && isRestartButtonDisplayed(), "Verify the (" + appName + ") is installed");

        // Press "Back" to return to the grid
        KeyEventUtils.pressBack(driver);
        assert isAppPagePresent();
        return appName;
    }

    private void removeAppAction() throws InterruptedException {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        String appName = null;
        if (isRemoveButtonDisplayed()) {
            appName = ElementUtils.findElement(driver, APP_NAME_LOCATOR).getText();
            NavigationUtils.goToItemOnMenuByRight(driver, "xpath=//android.widget.TextView[@text=\"Remove\"]/..");
            Allure.step("Removing the (" + appName + ") app ...");
            KeyEventUtils.pressCenter(driver);
        }
        AllureAssert.assertTrue(isAddAppButtonDisplayed(), "Verify the (" + appName + ") is uninstalled.");
        // Press "Back" to return to the grid
        KeyEventUtils.pressBack(driver);
        assert isAppPagePresent();
    }

    private void removeActiveAppAction() throws InterruptedException {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        String appName = null;
        WebElement removeButton = ElementUtils.findElement(driver, REMOVE_APP_LOCATOR);
        if (removeButton !=null) {
            appName = ElementUtils.findElement(driver, APP_NAME_LOCATOR).getText();
            NavigationUtils.goToItemOnMenuByRight(driver, "xpath=//android.widget.TextView[@text=\"Remove\"]/..");
            Allure.step("Removing the (" + appName + ") app ...");
            KeyEventUtils.pressCenter(driver);
            KeyEventUtils.pressBack(driver);

        } else {
            Allure.step(appName + " app is active, back to app page");
            KeyEventUtils.pressBack(driver);
        }
        // Press "Back" to return to the grid
        assert isAppPagePresent();
    }


}
