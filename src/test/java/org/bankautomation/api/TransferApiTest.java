package org.bankautomation.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TransferApiTest {

    private ApiSession session;

    @BeforeMethod
    public void setUp() {

        session = new ApiSession();

        session.loginAsTestUser();
    }

    @Test(groups = {"api", "smoke"})
    public void shouldCompleteTransferSuccessfully() {

        Response response =
                ApiClient.transfer(
                        session.getSessionId(),
                        2L,
                        "1000000003",
                        "10.00",
                        "Automation API Test"
                );

        response.then()
                .statusCode(200);

        response.prettyPrint();

        Assert.assertEquals(
                response.jsonPath()
                        .getString("status"),
                "COMPLETED"
        );

        Assert.assertEquals(
                response.jsonPath()
                        .getString("fromAccount"),
                "1000000002"
        );

        Assert.assertEquals(
                response.jsonPath()
                        .getString("toAccount"),
                "1000000003"
        );

        java.math.BigDecimal actualAmount =
                new java.math.BigDecimal(
                        response.jsonPath().getString("amount")
                );

        java.math.BigDecimal expectedAmount =
                new java.math.BigDecimal("10.00");

        Assert.assertEquals(
                actualAmount.compareTo(expectedAmount),
                0,
                "Transfer amount should be 10.00"
        );

        Assert.assertEquals(
                response.jsonPath()
                        .getString("reference"),
                "Automation API Test"
        );

        Assert.assertNotNull(
                response.jsonPath()
                        .getLong("transactionId"),
                "Transaction ID should be returned"
        );
    }

    @Test(groups = {"api", "regression"})
    public void shouldRejectInsufficientFunds() {

        Response response =
                ApiClient.transfer(
                        session.getSessionId(),
                        1L,
                        "1000000003",
                        "999999999.00",
                        "Insufficient Funds API Test"
                );

        response.then()
                .statusCode(400);

        Assert.assertEquals(
                response.jsonPath()
                        .getString("message"),
                "Insufficient funds"
        );
    }

    @Test(groups = {"api", "regression"})
    public void shouldRejectSameAccountTransfer() {

        Response response =
                ApiClient.transfer(
                        session.getSessionId(),
                        1L,
                        "1000000001",
                        "10.00",
                        "Same Account API Test"
                );

        response.then()
                .statusCode(400);

        Assert.assertEquals(
                response.jsonPath()
                        .getString("message"),
                "Cannot transfer money to the same account"
        );
    }

    @Test(groups = {"api", "regression"})
    public void shouldRejectInvalidRecipientAccount() {

        Response response =
                ApiClient.transfer(
                        session.getSessionId(),
                        1L,
                        "9999999999",
                        "10.00",
                        "Invalid Recipient API Test"
                );

        response.then()
                .statusCode(404);

        Assert.assertEquals(
                response.jsonPath()
                        .getString("message"),
                "Destination account not found"
        );
    }
}