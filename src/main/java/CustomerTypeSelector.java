import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomerTypeSelector extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String customerType = request.getParameter("customerType");
        Boolean submited = Boolean.parseBoolean(request.getParameter("submited"));
        if (submited) {
            if (customerType == null) {
                out.println("<p> select customer Type</p>");
            } else if (customerType.equals("real")) {
                realCustomerPage(request, out);
            } else if (customerType.equals("legal")) {
                legalCustomerPage(request, out);
            } else {
                response.sendRedirect("http://localhost:9090/ParameterReader?submited=true");
            }
        }
    }

    private void legalCustomerPage(HttpServletRequest request, PrintWriter out) {
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
                "        <form method=\"get\" action=\"http://localhost:9090/SaveAndShowCustomer\">\n" +
                "            <fieldset>\n" +
                "                <label for=\"companyName\">Company Name: </label>\n" +
                "                <input type=\"text\" name=\"companyName\" value=\"\" id=\"companyName\"/>\n" +
                "                <label for=\"registrationDate\"> Registration Date: </label>\n" +
                "                <input type=\"text\" name=\"registrationDate\" value=\"\" id=\"registrationDate\"/>\n" +
                "                <label for=\"economicCode\"> economic Code : </label>\n" +
                "                <input type=\"text\" name=\"economicCode\" value=\"\" id=\"economicCode\"/>\n" +
                "            </fieldset>\n" +
                "            <input type=\"submit\" value=\"save\"/>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    private void realCustomerPage(HttpServletRequest request, PrintWriter out) {
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
                "        <form method=\"get\" action=\"http://localhost:9090/SaveAndShowCustomer\">\n" +
                "            <fieldset>\n" +
                "                <label for=\"firstName\">First Name: </label>\n" +
                "                <input type=\"text\" name=\"firstName\" value=\"\" id=\"firstName\"/>\n" +
                "                <label for=\"lastName\"> Last Name: </label>\n" +
                "                <input type=\"text\" name=\"lastName\" value=\"\" id=\"lastName\"/>\n" +
                "                <label for=\"fatherName\"> Father Name : </label>\n" +
                "                <input type=\"text\" name=\"fatherName\" value=\"\" id=\"fatherName\"/>\n" +
                "                <label for=\"birthDay\"> Birth Day : </label>\n" +
                "                <input type=\"text\" name=\"birthDay\" value=\"\" id=\"birthDay\"/>\n" +
                "                <label for=\"nationalCode\"> National Code : </label>\n" +
                "                <input type=\"text\" name=\"nationalCode\" value=\"\" id=\"nationalCode\"/>\n" +
                "            </fieldset>\n" +
                "            <input type=\"submit\" value=\"save\"/>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

    }
}
