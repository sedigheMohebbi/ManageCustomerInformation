import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ParameterReader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>customer input form</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div>\n" +
                "    <div><h1> customer input form</h1></div>\n" +
                "\n" +
                "    <div>\n");
        String customerType = request.getParameter("customerType");
        boolean submited = Boolean.parseBoolean(request.getParameter("submited"));
        if (submited) {
            if (customerType == null) {
                out.println("<p> select customer Type </p>");
            } else if (!"real".equals(customerType) || !"legal".equals(customerType)) {

                out.println("<p>invalid customer Type</p>");
            }
        }
        out.println(
                " <form method=\"get\" action=\"http://localhost:9090/ManageCustomer\">\n" +
                        "            <fieldset>\n" +
                        "                <input type=\"radio\" name=\"customerType\" value=\"real\" id=\"realCustomerType\" checked/>\n" +
                        "                <label for=\"realCustomerType\">Real model.Customer</label>\n" +
                        "                <input type=\"radio\" name=\"customerType\" value=\"legal\" id=\"legalCustomerType\"/>\n" +
                        "                <label for=\"legalCustomerType\">Legal model.Customer </label>\n" +
                        "            </fieldset>\n" +
                        "            <input type=\"submit\" value=\"ok\"/>" +
                        "<input type=\"hidden\" name=\"submited\" value=\"true\"/>");

        out.println(" </form>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}

