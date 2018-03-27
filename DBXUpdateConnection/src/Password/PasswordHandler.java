package Password;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import Utilities.FileReader;
import Utilities.FileReplacer;

public class PasswordHandler {
	

	public static void main(String[] args) {
		decrypt("U2FsdGVkX19J6W37GYcix9oF1PeKlsUXjEph7mNY9Gw=");
		encrypt("myuser");
	}

	private static String executeCommand(String command) {
		StringBuilder output = new StringBuilder();
		//System.out.println("command: " + command);
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			//BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
			if (output.length() == 0) {
				while ((line = reader.readLine()) != null) {
					output.append(line);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	public static String decrypt(String cipherText) {
		String filePath = FileReader.readFile("..\\openSSLLocation.txt").trim();
		String cmd = "powershell.exe /c echo " + cipherText
				+ " | "+filePath+"openssl aes-256-cbc -d -base64 -pass file:..\\certs\\identity.dat";

		String plainPassword = executeCommand(cmd);
		
		System.out.println("plainPassword: " + plainPassword);
		return plainPassword;
	}

	public static String encrypt(String plainText) {
		String filePath = FileReader.readFile("..\\openSSLLocation.txt").trim();
		String cmd = "powershell.exe /c echo -n " + plainText
				+ " | "+filePath+"openssl aes-256-cbc -e -base64 -pass file:..\\certs\\identity.dat";
		String passwordHash = executeCommand(cmd);
		System.out.println("hashed " + passwordHash);
		
		return passwordHash;
	}

	public static void updatePassword(String originalPassword, String plainPassword) {
		FileReplacer.updateContent(originalPassword.trim(), encrypt(plainPassword).trim());
	}
}
