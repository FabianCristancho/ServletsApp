import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

// Extend HttpServlet class
public class Ping extends HttpServlet {

	private PingFile pingFile;
	MyQueue<String> myQueue;

	public Ping(){
		this.pingFile = new PingFile("webapps/App/src/logs/ping.log");
		this.myQueue = new MyQueue<String>();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {      
						Thread.sleep(30000);     
						addRequest();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

   	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String server = request.getParameter("id");
		String title = "";
		String ip = "";
		String user = "";
		String password = "";
		boolean isPing = true;
		
		switch(server){
			case "1": 
				title = "PING DEL SERVIDOR 1";
				ip = "192.168.100.136";
				isPing = true;
			break;
			case "2": 
				title = "PING DEL SERVIDOR 2";
				ip = "192.168.100.161";
				isPing = true;
			break;
			case "3": 
				title = "REINICIO DE SERVIDOR 1";
				ip = "192.168.100.136";
				user = "server1";
				password = "1234567";
				isPing = false;
			break;
			case "4": 
				title = "REINICIO DE SERVIDOR 2";
				ip = "192.168.100.161";
				user = "server2";
				password = "1234567";
				isPing = false;
			break;
		}
		
		if(isPing)
			this.myQueue.addElement("Haciendo ping a servidor con ip " +ip);
		else
			this.myQueue.addElement("Reiniciando el servidor con ip " +ip);

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
					if(isPing)
						getServerPing(ip, out, true);
					else
						getRebootServer(ip, user, password);
				out.println("</div>");
			out.println("</body>");
		out.println("</html>");
	   }
	   

	public void addRequest(){
		if(!this.myQueue.isEmpty()){
			Date date = new Date();
			pingFile.saveLogs(date.toString());
			pingFile.saveLogs(this.myQueue.getElement());
			pingFile.saveLogs("");
		}
	}

	public void getServerPing(String ip, PrintWriter out, boolean flag){
		try {
			Process p = Runtime.getRuntime().exec("ping " +ip);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";
			Date date = new Date();

			while ((s = inputStream.readLine()) != null) {
				out.println("<p class='text-center'>"+s+"</p>");
			}
		} catch (Exception e) {
			out.println("<p>"+e.getMessage()+"</p>");		
		}
	}

	public void getRebootServer(String ip, String user, String password){
		JSch jsch = new JSch();

		Session session;
		try {

			// Open a Session to remote SSH server and Connect.
			// Set User and IP of the remote host and SSH port.
			session = jsch.getSession(user, ip, 22);
			// When we do SSH to a remote host for the 1st time or if key at the remote host
			// changes, we will be prompted to confirm the authenticity of remote host.
			// This check feature is controlled by StrictHostKeyChecking ssh parameter.
			// By default StrictHostKeyChecking is set to yes as a security measure.
			session.setConfig("StrictHostKeyChecking", "no");
			// Set password
			session.setPassword(password);
			session.connect();

			// create the execution channel over the session
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			// Set the command to execute on the channel and execute the command
			channelExec.setCommand("echo 1234567 | sudo -S reboot");
			channelExec.connect();

			// Get an InputStream from this channel and read messages, generated
			// by the executing command, from the remote side.
			InputStream in = channelExec.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			// Command execution completed here.

			// Retrieve the exit status of the executed command
			int exitStatus = channelExec.getExitStatus();
			if (exitStatus > 0) {
				System.out.println("Remote script exec error! " + exitStatus);
				System.out.println(channelExec.getErrStream());
			}
			// Disconnect the Session
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}