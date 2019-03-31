package com.inventory;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class frm_add_edit_product extends JDialog{
	
	JButton JBUpdate = new JButton(new ImageIcon("images/save.png"));
	JButton JBReset = new JButton("Reset",new ImageIcon("images/reset.png"));
	JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));

	JLabel JLPic1 = new JLabel();
	JLabel JLBanner = new JLabel("Please fill-up all the required fields.");

	JLabel JLPid = new JLabel("Product ID");
	JLabel JLProductName = new JLabel("Product Name");
	JLabel JLQuantity = new JLabel("Quantity:");
	JLabel JLUnitCost = new JLabel("Unit Cost:");
	JLabel JLsalesprice = new JLabel("Sales Price:");
	//JLabel JLQtyonhand = new JLabel("Quantity On Hand:");
	//JLabel JLlocation = new JLabel("Location:");
	//JLabel JLcategoryindex = new JLabel("Category Index:");
	JLabel JLsuppliername = new JLabel("Supplier Name:");

	JTextField JTFPid = new JTextField();
	JTextField JTFProductName = new JTextField();
	JTextField JTFQuantity = new JTextField();
	JTextField JTFUnitCost = new JTextField();
	JTextField JTFsalesprice = new JTextField();
	JComboBox JCBSupplierID;

	Connection cnAEP;
	Statement stAEP;
	ResultSet rsAEP;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	boolean ADDING_STATE;

	public frm_add_edit_product(boolean ADD_STATE,JFrame OwnerForm,Connection srcCon,String srcSQL){
		super(OwnerForm,true);
		cnAEP = srcCon;
		ADDING_STATE = ADD_STATE;
		try{
			stAEP = cnAEP.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){

		}
		
		JCBSupplierID = clsPublicMethods.fillCombo("SELECT * FROM imssupplier", cnAEP, "sid");
		
		if(ADD_STATE==true){
			JLPic1.setIcon(new ImageIcon("images/bNew.png"));
			setTitle("Add New Product");
			JBUpdate.setText("Update");
		}else{
			JLPic1.setIcon(new ImageIcon("images/bModify.png"));
			setTitle("Modify Product Details");
			JBUpdate.setText("Save");
			try{
				rsAEP = stAEP.executeQuery(srcSQL);
				rsAEP.next();
					JTFPid.setText("" + rsAEP.getString("pid"));
					JTFProductName.setText("" + rsAEP.getString("productname"));
					JTFQuantity.setText("" + rsAEP.getString("Quantity"));
					JTFUnitCost.setText("" + rsAEP.getString("Unitcost"));
					JTFsalesprice.setText("" + rsAEP.getString("sprice"));
					//JTFQtyonhand.setText("" + rsAEP.getString("qtyonhand"));
					//JTFlocation.setText("" + rsAEP.getString("location"));
					//JCBCategoryIndex.setSelectedItem("" + rsAEP.getString("categoryindex"));
					JCBSupplierID.setSelectedItem("" + rsAEP.getString("sid"));
			}catch(SQLException sqlEx){
				System.out.println(sqlEx.getMessage());
			}
		}
		JPanel JPContainer = new JPanel();
		JPContainer.setLayout(null);
		
		JLPic1.setBounds(5,5,32,32);
		JPContainer.add(JLPic1);

		
		JLBanner.setBounds(55,5,268,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JLBanner);

		
		/*JLPid.setBounds(5,50,105,20);
		JLPid.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFPid.setBounds(110,50,200,20);
		JTFPid.setFont(new Font("Dialog",Font.PLAIN,12));
		JTFPid.setEditable(false);

		JPContainer.add(JLPid);
		JPContainer.add(JTFPid);*/

		JLProductName.setBounds(5,72,105,20);
		JLProductName.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFProductName.setBounds(110,72,200,20);
		JTFProductName.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLProductName);
		JPContainer.add(JTFProductName);

		
		JLQuantity.setBounds(5,94,105,20);
		JLQuantity.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFQuantity.setBounds(110,94,200,20);
		JTFQuantity.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLQuantity);
		JPContainer.add(JTFQuantity);

		
		JLUnitCost.setBounds(5,116,105,20);
		JLUnitCost.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFUnitCost.setBounds(110,116,200,20);
		JTFUnitCost.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLUnitCost);
		JPContainer.add(JTFUnitCost);

		
		JLsalesprice.setBounds(5,138,105,20);
		JLsalesprice.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFsalesprice.setBounds(110,138,200,20);
		JTFsalesprice.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLsalesprice);
		JPContainer.add(JTFsalesprice);

		
		/*JLQtyonhand.setBounds(5,160,105,20);
		JLQtyonhand.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFQtyonhand.setBounds(110,160,200,20);
		JTFQtyonhand.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLQtyonhand);
		JPContainer.add(JTFQtyonhand);

		
		JLlocation.setBounds(5,182,105,20);
		JLlocation.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFlocation.setBounds(110,182,200,20);
		JTFlocation.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLlocation);
		JPContainer.add(JTFlocation);

		
		JLcategoryindex.setBounds(5,204,105,20);
		JLcategoryindex.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBCategoryIndex.setBounds(110,204,200,20);
		JCBCategoryIndex.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLcategoryindex);
		JPContainer.add(JCBCategoryIndex);*/

		
		JLsuppliername.setBounds(5,226,105,20);
		JLsuppliername.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBSupplierID.setBounds(110,226,200,20);
		JCBSupplierID.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLsuppliername);
		JPContainer.add(JCBSupplierID);
		
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
			JOptionPane.showMessageDialog(null,"Some required fields is/are empty.\nPlease check it and try again.","Inventory Management System",JOptionPane.WARNING_MESSAGE);
			JTFPid.requestFocus();
			return true;
		}else{
			return false;
		}
	}
	private void clearFields(){
		JTFPid.setText("");
		JTFProductName.setText("");
		JTFQuantity.setText("");
		JTFUnitCost.setText("");
		JTFsalesprice.setText("");
		//JTFQtyonhand.setText("");
		//JTFlocation.setText("");
		//JCBCategoryIndex.setSelectedIndex(0);
		JCBSupplierID.setSelectedIndex(0);
	}

	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="update"){
				if(RequiredFieldEmpty()==false){
					if(ADDING_STATE == true){
						try{
							stAEP.executeUpdate("INSERT INTO imsproducts(productname,quantity,unitcost,sprice,sid,datetime) " +
		   							   	        "VALUES ('" +
		   							   	        JTFProductName.getText() + "', '" +
		   							   	        JTFQuantity.getText() + "', '" +
		   							   	        JTFUnitCost.getText() + "', '" +
		   							   	        JTFsalesprice.getText() + "', '" +
		   							   	        JCBSupplierID.getSelectedItem().toString() + "',sysdate)");
		   					
							stAEP.executeUpdate("commit");
		   					int total =0;
		   					total = clsPublicMethods.getMaxNum("SELECT * FROM imsproducts ORDER BY pid ASC",cnAEP,"pid");
		   					if(total != 0){
		   						FrmProduct.reloadRecord("SELECT * FROM imsproducts WHERE pid = " + total + " ORDER BY pid ASC");
		   					}else{
		   						FrmProduct.reloadRecord("SELECT * FROM imsproducts ORDER BY pid ASC");
		   					}
		   					total =0;
		   					
		   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
		   					String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Inventory Management System",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
							if(PromptResult==0){
								clearFields();
								JTFPid.requestFocus(true);
							}else{
								dispose();
							}
		   				}catch(SQLException sqlEx){
		   					System.out.println(sqlEx.getMessage());
		   				}
					}else{
						try{
							String RowIndex;
							RowIndex = rsAEP.getString("pid");
							stAEP.executeUpdate("UPDATE imsproducts SET pid = '" +
		   							   	        JTFPid.getText() + "', productname = '" +
		   							   	        JTFProductName.getText() + "', Quantity = '" +
		   							   	        JTFQuantity.getText() + "', UnitCost = '" +
		   							   	        JTFUnitCost.getText()  + "', sprice = '" +
		   							   	        JTFsalesprice.getText()  + "', sid = '" +
		   							   	        JCBSupplierID.getSelectedItem().toString()  +
		   							   	        "' WHERE pid = " + RowIndex);
							FrmProduct.reloadRecord("SELECT * FROM imsproducts WHERE pid = " + RowIndex + " ORDER BY pid ASC");
							JOptionPane.showMessageDialog(null,"Changes in the record have been successfully saved.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
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
