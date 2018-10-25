package com.sa.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractModel {
	
	protected PropertyChangeSupport propertyChangeSupport=null;
	
	public AbstractModel(){
		propertyChangeSupport=new PropertyChangeSupport(this);
	}
	
	public AbstractModel(PropertyChangeListener pl){
		propertyChangeSupport=new PropertyChangeSupport(this);
		propertyChangeSupport.addPropertyChangeListener(pl);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pl){
		propertyChangeSupport.addPropertyChangeListener(pl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pl){
		propertyChangeSupport.removePropertyChangeListener(pl);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
