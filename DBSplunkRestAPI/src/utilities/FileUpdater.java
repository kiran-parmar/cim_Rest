package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUpdater {
	public static void main(String[] args) {
		String dir = System.getProperty("user.dir");
		String fileLocation = dir + "\\src\\utilities\\test.txt";
		//appendContent(fileLocation, VerifyDBConnection.sqlServer());
		appendContent(fileLocation, "test123");
	}
	
	public static void appendContent(String fileLocation, String test) {
		try (FileWriter fw = new FileWriter(fileLocation, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println("");
			out.println("the text");
			// more code
			out.println("more text");
			// more code
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}
