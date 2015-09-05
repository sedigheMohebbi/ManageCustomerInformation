package dataacceess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnect {
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    private static SqlConnect ourInstance = new SqlConnect();

    public static SqlConnect getInstance() {
        return ourInstance;
    }

    private SqlConnect() {
        String CONNECTION_URL = "jdbc:mysql://localhost/customermanager?useUnicode=true&characterEncoding=UTF-8";
        String USER = "root";
        String PASSWORD = "asaasale";
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
