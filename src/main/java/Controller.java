import exception.SqlException;

public class Controller {
    public LegalCustomer createAndSaveLegalCustomer(String name, String date, String economicCode) throws SqlException {
        LegalCustomer legalCustomer = new LegalCustomer();
        legalCustomer.setCompanyName(name);
        legalCustomer.setCustomerNumber("1");//todo algorithme shomare moshtari
        legalCustomer.setEconomicCode(economicCode);
        legalCustomer.setDate(date);
        //todo validate
        DatabaseManager databaseManager = new DatabaseManager();
        LegalCustomer result = databaseManager.saveLegalCustomer(legalCustomer);
        return result;
    }

}
