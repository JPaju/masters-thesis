import java.util.*;
import java.io.*;

public class CheckedExceptions {
	public byte[] readFile(String fileName) throws IOException {
		var file = new File(fileName);
		var is = new FileInputStream(file); // can throw IOException
		return is.readAllBytes(); 			// can throw IOException
	}

	public void catchIt() {
		try { var bytes = readFile("file.txt"); }
		catch (IOException exc) { /* Handle error */ }
	}

	// Caller must handle IOException
	public void declareIt() throws IOException {
		var bytes = readFile("file.txt");
	}
}
