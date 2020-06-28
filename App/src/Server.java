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
      out.println("<!DOCTYPE html>");
      out.println("<html>");
         out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Administrador de servidores</title>");
            out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css' integrity='sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk' crossorigin='anonymous'>");
            out.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js' integrity='sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj' crossorigin='anonymous'></script>");
            out.println("<script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js' integrity='sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo' crossorigin='anonymous'></script>");
            out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js' integrity='sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI' crossorigin='anonymous'></script>");
         out.println("</head>");

         out.println("<body>");
            out.println("<div class='container'>");
               out.println("<h1 class='text-center text-primary m-5'>ADMINISTRADOR DE SERVIDORES</h1>");

               out.print("<div class='row'>");
                  out.println("<div class='col-md-6'>");
                     out.println("<div class='card'>");
                        out.println("<div class='card-header text-info'>SERVIDOR 1</div>");
                        out.println("<div class='card-body'>");
                           out.println("<h5 class='card-title'>Otro Título</h5>");
                           out.println("<p class='card-text'>With supporting text below as a natural lead-in to additional content.</p>");
                           out.println("<a href='/App/ping' class='btn btn-primary'>Go somewhere</a>");
                        out.println("</div>");
                     out.println("</div>");
                  out.println("</div>");
                  out.println("<div class='col-md-6'>");
                     out.println("<a href='/App/ping'>" + message + "</a>");
                     out.println("<a href='#' class='btn btn-primary'> Ping </a>");
                  out.println("</div>");
               out.println("</div>");
            out.println("</div>");
         out.println("</body>");
         
      out.println("</html>");
   }

   public void destroy() {
      // do nothing.
   }
}
