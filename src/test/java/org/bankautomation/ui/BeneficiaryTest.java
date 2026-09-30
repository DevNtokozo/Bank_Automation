
        package org.bankautomation.ui;

import org.bankautomation.pages.BeneficiaryPage;
import org.bankautomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeneficiaryTest extends BaseTest {

    @Test(groups = {"ui", "smoke"})
    public void shouldDisplayBeneficiariesPage() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        BeneficiaryPage beneficiaryPage =
                new BeneficiaryPage(driver);

        beneficiaryPage.openBeneficiariesPage();

        Assert.assertTrue(
                beneficiaryPage.isPageDisplayed(),
                "Beneficiaries page should be displayed"
        );
    }

    @Test(groups = {"ui"})
    public void shouldDisplayExistingBeneficiary() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        BeneficiaryPage beneficiaryPage =
                new BeneficiaryPage(driver);

        beneficiaryPage.openBeneficiariesPage();

        // Account 1 contains the seeded QA Recipient beneficiary
        beneficiaryPage.selectAccount("1");

        Assert.assertTrue(
                beneficiaryPage.isBeneficiaryDisplayed(
                        "1000000003"
                ),
                "Recipient account should be displayed as a beneficiary"
        );

        Assert.assertTrue(
                beneficiaryPage.isBeneficiaryNameDisplayed(
                        "QA Recipient"
                ),
                "QA Recipient should be displayed"
        );
    }

    @Test(groups = {"ui"})
    public void shouldDisplayBeneficiaryCount() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        BeneficiaryPage beneficiaryPage =
                new BeneficiaryPage(driver);

        beneficiaryPage.openBeneficiariesPage();

        // Account 1 contains the seeded beneficiary
        beneficiaryPage.selectAccount("1");

        int count =
                beneficiaryPage.getBeneficiaryCount();

        System.out.println(
                "Beneficiary count: " + count
        );

        Assert.assertTrue(
                count > 0,
                "At least one beneficiary should be displayed"
        );
    }

    @Test(groups = {"ui"})
    public void shouldDisplayDeleteButton() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        BeneficiaryPage beneficiaryPage =
                new BeneficiaryPage(driver);

        beneficiaryPage.openBeneficiariesPage();

        // Account 1 contains the seeded beneficiary
        beneficiaryPage.selectAccount("1");

        Assert.assertTrue(
                beneficiaryPage.isDeleteButtonDisplayed(),
                "Delete button should be displayed"
        );
    }

    @Test(groups = {"ui"})
    public void shouldAddBeneficiarySuccessfully() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.loginAsTestUser();

        BeneficiaryPage beneficiaryPage =
                new BeneficiaryPage(driver);

        beneficiaryPage.openBeneficiariesPage();

        // Account 2 is used because account 1 already
        // contains the seeded QA Recipient beneficiary.
        beneficiaryPage.selectAccount("2");

        String beneficiaryName =
                "Automation Beneficiary "
                        + System.currentTimeMillis();

        beneficiaryPage.enterName(
                beneficiaryName
        );

        beneficiaryPage.enterAccountNumber(
                "1000000003"
        );

        beneficiaryPage.enterBankName(
                "Dev Bank"
        );

        beneficiaryPage.clickAddBeneficiary();

        Assert.assertTrue(
                beneficiaryPage.isSuccessMessageDisplayed(),
                "Beneficiary should be added successfully"
        );

        Assert.assertTrue(
                beneficiaryPage.isBeneficiaryWithNameDisplayed(
                        beneficiaryName
                ),
                "Created beneficiary should appear in the list"
        );

        // ==========================================
        // Cleanup test data
        // ==========================================

        beneficiaryPage.deleteBeneficiaryByName(
                beneficiaryName
        );

        Assert.assertFalse(
                beneficiaryPage.isBeneficiaryWithNameDisplayed(
                        beneficiaryName
                ),
                "Created beneficiary should be deleted during test cleanup"
        );
    }
}

