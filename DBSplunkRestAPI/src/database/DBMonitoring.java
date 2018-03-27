package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBMonitoring {
	public static void main(String[] args) {

		try {
			sqlServerConnection("");
			// verifyOracleConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String sqlServerConnection(String sql) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Statement sta = null;
		ResultSet rs = null;
		String result = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager
					.getConnection("jdbc:sqlserver://192.168.192.1:1433;user=myuser;password=myuser;database=master");
			// System.out.println("test");
			sta = conn.createStatement();

			rs = sta.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(1);
			String name2 = rsmd.getColumnName(2);
			while (rs.next()) {
				result = name+"="+rs.getString(1)+" "+name2+"="+rs.getString(2);
				break;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			rs.close();
			sta.close();
			conn.close();
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
