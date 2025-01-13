package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class ElementUtils {

    private static final Logger logger = LogManager.getLogger(ElementUtils.class);

    /**
     * Finds a single element based on the locator strategy.
     *
     * @param driver  AndroidDriver instance
     * @param locator locator string in the format <strategy>=<value>
     * @return WebElement or null if the element is not found.
     */
    public static WebElement findElement(AndroidDriver driver, String locator) {
        WebElement element = null;

        try {
            if (locator.startsWith("id=")) {
                String id = locator.substring(3); // Extract the ID part
                element = driver.findElement(AppiumBy.accessibilityId(id));
            } else if (locator.startsWith("xpath=")) {
                String xpath = locator.substring(6); // Extract the XPath part
                element = driver.findElement(AppiumBy.xpath(xpath));
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
                logger.error("Unsupported locator strategy: {}", locator);
            }
        } catch (Exception e) {
            logger.error("Error finding element with locator.");
        }

        return element;
    }

    /**
     * Finds a list of elements based on the locator strategy.
     *
     * @param driver  AndroidDriver instance
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
                logger.error("Unsupported locator strategy: {}", locator);
            }
        } catch (Exception e) {
            logger.error("Error finding elements with locator '{}': {}", locator, e.getMessage(), e);
        }

        return (elements != null && !elements.isEmpty()) ? elements : Collections.emptyList(); // Return empty list if no elements found
    }

    /**
     * Retrieves the first child elements of a given element.
     *
     * @param element The WebElement whose child elements are to be retrieved.
     * @return List of WebElements or null if no child elements are found.
     */
    public static List<WebElement> getFirstChildElement(WebElement element) {
        try {
            // Use XPath to find the first child element
            return element.findElements(By.xpath("./*"));
        } catch (NoSuchElementException e) {
            logger.warn("No child elements found in the provided element: {}", e.getMessage());
            return null;
        }
    }

    public WebElement getActiveElementInWebView(AndroidDriver driver) {
        WebElement activeElement = null;
        try {
            // Switch to the WebView context
            Set<String> contexts = driver.getContextHandles();
            for (String context : contexts) {
                if (context.contains("WEBVIEW")) {
                    driver.context(context);
                    logger.info("Switched to WebView context: {}", context);
                    break;
                }
            }

            // Execute JavaScript to get the active element
            activeElement = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.activeElement");

            // Log or interact with the active element
            if (activeElement != null) {
                logger.info("Active element tag name: {}", activeElement.getTagName());
                logger.info("Active element text: {}", activeElement.getText());
            } else {
                logger.warn("No active element found in the WebView.");
            }

            // Switch back to the native context
            driver.context("NATIVE_APP");
            logger.info("Switched back to Native context.");
        } catch (Exception e) {
            logger.error("Error while getting the active element in WebView: {}", e.getMessage(), e);
        }
        return activeElement;
    }

}
