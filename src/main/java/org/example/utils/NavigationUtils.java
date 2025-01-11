package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.ConfigReader;
import org.example.utils.enums.Direction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class NavigationUtils {

    private static final Logger logger = LogManager.getLogger(NavigationUtils.class);

    /**
     * Moves in the specified direction and checks if the element is focused.
     *
     * @param driver    AndroidDriver instance to interact with the app
     * @param locator   locator string for the target element
     * @param direction the direction to move: "right", "left", "up", "down"
     * @throws InterruptedException if the thread is interrupted
     */
    public static void moveToElement(AndroidDriver driver, String locator, Direction direction) throws InterruptedException {
        int timeout = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();
        WebElement previousElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeout)) {
            try {
                WebElement currentActiveElement = driver.switchTo().activeElement();
                WebElement targetElement = ElementUtils.findElement(driver, locator);

                if (targetElement == null) {
                    logger.info("Target element not found. Moving {}.", direction);
                    move(driver, direction);
                    continue;
                }

                if (currentActiveElement != null && currentActiveElement.equals(targetElement)) {
                    logger.info("Target element is already focused.");
                    return;
                }

                if (previousElement != null && previousElement.equals(currentActiveElement)) {
                    logger.error("Reached the end of the row/menu. Element not found.");
                    throw new AssertionError("Failed to move to item: Reached the end of the row/menu. Element not found.");
                }

                logger.info("Target element not focused. Moving {}.", direction);
                move(driver, direction);
                previousElement = currentActiveElement;

            } catch (Exception e) {
                logger.error("Error occurred while moving: {}", e.getMessage(), e);
                move(driver, direction);
            }
        }

        throw new AssertionError("Failed to move to item within the specified timeout.");
    }

    /**
     * Moves in the specified direction until the end of the menu is reached.
     *
     * @param driver    AndroidDriver instance to interact with the app
     * @param direction the direction to move: "right", "left", "up", "down"
     * @throws InterruptedException if the thread is interrupted
     */
    public static void moveToTheEnd(AndroidDriver driver, Direction direction) throws InterruptedException {
        logger.info("Moving to the end of the row/menu in the {} direction.", direction);
        int timeout = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();
        WebElement previousElement = null;

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeout)) {
            try {
                WebElement currentActiveElement = driver.switchTo().activeElement();

                if (previousElement != null && previousElement.equals(currentActiveElement)) {
                    logger.info("Reached the end of the row/menu.");
                    return;
                }

                logger.info("Moving {}.", direction);
                move(driver, direction);
                previousElement = currentActiveElement;

            } catch (Exception e) {
                logger.error("Error occurred while moving to the end: {}", e.getMessage(), e);
                move(driver, direction);
            }
        }

        throw new AssertionError("Failed to reach the end of the row/menu within the specified timeout.");
    }

    /**
     * Navigates to the specified menu. The navigation stops if the active element
     * is found to be a child of the menu, or if the timeout is reached.
     *
     * @param driver      The AndroidDriver instance.
     * @param menuLocator The XPath locator for the menu.
     * @param direction   The direction to move (e.g., UP, DOWN, LEFT, RIGHT).
     * @throws InterruptedException If interrupted while sleeping between movements.
     */
    public static void goToMenu(AndroidDriver driver, String menuLocator, Direction direction) throws InterruptedException {
        int timeoutInSeconds = Integer.parseInt(ConfigReader.getProperty(Constant.MENU_NAVIGATION_TIMEOUT));
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(timeoutInSeconds)) {
            if (isActiveElementAChildOfMenu(driver, menuLocator)) {
                logger.info("Active element is already a child of the menu. Stopping navigation.");
                return;
            }

            logger.info("Active element not part of the menu. Moving {}.", direction);
            move(driver, direction);
            Thread.sleep(Integer.parseInt(ConfigReader.getProperty(Constant.KEY_EVENT_DELAY)));
        }

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
            WebElement activeElement = driver.switchTo().activeElement();
            if (activeElement == null) {
                logger.warn("No active element found.");
                return false;
            }

            WebElement menuElement = ElementUtils.findElement(driver, menuLocator);
            if (menuElement == null) {
                logger.warn("Menu element not found.");
                return false;
            }

            return menuElement.findElements(By.xpath(".//*")).contains(activeElement);
        } catch (Exception e) {
            logger.error("An error occurred while checking if active element is a child of menu: {}", e.getMessage(), e);
            return false;
        }
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
