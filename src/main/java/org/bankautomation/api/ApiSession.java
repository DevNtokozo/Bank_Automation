package org.bankautomation.api;

import io.restassured.response.Response;
import org.bankautomation.config.ConfigReader;

public class ApiSession {

    private String sessionId;

    public void loginAsTestUser() {

        Response response = ApiClient.login(
                ConfigReader.get("test.username"),
                ConfigReader.get("test.password")
        );

        response.then()
                .statusCode(200);

        sessionId = response.getCookie("JSESSIONID");

        if (sessionId == null || sessionId.isBlank()) {
            throw new RuntimeException(
                    "JSESSIONID was not returned after login"
            );
        }
    }

    public void loginAsRecipient() {

        Response response = ApiClient.login(
                ConfigReader.get("recipient.username"),
                ConfigReader.get("recipient.password")
        );

        response.then()
                .statusCode(200);

        sessionId = response.getCookie("JSESSIONID");

        if (sessionId == null || sessionId.isBlank()) {
            throw new RuntimeException(
                    "JSESSIONID was not returned after login"
            );
        }
    }

    public String getSessionId() {
        return sessionId;
    }
}