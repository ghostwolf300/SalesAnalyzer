package com.sa.model;

import java.util.ArrayList;
import java.util.List;

import com.sa.PolygonArea;
import com.sa.SAController;
import com.sa.to.SurveyTO;

public class SAModel extends AbstractModel {
	
	private List<PolygonArea> polygonAreas=null;
	private List<SurveyTO> surveys=null;
	
	
	public SAModel(){
		super();
	}
	
	public void setPolygonAreas(ArrayList<PolygonArea> polygonAreas){
		System.out.println("setting model property");
		List<PolygonArea> oldPolygonAreas=this.polygonAreas;
		this.polygonAreas=polygonAreas;
		this.firePropertyChange(SAController.P_POLYGON_AREAS, oldPolygonAreas, this.polygonAreas);
	}
	
	public void setSurveys(List<SurveyTO> surveys){
		this.surveys=surveys;
		this.firePropertyChange(SAController.P_SURVEYS, null, this.surveys);
	}
	
	public void actionAddSurvey(SurveyTO survey){
		if(surveys==null){
			surveys=new ArrayList<SurveyTO>();
		}
		surveys.add(survey);
		this.firePropertyChange(SAController.P_SURVEYS, null, this.surveys);
	}
	
	public void actionRemoveSurvey(SurveyTO survey){
		surveys.remove(survey);
		this.firePropertyChange(SAController.P_SURVEYS, null, surveys);
	}
	
}
