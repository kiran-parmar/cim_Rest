package DAO;

public class Connections {
	String connectionName, connectionType, database, host, identity, timezone;
	int disabled, port;
	boolean jdbcUseSSL, localTimezoneConversionEnabled, readonly;

	
	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isJdbcUseSSL() {
		return jdbcUseSSL;
	}

	public void setJdbcUseSSL(boolean jdbcUseSSL) {
		this.jdbcUseSSL = jdbcUseSSL;
	}

	public boolean isLocalTimezoneConversionEnabled() {
		return localTimezoneConversionEnabled;
	}

	public void setLocalTimezoneConversionEnabled(boolean localTimezoneConversionEnabled) {
		this.localTimezoneConversionEnabled = localTimezoneConversionEnabled;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
}
