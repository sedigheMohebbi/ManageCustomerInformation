package dataacceess;


import exception.SqlException;
import model.LegalCustomer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LegalCustomerCRUD {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.err.println("mysql jdbc driver not found");
        }
    }

    public static LegalCustomer saveLegalCustomer(LegalCustomer legalCustomer) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
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
    public static boolean existsLegalCustomerWithEconomicCode(String economicCode) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT economicCode FROM legalcustomer WHERE economicCode=?");
            preparedStatement.setString(1, economicCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new SqlException("Exception", e);
        }
    }
    public static boolean existsLegalEconomicCode(String economicCode, int id) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
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
    public  static List<LegalCustomer> searchLegalCustomer(LegalCustomer legalCustomer) {
        List<LegalCustomer> legalCustomers = new ArrayList<LegalCustomer>();
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
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

}