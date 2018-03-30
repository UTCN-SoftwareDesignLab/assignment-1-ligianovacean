package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM account";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Notification<Account> findById(Long id) throws EntityNotFoundException {
        Notification<Account> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM account WHERE id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                notification.setResult(getAccountFromResultSet(rs));
                return notification;
            } else {
                notification.addError("Invalid account id!");
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account VALUES (null, ?, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setDouble(2, account.getSum());
            insertStatement.setDate(3, new java.sql.Date(account.getCreation_date().getTime()));
            insertStatement.setLong(4, account.getId_client());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement updateAccountStatement = connection.
                    prepareStatement("UPDATE account SET `type`=?, `sum`=?, `creation_date` = ?, `id_client` = ? WHERE `id` = ?");
            updateAccountStatement.setString(1, account.getType());
            updateAccountStatement.setDouble(2, account.getSum());
            updateAccountStatement.setDate(3, new java.sql.Date(account.getCreation_date().getTime()));
            updateAccountStatement.setLong(4, account.getId_client());
            updateAccountStatement.setLong(5, account.getId());
            updateAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> findAccountsByClientId(Long id) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM account WHERE id_client = " + id;
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean delete(Account account) {
        try{
            PreparedStatement deleteAccountStatement = connection.prepareStatement("DELETE FROM account WHERE id = ?");
            deleteAccountStatement.setLong(1, account.getId());
            deleteAccountStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM account WHERE id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setSum(rs.getDouble("sum"))
                .setCreation_date(rs.getDate("creation_date"))
                .setId_client(rs.getLong("id_client"))
                .build();
    }
}
