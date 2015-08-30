import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SearchAndShowCustomers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Controller controller = new Controller();
        if ("legal".equals(request.getParameter("customerType"))) {
            List<LegalCustomer> legalCustomers = controller.searchLegalCustomer(request.getParameter("companyName"), request.getParameter("economicCode"), request.getParameter("customerNumber"));
            showSearchLegalCustomer(request, out, legalCustomers);
        }
        if("real".equals(request.getParameter("customerType"))){
            List<RealCustomer> realCustomers=controller.searchRealCustomer(request.getParameter("firstName"),request.getParameter("lastName"),request.getParameter("nationalCode"),request.getParameter("customerNumber"));
            showSearchRealCustomer(request,out,realCustomers);
        }

    }

    public void showSearchRealCustomer(HttpServletRequest request, PrintWriter out, List<RealCustomer> realCustomers) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div>\n" +
                "    <div><h1> show information</h1></div>\n" +
                "\n" +
                "    <div>\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <td> first Name--</td>\n" +
                "                <td> last name--</td>\n" +
                "                <td> father name--</td>\n" +
                "                <td> birth Date--</td>\n" +
                "                <td> customer Number--</td>\n" +
                "                <td> national code </td>\n" +
                "            </tr>\n");
        for (RealCustomer realCustomer : realCustomers) {
            out.println(
                    "            <tr>\n" +
                            "                <td>" + realCustomer.getFirstName() + "</td>\n" +
                            "                <td>" + realCustomer.getLastName() + "</td>\n" +
                            "                <td>" + realCustomer.getFatherName() + "</td>\n" +
                            "                <td>" + realCustomer.getBirthDay() + "</td>\n" +
                            "                <td>" + realCustomer.getCustomerNumber() + "</td>\n" +
                            "                <td>" + realCustomer.getNationalCode() + "</td>\n" +
                            "            </tr>\n");
        }
        out.println("        </table>\n" +
                "        <div>\n" +
                "            <a href=\"/ParameterReader\">back</a>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");


    }
    public void showSearchLegalCustomer(HttpServletRequest request, PrintWriter out, List<LegalCustomer> legalCustomers) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div>\n" +
                "    <div><h1> show information</h1></div>\n" +
                "\n" +
                "    <div>\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <td> company Name--</td>\n" +
                "                <td> economic code--</td>\n" +
                "                <td> customer Number--</td>\n" +
                "                <td> registration date</td>\n" +
                "            </tr>\n");
        for (LegalCustomer legalCustomer : legalCustomers) {
            out.println(
                    "            <tr>\n" +
                            "                <td>" + legalCustomer.getCompanyName() + "</td>\n" +
                            "                <td>" + legalCustomer.getEconomicCode() + "</td>\n" +
                            "                <td>" + legalCustomer.getCustomerNumber() + "</td>\n" +
                            "                <td>" + legalCustomer.getRegistrationDate() + "</td>\n" +
                            "            </tr>\n");
        }
        out.println("        </table>\n" +
                "        <div>\n" +
                "            <a href=\"/ParameterReader\">back</a>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");


    }
}
