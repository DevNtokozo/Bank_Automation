package org.bankautomation.ui;

import org.bankautomation.config.ConfigReader;
import org.bankautomation.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        DriverFactory.initializeDriver();

        driver = DriverFactory.getDriver();

        driver.get(
                ConfigReader.get("base.url")
        );
    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}