package com.sa.to;

public class MarketDataTO {
	
	private String postcode=null;
	private String municipality=null;
	private String town=null;
	private String region=null;
	private long population=0;
	private long households=0;
	private double purchasePowerIndex=0.0;
	private double marketsize=0.0;
	private double sales=0.0;
	private double marketShare=0.0;
	private double longitude=0.0;
	private double latitude=0.0;
	
	public MarketDataTO(){
		
	}
	
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
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
	public double getPurchasePowerIndex() {
		return purchasePowerIndex;
	}
	public void setPurchasePowerIndex(double purchasePowerIndex) {
		this.purchasePowerIndex = purchasePowerIndex;
	}
	public double getMarketsize() {
		return marketsize;
	}
	public void setMarketsize(double marketsize) {
		this.marketsize = marketsize;
	}
	public double getMarketShare() {
		return marketShare;
	}
	public void setMarketShare(double marketShare) {
		this.marketShare = marketShare;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
