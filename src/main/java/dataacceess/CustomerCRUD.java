package dataacceess;

import exception.SqlException;
import model.Customer;

import java.sql.*;


public class CustomerCRUD {
    static final String CONNECTION_URL = "jdbc:mysql://localhost/customermanager";
    static final String USER = "root";
    static final String PASSWORD = "123456q@";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.err.println("mysql jdbc driver not found");
        }
    }

    public static Customer getLastCustomer() throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT *  FROM customer WHERE  id=(select max(id) from customer)");
            if (!resultSet.next()) {
                return null;
            }
            Customer customer = new Customer();
            customer.setCustomerNumber(resultSet.getString("customerNumber"));
            customer.setId(resultSet.getInt("id"));
            connection.close();
            return customer;


        } catch (SQLException e) {
            throw new SqlException("Error at legal customer save Exception", e);
        }
    }
}
