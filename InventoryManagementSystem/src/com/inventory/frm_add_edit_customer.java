package com.inventory;

import javax.swing.*;
import java.sql.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;

public class frm_add_edit_customer extends JDialog

{
	JButton JBUpdate = new JButton(new ImageIcon("images/save.png"));
	JButton JBReset = new JButton("Reset", new ImageIcon("images/reset.png"));
	JButton JBCancel = new JButton("Cancel", new ImageIcon("images/cancel.png"));

	JLabel JLPic1 = new JLabel();
	JLabel JLBanner = new JLabel("Please fill-up all the required fields.");
	JLabel JLCName = new JLabel("Customer Name:");
	JLabel JLPhone = new JLabel("Phone:");
	JLabel JLDatetime = new JLabel("Date-Time:");

	JTextField JTFCName = new JTextField();
	JTextField JTFPhone = new JTextField();
	JTextField JTFDate = new JTextField();

	// JLabel JLId = new JLabel("Customer ID:");
	// JLabel JLContName = new JLabel("Contact Name:");
	// JLabel JLContTitle = new JLabel("Contact Title:");
	// JLabel JLAddr = new JLabel("Address:");
	// JLabel JLCity = new JLabel("City/Town:");
	// JLabel JLState = new JLabel("State/Province:");
	// JLabel JLZipCode = new JLabel("Zip Code:");
	// JLabel JLCountry = new JLabel("Country:");

	// JTextField JTFId = new JTextField();
	// JTextField JTFContName = new JTextField();
	// JTextField JTFContTitle = new JTextField();
	// JTextField JTFAddr = new JTextField();
	// JTextField JTFCity = new JTextField();
	// JTextField JTFState = new JTextField();
	// JTextField JTFZipCode = new JTextField();
	// JComboBox JCBCountry;

	Connection cnAEC;
	Statement stAEC;
	ResultSet rsAEC;

	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	boolean ADDING_STATE;
	
	public frm_add_edit_customer(boolean ADD_STATE, JFrame OwnerForm, Connection srcCon, String srcSQL) {
		super(OwnerForm, true);
		cnAEC = srcCon;
		ADDING_STATE = ADD_STATE;
		try {
			stAEC = cnAEC.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException sqlEx) {
			System.out.println("\nERROR IN frm_add_edit_customer(frm_add_edit_customer):" + sqlEx + "\n");
		}

		if (ADD_STATE == true) {
			JLPic1.setIcon(new ImageIcon("images/bNew.png"));
			setTitle("Add New Customer");
			JBUpdate.setText("Update");

		} else {
			JLPic1.setIcon(new ImageIcon("images/bModify.png"));
			setTitle("Modify Customer");
			JBUpdate.setText("Save");
			try {
				rsAEC = stAEC.executeQuery(srcSQL);
				rsAEC.next();
				JTFCName.setText("" + rsAEC.getString("cname"));
				JTFPhone.setText("" + rsAEC.getString("phone"));
				JTFDate.setText("" + rsAEC.getString("datetime"));
			} catch (SQLException sqlEx) {
				System.out.println(sqlEx.getMessage());
			}
		}
		JPanel JPContainer = new JPanel();
		JPContainer.setLayout(null);

		JLPic1.setBounds(5, 5, 32, 32);
		JPContainer.add(JLPic1);

		JLBanner.setBounds(55, 5, 268, 48);
		JLBanner.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLBanner);

		/*
		 * JLId.setBounds(5,50,105,20); JLId.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFId.setBounds(110,50,200,20); JTFId.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLId); JPContainer.add(JTFId);
		 */

		JLCName.setBounds(5, 72, 105, 20);
		JLCName.setFont(new Font("Dialog", Font.PLAIN, 12));

		JTFCName.setBounds(110, 72, 200, 20);
		JTFCName.setFont(new Font("Dialog", Font.PLAIN, 12));

		JPContainer.add(JLCName);
		JPContainer.add(JTFCName);

		JLPhone.setBounds(5, 94, 105, 20);
		JLPhone.setFont(new Font("Dialog", Font.PLAIN, 12));

		JTFPhone.setBounds(110, 94, 200, 20);
		JTFPhone.setFont(new Font("Dialog", Font.PLAIN, 12));

		JPContainer.add(JLPhone);
		JPContainer.add(JTFPhone);

		/*
		 * JLDatetime.setBounds(5,116,105,20); JLDatetime.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFDate.setBounds(110,116,200,20); JTFDate.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLDatetime); JPContainer.add(JTFDate);
		 */

		/*
		 * JLAddr.setBounds(5,138,105,20); JLAddr.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFAddr.setBounds(110,138,200,20); JTFAddr.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLAddr); JPContainer.add(JTFAddr);
		 */

		/*
		 * JLCity.setBounds(5,160,105,20); JLCity.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFCity.setBounds(110,160,200,20); JTFCity.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLCity); JPContainer.add(JTFCity);
		 */

		/*
		 * JLState.setBounds(5,182,105,20); JLState.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFState.setBounds(110,182,200,20); JTFState.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLState); JPContainer.add(JTFState);
		 * 
		 * 
		 * JLZipCode.setBounds(5,204,105,20); JLZipCode.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFZipCode.setBounds(110,204,200,20); JTFZipCode.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLZipCode); JPContainer.add(JTFZipCode);
		 * 
		 * 
		 * JLCountry.setBounds(5,226,105,20); JLCountry.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JCBCountry.setBounds(110,226,200,20); JCBCountry.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLCountry); JPContainer.add(JCBCountry);
		 * 
		 * 
		 * JLPhone.setBounds(5,248,105,20); JLPhone.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFPhone.setBounds(110,248,200,20); JTFPhone.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLPhone); JPContainer.add(JTFPhone);
		 * 
		 * 
		 * JLFax.setBounds(5,270,105,20); JLFax.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JTFFax.setBounds(110,270,200,20); JTFFax.setFont(new
		 * Font("Dialog",Font.PLAIN,12));
		 * 
		 * JPContainer.add(JLFax); JPContainer.add(JTFFax);
		 */

		JBUpdate.setBounds(5, 318, 105, 25);
		JBUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBUpdate.setMnemonic(KeyEvent.VK_A);
		JBUpdate.addActionListener(JBActionListener);
		JBUpdate.setActionCommand("update");
		JPContainer.add(JBUpdate);

		JBReset.setBounds(112, 318, 99, 25);
		JBReset.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReset.setMnemonic(KeyEvent.VK_R);
		JBReset.addActionListener(JBActionListener);
		JBReset.setActionCommand("reset");
		JPContainer.add(JBReset);

		JBCancel.setBounds(212, 318, 99, 25);
		JBCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCancel.setMnemonic(KeyEvent.VK_C);
		JBCancel.addActionListener(JBActionListener);
		JBCancel.setActionCommand("cancel");
		JPContainer.add(JBCancel);

		getContentPane().add(JPContainer);
		setSize(325, 383);
		setResizable(false);
		setLocation((screen.width - 325) / 2, ((screen.height - 383) / 2));
	}

	private boolean RequiredFieldEmpty() {
		if (JTFCName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Some required fields is/are empty.\nPlease check it and try again.",
					"Inventory Management System", JOptionPane.WARNING_MESSAGE);
			JTFCName.requestFocus();
			return true;
		} else {
			return false;
		}
	}

	private void clearFields() {
		JTFCName.setText("");
		JTFPhone.setText("");
		JTFDate.setText("");
	}

	ActionListener JBActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String srcObj = e.getActionCommand();
			if (srcObj == "update") {
				if (RequiredFieldEmpty() == false) {
					if (ADDING_STATE == true) {
						int min =101;
						int max =999;
						Random r = new Random();
						int generatedInvoiceNumber = r.nextInt((max - min) + 1) + min;
						
						try {

							stAEC.executeUpdate("INSERT INTO imsCustomer(cname,phone,datetime,invoice_number) " + "VALUES ('"
									+ JTFCName.getText() + "', '" + JTFPhone.getText() + "',sysdate ,'"+ generatedInvoiceNumber+"')");
							stAEC.executeUpdate("commit");
							int total = 0;
							total = clsPublicMethods.getMaxNum("SELECT * FROM imsCustomer ORDER BY cid ASC", cnAEC,
									"cid");
							if (total != 0) {
								FrmCustomer.reloadRecord(
										"SELECT * FROM imsCustomer WHERE cid = " + total + " ORDER BY CName ASC");
							} else {
								FrmCustomer.reloadRecord("SELECT * FROM imsCustomer ORDER BY CName ASC");
							}
							total = 0;

							JOptionPane.showMessageDialog(null, "New record has been successfully added.",
									"Inventory Management System", JOptionPane.INFORMATION_MESSAGE);
							String ObjButtons[] = { "Yes", "No" };
							int PromptResult = JOptionPane.showOptionDialog(null, "Do you want add another record?",
									"Inventory Management System", JOptionPane.DEFAULT_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[0]);
							if (PromptResult == 0) {
								clearFields();
								JTFCName.requestFocus(true);
							} else {
								dispose();
							}
						} catch (SQLException sqlEx) {
							System.out.println(sqlEx.getMessage());
						}
					} else {
						try {
							String RowIndex;
							RowIndex = rsAEC.getString("cid");
							stAEC.executeUpdate("UPDATE imsCustomer SET CName = '" + JTFCName.getText() + "', phone = '"
									+ JTFPhone.getText() + "', datetime = '" + JTFDate.getText() + "' WHERE cid = "
									+ RowIndex);
							FrmCustomer.reloadRecord(
									"SELECT * FROM imsCustomer WHERE cid = " + RowIndex + " ORDER BY CName ASC");
							JOptionPane.showMessageDialog(null, "Changes in the record have been successfully saved.",
									"Inventory Management System", JOptionPane.INFORMATION_MESSAGE);
							RowIndex = "";
							dispose();
						} catch (SQLException sqlEx) {
							System.out.println(sqlEx.getMessage());
						}
					}
				}
			} else if (srcObj == "reset") {
				clearFields();
			} else if (srcObj == "cancel") {
				dispose();
			}
		}
	};
}
