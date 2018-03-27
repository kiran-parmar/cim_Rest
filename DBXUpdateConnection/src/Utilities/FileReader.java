package Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import DAO.Connections;
import DAO.Identities;

public class FileReader {
	static String dir = System.getProperty("user.dir");

	public static void main(String[] args) {
		try {

			ArrayList<Connections> connections = getConnections();
			for (Connections connection : connections) {
				System.out.println("identity: " + connection.getIdentity());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static String readFile(String file) {
		String encryptionKey = null;
		try {

			File text = new File(file);
			Scanner scnr = new Scanner(text);
			while (scnr.hasNextLine()) {
				encryptionKey = scnr.nextLine();
			}
			scnr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encryptionKey.trim();
	}
	public static ArrayList<Identities> getIdentities() {
		ArrayList<Identities> identities = new ArrayList<Identities>();
		try {

			File text = new File("..\\local\\identities.conf");
			Scanner scnr = new Scanner(text);
			Identities identity = null;
			String currentLine = null;
			while (scnr.hasNextLine()) {
				currentLine = scnr.nextLine();
				if (currentLine.contains("[") && currentLine.contains("]")) {
					if (identity != null) {
						if (identity.getDisabled() == 0) {
							identities.add(identity);
						}
					}
					identity = new Identities();
					currentLine = currentLine.replace("[", "");
					currentLine = currentLine.replace("]", "");
					identity.setConnection(currentLine);
				} else if (currentLine.contains("disabled")) {
					currentLine = currentLine.replace("disabled = ", "");
					identity.setDisabled(Integer.parseInt(currentLine));
				} else if (currentLine.contains("password")) {
					currentLine = currentLine.replaceAll("password = ", "");
					identity.setPassword(currentLine);
				} else if (currentLine.contains("username")) {
					currentLine = currentLine.replace("username = ", "");
					identity.setUserName(currentLine);
				} else if (currentLine.contains("use_win_auth")) {
					currentLine = currentLine.replace("use_win_auth = ", "");
					identity.setUseWinAuth(Integer.parseInt(currentLine));
				}
			}
			identities.add(identity);
			scnr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return identities;
	}

	public static ArrayList<Connections> getConnections() {
		ArrayList<Connections> connections = new ArrayList<Connections>();
		try {

			File text = new File("..\\local\\db_connections.conf");
			Scanner scnr = new Scanner(text);
			Connections connection = null;
			String currentLine = null;
			while (scnr.hasNextLine()) {
				currentLine = scnr.nextLine();
				if (currentLine.contains("[") && currentLine.contains("]")) {
					if (connection != null) {
						if (connection.getDisabled() == 0) {
							connections.add(connection);
						}
					}
					connection = new Connections();
					currentLine = currentLine.replace("[", "");
					currentLine = currentLine.replace("]", "");
					connection.setConnectionName(currentLine);
				} else if (currentLine.contains("disabled")) {
					currentLine = currentLine.replace("disabled = ", "");
					connection.setDisabled(Integer.parseInt(currentLine));
				} else if (currentLine.contains("connection_type")) {
					currentLine = currentLine.replaceAll("connection_type ", "");
					connection.setConnectionType(currentLine);
				} else if (currentLine.contains("database")) {
					currentLine = currentLine.replace("database = ", "");
					connection.setDatabase(currentLine);
				} else if (currentLine.contains("port")) {
					currentLine = currentLine.replace("port = ", "");
					connection.setPort(Integer.parseInt(currentLine));
				} else if (currentLine.contains("host")) {
					currentLine = currentLine.replace("host = ", "");
					connection.setHost(currentLine);
				} else if (currentLine.contains("identity")) {
					currentLine = currentLine.replace("identity = ", "");
					connection.setIdentity(currentLine);
				} else if (currentLine.contains("timezone")) {
					currentLine = currentLine.replace("timezone = ", "");
					connection.setTimezone(currentLine);
				} else if (currentLine.contains("jdbcUseSSL")) {
					currentLine = currentLine.replace("jdbcUseSSL = ", "");
					connection.setJdbcUseSSL(Boolean.parseBoolean(currentLine));
				} else if (currentLine.contains("localTimezoneConversionEnabled")) {
					currentLine = currentLine.replace("localTimezoneConversionEnabled = ", "");
					connection.setLocalTimezoneConversionEnabled(Boolean.parseBoolean(currentLine));
				} else if (currentLine.contains("readonly")) {
					currentLine = currentLine.replace("readonly = ", "");
					connection.setReadonly(Boolean.parseBoolean(currentLine));
				}
			}
			connections.add(connection);
			scnr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connections;
	}

	public static Map<String, String> getPassword(String connectionName) {
		Map<String, String> credential = new HashMap<String, String>();
		ArrayList<Identities> identities = getIdentities();
		for (Identities identity : identities) {
			if (identity.getConnection().equalsIgnoreCase(connectionName)) {
				credential.put(identity.getUserName(), identity.getPassword());
				return credential;
			}
		}
		return null;
	}

}
