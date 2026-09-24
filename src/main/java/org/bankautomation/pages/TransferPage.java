package org.bankautomation.pages;

import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By transferHeading =
            By.xpath("//*[normalize-space()='Transfer Money']");

    private final By sourceAccount =
            By.cssSelector("[data-testid='source-account']");

    private final By recipientAccount =
            By.cssSelector("[data-testid='recipient-account']");

    private final By transferAmount =
            By.cssSelector("[data-testid='transfer-amount']");

    private final By transferReference =
            By.cssSelector("[data-testid='transfer-reference']");

    private final By transferButton =
            By.cssSelector("[data-testid='transfer-submit']");

    private final By transferSuccess =
            By.cssSelector("[data-testid='transfer-success']");

    private final By transferError =
            By.cssSelector("[data-testid='transfer-error']");

    public TransferPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("explicit.wait")
                )
        );
    }

    public boolean isTransferPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferHeading
                )
        ).isDisplayed();
    }

    public void openTransferPage() {

        By transferNavigation =
                By.xpath("//*[normalize-space()='Transfer']");

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        transferNavigation
                )
        ).click();
    }

    public TransferPage selectSourceAccountByValue(
            String accountId) {

        Select select = new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                sourceAccount
                        )
                )
        );

        select.selectByValue(accountId);

        return this;
    }

    public TransferPage enterRecipientAccount(
            String accountNumber) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        recipientAccount
                )
        ).sendKeys(accountNumber);

        return this;
    }

    public TransferPage enterAmount(
            String amount) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferAmount
                )
        ).sendKeys(amount);

        return this;
    }

    public TransferPage enterReference(
            String reference) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferReference
                )
        ).sendKeys(reference);

        return this;
    }

    public void clickTransfer() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        transferButton
                )
        ).click();
    }

    public void transfer(
            String sourceAccountId,
            String recipientAccount,
            String amount,
            String reference) {

        selectSourceAccountByValue(sourceAccountId);
        enterRecipientAccount(recipientAccount);
        enterAmount(amount);
        enterReference(reference);
        clickTransfer();
    }

    public boolean isTransferSuccessful() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferSuccess
                )
        ).isDisplayed();
    }

    public boolean isTransferErrorDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferError
                )
        ).isDisplayed();
    }

    public String getSuccessMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferSuccess
                )
        ).getText();
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        transferError
                )
        ).getText();
    }


}