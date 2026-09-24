package org.bankautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TransactionPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle =
            By.xpath("//h1[normalize-space()='Transactions']");

    private final By transactionsNavigation =
            By.xpath("//*[normalize-space()='Transactions']");

    private final By accountSelect =
            By.id("transaction-account");

    private final By accountBalance =
            By.cssSelector(".transaction-account-balance");

    private final By transactionRows =
            By.cssSelector(".transactions-table tbody tr");

    private final By transactionIds =
            By.cssSelector(
                    ".transactions-table tbody td[data-testid^='transaction-id-']"
            );

    private final By transactionAmounts =
            By.cssSelector(
                    ".transactions-table tbody td[data-testid^='transaction-amount-']"
            );

    private final By transactionStatuses =
            By.cssSelector(
                    ".transactions-table tbody [data-testid^='transaction-status-']"
            );

    public TransactionPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    public void openTransactionsPage() {

        wait.until(
                driver ->
                        driver.findElement(
                                transactionsNavigation
                        ).isDisplayed()
        );

        driver.findElement(
                transactionsNavigation
        ).click();

        wait.until(
                driver ->
                        driver.findElement(pageTitle)
                                .getText()
                                .equals("Transactions")
        );
    }

    public boolean isPageDisplayed() {

        return wait.until(
                driver ->
                        driver.findElement(pageTitle)
                                .getText()
                                .equals("Transactions")
        );
    }

    public void selectAccount(String accountId) {

        Select select = new Select(
                wait.until(
                        driver ->
                                driver.findElement(accountSelect)
                )
        );

        select.selectByValue(accountId);
    }

    public String getSelectedAccountBalance() {

        return wait.until(
                driver -> {

                    List<WebElement> elements =
                            driver.findElements(accountBalance);

                    if (elements.isEmpty()) {
                        return null;
                    }

                    String text = elements.get(0).getText();

                    if (text == null || text.trim().isEmpty()) {
                        return null;
                    }

                    return text;
                }
        );
    }

    public int getTransactionCount() {

        wait.until(
                driver ->
                        !driver.findElements(transactionRows)
                                .isEmpty()
        );

        return driver.findElements(transactionRows).size();
    }

    public List<WebElement> getTransactionRows() {

        return wait.until(
                driver ->
                        driver.findElements(transactionRows)
        );
    }

    public List<WebElement> getTransactionIds() {

        return wait.until(
                driver ->
                        driver.findElements(transactionIds)
        );
    }

    public List<WebElement> getTransactionAmounts() {

        return wait.until(
                driver ->
                        driver.findElements(transactionAmounts)
        );
    }

    public List<WebElement> getTransactionStatuses() {

        return wait.until(
                driver ->
                        driver.findElements(transactionStatuses)
        );
    }

    public String getTransactionId(int index) {

        return getTransactionIds()
                .get(index)
                .getText();
    }

    public String getTransactionAmount(int index) {

        return getTransactionAmounts()
                .get(index)
                .getText();
    }

    public String getTransactionStatus(int index) {

        return getTransactionStatuses()
                .get(index)
                .getText();
    }

    public String getTransactionType(int rowIndex) {

        return getTransactionRows()
                .get(rowIndex)
                .findElements(By.tagName("td"))
                .get(1)
                .getText();
    }

    public String getFromAccount(int rowIndex) {

        return getTransactionRows()
                .get(rowIndex)
                .findElements(By.tagName("td"))
                .get(2)
                .getText();
    }

    public String getToAccount(int rowIndex) {

        return getTransactionRows()
                .get(rowIndex)
                .findElements(By.tagName("td"))
                .get(3)
                .getText();
    }

    public String getReference(int rowIndex) {

        return getTransactionRows()
                .get(rowIndex)
                .findElements(By.tagName("td"))
                .get(5)
                .getText();
    }
}