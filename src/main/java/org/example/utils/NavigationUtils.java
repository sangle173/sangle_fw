package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.example.config.ConfigReader;
import org.example.utils.enums.Direction;
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
                WebElement targetElement = ElementUtil.findElement(driver, locator);

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
