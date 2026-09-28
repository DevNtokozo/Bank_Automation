package org.bankautomation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static ExtentReports getInstance() {

        if (extentReports == null) {

            String reportDirectory =
                    System.getProperty("user.dir")
                            + File.separator
                            + "reports";

            File directory =
                    new File(reportDirectory);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String reportPath =
                    reportDirectory
                            + File.separator
                            + "ExtentReport.html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config().setDocumentTitle(
                    "Bank Automation Test Report"
            );

            sparkReporter.config().setReportName(
                    "Dev Bank QA Automation"
            );

            extentReports =
                    new ExtentReports();

            extentReports.attachReporter(
                    sparkReporter
            );

            extentReports.setSystemInfo(
                    "Project",
                    "Bank Automation"
            );

            extentReports.setSystemInfo(
                    "Application",
                    "Dev Bank"
            );

            extentReports.setSystemInfo(
                    "Automation",
                    "Selenium / REST Assured / JDBC"
            );

            extentReports.setSystemInfo(
                    "Framework",
                    "TestNG"
            );

            extentReports.setSystemInfo(
                    "Build Tool",
                    "Maven"
            );

            extentReports.setSystemInfo(
                    "Database",
                    "PostgreSQL"
            );
        }

        return extentReports;
    }
}