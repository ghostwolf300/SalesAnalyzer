package com.sa.gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.sa.SAController;
import com.sa.to.SurveyDataTO;

import java.awt.GridBagConstraints;
import java.util.List;


public class SurveyDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrlSurveyData = null;
	private JTable tblSurveyData = null;
	
	/**
	 * This is the default constructor
	 */
	public SurveyDataPanel() {
		super();
		initialize();
	}
	
	public SurveyDataPanel(List<SurveyDataTO> dataList){
		super();
		initialize();
		SurveyDataTableModel model=(SurveyDataTableModel)tblSurveyData.getModel();
		model.setSurveyResults(dataList);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.weightx = 1.0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getScrlSurveyData(), gridBagConstraints);
	}

	/**
	 * This method initializes scrlSurveyResults	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrlSurveyData() {
		if (scrlSurveyData == null) {
			scrlSurveyData = new JScrollPane();
			scrlSurveyData.setViewportView(getTblSurveyData());
		}
		return scrlSurveyData;
	}

	/**
	 * This method initializes tblSurveyResults	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTblSurveyData() {
		if (tblSurveyData == null) {
			tblSurveyData = new JTable(new SurveyDataTableModel());
		}
		return tblSurveyData;
	}


}
