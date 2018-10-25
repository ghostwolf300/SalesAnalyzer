package com.sa.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sa.to.SurveyDataTO;

public class ExcelSurveyDataDAO implements SurveyDataDAO {
	
	private static final String sql_getSurveyData=
		"SELECT Store,PostCodeI,CashRegister,Transaction,Amount " +
		"FROM <storeSheet>;";
	
	public List<SurveyDataTO> getSurveyData(List<String> storeIds) {
		Connection con=ExcelDAOFactory.createConnection();
		ResultSet rs=null;
		String storeSheets=new String();
		for(String s : storeIds){
			if(storeSheets.length()>0){
				storeSheets.concat(",");
			}
			storeSheets.concat("store "+s);
		}
		return null;
	}
	
	private List<SurveyDataTO> createSurveyDataList(ResultSet rs) throws SQLException{
		List<SurveyDataTO> dataList=new ArrayList<SurveyDataTO>();
		while(rs.next()){
			SurveyDataTO data=new SurveyDataTO();
			data.setStoreId(rs.getString("store_id"));
			data.setPostcode(rs.getString("postcode"));
			data.setSales(rs.getDouble("amount"));
			dataList.add(data);
		}
		return dataList;
	}

}
