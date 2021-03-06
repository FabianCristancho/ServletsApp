import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PingFile {

	private FileWriter file;
	private PrintWriter pw;
	private String pathFile;

	public PingFile(String pathFile) {
		try {
			this.pathFile = pathFile;
			file = new FileWriter(this.pathFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveLogs(String lineWrite) {
		try {
			file = new FileWriter(this.pathFile, true);
			pw = new PrintWriter(file);
			pw.println(lineWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != file)
					file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
