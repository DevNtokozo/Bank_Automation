package org.bankautomation.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static void initializeDriver() {

        String browser = ConfigReader.get("browser");
        boolean headless =
                ConfigReader.getBoolean("headless");

        if (!browser.equalsIgnoreCase("chrome")) {
            throw new RuntimeException(
                    "Unsupported browser: " + browser
            );
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver webDriver =
                new ChromeDriver(options);

        webDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(
                        ConfigReader.getInt("implicit.wait")
                )
        );

        webDriver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(
                        ConfigReader.getInt("page.load.timeout")
                )
        );

        driver.set(webDriver);
    }

    public static WebDriver getDriver() {

        if (driver.get() == null) {
            throw new IllegalStateException(
                    "WebDriver has not been initialized."
            );
        }

        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}