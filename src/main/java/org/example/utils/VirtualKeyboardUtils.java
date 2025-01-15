package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VirtualKeyboardUtils {

    private static final Logger logger = LogManager.getLogger(VirtualKeyboardUtils.class);

    // Define the keyboard layout
    private static final String[] KEYBOARD_LAYOUT = {
            "abcdef", // Row 1
            "ghijkl", // Row 2
            "mnopqr", // Row 3
            "stuvwx", // Row 4
            "yz0123", // Row 5
            "567890", // Row 6
            " _DEL"   // Row 7: SPACE and DELETE
    };

    /**
     * Inputs a string keyword by navigating a virtual keyboard grid.
     *
     * @param driver  The AndroidDriver instance.
     * @param keyword The string keyword to input.
     * @throws InterruptedException If interrupted during navigation.
     */
    public static void inputStringViaKeyboard(AndroidDriver driver, String keyword) throws InterruptedException {
        logger.info("Inputting keyword: {}", keyword);

        for (char c : keyword.toCharArray()) {
            if (c == ' ') {
                // Handle SPACE
                logger.info("Handling SPACE character.");
                navigateToRow(driver, 6); // Row 6 (0-based index)
                navigateToColumn(driver, 0); // Column 0 for SPACE
                KeyEventUtils.pressCenter(driver);
                continue;
            }

            if (c == '<') {
                // Handle DELETE
                logger.info("Handling DELETE character.");
                navigateToRow(driver, 6); // Row 6 (0-based index)
                navigateToColumn(driver, 1); // Column 1 for DELETE
                KeyEventUtils.pressCenter(driver);
                continue;
            }

            // Handle other characters
            int[] position = getCharPosition(c);
            if (position == null) {
                logger.warn("Character '{}' is not in the keyboard layout. Skipping.", c);
                continue; // Skip unsupported characters
            }

            int targetRow = position[0];
            int targetColumn = position[1];

            // Navigate to the target row
            navigateToRow(driver, targetRow);

            // Navigate to the target column within the row
            navigateToColumn(driver, targetColumn);

            // Press the center key to select the character
            logger.info("Pressing the center key to select character '{}'.", c);
            KeyEventUtils.pressCenter(driver);
        }
    }

    /**
     * Finds the position of a character in the keyboard layout.
     *
     * @param c The character to find.
     * @return An array with two integers: [rowIndex, columnIndex], or null if not found.
     */
    private static int[] getCharPosition(char c) {
        for (int row = 0; row < KEYBOARD_LAYOUT.length; row++) {
            int col = KEYBOARD_LAYOUT[row].indexOf(c);
            if (col != -1) {
                return new int[]{row, col};
            }
        }
        return null; // Character not found
    }

    /**
     * Navigates to the target row on the virtual keyboard.
     *
     * @param driver    The AndroidDriver instance.
     * @param targetRow The target row index (0-based).
     * @throws InterruptedException If interrupted during navigation.
     */
    private static void navigateToRow(AndroidDriver driver, int targetRow) throws InterruptedException {
        int[] currentPosition = getActiveElementPosition(driver);
        if (currentPosition == null) {
            throw new IllegalStateException("Unable to determine the position of the active element.");
        }

        int currentRow = currentPosition[0];
        logger.info("Navigating to row {} (current row: {}).", targetRow, currentRow);

        while (currentRow != targetRow) {
            if (currentRow < targetRow) {
                KeyEventUtils.pressDown(driver);
                currentRow++;
            } else {
                KeyEventUtils.pressUp(driver);
                currentRow--;
            }
            Thread.sleep(200); // Small delay for stability
        }

        logger.info("Reached target row {}.", targetRow);
    }

    /**
     * Navigates to the target column within the current row.
     *
     * @param driver       The AndroidDriver instance.
     * @param targetColumn The target column index (0-based).
     * @throws InterruptedException If interrupted during navigation.
     */
    private static void navigateToColumn(AndroidDriver driver, int targetColumn) throws InterruptedException {
        int[] currentPosition = getActiveElementPosition(driver);
        if (currentPosition == null) {
            throw new IllegalStateException("Unable to determine the position of the active element.");
        }

        int currentColumn = currentPosition[1];
        logger.info("Navigating to column {} (current column: {}).", targetColumn, currentColumn);

        while (currentColumn != targetColumn) {
            if (currentColumn < targetColumn) {
                KeyEventUtils.pressRight(driver);
                currentColumn++;
            } else {
                KeyEventUtils.pressLeft(driver);
                currentColumn--;
            }
            Thread.sleep(200); // Small delay for stability
        }

        logger.info("Reached target column {}.", targetColumn);
    }

    /**
     * Gets the row and column index of the currently active element.
     *
     * @param driver The AndroidDriver instance.
     * @return An array with two integers: [rowIndex, columnIndex], or null if not found.
     */
    private static int[] getActiveElementPosition(AndroidDriver driver) {
        try {
            // Get the current active element
            WebElement activeElement = driver.switchTo().activeElement();
            if (activeElement == null) {
                logger.warn("No active element found.");
                return null;
            }

            // Get the className of the active element
            String className = activeElement.getAttribute("className");

            if ("android.view.View".equals(className)) {
                // Handle TextView elements
                List<WebElement> textViewChildren = activeElement.findElements(By.className("android.widget.TextView"));
                if (textViewChildren.isEmpty()) {
                    logger.warn("No child element with class 'android.widget.TextView' found in active element.");
                    return null;
                }

                // Get the text of the first child element
                String text = textViewChildren.get(0).getText();
                if (text == null || text.isEmpty()) {
                    logger.warn("The text of the child element is empty.");
                    return null;
                }

                // Use getCharPosition to determine the row and column index
                int[] position = getCharPosition(text.charAt(0)); // Assuming single character per TextView
                if (position == null) {
                    logger.warn("Character '{}' is not in the keyboard layout.", text.charAt(0));
                }
                return position;

            } else if ("android.widget.Button".equals(className)) {
                // Handle Button elements (assumed to be the SPACE button)
                logger.info("Detected active element as SPACE button.");
                return new int[]{6, 0}; // Fixed position for SPACE: Row 6, Column 0

            } else {
                // Unsupported className
                logger.warn("Unsupported active element className: {}", className);
                return null;
            }

        } catch (Exception e) {
            logger.error("Error occurred while getting the active element position: {}", e.getMessage(), e);
            return null;
        }
    }
}
