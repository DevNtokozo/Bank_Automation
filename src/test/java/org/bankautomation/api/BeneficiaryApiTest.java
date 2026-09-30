package org.bankautomation.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BeneficiaryApiTest {

    @Test
    public void shouldReturnBeneficiariesForAccount() {

        ApiSession apiSession =
                new ApiSession();

        apiSession.loginAsTestUser();

        Response response =
                ApiClient.getBeneficiaries(
                        apiSession.getSessionId(),
                        1L
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Beneficiary request should return HTTP 200"
        );

        List<Integer> beneficiaries =
                response.jsonPath()
                        .getList("id");

        Assert.assertNotNull(
                beneficiaries,
                "Beneficiary list should not be null"
        );

        System.out.println(
                "Beneficiaries returned: "
                        + beneficiaries.size()
        );
    }

    @Test
    public void shouldAddAndDeleteBeneficiarySuccessfully() {

        ApiSession apiSession =
                new ApiSession();

        apiSession.loginAsTestUser();

        Long accountId = 2L;

        String uniqueName =
                "API Automation Beneficiary "
                        + System.currentTimeMillis();

        String accountNumber =
                "1000000003";

        String bankName =
                "Dev Bank";

        String requestBody =
                """
                {
                    "name": "%s",
                    "accountNumber": "%s",
                    "bankName": "%s"
                }
                """.formatted(
                        uniqueName,
                        accountNumber,
                        bankName
                );

        System.out.println(
                "Creating beneficiary:"
        );

        System.out.println(
                "Name: " + uniqueName
        );

        System.out.println(
                "Account: " + accountNumber
        );

        Response createResponse =
                ApiClient.addBeneficiary(
                        apiSession.getSessionId(),
                        accountId,
                        requestBody
                );

        System.out.println(
                "Create status: "
                        + createResponse.statusCode()
        );

        System.out.println(
                "Create response: "
                        + createResponse.asPrettyString()
        );

        Assert.assertEquals(
                createResponse.statusCode(),
                200,
                "Beneficiary should be created successfully"
        );

        Long beneficiaryId =
                createResponse.jsonPath()
                        .getLong("id");

        Assert.assertNotNull(
                beneficiaryId,
                "Created beneficiary ID should not be null"
        );

        System.out.println(
                "Created beneficiary ID: "
                        + beneficiaryId
        );

        // Verify beneficiary appears in API
        Response listResponse =
                ApiClient.getBeneficiaries(
                        apiSession.getSessionId(),
                        accountId
                );

        Assert.assertEquals(
                listResponse.statusCode(),
                200,
                "Beneficiary list should return HTTP 200"
        );

        List<String> names =
                listResponse.jsonPath()
                        .getList("name");

        Assert.assertTrue(
                names.contains(uniqueName),
                "Created beneficiary should appear in beneficiary list"
        );

        // Delete beneficiary
        Response deleteResponse =
                ApiClient.deleteBeneficiary(
                        apiSession.getSessionId(),
                        beneficiaryId
                );

        Assert.assertEquals(
                deleteResponse.statusCode(),
                200,
                "Beneficiary should be deleted successfully"
        );

        System.out.println(
                "Beneficiary deleted successfully"
        );

        // Verify deletion
        Response finalResponse =
                ApiClient.getBeneficiaries(
                        apiSession.getSessionId(),
                        accountId
                );

        Assert.assertEquals(
                finalResponse.statusCode(),
                200,
                "Final beneficiary request should return HTTP 200"
        );

        List<Integer> finalIds =
                finalResponse.jsonPath()
                        .getList("id");

        Assert.assertFalse(
                finalIds.contains(
                        beneficiaryId.intValue()
                ),
                "Deleted beneficiary should no longer exist"
        );
    }

    @Test
    public void shouldRejectUnauthorisedBeneficiaryRequest() {

        Response response =
                ApiClient.getBeneficiaries(
                        "invalid-session",
                        1L
                );

        Assert.assertEquals(
                response.statusCode(),
                401,
                "Unauthorised beneficiary request should return HTTP 401"
        );
    }

    @Test
    public void shouldRejectUnauthorisedBeneficiaryCreation() {

        String requestBody =
                """
                {
                    "name": "Unauthorised Beneficiary",
                    "accountNumber": "1000000003",
                    "bankName": "Dev Bank"
                }
                """;

        Response response =
                ApiClient.addBeneficiary(
                        "invalid-session",
                        2L,
                        requestBody
                );

        Assert.assertEquals(
                response.statusCode(),
                401,
                "Unauthorised beneficiary creation should return HTTP 401"
        );
    }

    @Test
    public void shouldRejectUnauthorisedBeneficiaryDeletion() {

        Response response =
                ApiClient.deleteBeneficiary(
                        "invalid-session",
                        4L
                );

        Assert.assertEquals(
                response.statusCode(),
                401,
                "Unauthorised beneficiary deletion should return HTTP 401"
        );
    }
}