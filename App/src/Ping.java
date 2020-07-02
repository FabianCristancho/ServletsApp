import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.net.InetSocketAddress;
import java.net.Socket;

// Extend HttpServlet class
public class Ping extends HttpServlet {

	private PingFile pingFile;
	private String messageLog;
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

		if(Integer.parseInt(server)%2!=0){
			//Servidor 1
			ip = "192.168.100.137"; 
			user = "server1";
			password = "1234567";
		}else{
			//Servidor 2
			ip = "192.168.100.161"; 
			user = "server2";
			password = "1234567";
		}
		
		switch(server){
			case "1": 
				title = "PING DEL SERVIDOR 1";
				isPing = true;
			break;
			case "2": 
				title = "PING DEL SERVIDOR 2";
				isPing = true;
			break;
			case "3": 
				title = "REINICIO DE SERVIDOR 1";
				isPing = false;
			break;
			case "4": 
				title = "REINICIO DE SERVIDOR 2";
				isPing = false;
			break;
		}
		
		// if(isPing){
		// 	this.myQueue.addElement("Haciendo ping a servidor con ip " +ip +"\n");
		// }else{
		// 	this.myQueue.addElement("Reiniciando el servidor con ip " +ip +"\n");
		// }

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	
		out.println("<!DOCTYPE html>");
      	out.println("<html>");
         		out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<title>Administrador de servidores</title>");
				out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css' integrity='sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk' crossorigin='anonymous'>");
				out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js'></script>");
				out.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js' integrity='sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj' crossorigin='anonymous'></script>");
				out.println("<script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js' integrity='sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo' crossorigin='anonymous'></script>");
				out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js' integrity='sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI' crossorigin='anonymous'></script>");
			out.println("</head>");

			out.println("<body>");
				out.println("<div class='container'>");
					out.println("<h1 class='text-primary text-center mt-4 mb-1'>"+title+"</h1>");
					out.println("<h5 class='text-center mb-3'>"+ip+"</h5>");

					if(isReachable(ip, 22, 3000)){
						if(isPing)
							getServerPing(ip, out, true);
						else
							getRebootServer(out, ip, user, password);
					}else{
						out.println("<p class='text-center text-danger mt-4'>Lo sentimos, el servidor no esta disponible en este momento :( </p>");
						out.println("<div class='text-center'>");
							out.println("<img class='image' src='https://thumbs.dreamstime.com/b/icono-del-vector-color-glyph-servidor-error-elementos-para-los-apps-m-viles-concepto-y-web-l-nea-fina-iconos-el-dise-o-desarrollo-146391626.jpg' alt='Server unavailable' width='200' height='220'>");
						out.println("</div>");
						out.println("<div class='text-center'>");
							out.println("<a href='/App/api' class='btn btn-info'>Regresar</a>");
						out.println("</div>");
						this.myQueue.addElement("Fallo al hacer ping. Servidor con IP " +ip +" no disponible \n");
					}
				out.println("</div>");
			out.println("</body>");
		out.println("</html>");
	   }
	
	   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   doGet(request, response);
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
					out.println("<h1 class='text-primary text-center m-5'>REINICIO DE SERVIDOR</h1>");
					out.println("<p class='text-primary text-center m-5'>IP: "+request.getParameter("ip")+"</p>");
					getRebootServer(out, request.getParameter("ip"), request.getParameter("user"), request.getParameter("password"));
				out.println("</div>");
		   	out.println("</body>");
		   out.println("</html>");
	   }
	   

	public void addRequest(){
		if(!this.myQueue.isEmpty())
			addEventLogger(this.myQueue.getElement());
	}

	/**
	 * Agrega un evento al archivo log
	 * @param path Ruta del archivo
	 * @param message Mensaje que se agrega al log
	 */
	public static void addEventLogger(String message) {

		Logger logger = Logger.getGlobal();
		FileHandler fh;

		try {

			fh = new FileHandler("webapps/App/src/logs/ping.log", true);
			logger.addHandler(fh);

			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			logger.info(message);
			fh.close();

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getServerPing(String ip, PrintWriter out, boolean flag){

		try {
			Process p = Runtime.getRuntime().exec("ping " +ip);  
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));	
			String s = "";
			Date date = new Date();
			out.println("<div class='text-center'>");
				out.println("<img class='image' src='https://icon-library.com/images/icon-ping/icon-ping-28.jpg' alt='Server Ping' width='200' height='220'>");
			out.println("</div>");
			out.println("<div class='text-center'>");
				out.println("<a href='/App/api' class='btn btn-info'>Regresar</a>");
			out.println("</div>");
			while ((s = inputStream.readLine()) != null) {
				out.println("<p class='text-center'>"+s+"</p>");
			}
			this.myQueue.addElement("Haciendo ping a servidor con IP " +ip +"\n");
		} catch (Exception e) {
			out.println("<p>"+e.getMessage()+"</p>");		
			this.myQueue.addElement("[ERROR] Fallo al hacer ping a servidor con IP " +ip +": " +e.getMessage());
		}
	}

	public void getRebootServer(PrintWriter out, String ip, String user, String password){
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
			channelExec.setCommand("echo " +password +" | sudo -S reboot");
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
				out.println("<p class='text-center text-danger'>Ha ocurrido un error al reiniciar el servidor, compruebe los permisos de root </p>");
				this.myQueue.addElement("Remote script exec error! " + exitStatus +"\n");
				this.myQueue.addElement(channelExec.getErrStream() +"\n");
			}else{
				out.println("<p class='text-center text-success'>El servidor se esta reiniciando satisfactoriamente </p>");
				out.println("<div class='text-center mb-3'> <img src='https://66.media.tumblr.com/3c66642b189119f521255b9c9e5a9903/tumblr_oa04ubDck91r2gwz1o1_400.gifv' alt='Reboot server'> </div>");
				out.println("<div class='text-center'>");
					out.println("<a href='/App/api' class='btn btn-info'>Regresar</a>");
				out.println("</div>");
				this.myQueue.addElement("Reiniciando el servidor con ip " +ip +"\n");
			}
			// Disconnect the Session
			session.disconnect();
		} catch (JSchException e) {
			this.myQueue.addElement("[ERROR] Ha ocurrido un problema al reiniciar el servidor con IP " +ip +". Compruebe los permisos del servidor\n");
		} catch (IOException e) {
			this.myQueue.addElement("[ERROR] Ha ocurrido un problema al reiniciar el servidor con IP " +ip +"\n");
			e.printStackTrace();
		}
	}

	
	private boolean isReachable(String address, int port, int time) {
		try {
			try (Socket s = new Socket()){
				s.connect(new InetSocketAddress(address, port), time);
			}
			return true;
		}catch (IOException e) {
			return false;
		}
	}
}