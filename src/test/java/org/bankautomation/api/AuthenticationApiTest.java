package org.bankautomation.api;

import io.restassured.response.Response;
import org.bankautomation.config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationApiTest {

    @Test(groups = {"api", "smoke"})
    public void shouldLoginSuccessfully() {

        Response response = ApiClient.login(
                ConfigReader.get("test.username"),
                ConfigReader.get("test.password")
        );

        response.then()
                .statusCode(200);

        Assert.assertEquals(
                response.jsonPath().getString("message"),
                "Login successful"
        );

        Assert.assertNotNull(
                response.jsonPath().getLong("userId")
        );

        Assert.assertEquals(
                response.jsonPath().getString("username"),
                "testuser"
        );

        Assert.assertEquals(
                response.jsonPath().getString("role"),
                "CUSTOMER"
        );

        String sessionId =
                response.getCookie("JSESSIONID");

        Assert.assertNotNull(
                sessionId,
                "JSESSIONID should be returned after login"
        );

        System.out.println(
                "Session ID received successfully."
        );
    }

    @Test(groups = {"api"})
    public void shouldRejectInvalidPassword() {

        Response response = ApiClient.login(
                ConfigReader.get("test.username"),
                "WrongPassword"
        );

        response.then()
                .statusCode(401);

        Assert.assertEquals(
                response.jsonPath().getString("message"),
                "Invalid username or password"
        );
    }

    @Test(groups = {"api"})
    public void shouldRejectUnknownUser() {

        Response response = ApiClient.login(
                "unknownuser",
                "WrongPassword"
        );

        response.then()
                .statusCode(401);

        Assert.assertEquals(
                response.jsonPath().getString("message"),
                "Invalid username or password"
        );
    }
}