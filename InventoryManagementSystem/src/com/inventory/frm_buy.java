package com.inventory;

import java.awt.Color;
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
import javax.swing.table.TableModel;
import java.util.Random;;

public class frm_buy extends JDialog {
	
	public static JPanel JPContainer = new JPanel();
	
	JButton JBUpdate = new JButton(new ImageIcon("images/save.png"));
	JButton JBReset = new JButton("Reset",new ImageIcon("images/reset.png"));
	JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));
	
	JLabel JLPic1 = new JLabel();
	JLabel JLBanner = new JLabel("Please fill-up all the required fields.");
	JLabel JLInvoiceNumber = new JLabel("Invoice Number");
	JLabel JLCustomerName = new JLabel("Customer Name");
	JLabel JLGrandTotal = new JLabel("Grand Total");
	
	JTextField JTFCustomerName = new JTextField();
	JTextField JTFInvoiceNumer = new JTextField();
	JTextField JTFGrandTotal = new JTextField();
	JTextField JTFQuantityTotal = new JTextField();
	
	JButton JBGenerateInvoice = new JButton("Generate Invoice");
	
	public static Connection cnB;
	public static Statement stB;
	public static ResultSet rsB;
	
	public static String Content[][];
	public static String strSQL;
	public static String strSumSQL;
	public static int rowNum = 0;
	public static int total = 0;
	
	public static JScrollPane CusTableJSP = new JScrollPane();
	public static JTable JTCusTable;
	
	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	boolean ADDING_STATE;

	public frm_buy(boolean ADD_STATE, JFrame OwnerForm, Connection srcCon, String srcSQL) {
		
		super(OwnerForm,true);
		cnB = srcCon;
		ADDING_STATE = ADD_STATE;
		try{
			JLPic1.setIcon(new ImageIcon("images/bModify.png"));
			setTitle("Sell Record");
			JBUpdate.setText("Add");
			stB = cnB.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rsB = stB.executeQuery(srcSQL);
			rsB.next();
			JTFCustomerName.setText("" + rsB.getString("cname"));
			JTFInvoiceNumer.setText("" + rsB.getString("invoice_number"));
		}catch( SQLException sqlEx){
			if(sqlEx.getMessage() != null){
				System.out.println(sqlEx.getMessage());
			}else{
				JOptionPane.showMessageDialog(null,"Please select a record in the list to modify.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		strSQL = "SELECT * FROM gtotalview";
		strSumSQL = "SELECT SUM(quantity),SUM(totalprice) from gtotalview";
		try{
			rsB = stB.executeQuery(strSumSQL);
			while(rsB.next()){
				JTFQuantityTotal.setText(""+rsB.getString("sum(quantity)"));
				JTFGrandTotal.setText(""+rsB.getString("sum(totalprice)"));
			}
		}catch(Exception eE){
		}
		
		if(ADD_STATE==true){
			JLPic1.setIcon(new ImageIcon("images/bNew.png"));
			setTitle("Buy New");
			JBUpdate.setText("Update");
		}
		
		JPContainer.setLayout(null);
		
		JLPic1.setBounds(5,5,32,32);
		JPContainer.add(JLPic1);

		JLBanner.setBounds(55,5,268,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JLBanner);

		JLCustomerName.setBounds(5, 55, 100, 20);
		JLCustomerName.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JLCustomerName);
		
		JTFCustomerName.setBounds(105, 55, 80, 20);
		JTFCustomerName.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JTFCustomerName);
		
		JLInvoiceNumber.setBounds(200, 55, 100, 20);
		JLInvoiceNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JLInvoiceNumber);
		
		JTFInvoiceNumer.setBounds(300, 55, 80, 20);
		JTFInvoiceNumer.setFont(new Font("Dialog",Font.PLAIN,12));
		JPContainer.add(JTFInvoiceNumer);
		
		JTCusTable=CreateTable();
		CusTableJSP.getViewport().add(JTCusTable);
		CusTableJSP.setBounds(5,110,390,150);
		JPContainer.add(CusTableJSP);
		
		JLGrandTotal.setBounds(50, 280, 100, 20);
		JPContainer.add(JLGrandTotal);
		
		JTFQuantityTotal.setBounds(240,280,75,20);
		JPContainer.add(JTFQuantityTotal);
		
		JTFGrandTotal.setBounds(320,280,60,20);
		JPContainer.add(JTFGrandTotal);
		
		JBGenerateInvoice.setBounds(40, 320, 160, 20);
		JBGenerateInvoice.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBGenerateInvoice.setMnemonic(KeyEvent.VK_A);
		JBGenerateInvoice.addActionListener(JBActionListener);
		JBUpdate.setActionCommand("generateinvoicedetails");
		JPContainer.add(JBGenerateInvoice);
		
		getContentPane().add(JPContainer);
		setSize(420,450);
		setResizable(true);
		setLocation((screen.width - 325)/2,((screen.height-357)/2));
	}

	public static JTable CreateTable() {
		String ColumnHeaderName[] = {
				"Pid","Product","Unit Cost","Quantity","Price"
			};
		try{
			rsB = stB.executeQuery(strSQL);
			total = 0;
			
			rsB.afterLast();
			
			if(rsB.previous())total = rsB.getRow();
			
			rsB.beforeFirst();
			if(total > 0){
				Content = new String[total][6];
				while(rsB.next()){
					Content[rowNum][0] = "" + rsB.getString("pid");
					Content[rowNum][1] = "" + rsB.getString("product");
					Content[rowNum][2] = "" + rsB.getString("unitcost");
					Content[rowNum][3] = "" + rsB.getString("quantity");
					Content[rowNum][4] = "" + rsB.getString("totalprice");
					rowNum++;
				}
			}else{
				Content = new String[0][5];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
				Content[0][4] = " ";
			}
		}catch(Exception eE){
		}
		
		
		JTable NewTable = new JTable (Content,ColumnHeaderName){
			public boolean isCellEditable (int iRows, int iCols) {
				return false;
			}
		};
		NewTable.setPreferredScrollableViewportSize(new Dimension(320, 400));
		NewTable.setBackground(Color.white);

		NewTable.getColumnModel().getColumn(0).setWidth(10);
		NewTable.getColumnModel().getColumn(1).setWidth(70);
		NewTable.getColumnModel().getColumn(2).setWidth(20);
		NewTable.getColumnModel().getColumn(3).setWidth(20);
		
		ColumnHeaderName=null;
		Content=null;

		rowNum = 0;

		
		return NewTable;
	}
	ActionListener JBActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String srcObj = e.getActionCommand();
			if (srcObj == "generateinvoicedetails") {
				try{
					if(JTCusTable.getValueAt(JTCusTable.getSelectedRow(),JTCusTable.getSelectedColumn()) != null){
						//JDialog JDEdit = new frm_Invoice_generator(false,JFParentFrame,cnCus,"SELECT * FROM imscustomer WHERE cid = " + JTCusTable.getValueAt(JTCusTable.getSelectedRow(),0));
						//JDEdit.show();
					}
				}catch (Exception sqlEx) {
					System.out.println(sqlEx.getMessage());
				}
			}
		}
	};

}
