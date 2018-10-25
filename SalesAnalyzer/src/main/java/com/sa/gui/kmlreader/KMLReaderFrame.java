package com.sa.gui.kmlreader;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;

import com.sa.SAController;
import com.sa.gui.KMLFilter;

public class KMLReaderFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String ACTION_PICK_KML="pickkml";
	private static final String ACTION_EXPORT_TO_DATABASE="exporttodatabase";
	private static final String ACTION_EXPORT_TO_EXCEL="exporttoexcel";
	private static final String ACTION_CLOSE="close";
	
	private JPanel jContentPane = null;
	private JPanel readerPane=null;
	private JMenuBar readerMenuBar=null;
	private JFileChooser fileChooser=null;
	

	/**
	 * This is the default constructor
	 */
	public KMLReaderFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(460, 340);
		this.setContentPane(getJContentPane());
		this.setJMenuBar(getReaderMenuBar());
		this.setTitle("KML Reader");
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
			jContentPane.add(getReaderPane(),BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	private JPanel getReaderPane() {
		if (readerPane == null) {
			readerPane = new KMLReaderPanel();
		}
		return readerPane;
	}
	
	private JMenuBar getReaderMenuBar(){
		if(readerMenuBar==null){
			readerMenuBar=new JMenuBar();
			JMenu menuFile=new JMenu("File");
			
			JMenuItem itemPickKML=new JMenuItem("Pick KML");
			itemPickKML.setActionCommand(KMLReaderFrame.ACTION_PICK_KML);
			itemPickKML.addActionListener(this);
			menuFile.add(itemPickKML);
			
			JMenu menuExport=new JMenu("Export...");
			JMenuItem itemToDatabase=new JMenuItem("to Database");
			JMenuItem itemToExcel=new JMenuItem("to Excel");
			menuExport.add(itemToDatabase);
			menuExport.add(itemToExcel);
			menuFile.add(menuExport);
			
			menuFile.addSeparator();
			JMenuItem itemClose=new JMenuItem("Close");
			menuFile.add(itemClose);
			
			readerMenuBar.add(menuFile);
		}
		return readerMenuBar;
	}
	
	private JFileChooser getFileChooser(){
		if(fileChooser==null){
			fileChooser=new JFileChooser();
			fileChooser.setFileFilter(new KMLFilter());
		}
		return fileChooser;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(KMLReaderFrame.ACTION_PICK_KML)){
			JFileChooser fc=getFileChooser();
			int choice=fc.showOpenDialog(this);
			if(choice==JFileChooser.APPROVE_OPTION){
				File f=fc.getSelectedFile();
				SAController.getInstance().loadAreaData(f);
			}
			
		}
	}
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
