package com.sa;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.sa.to.MarketDataTO;
import com.sa.to.PostcodeTO;
import com.sa.to.SalesDataTO;

public class KMLCreator {
	
	public static final int MARKER_DEFAULT=0;
	
	private Document docKml=null;
	private Document docPolygonStyle=null;
	private Document docPlacemarkStyle=null;
	private double maxSize=0.0;
	private double salesThreshold=0.0;
	private Map<String,List<Double>> salesMap=null;
	
	public KMLCreator(){
		salesMap=new HashMap<String,List<Double>>();
	}
	
	public void markPostcodes(List<PostcodeTO> postcodes,int markerType){
		
		try {
			
			Document docKml=new Document();
			Document docPlacemark=getPlacemark();
			
			Element root=new Element("kml");
			Namespace gx=Namespace.getNamespace("gx", "http://www.google.com/kml/ext/2.2");
			Namespace kml=Namespace.getNamespace("kml", "http://www.opengis.net/kml/2.2");
			Namespace atom=Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom");
			
			root.addNamespaceDeclaration(gx);
			root.addNamespaceDeclaration(kml);
			root.addNamespaceDeclaration(atom);
			
			Element eDocument=new Element("Document");
			Element eFolder=new Element("Folder");
			Element eFolderName=new Element("name");
			eFolderName.setText("Postcodes");
			eFolder.addContent(eFolderName);
			
			for(PostcodeTO pc : postcodes){
				Element ePlacemark=(Element)docPlacemark.getRootElement().clone();
				ePlacemark.getChild("address").setText(pc.getPostcode()+", "+pc.getTown());
				String lon=String.valueOf(pc.getLongitude()).replace(",", ".");
				String lat=String.valueOf(pc.getLattitude()).replace(",", ".");
				ePlacemark.getChild("Point").getChild("coordinates").setText(lon+","+lat+",0");
				eFolder.addContent(ePlacemark);
			}
			
			eDocument.addContent(eFolder);
			
			root.setContent(eDocument);
			docKml.setRootElement(root);
			
			save(docKml,"markers.kml");
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initDocument(double maxSize,double salesThreshold){
		
		this.maxSize=maxSize;
		this.salesThreshold=salesThreshold;
		
		docKml=new Document();
		
		Element root=new Element("kml");
			
		Namespace gx=Namespace.getNamespace("gx", "http://www.google.com/kml/ext/2.2");
		Namespace kml=Namespace.getNamespace("kml", "http://www.opengis.net/kml/2.2");
		Namespace atom=Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom");
			
		root.addNamespaceDeclaration(gx);
		root.addNamespaceDeclaration(kml);
		root.addNamespaceDeclaration(atom);
			
		docKml.setRootElement(root);
			
		Element eDocument=new Element("Document");
		root.addContent(eDocument);
		
		
	}
	
	public void saveDocument(String fileName){
		
		File file=new File(fileName+".kml");
        XMLOutputter outputter = new XMLOutputter();
        FileWriter writer=null;
        
		try {
			writer = new FileWriter(file);
			outputter.output(docKml,writer);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				writer.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
        
	}
	
	public void insertSalesData(String folderName,List<SalesDataTO> salesDataList,Color squareColor){
		
		Document docPolygonStyle=null;
		Document docPlaceMarkStyle=null;
		
		List styleMapElements=null;
		List styleElements=null;
		Iterator iter=null;
		
		try {
			docPolygonStyle=getPolygonStyle();
			docPlaceMarkStyle=getPlacemarkStyle();
			
			Element eDocument=docKml.getRootElement().getChild("Document");
			
			Element eStoreFolder=new Element("Folder");
			eStoreFolder.addContent(new Element("name"));
			eStoreFolder.getChild("name").setText(folderName);
			eDocument.addContent(eStoreFolder);
			
			Element ePolyFolder=new Element("Folder");
			Element ePolyFolderName=new Element("name");
			ePolyFolderName.setText("Sales Graphics");
			ePolyFolder.addContent(ePolyFolderName);
			eStoreFolder.addContent(ePolyFolder);
			
			Element eMarkerFolder=new Element("Folder");
			Element eMarkerFolderName=new Element("name");
			eMarkerFolderName.setText("Location Markers");
			eMarkerFolder.addContent(eMarkerFolderName);
			eStoreFolder.addContent(eMarkerFolder);
			
			Element polyStyleRoot=docPolygonStyle.getRootElement();
			
			styleMapElements=new ArrayList(polyStyleRoot.getChildren("StyleMap"));
			styleElements=new ArrayList(polyStyleRoot.getChildren("Style"));
			
			iter=styleMapElements.listIterator();
			
			while(iter.hasNext()){
				Element ePolyStyleMap=(Element) ((Content)iter.next()).detach();
				ePolyFolder.addContent(ePolyStyleMap);
			}
			
			iter=styleElements.listIterator();
			
			while(iter.hasNext()){
				Element ePolyStyle=(Element) ((Content)iter.next()).detach();
				if(ePolyStyle.getAttribute("id").getValue().equals("markerSquare")){
					ePolyStyle.getChild("PolyStyle").getChild("color").setText(colorToKMLHex(squareColor));
				}
				ePolyFolder.addContent(ePolyStyle);
			}
			
			Element markerStyleRoot=docPlaceMarkStyle.getRootElement();
			
			styleMapElements=new ArrayList(markerStyleRoot.getChildren("StyleMap"));
			styleElements=new ArrayList(markerStyleRoot.getChildren("Style"));
			
			iter=styleMapElements.listIterator();
			
			while(iter.hasNext()){
				Element eMarkerStyleMap=(Element) ((Content)iter.next()).detach();
				eMarkerFolder.addContent(eMarkerStyleMap);
			}
			
			iter=styleElements.listIterator();
			
			while(iter.hasNext()){
				Element eMarkerStyle=(Element) ((Content)iter.next()).detach();
				eMarkerFolder.addContent(eMarkerStyle);
			}
			
			Document polyDoc=getPolygon();
			Document markDoc=getPlacemark();
			
			double diameter=0.0;
			NumberFormat form=NumberFormat.getCurrencyInstance();
			
			for(SalesDataTO sd : salesDataList){
				
				int order=0;
				String salesAmount=form.format(sd.getSales());
				double p=sd.getSales()/this.salesThreshold;
				
				if(p>=1.0){
					diameter=this.maxSize;
					order=0;
				}
				else if(p<1 && p>=0.80){
					diameter=0.80*this.maxSize;
					order=1;
				}
				else if(p<0.80 && p>=0.60){
					diameter=0.60*this.maxSize;
					order=2;
				}
				else if(p<0.60 && p>=0.40){
					diameter=0.40*this.maxSize;
					order=3;
				}
				else if(p<0.40 && p>=0.20){
					diameter=0.20*this.maxSize;
					order=4;
				}
				else if(p<0.20){
					diameter=0.1*this.maxSize;
					order=5;
				}
				
				Element ePolygon=(Element)polyDoc.getRootElement().clone();
				
				ePolygon.getChild("name").setText(sd.getPostcode());
				ePolygon.getChild("description").setText(sd.getTown()+", "+sd.getRegion()+", sales: "+salesAmount);
				ePolygon.getChild("Polygon").getChild("drawOrder",ePolygon.getNamespace("gx")).setText(String.valueOf(order));
				Element eCoordinates=ePolygon.getChild("Polygon").getChild("outerBoundaryIs").getChild("LinearRing").getChild("coordinates");
				String p1=String.valueOf(sd.getLongitude()+diameter)+","+String.valueOf(sd.getLatitude())+",0";
				String p2=String.valueOf(sd.getLongitude())+","+String.valueOf(sd.getLatitude()+0.483*diameter)+",0";
				String p3=String.valueOf(sd.getLongitude()-diameter)+","+String.valueOf(sd.getLatitude())+",0";
				String p4=String.valueOf(sd.getLongitude())+","+String.valueOf(sd.getLatitude()-0.483*diameter)+",0";
				String c=p1+" "+p2+" "+p3+" "+p4;
				eCoordinates.setText(c);
				ePolyFolder.addContent(ePolygon);
				
				/*Element eMarker=(Element)markDoc.getRootElement().clone();
				String postcodeAddress=null;
				if((postcodeAddress=sd.getTown())==null){
					postcodeAddress=sd.getMunicipality();
				}
				eMarker.getChild("name").setText(sd.getPostcode()+" "+postcodeAddress);
				eMarker.getChild("address").setText(sd.getPostcode()+" "+sd.getMunicipality()+" "+sd.getTown()+" ");
				eMarker.getChild("description").setText("sales from area:\n"+salesAmount);
				eMarker.getChild("Point").getChild("coordinates").setText(sd.getLongitude()+","+sd.getLatitude()+",0");
				eMarkerFolder.addContent(eMarker);*/
			}
		
		} 
		catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertMarketData(String folderName,List<MarketDataTO> marketDataList,Color squareColor){
		Document docPolygonStyle=null;
		Document docPlaceMarkStyle=null;
		
		List styleMapElements=null;
		List styleElements=null;
		Iterator iter=null;
		
		try {
			docPolygonStyle=getPolygonStyle();
			docPlaceMarkStyle=getPlacemarkStyle();
			
			Element eDocument=docKml.getRootElement().getChild("Document");
			
			Element eStoreFolder=new Element("Folder");
			eStoreFolder.addContent(new Element("name"));
			eStoreFolder.getChild("name").setText(folderName);
			eDocument.addContent(eStoreFolder);
			
			Element ePolyFolder=new Element("Folder");
			Element ePolyFolderName=new Element("name");
			ePolyFolderName.setText("Market Data Graphics");
			ePolyFolder.addContent(ePolyFolderName);
			eStoreFolder.addContent(ePolyFolder);
			
			Element eMarkerFolder=new Element("Folder");
			Element eMarkerFolderName=new Element("name");
			eMarkerFolderName.setText("Location Markers");
			eMarkerFolder.addContent(eMarkerFolderName);
			eStoreFolder.addContent(eMarkerFolder);
			
			Element polyStyleRoot=docPolygonStyle.getRootElement();
			
			styleMapElements=new ArrayList(polyStyleRoot.getChildren("StyleMap"));
			styleElements=new ArrayList(polyStyleRoot.getChildren("Style"));
			
			iter=styleMapElements.listIterator();
			
			while(iter.hasNext()){
				Element ePolyStyleMap=(Element) ((Content)iter.next()).detach();
				ePolyFolder.addContent(ePolyStyleMap);
			}
			
			iter=styleElements.listIterator();
			
			while(iter.hasNext()){
				Element ePolyStyle=(Element) ((Content)iter.next()).detach();
				if(ePolyStyle.getAttribute("id").getValue().equals("markerSquare")){
					ePolyStyle.getChild("PolyStyle").getChild("color").setText(colorToKMLHex(squareColor));
				}
				ePolyFolder.addContent(ePolyStyle);
			}
			
			Element markerStyleRoot=docPlaceMarkStyle.getRootElement();
			
			styleMapElements=new ArrayList(markerStyleRoot.getChildren("StyleMap"));
			styleElements=new ArrayList(markerStyleRoot.getChildren("Style"));
			
			iter=styleMapElements.listIterator();
			
			while(iter.hasNext()){
				Element eMarkerStyleMap=(Element) ((Content)iter.next()).detach();
				eMarkerFolder.addContent(eMarkerStyleMap);
			}
			
			iter=styleElements.listIterator();
			
			while(iter.hasNext()){
				Element eMarkerStyle=(Element) ((Content)iter.next()).detach();
				eMarkerFolder.addContent(eMarkerStyle);
			}
			
			Document polyDoc=getPolygon();
			Document markDoc=getPlacemark();
			
			double diameter=0.0;
			NumberFormat form=NumberFormat.getPercentInstance();
			form.setMinimumFractionDigits(2);
			form.setMaximumFractionDigits(2);
			
			for(MarketDataTO md : marketDataList){
				
				int order=0;
				String marketShare=form.format(md.getMarketShare());
				double p=md.getMarketShare()/this.salesThreshold;
				
				if(p>=1.0){
					diameter=this.maxSize;
					order=0;
				}
				else if(p<1 && p>=0.80){
					diameter=0.80*this.maxSize;
					order=1;
				}
				else if(p<0.80 && p>=0.60){
					diameter=0.60*this.maxSize;
					order=2;
				}
				else if(p<0.60 && p>=0.40){
					diameter=0.40*this.maxSize;
					order=3;
				}
				else if(p<0.40 && p>=0.20){
					diameter=0.20*this.maxSize;
					order=4;
				}
				else if(p<0.20){
					diameter=0.1*this.maxSize;
					order=5;
				}
				
				Element ePolygon=(Element)polyDoc.getRootElement().clone();
				
				ePolygon.getChild("name").setText(md.getPostcode());
				ePolygon.getChild("description").setText(md.getTown()+", "+md.getRegion()+", market share: "+marketShare);
				ePolygon.getChild("Polygon").getChild("drawOrder",ePolygon.getNamespace("gx")).setText(String.valueOf(order));
				Element eCoordinates=ePolygon.getChild("Polygon").getChild("outerBoundaryIs").getChild("LinearRing").getChild("coordinates");
				String p1=String.valueOf(md.getLongitude()+diameter)+","+String.valueOf(md.getLatitude())+",0";
				String p2=String.valueOf(md.getLongitude())+","+String.valueOf(md.getLatitude()+0.483*diameter)+",0";
				String p3=String.valueOf(md.getLongitude()-diameter)+","+String.valueOf(md.getLatitude())+",0";
				String p4=String.valueOf(md.getLongitude())+","+String.valueOf(md.getLatitude()-0.483*diameter)+",0";
				String c=p1+" "+p2+" "+p3+" "+p4;
				eCoordinates.setText(c);
				ePolyFolder.addContent(ePolygon);
				
				Element eMarker=(Element)markDoc.getRootElement().clone();
				String postcodeAddress=null;
				if((postcodeAddress=md.getTown())==null){
					postcodeAddress=md.getMunicipality();
				}
				eMarker.getChild("name").setText(md.getPostcode()+" "+postcodeAddress+" "+form.format(md.getMarketShare()));
				eMarker.getChild("address").setText(md.getPostcode()+" "+md.getMunicipality()+" "+md.getTown()+" ");
				//eMarker.getChild("description").setText("sales from area:\n"+salesAmount);
				eMarker.getChild("Point").getChild("coordinates").setText(md.getLongitude()+","+md.getLatitude()+",0");
				eMarkerFolder.addContent(eMarker);
			}
		
		} 
		catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void save(Document doc, String fileName) throws IOException {
		File file=new File(fileName);
        XMLOutputter outputter = new XMLOutputter();
        FileWriter writer = new FileWriter(file);
        outputter.output(doc,writer);
        writer.close();
    }
	
	private Document getPlacemarkStyle() throws JDOMException, IOException{
		if(docPlacemarkStyle==null){
			SAXBuilder builder=new SAXBuilder();
			docPlacemarkStyle=builder.build("markerschema.xml");
		}
		return (Document)docPlacemarkStyle.clone();
	}
	
	private Document getPolygonStyle() throws JDOMException, IOException{
		if(docPolygonStyle==null){
			SAXBuilder builder=new SAXBuilder();
			docPolygonStyle=builder.build("polyschema.xml");
		}
		return (Document)docPolygonStyle.clone();
	}
	
	private Document getPolygon() throws JDOMException, IOException{
		Document doc=null;
		SAXBuilder builder=new SAXBuilder();
		doc=builder.build("polygon.xml");
		return doc;
	}
	
	private Document getPlacemark() throws JDOMException, IOException{
		Document doc=null;
		SAXBuilder builder=new SAXBuilder();
		doc=builder.build("marker.xml");
		return doc;
	}
	
	private String colorToKMLHex(Color c){
		String hexValue="ff";
		String componentHex=null;;
		componentHex=Integer.toHexString(c.getBlue());
		if(componentHex.length()<2){
			componentHex=componentHex+"0";
		}
		hexValue=hexValue.concat(componentHex);
		componentHex=Integer.toHexString(c.getGreen());
		if(componentHex.length()<2){
			componentHex=componentHex+"0";
		}
		hexValue=hexValue.concat(componentHex);
		componentHex=Integer.toHexString(c.getRed());
		if(componentHex.length()<2){
			componentHex=componentHex+"0";
		}
		hexValue=hexValue.concat(componentHex);
		return hexValue;
	}
	
	private int resolveDrawOrder(SalesDataTO sd){
		int order=10;
		List<Double> salesList=salesMap.get(sd.getPostcode());
		if(salesList!=null){
			Collections.sort(salesList);
			for(Double val : salesList){
				if(sd.getSales()>=val){
					order++;
				}
				else{
					salesList.add(sd.getSales());
					break;
				}
				
			}
		}
		else{
			salesList=new ArrayList<Double>();
			salesList.add(sd.getSales());
			salesMap.put(sd.getPostcode(), salesList);
		}
		return order;
	}

	
}
