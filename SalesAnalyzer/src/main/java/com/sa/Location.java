package com.sa;

public class Location {
	
	private String locationName=null;
	private double longitude=0.0;
	private double lattitude=0.0;
	
	public Location(){
		
	}
	
	public Location(String name,double longitude,double lattitude){
		this.locationName=name;
		this.longitude=longitude;
		this.lattitude=lattitude;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	
}
