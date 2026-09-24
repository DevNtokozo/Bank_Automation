package org.bankautomation.ui;

import org.bankautomation.pages.DashboardPage;
import org.bankautomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"ui", "smoke"})
    public void shouldLoginSuccessfully() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        DashboardPage dashboardPage =
                new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard should be displayed after successful login"
        );

        Assert.assertTrue(
                dashboardPage.isWelcomeMessageDisplayed(),
                "Welcome message should be displayed"
        );

        Assert.assertTrue(
                dashboardPage.isTotalBalanceDisplayed(),
                "Total Balance should be displayed"
        );

        Assert.assertTrue(
                dashboardPage.areAccountsDisplayed(),
                "Your Accounts section should be displayed"
        );

        Assert.assertTrue(
                dashboardPage.getWelcomeMessage()
                        .contains("testuser"),
                "Welcome message should contain testuser"
        );
    }

    @Test(groups = {"ui"})
    public void shouldRejectInvalidLogin() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                "testuser",
                "WrongPassword"
        );

        String currentUrl =
                driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("/login"),
                "Invalid login should remain on login page"
        );
    }

    @Test(groups = {"ui", "smoke"})
    public void shouldLoginAsRecipient() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsRecipient();

        DashboardPage dashboardPage =
                new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard should be displayed after recipient login"
        );

        Assert.assertTrue(
                dashboardPage.isWelcomeMessageDisplayed(),
                "Welcome message should be displayed"
        );

        Assert.assertTrue(
                dashboardPage.isTotalBalanceDisplayed(),
                "Total Balance should be displayed"
        );

        Assert.assertTrue(
                dashboardPage.areAccountsDisplayed(),
                "Your Accounts section should be displayed"
        );

        Assert.assertTrue(
                dashboardPage.getWelcomeMessage()
                        .contains("recipient"),
                "Welcome message should contain recipient"
        );
    }
}