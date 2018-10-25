package com.sa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sa.to.MarketDataTO;
import com.sa.to.PostcodeTO;
import com.sa.to.SalesDataTO;
import com.sa.to.SurveyDataTO;
import com.sa.to.SurveyTO;

public class AccessPostcodeDAO implements PostcodeDAO {
	
	private static final String sql_getAllPostcodes=
		"SELECT * FROM tbl_postcode;";
	private static final String sql_getPostcodes=
		"SELECT * FROM qry_geomap;";
	private static final String sql_getPMAPostcodes=
		"SELECT * FROM qry_pma_geo WHERE store_id=? AND fy=?;";
	private static final String sql_updatePostcode=
		"UPDATE tbl_postcode SET longitude=?,latitude=? WHERE postcode=?;";
	private static final String sql_getSalesData=
		"SELECT * FROM qry_purchaseValue WHERE survey_id=? AND store_id=?;";
	private static final String sql_createTable=
		"CREATE TABLE tbl_temp (postcode CHAR);";
	private static final String sql_insertPostcode=
		"INSERT INTO tbl_temp (postcode) VALUES(?);";
	private static final String sql_getMarketData=
		"SELECT * FROM qry_marketdata_all WHERE survey_year=? AND basemap_year=?";
	private static final String sql_getSurveyInfo=
		"SELECT * FROM tbl_survey WHERE id=?;";
	private static final String sql_getSurveyData=
		"SELECT * FROM tbl_surveyresult WHERE survey_id=?;";
	
	@Override
	public List<PostcodeTO> getAllPostcodes() {
		List<PostcodeTO> postcodes=null;
		Connection con=AccessDAOFactory.createConnection();
		Statement stmnt=null;
		ResultSet rs=null;
		
		try {
			stmnt=con.createStatement();
			rs=stmnt.executeQuery(sql_getAllPostcodes);
			postcodes=createPostcodeList(rs);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				stmnt.close();
				con.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return postcodes;
	}
	
	@Override
	public List<PostcodeTO> getPostcodes() {
		List<PostcodeTO> postcodes=null;
		Connection con=AccessDAOFactory.createConnection();
		Statement stmnt=null;
		ResultSet rs=null;
		
		try {
			stmnt=con.createStatement();
			rs=stmnt.executeQuery(sql_getPostcodes);
			postcodes=createPostcodeList(rs);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				stmnt.close();
				con.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return postcodes;
	}
	
	@Override
	public List<PostcodeTO> getPMAPostcodes(String storeId,int pmaYear) {
		
		List<PostcodeTO> postcodes=null;
		Connection con=AccessDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		
		try {
			pstmnt=con.prepareStatement(sql_getPMAPostcodes);
			pstmnt.setString(1, storeId);
			pstmnt.setInt(2,pmaYear);
			rs=pstmnt.executeQuery();
			postcodes=createPostcodeList(rs);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmnt.close();
				con.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return postcodes;
	}


	@Override
	public void updatePostcodes(List<PostcodeTO> postcodes) {
		Connection con=AccessDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		
		try {
			pstmnt=con.prepareStatement(sql_updatePostcode);
			for(PostcodeTO pc : postcodes){
				pstmnt.clearParameters();
				pstmnt.setDouble(1, pc.getLongitude());
				pstmnt.setDouble(2, pc.getLattitude());
				pstmnt.setString(3, pc.getPostcode());
				pstmnt.executeUpdate();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				pstmnt.close();
				con.close();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public List<SalesDataTO> getSalesData(String storeId,int surveyId) {
		List<SalesDataTO> sales=null;
		Connection con=AccessDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		
		try {
			pstmnt=con.prepareStatement(sql_getSalesData);
			pstmnt.setInt(1, surveyId);
			pstmnt.setString(2, storeId);
			rs=pstmnt.executeQuery();
			sales=createSalesDataList(rs);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmnt.close();
				con.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sales;
	}
	
	@Override
	public List<MarketDataTO> getMarketData(int surveyYear, int basemapYear) {
		
		List<MarketDataTO> dataList=null;
		Connection con=AccessDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		
		try {
			pstmnt=con.prepareStatement(sql_getMarketData);
			pstmnt.setInt(1, surveyYear);
			pstmnt.setInt(2, basemapYear);
			rs=pstmnt.executeQuery();
			dataList=createMarketDataList(rs);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmnt.close();
				con.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataList;
	}

	
	@Override
	public void createPostcodeTable(String tableName, List<PostcodeTO> postcodes) {
		
		Connection con=AccessDAOFactory.createConnection();
		PreparedStatement createTableStmnt=null;
		PreparedStatement changeNameStmnt=null;
		PreparedStatement pstmntInsert=null;
		
		String sql_create=new String(sql_createTable);
		sql_create=sql_create.replace("tbl_temp", tableName);
		
		String sql_insert=new String(sql_insertPostcode);
		sql_insert=sql_insert.replace("tbl_temp", tableName);
		
		try {
			createTableStmnt=con.prepareStatement(sql_create);
			createTableStmnt.executeUpdate();
			
			pstmntInsert=con.prepareStatement(sql_insert);
			
			for(PostcodeTO pc : postcodes){
				pstmntInsert.clearParameters();
				pstmntInsert.setString(1, pc.getPostcode());
				pstmntInsert.executeUpdate();
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private List<PostcodeTO> createPostcodeList(ResultSet rs) throws SQLException{
		List<PostcodeTO> postcodes=new ArrayList<PostcodeTO>();
		while(rs.next()){
			PostcodeTO postcode=new PostcodeTO();
			postcode.setPostcode(rs.getString("postcode"));
			postcode.setTown(rs.getString("name"));
			postcode.setRegion(rs.getString("region"));
			postcode.setLattitude(rs.getDouble("latitude"));
			postcode.setLongitude(rs.getDouble("longitude"));
			postcodes.add(postcode);
		}
		return postcodes;
	}
	
	private List<SalesDataTO> createSalesDataList(ResultSet rs) throws SQLException{
		List<SalesDataTO> salesList=new ArrayList<SalesDataTO>();
		while(rs.next()){
			SalesDataTO areaSales=new SalesDataTO();
			areaSales.setPostcode(rs.getString("postcode"));
			areaSales.setMunicipality(rs.getString("name"));
			areaSales.setTown(rs.getString("name_2"));
			areaSales.setRegion(rs.getString("region"));
			areaSales.setSales(rs.getDouble("TotalAmount"));
			areaSales.setLatitude(rs.getDouble("latitude"));
			areaSales.setLongitude(rs.getDouble("longitude"));
			salesList.add(areaSales);
		}
		return salesList;
	}
	
	private List<MarketDataTO> createMarketDataList(ResultSet rs) throws SQLException{
		List<MarketDataTO> dataList=new ArrayList<MarketDataTO>();
		while(rs.next()){
			MarketDataTO data=new MarketDataTO();
			data.setPostcode(rs.getString("postcode"));
			data.setMunicipality(rs.getString("municipality"));
			data.setTown(rs.getString("town"));
			data.setPopulation(rs.getLong("population"));
			data.setHouseholds(rs.getLong("households"));
			data.setPurchasePowerIndex(rs.getDouble("pp_index"));
			data.setSales(rs.getDouble("sales"));
			data.setMarketShare(rs.getDouble("marketshare"));
			data.setLongitude(rs.getDouble("longitude"));
			data.setLatitude(rs.getDouble("latitude"));
			dataList.add(data);
		}
		return dataList;
	}
	
	private List<SurveyDataTO> createSurveyDataList(ResultSet rs) throws SQLException{
		List<SurveyDataTO> dataList=new ArrayList<SurveyDataTO>();
		while(rs.next()){
			SurveyDataTO data=new SurveyDataTO();
			data.setSurveyId(rs.getInt("survey_id"));
			data.setResultId(rs.getInt("result_id"));
			data.setStoreId(rs.getString("store_id"));
			data.setPostcode(rs.getString("postcode"));
			data.setSales(rs.getDouble("amount"));
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	public SurveyTO getSurvey(int surveyId) {
		
		SurveyTO survey=new SurveyTO();
		
		Connection con=AccessDAOFactory.createConnection();
		PreparedStatement psSurveyInfo=null;
		PreparedStatement psSurveyData=null;
		ResultSet rsInfo=null;
		ResultSet rsData=null;
		
		try {
			psSurveyInfo=con.prepareStatement(sql_getSurveyInfo);
			psSurveyInfo.setInt(1, surveyId);
			rsInfo=psSurveyInfo.executeQuery();
			while(rsInfo.next()){
				survey.setSurveyId(rsInfo.getInt("id"));
				survey.setName(rsInfo.getString("survey_name"));
				survey.setYear(rsInfo.getInt("year"));
				survey.setDescription(rsInfo.getString("description"));
				survey.setDurationWeeks(rsInfo.getInt("duration"));
			}
			psSurveyData=con.prepareStatement(sql_getSurveyData);
			psSurveyData.setInt(1, surveyId);
			rsData=psSurveyData.executeQuery();
			
			survey.setData(createSurveyDataList(rsData));
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(rsInfo!=null){
					rsInfo.close();
				}
				if(rsData!=null){
					rsData.close();
				}
				psSurveyInfo.close();
				psSurveyData.close();
				con.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return survey;
	}

	
}
