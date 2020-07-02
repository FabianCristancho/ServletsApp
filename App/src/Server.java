import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class Server extends HttpServlet {
 

   public Server(){
   }

   public void init() throws ServletException {
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
            out.println("<script> function myFunction() {alert('Reiniciar server');}</script>");
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
                           out.println("<p class='card-text'>Evalue la disponibilidad del servidor</p>");
                           out.println("<div class='text-center m-3'>");
							         out.println("<img class='image' src='https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQVoHezkPyHHh0kmB3FXvHFptcRvv7HeXkceg&usqp=CAU' alt='Server unavailable'>");
						         out.println("</div>");
                           out.println("<a href='/App/ping?id=1' class='btn btn-info'>Hacer Ping a servidor</a>");
                           out.println("<a href='/App/ping?id=3' class='btn btn-primary'>Reiniciar Servidor</a>");
                        out.println("</div>");
                     out.println("</div>");
                  out.println("</div>");

                  out.println("<div class='col-md-6'>");
                     out.println("<div class='card'>");
                        out.println("<div class='card-header text-info'>SERVIDOR 2</div>");
                        out.println("<div class='card-body'>");
                           out.println("<p class='card-text'>Evalue la disponibilidad del servidor</p>");
                           out.println("<div class='text-center m-3'>");
							         out.println("<img class='image' src='https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQVoHezkPyHHh0kmB3FXvHFptcRvv7HeXkceg&usqp=CAU' alt='Server unavailable'>");
						         out.println("</div>");
                           out.println("<a href='/App/ping?id=2' class='btn btn-info'>Hacer Ping a servidor</a>");
                           out.println("<a href='/App/ping?id=4' class='btn btn-primary'>Reiniciar Servidor</a>");
                        out.println("</div>");
                     out.println("</div>");
                  out.println("</div>");
               out.println("</div>");
            out.println("</div>");
         out.println("</body>");
         
      out.println("</html>");
   }

   private void getCustomPing(PrintWriter out){
      out.println("<div class='modal fade' id='exampleModal' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>");
         out.println("<div class='modal-dialog' role='document'>");
            out.println("<div class='modal-content'>");
               out.println("<div class='modal-header'>");
                     out.println("<h5 class='modal-title' id='exampleModalLabel'>Modal title</h5>");
                     out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                     out.println("<span aria-hidden='true'>&times;</span>");
                        out.println("</button>");
                        out.println("</div>");
                        out.println("<div class='modal-body'>");

                           out.println("<form action='/App/ping' method='post'>");
                              out.println("<div class='form-group'>");
                                 out.println("<label for='exampleInputEmail1'>IP</label>");
                                 out.println("<input type='text' class='form-control' id='exampleInputEmail1' name='ip' aria-describedby='emailHelp' placeholder='IP de servidor'>");
                              out.println("</div>");
                              out.println("<div class='form-group'>");
                                 out.println("<label for='exampleInputEmail1'>SERVIDOR</label>");
                                 out.println("<input type='text' class='form-control' id='exampleInputEmail1' name='user' aria-describedby='emailHelp' placeholder='Nombre de usuario'>");
                              out.println("</div>");
                              out.println("<div class='form-group'>");
                                 out.println("<label for='exampleInputPassword1'>CONTRASENIA</label>");
                                 out.println("<input type='password' class='form-control' id='exampleInputPassword1' name='password' placeholder='Contrasenia de servidor'>");
                              out.println("</div>");
                              out.println("<button type='submit' class='btn btn-primary'>Enviar</button>");
                           out.println("</form>");

                        out.println("</div>");
                        out.println("<div class='modal-footer'>");
                           out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
                        out.println("</div>");
                     out.println("</div>");
                  out.println("</div>");
      out.println("</div>");
   }
}
