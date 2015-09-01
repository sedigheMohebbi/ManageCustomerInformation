package business;

public class RealCustomerBiz {
    private static RealCustomerBiz ourInstance = new RealCustomerBiz();

    public static RealCustomerBiz getInstance() {
        return ourInstance;
    }

    private RealCustomerBiz() {
    }
}
