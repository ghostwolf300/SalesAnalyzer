package com.sa;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Locator {
	
	private static final String geocoderAddress="http://maps.googleapis.com/maps/api/geocode/";
	private static final String format="xml";
	
	
	
	public Locator(){
		
	}
	
	public Location locate(String locationName){
		
		URL geocoderURL=null;
		HttpURLConnection con=null;
		StringBuffer response=null;
		Location loc=null;
		
		try {
			geocoderURL=new URL(geocoderAddress+format+"?address="+locationName+"&sensor=false");
			//System.out.println(geocoderURL);
			con=(HttpURLConnection) geocoderURL.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("GET");    
			con.connect();
			
			if(HttpURLConnection.HTTP_OK == con.getResponseCode()){
		    	 InputStream is = con.getInputStream();
		    	 BufferedReader in = new BufferedReader(new InputStreamReader(is));
		    	 
		    	 
		    	 response=new StringBuffer();
		    	 String line=null;
		    	 
		    	 while((line=in.readLine())!=null){
		    		 response.append(line);
		    	 }
		    	 
		         in.close();
		         is.close();
		         con.disconnect();
		    }
		    else{
		    	System.out.println("didn't go as planned...\n"+con.getResponseMessage());
		    }   
			
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		SAXBuilder builder=new SAXBuilder();
		Document doc=null;
		Element root=null;
		Element location=null;
		String status=null;
		
		Reader xmlIn=new StringReader(response.toString());
		try {
			doc=builder.build(xmlIn);
			root=doc.getRootElement();
			status=root.getChild("status").getValue();
			if(status.equals("OK")){
				location=root.getChild("result").getChild("geometry").getChild("location");
				loc=new Location();
				loc.setLocationName(locationName);
				loc.setLattitude(Double.parseDouble(location.getChild("lat").getValue()));
				loc.setLongitude(Double.parseDouble(location.getChild("lng").getValue()));
			}
			else{
				System.out.println("status not OK\n+"+status);
			}
		} 
		catch (JDOMException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return loc;
	}
	
	private Location getLocationXML(String xml){
		return null;
	}
	
}
