import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PingFile {

	private FileWriter file;
	private PrintWriter pw;

	public PingFile(String pathFile) {
		try {
			file = new FileWriter(pathFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveLogs(String lineWrite) {
		pw = new PrintWriter(file);
		pw.println(lineWrite);
	}

	public void closeFile() {
		try {
			if (null != file)
				file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
