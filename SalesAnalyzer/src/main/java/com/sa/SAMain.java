package com.sa;


import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

import com.sa.dao.DAOFactory;
import com.sa.dao.PostcodeDAO;
import com.sa.gui.SAFrame;
import com.sa.gui.kmlreader.KMLReaderFrame;
import com.sa.model.SAModel;
import com.sa.to.MarketDataTO;
import com.sa.to.PostcodeTO;
import com.sa.to.SalesDataTO;


public class SAMain {
	
	private static final String address="http://www.verkkoposti.com/e3/postinumeroluettelo";
	private static final int COL_STREET=1;
	private static final int COL_ZIPCODE=5;
	private static final int COL_TOWN=7;
	
	public SAMain(){
		//doGeomapping();
		//writeKML();
		findPostcodesInsideArea();
		//this.markPMAPostcodes("666", 2011);
		//new KMLReaderFrame();
		//SAController controller=SAController.getInstance();
		//controller.addModel(new SAModel());
		//new SAFrame();
		
	}
	
	private void checkZipCode(String zipCode){
		
		URL searchPage=null;
		HttpURLConnection con=null;
		
		try {
			searchPage=new URL(address+"?"+"po_commune_radio=commune&streetname=&po_commune=&zipcode="+zipCode);
			con=(HttpURLConnection)searchPage.openConnection();
			HttpURLConnection.setFollowRedirects(true);
		    con.setDoOutput(true);
		    con.setRequestMethod("GET");
		    
		    con.connect();
		    
		    if(HttpURLConnection.HTTP_OK == con.getResponseCode()){
		    	 InputStream is = con.getInputStream();
		    	 
		         OutputStream os = new FileOutputStream("output.html");
		         int data;
		         while((data=is.read()) != -1)
		         {
		           os.write(data);
		         }
		         is.close();
		         os.close();
		         con.disconnect();
		    }
		    else{
		    	System.out.println("didn't go as planned...");
		    }   
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private List<PostcodeTO> createZipCodeList(){
		
		List<PostcodeTO> zipcodes=new ArrayList<PostcodeTO>();
		
		Parser parser=new Parser();
		NodeList nodes=null;
		NodeList tableNodes=null;
		NodeList rowNodes=null;
		NodeList dataRows=null;
		
		String zipCode=null;
		String town=null;
		
		try {
			parser.setInputHTML(readFile("output.html"));
			parser.setEncoding("UTF-8");
			nodes=parser.parse(null);
			tableNodes=nodes.extractAllNodesThatMatch(new TagNameFilter("table"),true);
			
			rowNodes=tableNodes.elementAt(2).getChildren();
			dataRows=rowNodes.extractAllNodesThatMatch(new TagNameFilter("tr"),true);
			
			String htmlRow=null;
			String splitRow[]=null;
			for(int i=1;i<dataRows.size()-1;i++){
				htmlRow=dataRows.elementAt(i).toHtml().trim();
				splitRow=ParserUtils.splitTags(htmlRow,new String[]{"tr","td"},true,false);
				if(splitRow.length>1){
					zipCode=splitRow[COL_ZIPCODE].trim().substring(0,5);
					town=splitRow[COL_TOWN].replace("&nbsp;", "").trim();
					PostcodeTO zc=new PostcodeTO();
					zc.setPostcode(zipCode);
					zc.setTown(town);
					zipcodes.add(zc);
				}
			}
			
			
		} 
		catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return zipcodes;
		
	}
	
	
	/**
	 * @param args
	 */
	
	private String readFile(String fileName){
		BufferedReader reader=null;
		StringBuffer contents=new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String text=null;
			while((text=reader.readLine())!=null){
				contents.append(text);
			}
			
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				reader.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return contents.toString();
	}
	
	private void doGeomapping(){
		DAOFactory factory=DAOFactory.getDAOFactory(DAOFactory.ACCESS);
		factory.setDatabasePath("D:/Ohjelmointi/Tietokannat/IKEA_VC.mdb");
		PostcodeDAO dao=factory.getPostcodeDAO();
		Locator locator=new Locator();
		
		List<PostcodeTO> postcodes=dao.getPostcodes();
		System.out.println("size: "+postcodes.size());
		int counter=postcodes.size();
		boolean continueOk=false;
		for(PostcodeTO pc : postcodes){
			continueOk=false;
			while(!continueOk){
				Location loc=locator.locate(pc.getPostcode()+",Finland");
				if(loc!=null){
					pc.setLattitude(loc.getLattitude());
					pc.setLongitude(loc.getLongitude());
					System.out.println(pc.getPostcode()+"\tlng="+pc.getLongitude()+" lat="+pc.getLattitude());
					continueOk=true;
					counter--;
				}
				else{
					System.out.println("Null value, Waiting 5 seconds.");
					try {
						Thread.sleep(5000);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("resuming execution, postcodes left="+counter);
				}
			}
		}
		
		dao.updatePostcodes(postcodes);
	}
	
	private void writeKML(){
		DAOFactory factory=DAOFactory.getDAOFactory(DAOFactory.ACCESS);
		factory.setDatabasePath("D:/Ohjelmointi/Tietokannat/IKEA_VC.mdb");
		PostcodeDAO dao=factory.getPostcodeDAO();
		
		KMLCreator creator=new KMLCreator();
		
		creator.initDocument(0.01,0.25);
		
		List<MarketDataTO> dataList=null;
		dataList=dao.getMarketData(2010, 2010);
		
		creator.insertMarketData("marketdata",dataList,Color.RED);
		
		creator.saveDocument("marketdata");
		/*List<PostcodeTO> postcodes=dao.getPostcodes();
		creator.markPostcodes(postcodes, KMLCreator.MARKER_DEFAULT);*/
		
	}
	
	public void findPostcodesInsideArea(){
		
		DAOFactory factory=DAOFactory.getDAOFactory(DAOFactory.ACCESS);
		//factory.setDatabasePath(System.getProperty("user.dir")+"\\IKEA_VC.mdb");
		factory.setDatabasePath("C:/Users/ville.susi/Sales Analyzer/IKEA_VC.mdb");
		PostcodeDAO dao=factory.getPostcodeDAO();
		
		KMLReader reader=new KMLReader(new File("kuopio_pma_new.kml"));
		
		List<PolygonArea> polyAreas=reader.getPolygonAreas();
		
		
		Path2D.Double area=polyAreas.get(0).getPolygon();
		
		
		List<PostcodeTO> postcodes=dao.getAllPostcodes();
		List<PostcodeTO> areaPostcodes=new ArrayList<PostcodeTO>();
		for(PostcodeTO pc : postcodes){
			Point2D.Double point=new Point2D.Double(pc.getLongitude(), pc.getLattitude());
			if(area.contains(point)){
				areaPostcodes.add(pc);
			}
		}
		dao.createPostcodeTable("tbl_tmp_test", areaPostcodes);
		
		//KMLCreator creator=new KMLCreator();
		//creator.markPostcodes(areaPostcodes, KMLCreator.MARKER_DEFAULT);
		
		System.out.println("all postcodes: "+postcodes.size());
		System.out.println("postcodes inside area: "+areaPostcodes.size());
		
	}
	
	public void markPMAPostcodes(String storeId,int fy){
		DAOFactory factory=DAOFactory.getDAOFactory(DAOFactory.ACCESS);
		factory.setDatabasePath("D:/Ohjelmointi/Tietokannat/IKEA_VC.mdb");
		PostcodeDAO dao=factory.getPostcodeDAO();
		List<PostcodeTO> postcodes=dao.getPMAPostcodes(storeId, fy);
		KMLCreator creator=new KMLCreator();
		creator.markPostcodes(postcodes, KMLCreator.MARKER_DEFAULT);
	}
	
	public static void main(String[] args) {
		new SAMain();
	}

}
