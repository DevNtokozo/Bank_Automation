package org.bankautomation.e2e;

import io.restassured.response.Response;
import org.bankautomation.api.ApiClient;
import org.bankautomation.api.ApiSession;
import org.bankautomation.config.ConfigReader;
import org.bankautomation.database.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class TransferDatabaseValidationTest {

    @Test(groups = {"e2e", "database"})
    public void shouldPersistTransferAndUpdateBalances() {

        String sourceAccount = "1000000002";
        String destinationAccount = "1000000003";
        String amount = "10.00";
        String reference =
                "E2E Database Validation "
                        + System.currentTimeMillis();

        BigDecimal sourceBalanceBefore =
                DatabaseUtils.getAccountBalance(
                        sourceAccount
                );

        BigDecimal destinationBalanceBefore =
                DatabaseUtils.getAccountBalance(
                        destinationAccount
                );

        Assert.assertNotNull(
                sourceBalanceBefore,
                "Source account balance should exist"
        );

        Assert.assertNotNull(
                destinationBalanceBefore,
                "Destination account balance should exist"
        );

        ApiSession apiSession = new ApiSession();

        apiSession.loginAsTestUser();

        Response response =
                ApiClient.transfer(
                        apiSession.getSessionId(),
                        2L,
                        destinationAccount,
                        amount,
                        reference
                );

        response.then()
                .statusCode(200);

        String status =
                response.jsonPath()
                        .getString("status");

        Assert.assertEquals(
                status,
                "COMPLETED",
                "Transfer should be completed"
        );

        BigDecimal sourceBalanceAfter =
                DatabaseUtils.getAccountBalance(
                        sourceAccount
                );

        BigDecimal destinationBalanceAfter =
                DatabaseUtils.getAccountBalance(
                        destinationAccount
                );

        BigDecimal expectedSourceBalance =
                sourceBalanceBefore.subtract(
                        new BigDecimal(amount)
                );

        BigDecimal expectedDestinationBalance =
                destinationBalanceBefore.add(
                        new BigDecimal(amount)
                );

        Assert.assertEquals(
                sourceBalanceAfter.compareTo(
                        expectedSourceBalance
                ),
                0,
                "Source account balance should decrease by R10.00"
        );

        Assert.assertEquals(
                destinationBalanceAfter.compareTo(
                        expectedDestinationBalance
                ),
                0,
                "Destination account balance should increase by R10.00"
        );

        Long transactionId =
                response.jsonPath()
                        .getLong("transactionId");

        Assert.assertNotNull(
                transactionId,
                "Transaction ID should be returned"
        );

        Assert.assertTrue(
                DatabaseUtils.transactionExists(
                        transactionId
                ),
                "Transfer transaction should be persisted in database"
        );

        System.out.println(
                "E2E transfer transaction ID: "
                        + transactionId
        );

        System.out.println(
                "Source balance before: R"
                        + sourceBalanceBefore
        );

        System.out.println(
                "Source balance after: R"
                        + sourceBalanceAfter
        );

        System.out.println(
                "Destination balance before: R"
                        + destinationBalanceBefore
        );

        System.out.println(
                "Destination balance after: R"
                        + destinationBalanceAfter
        );
    }
}