package org.bankautomation.api;

import io.restassured.response.Response;
import org.testng.Assert;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountsApiTest {

    private ApiSession session;

    @BeforeMethod
    public void setUp() {
        session = new ApiSession();
        session.loginAsTestUser();
    }

    @Test(groups = {"api", "smoke"})
    public void shouldReturnUserAccounts() {

        Response response =
                ApiClient.getAccounts(
                        session.getSessionId()
                );

        response.then()
                .statusCode(200);

        System.out.println(
                "Accounts response:"
        );

        response.prettyPrint();

        Assert.assertNotNull(
                response.jsonPath().getList("$"),
                "Accounts response should not be null"
        );
    }

    @Test(groups = {"api", "smoke"})
    public void shouldReturnChequeAccount() {

        ApiSession apiSession = new ApiSession();
        apiSession.loginAsTestUser();

        Response response =
                ApiClient.getAccounts(
                        apiSession.getSessionId()
                );

        response.then()
                .statusCode(200);

        List<String> accountNumbers =
                response.jsonPath()
                        .getList("accountNumber");

        Assert.assertTrue(
                accountNumbers.contains("1000000001"),
                "Cheque account 1000000001 should exist"
        );
    }

    @Test(groups = {"api", "smoke"})
    public void shouldReturnSavingsAccount() {

        ApiSession apiSession = new ApiSession();
        apiSession.loginAsTestUser();

        Response response =
                ApiClient.getAccounts(
                        apiSession.getSessionId()
                );

        response.then()
                .statusCode(200);

        List<String> accountNumbers =
                response.jsonPath()
                        .getList("accountNumber");

        Assert.assertTrue(
                accountNumbers.contains("1000000002"),
                "Savings account 1000000002 should exist"
        );
    }
}