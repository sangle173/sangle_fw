package org.example.framework;

import io.appium.java_client.android.AndroidDriver;
import org.example.driver_manager.AppiumDriverManager;
import org.testng.annotations.*;

public class BaseTest {
    protected AndroidDriver driver;

    @BeforeClass
    public void setUp() {
        driver = AppiumDriverManager.getDriver();
    }

    @AfterClass
    public void tearDown() {
        AppiumDriverManager.quitDriver();
    }
}