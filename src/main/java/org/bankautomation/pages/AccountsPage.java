package org.bankautomation.pages;

import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By accountsHeading =
            By.xpath("//*[normalize-space()='Accounts']");

    public AccountsPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("explicit.wait")
                )
        );
    }

    public boolean isAccountsPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        accountsHeading
                )
        ).isDisplayed();
    }

    public void openAccountsPage() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        accountsHeading
                )
        ).click();
    }

    public boolean isAccountNumberDisplayed(
            String accountNumber) {

        By accountLocator = By.xpath(
                "//*[contains(normalize-space(), '" +
                        accountNumber +
                        "')]"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        accountLocator
                )
        ).isDisplayed();
    }

    public boolean isAccountTypeDisplayed(
            String accountType) {

        By accountTypeLocator = By.xpath(
                "//*[normalize-space()='" +
                        accountType +
                        "']"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        accountTypeLocator
                )
        ).isDisplayed();
    }

    public String getPageText() {

        return driver.findElement(
                By.tagName("body")
        ).getText();
    }
}