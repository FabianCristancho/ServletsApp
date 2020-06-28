import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class Ping extends HttpServlet {

	private FileWriter file;
	private PrintWriter pw;
	private String message;

   	public void init() throws ServletException {
      	message = "Ping a máquinas virtuales";
   	}

   	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<head><meta charset='UTF-8'></head>");
		out.println("<h3>PING MAQUINA 1<h3>");
		getServerPing("192.168.100.136", out);
		
		out.println("<br><br>");

		out.println("<h3>PING MAQUINA 2<h3>");
		getServerPing("192.168.100.161", out);
   	}

	public void getServerPing(String ip, PrintWriter out){
		try {
			Process p = Runtime.getRuntime().exec("ping " +ip);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";
		
			while ((s = inputStream.readLine()) != null) {
				out.println("<p>"+s+"</p>");
				saveLogs(s);
			}
		} catch (Exception e) {
			out.println("<p>"+e.getMessage()+"</p>");		
		}
	}

	public void saveLogs(String lineWrite) {
		PingFile pingFile = new PingFile("webapps/App/src/file/ping.log");
		pingFile.saveLogs(lineWrite);
		pingFile.closeFile();
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