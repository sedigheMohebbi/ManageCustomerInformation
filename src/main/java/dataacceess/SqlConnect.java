package dataacceess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SqlConnect {
    public Connection conn;

    private static SqlConnect ourInstance = new SqlConnect();

    public static SqlConnect getInstance() {
        return ourInstance;
    }

    private SqlConnect() {
        String CONNECTION_URL = "jdbc:mysql://localhost/customermanager";
        String USER = "root";
        String PASSWORD = "123456q@";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.err.println("mysql jdbc driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
