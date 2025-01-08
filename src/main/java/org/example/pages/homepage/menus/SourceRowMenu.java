package org.example.pages.homepage.menus;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.example.pages.BasePage;
import org.example.utils.NavigationUtils;
import org.example.utils.enums.Direction;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SourceRowMenu extends BasePage {

    // Constructor
    public SourceRowMenu(AndroidDriver driver) {
        super(driver);
    }


    // Add methods to interact with Source Row Menu here
    public void selectItem(String itemLocator) {
        System.out.println("Selecting item: " + itemLocator);
        // Implementation for selecting an item in the Source Row Menu
    }


    public List<String> getActiveAppOnSourceRow() throws InterruptedException {
        //Ensure start from first item
        NavigationUtils.moveToTheEnd(driver, Direction.LEFT);
        List<String> actualActiveAppsOnSourceRow = new ArrayList<>();
        // Iterate through each row
        boolean isEndOfRow = false;

        // Navigate through each app in the current row
        while (!isEndOfRow) {
            WebElement activeEle = driver.switchTo().activeElement();
            String appActiveNameOnSourceRow = null;
            List<WebElement> childOfActiveElement = activeEle.findElements(AppiumBy.xpath("//android.view.View[contains(@resource-id, 'home:app_rail:item')]"));
            if (!childOfActiveElement.isEmpty()) {
                if (!Objects.equals(childOfActiveElement.get(0).getAttribute("content-desc"), "HDMI 1") && !Objects.equals(childOfActiveElement.get(0).getAttribute("content-desc"), "HDMI 2") && !Objects.equals(childOfActiveElement.get(0).getAttribute("content-desc"), "HDMI 3")) {
                    appActiveNameOnSourceRow = childOfActiveElement.get(0).getAttribute("content-desc");
                    System.out.println(appActiveNameOnSourceRow);
                    actualActiveAppsOnSourceRow.add(appActiveNameOnSourceRow);
                }
            }

            isEndOfRow = NavigationUtils.checkEndOfRowWithoutMoving(driver, Direction.RIGHT);
        }
        return actualActiveAppsOnSourceRow;
    }

}