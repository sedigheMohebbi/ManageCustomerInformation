import exception.SqlException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost/customermanager";
    private static final String USER = "root";
    private static final String PASSWORD = "123456q@";


    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.err.println("mysql jdbc driver not found");
        }
    }

    public LegalCustomer saveLegalCustomer(LegalCustomer legalCustomer) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            Statement sqlStatement = connection.createStatement();
            sqlStatement.executeUpdate("INSERT INTO legalcustomer (companyName,registrationDate,economicCode,customerNumber ) VALUES " +
                    "('" + legalCustomer.getCompanyName() +
                    "','" + legalCustomer.getDate() +
                    "',' " + legalCustomer.getEconomicCode() +
                    "','" + legalCustomer.getCustomerNumber() +
                    "')");

        } catch (SQLException e) {
            throw new SqlException("Error at legal customer save Exception",e);
        }
        return legalCustomer;
    }
}