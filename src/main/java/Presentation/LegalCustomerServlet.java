package presentation;

import business.LegalCustomerBiz;
import exception.SqlException;
import exception.ValidationException;
import model.LegalCustomer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class LegalCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String operation = request.getParameter("operation");
        if ("add".equals(operation)) {
            addLegalCustomerPage(request, out);
            //comment
        } else if ("save".equals(operation)) {
            try {
                LegalCustomer res = LegalCustomerBiz.getInstance().createAndSaveLegalCustomer(request.getParameter("companyName"),
                        request.getParameter("registrationDate"), request.getParameter("economicCode"));
                showHtmlLegalCustomerResult(request, out, res);
            } catch (SqlException e) {
                errorHtml(request, out, e);
            } catch (ValidationException e) {
                errorHtml(request, out, e);
            }
        } else if ("search".equals(operation)) {
            legalCustomerSearchPage(request, out);
        } else if ("searchResult".equals(operation)) {
            List<LegalCustomer> legalCustomers = LegalCustomerBiz.getInstance().searchLegalCustomer(request.getParameter("companyName"), request.getParameter("economicCode"), request.getParameter("customerNumber"));
            showSearchLegalCustomer(request, out, legalCustomers);
        } else if ("update".equals(operation)) {
            int id = Integer.parseInt(request.getParameter("id"));
            LegalCustomer legalCustomer = null;
            try {
                legalCustomer = LegalCustomerBiz.getInstance().findLegalCustomer(id);
            } catch (SqlException e) {
                System.out.println(e.getMessage());
            }
            legalCustomerUpdatePage(request, out, legalCustomer);

        } else if ("updateSave".equals(operation)) {
            try {
                LegalCustomer legalCustomer = LegalCustomerBiz.getInstance().updateLegal(request.getParameter("companyName"), request.getParameter("economicCode"),
                        request.getParameter("registrationDate"), Integer.parseInt(request.getParameter("id")));
                showHtmlLegalCustomerResult(request, out, legalCustomer);
            } catch (SqlException e) {
                errorHtml(request, out, e);
            } catch (ValidationException e) {
                errorHtml(request, out, e);
            }
        } else if ("delete".equals(operation)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                LegalCustomerBiz.getInstance().deleteLegalCustomer(id);
                legalCustomerDeletePage(request, out);

            } catch (SqlException e) {
                errorHtml(request, out, e);
            }
        } else {
            manageLegalCustomer(out);

        }
    }

    private void manageLegalCustomer(PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "    <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                " <div class=\"firstDiv\">\n" +

                "<body>\n" +
                " <div >\n" +
                "    <div  class=\"headerDiv\"><h1> customer manager</h1></div>\n" +
                "    <div class=\"selectFrame contentDiv\">\n" +
                "\n" +
                "        <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                "            <input type=\"submit\" value=\"add\" class=\"selectButton\"/>\n" +
                "<input type=\"hidden\" name=\"operation\" value=\"add\"/>" +
                "\n" +
                "        </form>\n" +
                "        <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                "            <input type=\"submit\" value=\"search\" class=\"selectButton\"/>\n" +
                "<input type=\"hidden\" name=\"operation\" value=\"search\"/>" +
                "\n" +
                "        </form>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    private void addLegalCustomerPage(HttpServletRequest request, PrintWriter out) {
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Customer Manager</title>\n" +
                "    <link href=\"base.css\"  rel=\"stylesheet\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"firstDiv\">\n" +
                "    <div class=\"headerDiv\"><h1> Add Legal Customer</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                "    <ul class=\"form-style-1\">\n" +
                "        <li>\n" +
                "            <label>Company Name </label>\n" +
                "            <input type=\"text\" name=\"companyName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Registration Date </label>\n" +
                "            <input type=\"text\" name=\"registrationDate\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Economic Code </label>\n" +
                "            <input type=\"text\" name=\"economicCode\" class=\"field-long\" />\n" +
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
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
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
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void showHtmlLegalCustomerResult(HttpServletRequest request, PrintWriter out, LegalCustomer result) {
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
                "    <div class=headerDiv><h1> Legal Customer Show Information</h1></div>\n" +
                "    <div class=\"contentDiv\">\n" +
                "\n" +
                "\n" +
                "    <ul class=\"form-style-1\">\n" +
                "        <li>\n" +
                "            <label>Customer Number </label>\n" +
                "            <input type=\"text\"  class=\"field-long\" disabled value=\"" + result.getCustomerNumber() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Company Name </label>\n" +
                "            <input type=\"text\"  class=\"field-long\"  disabled value=\"" + result.getCompanyName() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Registration Date </label>\n" +
                "            <input type=\"text\" class=\"field-long\" disabled value=\"" + result.getRegistrationDate() + "\"/>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Economic Code </label>\n" +
                "            <input type=\"text\" class=\"field-long\" disabled value=\"" + result.getEconomicCode() + "\"/>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    private void legalCustomerSearchPage(HttpServletRequest request, PrintWriter out) {
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
                "    <div class=headerDiv><h1> Search Legal Customer</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                "    <ul class=\"form-style-1\">\n" +
                "        <li>\n" +
                "            <label>Customer Number </label>\n" +
                "            <input type=\"text\" name=\"customerNumber\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Company Name </label>\n" +
                "            <input type=\"text\" name=\"companyName\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Registration Date </label>\n" +
                "            <input type=\"text\" name=\"registrationDate\" class=\"field-long\" />\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <label>Economic Code </label>\n" +
                "            <input type=\"text\" name=\"economicCode\" class=\"field-long\" />\n" +
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
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
                "</div>\n" +
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
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Company Name&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Economic Code&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Customer Number&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Registration Date&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Update&nbsp;&nbsp;</td>\n" +
                "                <td class=\"resultTableHeader\"> &nbsp;&nbsp;Delete&nbsp;&nbsp;</td>\n" +
                "            </tr>\n");
        for (LegalCustomer legalCustomer : legalCustomers) {
            out.println(
                    "            <tr>\n" +
                            "                <td>" + legalCustomer.getCompanyName() + "</td>\n" +
                            "                <td>" + legalCustomer.getEconomicCode() + "</td>\n" +
                            "                <td>" + legalCustomer.getCustomerNumber() + "</td>\n" +
                            "                <td>" + legalCustomer.getRegistrationDate() + "</td>\n" +
                            "        <td>\n" +
                            "            <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                            "                <input type=\"submit\"  class=\"inputSubmit\" value=\"update\"/> \n" +
                            "                <input type=\"hidden\" name=\"operation\" value=\"update\"/>" +
                            "                <input type=\"hidden\" name=\"id\" value=\"" + legalCustomer.getId() + "\"/>" +
                            "            </form>\n" +
                            "        </td>\n" +
                            "        <td>\n" +
                            "            <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                            "                <input type=\"submit\"  class=\"inputSubmit\" value=\"delete\"/> \n" +
                            "                <input type=\"hidden\" name=\"operation\" value=\"delete\"/>" +
                            "                <input type=\"hidden\" name=\"id\" value=\"" + legalCustomer.getId() + "\"/>" +
                            "            </form>\n" +
                            "        </td>\n" +

                            "            </tr>\n");
        }
        out.println(" </table>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "        </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>");


    }

    private void legalCustomerUpdatePage(HttpServletRequest request, PrintWriter out, LegalCustomer legalCustomer) {
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
                "<div class=headerDiv><h1> Legal Customer Update</h1></div>\n" +
                "\n" +
                "    <div class=\"contentDiv\">\n" +
                "        <form method=\"get\" action=\"/LegalCustomerServlet\">\n" +
                "                   <ul class=\"form-style-1\">\n" +
                "                     <li>\n" +
                "                         <label>Company Name </label>\n" +
                "                          <input type=\"text\" name=\"companyName\" value=\"" + legalCustomer.getCompanyName() + "\" class=\"field-long\"/>\n" +
                "                       </li>\n" +
                "                      <li>\n" +
                "                         <label>Registration Date </label>\n" +
                "                     <input type=\"text\" name=\"registrationDate\" value=\"" + legalCustomer.getRegistrationDate() + "\" class=\"field-long\" />\n" +
                "                     </li>\n" +
                "                      <li>\n" +
                "                         <label>Economic Code </label>\n" +
                "                           <input type=\"text\" name=\"economicCode\" value=\"" + legalCustomer.getEconomicCode() + "\"class=\"field-long\" />\n" +
                "                     </li>\n" +
                "                    <li> \n" +
                "                        <input type=\"submit\" value=\"update\" />\n" +
                "                      </li>\n" +
                "<input type=\"hidden\" name=\"operation\" value=\"updateSave\"/>" +
                "<input type=\"hidden\" name=\"id\" value=\"" + legalCustomer.getId() + "\"/>" +

                "  </ul>\n" +
                "</form>\n" +
                "    </div>\n" +
                "<div class=\"backDiv\">\n" +
                "       <a href=\"/index.html\">home</a>\n" +
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public void legalCustomerDeletePage(HttpServletRequest request, PrintWriter out) {
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
                "       <a href=\"/LegalCustomerServlet\">back</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }
}


