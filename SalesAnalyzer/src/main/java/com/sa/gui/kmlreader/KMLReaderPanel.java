package com.sa.gui.kmlreader;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sa.PolygonArea;
import com.sa.SAController;
import com.sa.SAView;
import javax.swing.JList;

public class KMLReaderPanel extends JPanel implements SAView, MouseListener {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabDataViews = null;
	private JScrollPane scrlPostcodes = null;
	private JTable tblPostcodes = null;
	/**
	 * This is the default constructor
	 */
	public KMLReaderPanel() {
		super();
		SAController.getInstance().addView(this);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		this.setSize(532, 408);
		this.setLayout(new BorderLayout());
		this.add(getTabDataViews(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes tabDataViews	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTabDataViews() {
		if (tabDataViews == null) {
			tabDataViews = new JTabbedPane();
			tabDataViews.setPreferredSize(new Dimension(300, 300));
			tabDataViews.addTab(null, null, getScrlPostcodes(), null);
		}
		return tabDataViews;
	}

	/**
	 * This method initializes scrlPostcodes	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrlPostcodes() {
		if (scrlPostcodes == null) {
			scrlPostcodes = new JScrollPane();
			scrlPostcodes.setViewportView(getTblPostcodes());
		}
		return scrlPostcodes;
	}

	/**
	 * This method initializes tblPostcodes	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTblPostcodes() {
		if (tblPostcodes == null) {
			tblPostcodes = new JTable();
		}
		return tblPostcodes;
	}

	public void modelPropertyChange(PropertyChangeEvent e) {
		if(e.getPropertyName().equals(SAController.P_POLYGON_AREAS)){
			List<PolygonArea> polygonAreas=(List<PolygonArea>)e.getNewValue();
			
		}	
	}

	public void mouseClicked(MouseEvent me) {
		
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}  
