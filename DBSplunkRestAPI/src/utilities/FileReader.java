package utilities;

import java.io.File;
import java.util.Scanner;

public class FileReader {
	static String dir = System.getProperty("user.dir");
	public static void main(String[] args) {
		try {
			
			File text = new File(dir + "\\src\\utilities\\test.txt");
			System.out.println();
			Scanner scnr = new Scanner(text);
			while (scnr.hasNextLine()) {
				String line = scnr.nextLine();
				System.out.println(line);
			}
			scnr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
}
