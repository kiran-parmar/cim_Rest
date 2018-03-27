package Scheduler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import DAO.Connections;
import Database.DBMonitoring;
import Password.PasswordHandler;
import Utilities.FileReader;

public class VerifyConnection implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ArrayList<Connections> connections = new ArrayList<Connections>();
		connections = FileReader.getConnections();
		String connectionName = null;
		String plainPassword = null;
		boolean result = true;
		for (Connections connection : connections) {
			connectionName = connection.getIdentity();
			Map<String, String> credential = FileReader.getPassword(connectionName);
			Set<String> userNames = credential.keySet();
			for (String userName : userNames) {
				System.out.println("userName: "+userName);
				System.out.println("hashedPassword: " + credential.get(userName));
				String originalPassword = credential.get(userName);
				plainPassword = PasswordHandler.decrypt(credential.get(userName));

				if (plainPassword != null) {
					result = DBMonitoring.sqlServerConnection(connection.getHost(), connection.getPort(), userName.trim(), plainPassword.trim(), connection.getDatabase());
					//result = DBMonitoring.sqlServerConnection(connection.getHost(), connection.getPort(), userName, plainPassword, connection.getDatabase());
				}
				System.out.println("database connection status: " + result);
				
				if (!result) { // retrieve password from cyberArk plainPassword = ""; // test
					//newPassword will be coming from CyberArk Enterprise Password Vault
					String newPassword = "myuser";
					result = DBMonitoring.sqlServerConnection(connection.getHost(), connection.getPort(), userName.trim(), newPassword, connection.getDatabase()); 
					System.out.println("database connection status retest: "+result);
					if (result) {
						
						PasswordHandler.updatePassword(originalPassword, newPassword);
					}
				}
				

			}

		}

	}

}
