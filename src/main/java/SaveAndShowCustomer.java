import exception.SqlException;
import exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveAndShowCustomer extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Controller controller = new Controller();
        if ("legal".equals(request.getParameter("customerType"))) {
            try {
                LegalCustomer res = controller.createAndSaveLegalCustomer(request.getParameter("companyName"),
                        request.getParameter("registrationDate"), request.getParameter("economicCode"));
                showHtmlLegalCustomerResult(request, out, res);
            } catch (SqlException e) {
                errorHtml(request, out, e);
            } catch (ValidationException e) {
                errorHtml(request, out, e);
            }
        } else if ("real".equals(request.getParameter("customerType"))) {
            try {
                RealCustomer realCustomer = controller.createAndSaveRealCustomer(request.getParameter("firstName"),
                        request.getParameter("lastName"), request.getParameter("nationalCode"), request.getParameter("birthDay"),
                        request.getParameter("fatherName"));
                showHtmlRealCustomer(request, out, realCustomer);


            } catch (SqlException e) {
                errorHtml(request, out, e);
            } catch (ValidationException e) {
                errorHtml(request, out, e);
            }
        }
//todo


    }

    public void showHtmlLegalCustomerResult(HttpServletRequest request, PrintWriter out, LegalCustomer result) {
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
                "    <div>\n" +
                "\n" +
                "\n" +
                "        <fieldset>\n" +
                "            <label for=\"customerNumber\">customer Number: </label>\n" +
                "            <input type=\"text\" value=\"" + result.getCustomerNumber() + "\" id=\"customerNumber\" disabled/>\n" +
                "            <label for=\"companyName\">company Name: </label>\n" +
                "            <input type=\"text\" value=\"" + result.getCompanyName() + "\" id=\"companyName\" disabled/>\n" +
                "            <label for=\"registrationDate\"> registrationDate: </label>\n" +
                "            <input type=\"text\" value=\"" + result.getRegistrationDate() + "\" id=\"registrationDate\" disabled/>\n" +
                "            <label for=\"economicCode\"> economic Code : </label>\n" +
                "            <input type=\"text\" value=\"" + result.getEconomicCode() + "\" id=\"economicCode\" disabled/>\n" +
                "\n" +
                "        </fieldset>\n" +
                "       <div>\n" +
                "           <a href=\"http://localhost:9090/ParameterReader\">back</a>\n" +
                "       </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void showHtmlRealCustomer(HttpServletRequest request, PrintWriter out, RealCustomer result) {
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
                "    <div>\n" +
                "\n" +
                "\n" +
                "        <fieldset>\n" +
                "            <label for=\"customerNumber\">customer Number: </label>\n" +
                "            <input type=\"text\" value=\"" + result.getCustomerNumber() + "\" id=\"customerNumber\" disabled/>\n" +
                "            <label for=\"firstName\"> First Name: </label>\n" +
                "            <input type=\"text\" value=\"" + result.getFirstName() + "\" id=\"firstName\" disabled/>\n" +
                "            <label for=\"lastName\"> Last Name: </label>\n" +
                "            <input type=\"text\" value=\"" + result.getLastName() + "\" id=\"lastName\" disabled/>\n" +
                "            <label for=\"fatherName\"> Father Name : </label>\n" +
                "            <input type=\"text\" value=\"" + result.getFatherName() + "\" id=\"fatherName\" disabled/>\n" +
                "            <label for=\"birthDate\"> Birth Date : </label>\n" +
                "            <input type=\"text\" value=\"" + result.getBirthDay() + "\" id=\"birthDate\" disabled/>\n" +
                "            <label for=\"nationalCode\"> National Code : </label>\n" +
                "            <input type=\"text\" value=\"" + result.getNationalCode() + "\" id=\"nationalCode\" disabled/>\n" +
                "            \n" +
                "\n" +
                "        </fieldset>\n" +
                "       <div>\n" +
                "           <a href=\"http://localhost:9090/ParameterReader\">back</a>\n" +
                "       </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void errorHtml(HttpServletRequest request, PrintWriter out, Exception e) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div>\n" +
                "    <div><h1> Error</h1></div>\n" +
                "\n" +
                "    <div>\n" +
                "        <p>" + e.getMessage() + " </p>\n" +
                "            <a href=\"/ParameterReader\">backe</a>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}
