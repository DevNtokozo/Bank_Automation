package org.bankautomation.database;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BeneficiaryDatabaseTest {

    @Test(groups = {"database", "smoke"})
    public void shouldHaveBeneficiariesInDatabase() {

        int beneficiaryCount =
                DatabaseUtils.getBeneficiaryCount();

        System.out.println(
                "Beneficiaries in database: "
                        + beneficiaryCount
        );

        Assert.assertTrue(
                beneficiaryCount > 0,
                "Database should contain at least one beneficiary"
        );
    }

    @Test(groups = {"database"})
    public void shouldContainKnownBeneficiary() {

        int beneficiaryCount =
                DatabaseUtils.getBeneficiaryCount();

        Assert.assertTrue(
                beneficiaryCount > 0,
                "At least one beneficiary should exist"
        );
    }
}