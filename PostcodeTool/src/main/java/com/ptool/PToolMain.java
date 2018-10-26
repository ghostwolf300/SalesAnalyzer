package com.ptool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.geojson.GeoJsonReader;


public class PToolMain {

	public static void main(String[] args) {
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObj=null;
		
		try {
			jsonObj=(JSONObject)parser.parse(new FileReader("postinumeroalueet 2018.tjson"));
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(jsonObj.get("type"));
	
		
		
	}

}
