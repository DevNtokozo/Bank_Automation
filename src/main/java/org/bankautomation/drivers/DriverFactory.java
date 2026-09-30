package org.bankautomation.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static void initializeDriver() {

        String browser =
                ConfigReader.get("browser");

        boolean headless =
                ConfigReader.getBoolean("headless");

        if (!browser.equalsIgnoreCase("chrome")) {

            throw new RuntimeException(
                    "Unsupported browser: " + browser
            );
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options =
                new ChromeOptions();

        // Headless mode
        if (headless) {

            options.addArguments(
                    "--headless=new"
            );
        }

        // Browser configuration
        options.addArguments(
                "--start-maximized"
        );

        options.addArguments(
                "--disable-notifications"
        );

        // Disable Chrome password manager prompts
        Map<String, Object> preferences =
                new HashMap<>();

        preferences.put(
                "credentials_enable_service",
                false
        );

        preferences.put(
                "profile.password_manager_enabled",
                false
        );

        // Disable password leak detection warning
        preferences.put(
                "profile.password_manager_leak_detection",
                false
        );

        options.setExperimentalOption(
                "prefs",
                preferences
        );

        // Create browser
        WebDriver webDriver =
                new ChromeDriver(options);

        // Implicit wait
        webDriver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(
                                ConfigReader.getInt(
                                        "implicit.wait"
                                )
                        )
                );

        // Page load timeout
        webDriver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(
                                ConfigReader.getInt(
                                        "page.load.timeout"
                                )
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