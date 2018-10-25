package com.sa.dao;

import java.io.File;
import java.util.List;

import com.sa.PolygonArea;
import com.sa.KMLCreator;
import com.sa.KMLReader;

public class KMLDAO {
	
	public KMLReader reader=null;
	public KMLCreator creator=null;
	
	public KMLDAO(){
		reader=new KMLReader();
	}
	
	public List<PolygonArea> getPolygonAreas(File kmlFile){
		reader.setKMLFile(kmlFile);
		return reader.getPolygonAreas();
	}
	
}
