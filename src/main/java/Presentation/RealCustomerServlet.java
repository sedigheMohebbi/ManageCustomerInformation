package presentation;


import business.RealCustomerBiz;
import exception.SqlException;
import exception.ValidationException;
import model.RealCustomer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RealCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String operation = request.getParameter("operation");
        if ("add".equals(operation)) {
            addRealCustomerPage(request, out);
        } else if ("save".equals(operation)) {
            RealCustomer realCustomer = null;
            try {
                realCustomer = RealCustomerBiz.getInstance().createAndSaveRealCustomer(request.getParameter("firstName"),
                        request.getParameter("lastName"), request.getParameter("nationalCode"), request.getParameter("birthDate"),
                        request.getParameter("fatherName"));
            } catch (SqlException e) {
                errorHtml(request, out, e);
            } catch (ValidationException e) {
                errorHtml(request, out, e);
            }
            showHtmlRealCustomer(request, out, realCustomer);
        } else if ("search".equals(operation)) {
            realCustomerSearchPage(request, out);
        } else if ("searchResult".equals(operation)) {
            List<RealCustomer> realCustomers = RealCustomerBiz.getInstance().searchRealCustomer(request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("nationalCode"), request.getParameter("customerNumber"));
            showSearchRealCustomer(request, out, realCustomers);

        } else if ("update".equals(operation)) {
            int id = Integer.parseInt(request.getParameter("id"));
            RealCustomer realCustomer = null;
            try {
                realCustomer = RealCustomerBiz.getInstance().findRealCustomer(id);
            } catch (SqlException e) {
                System.out.println(e.getMessage());
            }
            RealCustomerUpdatePage(request, out, realCustomer);

        } else if ("updateSave".equals(operation)) {
            try {
                RealCustomer realCustomer = RealCustomerBiz.getInstance().updateRealCustomer(request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("fatherName"), request.getParameter("nationalCode"),
                        request.getParameter("birthDate"), Integer.parseInt(request.getParameter("id")));
                showHtmlRealCustomer(request, out, realCustomer);
            } catch (SqlException e) {
                errorHtml(request, out, e);
            } catch (ValidationException e) {
                errorHtml(request, out, e);
            }

        } else if ("delete".equals(operation)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                RealCustomerBiz.getInstance().deleteRealCustomer(id);
                realCustomerDeletePage(request, out);

            } catch (SqlException e) {
                errorHtml(request, out, e);
            }
        } else {
            manageRealCustomer(out);
        }
    }

    private void manageRealCustomer(PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                " <div class=\"firstDiv\">\n" +
                "<body>\n" +
                " <div >\n" +
                "    <div  class=\"headerDiv\"><h1> customer manager</h1></div>\n" +
                "    <div class=\"selectFrame contentDiv\">\n" +
                "\n" +
                "        <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                "            <input type=\"submit\" value=\"add\" class=\"selectButton\"/>\n" +
                "<input type=\"hidden\" name=\"operation\" value=\"add\"/>" +
                "\n" +
                "        </form>\n" +
                "        <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                "            <input type=\"submit\" value=\"search\" class=\"selectButton\"/>\n" +
                "<input type=\"hidden\" name=\"operation\" value=\"search\"/>" +
                "\n" +
                "        </form>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    private void addRealCustomerPage(HttpServletRequest request, PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "    <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"firstDiv\">\n" +
                "    <div class=\"headerDiv\"><h1> Add Real Customer</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                "    <ul class=\"form-style-1\">\n" +
                "        <li>\n" +
                "            <label>First Name </label>\n" +
                "            <input type=\"text\" name=\"firstName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Last Name </label>\n" +
                "            <input type=\"text\" name=\"lastName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Father Name </label>\n" +
                "            <input type=\"text\" name=\"fatherName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Birth Date </label>\n" +
                "            <input type=\"text\" name=\"birthDate\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>national Code </label>\n" +
                "            <input type=\"text\" name=\"nationalCode\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <input type=\"submit\" value=\"save\" />\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "                <input type=\"hidden\" name=\"operation\" value=\"save\"/>" +
                "        </form>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void showHtmlRealCustomer(HttpServletRequest request, PrintWriter out, RealCustomer realCustomer) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "     <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=firstDiv>\n" +
                "    <div class=headerDiv><h1> Real Customer Show Information</h1></div>\n" +
                "    <div class=\"contentDiv\">\n" +
                "\n" +
                "\n" +
                "    <ul class=\"form-style-1\">\n" +
                "        <li>\n" +
                "            <label>Customer Number </label>\n" +
                "            <input type=\"text\"  class=\"field-long\" disabled value=\"" + realCustomer.getCustomerNumber() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>First Name </label>\n" +
                "            <input type=\"text\"  class=\"field-long\" disabled value=\"" + realCustomer.getFirstName() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Last Name </label>\n" +
                "            <input type=\"text\"  class=\"field-long\" disabled value=\"" + realCustomer.getLastName() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Father Name </label>\n" +
                "            <input type=\"text\"  class=\"field-long\"  disabled value=\"" + realCustomer.getFatherName() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Birth Date </label>\n" +
                "            <input type=\"text\" class=\"field-long\" disabled value=\"" + realCustomer.getBirthDate() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>national Code </label>\n" +
                "            <input type=\"text\" class=\"field-long\" disabled value=\"" + realCustomer.getNationalCode() + "\"/>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +
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
                "     <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=firstDiv>\n" +
                " <div class=\"headerDiv\"><h1> Error Page</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <p id=\"massage\">" + e.getMessage() + " </p>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
    }

    private void realCustomerSearchPage(HttpServletRequest request, PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "     <link href=\"base.css\"  rel=\"stylesheet\" />\n" +

                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=firstDiv>\n" +
                "    <div class=headerDiv><h1> Search Real Customer</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                "    <ul class=\"form-style-1\">\n" +
                "        <li>\n" +
                "            <label>Customer Number </label>\n" +
                "            <input type=\"text\" name=\"customerNumber\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>First Name </label>\n" +
                "            <input type=\"text\" name=\"firstName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Last Name </label>\n" +
                "            <input type=\"text\" name=\"lastName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Father Name </label>\n" +
                "            <input type=\"text\" name=\"fatherName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Birth Date </label>\n" +
                "            <input type=\"text\" name=\"birthDate\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>National Code </label>\n" +
                "            <input type=\"text\" name=\"nationalCode\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <input type=\"submit\" value=\"search\" />\n" +
                "        </li>\n" +
                "                <input type=\"hidden\" name=\"operation\" value=\"searchResult\"/>" +
                "    </ul>\n" +
                "</form>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void showSearchRealCustomer(HttpServletRequest request, PrintWriter out, List<RealCustomer> realCustomers) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Real Customer Manager</title>\n" +
                "     <link href=\"base.css\"  rel=\"stylesheet\" />\n" +

                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=firstDiv>\n" +
                "    <div class=headerDiv><h1> show information</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <table class=\"resultTable\"  cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;First Name&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Last Name&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Father Name&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp; Birth Date&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;National Code&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Customer Number&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Update&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Delete&nbsp;&nbsp;</td>\n" +
                "            </tr>\n");
        for (RealCustomer realCustomer : realCustomers) {
            out.println(
                    "            <tr>\n" +
                            "                <td>" + realCustomer.getFirstName() + "</td>\n" +
                            "                <td>" + realCustomer.getLastName() + "</td>\n" +
                            "                <td>" + realCustomer.getFatherName() + "</td>\n" +
                            "                <td>" + realCustomer.getBirthDate() + "</td>\n" +
                            "                <td>" + realCustomer.getNationalCode() + "</td>\n" +
                            "                <td>" + realCustomer.getCustomerNumber() + "</td>\n" +
                            "        <td>\n" +
                            "            <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                            "                <input type=\"submit\"  class=\"inputSubmit\" value=\"update\"/> \n" +
                            "                <input type=\"hidden\" name=\"operation\" value=\"update\"/>" +
                            "                <input type=\"hidden\" name=\"id\" value=\"" + realCustomer.getId() + "\"/>" +
                            "            </form>\n" +
                            "        </td>\n" +
                            "        <td>\n" +
                            "            <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                            "                <input type=\"submit\"  class=\"inputSubmit\" value=\"delete\"/> \n" +
                            "                <input type=\"hidden\" name=\"operation\" value=\"delete\"/>" +
                            "                <input type=\"hidden\" name=\"id\" value=\"" + realCustomer.getId() + "\"/>" +
                            "            </form>\n" +
                            "        </td>\n" +

                            "            </tr>\n");
        }
        out.println("        </table>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");


    }

    private void RealCustomerUpdatePage(HttpServletRequest request, PrintWriter out, RealCustomer realCustomer) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "     <link href=\"base.css\"  rel=\"stylesheet\" />\n" +

                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=firstDiv>\n" +
                "<div class=headerDiv><h1> Real Customer Update</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <form method=\"get\" action=\"/RealCustomerServlet\">\n" +
                "                   <ul class=\"form-style-1\">\n" +
                "                     <li>\n" +
                "                         <label>First Name </label>\n" +
                "                          <input type=\"text\" name=\"firstName\" value=\"" + realCustomer.getFirstName() + "\" class=\"field-long\"/>\n" +
                "                       </li>\n" +
                "                     <li>\n" +
                "                         <label>Last Name </label>\n" +
                "                          <input type=\"text\" name=\"lastName\" value=\"" + realCustomer.getLastName() + "\" class=\"field-long\"/>\n" +
                "                       </li>\n" +
                "                     <li>\n" +
                "                         <label>Father Name </label>\n" +
                "                          <input type=\"text\" name=\"fatherName\" value=\"" + realCustomer.getFatherName() + "\" class=\"field-long\"/>\n" +
                "                       </li>\n" +
                "                      <li>\n" +
                "                         <label>Birth Date </label>\n" +
                "                     <input type=\"text\" name=\"birthDate\" value=\"" + realCustomer.getBirthDate() + "\" class=\"field-long\" />\n" +
                "                     </li>\n" +
                "                      <li>\n" +
                "                         <label>National Code </label>\n" +
                "                           <input type=\"text\" name=\"nationalCode\" value=\"" + realCustomer.getNationalCode() + "\"class=\"field-long\" />\n" +
                "                     </li>\n" +
                "                    <li> \n" +
                "                        <input type=\"submit\" value=\"update\" />\n" +
                "                      </li>\n" +
                "<input type=\"hidden\" name=\"operation\" value=\"updateSave\"/>" +
                "<input type=\"hidden\" name=\"id\" value=\"" + realCustomer.getId() + "\"/>" +

                "  </ul>\n" +
                "</form>\n" +

                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void realCustomerDeletePage(HttpServletRequest request, PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>DELETE</title>\n" +
                "     <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=firstDiv>\n" +
                " <div class=\"headerDiv\"><h1> show information</h1></div>\n" +
                "    <div class=\"contentDiv\">\n" +
                "<p id=\"massage\">successful delete </p>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/RealCustomerServlet\">back</a>\n" +
                "</div>\n" +

                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}
