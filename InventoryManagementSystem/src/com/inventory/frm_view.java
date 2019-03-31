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
import javax.swing.table.TableModel;;

public class frm_view extends JDialog {
	
	public static JPanel JPContainer = new JPanel();
	
	JFrame JFParentFrame;
	public static Connection cnV;
	public static Statement stV;
	public static ResultSet rsV;
	public static ResultSet rsV1;
	
	//public static String Content[][];
	public static String strSQL;
	
	public static JScrollPane ViewTableJSP = new JScrollPane();
	public static JTable JTViewTable;
	public static int total = 0;
	
	JLabel JLCustomerName = new JLabel("Customer Name:");
	JLabel JLPhoneNo = new JLabel("Phone No:");
	JLabel JLCustomerID = new JLabel("Customer ID:");
	
	static JTextField JTFCustomerName = new JTextField();
	static JTextField JTFPhoneNo = new JTextField();
	static JTextField JTFCustomerID = new JTextField();
	
	JButton JBViewInvoice = new JButton("JBViewInvoice");
	
	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	static DefaultTableModel model = new DefaultTableModel(
			new Object[] { "PurchaseDate", "Invoice Number", "Purchase Time","Total"}, 1);
	JTable table = new JTable(model);
	boolean ADDING_STATE;

	public frm_view(boolean ADD_STATE, JFrame OwnerForm, Connection srcCon, String srcSQL) {
		
		super(OwnerForm,true);
		cnV = srcCon;
		ADDING_STATE = ADD_STATE;
		try{
			stV = cnV.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch( SQLException sqlEx){
			if(sqlEx.getMessage() != null){
				System.out.println(sqlEx.getMessage());
			}else{
				JOptionPane.showMessageDialog(null,"Please select a record in the list to view.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		try {
			int count = 0;
			int rows = model.getRowCount();
			rsV = stV.executeQuery(srcSQL);
			
			while(rsV.next()){
				//System.out.println(rsV.getString("cname"));
				model.setValueAt(rsV.getString("dt"), count, 0);
				model.setValueAt(rsV.getString("invoice_number"), count, 1);
				model.setValueAt(rsV.getString("to_char(purchasetime,'hh24:mi:ss')"), count, 2);
				model.setValueAt(rsV.getString("total"), count, 3);
				model.setRowCount(rows + 1);
				count++;
				}
			}
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		JPContainer.setLayout(null);
		
		JLCustomerName.setBounds(5, 5, 100, 20);
		JLCustomerName.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFCustomerName.setBounds(105, 5, 80, 20);
		JTFCustomerName.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFCustomerName.setEditable(false);
		
		JPContainer.add(JLCustomerName);
		JPContainer.add(JTFCustomerName);
		
		JLPhoneNo.setBounds(200, 5, 55, 20);
		JLPhoneNo.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFPhoneNo.setBounds(260, 5, 80, 20);
		JTFPhoneNo.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFPhoneNo.setEditable(false);
		
		JPContainer.add(JLPhoneNo);
		JPContainer.add(JTFPhoneNo);
		
		JLCustomerID.setBounds(355, 5, 70, 20);
		JLCustomerID.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFCustomerID.setBounds(430, 5, 80, 20);
		JTFCustomerID.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFCustomerID.setEditable(false);
		
		JPContainer.add(JLCustomerID);
		JPContainer.add(JTFCustomerID);
		
		//JTable table = new JTable(model);
		
		ViewTableJSP.getViewport().add(table);
		ViewTableJSP.setBounds(5,40,505,200);
		JPContainer.add(ViewTableJSP);
		
		JBViewInvoice.setBounds(190,370,150,25);
		JBViewInvoice.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBViewInvoice.setMnemonic(KeyEvent.VK_S);
		JBViewInvoice.addActionListener(JBActionListener);
		JBViewInvoice.setActionCommand("viewInvoice");
		JPContainer.add(JBViewInvoice);
		
		getContentPane().add(JPContainer);
		setSize(530,450);
		setResizable(true);
		setModalityType(JDialog.ModalityType.DOCUMENT_MODAL);
		setLocation((screen.width - 420) / 2, ((screen.height - 660) / 2));
	}
	
	ActionListener JBActionListener = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			System.out.println("Inside view invoice action");
			String srcObj = e.getActionCommand();
			if(srcObj=="viewInvoice"){
					try{
	
						System.out.println(table.getValueAt(table.getSelectedRow(), 1));	
						if(model.getValueAt(0, 1) != null){
								JDialog JDView = new FrmViewInvoiceDetails(false,JFParentFrame,cnV,"SELECT * FROM imssalesrecord WHERE invoice_number = " + table.getValueAt(table.getSelectedRow(), 1));
								JDView.show();
							}
					}catch(Exception sqlE){
						if(sqlE.getMessage() != null){
							System.out.println(sqlE.getMessage());
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to modify.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
			}
			}
		};
	public static void reloadRecord(String srcSQL) {
		try {
			rsV1 = stV.executeQuery(srcSQL);
			if(rsV1.next()){
				JTFCustomerID.setText(rsV1.getString("cid").toString());
				JTFCustomerName.setText(rsV1.getString("cname"));
				JTFPhoneNo.setText(rsV1.getString("phone"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
