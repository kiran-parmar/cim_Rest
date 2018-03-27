package Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.quartz.SchedulerException;

import Scheduler.ConnectionScheduler;

public class CSVReader {

	public static void main(String[] args) {

		String csvFile = "../connections.csv";
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			StringBuilder connectionSB = new StringBuilder();
			StringBuilder identitySB = new StringBuilder();
			int counter = 0;
			while ((line = br.readLine()) != null) {
				// use comma as separator
				if (counter > 0) {
					String[] dbx = line.split(cvsSplitBy);
					//connectionSB.append("\n");
					connectionSB.append("[" + dbx[0] + "]\n");
					connectionSB.append("connection_type = " + dbx[1] + "\n");
					connectionSB.append("database = " + dbx[2] + "\n");
					connectionSB.append("disabled = " + dbx[3] + "\n");
					connectionSB.append("host = " + dbx[4] + "\n");
					connectionSB.append("identity = " + dbx[5] + "\n");
					connectionSB.append("jdbcUseSSL = " + dbx[6] + "\n");
					connectionSB.append("localTimezoneConversionEnabled = " + dbx[7] + "\n");
					connectionSB.append("port = " + dbx[8] + "\n");
					connectionSB.append("readonly = " + dbx[9] + "\n");
					connectionSB.append("timezone = " + dbx[10]);
					//identitySB.append("\n");
					identitySB.append("[" + dbx[11] + "]\n");
					identitySB.append("disabled = " + dbx[12] + "\n");
					identitySB.append("password = " + dbx[13] + "\n");
					identitySB.append("use_win_auth = " + dbx[14] + "\n");
					identitySB.append("username = " + dbx[15]);
				}
				counter++;
			}
			FileUpdater.updateConnection(connectionSB.toString());
			FileUpdater.updateIdentity(identitySB.toString());
			try {
				ConnectionScheduler.updateConnection();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}