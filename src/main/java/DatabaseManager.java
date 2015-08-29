import exception.SqlException;

import java.sql.*;

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
            sqlStatement.executeUpdate("INSERT INTO customer (customerNumber) VALUES ('"+legalCustomer.getCustomerNumber()+"')");
           ResultSet resultSet = sqlStatement.executeQuery("SELECT id from customer WHERE customerNumber="+legalCustomer.getCustomerNumber());
            resultSet.first();
            sqlStatement.executeUpdate("INSERT INTO legalcustomer (companyName,registrationDate,economicCode ,id) VALUES " +
                    "('" + legalCustomer.getCompanyName() +
                    "','" + legalCustomer.getRegistrationDate() +
                    "',' " + legalCustomer.getEconomicCode() +
                    "',"+ resultSet.getInt("id")+
                    ")");
connection.close();
        } catch (SQLException e) {
            throw new SqlException("Error at legal customer save Exception", e);
        }
        return legalCustomer;
    }

    public Customer getLastCustomer() throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            Statement sqlStatement=connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT *  FROM customer where  id=(select max(id) from customer)");
            if(!resultSet.next()){
                return null;
            }
            Customer customer = new Customer();
            customer.setCustomerNumber(resultSet.getString("customerNumber"));
            customer.setId(resultSet.getInt("id"));
            connection.close();
            return customer;


        } catch (SQLException e) {
                throw new SqlException("Error at legal customer save Exception",e);
            }
    }
    public RealCustomer saveRealCustomer(RealCustomer realCustomer) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            Statement sqlStatement = connection.createStatement();
            sqlStatement.executeUpdate("INSERT INTO customer (customerNumber) VALUES ('"+realCustomer.getCustomerNumber()+"')");
            ResultSet resultSet = sqlStatement.executeQuery("SELECT id from customer WHERE customerNumber=" + realCustomer.getCustomerNumber());
            resultSet.first();
            sqlStatement.executeUpdate("INSERT INTO realCustomer (firstName,lastName,fatherName,birthDate,nationalCode ,id) VALUES " +
                    "('" + realCustomer.getFirstName() +
                    "','" + realCustomer.getLastName() +
                    "','" + realCustomer.getFatherName() +
                    "',' " + realCustomer.getBirthDay() +
                    "',' " + realCustomer.getNationalCode() +
                    "',"+ resultSet.getInt("id")+
                    ")");

  connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomer;
    }}