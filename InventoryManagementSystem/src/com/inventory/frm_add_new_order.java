package com.inventory;

import javax.swing.*;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;

public class frm_add_new_order extends JDialog{
	JButton JBUpdate = new JButton("Add",new ImageIcon("images/save.png"));
	JButton JBReset = new JButton("Reset",new ImageIcon("images/reset.png"));
	JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));

	JLabel JLPic1 = new JLabel();
	JLabel JLBanner = new JLabel("Please fill-up all the required fields.");

	JLabel JLProductName = new JLabel("Product :");
	JLabel JLQuantity = new JLabel("Quantity :");
	JLabel JLSupplierId = new JLabel("Supplier ID :");
	JLabel JLSupplierName = new JLabel("Supplier Name:");
	JLabel JLSupplierCity = new JLabel("Supplier City");
	JLabel JLUnitCost = new JLabel("Unit Cost");

	JTextField JTFProductName = new JTextField();
	JTextField JTFQuantity = new JTextField();
	//JTextField JTFSupplierId = new JTextField();
	//JTextField JTFSupplierName = new JTextField();
	//JTextField JTFSupplierCity = new JTextField();
	JTextField JTFUnitCost = new JTextField();
	
	AutoCompleteDecorator decorator;
	ArrayList<String> idList = new ArrayList<>();
	ArrayList<String> nameList = new ArrayList<>();
	ArrayList<String> cityList = new ArrayList<>();
	JComboBox JCBSupplierId;
	JComboBox JCBSupplierName;
	JComboBox JCBSupplierCity;
	
	int orderID =0;
	Connection cnAEO;
	Statement stAEO;
	ResultSet rsAEO;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	boolean ADDING_STATE;

	public frm_add_new_order(boolean ADD_STATE,JFrame OwnerForm,Connection srcCon,String srcSQL){
		super(OwnerForm,true);
		cnAEO = srcCon;
		ADDING_STATE = ADD_STATE;
		try{
			stAEO = cnAEO.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){

		}
		if(ADD_STATE==true){
			JLPic1.setIcon(new ImageIcon("images/bNew.png"));
			setTitle("Add New Warehouse");	
		}
		
		srcSQL = "Select max(orderid) as id from imspurchase";
		try {
			rsAEO = srcCon.createStatement().executeQuery(srcSQL);
			if(rsAEO.next()){
				String str = rsAEO.getString("id");
				if(str==null){
					orderID = 1;
				}
				else
				{
					int oID = Integer.parseInt(str);
					orderID = oID+1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Get supplier info
		String getSupplier = "Select * from imssupplier";
		try {
			rsAEO = srcCon.createStatement().executeQuery(getSupplier);
			while(rsAEO.next()){
				idList.add(rsAEO.getString("sid"));
				nameList.add(rsAEO.getString("supplier"));
				cityList.add(rsAEO.getString("location"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		idList.add(0, "");
		nameList.add(0,"");
		cityList.add(0,"");
		
		Object[] idobjects = idList.toArray();
		JCBSupplierId = new JComboBox(idobjects);
		AutoCompleteDecorator.decorate(JCBSupplierId);
		
		Object[] nameobjects = nameList.toArray();
		JCBSupplierName = new JComboBox(nameobjects);
		AutoCompleteDecorator.decorate(JCBSupplierName);
		
		Object[] cityobjects = cityList.toArray();
		JCBSupplierCity = new JComboBox(cityobjects);
		AutoCompleteDecorator.decorate(JCBSupplierCity);
		
		JPanel JPContainer = new JPanel();
		JPContainer.setLayout(null);
		
		JLPic1.setBounds(5,5,32,32);
		JPContainer.add(JLPic1);

		
		JLBanner.setBounds(55,5,268,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JLBanner);
		
		JLProductName.setBounds(5,50,105,20);
		JLProductName.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFProductName.setBounds(110,50,200,20);
		JTFProductName.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLProductName);
		JPContainer.add(JTFProductName);
		
		JLQuantity.setBounds(5,72,105,20);
		JLQuantity.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFQuantity.setBounds(110,72,200,20);
		JTFQuantity.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLQuantity);
		JPContainer.add(JTFQuantity);

		
		JLSupplierId.setBounds(5,94,105,20);
		JLSupplierId.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBSupplierId.setBounds(110,94,200,20);
		JCBSupplierId.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLSupplierId);
		JPContainer.add(JCBSupplierId);

		
		JLSupplierName.setBounds(5,116,105,20);
		JLSupplierName.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBSupplierName.setBounds(110,116,200,20);
		JCBSupplierName.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLSupplierName);
		JPContainer.add(JCBSupplierName);

		
		JLSupplierCity.setBounds(5,138,105,20);
		JLSupplierCity.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBSupplierCity.setBounds(110,138,200,20);
		JCBSupplierCity.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLSupplierCity);
		JPContainer.add(JCBSupplierCity);

		
		JLUnitCost.setBounds(5,160,105,20);
		JLUnitCost.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFUnitCost.setBounds(110,160,200,20);
		JTFUnitCost.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLUnitCost);
		JPContainer.add(JTFUnitCost);

		
		JBUpdate.setBounds(5,292,105,25);
		JBUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBUpdate.setMnemonic(KeyEvent.VK_A);
		JBUpdate.addActionListener(JBActionListener);
		JBUpdate.setActionCommand("update");
		JPContainer.add(JBUpdate);

		
		JBReset.setBounds(112,292,99,25);
		JBReset.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReset.setMnemonic(KeyEvent.VK_R);
		JBReset.addActionListener(JBActionListener);
		JBReset.setActionCommand("reset");
		JPContainer.add(JBReset);

		
		JBCancel.setBounds(212,292,99,25);
		JBCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCancel.setMnemonic(KeyEvent.VK_C);
		JBCancel.addActionListener(JBActionListener);
		JBCancel.setActionCommand("cancel");
		JPContainer.add(JBCancel);

		getContentPane().add(JPContainer);
		setSize(325,357);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-357)/2));
	}
	private boolean RequiredFieldEmpty(){
		if(JTFProductName.getText().equals("") || JTFQuantity.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Some required fields is/are empty.\nPlease check it and try again.","Inventory System",JOptionPane.WARNING_MESSAGE);
			JTFProductName.requestFocus();
			return true;
		}else{
			return false;
		}
	}
	private void clearFields(){
		JTFProductName.setText("");
		JTFQuantity.setText("");
		JTFUnitCost.setText("");
	}

	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			
			if(srcObj=="update"){
				if(RequiredFieldEmpty()==false){
					if(ADDING_STATE == true){
						try{
							stAEO.executeUpdate("INSERT INTO imspurchase(product,quantity,supplierid,supplierName,suppliercity,cost,orderdate,status,orderid) " +
		   							   	        "VALUES ('" +
		   							   	        JTFProductName.getText() + "', '" +
		   							   	        JTFQuantity.getText() + "', '" +
		   							   	        JCBSupplierId.getSelectedItem().toString() + "', '" +
		   							   	        JCBSupplierName.getSelectedItem().toString() + "', '" +
		   							   	        JCBSupplierCity.getSelectedItem().toString() + "', '" +
		   							   	        JTFUnitCost.getText() + "',sysdate,'pending',"+orderID+")");
							stAEO.executeUpdate("Insert into imsstatus values('"+orderID+"','"+JTFProductName.getText()+"',sysdate,'pending','ordered product')");
							String com = "commit";
							stAEO.executeUpdate(com);
							// Start Display the new record
		   					/*int total =0;
		   					total = clsPublicMethods.getMaxNum("SELECT * FROM imspurchase ORDER BY product ASC",cnAEW,"product");
		   					if(total != 0){
		   						FrmPurchase.reloadRecord("SELECT * FROM imspurchase WHERE WarehouseIndex = " + total + " ORDER BY WarehouseName ASC");
		   					}else{
		   						FrmPurchase.reloadRecord("SELECT * FROM imspurchase ORDER BY product ASC");
		   					}
		   					total =0;*/
		   					// End Display the new record
		   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
		   					String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Inventory Management System",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
							if(PromptResult==0){
								clearFields();
								JTFProductName.requestFocus(true);
							}else{
								dispose();
							}
		   				}catch(SQLException sqlEx){
		   					System.out.println(sqlEx.getMessage());
		   				}
					}
				}
			}else if(srcObj=="reset"){
				clearFields();
			}else if(srcObj=="cancel"){
				dispose();
			}
		}
	};

}