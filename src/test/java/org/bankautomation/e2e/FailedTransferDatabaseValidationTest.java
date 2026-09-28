package org.bankautomation.e2e;

import io.restassured.response.Response;
import org.bankautomation.api.ApiClient;
import org.bankautomation.api.ApiSession;
import org.bankautomation.database.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class FailedTransferDatabaseValidationTest {

    @Test(groups = {"e2e", "database"})
    public void shouldNotChangeBalancesWhenTransferFails() {

        String sourceAccount = "1000000002";
        String destinationAccount = "1000000003";

        BigDecimal sourceBalanceBefore =
                DatabaseUtils.getAccountBalance(
                        sourceAccount
                );

        BigDecimal destinationBalanceBefore =
                DatabaseUtils.getAccountBalance(
                        destinationAccount
                );

        int transactionCountBefore =
                DatabaseUtils.getTransactionCount();

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
                        "999999999.00",
                        "Failed Transfer Database Test"
                );

        Assert.assertEquals(
                response.statusCode(),
                400,
                "Insufficient funds transfer should return HTTP 400"
        );

        BigDecimal sourceBalanceAfter =
                DatabaseUtils.getAccountBalance(
                        sourceAccount
                );

        BigDecimal destinationBalanceAfter =
                DatabaseUtils.getAccountBalance(
                        destinationAccount
                );

        int transactionCountAfter =
                DatabaseUtils.getTransactionCount();

        Assert.assertEquals(
                sourceBalanceAfter.compareTo(
                        sourceBalanceBefore
                ),
                0,
                "Source balance should not change"
        );

        Assert.assertEquals(
                destinationBalanceAfter.compareTo(
                        destinationBalanceBefore
                ),
                0,
                "Destination balance should not change"
        );

        Assert.assertEquals(
                transactionCountAfter,
                transactionCountBefore,
                "Failed transfer should not create a transaction"
        );

        System.out.println(
                "Source balance unchanged: R"
                        + sourceBalanceAfter
        );

        System.out.println(
                "Destination balance unchanged: R"
                        + destinationBalanceAfter
        );

        System.out.println(
                "Transaction count unchanged: "
                        + transactionCountAfter
        );
    }
}