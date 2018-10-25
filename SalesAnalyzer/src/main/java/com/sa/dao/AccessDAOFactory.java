package com.sa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccessDAOFactory extends DAOFactory {
	
	//private static final String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	private static final String DRIVER="net.ucanaccess.jdbc.UcanaccessDriver";
	private static final String con1="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private static final String con2=";DriverID=22;READONLY=true}";
	private static String dbPath=null;
	private static String conStr=null;
	
	
	public PostcodeDAO getPostcodeDAO() {
		return new AccessPostcodeDAO();
	}

	public void setDatabasePath(String path) {
		//dbPath=cleanPath(path);
		//conStr=con1+dbPath.trim()+con2;
		conStr="jdbc:ucanaccess://" +path;
		loadDriver();
	}
	
	private void loadDriver(){
		
		try {
			Class.forName(DRIVER);
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Connection createConnection(){
		Connection conn=null;
		try {
			
			conn=DriverManager.getConnection(conStr);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private String cleanPath(String path){
		String output=path.replace("\\", "/");
		return output;
	}

	public SurveyDataDAO getSurveyDataDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
