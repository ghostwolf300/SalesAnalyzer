package com.sa.to;

import java.util.List;

public class SurveyTO {
	
	private int surveyId=-1;
	private String name=null;
	private int year=-1;
	private String description=null;
	private int durationWeeks=0;
	private List<SurveyDataTO> data=null;
	
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public List<SurveyDataTO> getData() {
		return data;
	}
	
	public void setData(List<SurveyDataTO> data) {
		this.data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDurationWeeks() {
		return durationWeeks;
	}
	public void setDurationWeeks(int durationWeeks) {
		this.durationWeeks = durationWeeks;
	}
	
}
