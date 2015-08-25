import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class Hola extends HttpServlet {


    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;Charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<head>");
        printWriter.println("<title> hola</title></head>");
        printWriter.println("<body>");
        printWriter.println("<h1>hola come estan</h1></body>");

    }
}
