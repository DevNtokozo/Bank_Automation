package org.bankautomation.pages;

import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Login page locators
    private final By usernameInput =
            By.cssSelector("[data-testid='login-username']");

    private final By passwordInput =
            By.cssSelector("[data-testid='login-password']");

    private final By loginButton =
            By.cssSelector("[data-testid='login-button']");

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("explicit.wait")
                )
        );
    }

    public LoginPage enterUsername(String username) {

        WebElement usernameField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                usernameInput
                        )
                );

        usernameField.clear();
        usernameField.sendKeys(username);

        return this;
    }

    public LoginPage enterPassword(String password) {

        WebElement passwordField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                passwordInput
                        )
                );

        passwordField.clear();
        passwordField.sendKeys(password);

        return this;
    }

    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        loginButton
                )
        ).click();
    }

    public void login(
            String username,
            String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void loginAsTestUser() {

        login(
                ConfigReader.get("test.username"),
                ConfigReader.get("test.password")
        );
    }

    public void loginAsRecipient() {

        login(
                ConfigReader.get("recipient.username"),
                ConfigReader.get("recipient.password")
        );
    }
}