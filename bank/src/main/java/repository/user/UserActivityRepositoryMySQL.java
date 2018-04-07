package repository.user;

import model.Activity;
import model.builder.ActivityBuilder;
import repository.user.UserActivityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class UserActivityRepositoryMySQL implements UserActivityRepository {

    private Connection connection;

    public UserActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Activity activity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO activity VALUES (null, ?, ?, ?, ?)");
            insertStatement.setLong(1, activity.getUserId());
            insertStatement.setString(2, activity.getUserRole());
            insertStatement.setDate(3, new java.sql.Date(activity.getDate().getTime()));
            insertStatement.setString(4, activity.getDescription());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Activity> findByDateAndUser(Date startDate, Date endDate, String username) {
        List<Activity> activities= new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM activity JOIN user ON activity.user_id = user.id" +
                                    " WHERE user.username = \"" + username + "\" AND activity.user_role = \"" + EMPLOYEE +
                                    "\" AND date(activity.date) BETWEEN \'" + startDate.toString() +
                                                                "\' AND \'" + endDate.toString() + "\'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                activities.add(getActivityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM activity WHERE id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Activity getActivityFromResultSet(ResultSet rs) throws SQLException{
        return new ActivityBuilder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setUserRole(rs.getString("user_role"))
                .setDate(rs.getDate("date"))
                .setDescription(rs.getString("description"))
                .build();
    }
}
