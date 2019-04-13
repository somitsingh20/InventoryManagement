package com.inventory;

import javax.swing.*;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FrmRevenueByCustomer extends JDialog {
	//Start create variables
	JButton JBSearch = new JButton("OK");
	
	JRadioButton JRBCustId = new JRadioButton("Customer ID");
	JRadioButton JRBCustName = new JRadioButton("Customer Name");
	
	ButtonGroup BGroup = new  ButtonGroup();
	
	JLabel JLPic1 = new JLabel(new ImageIcon("images/bSearch.png"));
	JLabel JLBanner = new JLabel("Select to search record");
	JPanel JPDialogContainer = new JPanel();
	
	JTextField JTFCustId = new JTextField();
	JTextField JTFCustName = new JTextField();
	
	public static Connection cnSearch;
	public static Statement stSearch;
	public static ResultSet rsId;
	public static ResultSet rsName;
	AutoCompleteDecorator decorator;
	String StrListItem[]={};
	ArrayList<String> idlist = new ArrayList<>();
	ArrayList<String> namelist = new ArrayList<>();
	JComboBox JCBSearchById;
	JComboBox JCBSearchByName;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	public FrmRevenueByCustomer(JFrame OwnerForm, Connection cnCus){
		super(OwnerForm,true);
	    setTitle("Search Records By Customer");
	    
	    ////////////////////
	    
	    cnSearch = cnCus;
	    try {
			stSearch = cnSearch.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    //Search Customer ID by Lookup
	    String getCustID = "Select cid from imscustomer";
	    try {
			rsId = cnCus.createStatement().executeQuery(getCustID);
			while(rsId.next()){
				idlist.add(rsId.getString("cid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    idlist.add(0, "");
	    Object[] objects = idlist.toArray();
	    JCBSearchById = new JComboBox(objects);
		AutoCompleteDecorator.decorate(JCBSearchById);
		
		//Search Customer Name by Lookup
		String getCustName = "Select cname from imscustomer";
		try {
			rsName = cnCus.createStatement().executeQuery(getCustName);
			while(rsName.next()){
				namelist.add(rsName.getString("cname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		namelist.add(0,"");
		Object[] obj = namelist.toArray();
		JCBSearchByName = new JComboBox(obj);
		AutoCompleteDecorator.decorate(JCBSearchByName);
		/////////////////////////////
	    
		JPDialogContainer.setLayout(null);

		JLPic1.setBounds(25,5,32,32);
		JPDialogContainer.add(JLPic1);

		JLBanner.setBounds(65,5,280,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPDialogContainer.add(JLBanner);
		
		JRBCustId.setBounds(25,70,100,25);
		JRBCustId.setSelected(true);
		JCBSearchById.setBounds(130,70,100,25);
		JPDialogContainer.add(JRBCustId);
		JPDialogContainer.add(JCBSearchById);
		
		JRBCustName.setBounds(25,120,100,25);
		JCBSearchByName.setBounds(130,120,100,25);
		JPDialogContainer.add(JRBCustName);
		JPDialogContainer.add(JCBSearchByName);
		
		BGroup.add(JRBCustId);
		BGroup.add(JRBCustName);
		
		JBSearch.setBounds(290,120,70,28);
		JBSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearch.setMnemonic(KeyEvent.VK_S);
		JBSearch.addActionListener(JBActionListener);
		JBSearch.setActionCommand("search");
		JPDialogContainer.add(JBSearch);

		getContentPane().add(JPDialogContainer);
		setSize(400,330);
		setResizable(false);
		setLocation((screen.width - 350)/2,((screen.height-465)/2));

	}
	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="search"){
				
					if(JRBCustId.isSelected()){
						FrmRevenueRecord.reloadRecord("SELECT invoice_number,sales,to_char(datetime, 'DD.MM.YYYY') dt FROM view_revenue WHERE cid="+JCBSearchById.getSelectedItem());
						dispose();
					}
					else if (JRBCustName.isSelected()){
						FrmRevenueRecord.reloadRecord("SELECT invoice_number,sales,to_char(datetime, 'DD.MM.YYYY') dt FROM view_revenue WHERE cname='"+JCBSearchByName.getSelectedItem()+"'");
						dispose();
					}
			}else{
				dispose();
			}
		}
	};
}
