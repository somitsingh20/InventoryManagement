package com.inventory;

import java.awt.BorderLayout;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class frm_add_edit_invoice extends JDialog {
	
	JButton JBUpdate = new JButton(new ImageIcon("images/save.png"));
	JButton JBReset = new JButton("Reset",new ImageIcon("images/reset.png"));
	JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));
	
	JLabel JLPic1 = new JLabel();
	JLabel JLBanner = new JLabel("Please fill-up all the required fields.");
	
	//public static JScrollPane CusTableJSP = new JScrollPane();
	
	JLabel JLName = new JLabel("Customer Name:");
	JLabel JLContName = new JLabel("Contact Name:");
	JLabel JLInvNumber = new JLabel("Invoice Number");
	JLabel JLDesc = new JLabel("Description");
	JLabel JLQty = new JLabel("Quantity");
	JLabel JLUnitCost = new JLabel("Unit Cost");
	JLabel JLTotalPrice = new JLabel("Total Price");
	
	JTextField JTFInvNumber = new JTextField();
	JTextField JTFName = new JTextField();
	JTextField JTFContName = new JTextField();
	JTextField JTFDescription = new JTextField();
	JTextField JTFQty = new JTextField();
	JTextField JTFUnitCost = new JTextField();
	JTextField JTFTotalPrice = new JTextField();
	
	Connection cnAEI;
	Statement stAEI;
	ResultSet rsAEI;
	
	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	boolean ADDING_STATE;
	
	public frm_add_edit_invoice(boolean ADD_STATE, JFrame OwnerForm, Connection srcCon, String srcSQL) {
		
		super(OwnerForm,true);
		cnAEI = srcCon;
		ADDING_STATE = ADD_STATE;
		
		try{
			stAEI = cnAEI.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){
			System.out.println("\nERROR IN frm_add_edit_invoice(frm_add_edit_invoice):" + sqlEx + "\n");
		}
		
		if(ADD_STATE==true){
			JLPic1.setIcon(new ImageIcon("images/bNew.png"));
			setTitle("New Invoice");
			JBUpdate.setText("Update");
		}
		
		
		JPanel JPContainer = new JPanel();
		JPContainer.setLayout(null);
		
		JLPic1.setBounds(5,5,32,32);
		//JPContainer.add(JLPic1);
		
		JLBanner.setBounds(55,5,268,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		//JPContainer.add(JLBanner);
		
		////////////////////
		
		Object[][] data = { { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 }, { 4, 4, 4 } };
		String[] columnNames = { "Column 1", "Column 2", "Column 3" };
		JTable exampleJTable = new JTable(data, columnNames);
		
		JScrollPane sp = new JScrollPane(exampleJTable);
		
		TableModel tabModel = exampleJTable.getModel();
		JPContainer.add(exampleJTable);
		//////////////////////////////////
		JLInvNumber.setBounds(5, 50, 105, 20);
		JLInvNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFInvNumber.setBounds(110,50,200,20);
		JTFInvNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLInvNumber);
		JPContainer.add(JTFInvNumber);
		
		JLName.setBounds(5,72,105,20);
		JLName.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JTFName.setBounds(110,72,200,20);
		JTFName.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLName);
		JPContainer.add(JTFName);
		
		JBUpdate.setBounds(5,318,105,25);
		JBUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBUpdate.setMnemonic(KeyEvent.VK_A);
		JBUpdate.addActionListener(JBActionListener);
		JBUpdate.setActionCommand("update");
		JPContainer.add(JBUpdate);
		
		JBReset.setBounds(112,318,99,25);
		JBReset.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReset.setMnemonic(KeyEvent.VK_R);
		JBReset.addActionListener(JBActionListener);
		JBReset.setActionCommand("reset");
		JPContainer.add(JBReset);
		
		JBCancel.setBounds(212,318,99,25);
		JBCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCancel.setMnemonic(KeyEvent.VK_C);
		JBCancel.addActionListener(JBActionListener);
		JBCancel.setActionCommand("cancel");
		JPContainer.add(JBCancel);
		
		getContentPane().add(JPContainer);
		setSize(360,410);
		setResizable(false);
		setLocation((screen.width - 360)/2,((screen.height-410)/2));
		
	}
	
	private boolean RequiredFieldEmpty(){
		if(JTFName.getText().equals("") || JTFContName.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Some required fields is/are empty.\nPlease check it and try again.","Inventory Management System",JOptionPane.WARNING_MESSAGE);
			JTFName.requestFocus();
			return true;
		}else{
			return false;
		}
	}
	private void clearFields(){
		
	}
	
	ActionListener JBActionListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {

			String srcObj = e.getActionCommand();
			if(srcObj=="update"){
				if(RequiredFieldEmpty()==false){
					if(ADDING_STATE==true){
						try{
							stAEI.executeUpdate("INSERT INTO tblInvoice(invoicenumber,companyname,contactname,description,quantity,unitcost,totalprice)"+ 
												"VALUES ('"+ 
												JTFInvNumber.getText() + "','" +
												JTFName.getText() + "','" +
												JTFContName.getText() +	"', '" +
												JTFDescription.getText() + "', '" +
												JTFQty.getText() + "','" +
												JTFUnitCost.getText() + "','" +
												JTFTotalPrice.getText() + "')");
							
							int total=0;
							total = clsPublicMethods.getMaxNum("SELECT * FROM tblInvoice ORDER BY InvoiceIndex ASC",cnAEI,"InvoiceIndex");
		   					if(total != 0){
		   						FrmCustomer.reloadRecord("SELECT * FROM tblInvoice WHERE InvoiceIndex = " + total + " ORDER BY InvoiceNumber ASC");	
		   					}else{
		   						FrmCustomer.reloadRecord("SELECT * FROM tblInvoice ORDER BY InvoiceNumber ASC");	
		   					}
		   					total =0;
		   					
		   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
						}
						catch(SQLException sqlEx){
		   					System.out.println(sqlEx.getMessage());
		   				}
					}
					else {
						try{
							String RowIndex;
							RowIndex = rsAEI.getString("InvoiceIndex");
							stAEI.executeUpdate("UPDATE tblInvoice SET InvoiceNumber = '" +
												JTFInvNumber.getText() + "',CompanyName = '" +
												JTFName.getText() + "', ContactName = '" +
												JTFContName.getText() +	"', Description = '" +
												JTFDescription.getText() + "', Quantity = '" +
												JTFQty.getText() + "', UnitCost = '" +
												JTFUnitCost.getText() + "', TotalPrice = '" +
												JTFTotalPrice.getText() + 
												"' WHERE InvoiceIndex = "+ RowIndex);
							
							FrmInvoice.reloadRecord("SELECT * FROM tblInvoice WHERE InvoiceIndex = " + RowIndex + " ORDER BY InvoiceNumber ASC");	
		   					JOptionPane.showMessageDialog(null,"Changes in the record have been successfully saved.","Inventory Management System",JOptionPane.INFORMATION_MESSAGE);
		   					RowIndex="";
							dispose();
						}
						catch(SQLException sqlEx){
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
