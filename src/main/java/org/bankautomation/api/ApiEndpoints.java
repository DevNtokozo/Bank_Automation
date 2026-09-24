package org.bankautomation.api;

public class ApiEndpoints {

    public static final String LOGIN = "/auth/login";
    public static final String LOGOUT = "/auth/logout";
    public static final String CURRENT_USER = "/auth/me";

    public static final String ACCOUNTS = "/accounts";

    public static final String TRANSFERS = "/transfers";

    public static final String TRANSACTIONS = "/transactions";

    public static final String BENEFICIARIES = "/beneficiaries";

    private ApiEndpoints() {
    }
}