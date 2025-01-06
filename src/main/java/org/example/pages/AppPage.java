package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.example.utils.ElementUtils;
import org.example.utils.KeyEventUtils;
import org.example.utils.NavigationUtils;
import org.example.utils.WaitUtils;
import org.example.utils.enums.Direction;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AppPage extends BasePage {

    private static final int NO_OF_ROW = 3;

    private static final String DYNAMIC_APP_LOCATOR = "xpath=//android.view.View[@resource-id=\"%s\"]/android.view.View";
    private static final String APP_PAGE_LOCATOR = "xpath=//android.widget.FrameLayout[@resource-id=\"android:id/content\"]//android.widget.TextView[@text=\"Apps\"]";
    private static final String ALL_APP_LOCATOR = "xpath=//android.widget.FrameLayout[@resource-id=\"android:id/content\"]//android.widget.TextView[@text=\"Apps\"]/following-sibling::android.view.View/android.view.View";

    public AppPage(AndroidDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllAppElement() {
        return ElementUtils.findElements(driver, ALL_APP_LOCATOR);
    }


    // Example: Method to validate an item exists in the menu
    public boolean isAppPagePresent() {
        WaitUtils.waitForElementToBeVisible(driver,APP_PAGE_LOCATOR);
        WebElement element = ElementUtils.findElement(driver, APP_PAGE_LOCATOR);
        return element.isDisplayed();
    }

    public boolean isAddAppButtonDisplayed(){
        WaitUtils.waitForElementToBeVisible(driver, "uiautomator=new UiSelector().text(\"Add App\")");
        return ElementUtils.findElement(driver,"uiautomator=new UiSelector().text(\"Add App\")").isDisplayed();
    }

    public boolean isRestartButtonDisplayed(){
        WaitUtils.waitForElementToBeVisible(driver, "xpath=//android.widget.TextView[@text=\"Restart\"]");
        return ElementUtils.findElement(driver,"xpath=//android.widget.TextView[@text=\"Restart\"]").isDisplayed();
    }

    public boolean isRemoveButtonDisplayed(){
        WaitUtils.waitForElementToBeVisible(driver, "xpath=//android.widget.TextView[@text=\"Remove\"]");
        return ElementUtils.findElement(driver,"xpath=//android.widget.TextView[@text=\"Remove\"]").isDisplayed();
    }

    public boolean isAddAppButtonFocused(){
        return Boolean.parseBoolean(ElementUtils.findElement(driver,"uiautomator=new UiSelector().text(\"Add App\")").getAttribute("focused"));
    }

    public void navigateToAndAddAppThroughGridApps() throws InterruptedException {
        int numberOfRows = NO_OF_ROW; // Number of rows in the grid
        boolean moveRight = true; // Initial direction for movement

        // Iterate through each row
        for (int row = 0; row < numberOfRows; row++) {
            boolean isEndOfRow = false;

            // Navigate through each app in the current row
            while (!isEndOfRow) {
                // Perform action on the current app
                addAppAction();

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



    private boolean checkEndOfRowWithoutMoving(AndroidDriver driver, Direction direction) {
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





    private void addAppAction() {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        if (isAddAppButtonDisplayed()){
            KeyEventUtils.pressCenter(driver);
        }
        assert isRestartButtonDisplayed();
        // Press "Back" to return to the grid
        KeyEventUtils.pressBack(driver);
        assert isAppPagePresent();
    }

    private void removeAppAction() throws InterruptedException {
        // Press "Enter" to select the current app
        KeyEventUtils.pressCenter(driver);
        if (isRemoveButtonDisplayed()){
            NavigationUtils.moveToElement(driver,"xpath=//android.widget.TextView[@text=\"Remove\"]/..", Direction.RIGHT);
            KeyEventUtils.pressCenter(driver);
        }
        assert isAddAppButtonDisplayed();
        // Press "Back" to return to the grid
        KeyEventUtils.pressBack(driver);
        assert isAppPagePresent();
    }
}
