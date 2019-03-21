package com.inventory;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class frm_add_edit_supplier extends JDialog{
	JButton JBUpdate = new JButton(new ImageIcon("images/save.png"));
	JButton JBReset = new JButton("Reset",new ImageIcon("images/reset.png"));
	JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));
	
	JLabel JLPic1 = new JLabel();
	JLabel JLBanner = new JLabel("Please fill-up all the required fields.");
	
	JLabel JLSupplierId = new JLabel("Supplier ID:");
	JLabel JLSupplierName = new JLabel("Supplier Name:");
	JLabel JLLocation = new JLabel("Contact Name:");
	JLabel JLPhoneNumber = new JLabel("Contact Title:");
	
	JTextField JTFSupplierId = new JTextField();
	JTextField JTFSupplierName = new JTextField();
	JTextField JTFLocation = new JTextField();
	JTextField JTFPhoneNumber = new JTextField();
	
	Connection cnAES;
	Statement stAES;
	ResultSet rsAES;
		
	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	boolean ADDING_STATE;
	
	public frm_add_edit_supplier(boolean ADD_STATE,JFrame OwnerForm,Connection srcCon,String srcSQL){
		super(OwnerForm,true);
		cnAES = srcCon;
		ADDING_STATE = ADD_STATE;
		try{
			stAES = cnAES.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){
			System.out.println("\nERROR IN frm_add_edit_supplier(frm_add_edit_supplier):" + sqlEx + "\n");
		}
		
		if(ADD_STATE==true){
			JLPic1.setIcon(new ImageIcon("images/bNew.png"));
			setTitle("Add New Supplier");
			JBUpdate.setText("Update");
			
		}else{
			JLPic1.setIcon(new ImageIcon("images/bModify.png"));
			setTitle("Modify Supplier");
			JBUpdate.setText("Save");
			try{
				rsAES = stAES.executeQuery(srcSQL);
				rsAES.next();					
					JTFSupplierId.setText("" + rsAES.getString("sid"));
					JTFSupplierName.setText("" + rsAES.getString("supplier"));
					JTFLocation.setText("" + rsAES.getString("location"));
					JTFPhoneNumber.setText("" + rsAES.getString("pnumber"));
			}catch(SQLException sqlEx){
				System.out.println(sqlEx.getMessage());
			}
		}
		JPanel JPContainer = new JPanel();
		JPContainer.setLayout(null);
		//-- Add the JLPic1
		JLPic1.setBounds(5,5,32,32);
		JPContainer.add(JLPic1);
		
		JLBanner.setBounds(55,5,268,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JLBanner);
		
		/*JLSupplierId.setBounds(5,50,105,20);
		JLSupplierId.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFSupplierId.setBounds(110,50,200,20);
		JTFSupplierId.setFont(new Font("Dialog",Font.PLAIN,12));
		JTFSupplierId.setEditable(false);
		
		JPContainer.add(JLSupplierId);
		JPContainer.add(JTFSupplierId);*/
		
		JLSupplierName.setBounds(5,72,105,20);
		JLSupplierName.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFSupplierName.setBounds(110,72,200,20);
		JTFSupplierName.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLSupplierName);
		JPContainer.add(JTFSupplierName);
		
		JLLocation.setBounds(5,94,105,20);
		JLLocation.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFLocation.setBounds(110,94,200,20);
		JTFLocation.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLLocation);
		JPContainer.add(JTFLocation);
		
		
		JLPhoneNumber.setBounds(5,116,105,20);
		JLPhoneNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFPhoneNumber.setBounds(110,116,200,20);
		JTFPhoneNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLPhoneNumber);
		JPContainer.add(JTFPhoneNumber);
		
		/*JLAddr.setBounds(5,138,105,20);
		JLAddr.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFAddr.setBounds(110,138,200,20);
		JTFAddr.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLAddr);
		JPContainer.add(JTFAddr);
		
		JLCity.setBounds(5,160,105,20);
		JLCity.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFCity.setBounds(110,160,200,20);
		JTFCity.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLCity);
		JPContainer.add(JTFCity);
		
		JLState.setBounds(5,182,105,20);
		JLState.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFState.setBounds(110,182,200,20);
		JTFState.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLState);
		JPContainer.add(JTFState);
		
		JLZipCode.setBounds(5,204,105,20);
		JLZipCode.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFZipCode.setBounds(110,204,200,20);
		JTFZipCode.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLZipCode);
		JPContainer.add(JTFZipCode);
		
		JLCountry.setBounds(5,226,105,20);
		JLCountry.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JCBCountry.setBounds(110,226,200,20);
		JCBCountry.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLCountry);
		JPContainer.add(JCBCountry);
		
		JLPhone.setBounds(5,248,105,20);
		JLPhone.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFPhone.setBounds(110,248,200,20);
		JTFPhone.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLPhone);
		JPContainer.add(JTFPhone);
		
		JLFax.setBounds(5,270,105,20);
		JLFax.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFFax.setBounds(110,270,200,20);
		JTFFax.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLFax);
		JPContainer.add(JTFFax);
		
		JLWebsite.setBounds(5,292,105,20);
		JLWebsite.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFWebsite.setBounds(110,292,200,20);
		JTFWebsite.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLWebsite);
		JPContainer.add(JTFWebsite);*/
		
		JBUpdate.setBounds(5,340,105,25);
		JBUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBUpdate.setMnemonic(KeyEvent.VK_A);
		JBUpdate.addActionListener(JBActionListener);
		JBUpdate.setActionCommand("update");
		JPContainer.add(JBUpdate);
		
		JBReset.setBounds(112,340,99,25);
		JBReset.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReset.setMnemonic(KeyEvent.VK_R);
		JBReset.addActionListener(JBActionListener);
		JBReset.setActionCommand("reset");
		JPContainer.add(JBReset);
		
		JBCancel.setBounds(212,340,99,25);
		JBCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCancel.setMnemonic(KeyEvent.VK_C);
		JBCancel.addActionListener(JBActionListener);
		JBCancel.setActionCommand("cancel");
		JPContainer.add(JBCancel);
		
		getContentPane().add(JPContainer);
		setSize(325,405);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-405)/2));
	}
	private boolean RequiredFieldEmpty(){
		if(JTFSupplierName.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Some required fields is/are empty.\nPlease check it and try again.","Inventory Management System",JOptionPane.WARNING_MESSAGE);
			JTFSupplierId.requestFocus();
			return true;
		}else{
			return false;
		}
	}
	private void clearFields(){
		JTFSupplierId.setText("");
		JTFSupplierName.setText("");
		JTFLocation.setText("");
		JTFPhoneNumber.setText("");
	}
	
	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="update"){
				if(RequiredFieldEmpty()==false){
					if(ADDING_STATE == true){
						try{
							
							stAES.executeUpdate("INSERT INTO imssupplier(SupplierID,CompanyName,ContactName,ContactTitle,Address,CityTown,StateProvince,ZipCode,Country,Phone,Fax,Website) " +
		   							   	        "VALUES ('" +
		   							   	        JTFSupplierName.getText() + "', '" +
		   							   	        JTFLocation.getText() + "', '" +
		   							   	        JTFPhoneNumber.getText()+
		   							   	        "')");
		   					int total =0;
		   					total = clsPublicMethods.getMaxNum("SELECT * FROM imssupplier ORDER BY sid ASC",cnAES,"sid");
		   					if(total != 0){
		   						FrmSupplier.reloadRecord("SELECT * FROM imssupplier WHERE sid = " + total + " ORDER BY supplier ASC");	
		   					}else{
		   						FrmSupplier.reloadRecord("SELECT * FROM imssupplier ORDER BY supplier ASC");	
		   					}
		   					total =0;
		   					
		   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Inventory ManagementSystem",JOptionPane.INFORMATION_MESSAGE);
		   					String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Inventory Management System",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
							if(PromptResult==0){
								clearFields();
								JTFSupplierId.requestFocus(true);
							}else{
								dispose();
							}
		   				}catch(SQLException sqlEx){
		   					System.out.println(sqlEx.getMessage());
		   				}
					}else{
						try{
							String RowIndex;
							RowIndex = rsAES.getString("sid");	        
							stAES.executeUpdate("UPDATE imssupplier SET Supplier = '" +
		   							   	        JTFSupplierName.getText() + "', Location = '" +
		   							   	        JTFLocation.getText() + "', Pnumber = '" +
		   							   	        JTFPhoneNumber.getText() +
		   							   	        "' WHERE sid = " + RowIndex);
		   					FrmSupplier.reloadRecord("SELECT * FROM imssupplier WHERE sid = " + RowIndex + " ORDER BY supplier ASC");	
		   					JOptionPane.showMessageDialog(null,"Changes in the record has been successfully save.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
		   					RowIndex="";
							dispose();
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