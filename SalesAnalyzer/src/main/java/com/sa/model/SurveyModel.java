package com.sa.model;

import java.util.List;

import com.sa.SAController;
import com.sa.to.SurveyDataTO;
import com.sa.to.SurveyTO;

public class SurveyModel extends AbstractModel {
	
	private List<SurveyTO> surveys=null;
	
	public void setSurveyResults(List<SurveyTO> surveys){
		this.surveys=surveys;
		this.firePropertyChange(SAController.P_SURVEYS, null, this.surveys);
	}
	
}
