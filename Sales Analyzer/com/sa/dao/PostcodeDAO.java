package com.sa.dao;

import java.util.List;

import com.sa.to.MarketDataTO;
import com.sa.to.PostcodeTO;
import com.sa.to.SalesDataTO;
import com.sa.to.SurveyTO;

public interface PostcodeDAO {
	
	public List<PostcodeTO> getPostcodes();
	public List<PostcodeTO> getPMAPostcodes(String storeId,int pmaYear);
	public List<PostcodeTO> getAllPostcodes();
	public void updatePostcodes(List<PostcodeTO> postcodes);
	public List<SalesDataTO> getSalesData(String storeId,int surveyId);
	public List<MarketDataTO> getMarketData(int surveyYear,int basemapYear);
	public void createPostcodeTable(String tableName,List<PostcodeTO> postcodes);
	public SurveyTO getSurvey(int surveyId);
	
}
