package com.sa;

import java.awt.geom.Path2D;
import java.util.List;

import com.sa.to.PostcodeTO;

public class PolygonArea {
	
	private String name=null;
	private Path2D.Double polygon=null;
	private List<PostcodeTO> postcodes=null;
	
	public PolygonArea(){
		
	}
	
	public PolygonArea(String name,Path2D.Double polygon){
		this.name=name;
		this.polygon=polygon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Path2D.Double getPolygon() {
		return polygon;
	}

	public void setPolygon(Path2D.Double polygon) {
		this.polygon = polygon;
	}

	public List<PostcodeTO> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(List<PostcodeTO> postcodes) {
		this.postcodes = postcodes;
	}
	
	
	

}
