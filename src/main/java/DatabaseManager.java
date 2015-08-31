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
            preparedStatement2.executeUpdate();
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM legalcustomer inner join customer on legalcustomer.id=customer.id\n" +
                    " WHERE 1=1 " +
                    (legalCustomer.getCompanyName().length() > 0 ? " AND companyName = ?" : "") +// meghdar gereft
                    (legalCustomer.getEconomicCode().length() > 0 ? " AND economicCode = ?" : "") +
                    (legalCustomer.getCustomerNumber().length() > 0 ? "AND customerNumber = ?" : ""));
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

    public List<RealCustomer> searchRealCustomer(RealCustomer realCustomer) {
        List<RealCustomer> realCustomers = new ArrayList<RealCustomer>();
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM realcustomer inner join customer on realcustomer.id=customer.id\n" +
                    " WHERE 1=1 " +
                    (realCustomer.getFirstName().length() > 0 ? " AND firstName = ?" : "") +// meghdar gereft
                    (realCustomer.getLastName().length() > 0 ? " AND lastName = ?" : "") +
                    (realCustomer.getNationalCode().length() > 0 ? " AND nationalCode = ?" : "") +
                    (realCustomer.getCustomerNumber().length() > 0 ? "AND customerNumber = ?" : ""));
            int index = 1;
            if (realCustomer.getFirstName().length() > 0) {
                preparedStatement.setString(index, realCustomer.getFirstName());
                index++;
            }
            if (realCustomer.getLastName().length() > 0) {
                preparedStatement.setString(index, realCustomer.getLastName());
                index++;
            }
            if (realCustomer.getNationalCode().length() > 0) {
                preparedStatement.setString(index, realCustomer.getNationalCode());
                index++;
            }
            if (realCustomer.getCustomerNumber().length() > 0) {
                preparedStatement.setString(index, realCustomer.getCustomerNumber());
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RealCustomer realCustomer1 = new RealCustomer();
                realCustomer1.setFirstName(resultSet.getString("firstName"));
                realCustomer1.setLastName(resultSet.getString("lastName"));
                realCustomer1.setFatherName(resultSet.getString("fatherName"));
                realCustomer1.setBirthDay(resultSet.getString("birthDate"));
                realCustomer1.setNationalCode(resultSet.getString("nationalCode"));
                realCustomer1.setId(resultSet.getInt("id"));
                realCustomer1.setCustomerNumber(resultSet.getString("customerNumber"));
                realCustomers.add(realCustomer1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }

    public boolean existsLegalCustomerWithEconomicCode(String economicCode) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT economicCode FROM legalcustomer WHERE economicCode=?");
            preparedStatement.setString(1, economicCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new SqlException("Exception", e);
        }
    }

    public boolean existRealCustomerWithNationalCode(String nationalCode) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nationalCode FROM realcustomer WHERE nationalCode=?");
            preparedStatement.setString(1, nationalCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new SqlException("EXCEPTION", e);
        }
    }

    public boolean existsRealCustomerNationalCode(String nationalCode, int id) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nationalCode FROM realcustomer WHERE nationalCode=? AND id<>? ");
            preparedStatement.setString(1, nationalCode);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
           throw new SqlException("Exception",e);
        }
    }

    public boolean existsLegalEconomicCode(String economicCode, int id) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT economicCode FROM legalcustomer WHERE economicCode=? AND id<>?");
            preparedStatement.setString(1, economicCode);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new SqlException("EXCEPTION", e);
        }
    }
}