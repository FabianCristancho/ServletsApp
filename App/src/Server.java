import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class Server extends HttpServlet {
 
   private String message;

   public void init() throws ServletException {
      message = "Hacer ping a server2";
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
      
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      out.println("<a href='/App/ping'>" + message + "</a>");
   }

   public void destroy() {
      // do nothing.
   }
}
