package com.sa.gui.kmlreader;

import com.sa.to.PostcodeTO;

public class PostcodeTableRow {
	
	private String postcode=null;
	private String town=null;
	private String region=null;
	private long population=0;
	private long households=0;
	private double ppIndex=0.0;
	private double marketsize=0.0;
	
	public PostcodeTableRow(){
		
	}
	
	public PostcodeTableRow(PostcodeTO postcode){
		this.postcode=postcode.getPostcode();
		this.town=postcode.getTown();
		this.region=postcode.getRegion();
	}
	
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public long getPopulation() {
		return population;
	}
	public void setPopulation(long population) {
		this.population = population;
	}
	public long getHouseholds() {
		return households;
	}
	public void setHouseholds(long households) {
		this.households = households;
	}
	public double getPpIndex() {
		return ppIndex;
	}
	public void setPpIndex(double ppIndex) {
		this.ppIndex = ppIndex;
	}
	public double getMarketsize() {
		return marketsize;
	}
	public void setMarketsize(double marketsize) {
		this.marketsize = marketsize;
	}
	
}
