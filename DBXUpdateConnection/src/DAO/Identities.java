package DAO;

public class Identities {
	String connection, password, userName;
	int disabled, useWinAuth;
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDisabled() {
		return disabled;
	}
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	public int getUseWinAuth() {
		return useWinAuth;
	}
	public void setUseWinAuth(int useWinAuth) {
		this.useWinAuth = useWinAuth;
	}
	
	
}
