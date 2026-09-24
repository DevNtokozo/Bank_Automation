package org.bankautomation.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    private DatabaseUtils() {
    }

    public static int getUserCount() {

        String sql = "SELECT COUNT(*) FROM users";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve user count",
                    e
            );
        }
    }

    public static int getAccountCount() {

        String sql = "SELECT COUNT(*) FROM accounts";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve account count",
                    e
            );
        }
    }

    public static boolean accountExists(String accountNumber) {

        String sql =
                "SELECT COUNT(*) FROM accounts WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }

                return false;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to check account",
                    e
            );
        }
    }

    public static java.math.BigDecimal getAccountBalance(
            String accountNumber) {

        String sql =
                "SELECT balance " +
                        "FROM accounts " +
                        "WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getBigDecimal(
                            "balance"
                    );
                }

                return null;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve account balance: "
                            + e.getMessage(),
                    e
            );
        }


    }

    public static int getBeneficiaryCount() {

        String sql =
                "SELECT COUNT(*) FROM beneficiaries";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve beneficiary count: "
                            + e.getMessage(),
                    e
            );
        }
    }

    public static boolean beneficiaryExists(
            Long beneficiaryId) {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM beneficiaries " +
                        "WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, beneficiaryId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }

                return false;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to check beneficiary: "
                            + e.getMessage(),
                    e
            );
        }
    }

    public static int getTransactionCount() {

        String sql =
                "SELECT COUNT(*) FROM transactions";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve transaction count: "
                            + e.getMessage(),
                    e
            );
        }
    }

    public static boolean transactionExists(
            Long transactionId) {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM transactions " +
                        "WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, transactionId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }

                return false;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to check transaction: "
                            + e.getMessage(),
                    e
            );
        }

    }

    public static Long getLatestTransactionId() {

        String sql =
                "SELECT id " +
                        "FROM transactions " +
                        "ORDER BY id DESC " +
                        "LIMIT 1";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong("id");
            }

            return null;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve latest transaction: "
                            + e.getMessage(),
                    e
            );
        }
    }


}