package org.bankautomation.utils;

import org.bankautomation.drivers.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static String captureScreenshot(String testName) {

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            System.out.println(
                    "Screenshot not captured: WebDriver is null"
            );
            return null;
        }

        try {

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            String directory =
                    System.getProperty("user.dir")
                            + File.separator
                            + "reports"
                            + File.separator
                            + "screenshots";

            Files.createDirectories(
                    Path.of(directory)
            );

            String filePath =
                    directory
                            + File.separator
                            + testName
                            + "_"
                            + System.currentTimeMillis()
                            + ".png";

            Files.copy(
                    screenshot.toPath(),
                    Path.of(filePath)
            );

            System.out.println(
                    "Screenshot captured: "
                            + filePath
            );

            return filePath;

        } catch (IOException e) {

            System.out.println(
                    "Unable to capture screenshot: "
                            + e.getMessage()
            );

            return null;
        }
    }
}