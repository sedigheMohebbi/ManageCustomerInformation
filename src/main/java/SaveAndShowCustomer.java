import exception.SqlException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class SaveAndShowCustomer extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        Controller controller=new Controller();
        try {
           LegalCustomer res= controller.createAndSaveLegalCustomer(request.getParameter("companyName"),
                      request.getParameter("registrationDate"),request.getParameter("economicCode"));
        } catch (SqlException e) {
            e.printStackTrace();
        }
//todo


    }
}
