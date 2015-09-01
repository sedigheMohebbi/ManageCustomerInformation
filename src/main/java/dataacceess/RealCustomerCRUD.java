package dataacceess;


import exception.SqlException;
import model.RealCustomer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RealCustomerCRUD {

        static {
            try {
                Class.forName("com.mysql.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                System.err.println("mysql jdbc driver not found");
            }
        }
    public static RealCustomer saveRealCustomer(RealCustomer realCustomer) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
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
    public static boolean existRealCustomerWithNationalCode(String nationalCode) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nationalCode FROM realcustomer WHERE nationalCode=?");
            preparedStatement.setString(1, nationalCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new SqlException("EXCEPTION", e);
        }
    }

    public static boolean existsRealCustomerNationalCode(String nationalCode, int id) throws SqlException {
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nationalCode FROM realcustomer WHERE nationalCode=? AND id<>? ");
            preparedStatement.setString(1, nationalCode);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new SqlException("Exception", e);
        }
    }
    public static List<RealCustomer> searchRealCustomer(RealCustomer realCustomer) {
        List<RealCustomer> realCustomers = new ArrayList<RealCustomer>();
        try {
            Connection connection = DriverManager.getConnection(CustomerCRUD.CONNECTION_URL, CustomerCRUD.USER, CustomerCRUD.PASSWORD);
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


}
