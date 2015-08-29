import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ManageCustomer extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
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
                "    <div>\n" +
                "\n" +
                "        <form method=\"get\" action=\"/AddCustomer\">\n" +
                "            <input type=\"submit\" value=\"add\"/>\n" +
                "<input type=\"hidden\" name=\"customerType\" value=\""+request.getParameter("customerType")+"\"/>"+
                "\n" +
                "        </form>\n" +
                "        <form method=\"get\" action=\"/SearchCustomer\">\n" +
                "            <input type=\"submit\" value=\"search\"/>\n" +
               "<input type=\"hidden\" name=\"customerType\" value=\""+request.getParameter("customerType")+"\"/>"+
                "\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}
