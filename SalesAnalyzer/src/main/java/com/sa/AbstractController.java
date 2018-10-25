package com.sa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sa.model.AbstractModel;
import com.sa.SAView;

public abstract class AbstractController implements PropertyChangeListener {
	
	private List<AbstractModel> models=null;
	private List<SAView> views=null;
	
	public AbstractController(){
		models=new ArrayList<AbstractModel>();
		views=new ArrayList<SAView>();
	}
	
	public void addModel(AbstractModel model){
		model.addPropertyChangeListener(this);
		models.add(model);
	}
	
	public void removeModel(AbstractModel model){
		models.remove(model);
	}
	
	public void addView(SAView view){
		views.add(view);
	}
	
	public void propertyChange(PropertyChangeEvent e){
		for(SAView v : views){
			v.modelPropertyChange(e);
		}
	}
	
	protected void setModelProperty(String propertyName,Object newValue){
		
		for(AbstractModel model : models){
			try{
				for(Method method : model.getClass().getMethods()){
					if(method.getName().equals("set"+propertyName)){
						Class<?>[] paramTypes=method.getParameterTypes();
						//setModelProperty only takes one parameter
						//target objects method should not have more than one parameter also
						//TODO: Investigate more
						Class<?> paramType=paramTypes[0];
						//System.out.println(newValue.getClass().getName());
						
						if(paramType.isAssignableFrom(newValue.getClass())){
							//System.out.println("invoking...");
							method.invoke(model, newValue);
							break;
						}
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	protected void action(String actionName){
		for(AbstractModel m : models){
			try{
				Method method=m.getClass().getMethod("action"+actionName);
				method.invoke(m);
			}
			catch(NoSuchMethodException e){
				//Let it pass
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	protected void action(String actionName,Object actionParameter){
		for(AbstractModel m : models){
			try{
				Method method=m.getClass().getMethod("action"+actionName,new Class[]{
						actionParameter.getClass()
				});
				method.invoke(m,actionParameter);
			}
			catch(NoSuchMethodException e){
				//Let it pass
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	protected Object getModelProperty(String propertyName){
		Object modelProperty=null;
		for(AbstractModel m : models){
			try{
				Method method=m.getClass().getMethod("get"+propertyName);
				modelProperty=method.invoke(m);
			}
			catch(NoSuchMethodException e){
				//Let it pass
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return modelProperty;
	}
	
	protected AbstractModel getModel(String modelKey){
		return null;
	}
	
}
