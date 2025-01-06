package base;

import org.example.driver_manager.SeleniumDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class SeleniumBaseTest {
    @BeforeMethod
    public void setUp() {
        SeleniumDriverManager.getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        SeleniumDriverManager.quitDriver();
    }
}
