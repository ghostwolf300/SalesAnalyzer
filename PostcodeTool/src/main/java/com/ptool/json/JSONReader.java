package com.ptool.json;

public class JSONReader {
	
	private static JSONReader instance;
	
	
	private JSONReader() {
		
	}
	
	public static synchronized JSONReader getInstance() {
		if(instance==null) {
			instance=new JSONReader();
		}
		return instance;
	}
	
}
