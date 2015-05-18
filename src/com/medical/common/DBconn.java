package com.medical.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {
	public Connection getJDBCConn() throws SQLException, ClassNotFoundException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@10.10.10.222:1521:orcl";
			String userName = "yljzn";
			String password = "yljzn";
			con = DriverManager.getConnection(url, userName, password);
		} catch (SQLException ex) {
			throw ex;
		} catch (ClassNotFoundException ex) {
			throw ex;
		}
		return con;
	}
}
