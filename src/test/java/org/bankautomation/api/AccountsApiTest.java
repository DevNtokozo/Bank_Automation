package org.bankautomation.api;

import io.restassured.response.Response;
import org.testng.Assert;
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

    @Test(groups = {"api"})
    public void shouldReturnChequeAccount() {

        Response response =
                ApiClient.getAccounts(
                        session.getSessionId()
                );

        response.then()
                .statusCode(200);

        String accountNumber =
                response.jsonPath()
                        .getString(
                                "[0].accountNumber"
                        );

        Assert.assertEquals(
                accountNumber,
                "1000000001"
        );
    }

    @Test(groups = {"api"})
    public void shouldReturnSavingsAccount() {

        Response response =
                ApiClient.getAccounts(
                        session.getSessionId()
                );

        response.then()
                .statusCode(200);

        String accountNumber =
                response.jsonPath()
                        .getString(
                                "[1].accountNumber"
                        );

        Assert.assertEquals(
                accountNumber,
                "1000000002"
        );
    }
}