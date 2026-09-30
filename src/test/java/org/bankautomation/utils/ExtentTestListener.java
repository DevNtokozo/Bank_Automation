package org.bankautomation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener
        implements ITestListener {

    private static final ExtentReports extentReports =
            ExtentManager.getInstance();

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test =
                extentReports.createTest(
                        result.getMethod().getMethodName()
                );

        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().pass(
                "Test passed successfully"
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = extentTest.get();

        if (test == null) {
            return;
        }

        test.fail(
                result.getThrowable()
        );

        String screenshotPath =
                ScreenshotUtils.captureScreenshot(
                        result.getMethod().getMethodName()
                );

        if (screenshotPath != null) {

            try {

                test.addScreenCaptureFromPath(
                        screenshotPath
                );

            } catch (Exception e) {

                test.warning(
                        "Screenshot could not be attached: "
                                + e.getMessage()
                );
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get().skip(
                "Test skipped"
        );
    }

    @Override
    public void onFinish(ITestContext context) {

        extentReports.flush();

        extentTest.remove();
    }
}