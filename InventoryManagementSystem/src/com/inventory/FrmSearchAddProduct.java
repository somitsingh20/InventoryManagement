package com.inventory;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class FrmSearchAddProduct extends JDialog {

	JButton JBSearch = new JButton("Search",new ImageIcon("images/search.png"));
	JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));

	JLabel JLPic1 = new JLabel(new ImageIcon("images/bSearch.png"));
	JLabel JLBanner = new JLabel("Enter text and select where to locate.");
	JPanel JPDialogContainer = new JPanel();

	//JLabel JLSearchFor = new JLabel("Search For:");
	JLabel JLSearchIn = new JLabel("Look In:");

	//JTextField JTFSearchFor = new JTextField();
	public static Connection cnSearch;
	public static Statement stSearch;
	public static ResultSet rsSearch;
	AutoCompleteDecorator decorator;
	String StrListItem[]={};
	ArrayList<String> list = new ArrayList<>();
	JComboBox JCBSearchProduct;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	public FrmSearchAddProduct(JFrame OwnerForm, Connection srcCon){
		super(OwnerForm,true);
	    setTitle("Search Customer");
	    
	    cnSearch = srcCon;
	    try {
			stSearch = cnSearch.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    //Search Products by Lookup
	    String getProductList = "Select productname from imsproducts";
	    try {
			rsSearch = srcCon.createStatement().executeQuery(getProductList);
			while(rsSearch.next()){
				list.add(rsSearch.getString("productname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    list.add(0, "");
	    Object[] objects = list.toArray();
		JCBSearchProduct = new JComboBox(objects);
		AutoCompleteDecorator.decorate(JCBSearchProduct);
		StrListItem = null;

		JPDialogContainer.setLayout(null);

		JLPic1.setBounds(5,5,32,32);
		JPDialogContainer.add(JLPic1);

		JLBanner.setBounds(55,5,280,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPDialogContainer.add(JLBanner);
		
		/*JLSearchFor.setBounds(5,50,105,20);
		JLSearchFor.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFSearchFor.setBounds(110,50,225,20);
		JTFSearchFor.setFont(new Font("Dialog",Font.PLAIN,12));

		JPDialogContainer.add(JLSearchFor);
		JPDialogContainer.add(JTFSearchFor);*/

		JLSearchIn.setBounds(5,72,105,20);
		JLSearchIn.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBSearchProduct.setBounds(110,72,225,20);
		JCBSearchProduct.setFont(new Font("Dialog",Font.PLAIN,12));

		JPDialogContainer.add(JLSearchIn);
		JPDialogContainer.add(JCBSearchProduct);
		
		JBSearch.setBounds(137,100,99,25);
		JBSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearch.setMnemonic(KeyEvent.VK_S);
		JBSearch.addActionListener(JBActionListener);
		JBSearch.setActionCommand("search");
		JPDialogContainer.add(JBSearch);

		JBCancel.setBounds(237,100,99,25);
		JBCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCancel.setMnemonic(KeyEvent.VK_C);
		JBCancel.addActionListener(JBActionListener);
		JBCancel.setActionCommand("cancel");
		JPDialogContainer.add(JBCancel);

		getContentPane().add(JPDialogContainer);
		setSize(350,165);
		setResizable(false);
		setLocation((screen.width - 350)/2,((screen.height-165)/2));

	}
	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="search"){
					FrmSales.reloadRecord("SELECT productname,quantity,sprice FROM imsproducts WHERE productname='" + JCBSearchProduct.getSelectedItem().toString()+ "'");
					dispose();
				}
			else{
				dispose();
			}
		}
	};

}
