package com.sa.gui;

import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTree;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Insets;
import java.net.URL;

public class SurveySelectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrlSurveys = null;
	private JTree treeSurveys = null;
	private JScrollPane scrlSelection = null;
	private JButton btnAdd = null;
	private JButton btnRemove = null;
	private JButton btnCancel = null;
	private JButton btnOk = null;

	/**
	 * This is the default constructor
	 */
	public SurveySelectPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 2;
		gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints5.insets = new Insets(0, 0, 20, 0);
		gridBagConstraints5.gridy = 2;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints4.insets = new Insets(0, 0, 20, 0);
		gridBagConstraints4.gridy = 2;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.insets = new Insets(0, 0, 60, 0);
		gridBagConstraints3.gridy = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.insets = new Insets(70, 0, 0, 0);
		gridBagConstraints2.gridy = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.NONE;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.gridheight = 2;
		gridBagConstraints1.gridx = 2;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.gridx = 0;
		this.setSize(380, 270);
		this.setLayout(new GridBagLayout());
		this.add(getScrlSurveys(), gridBagConstraints);
		this.add(getScrlSelection(), gridBagConstraints1);
		this.add(getBtnAdd(), gridBagConstraints2);
		this.add(getBtnRemove(), gridBagConstraints3);
		this.add(getBtnCancel(), gridBagConstraints4);
		this.add(getBtnOk(), gridBagConstraints5);
	}

	/**
	 * This method initializes scrlSurveys	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrlSurveys() {
		if (scrlSurveys == null) {
			scrlSurveys = new JScrollPane();
			scrlSurveys.setPreferredSize(new Dimension(160, 180));
			scrlSurveys.setViewportView(getTreeSurveys());
		}
		return scrlSurveys;
	}

	/**
	 * This method initializes treeSurveys	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getTreeSurveys() {
		if (treeSurveys == null) {
			treeSurveys = new JTree();
		}
		return treeSurveys;
	}

	/**
	 * This method initializes scrlSelection	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrlSelection() {
		if (scrlSelection == null) {
			scrlSelection = new JScrollPane();
			scrlSelection.setPreferredSize(new Dimension(160, 180));
		}
		return scrlSelection;
	}

	/**
	 * This method initializes btnAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			String imgLocation="/toolbarButtonGraphics/navigation/Forward24.gif";
			URL imgURL=SurveySelectPanel.class.getResource(imgLocation);
			btnAdd.setIcon(new ImageIcon(imgURL));
			btnAdd.setPreferredSize(new Dimension(38, 38));
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnRemove	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton();
			String imgLocation="/toolbarButtonGraphics/navigation/Back24.gif";
			URL imgURL=SurveySelectPanel.class.getResource(imgLocation);
			btnRemove.setIcon(new ImageIcon(imgURL));
			btnRemove.setPreferredSize(new Dimension(38, 38));
		}
		return btnRemove;
	}

	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setPreferredSize(new Dimension(80, 36));
			btnCancel.setText("Cancel");
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setPreferredSize(new Dimension(80, 36));
			btnOk.setText("Ok");
		}
		return btnOk;
	}

}
