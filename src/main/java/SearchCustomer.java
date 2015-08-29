import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchCustomer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String customerType = request.getParameter("customerType");

        if (customerType == null) {
            out.println("<p> select customer Type</p>");
        } else if ("real".equals(customerType)) {
          //  realCustomerPage(request, out);
        } else if ("legal".equals(customerType)) {
            legalCustomerSearchPage(request, out);
        } else {
            response.sendRedirect("http://localhost:9090/ParameterReader?submited=true");
        }

    }
    private void legalCustomerSearchPage(HttpServletRequest request, PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div>\n" +
                "    <div><h1> customer manager</h1></div>\n" +
                "\n" +
                "    <div>\n" +
                "        <form method=\"get\" action=\"http://localhost:9090/SearchAndShowCustomer\">\n" +
                "            <fieldset>\n" +
                "                <label for=\"companyName\">Company Name: </label>\n" +
                "                <input type=\"text\" name=\"companyName\" value=\"\" id=\"companyName\"/>\n" +
                "                <label for=\"registrationDate\"> Registration Date: </label>\n" +
                "                <input type=\"text\" name=\"registrationDate\" value=\"\" id=\"registrationDate\"/>\n" +
                "                <label for=\"economicCode\"> economic Code : </label>\n" +
                "                <input type=\"text\" name=\"economicCode\" value=\"\" id=\"economicCode\"/>\n" +
                "                <input type=\"hidden\" name=\"customerType\" value=\"legal\"/>" +
                "            </fieldset>\n" +
                "            <input type=\"submit\" value=\"search\"/>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}
