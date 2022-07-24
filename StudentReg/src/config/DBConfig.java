package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

	private final String CONNECTION = "jdbc:mysql://localhost:3306/studentregistration";
	private final String PASSWORD = "root";
	private static Connection con = null;

	public Connection getConnection() throws SQLException {

		if (null == con) {
			con = (Connection) DriverManager.getConnection(this.CONNECTION, "root", this.PASSWORD);
		}
		return con;
	}
}
