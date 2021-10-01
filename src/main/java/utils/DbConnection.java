package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

    private static Connection connection;

    private static void openConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://31.131.249.140:5432/app-db",
                "app-db-admin", "AiIoqv6c2k0gVhx2");
    }

    private static void closeConnection() throws SQLException {
        connection.close();
    }

    public static ResultSet sqlSelectQuery(String query) throws SQLException {
        openConnection();
        PreparedStatement prep = connection.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        rs.next();
        closeConnection();
        return rs;
    }


    public static void sqlDeleteRegion(int responseRegionId) throws SQLException {
        openConnection();
        String sql = "DELETE FROM region WHERE id = " + responseRegionId;
        PreparedStatement prep = connection.prepareStatement(sql);
        prep.executeUpdate();
        closeConnection();
    }
}
