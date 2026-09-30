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

    // ==============================
    // Page Locators
    // ==============================

    private final By beneficiariesHeading =
            By.xpath("//h1[normalize-space()='Beneficiaries']");

    private final By beneficiaryNavigation =
            By.xpath("//*[normalize-space()='Beneficiaries']");

    private final By accountSelect =
            By.cssSelector(
                    "[data-testid='beneficiary-account']"
            );

    private final By beneficiaryName =
            By.cssSelector(
                    "[data-testid='beneficiary-name']"
            );

    private final By beneficiaryAccountNumber =
            By.cssSelector(
                    "[data-testid='beneficiary-account-number']"
            );

    private final By beneficiaryBank =
            By.cssSelector(
                    "[data-testid='beneficiary-bank']"
            );

    private final By addBeneficiaryButton =
            By.cssSelector(
                    "[data-testid='add-beneficiary-button']"
            );

    private final By beneficiaryItems =
            By.cssSelector(
                    ".beneficiary-item"
            );

    private final By deleteButtons =
            By.cssSelector(
                    ".delete-beneficiary-button"
            );

    private final By beneficiarySuccess =
            By.cssSelector(
                    "[data-testid='beneficiary-success']"
            );

    private final By beneficiaryError =
            By.cssSelector(
                    "[data-testid='beneficiary-error']"
            );

    // ==============================
    // Constructor
    // ==============================

    public BeneficiaryPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt(
                                "explicit.wait"
                        )
                )
        );
    }

    // ==============================
    // Navigation
    // ==============================

    public void openBeneficiariesPage() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        beneficiaryNavigation
                )
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiariesHeading
                )
        );
    }

    public boolean isPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiariesHeading
                )
        ).isDisplayed();
    }

    // ==============================
    // Account Selection
    // ==============================

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

        wait.until(
                ExpectedConditions.attributeToBe(
                        accountSelect,
                        "value",
                        accountId
                )
        );
    }

    // ==============================
    // Form Fields
    // ==============================

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

    // ==============================
    // Add Beneficiary
    // ==============================

    public void clickAddBeneficiary() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addBeneficiaryButton
                )
        ).click();
    }

    public void addBeneficiary(
            String accountId,
            String name,
            String accountNumber,
            String bankName) {

        selectAccount(accountId);

        enterName(name);

        enterAccountNumber(
                accountNumber
        );

        enterBankName(
                bankName
        );

        clickAddBeneficiary();
    }

    // ==============================
    // Success Message
    // ==============================

    public boolean isSuccessMessageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiarySuccess
                )
        ).isDisplayed();
    }

    public String getSuccessMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiarySuccess
                )
        ).getText();
    }

    // ==============================
    // Error Message
    // ==============================

    public boolean isErrorMessageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiaryError
                )
        ).isDisplayed();
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiaryError
                )
        ).getText();
    }

    // ==============================
    // Beneficiary List
    // ==============================

    public int getBeneficiaryCount() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        beneficiaryItems
                )
        );

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

    // ==============================
    // Delete Beneficiary
    // ==============================

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

    // ==============================
    // Find Specific Beneficiary
    // ==============================

    public boolean isBeneficiaryWithNameDisplayed(
            String name) {

        By beneficiary =
                By.xpath(
                        "//div[contains(@class,'beneficiary-item')]" +
                                "//h3[normalize-space()='" +
                                name +
                                "']"
                );

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            beneficiary
                    )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    public void deleteBeneficiaryByName(
            String name) {

        By deleteButton =
                By.xpath(
                        "//div[contains(@class,'beneficiary-item')]" +
                                "[.//h3[normalize-space()='" +
                                name +
                                "']]" +
                                "//button[contains(@class," +
                                "'delete-beneficiary-button')]"
                );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButton
                )
        ).click();
    }

    // ==============================
    // Page Text
    // ==============================

    public String getPageText() {

        return driver.findElement(
                By.tagName("body")
        ).getText();
    }
}