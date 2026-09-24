package org.bankautomation.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.bankautomation.config.ConfigReader;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URL =
            ConfigReader.get("api.base.url");

    static {
        RestAssured.baseURI = BASE_URL;
    }

    public static Response login(
            String username,
            String password) {

        return given()
                .contentType("application/json")
                .body("""
                        {
                            "username": "%s",
                            "password": "%s"
                        }
                        """.formatted(username, password))
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    public static Response logout(String sessionId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .post(ApiEndpoints.LOGOUT);
    }

    public static Response getCurrentUser(
            String sessionId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get(ApiEndpoints.CURRENT_USER);
    }

    public static Response getAccounts(
            String sessionId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get(ApiEndpoints.ACCOUNTS);
    }

    public static Response transfer(
            String sessionId,
            Long fromAccountId,
            String toAccountNumber,
            String amount,
            String reference) {

        return given()
                .contentType("application/json")
                .cookie("JSESSIONID", sessionId)
                .body("""
                    {
                        "fromAccountId": %d,
                        "toAccountNumber": "%s",
                        "amount": %s,
                        "reference": "%s"
                    }
                    """.formatted(
                        fromAccountId,
                        toAccountNumber,
                        amount,
                        reference
                ))
                .when()
                .post(ApiEndpoints.TRANSFERS);
    }

    public static Response getAccountTransactions(
            String sessionId,
            Long accountId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get(
                        ApiEndpoints.TRANSACTIONS
                                + "/account/"
                                + accountId
                );
    }

    public static Response getTransaction(
            String sessionId,
            Long transactionId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get(
                        ApiEndpoints.TRANSACTIONS
                                + "/"
                                + transactionId
                );
    }

    public static Response getBeneficiaries(
            String sessionId,
            Long accountId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get(
                        ApiEndpoints.BENEFICIARIES
                                + "/account/"
                                + accountId
                );
    }

    public static Response addBeneficiary(
            String sessionId,
            Long accountId,
            Object request) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .contentType("application/json")
                .body(request)
                .when()
                .post(
                        ApiEndpoints.BENEFICIARIES
                                + "/account/"
                                + accountId
                );
    }

    public static Response deleteBeneficiary(
            String sessionId,
            Long beneficiaryId) {

        return given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .delete(
                        ApiEndpoints.BENEFICIARIES
                                + "/"
                                + beneficiaryId
                );
    }

}