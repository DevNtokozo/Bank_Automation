package org.bankautomation.pages;

import org.bankautomation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BeneficiaryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageHeading =
            By.xpath("//h1[normalize-space()='Beneficiaries']");

    private final By beneficiaryNavigation =
            By.xpath("//*[normalize-space()='Beneficiaries']");

    private final By accountSelect =
            By.cssSelector("[data-testid='beneficiary-account']");

    private final By beneficiaryName =
            By.cssSelector("[data-testid='beneficiary-name']");

    private final By beneficiaryAccountNumber =
            By.cssSelector(
                    "[data-testid='beneficiary-account-number']"
            );

    private final By beneficiaryBank =
            By.cssSelector("[data-testid='beneficiary-bank']");

    private final By addBeneficiaryButton =
            By.cssSelector(
                    "[data-testid='add-beneficiary-button']"
            );

    private final By beneficiaryItems =
            By.cssSelector(".beneficiary-item");

    private final By deleteButtons =
            By.cssSelector(".delete-beneficiary-button");

    public BeneficiaryPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("explicit.wait")
                )
        );
    }

    public void openBeneficiariesPage() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        beneficiaryNavigation
                )
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        pageHeading
                )
        );
    }

    public boolean isPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        pageHeading
                )
        ).isDisplayed();
    }

    public void selectAccount(String accountId) {

        Select select =
                new Select(
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        accountSelect
                                )
                        )
                );

        select.selectByValue(accountId);
    }

    public void enterName(String name) {

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                beneficiaryName
                        )
                );

        field.clear();
        field.sendKeys(name);
    }

    public void enterAccountNumber(
            String accountNumber) {

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                beneficiaryAccountNumber
                        )
                );

        field.clear();
        field.sendKeys(accountNumber);
    }

    public void enterBankName(String bankName) {

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                beneficiaryBank
                        )
                );

        field.clear();
        field.sendKeys(bankName);
    }

    public void clickAddBeneficiary() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addBeneficiaryButton
                )
        ).click();
    }

    public void addBeneficiary(
            String name,
            String accountNumber,
            String bankName) {

        enterName(name);
        enterAccountNumber(accountNumber);
        enterBankName(bankName);
        clickAddBeneficiary();
    }

    public int getBeneficiaryCount() {

        return driver.findElements(
                beneficiaryItems
        ).size();
    }

    public List<WebElement> getBeneficiaries() {

        return driver.findElements(
                beneficiaryItems
        );
    }

    public boolean isBeneficiaryDisplayed(
            String accountNumber) {

        By beneficiary =
                By.xpath(
                        "//*[contains(normalize-space(), '" +
                                accountNumber +
                                "')]"
                );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiary
                )
        ).isDisplayed();
    }

    public boolean isBeneficiaryNameDisplayed(
            String name) {

        By nameLocator =
                By.xpath(
                        "//div[contains(@class,'beneficiary-item')]" +
                                "//h3[normalize-space()='" +
                                name +
                                "']"
                );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        nameLocator
                )
        ).isDisplayed();
    }

    public boolean isDeleteButtonDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        deleteButtons
                )
        ).isDisplayed();
    }

    public void deleteFirstBeneficiary() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButtons
                )
        ).click();
    }

    public String getPageText() {

        return driver.findElement(
                By.tagName("body")
        ).getText();
    }
}