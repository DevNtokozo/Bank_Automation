package org.bankautomation.e2e;

import io.restassured.response.Response;
import org.bankautomation.api.ApiClient;
import org.bankautomation.api.ApiSession;
import org.bankautomation.database.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BeneficiaryDatabaseValidationTest {

    @Test(groups = {"e2e", "database"})
    public void shouldValidateBeneficiaryPersistenceAcrossApiAndDatabase() {

        Long accountId = 1L;

        String beneficiaryAccountNumber =
                "1000000003";

        ApiSession apiSession =
                new ApiSession();

        apiSession.loginAsTestUser();

        /*
         * Get beneficiaries from the API.
         */
        Response response =
                ApiClient.getBeneficiaries(
                        apiSession.getSessionId(),
                        accountId
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Beneficiary API request should return HTTP 200"
        );

        System.out.println(
                "Beneficiary API response: "
                        + response.asString()
        );

        /*
         * Verify the known beneficiary exists
         * in the API response.
         */
        List<String> accountNumbers =
                response.jsonPath()
                        .getList("accountNumber");

        Assert.assertTrue(
                accountNumbers.contains(
                        beneficiaryAccountNumber
                ),
                "Beneficiary account "
                        + beneficiaryAccountNumber
                        + " should exist in API response"
        );

        /*
         * Verify beneficiaries exist in database.
         */
        int beneficiaryCount =
                DatabaseUtils.getBeneficiaryCount(
                        accountId
                );

        Assert.assertTrue(
                beneficiaryCount > 0,
                "Database should contain at least one beneficiary"
        );

        System.out.println(
                "Beneficiaries in database: "
                        + beneficiaryCount
        );

        /*
         * Find the beneficiary ID from the API response.
         */
        List<Integer> ids =
                response.jsonPath()
                        .getList("id");

        Long beneficiaryId = null;

        for (int i = 0; i < accountNumbers.size(); i++) {

            if (beneficiaryAccountNumber.equals(
                    accountNumbers.get(i)
            )) {

                beneficiaryId =
                        ids.get(i).longValue();

                break;
            }
        }

        Assert.assertNotNull(
                beneficiaryId,
                "Beneficiary ID should be returned by API"
        );

        System.out.println(
                "Verified beneficiary ID: "
                        + beneficiaryId
        );

        /*
         * Verify the same beneficiary exists
         * in PostgreSQL.
         */
        Assert.assertTrue(
                DatabaseUtils.beneficiaryExists(
                        beneficiaryId
                ),
                "Beneficiary should exist in database"
        );

        System.out.println(
                "Beneficiary API and database validation passed"
        );
    }
}