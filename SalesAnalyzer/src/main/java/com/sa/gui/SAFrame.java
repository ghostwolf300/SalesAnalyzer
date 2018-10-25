package com.sa.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.sa.SAController;
import com.sa.SAView;
import com.sa.to.SurveyTO;

public class SAFrame extends JFrame implements ActionListener,InternalFrameListener,SAView{

	private static final long serialVersionUID = 1L;
	
	public static final String AC_IMPORT_SURVEY="cmd_importSurvey";  //  @jve:decl-index=0:
	
	private JPanel jContentPane = null;
	private JMenuBar saMenuBar = null;
	private JMenu menuFile = null;
	private JMenuItem itemPickKml = null;
	private JMenu menuExport = null;
	private JMenuItem itemToDatabase = null;
	private JMenuItem itemToExcel = null;
	private JMenuItem itemClose = null;
	private JMenu menuSettings = null;
	private JMenuItem itemDatabase = null;
	private JMenu menuImport = null;
	private JMenuItem itemSurveyResults = null;
	private JMenuItem itemPmaData = null;
	private JMenuItem itemBasemapData = null;
	private JFileChooser fileChooser=null;
	
	private Map<String,JInternalFrame> iframes=null;
	private JDesktopPane deskPane = null;

	private JMenu menuAnalyzer = null;
	
	private SAController controller=null;
	
	/**
	 * This is the default constructor
	 */
	public SAFrame() {
		super();
		initialize();
		iframes=new HashMap<String,JInternalFrame>();
		controller=SAController.getInstance();
		controller.addView(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		Dimension screenDim=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenDim);
		this.setJMenuBar(getSaMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Survey Analyzer Beta");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			jContentPane.add(getDeskPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes saMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getSaMenuBar() {
		if (saMenuBar == null) {
			saMenuBar = new JMenuBar();
			saMenuBar.add(getMenuFile());
			saMenuBar.add(getMenuSettings());
			saMenuBar.add(getMenuAnalyzer());
		}
		return saMenuBar;
	}

	/**
	 * This method initializes menuFile	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuFile() {
		if (menuFile == null) {
			menuFile = new JMenu("File");
			menuFile.add(getItemPickKml());
			menuFile.add(getMenuImport());
			menuFile.add(getMenuExport());
			menuFile.addSeparator();
			menuFile.add(getItemClose());
		}
		return menuFile;
	}

	/**
	 * This method initializes itemPickKML	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemPickKml() {
		if (itemPickKml == null) {
			itemPickKml = new JMenuItem("Pick KML");
			itemPickKml.setActionCommand("pickKml");
		}
		return itemPickKml;
	}

	/**
	 * This method initializes menuExport	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuExport() {
		if (menuExport == null) {
			menuExport = new JMenu("Export...");
			menuExport.add(getItemToDatabase());
			menuExport.add(getItemToExcel());
		}
		return menuExport;
	}

	/**
	 * This method initializes itemToDatabase	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemToDatabase() {
		if (itemToDatabase == null) {
			itemToDatabase = new JMenuItem("to Database");
		}
		return itemToDatabase;
	}

	/**
	 * This method initializes itemToExcel	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemToExcel() {
		if (itemToExcel == null) {
			itemToExcel = new JMenuItem("to Excel");
		}
		return itemToExcel;
	}

	/**
	 * This method initializes itemClose	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemClose() {
		if (itemClose == null) {
			itemClose = new JMenuItem("Close");
		}
		return itemClose;
	}

	/**
	 * This method initializes menuKml	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuSettings() {
		if (menuSettings == null) {
			menuSettings = new JMenu("Settings");
			menuSettings.add(getItemDatabase());
		}
		return menuSettings;
	}

	/**
	 * This method initializes itemDatabase	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemDatabase() {
		if (itemDatabase == null) {
			itemDatabase = new JMenuItem("Database");
		}
		return itemDatabase;
	}

	/**
	 * This method initializes menuImport	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuImport() {
		if (menuImport == null) {
			menuImport = new JMenu("Import...");
			menuImport.add(getItemSurveyResults());
			menuImport.add(getItemPmaData());
			menuImport.add(getItemBasemapData());
		}
		return menuImport;
	}

	/**
	 * This method initializes itemSurveyResults	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemSurveyResults() {
		if (itemSurveyResults == null) {
			itemSurveyResults = new JMenuItem("Survey Results");
			itemSurveyResults.addActionListener(this);
			itemSurveyResults.setActionCommand(SAFrame.AC_IMPORT_SURVEY);
		}
		return itemSurveyResults;
	}

	/**
	 * This method initializes itemPmaData	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemPmaData() {
		if (itemPmaData == null) {
			itemPmaData = new JMenuItem("PMA");
		}
		return itemPmaData;
	}

	/**
	 * This method initializes itemBasemapData	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getItemBasemapData() {
		if (itemBasemapData == null) {
			itemBasemapData = new JMenuItem("Basemap");
		}
		return itemBasemapData;
	}
	
	/**
	 * This method initializes deskPane	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getDeskPane() {
		if (deskPane == null) {
			deskPane = new JDesktopPane();
		}
		return deskPane;
	}
	
	private JFileChooser getFileChooser(){
		if(fileChooser==null){
			fileChooser=new JFileChooser("D:\\Ohjelmointi\\Tietokannat\\visitor catchment\\kyselyt\\");
		}
		return fileChooser;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(SAFrame.AC_IMPORT_SURVEY)){
			JFileChooser fc=getFileChooser();
			int choice=fc.showOpenDialog(this);
			if(choice==JFileChooser.APPROVE_OPTION){
				File surveyFile=fc.getSelectedFile();
				controller.loadSurvey(surveyFile);
			}
		}
		
	}
	
	private void createSurveyDataIFrame(SurveyTO survey){
		JInternalFrame surveyFrame=new IFrameSurveyData(survey);
		surveyFrame.addInternalFrameListener(this);
		iframes.put(survey.getName(), surveyFrame);
		deskPane.add(surveyFrame);
	}

	/**
	 * This method initializes menuAnalyzer	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuAnalyzer() {
		if (menuAnalyzer == null) {
			menuAnalyzer = new JMenu("Analyzer");
		}
		return menuAnalyzer;
	}

	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent e) {
		if(e.getPropertyName().equals(SAController.P_SURVEYS)){
			List<SurveyTO> surveys=(List<SurveyTO>)e.getNewValue();
			for(SurveyTO s : surveys){
				if(iframes.get(s.getName())==null){
					this.createSurveyDataIFrame(s);
				}
			}
		}
		
	}

	public void internalFrameActivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameClosed(InternalFrameEvent e) {
		IFrameSurveyData iframe=(IFrameSurveyData)e.getInternalFrame();
		SurveyTO s=iframe.getSurvey();
		iframes.remove(s.getSurveyId());
		controller.removeSurvey(s);
	}

	public void internalFrameClosing(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameIconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameOpened(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
