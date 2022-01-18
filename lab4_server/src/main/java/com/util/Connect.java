package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {
	private Properties pro;
	private Connection con;
	public Connect() {
		pro = new Properties();
		InputStream in=Connect.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			pro.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String driver=pro.getProperty("driver");
		String url=pro.getProperty("url");
		String name=pro.getProperty("name");
		String password=pro.getProperty("password");
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection(url, name, password);
		}catch(SQLException e){
			
		}
	}
	public Connection getConnection(){
		return con;
	}
	public void dis_connect() throws SQLException{
		con.close();
	}
}
