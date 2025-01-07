package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class ElementUtils {

    /**
     * Finds a single element based on the locator strategy.
     * @param driver AndroidDriver instance
     * @param locator locator string in the format <strategy>=<value>
     * @return WebElement or null if element is not found.
     */
    public static WebElement findElement(AndroidDriver driver, String locator) {
        WebElement element = null;

        try {
            if (locator.startsWith("id=")) {
                String id = locator.substring(3); // Extract the ID part
                element = driver.findElement(By.id(id));
            } else if (locator.startsWith("xpath=")) {
                String xpath = locator.substring(6); // Extract the XPath part
                element = driver.findElement(By.xpath(xpath));
            } else if (locator.startsWith("name=")) {
                String name = locator.substring(5); // Extract the Name part
                element = driver.findElement(By.name(name));
            } else if (locator.startsWith("className=")) {
                String className = locator.substring(10); // Extract the Class Name part
                element = driver.findElement(By.className(className));
            } else if (locator.startsWith("accessibilityId=")) {
                String accessibilityId = locator.substring(16); // Extract the Accessibility ID part
                element = driver.findElement(AppiumBy.accessibilityId(accessibilityId));
            } else if (locator.startsWith("uiautomator=")) {
                String uiAutomator = locator.substring(12); // Extract the UIAutomator part
                element = driver.findElement(AppiumBy.androidUIAutomator(uiAutomator));
            } else {
                throw new IllegalArgumentException("Unsupported locator strategy: " + locator);
            }
        } catch (Exception e) {
            System.err.println("Error finding element with locator: " + locator);
//            e.printStackTrace(); // Or use a logger to log the error for debugging
        }

        return element;
    }

    /**
     * Finds a list of elements based on the locator strategy.
     * @param driver AndroidDriver instance
     * @param locator locator string in the format <strategy>=<value>
     * @return List of WebElements, or an empty list if no elements are found.
     */
    public static List<WebElement> findElements(AndroidDriver driver, String locator) {
        List<WebElement> elements = null;

        try {
            if (locator.startsWith("id=")) {
                String id = locator.substring(3); // Extract the ID part
                elements = driver.findElements(By.id(id));
            } else if (locator.startsWith("xpath=")) {
                String xpath = locator.substring(6); // Extract the XPath part
                elements = driver.findElements(By.xpath(xpath));
            } else if (locator.startsWith("name=")) {
                String name = locator.substring(5); // Extract the Name part
                elements = driver.findElements(By.name(name));
            } else if (locator.startsWith("className=")) {
                String className = locator.substring(10); // Extract the Class Name part
                elements = driver.findElements(By.className(className));
            } else if (locator.startsWith("accessibilityId=")) {
                String accessibilityId = locator.substring(16); // Extract the Accessibility ID part
                elements = driver.findElements(AppiumBy.accessibilityId(accessibilityId));
            } else if (locator.startsWith("uiautomator=")) {
                String uiAutomator = locator.substring(12); // Extract the UIAutomator part
                elements = driver.findElements(AppiumBy.androidUIAutomator(uiAutomator));
            } else {
                throw new IllegalArgumentException("Unsupported locator strategy: " + locator);
            }
        } catch (Exception e) {
            System.err.println("Error finding elements with locator: " + locator);
        }

        return (elements != null && !elements.isEmpty()) ? elements : Collections.emptyList(); // Return empty list if no elements found
    }

    public static List<WebElement> getFirstChildElement(WebElement element) {
        try {
            // Use XPath to find the first child element
            return element.findElements(By.xpath("./*"));
        } catch (NoSuchElementException e) {
            System.out.println("No child elements found in the menu element.");
            return null;
        }
    }
}

