package com.sa.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import com.sa.to.SurveyDataTO;
import com.sa.to.SurveyTO;

public class IFrameSurveyData extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane tabsStoreData=null;
	private SurveyTO survey=null;

	/**
	 * This is the xxx default constructor
	 */
	public IFrameSurveyData() {
		super();
		initialize();
	}
	
	public IFrameSurveyData(EventListener el){
		super();
		initialize();
	}
	
	public IFrameSurveyData(SurveyTO survey){
		super();
		initialize();
		this.survey=survey;
		this.setTitle(survey.getName());
		this.createStoreTabs(survey.getData());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setLocation(100, 100);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setClosable(true);
		this.setVisible(true);	
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTabsStoreData(),BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	private JTabbedPane getTabsStoreData(){
		if(tabsStoreData==null){
			tabsStoreData=new JTabbedPane();
		}
		return tabsStoreData;
	}
	
	private JPanel createDataPane(List<SurveyDataTO> dataList){
		JPanel dataPane=new SurveyDataPanel(dataList);
		return dataPane;
	}
	
	private void createStoreTabs(List<SurveyDataTO> dataList){
		Map<String,List<SurveyDataTO>> storeDataMap=new HashMap<String,List<SurveyDataTO>>();
		for(SurveyDataTO data : dataList){
			if(storeDataMap.get(data.getStoreId())==null){
				List<SurveyDataTO> storeData=new ArrayList<SurveyDataTO>();
				storeData.add(data);
				storeDataMap.put(data.getStoreId(), storeData);
			}
			else{
				storeDataMap.get(data.getStoreId()).add(data);
			}
		}
		Set<String> storeIds=storeDataMap.keySet();
		for(String sid : storeIds){
			tabsStoreData.addTab(sid, createDataPane(storeDataMap.get(sid)));
		}	
	}
	
	public SurveyTO getSurvey(){
		return survey;
	}

}
