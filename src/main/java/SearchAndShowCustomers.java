import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class SearchAndShowCustomers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        Controller controller=new Controller();
        if("legal".equals(request.getParameter("customerType"))) {
            List<LegalCustomer> legalCustomers= controller.searchLegalCustomer(request.getParameter("companyName"),request.getParameter("economicCode"),request.getParameter("customerNumber"));
            showSearchLegalCustomer(request,out,legalCustomers);
        }
    }
    public void showSearchLegalCustomer(HttpServletRequest request, PrintWriter out,List<LegalCustomer> legalCustomers){
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
                "            </tr>\n" );
        for(int i=0;i<legalCustomers.size();i++){
        out.println(
                "            <tr>\n" +
                "                <td>"+legalCustomers.get(i).getCompanyName()+"</td>\n" +
                "                <td>"+legalCustomers.get(i).getEconomicCode()+"</td>\n" +
                "                <td>"+legalCustomers.get(i).getCustomerNumber()+"</td>\n" +
                "                <td>"+legalCustomers.get(i).getRegistrationDate()+"</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <div>\n" +
                "            <a href=\"/ParameterReader\">back</a>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

    }}
}
