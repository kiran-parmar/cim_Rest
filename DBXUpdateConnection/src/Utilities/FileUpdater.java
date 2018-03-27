package Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUpdater {
	public static void main(String[] args) {
		
		//appendContent("test123");
	}
	
	public static void updateConnection(String identityStanza) {
		try (FileWriter fw = new FileWriter("..\\\\local\\\\db_connections.conf", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println("");
			out.println(identityStanza);
			
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
	public static void updateIdentity(String identityStanza) {
		try (FileWriter fw = new FileWriter("..\\\\local\\\\identities.conf", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println("");
			out.println(identityStanza);
			
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}
