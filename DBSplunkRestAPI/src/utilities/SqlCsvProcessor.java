package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SqlCsvProcessor {
	public static void main(String[] args) {
		loadOracleSQL();
		loadSqlServer();
	}

	@SuppressWarnings("unchecked")
	public static void loadOracleSQL() {
		String dir = System.getProperty("user.dir");
		File csvFile = new File(dir + "\\src\\utilities\\Oracle.csv");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		JSONArray sqlArray = new JSONArray();
		JSONObject sql = null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int lineCounter = 0;
			while ((line = br.readLine()) != null) {
				if (lineCounter > 0) {
					sql = new JSONObject();
					// use comma as separator
					String[] metrics = line.split(cvsSplitBy);
					sql.put("sourceType", metrics[0]);
					StringBuilder sb = new StringBuilder();
					for (int counter = 0; counter < metrics.length; counter++) {
						if (counter >= 1)
							sb = sb.append(metrics[counter]+",");
					}
					String str = sb.toString();
					str = str.toString().substring(0, str.length() - 1);
					sql.put("SQL", str);
					sqlArray.add(sql);
				}
				lineCounter++;
			}
			if (sqlArray != null) {
				JsonUtil.jsonWriter(sqlArray, "Oracle.json");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadSqlServer() {
		String dir = System.getProperty("user.dir");
		File csvFile = new File(dir + "\\src\\utilities\\SqlServer.csv");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		JSONArray sqlArray = new JSONArray();
		JSONObject sql = null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int lineCounter = 0;
			while ((line = br.readLine()) != null) {
				if (lineCounter > 0) {
					sql = new JSONObject();
					// use comma as separator
					String[] metric = line.split(cvsSplitBy);

					sql.put("group", metric[0]);
					
					sql.put("sourceType", metric[1]);
					//System.out.println(metric[0] +" - "+ metric[1]);
					StringBuilder sb = new StringBuilder();
					for (int counter = 0; counter < metric.length; counter++) {
						if (counter >= 2)
							sb = sb.append(metric[counter]+", ");
					}
					String str = sb.toString();
					str = str.toString().substring(0, str.length() - 1);
					sql.put("SQL", str);
					sqlArray.add(sql);
				}
				lineCounter++;
			}
			if (sqlArray != null) {
				JsonUtil.jsonWriter(sqlArray, "SQLServer.json");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
