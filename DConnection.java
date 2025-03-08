package lib;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnection {

	public static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/library";
	static String usernameString = "root";
	static String passwordString = "p0215";

	public static Connection getConnection() {

		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, usernameString, passwordString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void main(String[] args) {
		DConnection.getConnection();
	}
}
