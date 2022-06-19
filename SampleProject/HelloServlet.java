import  jakarta.servlet.http.*;
import  jakarta.servlet.*;
import  java.io.*;

public class HelloServlet  extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        print(out);
    }

    void print(PrintWriter out){
        out.println("<html>");
        out.println("<body>");

        out.println("<h1>Hello,World</h1>");

        out.println("</body>");
        out.println("</html>");
    }

    public static void main(String args[]){
        PrintWriter out = new PrintWriter(System.out);

        HelloServlet hs = new HelloServlet();
        hs.print(out);
        out.flush();
    }
}
