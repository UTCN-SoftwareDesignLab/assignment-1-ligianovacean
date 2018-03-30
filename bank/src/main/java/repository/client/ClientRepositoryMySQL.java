package repository.client;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM client";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                clients.add(getClientFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Notification<Client> findById(Long id) throws EntityNotFoundException {
        Notification<Client> findByIdNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM client WHERE id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                findByIdNotification.setResult(getClientFromResultSet(rs));
                return findByIdNotification;
            } else {
                findByIdNotification.addError("Invalid Client Id");
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client VALUES (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getId_card_no());
            insertStatement.setString(3, client.getPersonal_numerical_code());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement updateUserStatement = connection.
                    prepareStatement("UPDATE client SET `name`=?, `id_card_no`=?, `address` = ? WHERE `id` = ?");
            updateUserStatement.setString(1, client.getName());
            updateUserStatement.setString(2, client.getId_card_no());
            updateUserStatement.setString(3, client.getAddress());
            updateUserStatement.setLong(4, client.getId());
            updateUserStatement.executeUpdate();
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
            String sql = "DELETE FROM client WHERE id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException{
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setId_card_no(rs.getString("id_card_no"))
                .setPersonal_numerical_code(rs.getString("personal_numerical_code"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
