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



      try {

		Process p = Runtime.getRuntime().exec("ping 192.168.100.136");
		out.println("<p>Entra aqui</p>");
		BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(p.getInputStream()));

		String s = "";
		
		// reading output stream of the command
		while ((s = inputStream.readLine()) != null) {
			out.println("<p>"+s+"</p>");
		}
		out.println("<p>No entra en while</p>");

	} catch (Exception e) {
		out.println("<p>"+e.getMessage()+"</p>");
		
	}




      out.println("<p>Este es otro parrafo, arriba hay algo</p>");
   }

   public void destroy() {
      // do nothing.
   }
}
