import exception.SqlException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer (customerNumber) VALUES (?)");
            preparedStatement.setString(1, legalCustomer.getCustomerNumber());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT id from customer WHERE customerNumber=?");
            preparedStatement1.setString(1, legalCustomer.getCustomerNumber());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.first();
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO legalcustomer (companyName,registrationDate,economicCode ,id) VALUES (?,?,?,?)");
            preparedStatement2.setString(1, legalCustomer.getCompanyName());
            preparedStatement2.setString(2, legalCustomer.getRegistrationDate());
            preparedStatement2.setString(3, legalCustomer.getEconomicCode());
            preparedStatement2.setInt(4, resultSet.getInt("id"));
            preparedStatement2.executeQuery();
            connection.close();
        } catch (SQLException e) {
            throw new SqlException("Error at legal customer save Exception", e);
        }
        return legalCustomer;
    }

    public Customer getLastCustomer() throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT *  FROM customer where  id=(select max(id) from customer)");
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

    public RealCustomer saveRealCustomer(RealCustomer realCustomer) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer (customerNumber) VALUES (?)");
            preparedStatement.setString(1, realCustomer.getCustomerNumber());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT id from customer WHERE customerNumber = ?");
            preparedStatement1.setString(1, realCustomer.getCustomerNumber());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.first();
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO realCustomer (firstName,lastName,fatherName,birthDate,nationalCode ,id) VALUES (?,?,?,?,?,?)");
            preparedStatement2.setString(1, realCustomer.getFirstName());
            preparedStatement2.setString(2, realCustomer.getLastName());
            preparedStatement2.setString(3, realCustomer.getFatherName());
            preparedStatement2.setString(4, realCustomer.getBirthDay());
            preparedStatement2.setString(5, realCustomer.getNationalCode());
            preparedStatement2.setInt(6, resultSet.getInt("id"));
            preparedStatement2.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomer;
    }

    public List<LegalCustomer> searchLegalCustomer(LegalCustomer legalCustomer) {
        List<LegalCustomer> legalCustomers = new ArrayList<LegalCustomer>();

        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM legalcustomer INNER JOIN customer ON  legalcustomer.id=customer.id\n" + " WHERE 1=1" +
                    (legalCustomer.getCompanyName().length() > 0 ? " AND companyName = ?" : "") +
                    (legalCustomer.getEconomicCode().length() > 0 ? " AND economicCode= ?" : "") +
                    (legalCustomer.getCustomerNumber().length() > 0 ? "AND customerNumber ?" : ""));
            int index = 1;
            if (legalCustomer.getCompanyName().length() > 0) {
                preparedStatement.setString(index, legalCustomer.getCompanyName());
                index++;
            }
            if (legalCustomer.getEconomicCode().length() > 0) {
                preparedStatement.setString(index, legalCustomer.getEconomicCode());
                index++;
            }
            if (legalCustomer.getCustomerNumber().length() > 0) {
                preparedStatement.setString(index, legalCustomer.getCustomerNumber());
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LegalCustomer legalCustomer1 = new LegalCustomer();
                legalCustomer1.setCompanyName(resultSet.getString("companyName"));
                legalCustomer1.setEconomicCode(resultSet.getString("economicCode"));
                legalCustomer1.setCustomerNumber(resultSet.getString("customerNumber"));
                legalCustomer1.setRegistrationDate(resultSet.getString("registrationDate"));
                legalCustomer1.setId(resultSet.getInt("id"));
                legalCustomers.add(legalCustomer1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return legalCustomers;
    }
}