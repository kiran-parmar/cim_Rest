package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import database.SqlServerMetric;

public class JsonUtil {
	
	public static void main(String[] args) {
		jsonReader();
	}
	
	public static void jsonWriter(JSONArray sqlArray, String fileName) {

		 try (FileWriter file = new FileWriter(fileName)) {

	            file.write(sqlArray.toJSONString());
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	public static ArrayList<SqlServerMetric> jsonReader() {
		JSONParser parser = new JSONParser();
		String dir = System.getProperty("user.dir");
        ArrayList<SqlServerMetric> sqlList = new ArrayList<SqlServerMetric>();
		try {

            Object obj = parser.parse(new FileReader(dir+"\\SQLServer.json"));

            JSONArray jsonArray = (JSONArray) obj;
            SqlServerMetric sqlObj = new SqlServerMetric();
            for (int c=0; c< jsonArray.size(); c++) {
            	sqlObj.setGroup((String) ((JSONObject) jsonArray.get(c)).get("group"));
	            sqlObj.setSourceType((String) ((JSONObject) jsonArray.get(c)).get("sourceType"));
	            sqlObj.setSql(((JSONObject) jsonArray.get(c)).get("SQL").toString());
            	sqlList.add(sqlObj);        
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return sqlList;
	}
}
