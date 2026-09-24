package org.bankautomation.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class BeneficiaryApiTest {

    private String sessionId;

    @BeforeClass
    public void login() {

        Response response =
                ApiClient.login(
                        "testuser",
                        "Test@123"
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Login should be successful"
        );

        sessionId =
                response.getCookie("JSESSIONID");

        Assert.assertNotNull(
                sessionId,
                "JSESSIONID should be returned"
        );
    }

    @Test(groups = {"api", "smoke"})
    public void shouldReturnBeneficiariesForAccount() {

        Response response =
                ApiClient.getBeneficiaries(
                        sessionId,
                        1L
                );

        response.then()
                .statusCode(200);

        response.prettyPrint();

        Assert.assertNotNull(
                response.jsonPath().getList("$"),
                "Beneficiary list should not be null"
        );
    }

    @Test(groups = {"api"})
    public void shouldAddAndDeleteBeneficiarySuccessfully() {

        String uniqueName =
                "QA API Recipient " + System.currentTimeMillis();

        String requestBody = """
                {
                    "name": "%s",
                    "accountNumber": "1000000003",
                    "bankName": "Dev Bank"
                }
                """.formatted(uniqueName);

        // -----------------------------------------
        // STEP 1: Add beneficiary
        // -----------------------------------------

        Response addResponse =
                ApiClient.addBeneficiary(
                        sessionId,
                        2L,
                        requestBody
                );

        addResponse.prettyPrint();

        Assert.assertEquals(
                addResponse.statusCode(),
                200,
                "Beneficiary should be created successfully"
        );

        Long beneficiaryId =
                addResponse.jsonPath()
                        .getLong("id");

        Assert.assertNotNull(
                beneficiaryId,
                "Created beneficiary ID should be returned"
        );

        Assert.assertEquals(
                addResponse.jsonPath()
                        .getString("name"),
                uniqueName
        );

        Assert.assertEquals(
                addResponse.jsonPath()
                        .getString("accountNumber"),
                "1000000003"
        );

        Assert.assertEquals(
                addResponse.jsonPath()
                        .getString("bankName"),
                "Dev Bank"
        );

        Assert.assertEquals(
                addResponse.jsonPath()
                        .getString("status"),
                "ACTIVE"
        );

        // -----------------------------------------
        // STEP 2: Verify beneficiary exists
        // -----------------------------------------

        Response getResponse =
                ApiClient.getBeneficiaries(
                        sessionId,
                        2L
                );

        getResponse.then()
                .statusCode(200);

        List<Map<String, Object>> beneficiaries =
                getResponse.jsonPath()
                        .getList("$");

        boolean beneficiaryFound =
                beneficiaries.stream()
                        .anyMatch(
                                beneficiary ->
                                        uniqueName.equals(
                                                beneficiary.get("name")
                                        )
                        );

        Assert.assertTrue(
                beneficiaryFound,
                "Created beneficiary should appear in beneficiary list"
        );

        // -----------------------------------------
        // STEP 3: Delete beneficiary
        // -----------------------------------------

        Response deleteResponse =
                ApiClient.deleteBeneficiary(
                        sessionId,
                        beneficiaryId
                );

        deleteResponse.then()
                .statusCode(200);

        Assert.assertEquals(
                deleteResponse.asString(),
                "Beneficiary deleted successfully"
        );

        // -----------------------------------------
        // STEP 4: Verify beneficiary was deleted
        // -----------------------------------------

        Response finalResponse =
                ApiClient.getBeneficiaries(
                        sessionId,
                        2L
                );

        finalResponse.then()
                .statusCode(200);

        List<Map<String, Object>> finalBeneficiaries =
                finalResponse.jsonPath()
                        .getList("$");

        boolean beneficiaryStillExists =
                finalBeneficiaries.stream()
                        .anyMatch(
                                beneficiary ->
                                        uniqueName.equals(
                                                beneficiary.get("name")
                                        )
                        );

        Assert.assertFalse(
                beneficiaryStillExists,
                "Deleted beneficiary should no longer appear"
        );
    }

    @Test(groups = {"api"})
    public void shouldRejectUnauthorisedBeneficiaryRequest() {

        Response response =
                ApiClient.getBeneficiaries(
                        "invalid-session-id",
                        1L
                );

        Assert.assertEquals(
                response.statusCode(),
                401,
                "Unauthorised beneficiary request should return 401"
        );
    }

    @Test(groups = {"api"})
    public void shouldRejectUnauthorisedBeneficiaryCreation() {

        String requestBody = """
                {
                    "name": "Unauthorised Recipient",
                    "accountNumber": "1000000003",
                    "bankName": "Dev Bank"
                }
                """;

        Response response =
                ApiClient.addBeneficiary(
                        "invalid-session-id",
                        2L,
                        requestBody
                );

        Assert.assertEquals(
                response.statusCode(),
                401,
                "Unauthorised beneficiary creation should return 401"
        );
    }

    @Test(groups = {"api"})
    public void shouldRejectUnauthorisedBeneficiaryDeletion() {

        Response response =
                ApiClient.deleteBeneficiary(
                        "invalid-session-id",
                        4L
                );

        Assert.assertEquals(
                response.statusCode(),
                401,
                "Unauthorised beneficiary deletion should return 401"
        );
    }
}