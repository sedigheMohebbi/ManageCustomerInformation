import exception.SqlException;
import exception.ValidationException;

import java.util.List;

public class Controller {
    public LegalCustomer createAndSaveLegalCustomer(String name, String date, String economicCode) throws SqlException, ValidationException {
        LegalCustomer legalCustomer = new LegalCustomer();
        legalCustomer.setCompanyName(name);
        legalCustomer.setEconomicCode(economicCode);
        legalCustomer.setRegistrationDate(date);
        legalCustomer.setCustomerNumber(generateCustomerNumber());
        validateLegalCustomer(legalCustomer, true);
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

    public RealCustomer createAndSaveRealCustomer(String firstName, String lastName, String nationalCode, String birthDay, String fatherName) throws SqlException, ValidationException {
        RealCustomer realCustomer = new RealCustomer();
        realCustomer.setFirstName(firstName);
        realCustomer.setLastName(lastName);
        realCustomer.setFatherName(fatherName);
        realCustomer.setNationalCode(nationalCode);
        realCustomer.setBirthDay(birthDay);
        realCustomer.setCustomerNumber(generateCustomerNumber());
        validateRealCustomer(realCustomer, true);
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

    public void validateLegalCustomer(LegalCustomer legalCustomer, boolean add) throws ValidationException, SqlException {
        DatabaseManager databaseManager = new DatabaseManager();

        if (legalCustomer.getCompanyName() == null || legalCustomer.getCompanyName().length() == 0) {
            throw new ValidationException("company name has no value");
        }
        if (legalCustomer.getRegistrationDate() == null || legalCustomer.getRegistrationDate().length() == 0) {
            throw new ValidationException("Registration Date has no value");
        }
        if (legalCustomer.getEconomicCode() == null || legalCustomer.getRegistrationDate().length() == 0) {
            throw new ValidationException("Registration Date has no value");
        }
        boolean economicExists;
        if (add) {
            economicExists = databaseManager.existsLegalCustomerWithEconomicCode(legalCustomer.getEconomicCode());
        } else {
            economicExists = databaseManager.existsLegalEconomicCode(legalCustomer.getEconomicCode(), legalCustomer.getId());
        }
        if (economicExists) {
            throw new ValidationException("Economic code exists");
        }
        if (!legalCustomer.getEconomicCode().matches("[0-9]{5}")) {
            throw new ValidationException("economic code is invalid");
        }
        if (!legalCustomer.getRegistrationDate().matches("[1-9][0-9]{3}/[0-1][0-9]/[0-3][0-9]")) {
            throw new ValidationException("Registration date is invalid");
        }
    }

    public void validateRealCustomer(RealCustomer realCustomer, boolean add) throws ValidationException, SqlException {
        DatabaseManager databaseManager = new DatabaseManager();
        if (realCustomer.getFirstName() == null || realCustomer.getFirstName().length() == 0) {
            throw new ValidationException("first name has no value");
        }
        if (!realCustomer.getFirstName().matches("[a-zA-Z]*")) {
            throw new ValidationException("first name is invalid");
        }
        if (realCustomer.getLastName() == null || realCustomer.getLastName().length() == 0) {
            throw new ValidationException("last name has no value");
        }
        if (!realCustomer.getLastName().matches("[a-zA-Z]*")) {
            throw new ValidationException("last Name is invalid");
        }
        if (realCustomer.getFatherName() == null || realCustomer.getFatherName().length() == 0) {
            throw new ValidationException("father name has no value");
        }
        if (!realCustomer.getFatherName().matches("[a-zA-Z]*")) {
            throw new ValidationException("father Name is invalid");
        }
        if (realCustomer.getBirthDay() == null || realCustomer.getBirthDay().length() == 0) {
            throw new ValidationException("Birth day has no value");
        }
        if (!realCustomer.getBirthDay().matches("[1-9][0-9]{3}/[0-1][0-9]/[0-3][0-9]")) {
            throw new ValidationException("Birth day is invalid");
        }
        if (realCustomer.getNationalCode() == null || realCustomer.getNationalCode().length() == 0) {
            throw new ValidationException("nation code is has no value");
        }
        if (!realCustomer.getNationalCode().matches("[0-9]{5}")) {
            throw new ValidationException("nation code is invalid");
        }
        boolean ExistsNationalCode;
        if (add) {
            ExistsNationalCode = databaseManager.existRealCustomerWithNationalCode(realCustomer.getNationalCode());
        } else {
            ExistsNationalCode = databaseManager.existsRealCustomerNationalCode(realCustomer.getNationalCode(), realCustomer.getId());
        }
        if (ExistsNationalCode) {
            throw new ValidationException("national code exists");
        }


    }
}