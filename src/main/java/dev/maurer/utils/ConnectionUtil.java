package dev.maurer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

  public static Connection createConnection(){
        String connectionDetails = System.getenv("runeterraDBConnectionInfo");
        try {
            return DriverManager.getConnection(connectionDetails);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
