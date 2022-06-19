import  jakarta.servlet.http.*;
import  jakarta.servlet.*;
import  java.io.*;

public class HelloServlet  extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest res,HttpServletResponse res) throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<h1>Hello,World</h1>");

        out.println("</body>");
        out.println("</html>");
    }
}
