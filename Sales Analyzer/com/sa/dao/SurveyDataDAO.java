package com.sa.dao;

import java.util.List;

import com.sa.to.SurveyDataTO;

public interface SurveyDataDAO {
	
	public List<SurveyDataTO> getSurveyData(List<String> storeIds);
	
	
}
