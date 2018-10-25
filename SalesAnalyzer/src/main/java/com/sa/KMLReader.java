package com.sa;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;


public class KMLReader {
	
	private Document doc=null;
	
	public KMLReader(){
		
	}
	
	public KMLReader(File file){
		try {
			doc=buildDocument(file);
		} 
		catch (JDOMException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setKMLFile(File file){
		try {
			doc=buildDocument(file);
		} 
		catch (JDOMException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<PolygonArea> getPolygonAreas(){
		
		List<PolygonArea> areas=new ArrayList<PolygonArea>();
		
		Namespace ns=Namespace.getNamespace("http://www.opengis.net/kml/2.2");
		Element root=doc.getRootElement();
		Element eDoc=root.getChild("Document",ns);
		
		List<Element> placemarks=eDoc.getChildren("Placemark",ns);
		
		ListIterator<Element> iter=placemarks.listIterator();
		Element polyPlacemark=null;
		
		while(iter.hasNext()){
			
			polyPlacemark=iter.next();
			PolygonArea area=new PolygonArea();
			
			String areaName=polyPlacemark.getChild("name",ns).getText();
			area.setName(areaName);
			
			String coordStr=null;
			coordStr=polyPlacemark.getChild("Polygon",ns).getChild("outerBoundaryIs",ns).getChild("LinearRing",ns).getChild("coordinates",ns).getText();
			String[] coordStrArr=coordStr.split("\\s");
			
			List<Point2D.Double> points=getPointList(coordStrArr);
			
			Path2D.Double polygon=new Path2D.Double();
			ListIterator<Point2D.Double> piter=points.listIterator();
			
			boolean first=true;
			Point2D.Double p=null;
			
			while(piter.hasNext()){
				p=piter.next();
				if(first){
					polygon.moveTo(p.x, p.y);
					first=false;
				}
				else{
					polygon.lineTo(p.x, p.y);
				}
			}
			
			polygon.closePath();
			
			area.setPolygon(polygon);
			areas.add(area);
		}
			
		return areas;
	}
	
	private List<Point2D.Double> getPointList(String[] coordStrArr){
		
		List<Point2D.Double> pointList=new ArrayList<Point2D.Double>();
		System.out.println(coordStrArr.length);
		for(int i=0;i<coordStrArr.length;i++){
			//System.out.println(coordStrArr[i]);
			if(!coordStrArr[i].isEmpty()){
				String[] xyzStr=coordStrArr[i].split("\\s*,\\s*");
				//System.out.println(xyzStr[0].trim());
				Point2D.Double point=new Point2D.Double();
				point.x=Double.parseDouble(xyzStr[0]);
				point.y=Double.parseDouble(xyzStr[1]);
				pointList.add(point);
			}
		}
		
		return pointList;
	}
	
	
	private Document buildDocument(File file) throws JDOMException, IOException{
		SAXBuilder builder=new SAXBuilder();
		Document doc=builder.build(file);
		return doc;
	}
	
}
