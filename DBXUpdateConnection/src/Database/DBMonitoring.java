package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBMonitoring {
	public static void main(String[] args) {

		
			//sqlServerConnection("myuser", "myuser");
			// verifyOracleConnection();
		
	}

	public static boolean sqlServerConnection(String host, int port, String userName, String password, String database){
		
		boolean result = false;
		//Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+host+":"+port+";user="+userName+";password="+password+";database="+database);
		//Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.192.1:1433;user=myuser;password=myuser;database=master");
		//System.out.println(host+" "+port+" "+userName+" "+password +" "+database);
		try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+host+":"+port+";user="+userName+";password="+password+";database="+database);
				) {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			result = true;
		} catch (Exception e) {
			//System.out.println("db connection error: "+e);
			return false;
		} 
		return result;
	}

	public static String verifyOracleConnection() throws SQLException, ClassNotFoundException {
		String result = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "admin", "admin");

			// step3 create the statement object
			stmt = conn.createStatement();

			// step4 execute query
			rs = stmt.executeQuery("select * from test");

			while (rs.next()) {
				result = rs.getString(1);
				break;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}
}
