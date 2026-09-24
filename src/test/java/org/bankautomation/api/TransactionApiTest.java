package org.bankautomation.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class TransactionApiTest {

    private String sessionId;

    @BeforeClass
    public void login() {

        Response response = ApiClient.login(
                "testuser",
                "Test@123"
        );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Login should be successful"
        );

        sessionId = response.getDetailedCookie("JSESSIONID").getValue();

        Assert.assertNotNull(
                sessionId,
                "Session ID should not be null"
        );
    }

    @Test
    public void shouldDisplayAccountTransactions() {

        Response response =
                ApiClient.getAccountTransactions(
                        sessionId,
                        2L
                );

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertNotNull(
                response.jsonPath().getList("$"),
                "Transaction list should not be null"
        );

        Assert.assertTrue(
                response.jsonPath().getList("$").size() > 0,
                "Account should contain transactions"
        );
    }

    @Test
    public void shouldDisplayTransactionDetails() {

        Response response =
                ApiClient.getTransaction(
                        sessionId,
                        14L
                );

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertEquals(
                response.jsonPath().getLong("id"),
                14L
        );

        Assert.assertEquals(
                response.jsonPath().getString("transactionType"),
                "TRANSFER"
        );

        Assert.assertEquals(
                response.jsonPath().getString("fromAccount"),
                "1000000002"
        );

        Assert.assertEquals(
                response.jsonPath().getString("toAccount"),
                "1000000003"
        );

        Assert.assertEquals(
                new BigDecimal(
                        response.jsonPath().getString("amount")
                ).compareTo(
                        new BigDecimal("10.00")
                ),
                0,
                "Transaction amount should be 10.00"
        );

        Assert.assertEquals(
                response.jsonPath().getString("reference"),
                "Automation API Test"
        );

        Assert.assertEquals(
                response.jsonPath().getString("status"),
                "COMPLETED"
        );
    }

    @Test
    public void shouldRejectUnknownTransaction() {

        Response response =
                ApiClient.getTransaction(
                        sessionId,
                        999999L
                );

        Assert.assertEquals(
                response.statusCode(),
                404
        );
    }

    @Test
    public void shouldRejectUnauthorisedAccountAccess() {

        Response response =
                ApiClient.getAccountTransactions(
                        "invalid-session-id",
                        2L
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }

    @Test
    public void shouldRejectUnauthorisedTransactionAccess() {

        Response response =
                ApiClient.getTransaction(
                        "invalid-session-id",
                        14L
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }
}