package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.ConfigReader;
import org.example.utils.enums.Direction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NavigationUtils {

    private static final Logger logger = LogManager.getLogger(NavigationUtils.class);

    /**
     * Navigates to a specific item on the menu by moving right.
     *
     * @param driver        The AndroidDriver instance.
     * @param targetLocator The locator for the target element.
     * @throws InterruptedException If interrupted during navigation.
     */
    public static void goToItemOnMenuByRight(AndroidDriver driver, String targetLocator) throws InterruptedException {
        logger.info("Navigating to the target item by moving RIGHT: {}", targetLocator);

        // Step 1: Ensure the active element is at the far left of the menu
        logger.info("Ensuring the active element is at the far left of the menu.");
        moveToEndOfMenu(driver, Direction.LEFT);

        // Step 2: Navigate to the target item by moving right
        navigateToTarget(driver, targetLocator, Direction.RIGHT);
    }

    /**
     * Navigates to a specific item on the menu by moving down.
     *
     * @param driver        The AndroidDriver instance.
     * @param targetLocator The locator for the target element.
     * @throws InterruptedException If interrupted during navigation.
     */
    public static void goToItemOnMenuByDown(AndroidDriver driver, String targetLocator) throws InterruptedException {
        logger.info("Navigating to the target item by moving DOWN: {}", targetLocator);

        // Step 1: Ensure the active element is at the top of the menu
        logger.info("Ensuring the active element is at the top of the menu.");
        moveToEndOfMenu(driver, Direction.UP);

        // Step 2: Navigate to the target item by moving down
        navigateToTarget(driver, targetLocator, Direction.DOWN);
    }


    /**
     * Moves in the specified direction to navigate to a target element.
     *
     * @param driver        The AndroidDriver instance.
     * @param targetLocator The locator for the target element.
     * @param direction     The direction to move (e.g., RIGHT or DOWN).
     * @throws InterruptedException If interrupted during navigation.
     */
    private static void navigateToTarget(AndroidDriver driver, String targetLocator, Direction direction) throws InterruptedException {
        logger.info("Starting navigation in the {} direction to target: {}", direction, targetLocator);

        int timeout = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();
        WebElement previousActiveElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeout)) {
            try {
                // Check if the active element is the target or its parent
                if (isActiveElementParentOfTarget(driver, targetLocator)) {
                    logger.info("Target element found. Navigation complete.");
                    return;
                }

                // Get the current active element
                WebElement currentActiveElement = driver.switchTo().activeElement();

                // Stop if the active element does not change after a move
                if (previousActiveElement != null && previousActiveElement.equals(currentActiveElement)) {
                    logger.error("Reached the end of the menu in the {} direction without finding the target element.", direction);
                    throw new AssertionError("Target element not found in the menu.");
                }

                // Move in the specified direction
                logger.info("Moving {}.", direction);
                move(driver, direction);

                // Update the previous active element
                previousActiveElement = currentActiveElement;

                // Add delay for navigation stability
                Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));

            } catch (Exception e) {
                logger.error("Error occurred while navigating in the {} direction: {}", direction, e.getMessage(), e);
            }
        }

        throw new AssertionError("Timeout reached: Unable to navigate to the target element.");
    }



    /**
     * Moves to the end of the menu in the specified direction.
     *
     * @param driver    The AndroidDriver instance.
     * @param direction The direction to move (e.g., LEFT, RIGHT, UP, DOWN).
     * @throws InterruptedException If interrupted during navigation.
     */
    public static void moveToEndOfMenu(AndroidDriver driver, Direction direction) throws InterruptedException {
        logger.info("Moving to the end of the menu in the {} direction.", direction);

        int timeout = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();
        WebElement previousElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeout)) {
            try {
                WebElement currentActiveElement = driver.switchTo().activeElement();

                // Stop moving if the active element doesn't change
                if (previousElement != null && previousElement.equals(currentActiveElement)) {
                    logger.info("Reached the end of the menu in the {} direction.", direction);
                    return;
                }

                logger.info("Moving {}.", direction);
                move(driver, direction);
                previousElement = currentActiveElement;

                Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
            } catch (Exception e) {
                logger.error("Error occurred while moving to the end of the menu: {}", e.getMessage(), e);
            }
        }

        throw new AssertionError("Failed to reach the end of the menu within the specified timeout.");
    }


    /**
     * Navigates to the specified menu. The navigation stops if the active element
     * is found to be a child of the menu, or if the end of the menu is reached
     * (the active element remains the same after moving in the specified direction),
     * or if the timeout is reached.
     *
     * @param driver      The AndroidDriver instance.
     * @param menuLocator The XPath locator for the menu.
     * @param direction   The direction to move (e.g., UP, DOWN, LEFT, RIGHT).
     * @throws InterruptedException If interrupted while sleeping between movements.
     */
    public static void goToMenu(AndroidDriver driver, String menuLocator, Direction direction) throws InterruptedException {
        logger.info("Starting navigation to menu: {}", menuLocator);

        int timeoutInSeconds = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();

        WebElement previousActiveElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeoutInSeconds)) {
            try {
                // Get the current active element
                WebElement currentActiveElement = driver.switchTo().activeElement();

                // Check if the active element is already part of the menu
                if (isActiveElementAChildOfMenu(driver, menuLocator)) {
                    logger.info("Active element is already a child of the menu. Stopping navigation.");
                    return;
                }

                // Check if the active element is the same as the previous one
                if (previousActiveElement != null && previousActiveElement.equals(currentActiveElement)) {
                    logger.info("Reached the end of the menu. No further movement possible in direction: {}", direction);
                    throw new AssertionError("Failed to move to item: Reached the end of the row/menu. Element not found.");
                }

                // Log and move in the specified direction
                logger.info("Active element not part of the menu. Moving {}.", direction);
                move(driver, direction);

                // Add a delay for navigation stability
                Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));

                // Update the previous active element
                previousActiveElement = currentActiveElement;

            } catch (Exception e) {
                logger.error("An error occurred while navigating to the menu: {}", e.getMessage(), e);
            }
        }

        // If timeout is reached
        throw new IllegalStateException("Timeout reached: Unable to navigate to the menu.");
    }

    /**
     * Checks if the active element is a child of the specified menu.
     *
     * @param driver      The AndroidDriver instance.
     * @param menuLocator The locator for the menu to check.
     * @return true if the active element is a child of the specified menu, false otherwise.
     */
    public static boolean isActiveElementAChildOfMenu(AndroidDriver driver, String menuLocator) {
        try {
            // Get the active element
            WebElement activeElement = driver.switchTo().activeElement();
            if (activeElement == null) {
                logger.warn("No active element found.");
                return false;
            }

            // Find the menu element
            WebElement menuElement = ElementUtils.findElement(driver, menuLocator);
            if (menuElement == null) {
                logger.warn("Menu element not found.");
                return false;
            }

            // Check if the active element is a child of the menu element
            List<WebElement> menuChildren = menuElement.findElements(By.xpath(".//*"));
            if (menuChildren.contains(activeElement)) {
                logger.info("Active element is part of the menu.");
                return true;
            } else {
                logger.info("Active element is not part of the menu.");
            }
        } catch (Exception e) {
            logger.error("Error while checking if active element is a child of the menu: {}", e.getMessage(), e);
        }

        return false;
    }


    /**
     * Moves in the specified direction.
     *
     * @param driver    AndroidDriver instance to interact with the app
     * @param direction the direction to move: "right", "left", "up", "down"
     * @throws InterruptedException if the thread is interrupted
     */
    public static void move(AndroidDriver driver, Direction direction) throws InterruptedException {
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

    /**
     * Checks if the active element is the target element or the parent of the target element.
     *
     * @param driver        The AndroidDriver instance.
     * @param targetLocator The locator for the target element.
     * @return true if the active element is the target element or its parent, false otherwise.
     */
    public static boolean isActiveElementParentOfTarget(AndroidDriver driver, String targetLocator) {
        try {
            // Get the active element
            WebElement activeElement = driver.switchTo().activeElement();
            if (activeElement == null) {
                logger.warn("No active element found.");
                return false;
            }

            // Find the target element
            WebElement targetElement = ElementUtils.findElement(driver, targetLocator);
            if (targetElement == null) {
                logger.warn("Target element not found with locator: {}", targetLocator);
                return false;
            }

            // Check if the active element equals the target element
            if (activeElement.equals(targetElement)) {
                logger.info("Active element is the target element.");
                return true;
            }

            // Check if the active element is the parent of the target element
            List<WebElement> activeChildren = activeElement.findElements(By.xpath(".//*"));
            if (activeChildren.contains(targetElement)) {
                logger.info("Active element is the parent of the target element.");
                return true;
            }

            logger.info("Active element is neither the target element nor its parent.");
        } catch (Exception e) {
            logger.error("Error while checking if active element is the target element or its parent: {}", e.getMessage(), e);
        }
        return false;
    }


    /**
     * Checks if the end of a row or menu has been reached without moving.
     *
     * @param driver    The AndroidDriver instance to interact with the app.
     * @param direction The direction to check.
     * @return true if the end of the row or menu is reached, false otherwise.
     * @throws InterruptedException if the thread is interrupted.
     */
    public static boolean checkEndOfRowWithoutMoving(AndroidDriver driver, Direction direction) throws InterruptedException {
        WebElement activeElementBefore = driver.switchTo().activeElement();
        move(driver, direction);
        WebElement activeElementAfter = driver.switchTo().activeElement();

        if (activeElementBefore.equals(activeElementAfter)) {
            logger.info("End of the row/menu reached.");
            return true;
        }

        return false;
    }

}
