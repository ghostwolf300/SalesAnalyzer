package com.sa.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class KMLFilter extends FileFilter {
	
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory() || isKMLFile(f)){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isKMLFile(File f){
		String fileName=f.getName();
		String extension=null;
		    
	    int i = fileName.lastIndexOf('.');
	    if (i > 0 &&  i < fileName.length() - 1) {
	    	extension = fileName.substring(i+1).toLowerCase();
	    }
	    
	    if(extension!=null && extension.equals("kml")){
	    	return true;
	    }
	    else{
	    	return false;
	    }
		
	}

}
