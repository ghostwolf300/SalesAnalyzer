package com.sa.dao;

public abstract class DAOFactory {
	
	public static final int ACCESS=1;
	public static final int MYSQL=2;
	public static final int EXCEL=3;
	
	public abstract PostcodeDAO getPostcodeDAO();
	public abstract SurveyDataDAO getSurveyDataDAO();
	public abstract void setDatabasePath(String path);
	
	public static DAOFactory getDAOFactory(int whichFactory){
		switch(whichFactory){
			case ACCESS :
				DAOFactory factory=new AccessDAOFactory();
				return factory;
			case EXCEL :
				return new ExcelDAOFactory();
			case MYSQL :
				return null;
			default :
				return null;
		}
	}
	
	
	
}
