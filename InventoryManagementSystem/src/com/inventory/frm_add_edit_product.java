package com.inventory;

import java.sql.Connection;

import javax.swing.JDialog;
import javax.swing.JFrame;

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

	JLabel JLItemNo = new JLabel("Item No:");
	JLabel JLDescription = new JLabel("Description:");
	JLabel JLQuantity = new JLabel("Quantity:");
	JLabel JLUnitCost = new JLabel("Unit Cost:");
	JLabel JLsalesprice = new JLabel("Sales Price:");
	JLabel JLQtyonhand = new JLabel("Quantity On Hand:");
	JLabel JLlocation = new JLabel("Location:");
	JLabel JLcategoryindex = new JLabel("Category Index:");
	JLabel JLsupplierindex = new JLabel("Supplier Index:");

	JTextField JTFItemNo = new JTextField();
	JTextField JTFDescription = new JTextField();
	JTextField JTFQuantity = new JTextField();
	JTextField JTFUnitCost = new JTextField();
	JTextField JTFsalesprice = new JTextField();
	JTextField JTFQtyonhand = new JTextField();
	JTextField JTFlocation = new JTextField();
	JComboBox JCBCategoryIndex;
	JComboBox JCBSupplierIndex;

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
		
		JCBSupplierIndex = clsPublicMethods.fillCombo("SELECT * FROM tblSupplier",cnAEP,"supplierindex");
		JCBCategoryIndex = clsPublicMethods.fillCombo("SELECT * FROM tblCategory",cnAEP,"categoryindex");
		
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
					JTFItemNo.setText("" + rsAEP.getString("itemno"));
					JTFDescription.setText("" + rsAEP.getString("Description"));
					JTFQuantity.setText("" + rsAEP.getString("Quantity"));
					JTFUnitCost.setText("" + rsAEP.getString("Unitcost"));
					JTFsalesprice.setText("" + rsAEP.getString("salesprice"));
					JTFQtyonhand.setText("" + rsAEP.getString("qtyonhand"));
					JTFlocation.setText("" + rsAEP.getString("location"));
					JCBCategoryIndex.setSelectedItem("" + rsAEP.getString("categoryindex"));
					JCBSupplierIndex.setSelectedItem("" + rsAEP.getString("SupplierIndex"));
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

		
		JLItemNo.setBounds(5,50,105,20);
		JLItemNo.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFItemNo.setBounds(110,50,200,20);
		JTFItemNo.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLItemNo);
		JPContainer.add(JTFItemNo);

		
		JLDescription.setBounds(5,72,105,20);
		JLDescription.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFDescription.setBounds(110,72,200,20);
		JTFDescription.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLDescription);
		JPContainer.add(JTFDescription);

		
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

		
		JLQtyonhand.setBounds(5,160,105,20);
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
		JPContainer.add(JCBCategoryIndex);

		
		JLsupplierindex.setBounds(5,226,105,20);
		JLsupplierindex.setFont(new Font("Dialog",Font.PLAIN,12));

		JCBSupplierIndex.setBounds(110,226,200,20);
		JCBSupplierIndex.setFont(new Font("Dialog",Font.PLAIN,12));

		JPContainer.add(JLsupplierindex);
		JPContainer.add(JCBSupplierIndex);
		
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
		if(JTFItemNo.getText().equals("") || JTFDescription.getText().equals("") || JTFQuantity.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Some required fields is/are empty.\nPlease check it and try again.","Inventory Management System",JOptionPane.WARNING_MESSAGE);
			JTFItemNo.requestFocus();
			return true;
		}else{
			return false;
		}
	}
	private void clearFields(){
		JTFItemNo.setText("");
		JTFDescription.setText("");
		JTFQuantity.setText("");
		JTFUnitCost.setText("");
		JTFsalesprice.setText("");
		JTFQtyonhand.setText("");
		JTFlocation.setText("");
		JCBCategoryIndex.setSelectedIndex(0);
		JCBSupplierIndex.setSelectedIndex(0);
	}

	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="update"){
				if(RequiredFieldEmpty()==false){
					if(ADDING_STATE == true){
						try{
							stAEP.executeUpdate("INSERT INTO tblItems(Itemno,description,quantity,unitcost,salesprice,qtyonhand,location,categoryindex,supplierindex) " +
		   							   	        "VALUES ('" +
		   							   	        JTFItemNo.getText() + "', '" +
		   							   	        JTFDescription.getText() + "', '" +
		   							   	        JTFQuantity.getText() + "', '" +
		   							   	        JTFUnitCost.getText() + "', '" +
		   							   	        JTFsalesprice.getText() + "', '" +
		   							   	        JTFQtyonhand.getText() + "', '" +
		   							   	        JTFlocation.getText() + "', '" +
		   							   	        JCBCategoryIndex.getSelectedItem().toString() + "', '" +
		   							   	        JCBSupplierIndex.getSelectedItem().toString() + "', '" +
		   							   	        "')");
		   					
		   					int total =0;
		   					total = clsPublicMethods.getMaxNum("SELECT * FROM tblitems ORDER BY itemindex ASC",cnAEP,"itemindex");
		   					if(total != 0){
		   						FrmWarehouse.reloadRecord("SELECT * FROM tblitems WHERE itemindex = " + total + " ORDER BY description ASC");
		   					}else{
		   						FrmWarehouse.reloadRecord("SELECT * FROM tblitems ORDER BY description ASC");
		   					}
		   					total =0;
		   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
		   					String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Inventory Management System",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
							if(PromptResult==0){
								clearFields();
								JTFItemNo.requestFocus(true);
							}else{
								dispose();
							}
		   				}catch(SQLException sqlEx){
		   					System.out.println(sqlEx.getMessage());
		   				}
					}else{
						try{
							String RowIndex;
							RowIndex = rsAEP.getString("itemindex");
							stAEP.executeUpdate("UPDATE tblitems SET itemno = '" +
		   							   	        JTFItemNo.getText() + "', Description = '" +
		   							   	        JTFDescription.getText() + "', Quantity = '" +
		   							   	        JTFQuantity.getText() + "', UnitCost = '" +
		   							   	        JTFUnitCost.getText()  + "', salesprice = '" +
		   							   	        JTFsalesprice.getText()  + "', Qtyonhand = '" +
		   							   	        JTFQtyonhand.getText()   + "', location = '" +
		   							   	        JTFlocation.getText()   + "', categoryindex = '" +
		   							   	        JCBCategoryIndex.getSelectedItem().toString()   + "', supplierindex = '" +
		   							   	        JCBSupplierIndex.getSelectedItem().toString()  + "', ItemIndex = '" +
		   							   	        "' WHERE itemIndex = " + RowIndex);
		   					FrmWarehouse.reloadRecord("SELECT * FROM tblitems WHERE itemIndex = " + RowIndex + " ORDER BY description ASC");
							JOptionPane.showMessageDialog(null,"Changes in the record have been successfully saved.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
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
