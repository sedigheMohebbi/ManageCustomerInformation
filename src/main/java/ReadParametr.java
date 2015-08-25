import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReadParametr extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String title = "user input form";
        out.println("<html>\n" + "<head><title>" + title + "</title></head>\n" + "<h1 align=\"center\">" + title + "</h1>\n");
        out.println("<form method=get action=http://localhost:9090/ReadParametr>");
        out.println("Customer : <input type=radio name=customerType value=actual /> actual customer ");
        out.println(" <input type= radio  name= customerType  value= corporate /> corporate customer ");
        out.println("<input type= submit value=ok />");
        String customerType = request.getParameter("customerType");
        if (customerType.equals("actual")) {
            out.println("<p>Customer: actual</p>");
        } else {
            out.println("<p>Customer: corporate</p>");
        }
        out.println("</form>");
    }
}
