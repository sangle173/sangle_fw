package web_tests;

import org.example.driver_manager.SeleniumDriverManager;
import org.example.framework.SeleniumBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class WebSampleTest extends SeleniumBaseTest {

    @Test
    public void testGoogleSearch() {
        SeleniumDriverManager.getDriver().get("https://www.google.com");
        SeleniumDriverManager.getDriver().findElement(By.name("q")).sendKeys("Selenium WebDriver");
        SeleniumDriverManager.getDriver().findElement(By.name("btnK")).submit();
        Assert.assertTrue(SeleniumDriverManager.getDriver().getTitle().contains("Selenium asdasdas"));
        WebElement webElement = SeleniumDriverManager.getDriver().findElement(By.id(""));
    }
}
