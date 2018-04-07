package repository.bill;

import model.Bill;
import model.Client;
import model.builder.BillBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;

public class BillRepositoryMySQL implements BillRepository{

    Connection connection;

    public BillRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Bill bill) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO bill VALUES (null, ?, ?, ?)");
            insertStatement.setString(1, bill.getIdentif());
            insertStatement.setDouble(2, bill.getSum());
            insertStatement.setLong(3, bill.getId_client());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean findByIdentifier(String identifier) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM bill WHERE identif=" + identifier;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM bill WHERE id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
