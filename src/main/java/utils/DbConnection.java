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

    public static ResultSet sqlSelectQuery(String query) throws Exception {
        openConnection();
        PreparedStatement prep = connection.prepareStatement(query);
        ResultSet rs = prep.executeQuery();
        rs.next();
        connection.close();
        return rs;
    }
}
