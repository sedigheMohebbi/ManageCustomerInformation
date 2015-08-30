import exception.SqlException;

import java.util.List;

public class Controller {
    public LegalCustomer createAndSaveLegalCustomer(String name, String date, String economicCode) throws SqlException {
        LegalCustomer legalCustomer = new LegalCustomer();
        legalCustomer.setCompanyName(name);
        legalCustomer.setEconomicCode(economicCode);
        legalCustomer.setRegistrationDate(date);
        legalCustomer.setCustomerNumber(generateCustomerNumber());
        //todo validate
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.saveLegalCustomer(legalCustomer);
    }

    static final Boolean lock = true;

    private String generateCustomerNumber() throws SqlException {
        DatabaseManager databaseManager = new DatabaseManager();
        synchronized (lock) {
            Customer customer = databaseManager.getLastCustomer();
            if (customer == null) {
                return String.valueOf(1);
            }
            int lastCustomer = Integer.parseInt(customer.getCustomerNumber());
            lastCustomer += 1;
            return String.valueOf(lastCustomer);
        }
    }

    public RealCustomer createAndSaveRealCustomer(String firstName, String lastName, String nationalCode, String birthDay, String fatherName) throws SqlException {
        RealCustomer realCustomer = new RealCustomer();
        realCustomer.setFirstName(firstName);
        realCustomer.setLastName(lastName);
        realCustomer.setFatherName(fatherName);
        realCustomer.setNationalCode(nationalCode);
        realCustomer.setBirthDay(birthDay);
        realCustomer.setCustomerNumber(generateCustomerNumber());
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.saveRealCustomer(realCustomer);

    }

    public List<LegalCustomer> searchLegalCustomer(String companyName, String economicCode, String customerNumber) {
        LegalCustomer legalCustomer = new LegalCustomer();
        legalCustomer.setCompanyName(companyName);
        legalCustomer.setEconomicCode(economicCode);
        legalCustomer.setCustomerNumber(customerNumber);
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.searchLegalCustomer(legalCustomer);
    }

    public List<RealCustomer> searchRealCustomer(String firstName, String lastName, String nationalCode, String customerNumber) {
        DatabaseManager databaseManager = new DatabaseManager();
        RealCustomer realCustomer = new RealCustomer();
        realCustomer.setFirstName(firstName);
        realCustomer.setLastName(lastName);
        realCustomer.setNationalCode(nationalCode);
        realCustomer.setCustomerNumber(customerNumber);
        return databaseManager.searchRealCustomer(realCustomer);
    }

}
