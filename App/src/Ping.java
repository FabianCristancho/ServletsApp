import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

// Extend HttpServlet class
public class Ping extends HttpServlet {

	private FileWriter file;
	private PrintWriter pw;
	private String message;
	private PingFile pingFile;

	public Ping(){
		pingFile = new PingFile("webapps/App/src/logs/ping.log");
	}

   	public void init() throws ServletException {
      	message = "Ping a máquinas virtuales";
   	}

   	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String server = request.getParameter("id");
		String title = "";
		String ip = "";
		
		switch(server){
			case "1": 
				title = "PING DEL SERVIDOR 1";
				ip = "www.google.com";
			break;
			case "2": 
				title = "PING DEL SERVIDOR 2";
				ip = "192.168.100.161";
			break;
		}
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
					out.println("<h1 class='text-primary text-center m-5'>"+title+"</h1>");
					getServerPing(ip, out, true);
				out.println("</div>");
			out.println("</body>");
		out.println("</html>");
   	}

	public void getServerPing(String ip, PrintWriter out, boolean flag){
		try {
			Process p = Runtime.getRuntime().exec("ping " +ip);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";
			Date date = new Date();

			pingFile.saveLogs(date.toString());
			while ((s = inputStream.readLine()) != null) {
				if(flag)
					out.println("<p class='text-center'>"+s+"</p>");
				pingFile.saveLogs(s);
			}
			pingFile.saveLogs("-------------------------------------------------------------------------------");
			pingFile.saveLogs("");
			pingFile.closeFile();
		} catch (Exception e) {
			out.println("<p>"+e.getMessage()+"</p>");		
		}
	}

	public void closeFile() {
		try {
			if (null != file)
				file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

   	public void destroy() {
   	}
}