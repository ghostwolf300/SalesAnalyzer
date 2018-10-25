package com.sa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExcelDAOFactory extends DAOFactory {
	
	private static final String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	private static String databasePath=null;
	private static String con1="jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=";
	private static String con2=";DriverID=22;READONLY=true}";
	private static String connectionString=null;
	
	public ExcelDAOFactory(){
		
	}
	
	@Override
	public PostcodeDAO getPostcodeDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SurveyDataDAO getSurveyDataDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDatabasePath(String path) {
		databasePath=cleanPath(path);
		connectionString=con1+databasePath+con2;
		loadDriver();
	}
	
	public static Connection createConnection(){
		try {
			
			return DriverManager.getConnection(connectionString,"","");
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
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
	
	private String cleanPath(String path){
		String output=path.replace("\\", "/");
		return output;
	}

	

}
