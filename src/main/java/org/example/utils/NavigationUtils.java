package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.example.config.ConfigReader;
import org.example.utils.enums.Direction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class NavigationUtils {
    /**
     * Moves in the specified direction and checks if the element is focused.
     *
     * @param driver    AndroidDriver instance to interact with the app
     * @param locator   locator string for the target element
     * @param direction the direction to move: "right", "left", "up", "down"
     * @throws InterruptedException if the thread is interrupted
     */
    public static void moveToElement(AndroidDriver driver, String locator, Direction direction) throws InterruptedException {
        // Define the timeout (in seconds)
        int timeout = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();

        // Initialize variables to track movement
        WebElement previousElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeout)) {
            try {
                // Get the current active element
                WebElement currentActiveElement = driver.switchTo().activeElement();

                // Find the target element using the locator
                WebElement targetElement = ElementUtils.findElement(driver, locator);

                // If the target element is not found, throw an exception
                if (targetElement == null) {
                    System.out.println("Target element not found. Moving " + direction + ".");
                    move(driver, direction);
                    continue;
                }

                // Check if the target element is already the active element
                if (currentActiveElement != null && currentActiveElement.equals(targetElement)) {
                    System.out.println("Target element is already focused.");
                    return; // Stop moving
                }

                // Check if the current active element is the same as the previous element
                if (previousElement != null && previousElement.equals(currentActiveElement)) {
                    System.out.println("Reached the end of the row/menu. Element not found.");
                    throw new AssertionError("Failed to move to item: Reached the end of the row/menu. Element not found.");
                }

                // Perform a movement in the specified direction
                System.out.println("Target element not focused. Moving " + direction + ".");
                move(driver, direction);

                // Update the previous element
                previousElement = currentActiveElement;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                move(driver, direction); // Keep moving
            }
        }

        // If the timeout is reached without focusing on the desired element
        throw new AssertionError("Failed to move to item within the specified timeout.");
    }

    public static void moveToTheEnd(AndroidDriver driver, Direction direction) throws InterruptedException {
        // Define the timeout (in seconds)
        System.out.println("Moving to the end of the row/menu in the " + direction + " direction.");
        int timeout = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();

        // Initialize variables to track movement
        WebElement previousElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeout)) {
            try {
                // Get the current active element
                WebElement currentActiveElement = driver.switchTo().activeElement();

                // Check if the active element has not changed
                if (previousElement != null && previousElement.equals(currentActiveElement)) {
                    System.out.println("Reached the end of the row/menu.");
                    return; // Exit the method as the end of the row/menu is reached
                }

                // Perform a movement in the specified direction
                System.out.println("Moving " + direction + ".");
                move(driver, direction);

                // Update the previous element
                previousElement = currentActiveElement;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                move(driver, direction); // Keep moving
            }
        }

        // If the timeout is reached without detecting the end of the row/menu
        throw new AssertionError("Failed to reach the end of the row/menu within the specified timeout.");
    }

    private static boolean isFocused(WebElement element) {
        // Check if the element is focused by checking the "focused" attribute
        return "true".equals(element.getAttribute("focused"));
    }

    /**
     * Navigates to the specified menu. The navigation stops if the active element
     * is found to be a child of the menu, or if the timeout is reached.
     *
     * @param driver The AndroidDriver instance.
     * @param menuLocator The XPath locator for the menu.
     * @param direction The direction to move (e.g., UP, DOWN, LEFT, RIGHT).
     * @throws InterruptedException If interrupted while sleeping between movements.
     */
    public static void goToMenu(AndroidDriver driver, String menuLocator, Direction direction) throws InterruptedException {
        // Get timeout from the configuration or use a default
        int timeoutInSeconds = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeoutInSeconds)) {
            // Check if the active element is already a child of the menu
            if (isActiveElementAChildOfMenu(driver, menuLocator)) {
                System.out.println("Active element is already a child of the menu. Stopping navigation.");
                return; // Exit if the active element is part of the menu
            }

            // Move in the specified direction
            System.out.println("Active element not part of the menu. Moving " + direction + ".");
            move(driver, direction);

            // Allow some delay to simulate real navigation
            Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
        }

        throw new IllegalStateException("Timeout reached: Unable to navigate to the menu.");
    }

    /**
     * Checks if the active element is a child of the specified menu.
     *
     * @param menuLocator The locator for the menu to check.
     * @param driver The AndroidDriver instance.
     * @return true if the active element is a child of the specified menu, false otherwise.
     */
    public static boolean isActiveElementAChildOfMenu(AndroidDriver driver, String menuLocator) {
        try {
            // Get the active element
            WebElement activeElement = driver.switchTo().activeElement();
            if (activeElement == null) {
                System.out.println("No active element found.");
                return false;
            }

            // Find the menu element
            WebElement menuElement = ElementUtils.findElement(driver,menuLocator);
            if (menuElement == null) {
                System.out.println("Menu element not found.");
                return false;
            }



            // Check if the active element is a child of the menu
            return menuElement.findElements(By.xpath(".//*")).contains(activeElement);
        } catch (Exception e) {
//            System.err.println("An error occurred while checking if active element is a child of menu: " + e.getMessage());
            return false;
        }
    }


    /**
     * Moves in the specified direction.
     *
     * @param driver    AndroidDriver instance to interact with the app
     * @param direction the direction to move: "right", "left", "up", "down"
     */
    private static void move(AndroidDriver driver, Direction direction) {
        switch (direction) {
            case RIGHT:
                KeyEventUtils.pressRight(driver);
                break;
            case LEFT:
                KeyEventUtils.pressLeft(driver);
                break;
            case UP:
                KeyEventUtils.pressUp(driver);
                break;
            case DOWN:
                KeyEventUtils.pressDown(driver);
                break;
            default:
                throw new IllegalArgumentException("Unsupported direction: " + direction);
        }
    }

}
