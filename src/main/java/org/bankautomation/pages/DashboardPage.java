package org.bankautomation.pages;

import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeading =
            By.xpath("//*[normalize-space()='Dashboard']");

    private final By welcomeMessage =
            By.xpath("//*[contains(normalize-space(), 'Welcome,')]");

    private final By totalBalance =
            By.xpath("//*[normalize-space()='Total Balance']");

    private final By yourAccounts =
            By.xpath("//*[normalize-space()='Your Accounts']");

    public DashboardPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("explicit.wait")
                )
        );
    }

    public boolean isDashboardDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        dashboardHeading
                )
        ).isDisplayed();
    }

    public boolean isWelcomeMessageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        welcomeMessage
                )
        ).isDisplayed();
    }

    public boolean isTotalBalanceDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        totalBalance
                )
        ).isDisplayed();
    }

    public boolean areAccountsDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        yourAccounts
                )
        ).isDisplayed();
    }

    public String getWelcomeMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        welcomeMessage
                )
        ).getText();
    }
}