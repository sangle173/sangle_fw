package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.example.utils.*;
import org.example.utils.enums.Direction;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

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


    public List<String> navigateToAndAddAppThroughGridApps() throws InterruptedException {
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
                isEndOfRow = checkEndOfRowWithoutMoving(driver, moveRight ? Direction.RIGHT : Direction.LEFT);
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
                isEndOfRow = checkEndOfRowWithoutMoving(driver, moveRight ? Direction.RIGHT : Direction.LEFT);
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

    public List<String> navigateToAndVerifyTheAppActiveProperly(List<String> appActiveList) throws InterruptedException {
        int numberOfRows = 1; // Number of rows in the grid
        //Ensure start from first item
        NavigationUtils.moveToTheEnd(driver, Direction.LEFT);
        boolean moveRight = true; // Initial direction for movement

        // Iterate through each row
        for (int row = 0; row < numberOfRows; row++) {
            boolean isEndOfRow = false;

            // Navigate through each app in the current row
            while (!isEndOfRow) {
                // Perform action on the current app
                ContextMenu contextMenu = new BasePage(driver).openContextMenu();
                assert contextMenu.isAppDetailsOptionDisplayed();
                contextMenu.moveAppDetailsOptionAndOpen();
                String appActiveName = ElementUtils.findElement(driver,APP_NAME_LOCATOR).getText();
                assert appActiveList.contains(appActiveName);
                Assert.assertTrue(appActiveList.contains(appActiveName), "The app " + appActiveName + " active properly.");
                // Press "Back" to return to the grid
                KeyEventUtils.pressBack(driver);
                // Check if unable to move further in the current direction
                isEndOfRow = checkEndOfRowWithoutMoving(driver, moveRight ? Direction.RIGHT : Direction.LEFT);
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

    private boolean checkEndOfRowWithoutMoving(AndroidDriver driver, Direction direction) throws InterruptedException {
        WebElement activeElementBefore = driver.switchTo().activeElement(); // Get the current active element

        // Simulate a move in the given direction
        NavigationUtils.move(driver, direction);

        // Get the active element after the move
        WebElement activeElementAfter = driver.switchTo().activeElement();

        // Determine if the end of the row is reached
        if (activeElementBefore.equals(activeElementAfter)) {
            // If the element didn't change, it means we are at the end of the row
            return true;
        }

        // Return false if the element changed
        return false;
    }


    private String addAppAction() throws InterruptedException {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        String appName = null;
        if (isAddAppButtonDisplayed()) {
            KeyEventUtils.pressCenter(driver);
            assert isOpenButtonDisplayed();
            appName = ElementUtils.findElement(driver, APP_NAME_LOCATOR).getText();
        }
        AllureAssert.assertTrue(isOpenButtonDisplayed(), "Verify the Open button is displayed");
        AllureAssert.assertTrue(isRestartButtonDisplayed(), "Verify the Restart button is displayed");
//        AllureAssert.assertTrue(isRemoveButtonDisplayed(), "Verify the Remove button is displayed");

        // Press "Back" to return to the grid
        KeyEventUtils.pressBack(driver);
        assert isAppPagePresent();
        return appName;
    }

    private void removeAppAction() throws InterruptedException {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        if (isRemoveButtonDisplayed()) {
            NavigationUtils.moveToElement(driver, "xpath=//android.widget.TextView[@text=\"Remove\"]/..", Direction.RIGHT);
            KeyEventUtils.pressCenter(driver);
        }
        AllureAssert.assertTrue(isAddAppButtonDisplayed(), "Verify the Add button is displayed");
        // Press "Back" to return to the grid
        KeyEventUtils.pressBack(driver);
        assert isAppPagePresent();
    }


}
