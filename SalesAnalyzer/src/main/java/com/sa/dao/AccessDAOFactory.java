package com.sa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccessDAOFactory extends DAOFactory {
	
	private static final String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	private static final String con1="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private static final String con2=";DriverID=22;READONLY=true}";
	private static String dbPath=null;
	private static String conStr=null;
	
	@Override
	public PostcodeDAO getPostcodeDAO() {
		return new AccessPostcodeDAO();
	}

	@Override
	public void setDatabasePath(String path) {
		dbPath=cleanPath(path);
		conStr=con1+dbPath.trim()+con2;
		loadDriver();
	}
	
	private void loadDriver(){
		
		try {
			Class.forName(driver).newInstance(); 
		} 
		catch (ClassNotFoundException e) {
			System.out.println("Class: DataBaseTester Method: loadDriver Exception: ClassNotFound");
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			System.out.println("Class: DataBaseTester Method: loadDriver Exception: Instantiation");
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			System.out.println("Class: DataBaseTester Method: loadDriver Exception: IllegalAccess");
			e.printStackTrace();
		}
	}
	
	public static Connection createConnection(){
		try {
			
			return DriverManager.getConnection(conStr,"","");
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String cleanPath(String path){
		String output=path.replace("\\", "/");
		return output;
	}

	@Override
	public SurveyDataDAO getSurveyDataDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
